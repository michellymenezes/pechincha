package com.projeto1.projeto1;

/**
 * Created by rafaelle on 06/07/17.
 */

import android.content.Intent;
import android.graphics.Color;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.projeto1.projeto1.fragments.LoginFragment;
import com.projeto1.projeto1.fragments.MainFragment;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;
import java.util.Date;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LOGIN_ACTIVITY";


    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        if (!SharedPreferencesUtils.getToken(getBaseContext()).equals("")){
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(main);
        } else {
            FacebookSdk.sdkInitialize(getApplicationContext());
            callbackManager = CallbackManager.Factory.create();
            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {
                    token = newToken.getToken();
                }
            };

            profileTracker = new ProfileTracker() {
                @Override
                protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                    //nextActivity(newProfile);
                }
            };
            accessTokenTracker.startTracking();
            profileTracker.startTracking();

            LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
            FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
                @Override
                public void onSuccess(LoginResult loginResult) {
                    Profile profile = Profile.getCurrentProfile();
                    Toast.makeText(getApplicationContext(), "Entrando...", Toast.LENGTH_SHORT).show();

                    nextActivity(profile);
                }

                @Override
                public void onCancel() {
                }

                @Override
                public void onError(FacebookException e) {
                }
            };
            loginButton.registerCallback(callbackManager, callback);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Profile profile = Profile.getCurrentProfile();
        nextActivity(profile);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);

    }

    private void nextActivity(Profile profile){
        if(profile != null){
            SharedPreferencesUtils.setToken(getBaseContext(), token );
            Intent main = new Intent(LoginActivity.this, MainActivity.class);
            User user = new User(profile.getFirstName(), profile.getId(),"EMAIL",profile.getProfilePictureUri(200,200).toString(), SystemClock.uptimeMillis(), 0.0,new ArrayList<String>());
            Intent intent= new Intent(getBaseContext(), LoginActivity.class);
            intent.putExtra("USER", user);
            startActivity(main);
        }
    }
}