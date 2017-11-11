package com.projeto1.projeto1.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.adapters.ProductListAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.listeners.FavoriteListener;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;
import java.util.List;

public class FavoritListFragment extends Fragment implements MarketListener, ProductListener, SaleListener, FavoriteListener {

    public static final String TAG = "FAVORITLIST_FRAGMENT";
    private View mView;
    private ProductListAdapter mProductAdapter;
    private List<Sale> salesList;
    private List<Market> marketsList;
    private List<Product> productsList;
    private HerokuGetSalesTask salesTask;
    private HerokuGetProductsTask productTask;
    private HerokuGetMarketsTask marketTask;
    private RecyclerView productRecycleView;


    public FavoritListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FavoritListFragment getInstance() {
        FavoritListFragment fragment = new FavoritListFragment();
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

        mView = inflater.inflate(R.layout.fragment_favorit_list, container, false);

        marketsList = new ArrayList<>();
        productsList = new ArrayList<>();
        updateProductList();
        updateMarkettList();

        final User user = SharedPreferencesUtils.getUser(getContext());
        salesList = user.getFavorites();
        final TextView no_results = (TextView) mView.findViewById(R.id.no_results);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(salesList.size() == 0) no_results.setVisibility(View.VISIBLE);
                else no_results.setVisibility(View.GONE);
                mView.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
            }
        }, 1500);

        updateView();
        return mView;
    }

    private void updateView(){

        productRecycleView = (RecyclerView) mView.findViewById(R.id.product_list);
        mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList, getContext(), this);
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        llm2.setOrientation(LinearLayoutManager.VERTICAL);
        productRecycleView.setLayoutManager(llm2);
        productRecycleView.setAdapter(mProductAdapter);

    }

    private void updateMarkettList() {
        marketTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) , this);
        marketTask.execute();
    }

    public void updateProductList(){
        productTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)), this);
        productTask.execute();
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
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {
        marketsList = markets;
        mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList, getContext(), this);
        productRecycleView.setAdapter(mProductAdapter);

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


    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        productsList= products;
        if(!isAdded()) {
            return;
        }
        marketTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) , this);
        marketTask.execute();
        mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList, getContext(), this);
        productRecycleView.setAdapter(mProductAdapter);

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

    @Override
    public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {


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
    public void OnFavoriteIsClicked(boolean clicked, Sale sale) {
        salesList.remove(sale);
        updateView();
    }
}
