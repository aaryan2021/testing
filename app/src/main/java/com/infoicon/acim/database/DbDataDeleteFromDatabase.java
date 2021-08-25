package com.infoicon.acim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


/**
 * Created by sumit on 12/9/17.
 */


public class DbDataDeleteFromDatabase {

    private static final String TAG = DbDataDeleteFromDatabase.class.getName();
    private static DbDataDeleteFromDatabase instance;
    private static DbHelper dbHelper;
    private final Context myContext;

    public DbDataDeleteFromDatabase(Context context) {
        this.myContext = context;
    }

    public static DbDataDeleteFromDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (DbDataDeleteFromDatabase.class) {
                if (instance == null) {
                    instance = new DbDataDeleteFromDatabase(context);
                    dbHelper = DbHelper.getInstance(context);
                }
            }
        }
        return instance;
    }

    /**
     * method to delete selected row from table
     * @param timeCreated
     * */

   public boolean deleteNotes(String timeCreated) {
       SQLiteDatabase database = dbHelper.getWritableDatabase();
       try {

           int value = database.delete(DbConstants.ACIM_NOTES_TABLE, DbConstants.TIME_CREATED + " = ?", new String[]{timeCreated});

           Log.d(TAG,  DbConstants.ACIM_NOTES_TABLE + "data deleted "+value);
           database.close();
           if(value==0){
               return false;
           }
       }catch (Exception e){
           database.close();
           e.printStackTrace();
           return false;
       }
       return true;

    }

    /** method to delete selected affirmation from table
     * @param timeCreated*/

    public boolean deleteTextAffirmation(String timeCreated) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            int value = database.delete(DbConstants.ACIM_TEXT_AFFIRMATION_TABLE, DbConstants.TIME_CREATED + " = ?", new String[]{timeCreated});

            Log.d(TAG,  DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + "data deleted "+value);
            database.close();
            if(value==0){
                return false;
            }
        }catch (Exception e){
            database.close();
            e.printStackTrace();
            return false;
        }
        return true;

    }


    /** method to delete selected affirmation from table
     * @param timeCreated*/

    public boolean deleteAudioAffirmation(String timeCreated) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            int value = database.delete(DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE, DbConstants.TIME_CREATED + " = ?", new String[]{timeCreated});

            Log.d(TAG,  DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE + "data deleted "+value);
            database.close();
            if(value==0){
                return false;
            }
        }catch (Exception e){
            database.close();
            e.printStackTrace();
            return false;
        }
        return true;

    }

    /** method to delete reminder from reminder table
     * @param id*/

    public boolean deleteReminder(int  id) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            int value = database.delete(DbConstants.REMINDER_TABLE, DbConstants.ID + " = ?", new String[]{id+""});

            Log.d(TAG,  DbConstants.REMINDER_TABLE + "data deleted "+value);
            database.close();
            if(value==0){
                return false;
            }
        }catch (Exception e){
            database.close();
            e.printStackTrace();
            return false;
        }
        return true;

    }
    /**
     * this method is used to update data in REMINDER_TABLE
     * @param timeCreated
     * */
    public boolean deleteReminder(String timeCreated) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            int value = database.delete(DbConstants.REMINDER_TABLE, DbConstants.TIME_CREATED + " = ?", new String[]{timeCreated});

            Log.d(TAG, DbConstants.REMINDER_TABLE + "data deleted " + value);
            database.close();
            if (value == 0) {
                return false;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
