package com.projeto1.projeto1.view_itens;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.projeto1.projeto1.R;

/**
 * Created by samirsmedeiros on 18/06/17.
 */

public class ProductViewItem extends FrameLayout {


    public ProductViewItem(Context context) {
        super(context);
        initializeView(context);

    }

    private void initializeView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.product_item, this);
    }

    public void displayName(String name) {
        ((TextView)findViewById(R.id.product_name)).setText(name);

    }

    public void displayPrice(Double price) {
        String priceSt = String.format( "%.2f", price );
        ((TextView)findViewById(R.id.product_price)).setText("R$" + priceSt);

    }

    public void displayProductImg(int img) {
        ((ImageView)findViewById(R.id.product_img)).setImageResource(img);
    }

    public void displayBrandtImg(int img) {
        ((ImageView)findViewById(R.id.banner_img)).setImageResource(img);
    }

    public void d(String s){
        ((TextView) findViewById(R.id.market_name)).setText(s);
    }

    public RelativeLayout getRelativerLayout(){
        return (RelativeLayout)findViewById(R.id.product_container);
    }

}
