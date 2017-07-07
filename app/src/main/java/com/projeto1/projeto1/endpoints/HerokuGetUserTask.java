package com.projeto1.projeto1.endpoints;

import android.os.AsyncTask;

import com.projeto1.projeto1.listeners.LoginListener;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;

/**
 * Created by rafaelle on 07/07/17.
 */

public class HerokuGetUserTask extends AsyncTask {

    private static final String TAG = "HEROKU_GET_USER_TASK";

    private ArrayList<User> users;
    private User userToFind;

    private String responseMessage = "";
    private String endpoint;
    private LoginListener mListener;


    public HerokuGetUserTask(String url, LoginListener listener) {
        endpoint = url;
        users = new ArrayList<User>();
        mListener = listener;
    }

    public HerokuGetUserTask(String url, LoginListener listener, User user) {
        endpoint = url;
        userToFind = user;
        mListener = listener;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        if (userToFind == null){
            return  getAllUsers();
        } else {
            return FindUser();
        }
    }


    private User getAllUsers(){
        return null;
    }

    private User FindUser(){
        return null;
    }
}
