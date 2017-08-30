package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 29/08/17.
 */

public class SupermarketItem extends FrameLayout {


        public SupermarketItem(Context context) {
            super(context);
            initializeView(context);

        }

        private void initializeView(Context context) {
            LayoutInflater.from(context).inflate(R.layout.supermarket_item, this);
        }

        public void displayName(String name) {
            ((TextView)findViewById(R.id.market_name)).setText(name);

        }
        public void displayAddress(String name) {
            ((TextView)findViewById(R.id.market_address)).setText(name);

        }
        public RelativeLayout getLinearLayout(){
            return (RelativeLayout)findViewById(R.id.ll_market);
        }

    }