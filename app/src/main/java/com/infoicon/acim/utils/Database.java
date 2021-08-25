package com.infoicon.acim.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Database {
    Context context;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor dbeditor;

    public Database(Context context) {
        this.context = context;
        sharedPreferences=context.getSharedPreferences("",0);
        dbeditor=sharedPreferences.edit();
    }

    public void setstatus(String status){
        dbeditor.putString("Status",status);
        dbeditor.commit();
    }
    public  String getStatus(){
        return sharedPreferences.getString("Status","1");
    }
}
