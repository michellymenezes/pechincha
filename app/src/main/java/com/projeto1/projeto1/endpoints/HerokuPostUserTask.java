package com.projeto1.projeto1.endpoints;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.SharedPreferencesUtils;
import com.projeto1.projeto1.listeners.GetUserListener;
import com.projeto1.projeto1.listeners.PostUserListener;
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
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by rafaelle on 06/07/17.
 */

public class HerokuPostUserTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_USER_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private User user;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    private PostUserListener mListener;

    public HerokuPostUserTask(User user, Context context, String endpoint, PostUserListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.mListener = mListener;
        this.user = user;

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        URL url;

        try {

            //Models
            //String name, String id, String email, String image, Long createdAt, String birthday, String gender, Double reputation, ArrayList<String> preferences

            /* BD
            user {
                userID: String,
                fullname: String,
                email: String,
                created_at: String,
                image: String,
                reputation: Double,
                preferences: [String],
            }*/

            String parameters = "facebookId=" + user.getFacebookId() + "&fullName=" + user.getName()+ "&username="+ user.getName() +
                    "&email=" + user.getEmail() + "&avatar=" + user.getImage() +"&created_at=" + user.getCreatedAt() +
                    "&reputation=" + user.getReputation() + "&preferences=" + user.getPreferences();

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

                Iterator<String> keys = jsonObject.keys();

                while (keys.hasNext()) {
                    String key = (String) keys.next();
                    if (key.equals("_id")) {
                        user.setId(jsonObject.getString("_id"));
                        SharedPreferencesUtils.setUser(context, user);
                        break;
                    }
                }

                conn.disconnect();
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
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        mListener.OnPostLoginFinished(isSuccessfulRegister);
    }
}
