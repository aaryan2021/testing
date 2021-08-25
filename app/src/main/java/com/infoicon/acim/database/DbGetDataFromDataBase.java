package com.infoicon.acim.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.infoicon.acim.receiver.AlarmReceiver;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.Notes;
import com.infoicon.acim.bean.ReminderBean;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.reminder.NotificationScheduler;
import com.infoicon.acim.utils.UtilsMethods;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by sumit on 12/9/17.
 */


public class DbGetDataFromDataBase {

    private static DbGetDataFromDataBase instance;
    private static DbHelper dbHelper;
    private final Context myContext;

    public DbGetDataFromDataBase(Context context)
    {
        this.myContext = context;
    }

    public static DbGetDataFromDataBase getInstance(Context context)
    {
        if (instance == null)
        {
            synchronized (DbGetDataFromDataBase.class) {
                if (instance == null) {
                    instance = new DbGetDataFromDataBase(context);
                    dbHelper = DbHelper.getInstance(context);
                }
            }
        }
        return instance;
    }


    /**
     * method to get all notes form ACIM_NOTES_TABLE
     */
    public ArrayList<Notes> getNotes() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_NOTES_TABLE;
            cursor = database.rawQuery(q, null);
            ArrayList<Notes> notesArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    Notes notes = new Notes();
                /*notes.setChapter_id(cursor.getString(cursor.getColumnIndex(DbConstants.CHAPTER_ID)));
                notes.setTopic_id(cursor.getString(cursor.getColumnIndex(DbConstants.TOPIC_ID)));*/
                    notes.setNotes_type(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_TYPE)));
                    notes.setNotes_title(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_TITLE)));
                    notes.setNotes_desc(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_DESC)));
                    notes.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                    notesArrayList.add(notes);

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(notesArrayList.size()>1){
                    Collections.reverse(notesArrayList);
                }
                return notesArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * method to get all notes form ACIM_NOTES_TABLE
     *
     * @param notes_type
     */
    public ArrayList<Notes> getNotes(String notes_type) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_NOTES_TABLE + " where " + DbConstants.NOTES_TYPE + "=" + "'" + notes_type + "'";
            ;
            cursor = database.rawQuery(q, null);
            ArrayList<Notes> notesArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    Notes notes = new Notes();
          /*          notes.setChapter_id(cursor.getString(cursor.getColumnIndex(DbConstants.CHAPTER_ID)));
                    notes.setTopic_id(cursor.getString(cursor.getColumnIndex(DbConstants.TOPIC_ID)));*/
                    notes.setNotes_type(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_TYPE)));
                    notes.setNotes_title(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_TITLE)));
                    notes.setNotes_desc(cursor.getString(cursor.getColumnIndex(DbConstants.NOTES_DESC)));
                    notes.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                    notesArrayList.add(notes);

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(notesArrayList.size()>1){
                    Collections.reverse(notesArrayList);
                }
                return notesArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * method to get all text affirmation of a particular lesson
     *
     * @param lesson_id
     */
    public ArrayList<TextAffirmationBean> getTextAffirmation(String lesson_id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + " where " + DbConstants.LESSON_ID + "=" + "'" + lesson_id + "'";
            cursor = database.rawQuery(q, null);
            ArrayList<TextAffirmationBean> textAffirmationBeanArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    TextAffirmationBean textAffirmationBean = new TextAffirmationBean();
                    textAffirmationBean.setLesson_id(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)));
                    textAffirmationBean.setLesson_title(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)));
                    textAffirmationBean.setAffirmation_text(cursor.getString(cursor.getColumnIndex(DbConstants.AFFIRMATION_TEXT)));
                    textAffirmationBean.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                    textAffirmationBeanArrayList.add(textAffirmationBean);

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(textAffirmationBeanArrayList.size()>1){
                    Collections.reverse(textAffirmationBeanArrayList);
                }
                return textAffirmationBeanArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * method to get all text affirmation
     */
    public ArrayList<TextAffirmationBean> getAllTextAffirmation() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_TEXT_AFFIRMATION_TABLE;
            cursor = database.rawQuery(q, null);
            ArrayList<TextAffirmationBean> textAffirmationBeanArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    TextAffirmationBean textAffirmationBean = new TextAffirmationBean();
                    textAffirmationBean.setLesson_id(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)));
                    textAffirmationBean.setLesson_title(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)));
                    textAffirmationBean.setAffirmation_text(cursor.getString(cursor.getColumnIndex(DbConstants.AFFIRMATION_TEXT)));
                    textAffirmationBean.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                    textAffirmationBeanArrayList.add(textAffirmationBean);

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(textAffirmationBeanArrayList.size()>1){
                    Collections.reverse(textAffirmationBeanArrayList);
                }
                return textAffirmationBeanArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * method to get all audio affirmation of a particular lesson
     *
     * @param lesson_id
     */
    public ArrayList<AudioAffirmationBean> getAudioAffirmation(String lesson_id) {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE + " where " + DbConstants.LESSON_ID + "=" + "'" + lesson_id + "'";
            cursor = database.rawQuery(q, null);
            ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    AudioAffirmationBean audioAffirmationBean = new AudioAffirmationBean();
                    String audio_path = cursor.getString(cursor.getColumnIndex(DbConstants.AFFIRMATION_AUDIO_PATH));
                    File file = new File(audio_path);
                    if (file.exists()) {
                        audioAffirmationBean.setLesson_id(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)));
                        audioAffirmationBean.setLesson_name(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)));
                        audioAffirmationBean.setAudio_path(audio_path);
                        Log.e("audio_path", audio_path);
                        audioAffirmationBean.setDuration(cursor.getString(cursor.getColumnIndex(DbConstants.AUDIO_DURATION)));
                        audioAffirmationBean.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                        audioAffirmationBeanArrayList.add(audioAffirmationBean);
                    }


                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(audioAffirmationBeanArrayList.size()>1){
                    Collections.reverse(audioAffirmationBeanArrayList);
                }
                return audioAffirmationBeanArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }

    /**
     * method to get all audio affirmation
     */
    public ArrayList<AudioAffirmationBean> getAllAudioAffirmation() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE;
            cursor = database.rawQuery(q, null);
            ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    AudioAffirmationBean audioAffirmationBean = new AudioAffirmationBean();
                    String audio_path = cursor.getString(cursor.getColumnIndex(DbConstants.AFFIRMATION_AUDIO_PATH));
                    File file = new File(audio_path);
                    if (file.exists()) {
                        audioAffirmationBean.setLesson_id(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)));
                        audioAffirmationBean.setLesson_name(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)));
                        audioAffirmationBean.setAudio_path(audio_path);
                        Log.e("audio_path", audio_path);
                        audioAffirmationBean.setDuration(cursor.getString(cursor.getColumnIndex(DbConstants.AUDIO_DURATION)));
                        audioAffirmationBean.setTime_created(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                        audioAffirmationBeanArrayList.add(audioAffirmationBean);
                    }

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(audioAffirmationBeanArrayList.size()>1){
                    Collections.reverse(audioAffirmationBeanArrayList);
                }
                return audioAffirmationBeanArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * method to get all reminders
     */

    public ArrayList<ReminderBean> getAllReminders() {
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.REMINDER_TABLE;
            cursor = database.rawQuery(q, null);
            ArrayList<ReminderBean> reminderBeanArrayList = new ArrayList<>();

            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                do {
                    ReminderBean reminderBean = new ReminderBean();

                  int id=cursor.getInt(cursor.getColumnIndex(DbConstants.ID));
                    Log.e("id", id+"");
                    if (UtilsMethods.compareEndDate(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)),cursor.getString(cursor.getColumnIndex(DbConstants.END_TIME)))) {
                        reminderBean.setId(id);
                        reminderBean.setLesson_id(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)));
                        reminderBean.setLesson_title(cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)));
                        reminderBean.setStart_time(cursor.getString(cursor.getColumnIndex(DbConstants.START_TIME)));
                        reminderBean.setEnd_time(cursor.getString(cursor.getColumnIndex(DbConstants.END_TIME)));
                        reminderBean.setReminder_type(cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_TYPE)));
                        reminderBean.setRepeat_interval(cursor.getString(cursor.getColumnIndex(DbConstants.REPEAT_INTERVAL)));
                        reminderBean.setReminder_text(cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_TEXT)));
                        reminderBean.setReminder_text_time_created(cursor.getString(cursor.getColumnIndex(DbConstants.TEXT_AFFIRMATION_TIME_CREATED)));
                        reminderBean.setReminder_audio(cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_AUDIO)));
                        reminderBean.setReminder_audio_time_created(cursor.getString(cursor.getColumnIndex(DbConstants.AUDIO_AFFIRMATION_TIME_CREATED)));
                        reminderBean.setTimeCreated(cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));
                        reminderBean.setStatus(cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_STATUS)));
                        reminderBeanArrayList.add(reminderBean);
                    }else{
                        DbDataDeleteFromDatabase  dbDataDeleteFromDatabase=DbDataDeleteFromDatabase.getInstance(myContext);
                        dbDataDeleteFromDatabase.deleteReminder(id);
                  /*      NotificationScheduler.cancelReminder(myContext,AlarmReceiver.class,id,cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_ID)),
                                cursor.getString(cursor.getColumnIndex(DbConstants.LESSON_TITLE)),cursor.getString(cursor.getColumnIndex(DbConstants.START_TIME)),cursor.getString(cursor.getColumnIndex(DbConstants.END_TIME))
                        ,cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_TYPE)),cursor.getString(cursor.getColumnIndex(DbConstants.REPEAT_INTERVAL)),
                                cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_TEXT)),cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_AUDIO)),cursor.getString(cursor.getColumnIndex(DbConstants.REMINDER_STATUS)),cursor.getString(cursor.getColumnIndex(DbConstants.TIME_CREATED)));*/
                        NotificationScheduler.cancelReminder(myContext,id);

                    }
                    //}

                } while (cursor.moveToNext());
                cursor.close();
                database.close();
                if(reminderBeanArrayList.size()>1){
                      Collections.reverse(reminderBeanArrayList);
                }
                return reminderBeanArrayList;
            }
        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return null;
    }


    /**
     * method to get reminder id
     *
     * @param timeCreated
     */
    public int getReminderId(String timeCreated) {
        int reminder_id = -1;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.REMINDER_TABLE + " where " + DbConstants.TIME_CREATED + "=" + "'" + timeCreated + "'";
            cursor = database.rawQuery(q, null);
            int count = cursor.getCount();

            if (count > 0 && cursor.moveToFirst()) {
                reminder_id = cursor.getInt(cursor.getColumnIndex(DbConstants.ID));
            }
            cursor.close();
            database.close();


        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return reminder_id;
    }


    /**
     * method to get all reminder id for a particular written affirmation
     *
     * @param text_affirmation_time_created
     */
    public ArrayList<Integer> getReminderIdArrayForWrittenAffirmation(String text_affirmation_time_created) {
        ArrayList<Integer> arrayList=null;
        int reminder_id = -1;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.REMINDER_TABLE + " where " + DbConstants.TEXT_AFFIRMATION_TIME_CREATED + "=" + "'" + text_affirmation_time_created + "'";
            cursor = database.rawQuery(q, null);
            int count = cursor.getCount();
             if(count>0){
                 arrayList=new ArrayList<>();
             }
            if (count > 0 && cursor.moveToFirst()) {
                reminder_id = cursor.getInt(cursor.getColumnIndex(DbConstants.ID));
                arrayList.add(reminder_id);
            }
            cursor.close();
            database.close();


        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return arrayList;
    }


    /**
     * method to get all reminder id for a particular audio affirmation
     *
     * @param audio_affirmation_time_created
     */
    public ArrayList<Integer> getReminderIdArrayForAudioAffirmation(String audio_affirmation_time_created) {
        ArrayList<Integer> arrayList=null;
        int reminder_id = -1;
        SQLiteDatabase database = dbHelper.getReadableDatabase();
        Cursor cursor = null;
        try {

            String q = "SELECT * from " + DbConstants.REMINDER_TABLE + " where " + DbConstants.AUDIO_AFFIRMATION_TIME_CREATED + "=" + "'" + audio_affirmation_time_created + "'";
            cursor = database.rawQuery(q, null);
            int count = cursor.getCount();
            if(count>0){
                arrayList=new ArrayList<>();
            }
            if (count > 0 && cursor.moveToFirst()) {
                reminder_id = cursor.getInt(cursor.getColumnIndex(DbConstants.ID));
                arrayList.add(reminder_id);
            }
            cursor.close();
            database.close();


        } catch (Exception e) {
            database.close();
            e.printStackTrace();
        }
        if (cursor != null) {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return arrayList;
    }

}
