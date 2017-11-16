package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.adapters.CategoryAdapter;
import com.projeto1.projeto1.adapters.SubCategoryListAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductByBarcodeTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPostProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPostSalesTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Historic;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;
import com.shawnlin.numberpicker.NumberPicker;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class AddProductFragment extends Fragment  implements SaleListener, ProductListener, MarketListener {


    public static final String TAG = "ADD_PRODUCT_FRAGMENT";

    private View mview;
    private String quantity;
    private List<Product> productsList;
    private EditText productPriceET;
    private HerokuGetProductsTask produtcsTask;
    private HerokuGetProductByBarcodeTask productByCodeTask;
    private List<String> categoryList;
    Map<String,String> mMarketSugestions = new HashMap<String,String>();
    private String codeScan;
    private boolean barCodeReady;
    private Product product;
    private String date;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AddProductFragment() {
    }

    // TODO: Customize parameter initialization
    public static AddProductFragment getInstance() {
        AddProductFragment fragment = new AddProductFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mview = inflater.inflate(R.layout.fragment_add_product, container, false);

        productsList = new ArrayList<>();
        //mMarketSugestions = new ArrayList<>(Arrays.asList("Hiper Extra","Ideal Centro","Redecompras Centro","Supermercados Ideal"));
        updateProductList();
        updateMarketList();



        date = null;
        categoryList = new ArrayList<>(Arrays.asList( "Alimento", "Cuidados pessoais", "Limpeza",
                "Eletrônico", "Mobília", "Outros"));

        priceClick(mview);
        addSale(mview, inflater, container);

        ImageButton scanBtn = (ImageButton) mview.findViewById(R.id.scan_btn);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).startScanCode();
            }
        });

        return mview;
    }

    private void addSale(final View mview, final LayoutInflater inflater, final ViewGroup container) {
        final EditText productNameET = (EditText) mview.findViewById(R.id.product_name_input);
        final EditText productCodeET = (EditText) mview.findViewById(R.id.product_code_input);
        final EditText productIdET = (EditText) mview.findViewById(R.id.product_id_input);


        codeScan = ((MainActivity) getActivity()).getScanContent();

        if (codeScan.length()>0) {
            productCodeET.setText(codeScan);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                public void run() {
                    openDialog(inflater, container, productNameET, productCodeET, productIdET);
                }
            }, 500);
            ((MainActivity) getActivity()).setScanContent("");
        }

//        productCodeET.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (productCodeET.getText().toString().toString().length()==12) {
//                    openDialog(inflater, container, productNameET, productCodeET, productIdET);
//                }
//
//
//            }
//        });

        productCodeET.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                        openDialog(inflater, container, productNameET, productCodeET, productIdET);
//                    Log.v("TECLADO", "done");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });
       final AutoCompleteTextView productMarketET = (AutoCompleteTextView) mview.findViewById(R.id.market_input);
       /* ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_layout, getKeys(mMarketSugestions));
        productMarketET.setAdapter(adapter);

        final String[] selectedMarket = {""};*/

        productMarketET.setText(((MainActivity) getActivity()).getChosenMarket().getName());
        productMarketET.setEnabled(false);

      /*  productMarketET.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) productMarketET.showDropDown();
            }
        });

        productMarketET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                productMarketET.showDropDown();

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_layout, getKeys(mMarketSugestions));
                productMarketET.setAdapter(adapter);
                productMarketET.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                        selectedMarket[0] = (String) adapterView.getItemAtPosition(pos);
                        Log.v("MARKET NAME", selectedMarket[0]);
                        Log.v("MARKET ID", getMarketIdByName(selectedMarket[0]));


                    }
                });


            }
        });
*/

        // final EditText productQuantityET = (EditText) mview.findViewById(R.id.quantity_input);
        final Button expireDateBtn = (Button)  mview.findViewById(R.id.expire_date);

        productPriceET.setRawInputType(Configuration.KEYBOARD_12KEY);

//        quantityBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                quantityDialog(quantityBtn);
//            }
//        });

        expireDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(expireDateBtn);
            }
        });


        Button sendBtn = (Button) mview.findViewById(R.id.send_btn);


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productName = productNameET.getText().toString();
                String productCode = productCodeET.getText().toString();
                String productMarket = productMarketET.getText().toString();
                String price = productPriceET.getText().toString().substring(1);
                String productId = productIdET.getText().toString();

                  updateProductList();

                if(productId.equals("")){
                    for (Product p: productsList
                         ) {
                        if(p.getBarcode().equals(productCode)){
                            productId = p.getId();
                        }
                    }

                }

