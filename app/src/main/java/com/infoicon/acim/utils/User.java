package com.infoicon.acim.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.infoicon.acim.R;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by sumit on 4/2/17.
 */

public class User {

    private static User instance;
    private static SharedPreferences sharedPreferences;
    private static Context mContext;

    public static User getInstance(Context context) {
        mContext=context;
        if (instance == null) {
            synchronized (User.class) {
                if (instance == null) {
                    instance = new User();
                }
            }
        }
        sharedPreferences = context.getSharedPreferences(AppConstants.PREF_USER_INFO, MODE_PRIVATE);
        return instance;
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }

    public float getSize() {
        boolean tabletSize = mContext.getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {//check is tablet or phone
            return sharedPreferences.getFloat(AppConstants.SIZE,20 );

        }else{
            return sharedPreferences.getFloat(AppConstants.SIZE,15 );

        }
    }

    public void setSize(float size) {
        sharedPreferences
                .edit()
                .putFloat(AppConstants.SIZE, size)
                .commit();
    }

    public void saveBookmarkData(HashMap<String,String> data){
        Gson gson = new Gson();
        String hashMapString = gson.toJson(data);
        sharedPreferences.edit().putString("hashString", hashMapString).apply();
    }

    public HashMap<String, String> getBookmarkData(){
        HashMap<String, String> hashMap = new HashMap<>();
        String storedHashMapString = sharedPreferences.getString("hashString", "");
        if (!storedHashMapString.isEmpty()){
            java.lang.reflect.Type type = new TypeToken<HashMap<String, String>>(){}.getType();
            Gson gson = new Gson();
            if (gson.fromJson(storedHashMapString, type) != null){
                hashMap = gson.fromJson(storedHashMapString, type);
                return hashMap;
            }
        }
        return hashMap;
    }

}
