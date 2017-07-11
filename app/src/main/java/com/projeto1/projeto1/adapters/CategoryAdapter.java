package com.projeto1.projeto1.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.projeto1.projeto1.R;
import com.projeto1.projeto1.fragments.AddProductFragment;
import com.projeto1.projeto1.view_itens.ChecboxCategoryViewItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by samirsmedeiros on 10/07/17.
 */

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ChipViewHolder> {

    private List<String> items;
    private List<String> cbItems;
    private String selectedCategoy;
    private List<CheckBox> cbs;
    private TextView mtvSub;
    private int [] icons;

    public CategoryAdapter(List<String> items, TextView mtvSub) {
        this.items = items;
        cbs = new ArrayList<>();
        this.mtvSub = mtvSub;
        this.selectedCategoy = "";
        cbItems = new ArrayList<>();
    }

    @Override
    public ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new ChecboxCategoryViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ChipViewHolder holder, final int position) {
        View mView =  ((ChecboxCategoryViewItem)holder.itemView);

        icons = new int[]{R.drawable.ic_grocery, R.drawable.ic_hygiene, R.drawable.ic_wiping_white,
                R.drawable.ic_plug_white, R.drawable.ic_sofa_white, R.drawable.ic_other};
        ((ChecboxCategoryViewItem)holder.itemView).displayName(items.get(position));
        ((ChecboxCategoryViewItem)holder.itemView).displayIcon(icons[position]);
        final CheckBox cbItem = ((ChecboxCategoryViewItem)holder.itemView).getCheckBox();


        cbs.add(position,cbItem);

        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!cbItem.isChecked() && cbItems.contains(cbItem.getText().toString())){
                    cbItems.remove(cbItem.getText().toString());
                }else if (cbItem.isChecked() && !cbItems.contains(cbItem.getText().toString())){
                    cbItems.clear();
                    cbItems.add(cbItem.getText().toString());
                    mtvSub.setText(cbItem.getText().toString());
                    selectedCategoy = cbItem.getText().toString();
                    notifyDataSetChanged();
                }

            }
        });

        holder.cb.setChecked(cbItems.contains(holder.cb.getText()));

    }


    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ChipViewHolder extends RecyclerView.ViewHolder {

        public CheckBox cb;

        public ChipViewHolder(View itemView) {
            super(itemView);
            cb = ((ChecboxCategoryViewItem)itemView).getCheckBox();

        }
    }

    public void setClicled(String str){
        for (CheckBox cb: cbs
             ) {
            if(str.equals(cb.getText().toString())) cb.setChecked(true);
            notifyDataSetChanged();
        }
    }

    public String getCbSelected() {
        return selectedCategoy;
    }
}