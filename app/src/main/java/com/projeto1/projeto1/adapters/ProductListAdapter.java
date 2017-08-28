package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.endpoints.HerokuGetMarketsTask;
import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPutSaleTask;
import com.projeto1.projeto1.fragments.GroceryProductsFragment;
import com.projeto1.projeto1.fragments.SaleDetailsFragment;
import com.projeto1.projeto1.fragments.UpdateSaleFragment;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;
import com.projeto1.projeto1.view_itens.ChecboxCategoryViewItem;
import com.projeto1.projeto1.view_itens.ProductViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductItemHolder> implements ProductListener, SaleListener {

    private static final String TAG = "profile_list_adapter ";
    private final List<Sale> salesList;
    private final Context context;
    private List<Product> products;
    private List<Market> markets;
    private final Activity activity;
    private Resources resources;
    private HerokuGetProductsTask productTask;
    private HerokuGetMarketsTask marketTask;
    private List<String> likedProducts;
    private boolean onBind = false;



    public ProductListAdapter(Activity activity, List<Sale> salesList, List<Market> markets, List<Product> products, Context context) {
        this.salesList = salesList;
        this.activity = activity;
        this.markets = markets;
        this.products = products;
        this.context = context;
        likedProducts = new ArrayList<>();

    }
    @Override
    public ProductItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ProductItemHolder(new ProductViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ProductItemHolder holder, final int position) {

        String market = salesList.get(position).getMarketId();
        for (Market m:markets
                ) {
            if(m.getId().equals(salesList.get(position).getMarketId())){
                market = m.getName();
                break;
            }
        }

        final Sale saleItem = salesList.get(position);
        String product = salesList.get(position).getProductId();
        for (Product p:products
                ) {
            String p1 = p.getId();
            String p2 = salesList.get(position).getProductId();
            if(p1.equals(p2)){
                product = p.getName();
                break;
            }
        }
        final User user = SharedPreferencesUtils.getUser(activity.getBaseContext());
        View view = ((ProductViewItem) holder.itemView);
        ((ProductViewItem) holder.itemView).displayName(product);
        ((ProductViewItem) holder.itemView).displayPrice(salesList.get(position).getSalePrice());
        ((ProductViewItem) holder.itemView).displayLikeQuantity(salesList.get(position).getLikeCount());
        ((ProductViewItem) holder.itemView).d(market);
        ((ProductViewItem) holder.itemView).setCBText(saleItem.getId());

        final CheckBox likeCB = ((ProductViewItem) holder.itemView).getLikeCB();

        likeCB.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!onBind) {
                    saleItem.addRemoveLike(user.getId());
                    updateSale(saleItem);
                    notifyDataSetChanged();
                }


            }
        });

        final RelativeLayout saleCard = (RelativeLayout) ((ProductViewItem) holder.itemView).getRelativerLayout();

        saleCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO CARREGAR AS INFORMAÇOES DA PROMOÇAO NA TELA DE VISUALIZACAO

                Log.d(TAG, saleItem.toString());

                SharedPreferencesUtils.setSelectedSale(context, saleItem);

                ((MainActivity) activity).changeFragment(SaleDetailsFragment.getInstance(), SaleDetailsFragment.TAG,true);
            }
        });


        onBind = true;
        holder.cb.setChecked(saleItem.getLikeUsers().contains(user.getId()));
        onBind = false;
    }


    @Override
    public int getItemCount() {
        return salesList.size();
    }




    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        this.products = products;

    }

    @Override
    public void OnGetProductsByCategoryReady(boolean ready, ArrayList<Product> products) {

    }

    @Override
    public void OnPostProductFinished(boolean finished) {

    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {

    }

    @Override
    public void OnGetProductByBarcodeReady(boolean ready, Product product) {

    }

    @Override
    public void OnGetSalesReady(boolean ready, ArrayList<Sale> sales) {

    }

    @Override
    public void OnPostSaleFinished(boolean finished) {

    }

    @Override
    public void OnPutSaleFinished(boolean finished) {

    }

    public static class ProductItemHolder extends RecyclerView.ViewHolder {

        public CheckBox cb;

        public ProductItemHolder(View itemView) {
            super(itemView);
            cb = ((ProductViewItem)itemView).getLikeCB();

        }
    }

    private void updateSale(Sale update) {
        HerokuPutSaleTask salePutTask = new HerokuPutSaleTask(update, activity.getBaseContext(), String.format(activity.getResources().getString(R.string.HEROKU_SALE_ENDPOINT)) + "/" + update.getId(), this);
        salePutTask.execute();    }


}





