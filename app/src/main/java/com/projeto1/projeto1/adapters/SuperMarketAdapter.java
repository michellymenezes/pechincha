package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.fragments.AddProductFragment;
import com.projeto1.projeto1.fragments.SeachResultFragment;
import com.projeto1.projeto1.fragments.SupermarketFragment;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.view_itens.ChecboxCategoryViewItem;
import com.projeto1.projeto1.view_itens.SupermarketItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by samirsmedeiros on 29/08/17.
 */

public class SuperMarketAdapter extends RecyclerView.Adapter<SuperMarketAdapter.ChipViewHolder> {

    private List<Market> items;
    private Activity activity;
    private boolean isAddNewSale;


    public SuperMarketAdapter(Activity activity, List<Market> items, boolean isAddNewSale) {
        this.items = items;
        this.activity = activity;
        this.isAddNewSale = isAddNewSale;
    }

    @Override
    public ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new SupermarketItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ChipViewHolder holder, final int position) {
        View mView =  ((SupermarketItem)holder.itemView);
        ((SupermarketItem)holder.itemView).displayName(items.get(position).getName());
        ((SupermarketItem)holder.itemView).displayAddress(items.get(position).getAdress().getStreet() + ", "
                + items.get(position).getAdress().getNumber() + ", " +
                items.get(position).getAdress().getNeighborhood());

        RelativeLayout ll = ((SupermarketItem)holder.itemView).getLinearLayout();
        ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isAddNewSale){
                    ((MainActivity) activity).setChosenMarket(items.get(position));
                    ((MainActivity) activity).changeFragment(AddProductFragment.getInstance(),AddProductFragment.TAG,true);

                } else {
                    ((MainActivity) activity).setIdMarketSearch(items.get(position).getId());
                    ((MainActivity) activity).changeFragment(SeachResultFragment.getInstance(),SeachResultFragment.TAG,true);
                    ((MainActivity) activity).setIsAddNewSale(true);
                }

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