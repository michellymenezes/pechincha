package com.projeto1.projeto1.listeners;

import com.projeto1.projeto1.models.Sale;

import java.util.ArrayList;


public interface SaleListener {
    void OnGetSalesReady(boolean ready, ArrayList<Sale> sales);
    void OnPostSaleFinished(boolean finished);
    void OnPutSaleFinished(boolean finished);
    void OnPostLikeFinished(boolean finished);
    void OnDeleteLikeFinished(boolean finished);
    void OnPostDislikeFinished(boolean finished);
    void OnDeleteDislikeFinished(boolean finished);

}
