package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by Rafaelle on 21/06/2017.
 */

public class HerokuPostProductsTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_PRODUCTS_TASK";


    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private String id;
    private String v;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    public HerokuPostProductsTask(String id, String v, Context context, String endpoint) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.id = id;
        this.v = v;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {
            String parameters = "_id=" + id + "&__v=" + v;
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
        //mAuthTask = null;

        if (isSuccessfulRegister) {
            Toast.makeText(context, "Produto cadastrado com sucesso", Toast.LENGTH_LONG).show();
            //TODO login or
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}