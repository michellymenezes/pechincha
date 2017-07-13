package com.projeto1.projeto1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import com.projeto1.projeto1.endpoints.HerokuGetSalesTask;
import com.projeto1.projeto1.fragments.LoginFragment;
import com.projeto1.projeto1.fragments.MainFragment;
import com.projeto1.projeto1.fragments.SaleDetailsFragment;
import com.projeto1.projeto1.listeners.MarketListener;
import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.listeners.SaleListener;
import com.projeto1.projeto1.models.Address;
import com.projeto1.projeto1.models.Localization;
import com.projeto1.projeto1.models.Market;
import com.projeto1.projeto1.models.Sale;
import com.projeto1.projeto1.models.Product;
import com.projeto1.projeto1.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements  NavigationView.OnNavigationItemSelectedListener, ProductListener,  MarketListener{

    private static final String TAG = "MAIN_ACTIVITY";
    private TextView info;
    private LoginButton loginButton;
    private MainFragment myMainFragment;
    private LoginFragment loginFragment;
    private CallbackManager mCallbackManager;
    private ArrayList<Product> products;
    private AppBarLayout mAppBarLayout;
    private ArrayList<Sale> sales;
    private HerokuGetSalesTask mTask;
    private User user;
    private Sale selectedSale;

    //Menu
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initMenu();

        myMainFragment = MainFragment.getInstance();
        loginFragment = LoginFragment.getInstance();

        if (SharedPreferencesUtils.getUser(getBaseContext()) == null) {
            initializeFacebookSdk();
            changeFragment(loginFragment, LoginFragment.TAG, true);

        } else {
            Log.d(TAG, "Already logged");
            changeFragment(myMainFragment, MainFragment.TAG, true);
            user = SharedPreferencesUtils.getUser(getBaseContext());
            Log.d(TAG, user.toString());

        }

        /** GET TODOS OS USUARIOS**/
        //HerokuGetUserTask getUserTask = new HerokuGetUserTask(String.format(getResources().getString(R.string.HEROKU_USER_ENDPOINT)),this, user);
        //getUserTask.execute();


        /**POST DE PROMOÇÕES**/
        /*HerokuPostSalesTask mTask = new HerokuPostSalesTask(new Sale(), getBaseContext(), String.format(getResources().getString(R.string.HEROKU_SALE_ENDPOINT)));
        mTask.execute(); */

        /** POST DE PRODUTOS**/
        /*
        HerokuPostProductsTask mAuthTask = new HerokuPostProductsTask("594z81z04zz96z0004z93980", "0",
                getBaseContext(),String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)));
        mAuthTask.execute(); */

        /** GET DE PRODUTOS **/
        /*

        HerokuGetProductsTask mAuthTask = new HerokuGetProductsTask(String.format(getResources().getString(R.string.HEROKU_PRODUCT_ENDPOINT)));
        mAuthTask.execute();
        */

        Address address = new Address("Vila Nova da Rainha","461", "ponto de cem reis", "Campina Grande", "PB", "Brasil", " ");
        Localization localization = new Localization(-7.2134805,-35.885104);

        //Market market = new Market(null,"Supermercados Ideal",address," ", "08.957.326/0001-13",localization);
        //HerokuPostMarketsTask marketsTask = new HerokuPostMarketsTask(market, getBaseContext(), String.format(getResources().getString(R.string.HEROKU_MARKET_ENDPOINT)), this);
        //marketsTask.execute();

        modifyActioonBar();

    }

    private void makingMenuClickable() {
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    private void initMenu() {

        mAppBarLayout = (AppBarLayout) findViewById(R.id.app_bar_layout);
        mAppBarLayout.setVisibility(View.VISIBLE);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.open, R.string.close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        makingMenuClickable();
        mToggle.setDrawerIndicatorEnabled(false);

        mToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(GravityCompat.START);
            }
        });
        mToggle.setHomeAsUpIndicator(R.drawable.ic_hamburguer);


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
            if (tag.equals(LoginFragment.TAG)) {
                mAppBarLayout.setVisibility(View.GONE);
            } else {
                mAppBarLayout.setVisibility(View.VISIBLE);
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





    // Deve ser implementado para dar ação aos itens do menu
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:
                changeFragment(MainFragment.getInstance(), MainFragment.TAG, false);
                break;

            case R.id.nav_config:
                Toast.makeText(getBaseContext(), R.string.not_ready, Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_about:
                Toast.makeText(getBaseContext(), R.string.not_ready, Toast.LENGTH_LONG).show();
                break;

            case R.id.nav_logout:
                initializeFacebookSdk();
                LoginManager.getInstance().logOut();
                SharedPreferencesUtils.setUser(this, null);
                changeFragment(loginFragment, LoginFragment.TAG, false);
                break;


        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
     if (mToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    public CallbackManager initializeFacebookSdk() {

        FacebookSdk.sdkInitialize(getApplicationContext());
        mCallbackManager = CallbackManager.Factory.create();

        return mCallbackManager;
    }


    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public void OnGetProductsReady(boolean ready, ArrayList<Product> products) {
        setProducts(products);
    }

    @Override
    public void OnPostProductFinished(boolean finished) {
    }

    @Override
    public void OnGetProductReady(boolean b, Product product) {

    }

    public ArrayList<Sale> getSales() {
        return sales;
    }

    public void setSales(ArrayList<Sale> sales) {
        this.sales = sales;
    }



    @Override
    public void OnGetMarketsReady(boolean ready, ArrayList<Market> products) {

    }

    @Override
    public void OnPostMarketsFinished(boolean finished) {

    }

    @Override
    public void OnGetMarketReady(boolean b, Market market) {

    }

}