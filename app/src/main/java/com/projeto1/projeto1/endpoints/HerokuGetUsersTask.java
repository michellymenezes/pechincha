package com.projeto1.projeto1.endpoints;

import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.listeners.UserListener;
import com.projeto1.projeto1.models.User;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;


/**
 * Created by rafaelle on 07/07/17.
 */

public class HerokuGetUsersTask extends AsyncTask {

    private static final String TAG = "HEROKU_GET_USERS_TASK";

    private ArrayList<User> users;
    private User userToFind;

    private String responseMessage = "";
    private String endpoint;
    private UserListener mListener;


    public HerokuGetUsersTask(String url, UserListener listener) {
        endpoint = url;
        users = new ArrayList<User>();
        mListener = listener;
    }

    public HerokuGetUsersTask(String url, UserListener listener, User user) {
        endpoint = url;
        userToFind = user;
        mListener = listener;
        users = new ArrayList<User>();
    }

    @Override
    protected Boolean doInBackground(Object[] params) {
            return  getAllUsers();

    }

    private boolean getAllUsers(){
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
                JSONArray usersJSON = new JSONArray(responseMessage);
                Log.d(TAG, String.valueOf(usersJSON));


                for (int i = 0; i < usersJSON.length(); i++) {
                    if ( usersJSON.getJSONObject(i).has("facebookId")){
                        //Models
                        //String name, String id, String email, String image, Long createdAt, String birthday, String gender, Double reputation, ArrayList<String> preferences

                        /* BD
                        user {
                            _id: String,
                                    fullname: String,
                                    email: String,
                                    created_at: String,
                                    image: String,
                                    reputation: Double,
                                    preferences: [String],
                        }*/
                        String id = usersJSON.getJSONObject(i).getString("_id");
                        String facebookId = usersJSON.getJSONObject(i).getString("facebookId");
                        String name = usersJSON.getJSONObject(i).getString("fullName");
                        String email = usersJSON.getJSONObject(i).getString("email");
                        String createdAt = usersJSON.getJSONObject(i).getString("createdAt");
                        String image = usersJSON.getJSONObject(i).getString("avatar");
                        Double reputation = usersJSON.getJSONObject(i).getDouble("reputation");
                        //TODO ajustar preferences
                        String preferences = usersJSON.getJSONObject(i).getString("preferences");

                        ArrayList<String> favorites = new ArrayList<String>();
                        JSONArray favoritesList =  usersJSON.getJSONObject(i).getJSONArray("favorites");
                        for(int j = 0; j < favoritesList.length(); j++){

                            favorites.add(favoritesList.get(j).toString());
                        }

                        // users.add(new User(name, id,faceoockId, email, image, createdAt, reputation, new ArrayList<String>()));

                        users.add(new User(name, id,facebookId, email, image, createdAt, reputation, new ArrayList<String>(), favorites));

                     }

                }

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

    //TODO modificar checagem para facebookID
    private boolean FindUser(){
        if (!users.isEmpty()){
            for (User user: users){
                if (user.getFacebookId().equals(userToFind.getFacebookId())){
                    this.userToFind = user;
                    return true;
                }
            }
        }

        return false;

    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (userToFind != null) {
            Boolean findUser = FindUser();
            mListener.OnGetUserFinished(findUser, this.userToFind);
            Log.d(TAG, "Encontrou usuário: " + String.valueOf(findUser));
        } else {
            mListener.OnGetAllUsersFinished((users != null), this.users);

            /*
            if (users != null){
                mListener.OnGetAllUsersFinished(true, this.users);

            } else {
                mListener.OnGetAllUsersFinished(false, this.users);
            } */

        }
    }

}
