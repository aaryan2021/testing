package com.infoicon.acim.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.infoicon.acim.database.DbDataDeleteFromDatabase;
import com.infoicon.acim.receiver.AlarmReceiver;
import com.infoicon.acim.R;
import com.infoicon.acim.bean.ReminderBean;
import com.infoicon.acim.database.DataInsertionInDatabase;
import com.infoicon.acim.reminder.NotificationScheduler;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.customviews.CustomTextViewBold;

import java.util.ArrayList;

/**
 * Created by sumit on 10/10/17.
 */

public class ReminderListAdapter extends RecyclerView.Adapter<ReminderListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private ArrayList<ReminderBean> reminderBeanArrayList;


    public ReminderListAdapter(FragmentActivity activity, ArrayList<ReminderBean> reminderBeanArrayList) {
        this.activity = activity;
        this.reminderBeanArrayList = reminderBeanArrayList;

    }

    /* public void setListener(RecyclerViewClickListener itemListener){
         this.itemListener=itemListener;
     }*/

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reminder, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        String reminder_type=reminderBeanArrayList.get(position).getReminder_type();
        if(reminder_type.equals(AppConstants.TEXT_REMINDER)){
            viewHolder.textViewReminderTitle.setText(reminderBeanArrayList.get(position).getReminder_text());
        }else{
            viewHolder.textViewReminderTitle.setText("Audio Affirmation Reminder ");
        }
        viewHolder.textViewLesson.setText(reminderBeanArrayList.get(position).getLesson_title());
        int repeat_interval=Integer.parseInt(reminderBeanArrayList.get(position).getRepeat_interval());
        String repeat_time;

        if(repeat_interval<=59){
            repeat_time=repeat_interval+" mins";
        }else {
            int hours=(repeat_interval)/60;
            if(repeat_interval==60){
                repeat_time=hours+ " hour";
            }else {
                repeat_time=hours+ " hours";
            }
        }
        viewHolder.textViewReminderInfo.setText("Repeat every  "+ repeat_time+ " between "+reminderBeanArrayList.get(position).getStart_time()+" to "+reminderBeanArrayList.get(position).getEnd_time()
           );

     /*   viewHolder.textViewReminderInfo.setText("Start : "+reminderBeanArrayList.get(position).getStart_time()+" | "+"Stop: "+reminderBeanArrayList.get(position).getEnd_time()
        +" | "+"Repeat: Every "+repeat_time);*/
        String status=reminderBeanArrayList.get(position).getStatus();
        if(status.equals("true")){
            viewHolder.btnOnOff.setImageResource(R.drawable.reminder_on);
        }else{
            viewHolder.btnOnOff.setImageResource(R.drawable.reminder_off);

        }
        viewHolder.btnOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=reminderBeanArrayList.get(position).getStatus();
                DataInsertionInDatabase dbDataInsertionInDatabase=DataInsertionInDatabase.getInstance(activity);
                String value="";
                if(status.equals("true")){
                    value="false";
                }else{
                    value="true";
                }
                boolean b=  dbDataInsertionInDatabase.updateReminderTable(reminderBeanArrayList.get(position).getTimeCreated(),value);
                if(b){

                    ReminderBean reminderBean=new ReminderBean();
                    reminderBean.setId(reminderBeanArrayList.get(position).getId());
                    reminderBean.setLesson_id(reminderBeanArrayList.get(position).getLesson_id());
                    reminderBean.setLesson_title(reminderBeanArrayList.get(position).getLesson_title());
                    reminderBean.setStart_time(reminderBeanArrayList.get(position).getStart_time());
                    reminderBean.setEnd_time(reminderBeanArrayList.get(position).getEnd_time());
                    reminderBean.setReminder_type(reminderBeanArrayList.get(position).getReminder_type());
                    reminderBean.setRepeat_interval(reminderBeanArrayList.get(position).getRepeat_interval());
                    reminderBean.setReminder_text(reminderBeanArrayList.get(position).getReminder_text());
                    reminderBean.setReminder_text_time_created(reminderBeanArrayList.get(position).getReminder_text_time_created());
                    reminderBean.setReminder_audio(reminderBeanArrayList.get(position).getReminder_audio());
                    reminderBean.setReminder_audio_time_created(reminderBeanArrayList.get(position).getReminder_audio_time_created());
                    reminderBean.setTimeCreated(reminderBeanArrayList.get(position).getTimeCreated());
                    reminderBean.setStatus(value);
                    reminderBeanArrayList.set(position,reminderBean);
                    if(value=="false"){
                     /*   NotificationScheduler.cancelReminder(activity,AlarmReceiver.class,reminderBeanArrayList.get(position).getId(),reminderBeanArrayList.get(position).getLesson_id(),
                                reminderBeanArrayList.get(position).getLesson_title(),reminderBeanArrayList.get(position).getStart_time(),reminderBeanArrayList.get(position).getEnd_time(),
                                reminderBeanArrayList.get(position).getReminder_type(),reminderBeanArrayList.get(position).getRepeat_interval(),
                                reminderBeanArrayList.get(position).getReminder_text(),reminderBeanArrayList.get(position).getReminder_audio(),reminderBeanArrayList.get(position).getStatus(),
                                reminderBeanArrayList.get(position).getTimeCreated());*/
                     NotificationScheduler.cancelReminder(activity,reminderBeanArrayList.get(position).getId());
                        UtilsMethods.showToast(activity,"Reminder off successfully");
                    }else{
                        NotificationScheduler.setReminder(activity,AlarmReceiver.class,reminderBeanArrayList.get(position).getId(),reminderBeanArrayList.get(position).getLesson_id(),
                                reminderBeanArrayList.get(position).getLesson_title(),reminderBeanArrayList.get(position).getStart_time(),reminderBeanArrayList.get(position).getEnd_time(),
                                reminderBeanArrayList.get(position).getReminder_type(),reminderBeanArrayList.get(position).getRepeat_interval(),
                                reminderBeanArrayList.get(position).getReminder_text(),reminderBeanArrayList.get(position).getReminder_audio(),reminderBeanArrayList.get(position).getStatus(),
                                reminderBeanArrayList.get(position).getTimeCreated());
                        UtilsMethods.showToast(activity,"Reminder on successfully");
                    }

                }else{
                    UtilsMethods.showToast(activity,"Failed to  update reminder.");
                }
                notifyDataSetChanged();
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=reminderBeanArrayList.get(position).getStatus();
                if(status.equals("true")){
                 /*   NotificationScheduler.cancelReminder(activity,AlarmReceiver.class,reminderBeanArrayList.get(position).getId(),reminderBeanArrayList.get(position).getLesson_id(),
                            reminderBeanArrayList.get(position).getLesson_title(),reminderBeanArrayList.get(position).getStart_time(),reminderBeanArrayList.get(position).getEnd_time(),
                            reminderBeanArrayList.get(position).getReminder_type(),reminderBeanArrayList.get(position).getRepeat_interval(),
                            reminderBeanArrayList.get(position).getReminder_text(),reminderBeanArrayList.get(position).getReminder_audio(),reminderBeanArrayList.get(position).getStatus(),
                            reminderBeanArrayList.get(position).getTimeCreated());*/
                 NotificationScheduler.cancelReminder(activity,reminderBeanArrayList.get(position).getId());
                }
               DbDataDeleteFromDatabase dbDataDeleteFromDatabase=DbDataDeleteFromDatabase.getInstance(activity) ;
                boolean b=  dbDataDeleteFromDatabase.deleteReminder(reminderBeanArrayList.get(position).getTimeCreated());
                if(b){
                    UtilsMethods.showToast(activity,"Reminder deleted successfully");
                }else {
                    UtilsMethods.showToast(activity,"Failed to delete reminder");
                }
                reminderBeanArrayList.remove(position);
                notifyDataSetChanged();
            }

        });


        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ScheduleReminderFragment scheduleReminderFragment=new ScheduleReminderFragment();
                Bundle bundle=new Bundle();
                bundle.putInt(Keys.ID,reminderBeanArrayList.get(position).getId());
                bundle.putString(Keys.LESSON_ID,reminderBeanArrayList.get(position).getLesson_id());
                bundle.putString(Keys.LESSON_TITLE,reminderBeanArrayList.get(position).getLesson_title());
                bundle.putString(Keys.START_TIME,reminderBeanArrayList.get(position).getStart_time());
                bundle.putString(Keys.END_TIME,reminderBeanArrayList.get(position).getEnd_time());
                bundle.putString(Keys.REMINDER_TYPE,reminderBeanArrayList.get(position).getReminder_type());
                bundle.putString(Keys.REPEAT_INTERVAL,reminderBeanArrayList.get(position).getRepeat_interval());
                bundle.putString(Keys.REMINDER_TEXT,reminderBeanArrayList.get(position).getReminder_text());
                bundle.putString(Keys.TEXT_AFFIRMATION_TIME_CREATED,reminderBeanArrayList.get(position).getReminder_text_time_created());
                bundle.putString(Keys.REMINDER_AUDIO,reminderBeanArrayList.get(position).getReminder_audio());
                bundle.putString(Keys.AUDIO_AFFIRMATION_TIME_CREATED,reminderBeanArrayList.get(position).getReminder_audio_time_created());
                bundle.putString(Keys.TIME_CREATED,reminderBeanArrayList.get(position).getTimeCreated());
                bundle.putString(Keys.STATUS,reminderBeanArrayList.get(position).getStatus());
                bundle.putBoolean(Keys.UPDATE_REMINDER,true);
                scheduleReminderFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity,scheduleReminderFragment);

            }
        });




        /*   viewHolder.textViewLessonTitle.setText(reminderBeanArrayList.get(position).getLesson_title());
        viewHolder.textViewStartTime.setText(reminderBeanArrayList.get(position).getStart_time());
        viewHolder.textViewEndTime.setText(reminderBeanArrayList.get(position).getEnd_time());
        viewHolder.textViewRepeatInterval.setText(reminderBeanArrayList.get(position).getRepeat_interval());
        viewHolder.textViewReminderType.setText(reminderBeanArrayList.get(position).getReminder_type());
        String status=reminderBeanArrayList.get(position).getStatus();
        if(status.equals("true")){
            viewHolder.switchBtn.setChecked(true);
        }else{
            viewHolder.switchBtn.setChecked(false);
        }
        viewHolder.switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String status=reminderBeanArrayList.get(position).getStatus();
                DataInsertionInDatabase dbDataInsertionInDatabase=DataInsertionInDatabase.getInstance(activity);
                String value="";
                if(status.equals("true")){
                  value="false";
                }else{
                    value="true";
                }
                boolean b=  dbDataInsertionInDatabase.updateReminderTable(reminderBeanArrayList.get(position).getTimeCreated(),value);
                if(b){
                    UtilsMethods.showToast(activity,"Reminder updated successfully");
                    ReminderBean reminderBean=new ReminderBean();
                    reminderBean.setId(reminderBeanArrayList.get(position).getId());
                    reminderBean.setLesson_id(reminderBeanArrayList.get(position).getLesson_id());
                    reminderBean.setLesson_title(reminderBeanArrayList.get(position).getLesson_title());
                    reminderBean.setStart_time(reminderBeanArrayList.get(position).getStart_time());
                    reminderBean.setEnd_time(reminderBeanArrayList.get(position).getEnd_time());
                    reminderBean.setReminder_type(reminderBeanArrayList.get(position).getReminder_type());
                    reminderBean.setRepeat_interval(reminderBeanArrayList.get(position).getRepeat_interval());
                    reminderBean.setReminder_text(reminderBeanArrayList.get(position).getReminder_text());
                    reminderBean.setReminder_audio(reminderBeanArrayList.get(position).getReminder_audio());
                    reminderBean.setTimeCreated(reminderBeanArrayList.get(position).getTimeCreated());
                    reminderBean.setStatus(value);
                    reminderBeanArrayList.set(position,reminderBean);
                    if(value=="false"){
                        NotificationScheduler.cancelReminder(activity,AlarmReceiver.class,reminderBeanArrayList.get(position).getId(),reminderBeanArrayList.get(position).getLesson_id(),
                                reminderBeanArrayList.get(position).getLesson_title(),reminderBeanArrayList.get(position).getStart_time(),reminderBeanArrayList.get(position).getEnd_time(),
                                reminderBeanArrayList.get(position).getReminder_type(),reminderBeanArrayList.get(position).getRepeat_interval(),
                                reminderBeanArrayList.get(position).getReminder_text(),reminderBeanArrayList.get(position).getReminder_audio(),reminderBeanArrayList.get(position).getStatus(),
                                reminderBeanArrayList.get(position).getTimeCreated());
                    }else{
                        NotificationScheduler.setReminder(activity,AlarmReceiver.class,reminderBeanArrayList.get(position).getId(),reminderBeanArrayList.get(position).getLesson_id(),
                                reminderBeanArrayList.get(position).getLesson_title(),reminderBeanArrayList.get(position).getStart_time(),reminderBeanArrayList.get(position).getEnd_time(),
                                reminderBeanArrayList.get(position).getReminder_type(),reminderBeanArrayList.get(position).getRepeat_interval(),
                                reminderBeanArrayList.get(position).getReminder_text(),reminderBeanArrayList.get(position).getReminder_audio(),reminderBeanArrayList.get(position).getStatus(),
                                reminderBeanArrayList.get(position).getTimeCreated());
                    }

                }else{
                    UtilsMethods.showToast(activity,"Failed to  update reminder.");
                }
                notifyDataSetChanged();
            }
        });*/


    }

    @Override
    public int getItemCount() {
        return reminderBeanArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

      /*  private CustomTextView textViewLessonTitle,textViewStartTime, textViewEndTime, textViewRepeatInterval, textViewReminderType;
        private Switch switchBtn;*/

          private CustomTextViewBold textViewLesson,textViewReminderTitle;
          private CustomTextView         textViewReminderInfo;
          private ImageView btnOnOff,btnEdit,btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewLesson=(CustomTextViewBold)itemView.findViewById(R.id.textViewLesson);
            textViewReminderTitle=(CustomTextViewBold)itemView.findViewById(R.id.textViewReminderTitle);
            textViewReminderInfo=(CustomTextView)itemView.findViewById(R.id.textViewReminderInfo);
            btnOnOff=(ImageView)itemView.findViewById(R.id.btnOnOff);
            btnEdit=(ImageView)itemView.findViewById(R.id.btnEdit);
            btnDelete=(ImageView)itemView.findViewById(R.id.btnDelete);

           /* textViewLessonTitle=(CustomTextView)itemView.findViewById(R.id.textViewLessonTitle);
            textViewStartTime = (CustomTextView) itemView.findViewById(R.id.textViewStartTime);
            textViewEndTime = (CustomTextView) itemView.findViewById(R.id.textViewEndTime);
            textViewRepeatInterval = (CustomTextView) itemView.findViewById(R.id.textViewRepeatInterval);
            textViewReminderType = (CustomTextView) itemView.findViewById(R.id.textViewReminderType);
            switchBtn=(Switch)itemView.findViewById(R.id.switchBtn);*/
        }
    }
}



