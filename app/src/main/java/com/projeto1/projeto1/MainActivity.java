package com.projeto1.projeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;

import com.projeto1.projeto1.endpoints.HerokuGetProductsTask;
import com.projeto1.projeto1.endpoints.HerokuPostProductsTask;
import com.projeto1.projeto1.fragments.LoginFragment;
import com.projeto1.projeto1.fragments.MainFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MAIN_ACTIVITY";
    private TextView info;
    private LoginButton loginButton;
    private MainFragment myMainFragment;
    private LoginFragment loginFragment;
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        myMainFragment = MainFragment.getInstance();
        loginFragment = LoginFragment.getInstance();

        if (AccessToken.getCurrentAccessToken() == null){
            initializeLogin();
            changeFragment(loginFragment, LoginFragment.TAG, true);
        } else {
            Log.d(TAG, "Already logged");
            changeFragment(myMainFragment, MainFragment.TAG, true);
        }

        HerokuPostProductsTask mAuthTask = new HerokuPostProductsTask("594z81z04zz96z0004z93980", "0",
                getBaseContext(),String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)));
        mAuthTask.execute();
        /*
        HerokuGetProductsTask mAuthTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)));
        mAuthTask.execute();
        */

        modifyActioonBar();

    }

    public void changeFragment(Fragment frag, String tag, boolean saveInBackstack) {

        try {
            FragmentManager manager = getSupportFragmentManager();
            //fragment not in back stack, create it.
            FragmentTransaction transaction = manager.beginTransaction();


            transaction.replace(R.id.content_layout, frag, tag);

            if (saveInBackstack) {
                Log.d(TAG, "Change Fragment: addToBackTack " + tag);
                transaction.addToBackStack(tag);
            } else {
                Log.d(TAG, "Change Fragment: NO addToBackTack");
            }
            transaction.commit();
            // custom effect if fragment is already instanciated

        } catch (IllegalStateException exception) {
            Log.w(TAG, "Unable to commit fragment, could be activity as been killed in background. " + exception.toString());
        }
    }

    @Override
    public void onBackPressed() {
        int fragments = getSupportFragmentManager().getBackStackEntryCount();
        if (fragments == 1) {
            finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//        setContentView(R.layout.test);
    }

    /**
     * Inicia as definições da ActionBar para esse fragment
     */
    private void modifyActioonBar() {
        android.support.v7.app.ActionBar mActionbar = (this.getSupportActionBar());
        mActionbar.setTitle("");
        mActionbar.setElevation(0);
        mActionbar.setDisplayHomeAsUpEnabled(true);//if true displays the left menu
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.bag_button) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public CallbackManager initializeLogin(){

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();
        return mCallbackManager;
    }



}