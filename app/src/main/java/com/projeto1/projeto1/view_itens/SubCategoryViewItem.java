package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 22/06/17.
 */

public class SubCategoryViewItem extends FrameLayout {


    public SubCategoryViewItem(Context context) {
        super(context);
        initializeView(context);

    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.sub_category_item, this);
    }

    public void displayName(String name) {
        ((CheckBox)findViewById(R.id.checkbox)).setText(name);
    }

}