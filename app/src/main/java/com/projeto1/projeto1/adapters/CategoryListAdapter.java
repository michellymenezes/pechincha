package com.projeto1.projeto1.adapters;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.projeto1.projeto1.view_itens.CategoryViewItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class CategoryListAdapter extends RecyclerView.Adapter<CategoryListAdapter.CategoryItemHolder>{

    private static final String TAG = "profile_list_adapter ";
    private final List<String> items;
    private final Activity activity;
    private String category;


    public CategoryListAdapter(Activity activity, String category) {
        this.category = category;
        this.items = getSubcategory(category);
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

        Button button = (Button) ((CategoryViewItem) holder.itemView).getButton();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, items.get(position).toString());
            }

        });
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

    public List<String> getSubcategory(String str) {
        List<String> mArrySucategory = new ArrayList<>();
        switch (str) {
            case "Alimento": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Massas", "Lanches", "Grãos", "Bebidas", "Laticínio",
                        "Carnes", "Oleos", "Frutas e Verduras", "Outros"));
                break;
            }
            case "Cuidados pessoais": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Higiene", "Perfumaria", "Remédio", "Outros"));

                break;
            }
            case "Limpeza": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Objetos", "Sabão", "Desinfetantes", "Outros"));
                break;

            }
            case "Eletrônico": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Sala", "Cozinha", "Quarto", "Portátil", "Escritório", "Outros"));
                break;

            }
            case "Mobília": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Sala", "Cozinha", "Quarto", "Banheiro", "Escritório", "Outros"));

                break;
            }
            case "Outros": {
                mArrySucategory = new ArrayList<>(Arrays.asList("Outros"));
                break;

            }
            default:
                mArrySucategory = new ArrayList<>();
        }

        return mArrySucategory;
    }


}





