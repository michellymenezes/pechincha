package com.projeto1.projeto1.fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategoryListAdapter;
import com.projeto1.projeto1.adapters.ProductListAdapter;
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

 public class SeachResultFragment extends Fragment implements SaleListener, MarketListener, ProductListener {


        public static final String TAG = "SALE_RESULTS_FRAGMENT";

        private View mview;
        private ProductListAdapter mProductAdapter;

        private RecyclerView categoryRecycleView;
        private RecyclerView productRecycleView;
        private List<Sale> salesList;
        private List<Market> marketsList;
        private List<Product> productsList;
        private HerokuGetSalesTask salesTask;
        private HerokuGetProductsTask productTask;
        private HerokuGetMarketsTask marketTask;
        private MainActivity myMainActivity;

        /**
         * Mandatory empty constructor for the fragment manager to instantiate the
         * fragment (e.g. upon screen orientation changes).
         */
        public SeachResultFragment() {
        }

        // TODO: Customize parameter initialization
        @SuppressWarnings("unused")
        public static SeachResultFragment getInstance() {
            SeachResultFragment fragment = new SeachResultFragment();
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

            salesList = new ArrayList<>();
            salesList = ((MainActivity) getActivity()).getSalesSearch();
            marketsList = new ArrayList<>();
            productsList = new ArrayList<>();


            mview = inflater.inflate(R.layout.fragment_seach_result, container, false);



            productRecycleView = (RecyclerView) mview.findViewById(R.id.sale_list_result);

        /* GET DE PROMOÇÕES */
            salesTask = new HerokuGetSalesTask(String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)), this);
            salesTask.execute();





            //salesList = new ArrayList<>(Arrays.asList(new Sale("0000", "Feijao",null, 3.99, null, null,0,0,null,null,0,0, "comida")));

            Log.v("Sales size", String.valueOf(salesList.size()));

            mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList, getContext());
            LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
            llm2.setOrientation(LinearLayoutManager.VERTICAL);
            productRecycleView.setLayoutManager(llm2);
            productRecycleView.setAdapter(mProductAdapter);

            return mview;
        }

    public void setSalesList(List<Sale> salesList) {
        this.salesList = salesList;
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


     @SuppressLint("LongLogTag")
     @Override
     public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {

     }

     @Override
     public void OnPostSaleFinished(boolean finished) {

     }

     @Override
     public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {
         marketsList = markets;
         mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList, getContext());
         productRecycleView.setAdapter(mProductAdapter);


     }

     @Override
     public void OnPostMarketsFinished(boolean finished) {

     }

     @Override
     public void OnGetMarketReady(boolean b, Market market) {

     }

     @Override
     public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
         productsList = products;
         marketTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)) , this);
         marketTask.execute();
         //     mProductAdapter = new ProductListAdapter(getActivity(), salesList, marketsList,productsList);
         //     productRecycleView.setAdapter(mProductAdapter);

     }

     @Override
     public void OnPostProductFinished(boolean finished) {

     }

     @Override
     public void OnGetProductReady(boolean b, Product product) {

     }

 }