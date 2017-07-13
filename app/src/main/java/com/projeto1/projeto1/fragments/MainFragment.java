package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Arrays;


public class MainFragment extends Fragment implements SaleListener,ProductListener {


    public static final String TAG = "MAIN_FRAGMENT";

    private View mview;
    private ArrayList<Sale> mASales;
    private ArrayList<String> mArraySugestions;
    private ArrayList<Product> productsList;
    private HerokuGetProductsTask produtcsTask;
    private HerokuGetSalesTask salesTask;


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

        final String[] selectedText = {""};

        final AutoCompleteTextView searchView = (AutoCompleteTextView) mview.findViewById(R.id.search_bar);


        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setText("");
                mArraySugestions = getSalesName();
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.row_layout, mArraySugestions);
                searchView.setAdapter(adapter);
                searchView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                        //this is the way to find selected object/item
                        selectedText[0] = (String) adapterView.getItemAtPosition(pos);
                        Log.v("STRING", selectedText[0]);
                    }
                });
            }
        });

        final FloatingActionButton addProductBtn = (FloatingActionButton) mview.findViewById(R.id.scan_fab);

        final ImageButton grocery_btn = (ImageButton) mview.findViewById(R.id.grocery_btn);

        grocery_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
            }
        });

        addProductBtn.setClickable(true);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(AddProductFragment.getInstance(),AddProductFragment.TAG,true);

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
                if(product.getId().equals(sale.getProductId())) array.add(product.getName());
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
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        productsList = products;
        Log.v(TAG, "Quantidade de produtos: " + String.valueOf(productsList.size()));
    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {


    }
}
