package com.infoicon.acim.utils;

import android.Manifest;
import android.net.Uri;
import android.os.Environment;



import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by sumit on 16/8/17.
 */

public class AppConstants {


    public static final String TAB_HOME  = "home";
    public static final String TAB_TEXT  = "text";
    public static final String TAB_WORKBOOK  = "workbook";
    public static final String TAB_MANUAL  = "manual";
    public static final String TAB_SETTINGS  = "settings";
    public static String currentSelectedTabTag = "";
    public static final String TEXT_INTRO="text_intro";

    public static final String TEXT_FOREWARD="text_forward";
    public static final String WORKBOOK_PART1_INTRO="workbook_part1_intro";
    public static final String WORKBOOK_PART2_INTRO="workbook_part2_intro";
    public static final String WORKBOOK_EPILOGUE="workbook_epilogue";
    public static final String MANUAL_TEACHER_INTRO="manual_teacher_intro";


    /*shared preference related keys*/
    public static final String PREF_USER_INFO="user_info";
    public static final String PREF_FCM_TOKEN="fcm_token";
    public static final String SIZE="size";

    public static ArrayList<AllSelectdTabs> allSelectedTabs=new ArrayList<>();
    public static final SimpleDateFormat sdf=new SimpleDateFormat("dd MMM, HH:mm a");
    public static final String READ_STORAGE_PERMISSION= Manifest.permission.READ_EXTERNAL_STORAGE;
    public static final String WRITE_STORAGE_PERMISSION= Manifest.permission.WRITE_EXTERNAL_STORAGE;
    public static final int REQUEST_READ_STORAGE_CODE=100;
    public static final int REQUEST_WRITE_STORAGE_CODE=110;

    public static final String RECORD_AUDIO= Manifest.permission.RECORD_AUDIO;
    public static final int REQUEST_RECORD_AUDIO_CODE=200;


    public static final String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/ACIM";
    public static final String TAG = "ACIM";


    /*time in milisecs*/
    public static int FORWARD_BACKWARD_TIME=10000;

    public static int MIN_SIZE=10;


    public static final String PROJECT_AUDIO_BOOKS_URL="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=JE7385ASFY27J";
    public static final String PROJECT_DAILY_LESSONS_URL="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=3XFW3RL7M8DKY";
    public static final String PROJECT_TRANSLATION_URL="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=N42HFWCLMWKZ8";
    public static final String PROJECT_GENERAL_ADMINISTRATION_URL="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=FB9LB3ZZQSWNS";
    public static final String PROJECT_AUDIO_BOOK_TRANSLATION="audio_books_and_translation";
    public static final String PROJECT_DAILY_LESSON_MAILING="daily_lesson_mailing";


    public static int CLICKED_POS=0;
    public static int CLICKED_POS_GROUP=0;
    public static  int  PART_NUM=1;
    public static final String MANAGE_AFFIRMATION="manage_affirmation";
    public static final String MANAGE_NOTES="manage_notes";
    public static final String ADD_NOTES="add_notes";
    public static final String ADD_AFFIRMATION="add_affirmation";
    public static final String TEXT_REMINDER="text_reminder";
    public static final String AUDIO_REMINDER="audio_reminder";
    public static final String MANAGE_REMINDER="manage_reminder";
    public static final String SCHEDULE_REMINDER="schedule_reminder";
    public static final String SEARCH_URL="http://cocreatingclarity.org/JCIM5/searchapp.htm";
    public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");


    public static final String TEXT_CHAPTER="text_chapter";
    public static final String TEXT_CHAPTER_CONTENT="text_chapter_content";
    public static final String TEXT_CHAPTER_CONTENT_DESCRIPTION="text_chapter_content_description";
    public static final String MANUAL_CHAPTER_CONTENT_DESCRIPTION="manual_chapter_content_description";

    public static final String WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID="workbook_chapter_content_description";
    public static final String WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID="workbook_chapter_child_id_description";
    public static final String WORKBOOK_CHAPTER_PART_TExXTESCRIPTION_ID="workbook_chapter_part_text_description";
}
