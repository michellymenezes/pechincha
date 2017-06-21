package com.projeto1.projeto1.endpoints;

import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.R;
import com.projeto1.projeto1.models.Product;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rafaelle on 21/06/2017.
 */


public class HerokuGetProductsTask extends AsyncTask {
    private static final String TAG = "HEROKU_GET_PRODUCTS_TASK";

    private final ArrayList<Product> products;
    private String responseMessage = "";
    private String endpoint;

    public HerokuGetProductsTask(String url) {
        endpoint = url;
        products = new ArrayList<Product>();
    }

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
                JSONArray productsJSON = new JSONArray(responseMessage);
                Log.d(TAG, String.valueOf(productsJSON));

                    /*
                    for (int i = 0; i < productsJSON.length(); i++) {
                        String barcode = productsJSON.getJSONObject(i).getString("_id")
                        products.add(product);
                    }
                    */

                Log.d(TAG, "Number of products: " + String.valueOf(productsJSON.length()));
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
    }

}
