package com.projeto1.projeto1;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

/**
 * Created by rafaelle on 06/07/17.
 */

public class SharedPreferencesUtils {
    public static final String TAG = "SHARED_PREFERENCES";
    protected SharedPreferencesUtils() {}
    final static String SEPARATOR = "_";

    public static String getToken(Context context) {
        SharedPreferences settings = context.getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
        return settings.getString("USER_ID", "");
    }

    public static void setToken(Context context, String token){
        SharedPreferences settings = context.getSharedPreferences("USER_TOKEN", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("USER_TOKEN", token);
        editor.commit();
    }

}
