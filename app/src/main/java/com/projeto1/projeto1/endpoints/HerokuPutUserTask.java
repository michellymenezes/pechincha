package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.listeners.UserListener;
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

import javax.net.ssl.HttpsURLConnection;


public class HerokuPutUserTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_PUT_USER_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private User user;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    private  UserListener mListener;

    public HerokuPutUserTask(User user, Context context, String endpoint, UserListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.user = user;
        this.mListener = mListener;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {

            //historic: [ [Date, double] ]
            String favorites = user.getFavorites().toString();
            favorites.replace("{","").replace("}","").replaceAll("\\s","").trim();
            Log.d(TAG, favorites);

            //values[]=stringarrayitem1&values[]=stringarrayitem2&values[]=stringarrayitem3
            String parameters = "fullName=" + user.getName()+ "&username="+ user.getName() +
                    "&email=" + user.getEmail() + "&avatar=" + user.getImage() +"&created_at=" + user.getCreatedAt() +
                    "&reputation=" + user.getReputation() + "&preferences=" + user.getPreferences() + "&favorites" + user.favoritesToJson();
            url = new URL(ENDPOINT_ADDRESS + user.getId() + "/");

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
            Toast.makeText(context, "Usuário Alterado com sucesso", Toast.LENGTH_LONG).show();
            mListener.OnPutUserFinished(true);
        }
        else {
            mListener.OnPutUserFinished(false);
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}



