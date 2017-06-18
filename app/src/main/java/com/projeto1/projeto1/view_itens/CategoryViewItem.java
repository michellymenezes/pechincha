package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class CategoryViewItem extends FrameLayout {


    public CategoryViewItem(Context context) {
        super(context);
        initializeView(context);

    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.category_item, this);
    }

    public void displayName(String name) {
        ((TextView)findViewById(R.id.btn_category)).setText(name);

    }

}
