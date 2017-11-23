package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Historic;
import com.projeto1.projeto1.models.Sale;

import org.json.JSONArray;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class HerokuPostDislikeTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_DISLIKE_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private String saleId;
    private String userId;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;
    private Sale sale;

    private  SaleListener mListener;

    public HerokuPostDislikeTask(String saleId, String userId, Context context, String endpoint, SaleListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.saleId = saleId;
        this.mListener = mListener;
        this.userId = userId;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Void... params) {

        URL url;
        try {


            String parameters = "userId=" + userId;
            url = new URL(ENDPOINT_ADDRESS + "/" + saleId + "/dislike");

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

            Log.v(TAG, conn.getResponseMessage() + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line = "";
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line = br.readLine()) != null) {
                    responseMessage += line;
                }
                JSONObject jsonObject = new JSONObject(responseMessage);

                DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                String id = jsonObject.getString("_id");
                String productId = jsonObject.getString("product");
                String marketId = jsonObject.getString("market");
                Double salePrice = jsonObject.getDouble("salePrice");
                Double regularPrice = jsonObject.getDouble("regularPrice");
                String expirationDate =  jsonObject.getString("expirationDate");
                Date publicationDate = df.parse(jsonObject.getString("publicationDate"));
                String authorId = jsonObject.getString("author");

                List<Historic> historic = new ArrayList<Historic>();
                JSONArray historicList =  jsonObject.getJSONArray("historic");
                for(int j = 0; j < historicList.length(); j++){

                    double value = historicList.getJSONObject(j).getDouble("value");
                    Date date = df.parse(historicList.getJSONObject(j).getString("saleDate"));
                    int likeCount = historicList.getJSONObject(j).getInt("likeCount");
                    int dislikeCount = historicList.getJSONObject(j).getInt("dislikeCount");
                    int reportCount = historicList.getJSONObject(j).getInt("reportCount");

                    List<String> likeUsers = new ArrayList<String>();
                    JSONArray likeList =  historicList.getJSONObject(j).getJSONArray("likeUsers");
                    for(int k = 0; k < likeList.length(); k++){
                        likeUsers.add(likeList.getString(k));
                    }

                    List<String> dislikeUsers = new ArrayList<String>();
                    JSONArray dislikeList =  historicList.getJSONObject(j).getJSONArray("dislikeUsers");
                    for(int k = 0; k < dislikeList.length(); k++){
                        dislikeUsers.add(dislikeList.getString(k));
                    }

                    historic.add(new Historic(date, value, likeCount, dislikeCount, reportCount, likeUsers, dislikeUsers));
                }

                int likeCount = jsonObject.getInt("likeCount");
                int dislikeCount = jsonObject.getInt( "dislikeCount");
                int reportCount = jsonObject.getInt("reportCount");

                ArrayList<String> likeUsers = new ArrayList<String>();
                JSONArray likeList =  jsonObject.getJSONArray("likeUsers");
                for(int j = 0; j < likeList.length(); j++){
                    likeUsers.add(likeList.getString(j));
                }

                ArrayList<String> reportUsers = new ArrayList<String>();
                JSONArray reportList =  jsonObject.getJSONArray("likeUsers");
                for(int j = 0; j < reportList.length(); j++){
                    reportUsers.add(reportList.getString(j));
                }

                ArrayList<String> dislikeUsers = new ArrayList<String>();
                JSONArray dislikeList =  jsonObject.getJSONArray("dislikeUsers");
                for(int j = 0; j < dislikeList.length(); j++){
                    dislikeUsers.add(dislikeList.getString(j));
                }


                // int quantity = salesJSON.getJSONObject(i).getInt("quantity");
                // int unit = salesJSON.getJSONObject(i).getInt("unit");


                sale = new Sale(id, productId, marketId, salePrice, regularPrice, expirationDate, publicationDate, authorId, 1, historic, likeCount, dislikeCount, reportCount, likeUsers, reportUsers, dislikeUsers);

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
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return true;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected void onPostExecute(Boolean success) {

        if (isSuccessfulRegister) {
            Toast.makeText(context, "Dislike adicionado com sucesso", Toast.LENGTH_LONG).show();
            mListener.OnPostDislikeFinished(true, sale);
        }
        else {
            mListener.OnPostDislikeFinished(false, sale);
        }
        Log.d(TAG, responseMessage);

    }

    @Override
    protected void onCancelled() {
        mAuthTask = null;
    }

}



