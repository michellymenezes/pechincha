package com.projeto1.projeto1.endpoints;

import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.listeners.UserListener;
import com.projeto1.projeto1.models.Historic;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;


public class HerokuGetUserTask extends AsyncTask {

    private static final String TAG = "HEROKU_GET_USER_TASK";

    private User user;
    private User userToFind;

    private String responseMessage = "";
    private String endpoint;
    private UserListener mListener;


    public HerokuGetUserTask(String url, UserListener listener, User user) {
        endpoint = url;
        userToFind = user;
        mListener = listener;
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
            return  getUser();

    }

    private boolean getUser(){
        URL url;
        try {

            url = new URL(endpoint + '/' + userToFind.getId());

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
                JSONObject usersJSON = new JSONObject(responseMessage);
                Log.d(TAG, String.valueOf(usersJSON));



                String id = usersJSON.getString("_id");
                String facebookId = usersJSON.getString("facebookId");
                String name = usersJSON.getString("fullName");
                String email = usersJSON.getString("email");
                String createdAt = usersJSON.getString("createdAt");
                String image = usersJSON.getString("avatar");
                Double reputation = usersJSON.getDouble("reputation");
                //TODO ajustar preferences
                String preferences = usersJSON.getString("preferences");

                ArrayList<Sale> favorites = new ArrayList<Sale>();
                JSONArray favoritesList =  usersJSON.getJSONArray("favorites");
                for(int j = 0; j < favoritesList.length(); j++){
                    JSONObject salesJSON = (JSONObject) favoritesList.get(j);

                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

                    String idSale = salesJSON.getString("_id");
                    String productId = salesJSON.getString("product");
                    String marketId = salesJSON.getString("market");
                    Double salePrice = salesJSON.getDouble("salePrice");
                    Double regularPrice = salesJSON.getDouble("regularPrice");
                    String expirationDate =  salesJSON.getString("expirationDate");
                    Date publicationDate = df.parse(salesJSON.getString("publicationDate"));
                    String authorId = salesJSON.getString("author");

                    List<Historic> historic = new ArrayList<Historic>();
                    JSONArray historicList =  salesJSON.getJSONArray("historic");
                    for(int k = 0; k < historicList.length(); k++){

                        double value = historicList.getJSONObject(k).getDouble("value");
                        Date date = df.parse(historicList.getJSONObject(k).getString("saleDate"));
                        int likeCount = historicList.getJSONObject(k).getInt("likeCount");
                        int dislikeCount = historicList.getJSONObject(k).getInt("dislikeCount");
                        int reportCount = historicList.getJSONObject(k).getInt("reportCount");

                        List<String> likeUsers = new ArrayList<String>();
                        JSONArray likeList =  historicList.getJSONObject(k).getJSONArray("likeUsers");
                        for(int l = 0; k < likeList.length(); l++){
                            likeUsers.add(likeList.getString(l));
                        }

                        List<String> dislikeUsers = new ArrayList<String>();
                        JSONArray dislikeList =  historicList.getJSONObject(k).getJSONArray("dislikeUsers");
                        for(int l = 0; l < dislikeList.length(); l++){
                            dislikeUsers.add(dislikeList.getString(l));
                        }
                        historic.add(new Historic(date, value, likeCount, dislikeCount, reportCount, likeUsers, dislikeUsers));
                    }

                    int likeCount = salesJSON.getInt("likeCount");
                    int dislikeCount = salesJSON.getInt( "dislikeCount");
                    int reportCount = salesJSON.getInt("reportCount");

                    ArrayList<String> likeUsers = new ArrayList<String>();
                    JSONArray likeList =  salesJSON.getJSONArray("likeUsers");
                    for(int k = 0; k < likeList.length(); k++){
                        likeUsers.add(likeList.getString(k));
                    }

                    ArrayList<String> reportUsers = new ArrayList<String>();
                    JSONArray reportList =  salesJSON.getJSONArray("likeUsers");
                    for(int k = 0; k < reportList.length(); k++){
                        reportUsers.add(reportList.getString(k));
                    }

                    ArrayList<String> dislikeUsers = new ArrayList<String>();
                    JSONArray dislikeList =  salesJSON.getJSONArray("dislikeUsers");
                    for(int k = 0; k < dislikeList.length(); k++){
                        dislikeUsers.add(dislikeList.getString(k));
                    }

                   // Sale
                    Sale sale = new Sale(idSale, productId, marketId, salePrice, regularPrice, expirationDate, publicationDate, authorId, 1, historic, likeCount, dislikeCount, reportCount, likeUsers, reportUsers, dislikeUsers);

                    favorites.add(sale);
                }


                this.user = new User(name, id,facebookId, email, image, createdAt, reputation, new ArrayList<String>(), favorites);



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
        if (user != null) {
            mListener.OnGetUserFinished(true, this.user);
            Log.d(TAG, user.toString());
        } else {
            mListener.OnGetUserFinished(false, this.user);

        }
    }

}
