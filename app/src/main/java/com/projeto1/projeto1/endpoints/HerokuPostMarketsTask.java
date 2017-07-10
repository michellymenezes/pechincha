package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;


import com.projeto1.projeto1.MarketListener;
import com.projeto1.projeto1.models.Market;

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

import javax.net.ssl.HttpsURLConnection;

public class HerokuPostMarketsTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_MARKETS_TASK";


    private final String ENDPOINT_ADDRESS;
    private final Context context;
    private Market market;
    private MarketListener mListener;

    private String responseMessage = "";


    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    public HerokuPostMarketsTask(Market market, Context context, String endpoint, MarketListener listener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.market = market;
        mListener = listener;

    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {

            String parameters = "name=" + market.getName() + "&image=" + market.getImage() +
                    "&cnpj=" + market.getCnpj() + "&address={street=" + market.getAdress().getStreet() +
                    "&number=" + market.getAdress().getNumber() + "$complemet=" + market.getAdress().getComplement() +
                    "&neighborhood=" + market.getAdress().getNeighborhood() +
                    "&city=" + market.getAdress().getCity() + "&state=" + market.getAdress().getCity() +
                    "&country=" + market.getAdress().getCountry() + "}&localization={longitude=" + market.getLocalization().getLongitude() +
                    "&latitude=" + market.getLocalization().getLatitude() + "}";

           // parameters = jsonObject.toString();
            Log.v("PARAMETERS", parameters);
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
            Log.v("RESPONSE_CODE: ", conn.getResponseMessage());
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
            Toast.makeText(context, "Mercado cadastrado com sucesso", Toast.LENGTH_LONG).show();
            mListener.OnPostMarketsFinished(true);
        }
        else {
            mListener.OnPostMarketsFinished(false);
        }

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}