/*
            Para criar, tem que salvar o id de Market, User e o código de barras do produto.
            Tem que fazer um get pra pegar esses ids, da mesma maneira que foi feito em Product.
*/
                TimeZone tz = TimeZone.getTimeZone("UTC");
                SimpleDateFormat f = new SimpleDateFormat("DD-MMM-yyyy");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                df.setTimeZone(tz);
                String expirationDate = null;
                String marketId = ((MainActivity) getActivity()).getChosenMarket().getId();

                if (marketId.equals("-1")){
                    marketId = "5962f20b4a0cd90004064df6";
                }

                try {
                    expirationDate = df.format(new Date(f.parse("12-July-2018").getTime()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }


                String newExpDate = date == ""? expirationDate: date + expirationDate.substring(10, expirationDate.length());

                if(date.length() == 9){
                    newExpDate = date.substring(0, 8) + "0" + date.substring(8) + expirationDate.substring(10, expirationDate.length());
                }
                Sale sale;

                //TODO criar objeto e salvar no banco.
                if(date == null){
                    sale = new Sale(productId, marketId, Double.parseDouble(price), 2.0, expirationDate, SharedPreferencesUtils.getUser(getContext()).getId(), new ArrayList<Historic>());

                }
                else {
                    sale = new Sale(productId, marketId, Double.parseDouble(price), 2.0, newExpDate, SharedPreferencesUtils.getUser(getContext()).getId(), new ArrayList<Historic>());
                }
                //Sale sale = new Sale(productId, productMarket, productPrice, 2.0, expirationDate, SharedPreferencesUtils.getUser(getContext()).getId(), 1, "Uni");
                post(sale);
            }
        });

    }

    private ArrayList<String> getKeys(Map<String, String> mMarketSugestions) {
        ArrayList<String> markets = new ArrayList<>();
        markets.addAll(mMarketSugestions.keySet());
        return  markets;
    }

    private String getMarketIdByName(String s) {
        if (mMarketSugestions.containsKey(s)){
            return mMarketSugestions.get(s);

        } else {
             return "-1";
        }
    }

    private void openDialog(LayoutInflater inflater, ViewGroup container, final EditText productNameET, EditText productCodeET, final EditText productIdET) {


        final View mvDialog = inflater.inflate((R.layout.create_product_dialog),container,false);

        final EditText codeDialog = (EditText) mvDialog.findViewById(R.id.product_code_input_dialog);
        final EditText nameDialog = (EditText) mvDialog.findViewById(R.id.product_name_input_dialog);
        final EditText brandDialog = (EditText) mvDialog.findViewById(R.id.product_brand_input_dialog);
        final EditText categoryDialog = (EditText) mvDialog.findViewById(R.id.product_category_input_dialog);
        final EditText subCategoryDialog = (EditText) mvDialog.findViewById(R.id.product_subcategory_input_dialog);
        final EditText productId = (EditText) mvDialog.findViewById(R.id.product_id);


        //Dados de quantidade
        final NumberPicker picker = (NumberPicker) mvDialog.findViewById(R.id.number_picker);
        final EditText productQuantityET = (EditText) mvDialog.findViewById(R.id.quantity_input);
        picker.setDisplayedValues(null);
        picker.setMinValue(1);
        picker.setMaxValue(5);
        picker.setDisplayedValues( new String[] { "Uni", "Kg", "g","ml","L"} );

        final TextView tvSub = (TextView) mvDialog.findViewById(R.id.tv);


        final RecyclerView categoryRecycleView = (RecyclerView) mvDialog.findViewById(R.id.category_list);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        categoryRecycleView.setLayoutManager(llm);
        final CategoryAdapter categoryAdapter = new CategoryAdapter(categoryList,tvSub);
        categoryRecycleView.setAdapter(categoryAdapter);


        final RecyclerView subCategoryRecycleView = (RecyclerView) mvDialog.findViewById(R.id.sub_category_list);
        final LinearLayoutManager llm1 = new LinearLayoutManager(getActivity());
        llm1.setOrientation(LinearLayoutManager.HORIZONTAL);
        tvSub.setText(categoryAdapter.getCbSelected());
        final SubCategoryListAdapter[] mListAdapter = new SubCategoryListAdapter[1];
        tvSub.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mListAdapter[0] = new SubCategoryListAdapter(tvSub.getText().toString());
                subCategoryRecycleView.setLayoutManager(llm1);
                subCategoryRecycleView.setAdapter(mListAdapter[0]);

            }
        });


        LinearLayout llCategory = (LinearLayout) mvDialog.findViewById(R.id.ll_category);
        LinearLayout llCategoryIpunt = (LinearLayout) mvDialog.findViewById(R.id.category_ll);
        LinearLayout llSubCategoryInput = (LinearLayout) mvDialog.findViewById(R.id.subcategory_ll);
        LinearLayout llQuantityInput = (LinearLayout) mvDialog.findViewById(R.id.quantity_ll);

        codeDialog.setText(productCodeET.getText().toString());
        codeDialog.setEnabled(false);
        boolean productExists = false;

        productByCodeTask = new HerokuGetProductByBarcodeTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT_BY_BARCODE)) + productCodeET.getText().toString(), this);
        productByCodeTask.execute();

        String productName = "";
        Log.v("size", String.valueOf(productsList.size()));
        for(int i = 0; i < productsList.size(); i++){
            String cod = productCodeET.getText().toString();
            Log.v("PRODUCT", productsList.get(i).toString());
            if(cod.equals(productsList.get(i).getBarcode())){
                productExists = true;
                llCategory.setVisibility(LinearLayout.GONE);
                llQuantityInput.setVisibility(LinearLayout.GONE);
                Product product = productsList.get(i);
                productName = product.getName();
                nameDialog.setText(productName + " " + Integer.valueOf((int) product.getSize()) + " " + product.getSizeUnity());
                nameDialog.setEnabled(false);
                brandDialog.setText(product.getBrand());
                brandDialog.setEnabled(false);
                categoryAdapter.setClicled(product.getCategory());
                categoryDialog.setText(product.getCategory());
                categoryDialog.setEnabled(false);
                subCategoryDialog.setText(product.getSubcategory());
                subCategoryDialog.setEnabled(false);
                productId.setText(product.getId());
                break;
            }

        }



        //keyboard down
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(productCodeET.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);

        String positiveAction = "";
        String negativeAction = "";
        String productAction = "";

        if (productExists) {
            positiveAction = "Ok";
            negativeAction = "";
            productAction = "Informações do Produto";
            llCategoryIpunt.setVisibility(LinearLayout.VISIBLE);
            llSubCategoryInput.setVisibility(LinearLayout.VISIBLE);
            llQuantityInput.setVisibility(LinearLayout.GONE);
        }else{
            positiveAction = "Salvar";
            negativeAction = "Cancel";
            productAction =  "Cadastre o produto";
            llCategoryIpunt.setVisibility(LinearLayout.GONE);
            llSubCategoryInput.setVisibility(LinearLayout.GONE);
            llQuantityInput.setVisibility(LinearLayout.VISIBLE);

        }

        final boolean productExistsFinal = productExists;
        if ((productCodeET.getText().toString().length() > 0)){
            new AlertDialog.Builder(getContext())
                    .setTitle(productAction)
                    .setView(mvDialog)
                    .setPositiveButton(positiveAction, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            productNameET.setText(nameDialog.getText().toString());
                            productIdET.setText(productId.getText().toString());
                            productNameET.setEnabled(false);
                            Double unity = Double.valueOf(productQuantityET.getText().toString());
                            String selectedCategory = categoryAdapter.getCbSelected();
                            String selectedSubCategory = mListAdapter[0]==(null)? "": mListAdapter[0].getCbSelected();
                            if ((nameDialog.getText().toString().equals("") ||
                                    brandDialog.getText().toString().equals("") ||
                                    codeDialog.getText().toString().equals("") ||
                                    selectedCategory.equals("")||
                                    (!selectedCategory.equals("Outros") &&
                                    selectedSubCategory.equals(""))) && !productExistsFinal){
                                Toast.makeText(getContext(), "Todos os campos devem ser preenchidos", Toast.LENGTH_LONG).show();
                                productNameET.setText("");
                            } else {
                                if(!productExistsFinal) {
                                    Product product;
                                    if (selectedCategory.equals("Outros")){
                                        product = new Product(nameDialog.getText().toString(), brandDialog.getText().toString(), " ", " ", codeDialog.getText().toString(), selectedCategory," ", unity, picker.getDisplayedValues()[picker.getValue()-1]);

                                    }
                                    else {
                                        product = new Product(nameDialog.getText().toString(), brandDialog.getText().toString(), " ", " ", codeDialog.getText().toString(), selectedCategory, selectedSubCategory, unity, picker.getDisplayedValues()[picker.getValue() - 1]);
                                    }postProduct(product);
                                    updateProductList();
                                }
                            }

                        }
                    })
                    .setNegativeButton(negativeAction, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            productNameET.setText("");
                        }
                    })
                    .setIcon(R.drawable.ic_add)
                    .show();

        }else {
            Toast.makeText(getContext(), "O código de barras deve conter 12 dígitos", Toast.LENGTH_LONG).show();
            productNameET.setText("");
        }
    }


    private void dateDialog(final Button expireDateBtn) {
        final View viewDialog = View.inflate(getActivity(), R.layout.date_piker_dialog, null);

        final NumberPicker pickerDay = (NumberPicker) viewDialog.findViewById(R.id.number_picker_day);
        final NumberPicker pickerMonth = (NumberPicker) viewDialog.findViewById(R.id.number_picker_month);
        final NumberPicker pickerYear = (NumberPicker) viewDialog.findViewById(R.id.number_picker_year);

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());

        pickerDay.setDisplayedValues(null);
        pickerDay.setValue(calendar.get(Calendar.DATE)+1);
        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);

        pickerMonth.setDisplayedValues(null);
        pickerMonth.setValue(calendar.get(Calendar.MONTH) + 2);
        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerMonth.setDisplayedValues( new String[] {"Jan", "Fev", "Mar","Abr","Mai","Jun","Jul", "Ago", "Set","Out","Nov","Dez"} );

        pickerYear.setDisplayedValues(null);
        pickerYear.setMinValue(calendar.get(Calendar.YEAR));
        pickerYear.setMaxValue(calendar.get(Calendar.YEAR)+10);

        new AlertDialog.Builder(getContext())
                .setTitle("Data de Validade")
                .setView(viewDialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String month = pickerMonth.getValue() < 10? ("0" + pickerMonth.getValue()): pickerMonth.getValue()+"";
                        date = pickerYear.getValue() +"-"
                                +month +"-"
                                +pickerDay.getValue();
                        expireDateBtn.setText(pickerDay.getValue()+"-"+month+"-"+pickerYear.getValue());
                    }
                })
                .setIcon(R.drawable.ic_calendar)
                .show();
    }

    public void priceClick(View view) {
        productPriceET = (EditText) mview.findViewById(R.id.price_input);
        productPriceET.addTextChangedListener(new TextWatcher() {
            DecimalFormat dec = new DecimalFormat("0.00");

            @Override
            public void afterTextChanged(Editable arg0) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            private String current = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)){
                        productPriceET.removeTextChangedListener(this);

                    String cleanString = s.toString().replaceAll("[R$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed/100));

                    current = "R" + formatted;
                    productPriceET.setText(formatted);
                    productPriceET.setSelection(formatted.length());

                    productPriceET.addTextChangedListener(this);
                }
            }
        });
    }


    public void updateProductList(){
        produtcsTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)), this);
        produtcsTask.execute();
    }

    private void updateMarketList(){
        HerokuGetMarketsTask herokuGetMarketsTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)), this);
        herokuGetMarketsTask.execute();
    }


    private void post(Sale sale){
        HerokuPostSalesTask herokuPostSalesTask = new HerokuPostSalesTask(sale, getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)),this);
        herokuPostSalesTask.execute();
    }

    private void postProduct(Product product){
        User user = SharedPreferencesUtils.getUser(getContext());
        HerokuPostProductsTask herokuPostProductsTask = new HerokuPostProductsTask(product, getContext(), String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)),this, user);
        herokuPostProductsTask.execute();
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnGetSalesByMarketReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnPostSaleFinished(boolean finished) {
        ((MainActivity) getActivity()).changeFragment(MainFragment.getInstance(), MainFragment.TAG, true);
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
        productsList = products;
        Log.v(TAG, "Quantidade de products: " + String.valueOf(productsList.size()));
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
        this.product = product;
//        if (ready) barCodeReady = true;
//        else barCodeReady = false;

    }

    public void setSubCategory(View mvDialog, String cbSelected) {

    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {

        for (Market name : markets) {
            mMarketSugestions.put( name.getName(), name.getId());

        }

    }
    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketsBySearchReady(boolean ready, ArrayList<Market> markets) {

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {

    }

}