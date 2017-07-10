package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategoryListAdapter;
import com.projeto1.projeto1.adapters.ProductListAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class TestMainFragment extends Fragment {


    public static final String TAG = "HYGIENE_PRODUCTS_FRAGMENT";

    private View mview;
    private CategoryListAdapter mAdapter;
    private ProductListAdapter mProductAdapter;
    private List<String> categoryList;
    private RecyclerView categoryRecycleView;
    private RecyclerView productRecycleView;
    private List<Sale> salesList;
    private HerokuGetSalesTask salesTask;
    private MainActivity myMainActivity;



    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public TestMainFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static TestMainFragment getInstance() {
        TestMainFragment fragment = new TestMainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        mview = inflater.inflate(R.layout.fragment_test_main, container, false);


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

}
