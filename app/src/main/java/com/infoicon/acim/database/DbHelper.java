package com.infoicon.acim.database;

import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sumit on 12/9/17.
 */

public class DbHelper extends SQLiteOpenHelper {

    private static DbHelper instance;
    private final Context myContext;
    private SQLiteDatabase database;
    /**
     * Given database name and version code.
     */

    public DbHelper(Context context) {
        super(context,  DbConstants.DATABASE_NAME,null, DbConstants.DATABASE_VERSION);
        this.myContext = context;
    }


    public static DbHelper getInstance(Context context) {
        if(instance == null) {
            synchronized(DbHelper.class) {
                if(instance == null) {
                    instance = new DbHelper(context);
                }
            }
        }
        return instance;
    }



    /**
     * Class DbCreateTable and its method createTable use for create all table related to this project.
     */


    @Override
    public void onCreate(SQLiteDatabase db) {
        new DbCreateTable(db).createTable();

    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
