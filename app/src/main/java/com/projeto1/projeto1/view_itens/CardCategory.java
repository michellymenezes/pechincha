package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 16/11/2017.
 */

    public class CardCategory extends FrameLayout {


        public CardCategory(Context context) {
            super(context);
            initializeView(context);

        }

        private void initializeView(Context context) {
            LayoutInflater.from(context).inflate(R.layout.categoy_card, this);
        }

        public void displayName(String name) {
            ((TextView)findViewById(R.id.name_categoy)).setText(name);

        }

        public void displayIcon(int icon) {
            ((ImageButton)findViewById(R.id.category_btn)).setImageResource(icon);

        }

        public void setBG(Drawable bg) {
            ((ImageButton)findViewById(R.id.category_btn)).setBackground(bg);
        }



        public LinearLayout getLL(){
            return  ((LinearLayout)findViewById(R.id.ll_item));
        }



    }