package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Sale;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import javax.net.ssl.HttpsURLConnection;


public class HerokuGetSalesTask extends AsyncTask {
    private static final String TAG = "HEROKU_GET_SALES_TASK";

    private ArrayList<Sale> sales;
    private String responseMessage = "";
    private String endpoint;
    private SaleListener mListener;


    public HerokuGetSalesTask(String url, SaleListener listener) {
        endpoint = url;
        sales = new ArrayList<Sale>();
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
                JSONArray salesJSON = new JSONArray(responseMessage);
                Log.d(TAG, String.valueOf(salesJSON));


                    for (int i = 0; i < salesJSON.length(); i++) {
                        if ( salesJSON.getJSONObject(i).length() >=11){

                            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                            String id = salesJSON.getJSONObject(i).getString("_id");
                            String product = salesJSON.getJSONObject(i).getString("productName");
                           // String category = salesJSON.getJSONObject(i).getString("category");
                            Double regularPrice = salesJSON.getJSONObject(i).getDouble("regularPrice");
                            Double promotionPrice = salesJSON.getJSONObject(i).getDouble("promotionPrice");
                            Date expirationDate =  df.parse(salesJSON.getJSONObject(i).getString("expirationDate"));
                            String supermarket = salesJSON.getJSONObject(i).getString("supermarket");
                            int quantity = salesJSON.getJSONObject(i).getInt("quantity");
                            int stars = salesJSON.getJSONObject(i).getInt("stars");
                            String author = salesJSON.getJSONObject(i).getString("author");
                            Date publicationDate = df.parse(salesJSON.getJSONObject(i).getString("publicationDate"));
                            //Boolean expiredSale = salesJSON.getJSONObject(i).getBoolean("expiredSale");
                           // int likes = salesJSON.getJSONObject(i).getInt("likes");
                            //int dislikes = salesJSON.getJSONObject(i).getInt("dislikes");


                            // FALTA O GET DE COMMENTS



                            Sale sale = new Sale(id, product, regularPrice, promotionPrice, expirationDate, supermarket, quantity,
                                    stars, author, publicationDate, 0, 0, "comida");

                            sales.add(sale);
                        }

                    }


                Log.d(TAG, "Number of sales: " + String.valueOf(salesJSON.length()));
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
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }




    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (sales != null) {
            mListener.OnGetSalesReady(true, this.sales);
        } else {
            mListener.OnGetSalesReady(false, this.sales);
        }
    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;

    }
}
