package com.projeto1.projeto1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.projeto1.projeto1.view_itens.SubCategoryViewItem;

import java.util.List;

/**
 * Created by samirsmedeiros on 22/06/17.
 */

public class SubCategoryListAdapter extends RecyclerView.Adapter {

    private List<String> items;

    public SubCategoryListAdapter(List<String> items) {
        this.items = items;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new SubCategoryViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        View mView =  ((SubCategoryViewItem)holder.itemView);
        ((SubCategoryViewItem)holder.itemView).displayName(items.get(position));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    private class ChipViewHolder extends RecyclerView.ViewHolder {

        public ChipViewHolder(View itemView) {
            super(itemView);
        }
    }
}
