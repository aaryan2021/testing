package com.infoicon.acim.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



/**
 * Created by sumit on 12/9/17.
 */

public class DataInsertionInDatabase {

    private static DataInsertionInDatabase instance;
    private static DbHelper dbHelper;
    private final Context myContext;
    private static final String TAG=DataInsertionInDatabase.class.getName();

    public static DataInsertionInDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (DataInsertionInDatabase.class) {
                if (instance == null) {
                    instance = new DataInsertionInDatabase(context);
                    dbHelper = DbHelper.getInstance(context);
                }
            }
        }
        return instance;
    }

    public DataInsertionInDatabase(Context context) {
        this.myContext = context;
    }


    /**
     * this method is used to insert data in ACIM_NOTES TABLE

     * @param notes_type
     * @param notes_title
     * @param notes_desc
     * @param timeCreated
     * */

    public boolean insertDataInNotesTable(String notes_type, String notes_title, String notes_desc, String timeCreated) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
       try {

           ContentValues values = new ContentValues();
           /*values.put(DbConstants.CHAPTER_ID, chapter_id);
           values.put(DbConstants.TOPIC_ID, topic_id);*/
           values.put(DbConstants.NOTES_TYPE,notes_type);
           values.put(DbConstants.NOTES_TITLE,notes_title);
           values.put(DbConstants.NOTES_DESC, notes_desc);
           values.put(DbConstants.TIME_CREATED, timeCreated);
           long value = database.insert(DbConstants.ACIM_NOTES_TABLE, null, values);

        Log.d(TAG,  DbConstants.ACIM_NOTES_TABLE + "data inserted "+value);
           database.close();
           if(value==-1){
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
     * this method is used to insert data in ACIM_NOTES TABLE

     * @param notes_type
     * @param notes_title
     * @param notes_desc
     * @param timeCreated
     * */
    public boolean updateDataInNotesTable(String notes_type, String notes_title, String notes_desc, String timeCreated){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.NOTES_TYPE,notes_type);
            values.put(DbConstants.NOTES_TITLE,notes_title);
            values.put(DbConstants.NOTES_DESC, notes_desc);
            values.put(DbConstants.TIME_CREATED, timeCreated);
            long value =  database.update(DbConstants.ACIM_NOTES_TABLE, values, DbConstants.TIME_CREATED+"="+timeCreated, null);
            // long value = database.insert(DbConstants.ACIM_TEXT_AFFIRMATION_TABLE, null, values);

            Log.d(TAG,  DbConstants.ACIM_NOTES_TABLE + "data updated "+value);
            database.close();
            if(value==-1){
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
     * this method is used to insert data in ACIM_TEXT_AFFIRMATION_TABLE
     * @param lesson_id
     * @param lesson_title
     * @param affirmation_text
     * @param timeCreated
     * */
    public boolean insertDataInTextAffirmationTable(String lesson_id,String lesson_title,String affirmation_text,String timeCreated){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.LESSON_ID,lesson_id);
            values.put(DbConstants.LESSON_TITLE,lesson_title);
            values.put(DbConstants.AFFIRMATION_TEXT, affirmation_text);
            values.put(DbConstants.TIME_CREATED, timeCreated);
            long value = database.insert(DbConstants.ACIM_TEXT_AFFIRMATION_TABLE, null, values);

            Log.d(TAG,  DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + "data inserted "+value);
            database.close();
            if(value==-1){
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
     * this method is used to insert data in ACIM_AUDIO_AFFIRMATION_TABLE
     * @param lesson_id
     * @param lesson_title
     * @param audio_path
     * @param timeCreated
     * */
    public boolean insertDataInAudioAffirmationTable(String lesson_id,String lesson_title,String audio_path,String duration,String timeCreated){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.LESSON_ID,lesson_id);
            values.put(DbConstants.LESSON_TITLE,lesson_title);
            values.put(DbConstants.AFFIRMATION_AUDIO_PATH, audio_path);
            values.put(DbConstants.AUDIO_DURATION,duration);
            values.put(DbConstants.TIME_CREATED, timeCreated);
            long value = database.insert(DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE, null, values);

            Log.d(TAG,  DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE + "data inserted "+value);
            database.close();
            if(value==-1){
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
     * this method is used to update data in ACIM_TEXT_AFFIRMATION_TABLE
     * @param lesson_id
     * @param lesson_title
     * @param affirmation_text
     * @param timeCreated
     * */
    public boolean updateDataInTextAffirmationTable(String lesson_id,String lesson_title,String affirmation_text,String timeCreated){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.LESSON_ID,lesson_id);
            values.put(DbConstants.LESSON_TITLE,lesson_title);
            values.put(DbConstants.AFFIRMATION_TEXT, affirmation_text);
            values.put(DbConstants.TIME_CREATED, timeCreated);
            long value =  database.update(DbConstants.ACIM_TEXT_AFFIRMATION_TABLE, values, DbConstants.TIME_CREATED+"="+timeCreated, null);
           // long value = database.insert(DbConstants.ACIM_TEXT_AFFIRMATION_TABLE, null, values);

            Log.d(TAG,  DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + "data updated "+value);
            database.close();
            if(value==-1){
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
     * this method is used to insert data in REMINDER_TABLE
     * @param lesson_id
     * @param lesson_title
     * @param start_time
     * @param end_time
     * @param reminder_type
     * @param reminder_text
     * @param text_affirmation_time_created
     * @param reminder_audio
     * @param audio_affirmation_time_created
     * @param status
     * @param timeCreated
     * */
    public boolean insertDataInReminderTable(String lesson_id,String lesson_title,String start_time,String end_time,String reminder_type,
                                             String repeat_interval,String reminder_text,String text_affirmation_time_created,String reminder_audio,String audio_affirmation_time_created,String status,String timeCreated){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(DbConstants.LESSON_ID,lesson_id);
            values.put(DbConstants.LESSON_TITLE,lesson_title);
            values.put(DbConstants.START_TIME, start_time);
            values.put(DbConstants.END_TIME,end_time);
            values.put(DbConstants.END_TIME,end_time);
            values.put(DbConstants.REMINDER_TYPE,reminder_type);
            values.put(DbConstants.REPEAT_INTERVAL,repeat_interval);
            values.put(DbConstants.REMINDER_TEXT,reminder_text);
            values.put(DbConstants.TEXT_AFFIRMATION_TIME_CREATED,text_affirmation_time_created);
            values.put(DbConstants.REMINDER_AUDIO,reminder_audio);
            values.put(DbConstants.AUDIO_AFFIRMATION_TIME_CREATED,audio_affirmation_time_created);
            values.put(DbConstants.REMINDER_STATUS,status);
            values.put(DbConstants.TIME_CREATED, timeCreated);
            long value = database.insert(DbConstants.REMINDER_TABLE, null, values);
            Log.d(TAG,  DbConstants.REMINDER_TABLE + "data inserted "+value);
            database.close();
            if(value==-1){
                Log.d(TAG,  DbConstants.REMINDER_TABLE + "data insertion failed "+value);
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
     * @param status
     * */
    public boolean updateReminderTable(String timeCreated,String status){

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.REMINDER_STATUS,status);

            long value =  database.update(DbConstants.REMINDER_TABLE, values, DbConstants.TIME_CREATED+"="+timeCreated, null);


            Log.d(TAG,  DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + "data updated "+value);
            database.close();
            if(value==-1){
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
     * @param lesson_id
     * @param lesson_title
     * @param start_time
     * @param end_time
     * @param reminder_type
     * @param reminder_text
     * @param text_affirmation_time_created
     * @param reminder_audio
     * @param audio_affirmation_time_created
     * @param status
     * @param timeCreated
     * */
    public boolean updateReminder(String lesson_id,String lesson_title,String start_time,String end_time,String reminder_type,
                                             String repeat_interval,String reminder_text,String text_affirmation_time_created,String reminder_audio,String audio_affirmation_time_created,String status,String timeCreated){
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        try {

            ContentValues values = new ContentValues();
            values.put(DbConstants.LESSON_ID,lesson_id);
            values.put(DbConstants.LESSON_TITLE,lesson_title);
            values.put(DbConstants.START_TIME, start_time);
            values.put(DbConstants.END_TIME,end_time);
            values.put(DbConstants.END_TIME,end_time);
            values.put(DbConstants.REMINDER_TYPE,reminder_type);
            values.put(DbConstants.REPEAT_INTERVAL,repeat_interval);
            values.put(DbConstants.REMINDER_TEXT,reminder_text);
            values.put(DbConstants.TEXT_AFFIRMATION_TIME_CREATED,text_affirmation_time_created);
            values.put(DbConstants.REMINDER_AUDIO,reminder_audio);
            values.put(DbConstants.AUDIO_AFFIRMATION_TIME_CREATED,audio_affirmation_time_created);
            values.put(DbConstants.REMINDER_STATUS,status);
           // values.put(DbConstants.TIME_CREATED, timeCreated);
            long value = database.update(DbConstants.REMINDER_TABLE,values, DbConstants.TIME_CREATED+"="+timeCreated, null);

            Log.d(TAG,  DbConstants.REMINDER_TABLE + "data updated "+value);
            database.close();
            if(value==-1){
                Log.d(TAG,  DbConstants.REMINDER_TABLE + "data updated failed "+value);
                return false;
            }
        }catch (Exception e){
            database.close();
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
