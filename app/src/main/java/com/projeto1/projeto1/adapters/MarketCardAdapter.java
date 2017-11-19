package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.fragments.GroceryProductsFragment;
import com.projeto1.projeto1.fragments.SeachResultFragment;
import com.projeto1.projeto1.fragments.SupermarketFragment;
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
        items.add(null);
        this.activity = activity;
    }

    @Override
    public com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder(new CardMarket(parent.getContext()));
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(com.projeto1.projeto1.adapters.MarketCardAdapter.ChipViewHolder holder, final int position) {
        View mView =  ((CardMarket)holder.itemView);

        if(items.get(position)!=null){
            ((CardMarket)holder.itemView).displayName(items.get(position).getName());
            ((CardMarket)holder.itemView).displayAdress(items.get(position).getAdress().getNeighborhood());

            LinearLayout ll_card = ((CardMarket)holder.itemView).getLL();

            ll_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) activity).setIdMarketSearch(items.get(position).getId());
                    ((MainActivity) activity).changeFragment(SeachResultFragment.getInstance(), SeachResultFragment.TAG,true);
                }
            });
        } else {
            ((CardMarket)holder.itemView).displayAdress("Buscar");
            ((CardMarket)holder.itemView).setNameInvisible();
            ((CardMarket)holder.itemView).displayIcon(R.drawable.ic_search_market);
            ImageButton btn = ((CardMarket)holder.itemView).getBtn();
            btn.setBackground(activity.getResources().getDrawable(R.drawable.bg_category_white));
            btn.setElevation(5);
            LinearLayout ll_card = ((CardMarket)holder.itemView).getLL();
            ll_card.setBackground(activity.getResources().getDrawable(R.drawable.bg_transparent));
            ll_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) activity).setIsAddNewSale(false);
                    ((MainActivity) activity).changeFragment(SupermarketFragment.getInstance(), SupermarketFragment.TAG,true);
                }
            });
        }






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