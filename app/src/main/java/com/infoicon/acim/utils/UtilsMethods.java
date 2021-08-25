package com.infoicon.acim.utils;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.database.DbGetDataFromDataBase;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by sumit on 11/8/17.
 */

public class UtilsMethods {


    public static Typeface getTypeFaceFutura(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "futura_medium.ttf");
    }

    public static Typeface getTypeFaceMontserrat(Context context) {
        return Typeface.createFromAsset(context.getAssets(), "Sanchezregular.otf");
    }

    /**
     * method to replace fragment
     * @param mActivity FragmentActivity
     * @param fragment pass the fragment which you want to replace
     * */
  /*  public static void replaceFragment(FragmentActivity mActivity, Fragment fragment){
        AllSelectdTabs  allSelectdTabs=new AllSelectdTabs();
        allSelectdTabs.setSelectedTabs(AppConstants.currentSelectedTabTag);
        AppConstants.allSelectedTabs.add(allSelectdTabs);
        MainActivity.tabFragments.add(fragment);
        MainActivity.fragmentsStack.put(AppConstants.currentSelectedTabTag, MainActivity.tabFragments);
        mActivity.getSupportFragmentManager().beginTransaction().replace(R.id.frame_container, fragment).commit();
    }
*/

    /**
     * method to replace fragment
     *
     * @param mActivity FragmentActivity
     * @param fragment  pass the fragment which you want to replace
     */
    public static void replaceFragment(FragmentActivity mActivity, Fragment fragment) {
        if (mActivity != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
        }

    }

    /**
     * method to add fragment
     *
     * @param mActivity FragmentActivity
     * @param fragment  pass the fragment which you want to replace
     */
    public static void addFragment(FragmentActivity mActivity, Fragment fragment) {
        if (mActivity != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.add(R.id.frame_container, fragment).addToBackStack(null).commit();
        }

    }

    /**
     * method to replace fragment
     *
     * @param mActivity FragmentActivity
     * @param fragment  pass the fragment which you want to replace
     */
    public static void replaceFragmentWithBackStack(FragmentActivity mActivity, Fragment fragment) {
        if (mActivity != null) {
            FragmentManager fragmentManager = mActivity.getSupportFragmentManager();
            for (int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left);
            fragmentTransaction.replace(R.id.frame_container, fragment).addToBackStack(null).commit();
        }
    }


    /**
     * show simple message to bottom bar in app
     */
    public static void showSimpleMessageBottom(Activity activity, String message) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.WHITE);
        sbView.setBackgroundColor(ContextCompat.getColor(activity, R.color.semi_transparent));
        textView.setMaxLines(2);
        snackbar.show();
    }

    /**
     * showing message in toast
     */
    public static void showToast(Context context, String message) {
        Toast toast=Toast.makeText(context,message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * Method  to open Alert/Dialog if Server Request is failed
     */
    public static void serverRequestError(Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.message_server_alert));
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }


    /**
     * Method  to open Alert/Dialog if Server Request is failed
     */
    public static void serverRequestError(Context context, String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {


            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    /**
     * Method  to open Alert/Dialog when Server Request is TimeOut
     */
    public static void serverRequestTimeout(Context context) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.app_name));
        alertDialogBuilder.setMessage(context.getResources().getString(R.string.message_server_alert));
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    /**
     * method to parse the note added date
     */
    public static String parseNotesCreatedDate(String date) {
        String formattedDate = "";
        /*  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
          sdf.setTimeZone(TimeZone.getTimeZone("UTC"));*/

        // SimpleDateFormat output = new SimpleDateFormat("dd MMM, HH:mm a");
        //   output.setTimeZone(TimeZone.getDefault());

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(date));

        formattedDate = AppConstants.sdf.format(calendar.getTime());
        if (formattedDate.contains("a.m.")) {
            formattedDate = formattedDate.replace("a.m.", "AM");
        } else if (formattedDate.contains("p.m.")) {
            formattedDate = formattedDate.replace("p.m.", "PM");
        }
        return formattedDate;
    }

    /*method to check dynamic permission*/
    public static boolean hasPermissions(Context context, String permission) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permission != null) {

            if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }

        }
        return true;
    }

    public static void promptSettings(final Activity mActivity, String title, @Nullable String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                goToSettings(mActivity);
            }
        });
        builder.setNegativeButton("Not Now", null);
        builder.show();
    }

    public static void goToSettings(Activity mActivity) {
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + mActivity.getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(myAppSettings);
    }


    /**
     * method to convert millis sec time into mm:ss
     *
     * @param millis
     */
    public static String getAudioDuration(long millis) {
        String time = String.format("%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis),
                TimeUnit.MILLISECONDS.toSeconds(millis) -
                        TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
        return time;
    }

    public static void refreshStorage(Context mContext, File file) {
        MediaScannerConnection.scanFile(mContext,
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
                    }
                });
    }

    /**
     * Method for hiding soft keyboard
     */
    public static void hideSoftKeyboard(Activity mActivity) {
        if (mActivity == null) {
            return;
        }
        try {
            InputMethodManager inputManager = (InputMethodManager) mActivity
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            View focusedView = mActivity.getCurrentFocus();

            if (focusedView != null) {
                inputManager.hideSoftInputFromWindow(mActivity.getCurrentFocus()
                        .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * set dynamic height of ListView
     *
     * @param mListView
     */
    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    /**
     * method to open custom chrome
     *
     * @param context
     * @param url     pass the url which need to be open
     */

    public static void openCustomChrome(Context context, String url) {
        // Use a CustomTabsIntent.Builder to configure CustomTabsIntent.
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
// set toolbar color and/or setting custom actions before invoking build()
// Once ready, call CustomTabsIntent.Builder.build() to create a CustomTabsIntent
        CustomTabsIntent customTabsIntent = builder.build();
// and launch the desired Url with CustomTabsIntent.launchUrl()

        customTabsIntent.launchUrl(context, Uri.parse(url));

    }


    /**
     * method to compare dates
     *
     * @param prevDate
     */
    public static boolean compareDate(String prevDate) {
        boolean b = false;

        Date date1 = null, date2 = null;
        try {
            Date date = new Date(Long.parseLong(prevDate));
            String s = AppConstants.SDF.format(date);
            date1 = AppConstants.SDF.parse(s);
            date = new Date(System.currentTimeMillis());
            String s1 = AppConstants.SDF.format(date);
            date2 = AppConstants.SDF.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("date1 : " + AppConstants.SDF.format(date1));
        System.out.println("date2 : " + AppConstants.SDF.format(date2));

        if (date1.compareTo(date2) > 0) {
            System.out.println("Date1 is after Date2");
            b = false;
        } else if (date1.compareTo(date2) < 0) {
            System.out.println("Date1 is before Date2");
            b = false;
        } else if (date1.compareTo(date2) == 0) {
            System.out.println("Date1 is equal to Date2");
            b = true;

        }
        return b;
    }

    public static int getYear(String date) {
        Date parsed = new Date();
        try {
            parsed = AppConstants.SDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);
        return cal.get(Calendar.YEAR);
    }

    public static int getMonth(String date) {
        Date parsed = new Date();
        try {
            parsed = AppConstants.SDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);
        return cal.get(Calendar.MONTH);
    }

    public static int getDay(String date) {
        Date parsed = new Date();
        try {
            parsed = AppConstants.SDF.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);

        return cal.get(Calendar.DAY_OF_MONTH);
    }


    /**
     * method to compare reminder end time and date  with current date and time
     * if reminder end date is less than the current date then we return true and fire notifications
     *
     * @param reminderEndDate
     * @param reminderEndTime
     */
    public static boolean compareEndDate(String reminderEndDate, String reminderEndTime) {

        boolean b = false;

        SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
        Date reminderDate = null, currentDate = null;
        try {
            Date date = new Date(Long.parseLong(reminderEndDate));
            String s = SDF.format(date);
            reminderDate = SDF.parse(s);


            date = new Date(System.currentTimeMillis());
            String s1 = sdf.format(date);
            currentDate = sdf.parse(s1);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String AM_PM = reminderEndTime.substring(6, 8);
        int hour = Integer.parseInt(reminderEndTime.substring(0, 2));
        int min = Integer.parseInt(reminderEndTime.substring(3, 5));

        Calendar reminderCal = Calendar.getInstance();
        reminderCal.setTime(reminderDate);

        if (AM_PM.equalsIgnoreCase("PM")) {
            reminderCal.set(Calendar.AM_PM, Calendar.PM);
            if (hour == 12) {
                hour = 0;
            }
            hour = hour + 12;
        } else {
            reminderCal.set(Calendar.AM_PM, Calendar.AM);
        }
        reminderCal.set(Calendar.HOUR_OF_DAY, hour);
        reminderCal.set(Calendar.MINUTE, min);

        Calendar newCalender = Calendar.getInstance();
        newCalender.setTime(currentDate);


        //System.out.println("date1 : " + SDF.format(reminderDate));
        //  System.out.println("date2 : " + SDF.format(currentDate));

        if (reminderCal.after(newCalender)) {
            System.out.println("date is greater than reference that.");
            b = true;
        } else if (reminderCal.before(newCalender)) {
            System.out.println("date is lesser than reference that.");
            b = false;
        } else {
            System.out.println("date is equal to reference that.");
            b = false;
        }

        return b;
    }

    /**
     * show alert dialog when you don't have any affirmation
     *
     * @param context
     */

    public static void scheduleReminderAlertDialog(Context context, String message) {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(context);
        alertDialogBuilder.setTitle(context.getResources().getString(R.string.alert_caps));
        alertDialogBuilder.setMessage(message);
        // set positive button: Yes message
        alertDialogBuilder.setPositiveButton(context.getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        // show alert
        alertDialog.show();
    }

    /**
     * method to check whether an affirmation is stored in database or not
     *
     * @param context
     */
    public static boolean isUserHaveAnAffirmation(Context context) {
        DbGetDataFromDataBase dbGetDataFromDataBase = DbGetDataFromDataBase.getInstance(context);
        ArrayList<TextAffirmationBean> textAffirmationBeanArrayList = dbGetDataFromDataBase.getAllTextAffirmation();
        ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList = dbGetDataFromDataBase.getAllAudioAffirmation();
        if (textAffirmationBeanArrayList != null) {
            if (textAffirmationBeanArrayList.size() > 0) {
                return true;
            }
        }

        if (audioAffirmationBeanArrayList != null) {
            if (audioAffirmationBeanArrayList.size() > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * method to check whether an affirmation is stored in database or not
     *
     * @param context
     */
    public static boolean isLessonHaveAnAffirmation(Context context, String lesson_id) {
        DbGetDataFromDataBase dbGetDataFromDataBase = DbGetDataFromDataBase.getInstance(context);
        ArrayList<TextAffirmationBean> textAffirmationBeanArrayList = dbGetDataFromDataBase.getTextAffirmation(lesson_id);
        ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList = dbGetDataFromDataBase.getAudioAffirmation(lesson_id);
        if (textAffirmationBeanArrayList != null) {
            if (textAffirmationBeanArrayList.size() > 0) {
                return true;
            }
        }

        if (audioAffirmationBeanArrayList != null) {
            if (audioAffirmationBeanArrayList.size() > 0) {
                return true;
            }
        }

        return false;
    }

    /**
     * method to get time interval
     */

    public static int getTimeDifference(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);

        Date date1=null,date2=null;
        try {
            date1 = simpleDateFormat.parse(startTime);
            date2 = simpleDateFormat.parse(endTime);
            long difference = date2.getTime() - date1.getTime();
            int  days = (int) (difference / (1000 * 60 * 60 * 24));
            int   hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int    min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            if(hours>0){
                min=min+hours*60;
            }

            System.out.print("Hours==="+hours+" mins=="+min);
            return min;
        } catch (ParseException e) {
            e.printStackTrace();
        }



        //      Log.i("======= Hours", " :: " + hours);
        return 0;
    }

    /**
     * method to set the orientation in landscape mode if device is tablet
     * @param activity */
    public  static void checkTablet(Activity activity){
        boolean tabletSize = activity.getResources().getBoolean(R.bool.isTablet);
        if (tabletSize) {//check is tablet or phone
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }else{
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }

    public static void getToast(Context activity,String message){
        Toast toast=Toast.makeText(activity,message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
