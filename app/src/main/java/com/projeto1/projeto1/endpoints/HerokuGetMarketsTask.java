package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.MarketListener;
import com.projeto1.projeto1.models.Address;
import com.projeto1.projeto1.models.Localization;
import com.projeto1.projeto1.models.Market;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;



public class HerokuGetMarketsTask extends AsyncTask {
    private static final String TAG = "HEROKU_GET_MARKETS_TASK";

    private ArrayList<Market> markets;
    private String responseMessage = "";
    private String endpoint;
    private MarketListener mListener;


    public HerokuGetMarketsTask(String url, MarketListener listener) {
        endpoint = url;
        markets = new ArrayList<Market>();
        mListener = listener;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Object... params) {
        URL url;
        try {
            url = new URL(endpoint);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            conn.setRequestProperty("Content-Type", "application/json");

            conn.connect();
            int responseCode = conn.getResponseCode();

            Log.d(TAG, "Response Code: " + String.valueOf(responseCode));

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseMessage += line;
                }
                JSONArray marketsJSON = new JSONArray(responseMessage);
                Log.d(TAG, String.valueOf(marketsJSON));


                    for (int i = 0; i < marketsJSON.length(); i++) {
                        if ( marketsJSON.getJSONObject(i).length() > 5){

                            String id = marketsJSON.getJSONObject(i).getString("_id");
                            String name = marketsJSON.getJSONObject(i).getString("name");
                            String image = marketsJSON.getJSONObject(i).getString("image");
                            String cnpj = marketsJSON.getJSONObject(i).getString("cnpj");

                            JSONObject addressJSON = new JSONObject(marketsJSON.getJSONObject(i).getString("address"));
                            JSONObject localizationJSON = new JSONObject(marketsJSON.getJSONObject(i).getString("localization"));

                            if (addressJSON.length() > 7 & localizationJSON.length() > 2) {
                                String street = addressJSON.getString("street");
                                String number = addressJSON.getString("number");
                                String neighborhood = addressJSON.getString("neighborhood");
                                String city = addressJSON.getString("city");
                                String state = addressJSON.getString("state");
                                String country = addressJSON.getString("country");
                                String complement = addressJSON.getString("complement");
                                Address address = new Address(street, number, neighborhood, city, state, country, complement);

                                Number longitude = localizationJSON.getInt("longitude");
                                Number latitude = localizationJSON.getInt("latitude");
                                Localization localization = new Localization(longitude, latitude);

                                Market market = new Market(id, name,address, image, cnpj, localization);
                                markets.add(market);
                            }
                            else {
                                continue;
                            }
                        }

                    }


                Log.d(TAG, "Number of markets: " + markets.size());
                return true;

            } else {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line = "", error = "";
                while ((line = br1.readLine()) != null) {
                    error += line;
                }
                Log.d(TAG, error);
                return false;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (markets != null) {
            mListener.OnGetMarketsReady(true, this.markets);
        } else {
            mListener.OnGetMarketsReady(false, this.markets);
        }
    }

    public ArrayList<Market> getMarkets() {
        return markets;
    }

    public void setMarkets(ArrayList<Market> markets) {
        this.markets = markets;

    }
}
