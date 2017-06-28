package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projeto1.projeto1.models.Sale;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rafaelle on 28/06/2017.
 */

public class HerokuPostSalesTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_SALES_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private Sale sale;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    public HerokuPostSalesTask(Sale sale, Context context, String endpoint) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.sale = sale;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {

            //_id , productName, category, regularPrice, promotionPrice, expirationDate
            // supermarket, quantity, stars, "author","publicationDate, "likes", dislikes,comments

            String parameters = "productName=" + sale.getProductName() + "&regularPrice=" + sale.getRegularPrice() +
                    "&promotionPrice=" + sale.getCurrentPrice() + "&expirationDate=" + sale.getExpirationDate() + "&supermarket=" +sale.getSupermarket() +
                    "&quantity=" + sale.getQuantity() + "&stars=" + sale.getStarts() + "&author=" + sale.getAuthor() + "&publicationDate=" + sale.getPublicationDate();

            url = new URL(ENDPOINT_ADDRESS);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(parameters);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            int responseCode = conn.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseMessage += line;
                }
                JSONObject jsonObject = new JSONObject(responseMessage);
                Log.d(TAG, jsonObject.toString());

                isSuccessfulRegister = (jsonObject.length() > 2);

                /*
                 isSuccessfulRegister = false;
                Iterator<String> keys = jsonObject.keys();


                while( keys.hasNext() ) {
                    String key = (String)keys.next();
                    if (key.equals("success")){
                        isSuccessfulRegister = jsonObject.getString("success").contains("success") ;
                    }
                }*/

                conn.disconnect();
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPostExecute(Boolean success) {
        if (isSuccessfulRegister) {
            Toast.makeText(context, "Promoção cadastrada com sucesso", Toast.LENGTH_LONG).show();
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}



