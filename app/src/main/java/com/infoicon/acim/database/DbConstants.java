package com.infoicon.acim.database;

/**
 * Created by sumit on 12/9/17.
 */

public class DbConstants {

    public static String DATABASE_NAME="acim";
    public static final int DATABASE_VERSION = 1;
    public static final String ACIM_NOTES_TABLE = "notesTable";
    public static final String ID="id";
    public static final String CHAPTER_ID="chapter_id";
    public static final String TOPIC_ID="topic_id";
    public static final String NOTES_TYPE="type";
    public static final String NOTES_TITLE="title";
    public static final String NOTES_DESC="description";
    public static final String TIME_CREATED ="timeCreated";
    public static final String NOTES_TYPE_TEXT="text_notes";
    public static final String NOTES_TYPE_WORKBOOK="workbook_notes";
    public static final String NOTES_TYPE_TEACHER_MANUAL="teacher_manual_notes";
    public static final String TEXT = " TEXT";
    public static final String INTEGER = " int";
    public static final String BOOLEAN = " boolean";

    public static String CREATE_TABLE = "create table ";

    public static final String ACIM_TEXT_AFFIRMATION_TABLE="textAffirmationTable";
    public static final String  LESSON_ID="lesson_id";
    public static final String LESSON_TITLE="lesson_title";
    public static final String AFFIRMATION_TEXT="affirmation_text";



    public static final String ACIM_AUDIO_AFFIRMATION_TABLE="audioAffirmationTable";
    public static final String AFFIRMATION_AUDIO_PATH="audio_path";
    public static final String AUDIO_DURATION="audio_duration";


    public static final String REMINDER_TABLE="reminder_table";

    public static final String START_TIME="start_time";
    public static final String END_TIME="end_time";
    public static final String REMINDER_TYPE="reminder_type";
    public static final String REPEAT_INTERVAL="repeat_interval";
    public static final String REMINDER_TEXT="reminder_text";
    public static final String REMINDER_AUDIO="reminder_audio";
    public static final String REMINDER_STATUS="reminder_status";
    public static final String TEXT_AFFIRMATION_TIME_CREATED="text_affirmation_time_created";
    public static final String AUDIO_AFFIRMATION_TIME_CREATED="audio_affirmation_time_created";

}
