package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.projeto1.projeto1.R;
import com.projeto1.projeto1.models.Market;

import java.util.List;

/**
 * Created by rafaelle on 27/07/17.
 */

public class MarketListAdapter extends ArrayAdapter<Market> {


    private final List<Market> items;
    private final Activity activity;

    public MarketListAdapter(Activity activity, List<Market> items) {
        super(activity, android.R.layout.simple_list_item_1, items);
        this.items = items;
        this.activity = activity;
    }



    /**
     * Monta a view com informações da parada a ser escolhida
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        Market currMarket = items.get(position);


        if (v == null) {
            LayoutInflater inflater = activity.getLayoutInflater();
            v = inflater.inflate(R.layout.market_list_adapter_item, null);
        }


        TextView textView = (TextView) v.findViewById(R.id.text1);

        try {
            textView.setText(currMarket.getName());
        } catch (Exception e) {
            Log.e("currMarket", e.getMessage());
        }

        return v;
    }


    /**
     * Retorna o item (Market) do dropdown
     */
    @Override
    public Market getItem(int position) {
        return items.get(position);
    }

    public static class OnItemClickListener implements AdapterView.OnItemClickListener {
        private Market selectedMarket;


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectedMarket = (Market) parent.getSelectedItem();

        }


    }
}
