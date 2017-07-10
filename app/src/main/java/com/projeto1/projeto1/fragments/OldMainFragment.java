package com.projeto1.projeto1.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategorySwipeAdapter;

public class OldMainFragment extends Fragment {

    public static final String TAG = "MAIN_FRAGMENT";

    private CategorySwipeAdapter mAdapter;
    private ViewPager mPager;


    public OldMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static OldMainFragment getInstance() {
        OldMainFragment fragment = new OldMainFragment();
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

        View view = inflater.inflate(R.layout.fragment_main, container, false);

        mAdapter = new CategorySwipeAdapter(getChildFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.feed_pager);

        mPager.setAdapter(mAdapter);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mPager);



            tabLayout.setupWithViewPager(mPager);

            View viewTab1 = (View) inflater.inflate(R.layout.custom_tab, null);
            ImageButton iconTab1 = (ImageButton)  viewTab1.findViewById(R.id.icon_tab);
            iconTab1.setImageResource(R.drawable.ic_grocery);
            TextView tv_tab1 = (TextView)  viewTab1.findViewById(R.id.tv_tab);
            tv_tab1.setText("Alimentos");
            tabLayout.getTabAt(0).setCustomView(viewTab1);

        View viewTab2 = (View) inflater.inflate(R.layout.custom_tab, null);
        ImageButton iconTab2 = (ImageButton)  viewTab2.findViewById(R.id.icon_tab);
        iconTab2.setImageResource(R.drawable.ic_hygiene);
        TextView tv_tab2 = (TextView)  viewTab2.findViewById(R.id.tv_tab);
        tv_tab2.setText("Higiene");
        tabLayout.getTabAt(1).setCustomView(viewTab2);

        View viewTab3 = (View) inflater.inflate(R.layout.custom_tab, null);
        ImageButton iconTab3 = (ImageButton)  viewTab3.findViewById(R.id.icon_tab);
        iconTab3.setImageResource(R.drawable.ic_other);
        TextView tv_tab3 = (TextView)  viewTab3.findViewById(R.id.tv_tab);
        tv_tab3.setText("Outros");
        tabLayout.getTabAt(2).setCustomView(viewTab3);

        final FloatingActionButton addProductBtn = (FloatingActionButton) view.findViewById(R.id.scan_fab);

        addProductBtn.setClickable(true);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(AddProductFragment.getInstance(),AddProductFragment.TAG,true);

            }
        });




        // Inflate the layout for this fragment
        return view;
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