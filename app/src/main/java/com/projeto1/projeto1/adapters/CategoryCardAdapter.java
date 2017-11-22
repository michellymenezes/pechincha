package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;
import com.projeto1.projeto1.fragments.GroceryProductsFragment;
import com.projeto1.projeto1.view_itens.CardCategory;
import com.projeto1.projeto1.view_itens.ChecboxCategoryViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirsmedeiros on 16/11/2017.
 */

public class CategoryCardAdapter extends RecyclerView.Adapter<com.projeto1.projeto1.adapters.CategoryCardAdapter.ChipViewHolder> {

        private List<String> items;
        private int [] icons;
        private Activity activity;
    private int[] bgs;

    public CategoryCardAdapter(Activity activity, List<String> items) {
            this.items = items;
            this.activity = activity;
        }

        @Override
        public com.projeto1.projeto1.adapters.CategoryCardAdapter.ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new com.projeto1.projeto1.adapters.CategoryCardAdapter.ChipViewHolder(new CardCategory(parent.getContext()));
        }

        @Override
        public void onBindViewHolder(com.projeto1.projeto1.adapters.CategoryCardAdapter.ChipViewHolder holder, final int position) {
            View mView =  ((CardCategory)holder.itemView);

            icons = new int[]{R.drawable.ic_grocery_blue, R.drawable.ic_hygiene_blue, R.drawable.ic_wiping,
                    R.drawable.ic_plug, R.drawable.ic_sofa, R.drawable.ic_other_gray};
            ((CardCategory)holder.itemView).displayName(items.get(position));

           /* bgs = new int[]{R.drawable.bg_category_circle_yellow, R.drawable.bg_category_circle_blue, R.drawable.bg_category_circle_red,
                    R.drawable.bg_category_circle_green, R.drawable.bg_category_circle_orange, R.drawable.bg_category_circle_ligth};

            Drawable dw = activity.getResources().getDrawable(bgs[position]);
            ((CardCategory)holder.itemView).setBG(dw);*/

            ((CardCategory)holder.itemView).displayName(items.get(position));

            ((CardCategory)holder.itemView).displayIcon(icons[position]);

            LinearLayout ll_card = ((CardCategory)holder.itemView).getLL();

            ll_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ((MainActivity) activity).setCurrentCategory(items.get(position));
                    ((MainActivity) activity).changeFragment(GroceryProductsFragment.getInstance(),GroceryProductsFragment.TAG,true);
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
