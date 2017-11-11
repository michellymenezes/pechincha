package com.projeto1.projeto1.listeners;

import com.projeto1.projeto1.models.Market;

import java.util.ArrayList;


public interface MarketListener {
    void OnGetMarketsReady(boolean ready, ArrayList<Market> markets);
    void OnPostMarketsFinished(boolean finished);
    void OnGetMarketsBySearchReady(boolean ready, ArrayList<Market> markets);
    void OnGetMarketReady(boolean b, Market market);
}
