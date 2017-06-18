package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projeto1.projeto1.R;

import java.util.List;

/**
 * Created by samirsmedeiros on 17/06/17.
 */

public class OtherProductsFragment extends Fragment {


    public static final String TAG = "GROCERY_PRODUCTS_FRAGMENT";

    private RecyclerView mRecycleView;
    private View mview;
    private List<String> mIngredients;
    private List<String> mIngredientsImgs;




    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OtherProductsFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static OtherProductsFragment getInstance() {
        OtherProductsFragment fragment = new OtherProductsFragment();
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

        mview = inflater.inflate(R.layout.fragment_other_products, container, false);

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

}
