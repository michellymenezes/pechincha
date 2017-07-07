package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.fragments.SaleDetailsFragment;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.view_itens.CategoryViewItem;
import com.projeto1.projeto1.view_itens.ProductViewItem;

import java.util.List;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder>{

    private static final String TAG = "profile_list_adapter ";
    private final List<Sale> items;
    private final Activity activity;


    public ProductListAdapter(Activity activity, List<Sale> items) {
        this.items = items;
        this.activity = activity;
    }
    @Override
    public ProductItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductItemHolder(new ProductViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final ProductItemHolder holder, final int position) {

        View view = ((ProductViewItem) holder.itemView);
        ((ProductViewItem) holder.itemView).displayName(items.get(position).getProduct());
        ((ProductViewItem) holder.itemView).displayPrice(items.get(position).getCurrentPrice());
        ((ProductViewItem) holder.itemView).d(items.get(position).getSupermarket());

        RelativeLayout saleCard = (RelativeLayout) ((ProductViewItem) holder.itemView).getRelativerLayout();

        saleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CARREGAR AS INFORMAÇOES DA PROMOÇAO NA TELA DE VISUALIZACAO
                ((MainActivity) activity).changeFragment(SaleDetailsFragment.getInstance(),SaleDetailsFragment.TAG,true);
            }
        });



    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class ProductItemHolder extends RecyclerView.ViewHolder {


        public ProductItemHolder(View itemView) {
            super(itemView);

        }
    }


}





