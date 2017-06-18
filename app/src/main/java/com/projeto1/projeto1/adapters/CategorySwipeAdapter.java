package com.projeto1.projeto1.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.projeto1.projeto1.fragments.GroceryProductsFragment;
import com.projeto1.projeto1.fragments.HygieneProductsFragment;
import com.projeto1.projeto1.fragments.OtherProductsFragment;

/**
 * Created by samirsmedeiros on 17/06/17.
 */

public class CategorySwipeAdapter extends FragmentPagerAdapter {

    static final int NUM_ITEMS = 3;

    public CategorySwipeAdapter(FragmentManager fm) {
        super(fm);
    }

    /**
     * Retorna o Fragment da respectiva posição
     */
    @Override
    public Fragment getItem(int position)
    {
        switch (position){
            case 0 : return GroceryProductsFragment.getInstance();
            case 1 : return HygieneProductsFragment.getInstance();
            case 2 : return OtherProductsFragment.getInstance();
        }
        return null;
    }

    /**
     * Retorna o número de Fragments que serão mostrados na ViewPager
     */
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }


    /**
     * Este método retorna o título da tab de acordo com a posição.
     */

    @Override
    public CharSequence getPageTitle(int position) {

        switch (position){
            case 0 :
                return "Alimentos";
            case 1 :
                return "Higiene";

            case 2 :
                return "Outros";
        }
        return null;
    }

}

