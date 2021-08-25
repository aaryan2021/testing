package com.infoicon.acim.reminder;

import android.app.AlarmManager;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.infoicon.acim.R;
import com.infoicon.acim.main.MainActivity;
import com.infoicon.acim.receiver.AlarmReceiver;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by sumit on 20/06/17.
 */

public class NotificationScheduler {
 //   public static final int DAILY_REMINDER_REQUEST_CODE = 100;
    public static final String TAG = "NotificationScheduler";



    /**method to schedule a new reminder
     **@param context
     * @param cls
     * @param reminder_id
     * @param lesson_id
     * @param lesson_title
     * @param startTime
     * @param endTime
     * @param reminder_type
     * @param repeat_interval
     * @param text_affirmation
     * @param audio_path
     * @param status
     * @param timeCreated
     * */
    public static void setReminder(Context context, Class<?> cls,int reminder_id, String lesson_id, String lesson_title, String startTime, String endTime, String reminder_type,
                                   String repeat_interval, String text_affirmation, String audio_path, String status, String timeCreated) {
     //   Calendar calendar = Calendar.getInstance();
        String AM_PM = startTime.substring(6, 8);
        int hour = Integer.parseInt(startTime.substring(0, 2));
        int min = Integer.parseInt(startTime.substring(3, 5));

        Calendar setcalendar = Calendar.getInstance();

        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);

        if (AM_PM.equalsIgnoreCase("PM")) {
            if(hour==12){
                hour=0;
            }
                setcalendar.set(Calendar.HOUR_OF_DAY, hour+12);

          //  setcalendar.set(Calendar.AM_PM, Calendar.PM);
        } else {
            setcalendar.set(Calendar.HOUR_OF_DAY, hour);
       //     setcalendar.set(Calendar.AM_PM, Calendar.AM);
        }


        // cancel already scheduled reminders
        //      cancelReminder(context,cls);

    /*    if (setcalendar.before(calendar))
            setcalendar.add(Calendar.DATE, 1);*/

        // Enable a receiver

    /*    ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
                PackageManager.DONT_KILL_APP);*/

        Log.e("reminder_id",reminder_id+"");
        Intent intent1 = new Intent(context, cls);
        intent1.putExtra(Keys.REMINDER_ID,reminder_id);
        intent1.putExtra(Keys.LESSON_ID, lesson_id);
        intent1.putExtra(Keys.LESSON_TITLE, lesson_title);
        intent1.putExtra(Keys.START_TIME, startTime);
        intent1.putExtra(Keys.END_TIME, endTime);
        intent1.putExtra(Keys.REMINDER_TYPE, reminder_type);
        intent1.putExtra(Keys.REPEAT_INTERVAL, repeat_interval);
        intent1.putExtra(Keys.TEXT_AFFIRMATION, text_affirmation);
        intent1.putExtra(Keys.AUDIO_PATH, audio_path);
        intent1.putExtra(Keys.STATUS, status);
        intent1.putExtra(Keys.TIME_CREATED, timeCreated);

