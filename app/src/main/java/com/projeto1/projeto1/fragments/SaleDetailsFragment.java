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
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.endpoints.HerokuGetMarketTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuGetUserTask;
import com.projeto1.projeto1.listeners.GetUserListener;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;

/**
 * Created by samirsmedeiros on 17/06/17.
 */

public class SaleDetailsFragment extends Fragment implements ProductListener, MarketListener, GetUserListener {


    public static final String TAG = "SALE_DETAILS_FRAGMENT";

    private View mview;
    private Product product;
    private HerokuGetProductTask productTask;
    private HerokuGetMarketTask marketTask;
    private HerokuGetUserTask userTask;
    private Sale sale;
    private Market market;
    private User user;
    private MainActivity myMainActivity;
    private TextView mOld_price;
    private TextView mName_product;
    private TextView mBarcod;
    private TextView mValidity;
    private TextView currentPrice;
    private TextView marketName;
    private TextView validity;
    private TextView username;

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

        mview = inflater.inflate(R.layout.fragment_sale_details, container, false);


        sale = SharedPreferencesUtils.getSelectedSale(getContext());


        if (sale != null){
            productTask = new HerokuGetProductTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)) + "/" + sale.getProductId() , this);
            productTask.execute();
        }






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
        User user = SharedPreferencesUtils.getUser(getContext());
        user.setId(sale.getAuthorId());
        userTask = new HerokuGetUserTask(String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)), this, user);
        userTask.execute();

    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {
    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {
        this.market = market;

        mOld_price = (TextView) mview.findViewById(R.id.old_price);
        mName_product = (TextView) mview.findViewById(R.id.name_product);
        mBarcod = (TextView) mview.findViewById(R.id.barcode);
        mValidity = (TextView) mview.findViewById(R.id.validity);
        currentPrice = (TextView) mview.findViewById(R.id.current_price);
        marketName = (TextView) mview.findViewById(R.id.name_supermarket);
        validity = (TextView) mview.findViewById(R.id.validity);
        username = (TextView) mview.findViewById(R.id.user_name);


        mOld_price.setText("R$"+sale.getRegularPrice().toString());
        currentPrice.setText("R$"+sale.getSalePrice().toString());
        mName_product.setText(product.getName());
        mBarcod.setText(product.getBarcode());
        marketName.setText(market.getName());
        validity.setText(sale.getExpirationDate());
        username.setText(user.getName());


        mOld_price.setPaintFlags(mOld_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

    }

    public void setSale(Sale sale){
        this.sale = sale;
    }

    @Override
    public void OnGetAllUsersFinished(boolean ready, ArrayList<User> users) {

    }

    @Override
    public void OnGetUserFinished(boolean find, User user) {
        this.user = user;
        marketTask = new HerokuGetMarketTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) + "/" + sale.getMarketId() , this);
        marketTask.execute();

    }

    @Override
    public void OnPostUserFinished(boolean finished) {

    }
}