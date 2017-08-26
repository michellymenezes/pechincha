package com.projeto1.projeto1.endpoints;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.projeto1.projeto1.listeners.ProductListener;
import com.projeto1.projeto1.models.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


public class HerokuGetProductByBarcodeTask extends AsyncTask {
    private static final String TAG = "HEROKU_GET_PRODUCT_BY_BARCODE_TASK";

    private Product product;
    private String responseMessage = "";
    private String endpoint;
    private ProductListener mListener;


    public HerokuGetProductByBarcodeTask(String url, ProductListener listener) {
        endpoint = url;
        product = null;
        mListener = listener;
    }

    @SuppressLint("LongLogTag")
    @Override
    protected Boolean doInBackground(Object... params) {
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

                JSONArray productsJSON = new JSONArray((responseMessage));

                if(productsJSON.length() != 0) {
                    JSONObject productJSON = new JSONObject(String.valueOf(productsJSON.getJSONObject(0)));
                    Log.d(TAG, String.valueOf(productsJSON));

                    String id = productJSON.getString("_id");
                    String name = productJSON.getString("name");
                    String brand = productJSON.getString("brand");
                    String descripition = productJSON.getString("description");
                    String image = productJSON.getString("image");
                    String code = productJSON.getString("barCode");
                    String category = productJSON.getString("category");
                    String subcategory = productJSON.getString("subcategory");

                    product = new Product(id, name, brand, descripition, image, code, category, subcategory);


                    Log.d(TAG, "Product: " + product.toString());
                }
                else {
                    product = null;
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

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        if (product != null) {
            mListener.OnGetProductByBarcodeReady(true, this.product);
        } else {
            mListener.OnGetProductByBarcodeReady(false, this.product);
        }
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;

    }
}
