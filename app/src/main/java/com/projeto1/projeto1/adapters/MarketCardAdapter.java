package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.fragments.GroceryProductsFragment;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.view_itens.CardCategory;
import com.projeto1.projeto1.view_itens.CardMarket;

import java.util.List;

/**
 * Created by samirsmedeiros on 18/11/2017.
 */

public class MarketCardAdapter extends RecyclerView.Adapter<com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder> {

    private List<Market> items;
    private int [] icons;
    private Activity activity;
    private int[] bgs;

    public MarketCardAdapter(Activity activity, List<Market> items) {
        this.items = items;
        this.activity = activity;
    }

    @Override
    public com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder(new CardMarket(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder holder, final int position) {
        View mView =  ((CardMarket)holder.itemView);

        ((CardMarket)holder.itemView).displayName(items.get(position).getName());
        ((CardMarket)holder.itemView).displayAdress(items.get(position).getAdress().getNeighborhood());

        LinearLayout ll_card = ((CardMarket)holder.itemView).getLL();

        ll_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ChipViewHolder extends RecyclerView.ViewHolder {


        public ChipViewHolder(View itemView) {
            super(itemView);

        }
    }


}