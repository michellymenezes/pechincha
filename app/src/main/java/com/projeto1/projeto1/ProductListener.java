package com.projeto1.projeto1;

import com.projeto1.projeto1.models.Product;

import java.util.ArrayList;

/**
 * Created by rafaelle on 27/06/17.
 */

public interface ProductListener {
    void OnGetProductsReady(boolean ready, ArrayList<Product> products);
    void OnPostProductFinished(boolean finished);
}
