package com.infoicon.acim.database;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by sumit on 12/9/17.
 */

public class DbCreateTable {

    private SQLiteDatabase db;

    public DbCreateTable(SQLiteDatabase db) {
        this.db = db;
    }

    public void createTable() {

        String createACIMNotesTable = DbConstants.CREATE_TABLE + DbConstants.ACIM_NOTES_TABLE + "(" +
                DbConstants.NOTES_TYPE + DbConstants.TEXT + "," +
                DbConstants.NOTES_TITLE + DbConstants.TEXT + "," +
                DbConstants.NOTES_DESC + DbConstants.TEXT+ "," +
                DbConstants.TIME_CREATED + DbConstants.TEXT + ")";
        db.execSQL(createACIMNotesTable);

        String createTextAffirmationTable=DbConstants.CREATE_TABLE+ DbConstants.ACIM_TEXT_AFFIRMATION_TABLE + "(" +
                DbConstants.LESSON_ID + DbConstants.TEXT + "," +
                DbConstants.LESSON_TITLE + DbConstants.TEXT + ","+
                DbConstants.AFFIRMATION_TEXT + DbConstants.TEXT + ","+
                DbConstants.TIME_CREATED + DbConstants.TEXT + ")";
        db.execSQL(createTextAffirmationTable);


        String createAudioAffirmationTable=DbConstants.CREATE_TABLE+ DbConstants.ACIM_AUDIO_AFFIRMATION_TABLE + "(" +
                DbConstants.LESSON_ID + DbConstants.TEXT + "," +
                DbConstants.LESSON_TITLE + DbConstants.TEXT + ","+
                DbConstants.AFFIRMATION_AUDIO_PATH + DbConstants.TEXT + ","+
                DbConstants.AUDIO_DURATION + DbConstants.TEXT + ","+
                DbConstants.TIME_CREATED + DbConstants.TEXT + ")";
        db.execSQL(createAudioAffirmationTable);

        String createReminderTable=DbConstants.CREATE_TABLE+ DbConstants.REMINDER_TABLE + "(" +
                DbConstants.ID +" INTEGER PRIMARY KEY AUTOINCREMENT"+ ","+
                DbConstants.LESSON_ID + DbConstants.TEXT + "," +
                DbConstants.LESSON_TITLE + DbConstants.TEXT + ","+
                DbConstants.START_TIME + DbConstants.TEXT + ","+
                DbConstants.END_TIME + DbConstants.TEXT + ","+
                DbConstants.REMINDER_TYPE + DbConstants.TEXT + ","+
                DbConstants.REPEAT_INTERVAL + DbConstants.TEXT + ","+
                DbConstants.REMINDER_TEXT + DbConstants.TEXT + ","+
                DbConstants.TEXT_AFFIRMATION_TIME_CREATED + DbConstants.TEXT + ","+
                DbConstants.REMINDER_AUDIO + DbConstants.TEXT + ","+
                DbConstants.AUDIO_AFFIRMATION_TIME_CREATED + DbConstants.TEXT + ","+
                DbConstants.REMINDER_STATUS + DbConstants.TEXT + ","+
                DbConstants.TIME_CREATED + DbConstants.TEXT + ")";
        db.execSQL(createReminderTable);

        Log.e("TABLE CREATED", "DbCreateTable");
    }
}

