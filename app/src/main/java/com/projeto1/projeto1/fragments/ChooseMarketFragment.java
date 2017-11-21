package com.projeto1.projeto1.fragments;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.adapters.SuperMarketAdapter;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsBySearchTask;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.models.Market;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirsmedeiros on 20/11/2017.
 */


    public class ChooseMarketFragment extends Fragment implements MarketListener {

        public static final String TAG = "SUPERMARKET_FRAGMENT";
        private List<Market> mMarketSugestions;
        private View mView;


        public ChooseMarketFragment() {
            // Required empty public constructor
        }

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.2.
         * @return A new instance of fragment FeedFragment.
         */
        // TODO: Rename and change types and number of parameters
        public static ChooseMarketFragment getInstance() {
            ChooseMarketFragment fragment = new ChooseMarketFragment();
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

            mView = inflater.inflate(R.layout.fragment_choose_market, container, false);
            FloatingActionButton fab_add_market = (FloatingActionButton) mView.findViewById(R.id.fab_add_market);

            TextView tv_result = (TextView) mView.findViewById(R.id.textView);

            tv_result.setText("Lojas em " + ((MainActivity) getActivity()).getCurrentAddress().getCity());


            fab_add_market.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) getActivity()).changeFragment(AddMarketFragment.getInstance(), AddMarketFragment.TAG, true);

                }
            });

            final AutoCompleteTextView ac_supermarket = (AutoCompleteTextView) mView.findViewById(R.id.ac_supermarket);


            final ImageButton clear_btn_market = (ImageButton) mView.findViewById(R.id.clear_btn_market);


            ac_supermarket.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {


                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(ac_supermarket.getText().toString().length()>0) {
                        clear_btn_market.setImageResource(R.drawable.ic_close);
                        clear_btn_market.setTag(new Boolean(true));
                    }else{
                        clear_btn_market.setImageResource(R.drawable.ic_search);
                        clear_btn_market.setTag(new Boolean(false));
                    }
                    updateList(ac_supermarket.getText().toString(), "", ((MainActivity) getActivity()).getCurrentAddress().getCity(), "");

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






            hideKeyboard(ac_supermarket);

            return mView;
        }

        private void updateList(String name, String neighb, String city, String state) {
            Log.d("MARKET", name);
            Log.d("Bairro", neighb);
            Log.d("CIDADE", city);
            Log.d("ESTADO", state);

            name = name.equals("")? null: name.trim().replace("çã", "ca").replace("ã","a").replace("õ","o");
            neighb = neighb.equals("")? null: neighb.trim().replace("çã", "ca").replace("ã","a").replace("õ","o");
            city = city.equals("")? null: city.trim().replace("çã", "ca").replace("ã","a").replace("õ","o");
            state = state.equals("")? null: state.trim().replace("çã", "ca").replace("ã","a").replace("õ","o");

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
            if(!isAdded()) {
                return;
            }
            SuperMarketAdapter marketAdapter = new SuperMarketAdapter(getActivity(), mMarketSugestions);
            categoryRecycleView.setAdapter(marketAdapter);

        }

        @Override
        public void OnGetMarketReady(boolean b, Market market) {

        }

        private void updateMarketList(){
            updateList("", "", ((MainActivity) getActivity()).getCurrentAddress().getCity(), "");
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


