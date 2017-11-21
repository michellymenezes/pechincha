package com.projeto1.projeto1.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.adapters.CategoryCardAdapter;
import com.projeto1.projeto1.adapters.CategoryListAdapter;
import com.projeto1.projeto1.adapters.MarketCardAdapter;
import com.projeto1.projeto1.adapters.ProductListAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsBySearchTask;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainFragment extends Fragment implements SaleListener,ProductListener, MarketListener{


    public static final String TAG = "MAIN_FRAGMENT";

    private View mview;
    private ArrayList<Sale> mASales;
    private ArrayList<String> mArraySugestions;
    private ArrayList<Product> productsList;
    private HerokuGetProductsTask produtcsTask;
    private HerokuGetSalesTask salesTask;
    private SeachResultFragment mSearResultFragment;
    private RecyclerView categoryRecycleView;
    private CategoryCardAdapter mAdapter;
    private List<Market> mMarkets;
    private MarketCardAdapter mAdapterMarket;
    private TextView localization_tv;
    private ProductListAdapter mProductAdapter;
    private RecyclerView productRecycleView;
    private List<Market> marketsList;
    private HerokuGetMarketsTask marketTask;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MainFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        updateProductList();
        updateSaleList();
        mview = inflater.inflate(R.layout.fragment_main, container, false);

        localization_tv = (TextView) mview.findViewById(R.id.tv_localization);

        localization_tv.setText("Mercados em " + ((MainActivity) getActivity()).getCurrentAddress().getCity());
        productsList = new ArrayList<>();
        mASales = new ArrayList<>();
        mArraySugestions = new ArrayList<>();
        mSearResultFragment = SeachResultFragment.getInstance();
        mMarkets = new ArrayList<>();
        marketsList = new ArrayList<>();

        updateMarketList(((MainActivity) getActivity()).getCurrentAddress().getCity());

        Button change_location = (Button) mview.findViewById(R.id.change_location_btn);

        change_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeLocation();
            }
        });
        final String[] selectedText = {""};

        final AutoCompleteTextView searchView = (AutoCompleteTextView) mview.findViewById(R.id.search_bar);

        final ImageButton searchBtn = (ImageButton) mview.findViewById(R.id.search_btn);

        searchView.setHint("O que você busca, " + SharedPreferencesUtils.getUser(getContext()).getName().split(" ")[0] + "?");


        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                mArraySugestions = getSalesName(searchView.getText().toString());
                if(searchView.getText().toString().length()>0) {
                    searchBtn.setImageResource(R.drawable.ic_close);
                    searchBtn.setTag(new Boolean(true));
                }else{
                    searchBtn.setImageResource(R.drawable.ic_search);
                    searchBtn.setTag(new Boolean(false));
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_layout, mArraySugestions);
                searchView.setAdapter(adapter);
                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        //this is the way to find selected object/item
                        selectedText[0] = (String) adapterView.getItemAtPosition(pos);
                        ArrayList<Sale> salesSearch = new ArrayList<Sale>();
                        String str = selectedText[0].replace(" ", "%20");
                        Log.v("SELECTED " ,str);
                        salesSearch = getSalesByName(selectedText[0]);
                        hideKeyboard(getActivity());
                        ((MainActivity) getActivity()).setSearchStr(str);
                        ((MainActivity) getActivity()).changeFragment(mSearResultFragment,SeachResultFragment.TAG,true);


                    }
                });
                searchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            hideKeyboard(getActivity());
                            ((MainActivity) getActivity()).setSearchStr(searchView.getText().toString());
                            ((MainActivity) getActivity()).changeFragment(mSearResultFragment,SeachResultFragment.TAG,true);
                            return true;
                        }
                        return false;
                    }
                });


            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((searchBtn.getTag())!=null && ((Boolean)searchBtn.getTag())==true){
                    searchView.setText("");
                }

            }
        });


        final ImageButton barCodeScan = (ImageButton) mview.findViewById(R.id.scan_fab);
        final ImageButton addProduct = (ImageButton) mview.findViewById(R.id.write_post_btn);
        //final ImageButton addSupermarket = (ImageButton) mview.findViewById(R.id.add_supermarket_btn);
        final ImageButton favoritesBtn = (ImageButton) mview.findViewById(R.id.favorites_btn);
        final ImageButton profileBtn = (ImageButton) mview.findViewById(R.id.profile_btn);
        //final ImageButton grocery_btn = (ImageButton) mview.findViewById(R.id.grocery_btn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).callProfileFragment();
            }
        });
        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(FavoritListFragment.getInstance(),FavoritListFragment.TAG,true);
            }
        });
       /* addSupermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.not_ready, Toast.LENGTH_LONG).show();
            }
        });*/
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(ChooseMarketFragment.getInstance(),ChooseMarketFragment.TAG,true);
            }
        });

       /* grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Alimento");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });
*/

        barCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startScanCode();

            }
        });

        mAdapter = new CategoryCardAdapter(getActivity(), Arrays.asList("Alimento", "Cuidados pessoais", "Limpeza", "Eletrônico", "Mobília", "Outros"));

        categoryRecycleView = (RecyclerView) mview.findViewById(R.id.categories);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(llm);
        categoryRecycleView.setAdapter(mAdapter);





        //salesList = new ArrayList<>();







        /*ImageButton hygiene_btn = (ImageButton) mview.findViewById(R.id.hygiene_btn);
        hygiene_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Cuidados pessoais");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });

        ImageButton cleaning_btn = (ImageButton) mview.findViewById(R.id.cleaning_btn);
        cleaning_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Limpeza");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });

        ImageButton electronic_btn = (ImageButton) mview.findViewById(R.id.electronic_btn);
        electronic_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Eletrônico");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });

        ImageButton furniture_btn = (ImageButton) mview.findViewById(R.id.furniture_btn);
        furniture_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Mobília");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });

        ImageButton other_btn = (ImageButton) mview.findViewById(R.id.other_btn);
        other_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Outros");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });*/


        return mview;
    }



    private void updateSaleList() {
        salesTask = new HerokuGetSalesTask(String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
        salesTask.execute();
    }

    private ArrayList<String> getSalesName(String str) {
        ArrayList<String> array = new ArrayList<>();

        for (Product product: productsList
             ) {
            if(product.getName().toLowerCase().contains(str.toLowerCase()))
                if(!array.contains(product.getName().trim())) array.add(product.getName());
        }
        Log.v("SIZE", String.valueOf(array.size()));
        return array;
    }

    private void changeLocation() {
        View viewDialog = View.inflate(getActivity(), R.layout.localization_dialog, null);
        final AutoCompleteTextView ac_location = (AutoCompleteTextView) viewDialog.findViewById(R.id.ac_location);
        final ImageButton clear_btn_location = (ImageButton) viewDialog.findViewById(R.id.clear_btn_location);

        ac_location.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {


                if(ac_location.getText().toString().length()>0) {
                    clear_btn_location.setImageResource(R.drawable.ic_close);
                    clear_btn_location.setTag(new Boolean(true));
                }else{
                    clear_btn_location.setImageResource(R.drawable.ic_write);
                    clear_btn_location.setTag(new Boolean(false));
                }
            }
        });

        clear_btn_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clear_btn_location.getTag())!=null && ((Boolean)clear_btn_location.getTag())==true){
                    ac_location.setText("");
                }

            }
        });

        new AlertDialog.Builder(getContext())
                .setTitle("Mudar Localização")
                .setView(viewDialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        if(!ac_location.getText().toString().equals("")){
                            ((MainActivity) getActivity()).getCurrentAddress().setCity(ac_location.getText().toString());
                            localization_tv.setText("Mercados em " +((MainActivity) getActivity()).getCurrentAddress().getCity());
                            updateMarketList(((MainActivity) getActivity()).getCurrentAddress().getCity());
                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_map_pin_marked)
                .show();

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

    public void updateProductList(){
        produtcsTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)), this);
        produtcsTask.execute();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {
        if(!isAdded()) return;
        mASales = sales;
        Log.v(TAG, "Quantidade de sales: " + String.valueOf(mASales.size()));

        mArraySugestions = new ArrayList<>();

    }

    @Override
    public void OnGetSalesByMarketReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnPostSaleFinished(boolean finished) {

    }

    @Override
    public void OnPutSaleFinished(boolean finished) {

    }

    @Override
    public void OnPostLikeFinished(boolean finished) {

    }

    @Override
    public void OnDeleteLikeFinished(boolean finished) {

    }

    @Override
    public void OnPostDislikeFinished(boolean finished) {

    }

    @Override
    public void OnDeleteDislikeFinished(boolean finished) {

    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        if(!isAdded()) return;
        productsList = products;
        Log.v(TAG, "Quantidade de produtos: " + String.valueOf(productsList.size()));
    }

    @Override
    public void OnGetProductsByCategoryReady(boolean ready, ArrayList<Product> products) {

    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {


    }

    @Override
    public void OnGetProductByBarcodeReady(boolean ready, Product product) {

    }

    public ArrayList<Sale> getSalesByName(String s) {
        ArrayList<Sale> array = new ArrayList<>();
        for (Product product: productsList
                ) {
            for (Sale sale: mASales
                    ) {
                if(product.getId().equals(sale.getProductId()) && product.getName().equals(s)) array.add(sale);
            }

        }
        Log.v("SIZE", String.valueOf(array.size()));
        return array;
    }

    private void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }



    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {
        if(!isAdded()) return;
        marketsList = markets;
        productRecycleView = (RecyclerView) mview.findViewById(R.id.recent_sales);

        List<Sale> arraySale = (mASales.size() > 10)?  mASales.subList(0,10): mASales;



        mProductAdapter = new ProductListAdapter(getActivity(), arraySale, marketsList,productsList, getContext());
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        productRecycleView.setLayoutManager(llm2);
        productRecycleView.setAdapter(mProductAdapter);
    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketsBySearchReady(boolean ready, ArrayList<Market> markets) {
        if(!isAdded()) return;
        mMarkets = markets;
        mAdapterMarket = new MarketCardAdapter(getActivity(), mMarkets);
        RecyclerView marketRecycleView = (RecyclerView) mview.findViewById(R.id.markets);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        marketRecycleView.setLayoutManager(llm);
        marketRecycleView.setAdapter(mAdapterMarket);

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {

    }

    private void updateMarketList(String city){
        marketTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) , this);
        marketTask.execute();
        city = city.replace("ã", "a").replace("ç","c").replace("õ","o").toLowerCase();
        HerokuGetMarketsBySearchTask mtask = new HerokuGetMarketsBySearchTask(null, null,city.trim(), null, String.format(getResources().getString(R.string.HEROKU_MARKET_BY_SEARCH_ENDPOINT)), this);
        mtask.execute();

    }
}
