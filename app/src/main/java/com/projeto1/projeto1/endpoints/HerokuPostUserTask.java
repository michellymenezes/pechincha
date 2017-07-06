package com.projeto1.projeto1.endpoints;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.LoginListener;
import com.projeto1.projeto1.SaleListener;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.User;

/**
 * Created by rafaelle on 06/07/17.
 */

public class HerokuPostUserTask extends AsyncTask<Void, Void, Boolean> {
    private static final String TAG = "HEROKU_POST_SALES_TASK";

    private final String ENDPOINT_ADDRESS;
    private final Context context;

    private String responseMessage = "";
    private User user;

    private boolean isSuccessfulRegister;
    private Object mAuthTask;

    private LoginListener mListener;

    public HerokuPostUserTask(User user, Context context, String endpoint, LoginListener mListener) {
        ENDPOINT_ADDRESS = endpoint;
        this.context = context;
        this.mListener = mListener;
        this.user = user;

        mListener.OnPostLoginFinished(true);

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }
}
