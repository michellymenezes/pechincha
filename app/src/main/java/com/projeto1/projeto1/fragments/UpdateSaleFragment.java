package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.endpoints.HerokuGetProductTask;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.endpoints.HerokuPostSalesTask;
import com.projeto1.projeto1.endpoints.HerokuPutSaleTask;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Historic;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
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


/**
 * Created by Rafaelle on 16/08/2017.
 */

public class UpdateSaleFragment extends Fragment implements SaleListener, ProductListener{


    public static final String TAG = "UPDATE_SALE_FRAGMENT";

    private View mview;
    private String quantity;
    private EditText productPriceET;
    private Sale sale;
    private HerokuGetProductTask productTask;
    private Product product;
    private Market market;
    private Button quantityBtn;
    String date;
    private Button expireDateBtn;
    private EditText productQuantityET;
    private boolean priceClicked;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public UpdateSaleFragment() {
    }

    // TODO: Customize parameter initialization
    public static UpdateSaleFragment getInstance() {
        UpdateSaleFragment fragment = new UpdateSaleFragment();
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

        mview = inflater.inflate(R.layout.fragment_update_sale, container, false);
        sale = SharedPreferencesUtils.getSelectedSale(getContext());
        product = SharedPreferencesUtils.getProductFromSale(getContext());
        market = SharedPreferencesUtils.getMarketFromSale(getContext());
        date = "";
        priceClicked = false;

        // not editable
        final TextView productNameET = (TextView) mview.findViewById(R.id.product_name_input);
        final EditText productCodeET = (EditText) mview.findViewById(R.id.product_code_input);
        final EditText productMarketET = (EditText) mview.findViewById(R.id.market_input);
        final EditText productCurrPricetET = (EditText) mview.findViewById(R.id.current_price_input);


        productCodeET.setText(product.getBarcode());
        productCodeET.setEnabled(false);
        productNameET.setText(product.getName() + " " + ((int) product.getSize()) + product.getSizeUnity());
        productNameET.setEnabled(false);
        productMarketET.setText(market.getName());
        productMarketET.setEnabled(false);

        //Editable
        //quantityBtn = (Button) mview.findViewById(R.id.quantity_input);
        expireDateBtn = (Button) mview.findViewById(R.id.expire_date);
        productPriceET = (EditText) mview.findViewById(R.id.price_input);
        //quantityBtn.setText(String.valueOf(((int) product.getSize()) + product.getSizeUnity()));

        String [] date =  sale.getExpirationDate().substring(0,10).split("-");
        String expireDate = date[2] +"-"+ date[1] +"-"+ date[0];
        expireDateBtn.setText(expireDate);
        productCurrPricetET.setText("R$ "+String.valueOf(sale.getSalePrice()));
        productCurrPricetET.setEnabled(false);
        productPriceET.setText("R$0.00");
        productPriceET.setEnabled(true);

//        productQuantityET.setText(sale.getQuantity());

        //quantityBtn.setText(quantity + " " + picker.getDisplayedValues()[picker.getValue() - 1]);


        priceClick(mview);
        editSale(mview);
        return mview;

    }

    private void editSale(final View mview) {

        productPriceET.setRawInputType(Configuration.KEYBOARD_12KEY);

       /* quantityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityDialog(quantityBtn);
            }
        });
*/
        expireDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateDialog(expireDateBtn);
            }
        });

        Button cancelBtn = (Button) mview.findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity)getActivity()).changeFragment(SaleDetailsFragment.getInstance(), SaleDetailsFragment.TAG,true);
            }
        });

        Button updateBtn = (Button) mview.findViewById(R.id.update_btn);

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String price = priceClicked? productPriceET.getText().toString().substring(1): productPriceET.getText().toString().substring(2);
                Log.v("PRICEE", price);
                Log.v("PRICEEs", sale.getSalePrice()+"");

                String newExpDate = date == ""? sale.getExpirationDate(): date + sale.getExpirationDate().substring(10, sale.getExpirationDate().length());


                TimeZone tz = TimeZone.getTimeZone("UTC");
                SimpleDateFormat f = new SimpleDateFormat("dd-MMM-yyyy");
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'"); // Quoted "Z" to indicate UTC, no timezone offset
                df.setTimeZone(tz);
                Date expirationDate = null;
                Date date = null;

