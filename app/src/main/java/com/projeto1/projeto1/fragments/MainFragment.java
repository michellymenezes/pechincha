package com.projeto1.projeto1.fragments;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategorySwipeAdapter;

public class MainFragment extends Fragment {

    public static final String TAG = "DETAILS_FRAGMENT";

    private CategorySwipeAdapter mAdapter;
    private ViewPager mPager;


    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment getInstance() {
        MainFragment fragment = new MainFragment();
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

        View feed_view = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new CategorySwipeAdapter(getChildFragmentManager());
        mPager = (ViewPager) feed_view.findViewById(R.id.feed_pager);

        mPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) feed_view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);



            tabLayout.setupWithViewPager(mPager);

            TextView tab0 = (TextView) inflater.inflate(R.layout.custom_tab, null);
            tab0.setText("Alimentos");
            tab0.setHighlightColor(Color.WHITE);
            tab0.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_grocery, 0, 0);
            tabLayout.getTabAt(0).setCustomView(tab0);

            TextView tab1 = (TextView) inflater.inflate(R.layout.custom_tab, null);
            tab1.setText("Higiene");
            tab1.setHighlightColor(Color.WHITE);
            tab1.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_hygiene, 0, 0);
            tabLayout.getTabAt(1).setCustomView(tab1);

            TextView tab2 = (TextView) inflater.inflate(R.layout.custom_tab, null);
            tab2.setText("Outros");
            tab2.setHighlightColor(Color.WHITE);
            tab2.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_other, 0, 0);
            tabLayout.getTabAt(2).setCustomView(tab2);




        // Inflate the layout for this fragment
        return feed_view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}