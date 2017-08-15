package com.projeto1.projeto1.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;


public class MainFragment extends Fragment implements SaleListener,ProductListener {


    public static final String TAG = "MAIN_FRAGMENT";

    private View mview;
    private ArrayList<Sale> mASales;
    private ArrayList<String> mArraySugestions;
    private ArrayList<Product> productsList;
    private HerokuGetProductsTask produtcsTask;
    private HerokuGetSalesTask salesTask;
    private SeachResultFragment mSearResultFragment;


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


        productsList = new ArrayList<>();
        mASales = new ArrayList<>();
        mArraySugestions = new ArrayList<>();
        mSearResultFragment = SeachResultFragment.getInstance();

        final String[] selectedText = {""};

        final AutoCompleteTextView searchView = (AutoCompleteTextView) mview.findViewById(R.id.search_bar);

        final ImageButton searchBtn = (ImageButton) mview.findViewById(R.id.search_btn);




        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                mArraySugestions = getSalesName();
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
                        Log.v("SELECTED " ,selectedText[0]);
                        salesSearch = getSalesByName(selectedText[0]);
                        hideKeyboard(getActivity());
                        ((MainActivity) getActivity()).setSalesSearch(salesSearch);
                        ((MainActivity) getActivity()).changeFragment(mSearResultFragment,SeachResultFragment.TAG,true);


                    }
                });

            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((Boolean)searchBtn.getTag())==true){
                    searchView.setText("");
                }

            }
        });


        final ImageButton barCodeScan = (ImageButton) mview.findViewById(R.id.scan_fab);
        final ImageButton addProduct = (ImageButton) mview.findViewById(R.id.write_post_btn);
        final ImageButton addSupermarket = (ImageButton) mview.findViewById(R.id.add_supermarket_btn);
        final ImageButton favoritesBtn = (ImageButton) mview.findViewById(R.id.favorites_btn);
        final ImageButton profileBtn = (ImageButton) mview.findViewById(R.id.profile_btn);
        final ImageButton grocery_btn = (ImageButton) mview.findViewById(R.id.grocery_btn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).callProfileFragment();
            }
        });
        favoritesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.not_ready, Toast.LENGTH_LONG).show();

            }
        });
        addSupermarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), R.string.not_ready, Toast.LENGTH_LONG).show();
            }
        });
        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(AddProductFragment.getInstance(),AddProductFragment.TAG,true);
            }
        });

        grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).setCurrentCategory("Alimento");
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });


        barCodeScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startScanCode();

            }
        });

        ImageButton hygiene_btn = (ImageButton) mview.findViewById(R.id.hygiene_btn);
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
        });


        return mview;
    }



    private void updateSaleList() {
        salesTask = new HerokuGetSalesTask(String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
        salesTask.execute();
    }

    private ArrayList<String> getSalesName() {
        ArrayList<String> array = new ArrayList<>();

        for (Product product: productsList
             ) {
            for (Sale sale: mASales
                 ) {
                if(product.getId().equals(sale.getProductId()) && !array.contains(product.getName())) array.add(product.getName());
            }

        }
        Log.v("SIZE", String.valueOf(array.size()));
        return array;
    }

    private void startAdapter() {

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
        mASales = sales;
        Log.v(TAG, "Quantidade de sales: " + String.valueOf(mASales.size()));
        mArraySugestions = new ArrayList<>();
    }

    @Override
    public void OnPostSaleFinished(boolean finished) {

    }

    @Override
    public void OnPutSaleFinished(boolean finished) {

    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
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


}
