package com.projeto1.projeto1.fragments;

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

import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.SuperMarketAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.models.Market;

import java.util.ArrayList;
import java.util.List;


public class AddMarketFragment extends Fragment implements MarketListener {

    public static final String TAG = "ADD_SUPERMARKET_FRAGMENT";
    private View mView;


    public AddMarketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMarketFragment getInstance() {
        AddMarketFragment fragment = new AddMarketFragment();
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


        mView = inflater.inflate(R.layout.fragment_add_market, container, false);


        return mView;
    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {




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

    private void updateMarketList(){
        HerokuGetMarketsTask herokuGetMarketsTask = new HerokuGetMarketsTask(String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)), this);
        herokuGetMarketsTask.execute();
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
