package com.projeto1.projeto1;

import com.projeto1.projeto1.models.Market;

import java.util.ArrayList;


public interface MarketListener {
    void OnGetMarketsReady(boolean ready, ArrayList<Market> products);
    void OnPostMarketsFinished(boolean finished);
}
