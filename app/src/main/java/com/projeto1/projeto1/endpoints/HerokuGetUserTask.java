package com.projeto1.projeto1.endpoints;

import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.listeners.GetUserListener;
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
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


public class HerokuGetUserTask extends AsyncTask {

    private static final String TAG = "HEROKU_GET_USER_TASK";

    private User user;
    private User userToFind;

    private String responseMessage = "";
    private String endpoint;
    private GetUserListener mListener;


    public HerokuGetUserTask(String url, GetUserListener listener, User user) {
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


                user = new User(name, id,facebookId, email, image, createdAt, reputation, new ArrayList<String>());



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
        if (user != null) {
            mListener.OnGetUserFinished(true, this.user);
            Log.d(TAG, user.toString());
        } else {
            mListener.OnGetUserFinished(false, this.user);

        }
    }

}
