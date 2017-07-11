package com.projeto1.projeto1.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;

import com.projeto1.projeto1.fragments.AddProductFragment;
import com.projeto1.projeto1.view_itens.ChecboxCategoryViewItem;
import com.projeto1.projeto1.view_itens.SubCategoryViewItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by samirsmedeiros on 22/06/17.
 */

public class SubCategoryListAdapter extends RecyclerView.Adapter<SubCategoryListAdapter.ChipViewHolder> {

    private List<String> items;
    private List<String> cbItems;
    private String selectedSubCategory;
    private ArrayList<String> mArrySucategory;


    public SubCategoryListAdapter(String str) {
        this.items = getSubcategory(str);
        this.selectedSubCategory = "";
        cbItems = new ArrayList<>();
        mArrySucategory = new ArrayList<>();

    }

    @Override
    public ChipViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ChipViewHolder(new SubCategoryViewItem(parent.getContext()));
    }

    @Override
    public void onBindViewHolder(ChipViewHolder holder, final int position) {
        View mView =  ((SubCategoryViewItem)holder.itemView);
        ((SubCategoryViewItem)holder.itemView).displayName(items.get(position));
        final CheckBox cbItem = ((SubCategoryViewItem)holder.itemView).getCheckBox();




        cbItem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(!cbItem.isChecked() && cbItems.contains(cbItem.getText().toString())){
                    cbItems.remove(cbItem.getText().toString());
                }else if (cbItem.isChecked() && !cbItems.contains(cbItem.getText().toString())){
                    cbItems.clear();
                    cbItems.add(cbItem.getText().toString());
                    selectedSubCategory = cbItem.getText().toString();
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
            cb = ((SubCategoryViewItem)itemView).getCheckBox();

        }
    }

    public String getCbSelected() {
        return selectedSubCategory;
    }

    public List<String> getSubcategory(String str) {

        switch (str) {
            case "Alimento": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Grãos", "Bebidas", "Laticínio",
                        "Carnes", "Oleos", "Frutas e Verduras"));
                break;
            }
            case "Higiene": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Higiene"));

                break;
            }
            case "Limpeza": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Limpeza"));
                break;

            }
            case "Eletrônico": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Eletronico"));
                break;

            }
            case "Mobília": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Mobilia"));

                break;
            }
            case "Outros": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Ountros"));
                break;

            }
            default:
                mArrySucategory = new ArrayList<>();
        }

        return mArrySucategory;
    }
}
