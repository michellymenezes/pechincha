package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 10/07/17.
 */

public class ChecboxCategoryViewItem extends FrameLayout {


        public ChecboxCategoryViewItem(Context context) {
            super(context);
            initializeView(context);

        }

        private void initializeView(Context context) {
            LayoutInflater.from(context).inflate(R.layout.checkbox_category_item, this);
        }

        public void displayName(String name) {
            ((TextView)findViewById(R.id.checkbox_name)).setText(name);
            ((CheckBox)findViewById(R.id.checkbox)).setText(name);

        }

    public void displayIcon(int icon) {
        ((ImageButton)findViewById(R.id.checkbox_image)).setImageResource(icon);

    }

    public CheckBox getCheckBox() {
        return ((CheckBox)findViewById(R.id.checkbox));
    }
}
