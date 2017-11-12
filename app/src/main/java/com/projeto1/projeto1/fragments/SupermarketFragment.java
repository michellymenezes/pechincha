package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.CategoryAdapter;
import com.projeto1.projeto1.adapters.SuperMarketAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsBySearchTask;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SupermarketFragment extends Fragment implements MarketListener {

    public static final String TAG = "SUPERMARKET_FRAGMENT";
    private List<Market> mMarketSugestions;
    private View mView;


    public SupermarketFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SupermarketFragment getInstance() {
        SupermarketFragment fragment = new SupermarketFragment();
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
        mMarketSugestions = new ArrayList<>();
        updateMarketList();

        mView = inflater.inflate(R.layout.fragment_supermarket, container, false);
        FloatingActionButton fab_add_market = (FloatingActionButton) mView.findViewById(R.id.fab_add_market);


        fab_add_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).changeFragment(AddMarketFragment.getInstance(), AddMarketFragment.TAG, true);

            }
        });

        final AutoCompleteTextView ac_city = (AutoCompleteTextView) mView.findViewById(R.id.ac_city);
        final AutoCompleteTextView ac_state = (AutoCompleteTextView) mView.findViewById(R.id.ac_state);
        final AutoCompleteTextView ac_neighb = (AutoCompleteTextView) mView.findViewById(R.id.ac_neighb);
        final AutoCompleteTextView ac_supermarket = (AutoCompleteTextView) mView.findViewById(R.id.ac_supermarket);


        final ImageButton clear_btn_city = (ImageButton) mView.findViewById(R.id.clear_btn_city);
        final ImageButton clear_btn_state = (ImageButton) mView.findViewById(R.id.clear_btn_state);
        final ImageButton clear_btn_nei = (ImageButton) mView.findViewById(R.id.clear_btn_nei);
        final ImageButton clear_btn_market = (ImageButton) mView.findViewById(R.id.clear_btn_market);


        ac_city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MARKET", String.valueOf(ac_supermarket.getText().toString().equals("")));
                Log.d("Bairro", ac_neighb.getText().toString());
                Log.d("CIDADE", ac_city.getText().toString());
                Log.d("ESTADO", ac_state.getText().toString());

                if(ac_city.getText().toString().length()>0) {
                    clear_btn_city.setImageResource(R.drawable.ic_close);
                    clear_btn_city.setTag(new Boolean(true));
                }else{
                    clear_btn_city.setImageResource(R.drawable.ic_write);
                    clear_btn_city.setTag(new Boolean(false));
                }
                updateList(ac_supermarket.getText().toString(), ac_neighb.getText().toString(), ac_city.getText().toString(), ac_state.getText().toString());

            }
        });

        ac_state.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MARKET", String.valueOf(ac_supermarket.getText().toString().equals("")));
                Log.d("Bairro", ac_neighb.getText().toString());
                Log.d("CIDADE", ac_city.getText().toString());
                Log.d("ESTADO", ac_state.getText().toString());
                if(ac_state.getText().toString().length()>0) {
                    clear_btn_state.setImageResource(R.drawable.ic_close);
                    clear_btn_state.setTag(new Boolean(true));
                }else{
                    clear_btn_state.setImageResource(R.drawable.ic_write);
                    clear_btn_state.setTag(new Boolean(false));
                }
                updateList(ac_supermarket.getText().toString(), ac_neighb.getText().toString(), ac_city.getText().toString(), ac_state.getText().toString());

            }
        });

        ac_neighb.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MARKET", String.valueOf(ac_supermarket.getText().toString().equals("")));
                Log.d("Bairro", ac_neighb.getText().toString());
                Log.d("CIDADE", ac_city.getText().toString());
                Log.d("ESTADO", ac_state.getText().toString());
                if(ac_neighb.getText().toString().length()>0) {
                    clear_btn_nei.setImageResource(R.drawable.ic_close);
                    clear_btn_nei.setTag(new Boolean(true));
                }else{
                    clear_btn_nei.setImageResource(R.drawable.ic_write);
                    clear_btn_nei.setTag(new Boolean(false));
                }
                updateList(ac_supermarket.getText().toString(), ac_neighb.getText().toString(), ac_city.getText().toString(), ac_state.getText().toString());

            }
        });

        ac_supermarket.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("MARKET", String.valueOf(ac_supermarket.getText().toString().equals("")));
                Log.d("Bairro", ac_neighb.getText().toString());
                Log.d("CIDADE", ac_city.getText().toString());
                Log.d("ESTADO", ac_state.getText().toString());
                if(ac_supermarket.getText().toString().length()>0) {
                    clear_btn_market.setImageResource(R.drawable.ic_close);
                    clear_btn_market.setTag(new Boolean(true));
                }else{
                    clear_btn_market.setImageResource(R.drawable.ic_write);
                    clear_btn_market.setTag(new Boolean(false));
                }
                updateList(ac_supermarket.getText().toString(), ac_neighb.getText().toString(), ac_city.getText().toString(), ac_state.getText().toString());

            }
        });

        clear_btn_market.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clear_btn_market.getTag())!=null && ((Boolean)clear_btn_market.getTag())==true){
                    ac_supermarket.setText("");
                }

            }
        });
        clear_btn_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clear_btn_state.getTag())!=null && ((Boolean)clear_btn_state.getTag())==true){
                    ac_state.setText("");
                }

            }
        });

        clear_btn_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clear_btn_city.getTag())!=null && ((Boolean)clear_btn_city.getTag())==true){
                    ac_city.setText("");
                }

            }
        });

        clear_btn_nei.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((clear_btn_nei.getTag())!=null && ((Boolean)clear_btn_nei.getTag())==true){
                    ac_neighb.setText("");
                }

            }
        });

        hideKeyboard(ac_supermarket);
        hideKeyboard(ac_neighb);
        hideKeyboard(ac_city);
        hideKeyboard(ac_state);



        return mView;
    }

    private void updateList(String name, String neighb, String city, String state) {
        Log.d("MARKET", name);
        Log.d("Bairro", neighb);
        Log.d("CIDADE", city);
        Log.d("ESTADO", state);

        name = name.equals("")? null: name.trim();
        neighb = neighb.equals("")? null: neighb.trim();
        city = city.equals("")? null: city.trim();
        state = state.equals("")? null: state.trim();


        HerokuGetMarketsBySearchTask mtask = new HerokuGetMarketsBySearchTask(name, neighb,city, state, String.format(getResources().getString(R.string.HEROKU_MARKET_BY_SEARCH_ENDPOINT)), this);
        mtask.execute();
    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> markets) {

        for (Market name : markets) {
            if(!mMarketSugestions.contains(name))mMarketSugestions.add(name);
        }

        RecyclerView categoryRecycleView = (RecyclerView) mView.findViewById(R.id.supermarket_list_result);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(llm);
        Log.d("SIZE>>>>>>>>", mMarketSugestions.size()+"");
        SuperMarketAdapter marketAdapter = new SuperMarketAdapter(getActivity(), mMarketSugestions);
        categoryRecycleView.setAdapter(marketAdapter);


    }

    public  void hideKeyboard(final AutoCompleteTextView eT){

        eT.setOnEditorActionListener(new EditText.OnEditorActionListener() {


            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                // Identifier of the action. This will be either the identifier you supplied,
                // or EditorInfo.IME_NULL if being called due to the enter key being pressed.
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || event.getAction() == KeyEvent.ACTION_DOWN
                        ) {
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(eT.getWindowToken(),
                            InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    return true;
                }
                // Return true if you have consumed the action, else false.
                return false;
            }

        });
        //keyboard down

    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketsBySearchReady(boolean ready, ArrayList<Market> markets) {
        mMarketSugestions.clear();
        mMarketSugestions = markets;
        for (Market name : markets) {
            if(!mMarketSugestions.contains(name))mMarketSugestions.add(name);
        }
        RecyclerView categoryRecycleView = (RecyclerView) mView.findViewById(R.id.supermarket_list_result);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        categoryRecycleView.setLayoutManager(llm);
        Log.d("SIZE>>>>>>>>", mMarketSugestions.size()+"");
        SuperMarketAdapter marketAdapter = new SuperMarketAdapter(getActivity(), mMarketSugestions);
        categoryRecycleView.setAdapter(marketAdapter);

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
