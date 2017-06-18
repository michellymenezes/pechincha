package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import android.widget.ImageButton;
import android.widget.TextView;

import com.projeto1.projeto1.view_itens.CategoryViewItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryItemHolder>{

    private static final String TAG = "profile_list_adapter ";
    private final List<String> items;
    private final Activity activity;


    public CategoryListAdapter(Activity activity, List<String> items) {
        this.items = items;
        this.activity = activity;
    }
    @Override
    public CategoryItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryItemHolder(new CategoryViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(final CategoryItemHolder holder, final int position) {

        View currFilter = ((CategoryViewItem) holder.itemView);
        ((CategoryViewItem) holder.itemView).displayName(items.get(position));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }


    public static class CategoryItemHolder extends RecyclerView.ViewHolder {



        public CategoryItemHolder(View itemView) {
            super(itemView);

        }
    }


}





