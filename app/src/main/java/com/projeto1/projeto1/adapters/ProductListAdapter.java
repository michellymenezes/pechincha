package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.endpoints.HerokuPostMarketsTask;
import com.projeto1.projeto1.fragments.SaleDetailsFragment;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.view_itens.CategoryViewItem;
import com.projeto1.projeto1.view_itens.ProductViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder> implements ProductListener, MarketListener{

    private static final String TAG = "profile_list_adapter ";
    private final List<Sale> items;
    private List<Product> products;
    private List<Market> markets;
    private final Activity activity;
    private Resources resources;
    private HerokuGetProductsTask productTask;
    private HerokuGetMarketsTask marketTask;


    public ProductListAdapter(Activity activity, List<Sale> items, List<Market> markets, List<Product> products) {
        this.items = items;
        this.activity = activity;
        this.markets = markets;
        this.products = products;


    }
    @Override
    public ProductItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductItemHolder(new ProductViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final ProductItemHolder holder, final int position) {

        String market = items.get(position).getMarketId();
        for (Market m:markets
                ) {
            if(m.getId().equals(items.get(position).getMarketId())){
                market = m.getName();
                break;
            }
        }

        String product = items.get(position).getProductId();
        for (Product p:products
                ) {
            String p1 = p.getId();
            String p2 = items.get(position).getProductId();
            if(p1.equals(p2)){
                product = p.getName();
                break;
            }
        }

        View view = ((ProductViewItem) holder.itemView);
        ((ProductViewItem) holder.itemView).displayName(product);
        ((ProductViewItem) holder.itemView).displayPrice(items.get(position).getSalePrice());
        ((ProductViewItem) holder.itemView).d(market);

        final RelativeLayout saleCard = (RelativeLayout) ((ProductViewItem) holder.itemView).getRelativerLayout();

        saleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CARREGAR AS INFORMAÇOES DA PROMOÇAO NA TELA DE VISUALIZACAO
                ((MainActivity) activity).changeFragment(SaleDetailsFragment.getInstance(), SaleDetailsFragment.TAG,true);
            }
        });



    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> products) {

    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        this.products = products;

    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {

    }

    public static class ProductItemHolder extends RecyclerView.ViewHolder {


        public ProductItemHolder(View itemView) {
            super(itemView);

        }
    }


}