        int intervalInMillis = Integer.parseInt(repeat_interval) * 60 * 1000;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,
                reminder_id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar cal=Calendar.getInstance();
//        am.setExact(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis()+intervalInMillis, pendingIntent);
        if(cal.getTimeInMillis()<setcalendar.getTimeInMillis())
        {
            if (Build.VERSION.SDK_INT >= 23) {
                am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), pendingIntent);
                //am.setAlarmClock(new AlarmManager.AlarmClockInfo(setcalendar.getTimeInMillis(),pendingIntent), pendingIntent);

            }
            else if
                    (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), pendingIntent);
               // manager.setExact(AlarmManager.RTC_WAKEUP, alarmPeriodicTime, pendingIntent);
            }
            else {
                am.set(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), pendingIntent);
               // manager.set(AlarmManager.RTC_WAKEUP, alarmPeriodicTime, pendingIntent);
            }
           // am.setExact(AlarmManager.RTC_WAKEUP,
            // setcalendar.getTimeInMillis(), pendingIntent);
        }else{

            /*calculation of time to repeat the reminder on a certain interval
            * */
            long time=setcalendar.getTimeInMillis();
            while (time<cal.getTimeInMillis()) {
               time = time + intervalInMillis;
           }
            if (Build.VERSION.SDK_INT >= 23) {
               // am.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, time, pendingIntent);
                am.setAlarmClock(new AlarmManager.AlarmClockInfo(time,pendingIntent), pendingIntent);
            } else if (Build.VERSION.SDK_INT >= 19) {
                am.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            } else {
                am.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            }
          //  am.setExact(AlarmManager.RTC_WAKEUP, time, pendingIntent);
            Log.e("time",time+"");
        }
        Log.e("start_time",setcalendar.getTimeInMillis()+"");
        Log.e("current",cal.getTimeInMillis()+"");


        Log.e("schedule_reminder","inside_schedule_reminder");
    }


    /**method to update the reminder
    **@param context
     * @param cls
     * @param reminder_id
     * @param lesson_id
     * @param lesson_title
     * @param startTime
     * @param endTime
     * @param reminder_type
     * @param repeat_interval
     * @param text_affirmation
     * @param audio_path
     * @param status
     * @param timeCreated
     * */
    public static void updateReminder(Context context, Class<?> cls,int reminder_id, String lesson_id, String lesson_title, String startTime, String endTime, String reminder_type,
                                   String repeat_interval, String text_affirmation, String audio_path, String status, String timeCreated) {
        //   Calendar calendar = Calendar.getInstance();
        String AM_PM = startTime.substring(6, 8);
        int hour = Integer.parseInt(startTime.substring(0, 2));
        int min = Integer.parseInt(startTime.substring(3, 5));

        Calendar setcalendar = Calendar.getInstance();
        setcalendar.set(Calendar.HOUR_OF_DAY, hour);
        setcalendar.set(Calendar.MINUTE, min);
        setcalendar.set(Calendar.SECOND, 0);

        if (AM_PM.equalsIgnoreCase("PM")) {
            setcalendar.set(Calendar.AM_PM, Calendar.PM);
        } else {
            setcalendar.set(Calendar.AM_PM, Calendar.AM);
        }
        Log.e("reminder_id",reminder_id+"");
        Intent intent1 = new Intent(context, cls);
        intent1.putExtra(Keys.REMINDER_ID,reminder_id);
        intent1.putExtra(Keys.LESSON_ID, lesson_id);
        intent1.putExtra(Keys.LESSON_TITLE, lesson_title);
        intent1.putExtra(Keys.START_TIME, startTime);
        intent1.putExtra(Keys.END_TIME, endTime);
        intent1.putExtra(Keys.REMINDER_TYPE, reminder_type);
        intent1.putExtra(Keys.REPEAT_INTERVAL, repeat_interval);
        intent1.putExtra(Keys.TEXT_AFFIRMATION, text_affirmation);
        intent1.putExtra(Keys.AUDIO_PATH, audio_path);
        intent1.putExtra(Keys.STATUS, status);
        intent1.putExtra(Keys.TIME_CREATED, timeCreated);

        int intervalInMillis = Integer.parseInt(repeat_interval) * 60 * 1000;


        Calendar cal=Calendar.getInstance();
        PendingIntent newPendingIntent = PendingIntent.getBroadcast(context, reminder_id, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if(cal.getTimeInMillis()<setcalendar.getTimeInMillis())
        {
            am.setExact(AlarmManager.RTC_WAKEUP, setcalendar.getTimeInMillis(), newPendingIntent);
        }else{
            long time=setcalendar.getTimeInMillis();

             while (time<cal.getTimeInMillis()){

                 time=time+intervalInMillis;
             }

            am.setExact(AlarmManager.RTC_WAKEUP, time, newPendingIntent);
            Log.e("time",time+"");
        }
        Log.e("start_time",setcalendar.getTimeInMillis()+"");
        Log.e("current",cal.getTimeInMillis()+"");
        Log.e("update_reminder","inside_schedule_reminder");
    }



    /**method to cancel the reminder
     **@param context
     * @param cls
     * @param reminder_id
     * @param lesson_id
     * @param lesson_title
     * @param startTime
     * @param endTime
     * @param reminder_type
     * @param repeat_interval
     * @param text_affirmation
     * @param audio_path
     * @param status
     * @param timeCreated
     * */

    public static void cancelReminder(Context context, Class<?> cls,int reminder_id, String lesson_id, String lesson_title, String startTime, String endTime, String reminder_type,
                                      String repeat_interval, String text_affirmation, String audio_path, String status, String timeCreated) {
        // Disable a receiver
        Log.e("reminder_id",reminder_id+"");
       /*   ComponentName receiver = new ComponentName(context, cls);
        PackageManager pm = context.getPackageManager();

      pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);*/

        Intent intent1 = new Intent(context, cls);
        intent1.putExtra(Keys.REMINDER_ID,reminder_id);
        intent1.putExtra(Keys.LESSON_ID, lesson_id);
        intent1.putExtra(Keys.LESSON_TITLE, lesson_title);
        intent1.putExtra(Keys.START_TIME, startTime);
        intent1.putExtra(Keys.END_TIME, endTime);
        intent1.putExtra(Keys.REMINDER_TYPE, reminder_type);
        intent1.putExtra(Keys.REPEAT_INTERVAL, repeat_interval);
        intent1.putExtra(Keys.TEXT_AFFIRMATION, text_affirmation);
        intent1.putExtra(Keys.AUDIO_PATH, audio_path);
        intent1.putExtra(Keys.STATUS, status);
        intent1.putExtra(Keys.TIME_CREATED, timeCreated);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder_id, intent1, PendingIntent.FLAG_NO_CREATE);
        AlarmManager am = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if(pendingIntent != null){
            am.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.e("cancel_reminder","cancel_reminder");
        }

    }
    /**method to cancel the reminder
     **@param context
     * @param reminder_id

     * */


    public static void cancelReminder(Context context,int reminder_id) {
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, reminder_id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        if(pendingIntent != null){
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.e("cancel_reminder","cancel_reminder");
        }

    }

    /**
     * method to show notification
     * @param context
     * @param cls
     * @param reminder_id
     * @param lesson_id
     * @param lesson_title
     * @param reminder_type
     * @param text_affirmation
     * @param audio_path
     * */


    public static void showNotification(Context context, Class<?> cls, int reminder_id,String lesson_id, String lesson_title,  String reminder_type, String text_affirmation, String audio_path,
                                        String startTime,String endTime,String timeCreated,String repeat_interval,String status) {


        Log.e("showNotification", "showNotification reminder_id=" + reminder_id);
        Uri alarmSound = null;
        if (reminder_type.equals(AppConstants.AUDIO_REMINDER)) {
            if (audio_path.length() > 0) {
                File file = new File(audio_path);
                if (file.exists()) {
                    alarmSound = Uri.parse(audio_path);
                }
            }
            text_affirmation = "Audio Affirmation";
        }

        if (alarmSound == null) {
            alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }

       /* AudioManager mobilemode = (AudioManager)context.getSystemService(Context.AUDIO_SERVICE);
        switch (mobilemode.getRingerMode()) {
            case AudioManager.RINGER_MODE_SILENT:
                Log.i("MyApp","Silent mode");

                mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mobilemode.setStreamVolume(AudioManager.STREAM_RING,mobilemode.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                break;
            case AudioManager.RINGER_MODE_VIBRATE:
                Log.i("MyApp","Vibrate mode");
                mobilemode.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                mobilemode.setStreamVolume(AudioManager.STREAM_RING,mobilemode.getStreamMaxVolume(AudioManager.STREAM_RING),0);
                break;
            case AudioManager.RINGER_MODE_NORMAL:
                Log.i("MyApp","Normal mode");
                break;
        }*/

        Intent notificationIntent = new Intent(context, cls);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        notificationIntent.putExtra(Keys.FROM_NOTIFICATION, "from_notification");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(cls);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(reminder_id, PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }


       /* NotificationCompat.Builder builder =
                new  NotificationCompat.bui.Builder(context, channelId);

   */       NotificationCompat.Builder mBuilder = new NotificationCompat.
                Builder(context, channelId);
        Notification notification = mBuilder.setContentTitle(lesson_title)
                .setContentText(text_affirmation)
                .setAutoCancel(true)
                .setSound(alarmSound)
                .setSmallIcon(R.drawable.reminder_detactive)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                        R.mipmap.app_icon))
                .setContentIntent(pendingIntent).build();



        Random random=new Random();
        int id=random.nextInt(10000);
        Log.e("notification_id",id+"");
        notificationManager.notify(id, notification);
        //  notificationManager.notify((int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE), notification);



        /* reminder repeat is not possible in setExact method of alarm manager
        * so we need to set the alarm again to repeat this
        * untill it reaches the end time
        * */

        boolean b = UtilsMethods.compareEndDate(timeCreated, endTime);
        if (b) {
            NotificationScheduler.cancelReminder(context, reminder_id);
            NotificationScheduler.setReminder(context, AlarmReceiver.class, reminder_id, lesson_id, lesson_title, startTime, endTime,
                    reminder_type, repeat_interval, text_affirmation, audio_path, status, timeCreated);
        }
    }
}
