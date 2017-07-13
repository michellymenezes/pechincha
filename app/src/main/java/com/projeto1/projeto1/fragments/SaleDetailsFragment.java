package com.projeto1.projeto1.fragments;

import com.projeto1.projeto1.MainActivity;

import android.content.Context;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.endpoints.HerokuGetProductTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;

/**
 * Created by samirsmedeiros on 17/06/17.
 */

public class SaleDetailsFragment extends Fragment implements ProductListener, MarketListener {


    public static final String TAG = "SALE_DETAILS_FRAGMENT";

    private View mview;
    private Product product;
    private HerokuGetProductTask productTask;
    private Sale sale;
    private MainActivity myMainActivity;






    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public SaleDetailsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static SaleDetailsFragment getInstance() {
        SaleDetailsFragment fragment = new SaleDetailsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


       // productTask = new HerokuGetProductTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)) + "/" + sale.getProductId() , this);
        //productTask.execute();

        mview = inflater.inflate(R.layout.fragment_sale_details, container, false);

        TextView old_price = (TextView) mview.findViewById(R.id.old_price);
        old_price.setText("4.99");
        old_price.setPaintFlags(old_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        TextView name_product = (TextView) mview.findViewById(R.id.name_product);
        //name_product.setText(product.getName());


        startAdapter();



        return mview;
    }

    private void startAdapter() {

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
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {

    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {
        this.product = product;
    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {

    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {

    }

    public void setSale(Sale sale){
        this.sale = sale;
    }
}