/*                try {
                    //expirationDate=newExpDate;
                    expirationDate = (new Date(f.parse(sale.getExpirationDate()).getTime()));
                    Log.d(TAG, String.valueOf(expirationDate));
                    date = df.parse(sale.getExpirationDate());

                } catch (ParseException e) {
                    e.printStackTrace();
                }*/


                //Dando erro de formatação em price - valor adaptado
              //  String price_adaptado = price.substring(1);
              //  price_adaptado = price_adaptado.replaceAll(",", ".");

                //Sale update = new Sale(product.getId(), market.getId(), Double.parseDouble(price_adaptado), sale.getSalePrice(), expirationDate, SharedPreferencesUtils.getUser(getContext()).getId(), 1, "Uni", new ArrayList<Historic>());
                //sale.addHistoric(new Historic(expirationDate,sale.getSalePrice(), sale.getLikeCount(), sale.getDislikeCount(), sale.getReportCount(), sale.getLikeUsers(), sale.getDislikeUsers()));
                sale.setExpirationDate(newExpDate);
                //sale.setQuantUni();

                if(Double.parseDouble(price)==0) {
                    Toast toast = Toast.makeText(getContext(), "O preço deve ser e maior que zero!", Toast.LENGTH_SHORT);
                    toast.show();
                }else {
                    sale.setSalePrice(Double.parseDouble(price));
                    updateSale(sale);
                }

                //EXEMPLO DE EDIÇÃO DE PROMOÇÃO - PUT
            /*
            Sale newSale = sale;
            newSale.setSalePrice(1.0);

            salePutTask = new HerokuPutSaleTask(newSale, getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)) + "/" + sale.getId(), this);
            salePutTask.execute();*/
            }
        });

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
        pickerMonth.setValue(calendar.get(Calendar.MONTH) + 1);
        pickerMonth.setMinValue(1);
        pickerMonth.setMaxValue(12);
        pickerMonth.setDisplayedValues(new String[]{"Jan", "Fev", "Mar", "Abr", "Mai", "Jun", "Jul", "Ago", "Set", "Out", "Nov", "Dez"});

        pickerYear.setDisplayedValues(null);
        pickerYear.setMinValue(calendar.get(Calendar.YEAR));
        pickerYear.setMaxValue(calendar.get(Calendar.YEAR) + 10);

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

    private void quantityDialog(final Button quantityBtn) {
        final View viewDialog = View.inflate(getActivity(), R.layout.quantity_picker_dialog, null);

        final NumberPicker picker = (NumberPicker) viewDialog.findViewById(R.id.number_picker);
        productQuantityET = (EditText) viewDialog.findViewById(R.id.quantity_input);

        picker.setDisplayedValues(null);
        picker.setMinValue(1);
        picker.setMaxValue(5);
        picker.setDisplayedValues(new String[]{"Uni", "Kg", "g", "ml", "L"});


        new AlertDialog.Builder(getContext())
                .setTitle("Quantidade e medida")
                .setView(viewDialog)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        quantity = productQuantityET.getText().toString();
                        quantityBtn.setText(quantity + " " + picker.getDisplayedValues()[picker.getValue() - 1]);

                    }
                })
                .setIcon(R.drawable.ic_food_scale_tool)
                .show();
    }

    public void updateSale(Sale update) {
        HerokuPutSaleTask salePutTask = new HerokuPutSaleTask(update, SharedPreferencesUtils.getUser(getContext()), getContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
        salePutTask.execute();
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
        if(!isAdded()) {
            return;
        }
        for (Sale sale:sales) {
            if(this.sale.getId().equals(sale.getId())) this.sale = sale;

        }
        SharedPreferencesUtils.setSelectedSale(getContext(),sale);
        Log.d("LIKE SIZE", String.valueOf(sale.getLikeCount()));

        ((MainActivity)getActivity()).changeFragment(SaleDetailsFragment.getInstance(), SaleDetailsFragment.TAG,true);


    }

    @Override
    public void OnGetSalesByMarketReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnPostSaleFinished(boolean finished) {
    }

    @Override
    public void OnPutSaleFinished(boolean finished) {
        HerokuGetSalesTask salesTask = new HerokuGetSalesTask(String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
        salesTask.execute();

    }

    @Override
    public void OnPostLikeFinished(boolean finished) {

    }

    @Override
    public void OnDeleteLikeFinished(boolean finished) {

    }

    @Override
    public void OnPostDislikeFinished(boolean finished, Sale sale) {

    }

    @Override
    public void OnDeleteDislikeFinished(boolean finished) {

    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {

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
                    priceClicked = true;
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

}
