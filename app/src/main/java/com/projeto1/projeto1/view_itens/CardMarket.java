package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 18/11/2017.
 */

public class CardMarket extends FrameLayout {


    public CardMarket(Context context) {
        super(context);
        initializeView(context);

    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.market_card, this);
    }

    public void displayName(String name) {
        ((TextView)findViewById(R.id.name_market)).setText(name);

    }

    public void displayAdress(String ad) {
        ((TextView)findViewById(R.id.adress)).setText(ad);

    }

    public void setNameInvisible() {
        ((TextView)findViewById(R.id.name_market)).setTextSize(0);
    }


    public void displayIcon(int icon) {
        ((ImageButton)findViewById(R.id.market_btn)).setImageResource(icon);

    }

    public ImageButton getBtn() {
        return ((ImageButton)findViewById(R.id.market_btn));
    }


    public LinearLayout getLL(){
        return  ((LinearLayout)findViewById(R.id.ll_item));
    }



}