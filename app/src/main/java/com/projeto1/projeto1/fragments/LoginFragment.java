package com.projeto1.projeto1.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.projeto1.projeto1.MainActivity;
import com.projeto1.projeto1.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

/**
 * Created by rafaelle on 20/06/17.
 */

public class LoginFragment extends Fragment {

    public static final String TAG = "LOGIN_FRAGMENT";
    private View mView;
    private LoginButton mLoginButton;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment getInstance() {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_login, container, false);

        mLoginButton = (LoginButton) mView.findViewById(R.id.login_button);
        mLoginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email", "user_birthday", "user_friends"));
        
        login(mLoginButton);

        return mView;
    }

    private void login(LoginButton loginButton){
        CallbackManager callbackManager = ((MainActivity) getActivity()).initializeLogin();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG,
                        "User ID: "
                                + loginResult.getAccessToken().getUserId()
                                + "\n" +
                                "Auth Token: "
                                + loginResult.getAccessToken().getToken() +
                                "\n"
                );

                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                Log.v(TAG, response.toString());
                                // Application code
                                try {
                                    String id = object.getString("id");
                                    String email = object.getString("email");
                                    String name = object.getString("name");
                                    String gender = object.getString("gender");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                ((MainActivity) getActivity()).changeFragment(MainFragment.getInstance(), MainFragment.TAG, true);
                            }
                        }
                );

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                Log.d(TAG,"Login attempt canceled.");
            }

            @Override
            public void onError(FacebookException e) {
                Log.d(TAG,"Login attempt failed.");
            }
        });

    }




}
