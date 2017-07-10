package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.ProductListener;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SaleListener;
import com.projeto1.projeto1.adapters.CategoryListAdapter;
import com.projeto1.projeto1.adapters.SubCategoryListAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPostProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPostSalesTask;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.shawnlin.numberpicker.NumberPicker;
import com.xiaofeng.flowlayoutmanager.FlowLayoutManager;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AddProductFragment extends Fragment  implements SaleListener, ProductListener{


    public static final String TAG = "ADD_PRODUCT_FRAGMENT";

    private View mview;
    private CategoryListAdapter mAdapter;
    private SubCategoryListAdapter mListAdapter;
    private List<String> subCategoryList;
    private RecyclerView subCategoryRecycleView;
    private ArrayList<String> groceryCategoryList;
    private ArrayList<String> hygieneCategoryList;
    private ArrayList<String> otherCategoryList;
    private GroceryProductsFragment groceryProductsFragment;
    private String quantity;
    private List<Product> productsList;
    private EditText productPriceET;
    private HerokuGetProductsTask produtcsTask;



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
        produtcsTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)), this);
        produtcsTask.execute();

        final CheckBox cb_grocery = (CheckBox) mview.findViewById(R.id.checkbox_grocery);
        final CheckBox cb_hygiene = (CheckBox) mview.findViewById(R.id.checkbox_hygiene);
        final CheckBox cb_other = (CheckBox) mview.findViewById(R.id.checkbox_other);

        subCategoryRecycleView = (RecyclerView) mview.findViewById(R.id.sub_category_list);
        subCategoryRecycleView.setLayoutManager(new FlowLayoutManager());
        groceryCategoryList = new ArrayList<>(Arrays.asList( "Grãos", "Bebidas", "Laticínio",
                "Carnes", "Oleos", "Frutas e Verduras"));
        hygieneCategoryList = new ArrayList<>(Arrays.asList( "Sabonetes", "Detergentes", "Limpeza",
                "Hidratantes"));
        otherCategoryList = new ArrayList<>(Arrays.asList( "Outros"));

        cb_grocery.setChecked(true);
        mListAdapter = new SubCategoryListAdapter(groceryCategoryList);
        subCategoryRecycleView.setAdapter(mListAdapter);

        cb_grocery.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_grocery.isChecked()) {
                    cb_other.setChecked(false);
                    cb_hygiene.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(groceryCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });

        cb_hygiene.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_hygiene.isChecked()) {
                    cb_other.setChecked(false);
                    cb_grocery.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(hygieneCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });

        cb_other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb_other.isChecked()) {
                    cb_hygiene.setChecked(false);
                    cb_grocery.setChecked(false);
                    mListAdapter = new SubCategoryListAdapter(otherCategoryList);
                    subCategoryRecycleView.setAdapter(mListAdapter);
                }
            }
        });


        final EditText productNameET = (EditText) mview.findViewById(R.id.product_name_input);
        final EditText productCodeET = (EditText) mview.findViewById(R.id.product_code_input);


        productCodeET.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                    Log.v("TECLADO", "done");

                    for(int i = 0; i < productsList.size(); i++){
                        String cod = productCodeET.getText().toString();
                        if(cod.equals(productsList.get(i).getBarcode())){
                            productNameET.setText(productsList.get(i).getName());
                            productNameET.setEnabled(false);
                            break;
                        }
                        productNameET.setEnabled(true);

                    }
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }
        });
        final EditText productMarketET = (EditText) mview.findViewById(R.id.brand_input);
        productPriceET = (EditText) mview.findViewById(R.id.price_input);
       // final EditText productQuantityET = (EditText) mview.findViewById(R.id.quantity_input);
        final Button quantityBtn = (Button)  mview.findViewById(R.id.quantity_input);
        final Button expireDateBtn = (Button)  mview.findViewById(R.id.expire_date);

        productPriceET.setRawInputType(Configuration.KEYBOARD_12KEY);

        priceClick(mview);
        quantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityDialog(quantityBtn);
            }
        });

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
                String productPrice = productPriceET.getText().toString();

                //TODO criar objeto e salvar no banco.
               Sale sale = new Sale(productCode, productName,10.0, Double.parseDouble(productPrice.substring(2)), new Date(2017, 3,2), productMarket, Integer.parseInt(quantity),0,"pessoa", null,0,0, "");
                post(sale);

            }
        });
        return mview;
    }

    private void dateDialog(Button expireDateBtn) {
        final View viewDialog = View.inflate(getActivity(), R.layout.date_piker_dialog, null);

        final NumberPicker pickerDay = (NumberPicker) viewDialog.findViewById(R.id.number_picker_day);
        final NumberPicker pickerMonth = (NumberPicker) viewDialog.findViewById(R.id.number_picker_month);
        final NumberPicker pickerYear = (NumberPicker) viewDialog.findViewById(R.id.number_picker_year);

        pickerDay.setDisplayedValues(null);
        pickerDay.setValue(new Date().getDay()+2);
        pickerDay.setMinValue(1);
        pickerDay.setMaxValue(31);

        pickerMonth.setDisplayedValues(null);
        pickerMonth.setValue(new Date().getMonth()+1);
        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerMonth.setDisplayedValues( new String[] {"Jan", "Fev", "Mar","Abr","Mai","Jun","Jul", "Ago", "Set","Out","Nov","Dez"} );

        pickerYear.setDisplayedValues(null);
        pickerYear.setMinValue(2017);
        pickerYear.setMaxValue(2030);

        new AlertDialog.Builder(getContext())
                .setTitle("Data de Validade")
                .setView(viewDialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setIcon(R.drawable.ic_calendar)
                .show();
    }

    public void priceClick(View view) {
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
                    productPriceET.setText("R" + formatted);
                    productPriceET.setSelection(formatted.length()+1);

                    productPriceET.addTextChangedListener(this);
                }
            }
        });
    }



    private void quantityDialog(final Button quantityBtn) {
        final View viewDialog = View.inflate(getActivity(), R.layout.quantity_picker_dialog, null);

        final NumberPicker picker = (NumberPicker) viewDialog.findViewById(R.id.number_picker);
         final EditText productQuantityET = (EditText) viewDialog.findViewById(R.id.quantity_input);

        picker.setDisplayedValues(null);
        picker.setMinValue(1);
        picker.setMaxValue(5);
        picker.setDisplayedValues( new String[] { "Uni", "Kg", "g","ml","L"} );


        new AlertDialog.Builder(getContext())
                .setTitle("Quantidade e medida")
                .setView(viewDialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        quantity = productQuantityET.getText().toString();
                        quantityBtn.setText(quantity + " " +picker.getDisplayedValues()[picker.getValue()-1]);

                    }
                })
                .setIcon(R.drawable.ic_food_scale_tool)
                .show();
    }

    private void post(Sale sale){
        HerokuPostSalesTask herokuPostSalesTask = new HerokuPostSalesTask(sale, getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)),this);
        herokuPostSalesTask.execute();
    }

    private void postProduct(Product product){
        HerokuPostProductsTask herokuPostProductsTask = new HerokuPostProductsTask(product, getContext(), String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)),this);
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
    public void OnPostSaleFinished(boolean finished) {
        ((MainActivity) getActivity()).changeFragment(OldMainFragment.getInstance(), OldMainFragment.TAG, true);
    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        productsList = products;
        Log.v(TAG, "Quantidade de products: " + String.valueOf(productsList.size()));
    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }
}