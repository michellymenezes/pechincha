package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projeto1.projeto1.listeners.UserListener;
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

import javax.net.ssl.HttpsURLConnection;


public class HerokuRemoveFavoriteSaleTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_REMOVE_FAVORITE_SALE_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private User user;
    private String saleId;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    private  UserListener mListener;

    public HerokuRemoveFavoriteSaleTask(User user, String saleId, Context context, String endpoint, UserListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.user = user;
        this.mListener = mListener;
        this.saleId = saleId;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {

            String parameters = "saleId=" + saleId;
            url = new URL(ENDPOINT_ADDRESS + user.getId() + "/favorite");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("DELETE");

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
            Toast.makeText(context, "Oferta Removida com sucesso", Toast.LENGTH_LONG).show();
            mListener.OnRemoveFavoriteSaleFinished(true);
        }
        else {
            mListener.OnRemoveFavoriteSaleFinished(false);
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}



