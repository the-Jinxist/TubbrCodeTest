package com.example.tubbrcodetest.repo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.tubbrcodetest.ui.MainActivity;

public class SharedprefsRepo {

    private static SharedPreferences getInstance(Activity activity){
        return activity.getSharedPreferences("tokens", Context.MODE_PRIVATE);
    }

    public static  String getAccessToken(Activity activity){
        Log.d("Stuff", "getAccessToken: " + getInstance(activity).getString("access_token", ""));
        return getInstance(activity).getString("access_token", "");
    }

    public static  Boolean isLoggedIn(Activity activity){
        //If the user is logged in, their user token will have been saved
        String value = getAccessToken(activity);
        return !value.isEmpty();
    }

    public static Boolean putAccessToken(Activity activity, String accessToken){
        SharedPreferences.Editor editor = getInstance(activity).edit();
        editor.putString("access_token", accessToken);
        return editor.commit();
    }

    public static Boolean clear(Activity activity){
        SharedPreferences.Editor editor = getInstance(activity).edit();
        editor.putString("access_token", "");
        return editor.commit();
    }

    public static Boolean logout(Activity activity){
        SharedPreferences.Editor editor = getInstance(activity).edit();
        editor.putString("access_token", "");

        activity.startActivity(new Intent(activity, MainActivity.class));
        activity.finish();

        return editor.commit();
    }

}
