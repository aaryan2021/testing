package com.infoicon.acim.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.infoicon.acim.bean.ReminderBean;
import com.infoicon.acim.database.DbDataDeleteFromDatabase;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.main.MainActivity;
import com.infoicon.acim.reminder.NotificationScheduler;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;

/**
 * Created by Sumit Singh on 17/06/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    String TAG = "AlarmReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub

        if (intent.getAction() != null && context != null) {
            if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
                // Set the alarm here.
                Log.d(TAG, "onReceive: BOOT_COMPLETED");
                //       LocalData localData = new LocalData(context);
                //  NotificationScheduler.setReminder(context, AlarmReceiver.class, localData.get_hour(), localData.get_min());

                DbGetDataFromDataBase dbGetDataFromDataBase=DbGetDataFromDataBase.getInstance(context);
                ArrayList<ReminderBean> reminderBeanArrayList=dbGetDataFromDataBase.getAllReminders();
                if(reminderBeanArrayList!=null){
                    if(reminderBeanArrayList.size()>0){
                        for(int i=0;i<reminderBeanArrayList.size();i++){
                            String status=reminderBeanArrayList.get(i).getStatus();
                            if(status.equals("true")){
                                NotificationScheduler.setReminder(context,AlarmReceiver.class,reminderBeanArrayList.get(i).getId(),reminderBeanArrayList.get(i).getLesson_id(),
                                        reminderBeanArrayList.get(i).getLesson_title(),reminderBeanArrayList.get(i).getStart_time(),reminderBeanArrayList.get(i).getEnd_time(),
                                        reminderBeanArrayList.get(i).getReminder_type(),reminderBeanArrayList.get(i).getRepeat_interval(),
                                        reminderBeanArrayList.get(i).getReminder_text(),reminderBeanArrayList.get(i).getReminder_audio(),reminderBeanArrayList.get(i).getStatus(),
                                        reminderBeanArrayList.get(i).getTimeCreated());
                            }
                        }
                    }
                }
                return;
            }
        } else {

            Log.d(TAG, "onReceive: ");
            //Trigger the notification
            int reminder_id=intent.getIntExtra(Keys.REMINDER_ID,0);
            String lesson_id = intent.getStringExtra(Keys.LESSON_ID);
            String lesson_title = intent.getStringExtra(Keys.LESSON_TITLE);
            String startTime = intent.getStringExtra(Keys.START_TIME);
            String endTime = intent.getStringExtra(Keys.END_TIME);
            String reminder_type = intent.getStringExtra(Keys.REMINDER_TYPE);
            String repeat_interval = intent.getStringExtra(Keys.REPEAT_INTERVAL);
            String text_affirmation = intent.getStringExtra(Keys.TEXT_AFFIRMATION);
            String audio_path = intent.getStringExtra(Keys.AUDIO_PATH);
            String status = intent.getStringExtra(Keys.STATUS);
            String timeCreated = intent.getStringExtra(Keys.TIME_CREATED);

            /**
             * method to compare reminder end date and time with current time if reminder end time is greater than current date and time then we send reminder
             * otherwise cancel the reminder and delete this form database
             *
             * */
            boolean b=UtilsMethods.compareEndDate(timeCreated,endTime);

           /* (Context context, Class<?> cls,int reminder_id, String lesson_id, String lesson_title, String startTime, String endTime, String reminder_type,
                    String repeat_interval, String text_affirmation, String audio_path, String status, String timeCreated)*/

            if (b){
                NotificationScheduler.showNotification(context, MainActivity.class,reminder_id,
                        lesson_id,lesson_title,reminder_type,text_affirmation,audio_path,startTime,endTime,timeCreated,repeat_interval,status);

            }else{

               /* NotificationScheduler.cancelReminder(context,AlarmReceiver.class,reminder_id,lesson_id,lesson_title,startTime,endTime,reminder_type
                ,repeat_interval,text_affirmation,audio_path,status,timeCreated);*/
                NotificationScheduler.cancelReminder(context,reminder_id);
                DbDataDeleteFromDatabase  dbDataDeleteFromDatabase=DbDataDeleteFromDatabase.getInstance(context);
                dbDataDeleteFromDatabase.deleteReminder(reminder_id);
            }

        }
    }
}


