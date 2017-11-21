package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Historic;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Rafaelle on 28/06/2017.
 */

public class HerokuPutSaleTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_PUT_SALES_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private Sale sale;
    private User user;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    private  SaleListener mListener;

    public HerokuPutSaleTask(Sale sale, User user, Context context, String endpoint, SaleListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.sale = sale;
        this.user = user;
        this.mListener = mListener;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
            Date date = new Date(System.currentTimeMillis());
            date.setHours(date.getHours()+1);
            String stringDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(date);


            String parameters = "product=" + sale.getProductId() + "&market=" + sale.getMarketId() +
                    "&salePrice=" + sale.getSalePrice() + "&regularPrice=1" +
                    "&expirationDate=" + sale.getExpirationDate() + "&publicationDate=" + stringDate +
                    "&author=" + user.getId()  + "&likeCount=0" +
                    "&dislikeCount=0"+ "&reportCount=0" +
                    "&likeUsers=[]" + "&dislikeUsers=[]" + "&reportUsers=[]" + "&historic=[]";
            url = new URL(ENDPOINT_ADDRESS + "/" + sale.getId());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("PUT");

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(parameters);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();
            int responseCode = conn.getResponseCode();

            Log.v(TAG, conn.getResponseMessage() + responseCode);

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
            } else {
                BufferedReader br1 = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String line = "", error = "";
                while ((line = br1.readLine()) != null) {
                    error += line;
                }
                Log.wtf(TAG, error);
                return false;
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
            Toast.makeText(context, "Promoção Alterada com sucesso", Toast.LENGTH_LONG).show();
            mListener.OnPutSaleFinished(true);
        }
        else {
            mListener.OnPutSaleFinished(false);
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}



