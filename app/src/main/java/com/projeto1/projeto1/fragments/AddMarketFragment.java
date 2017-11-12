package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.SuperMarketAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuPostMarketsTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.models.Address;
import com.projeto1.projeto1.models.Localization;
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

        final EditText market_name = (EditText) mView.findViewById(R.id.market_name);
        final EditText market_city = (EditText) mView.findViewById(R.id.market_city);
        final EditText market_neighborhood = (EditText) mView.findViewById(R.id.market_neighborhood);
        final EditText market_state = (EditText) mView.findViewById(R.id.market_state);
        final EditText market_street = (EditText) mView.findViewById(R.id.market_street);
        final EditText market_number = (EditText) mView.findViewById(R.id.market_number);
        Button sendBtn = (Button) mView.findViewById(R.id.send_btn);


        final Localization local = new Localization(2.34, 3.33);

        final String[] marketName = {""};
        final String[] street = {""};
        final String[] number = {""};
        final String[] neighborhood = {""};
        final String[] city = {""};
        final String[] state = {""};
        final String country = "Brasil";
        final String complement = "Ao lado";

        market_name.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    marketName[0] = market_name.getText().toString();
                    hideKeyboard(market_name);
                    Log.v("TECLADO", "done name");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });

        market_street.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    street[0] = market_street.getText().toString();
                    hideKeyboard(market_street);
                    Log.v("TECLADO", "done street");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });

        market_number.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    number[0] =  market_number.getText().toString();
                    hideKeyboard(market_number);
                    Log.v("TECLADO", "done number");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });

        market_neighborhood.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    neighborhood[0] =  market_neighborhood.getText().toString();
                    hideKeyboard(market_neighborhood);
                    Log.v("TECLADO", "done nei");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });

        market_city.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    city[0] =  market_city.getText().toString();
                    hideKeyboard(market_city);
                    Log.v("TECLADO", "done city");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });
        market_state.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    state[0] =  market_state.getText().toString();
                    hideKeyboard(market_state);
                    Log.v("TECLADO", "done state");
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state[0] =  market_state.getText().toString();
                neighborhood[0] =  market_neighborhood.getText().toString();
                city[0] =  market_city.getText().toString();
                number[0] =  market_number.getText().toString();
                street[0] = market_street.getText().toString();
                marketName[0] = market_name.getText().toString();
                if(marketName[0].equals("") || street[0].equals("") || city[0].equals("") || state[0].equals("") || neighborhood[0].equals("") || number[0].equals("")){
                    Toast toast = Toast.makeText(getContext(),
                            "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    final Address address = new Address(street[0].trim(), number[0].trim(), neighborhood[0].trim(), city[0].trim(), state[0].trim(), country.trim(), complement.trim());
                    Market market = new Market(marketName[0].trim(), address, "image", "07.170.938/0001-07", local);
                    sendMarket(market);

                }
            }
        });

        return mView;
    }

    private void sendMarket(Market market) {
        HerokuPostMarketsTask mTask = new HerokuPostMarketsTask(market, getContext(), String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)), this);
        mTask.execute();
    }

    public  void hideKeyboard(EditText  eT){
        //keyboard down
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(eT.getWindowToken(),
                InputMethodManager.RESULT_UNCHANGED_SHOWN);
    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {




    }
    @Override
    public void OnPostMarketsFinished(boolean finished) {
        ((MainActivity) getActivity()).changeFragment(SupermarketFragment.getInstance(), SupermarketFragment.TAG, true);


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
