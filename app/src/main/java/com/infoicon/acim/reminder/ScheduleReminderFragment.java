package com.infoicon.acim.reminder;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TimePicker;

import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.receiver.AlarmReceiver;
import com.infoicon.acim.R;
import com.infoicon.acim.adapter.ReminderAudioListAdapter;
import com.infoicon.acim.adapter.ReminderTextListAdapter;
import com.infoicon.acim.adapter.RepeatIntervalAdapter;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.database.DataInsertionInDatabase;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by sumit on 29/9/17.
 */

public class ScheduleReminderFragment extends BaseFragment implements View.OnClickListener,CompoundButton.OnCheckedChangeListener{


    private View rootView;
    private CustomTextView textViewStartTime,textViewEndTime,textViewRepeat;
    private String[] interval_list={5+"",10+"",15+"",20+"",30+"",45+"",+60+"",120+"",180+""};
    private String interval_value=5+"";
    private ImageButton btnBack;
    private String lesson_id,lesson_title;
    private RecyclerView recyclerViewTextAffirmation,recyclerViewAudioAffirmation;
    private ArrayList<TextAffirmationBean> textAffirmationBeanArrayList;
    private ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private ReminderAudioListAdapter reminderAudioListAdapter;
    private ReminderTextListAdapter reminderTextListAdapter;
    private CustomButton btnSave;
    private CheckBox checkBoxTextAffirmation,checkBoxAudioAffirmation;
    private String reminder_type="";
    private LinearLayout layoutAffirmation,layoutEmptyNotes;
    public static int SELECTED_AFFIRMATION_TEXT_POS=-1,SELECTED_AFFIRMATION_AUDIO_POS=-1;
    private int reminder_id,preRepeatInterval;
    private boolean isUpdate;
    private String prevStartTime,prevEndTime,prevReminderText,prevStatus,prevReminderAudio,prevTimeCreated,textAffirmationTimeCreated,audioAffirmationTimeCReated;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }
    private void getBundleData() {
        if(getArguments().containsKey(Keys.UPDATE_REMINDER)){
            reminder_id=getArguments().getInt(Keys.ID,0);
            lesson_id=getArguments().getString(Keys.LESSON_ID);
            lesson_title=getArguments().getString(Keys.LESSON_TITLE);
            prevStartTime=getArguments().getString(Keys.START_TIME);
            Log.e("star_time",prevStartTime);
            prevEndTime=getArguments().getString(Keys.END_TIME);
            reminder_type=getArguments().getString(Keys.REMINDER_TYPE);
            preRepeatInterval=Integer.parseInt(getArguments().getString(Keys.REPEAT_INTERVAL));
            Log.e("repeat_interval",preRepeatInterval+"");
            prevReminderText=getArguments().getString(Keys.REMINDER_TEXT);
            textAffirmationTimeCreated=getArguments().getString(Keys.TEXT_AFFIRMATION_TIME_CREATED);
            prevReminderAudio=getArguments().getString(Keys.REMINDER_AUDIO);
            audioAffirmationTimeCReated=getArguments().getString(Keys.AUDIO_AFFIRMATION_TIME_CREATED);
            prevTimeCreated=getArguments().getString(Keys.TIME_CREATED);
            prevStatus=getArguments().getString(Keys.STATUS);
            isUpdate= getArguments().getBoolean(Keys.UPDATE_REMINDER,false);


        }else{
            if(getArguments().containsKey(Keys.PAGE_TYPE)){

            }else{
                lesson_id = getArguments().getString(Keys.LESSON_ID);
                lesson_title = getArguments().getString(Keys.LESSON_TITLE);
            }

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_schedule_reminder,container,false);
        rootView.setOnClickListener(null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
    }

    @Override
    public void initViews() {
        CustomTextView  title = (CustomTextView) getActivity().findViewById(R.id.title);

        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        SELECTED_AFFIRMATION_TEXT_POS=-1;
        SELECTED_AFFIRMATION_AUDIO_POS=-1;
        btnSave=(CustomButton)rootView.findViewById(R.id.btnSave);
        checkBoxTextAffirmation=(CheckBox)rootView.findViewById(R.id.checkBoxTextAffirmation);
        checkBoxAudioAffirmation=(CheckBox)rootView.findViewById(R.id.checkBoxAudioAffirmation);
        layoutAffirmation=(LinearLayout)rootView.findViewById(R.id.layoutAffirmation);
        layoutEmptyNotes=(LinearLayout)rootView.findViewById(R.id.layoutEmptyNotes);

        textViewStartTime=(CustomTextView)rootView.findViewById(R.id.textViewStartTime);
        textViewEndTime=(CustomTextView)rootView.findViewById(R.id.textViewEndTime);
        textViewRepeat=(CustomTextView)rootView.findViewById(R.id.textViewRepeat);
        textViewRepeat.setText(interval_value +" mins");
        recyclerViewTextAffirmation=(RecyclerView)rootView.findViewById(R.id.recyclerViewTextAffirmation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTextAffirmation.setLayoutManager(linearLayoutManager);
        recyclerViewTextAffirmation.setNestedScrollingEnabled(false);
        recyclerViewTextAffirmation.setVisibility(View.GONE);

        recyclerViewAudioAffirmation=(RecyclerView)rootView.findViewById(R.id.recyclerViewAudioAffirmation);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAudioAffirmation.setLayoutManager(linearLayoutManager1);
        recyclerViewAudioAffirmation.setNestedScrollingEnabled(false);
        recyclerViewAudioAffirmation.setVisibility(View.GONE);

        setUpListView();
        if(isUpdate){
            title.setText(R.string.update_reminder);
            textViewStartTime.setText(prevStartTime);
            textViewEndTime.setText(prevEndTime);
            interval_value=preRepeatInterval+"";
            if(preRepeatInterval<=59){
                textViewRepeat.setText(preRepeatInterval+" mins");
            }else {
                int hours=(preRepeatInterval)/60;
                if(preRepeatInterval==60){
                    textViewRepeat.setText(hours+ " hour");
                }else {
                    textViewRepeat.setText(hours+ " hours");
                }
            }
            btnSave.setText(R.string.update);

            if(reminder_type.equals(AppConstants.AUDIO_REMINDER)){
                if(audioAffirmationBeanArrayList!=null ) {
                    if (audioAffirmationBeanArrayList.size() > 0) {
                        checkBoxAudioAffirmation.setChecked(true);
                        recyclerViewAudioAffirmation.setVisibility(View.VISIBLE);
                        getPrevAudioAffirmationPosition();
                    }
                }
            }else{
                if(textAffirmationBeanArrayList!=null ) {
                    if (textAffirmationBeanArrayList.size() > 0) {
                        checkBoxTextAffirmation.setChecked(true);
                        recyclerViewTextAffirmation.setVisibility(View.VISIBLE);
                        getPrevWrittenAffirmationPosition();
                    }
                }
            }
        }else {
            title.setText(R.string.schedule_reminder);
        }
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
    }

    @Override
    public void initListeners() {
        textViewStartTime.setOnClickListener(this);
        textViewEndTime.setOnClickListener(this);
        textViewRepeat.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
        checkBoxAudioAffirmation.setOnCheckedChangeListener(this);
        checkBoxTextAffirmation.setOnCheckedChangeListener(this);
    }
    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
    /**
     * call the Andorid Time Picker
     * "2017-08-15 14:50:00"
     *
     * @param textView
     */
    private void setStartTime(final CustomTextView textView) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        datetime.set(Calendar.MINUTE, minute);
                        if(datetime.getTimeInMillis() > c.getTimeInMillis()){
                            //            it's after current
                            String hourString = "";
                            if(hourOfDay < 12) {
                                hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
                            } else {
                                if(hourOfDay==12){
                                    hourString = (hourOfDay) < 10 ? "0"+(hourOfDay) : ""+(hourOfDay);
                                }else{
                                    hourString = (hourOfDay - 12) < 10 ? "0"+(hourOfDay - 12) : ""+(hourOfDay - 12);
                                }

                            }
                            String minuteString = minute < 10 ? "0"+minute : ""+minute;

                            String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                            String time = hourString+":"+minuteString + " " + am_pm;

                            textView.setText( time);

                        }else{
                          //   it's before current time'

                            UtilsMethods.showToast(getActivity(),"You don't select the time before the current time");
                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
    /**
     * call the Andorid Time Picker
     * "2017-08-15 14:50:00"
     *
     * @param textView
     */
    private void setEndTime(final CustomTextView textView) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                         String start_time=textViewStartTime.getText().toString();
                         int start_hrs=Integer.parseInt(start_time.substring(0,2));
                         int start_min=Integer.parseInt(start_time.substring(3,5));
                        String AM_PM=start_time.substring(6,8);
                        if(AM_PM.equals("PM")){
                            if(start_hrs>12){
                                start_hrs=start_hrs+12;
                            }

                        }

                        Calendar c = Calendar.getInstance();
                        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        c.set(Calendar.MINUTE, (minute));

                        Calendar datetime = Calendar.getInstance();
                        datetime.set(Calendar.HOUR_OF_DAY, start_hrs);
                        datetime.set(Calendar.MINUTE, (start_min));
                        if(datetime.getTimeInMillis()<c.getTimeInMillis()){
                            //            it's after start time
                            String hourString = "";
                            if(hourOfDay < 12) {
                                hourString = hourOfDay < 10 ? "0"+hourOfDay : ""+hourOfDay;
                            } else {
                                if (hourOfDay == 12) {
                                    hourString = (hourOfDay) < 10 ? "0" + (hourOfDay) : "" + (hourOfDay);
                                } else {
                                    hourString = (hourOfDay - 12) < 10 ? "0" + (hourOfDay - 12) : "" + (hourOfDay - 12);
                                }
                            }
                            String minuteString = minute < 10 ? "0"+minute : ""+minute;

                            String am_pm = (hourOfDay < 12) ? "AM" : "PM";
                            String time = hourString+":"+minuteString + " " + am_pm;



                            textView.setText( time);
                        }else{
                               // it's before start time'
                            UtilsMethods.showToast(getActivity(),"You don't select the time before the start time");
                        }


                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;

            case R.id.textViewStartTime:
                textViewEndTime.setText(getResources().getString(R.string.default_time));
                setStartTime(textViewStartTime);
                break;
            case R.id.textViewEndTime:
                if(textViewStartTime.getText().toString().equals(getResources().getString(R.string.default_time))){
                    UtilsMethods.showToast(getActivity(),"Please select the start time first");
                }else{
                    setEndTime(textViewEndTime);
                }

                break;
            case R.id.textViewRepeat:
                showBottomSheetDialog();
                break;
            case R.id.btnSave:
                if(validateReminderDetails()) {
                    if (!isUpdate) {
                        saveReminder();
                    } else {
                     updateReminder();
                    }
                }
                break;

        }
    }

    private boolean validateReminderDetails() {

        if(checkBoxTextAffirmation.isChecked()){
            reminder_type=AppConstants.TEXT_REMINDER;
        }else if(checkBoxAudioAffirmation.isChecked()){
            reminder_type=AppConstants.AUDIO_REMINDER;
        }

        if(textViewStartTime.getText().toString().trim().equals(getResources().getString(R.string.default_time))){
            UtilsMethods.showToast(getActivity(),"Please select the start time.");
            return false;
        }else if(textViewEndTime.getText().toString().trim().equals(getResources().getString(R.string.default_time))){
            UtilsMethods.showToast(getActivity(),"Please select the end time.");
            return false;
        }else if(interval_value==null){
            UtilsMethods.showToast(getActivity(),"Please select the repeat interval.");
            return false;
        } else if(Integer.parseInt(interval_value)>UtilsMethods.getTimeDifference(textViewStartTime.getText().toString(),textViewEndTime.getText().toString())){
            UtilsMethods.scheduleReminderAlertDialog(getActivity(),"Difference between reminder start and end time should be greater than repeat interval.");
            return false;
        }else if(reminder_type.length()==0){
            UtilsMethods.showToast(getActivity(),"Please select a written or audio affirmation.");
            return false;
        }else if(reminder_type.equals(AppConstants.TEXT_REMINDER)&SELECTED_AFFIRMATION_TEXT_POS==-1){
            UtilsMethods.showToast(getActivity(),"Please select a written affirmation.");
            return false;
        }else if(reminder_type.equals(AppConstants.AUDIO_REMINDER)&SELECTED_AFFIRMATION_AUDIO_POS==-1){
            UtilsMethods.showToast(getActivity(),"Please select an audio affirmation.");
            return false;
        }
         return true;
    }

    private void showBottomSheetDialog(){
       final Dialog mBottomSheetDialog = new Dialog(getActivity());
        View sheetView = getActivity().getLayoutInflater().inflate(R.layout.dialog_repeat, null);
        RecyclerView recyclerViewRepeatTime=(RecyclerView)sheetView.findViewById(R.id.recyclerViewRepeatTime) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewRepeatTime.setLayoutManager(linearLayoutManager);
        RepeatIntervalAdapter repeatIntervalAdapter=new RepeatIntervalAdapter(getActivity(),interval_list);
        recyclerViewRepeatTime.setAdapter(repeatIntervalAdapter);
        repeatIntervalAdapter.setListener(new RecyclerViewClickListener() {
            @Override
            public void recyclerViewListClicked(View v, final int position) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        interval_value=interval_list[position];

                        if(position<6){
                            textViewRepeat.setText(interval_list[position]+ " mins");
                        }else {
                            int hours=(Integer.parseInt(interval_list[position]))/60;
                            if(position==6){
                                textViewRepeat.setText(hours+ " hour");
                            }else {
                                textViewRepeat.setText(hours+ " hours");
                            }
                        }

                        /*if((position+1)==interval_list.length){
                            textViewRepeat.setText(interval_list[position]);
                        }else{
                            textViewRepeat.setText(interval_list[position]+ " mins");
                        }*/
                        mBottomSheetDialog.dismiss();
                    }
                },500);

            }
        });
        CustomTextView btnCancel=(CustomTextView)sheetView.findViewById(R.id.btnCancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               mBottomSheetDialog.dismiss();
           }
       });

        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.show();
    }

    private void setUpListView(){

            dbGetDataFromDataBase= DbGetDataFromDataBase.getInstance(getActivity());
        if(lesson_id!=null) {
            textAffirmationBeanArrayList = dbGetDataFromDataBase.getTextAffirmation(lesson_id);
        }else {
            textAffirmationBeanArrayList = dbGetDataFromDataBase.getAllTextAffirmation();
        }
        //    textAffirmationBeanArrayList=dbGetDataFromDataBase.getAllTextAffirmation();
            if(textAffirmationBeanArrayList !=null){

                reminderTextListAdapter=new ReminderTextListAdapter(getActivity(),textAffirmationBeanArrayList);
                recyclerViewTextAffirmation.setAdapter(reminderTextListAdapter);

            }else{
                checkBoxTextAffirmation.setVisibility(View.GONE);
                recyclerViewTextAffirmation.setVisibility(View.GONE);
            }
        if(lesson_id!=null) {
            audioAffirmationBeanArrayList = dbGetDataFromDataBase.getAudioAffirmation(lesson_id);
        }else {
            audioAffirmationBeanArrayList = dbGetDataFromDataBase.getAllAudioAffirmation();
        }
            if(audioAffirmationBeanArrayList !=null){
                if(audioAffirmationBeanArrayList.size()>0){
                    reminderAudioListAdapter=new ReminderAudioListAdapter(getActivity(),audioAffirmationBeanArrayList);
                    recyclerViewAudioAffirmation.setAdapter(reminderAudioListAdapter);
                }else{
                    checkBoxAudioAffirmation.setVisibility(View.GONE);
                    recyclerViewAudioAffirmation.setVisibility(View.GONE);
                }
            }

        if(textAffirmationBeanArrayList==null && audioAffirmationBeanArrayList==null){
                   layoutEmptyNotes.setVisibility(View.VISIBLE);
                    layoutAffirmation.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView==checkBoxTextAffirmation){
               if(checkBoxAudioAffirmation.isChecked()){
                   UtilsMethods.showToast(getActivity(),"You can select reminder for only one affirmation at a time.");
                   checkBoxTextAffirmation.setChecked(false);

               }else{
                   //checkBoxTextAffirmation.setChecked(true);
                   recyclerViewTextAffirmation.setVisibility(View.VISIBLE);
                   recyclerViewAudioAffirmation.setVisibility(View.GONE);
                   reminder_type=AppConstants.TEXT_REMINDER;
               }


               if(!checkBoxTextAffirmation.isChecked()){
                   recyclerViewTextAffirmation.setVisibility(View.GONE);
                   reminder_type="";
               }

        }if(buttonView==checkBoxAudioAffirmation){
            if(checkBoxTextAffirmation.isChecked()){
                UtilsMethods.showToast(getActivity(),"You can select reminder for only one affirmation at a time.");
                checkBoxAudioAffirmation.setChecked(false);

            }else{
              //  checkBoxAudioAffirmation.setChecked(true);
                recyclerViewAudioAffirmation.setVisibility(View.VISIBLE);
                recyclerViewTextAffirmation.setVisibility(View.GONE);
                reminder_type=AppConstants.AUDIO_REMINDER;
            }
            if(!checkBoxAudioAffirmation.isChecked()){
                recyclerViewAudioAffirmation.setVisibility(View.GONE);
                reminder_type="";
            }
        }
    }


    private void saveReminder()
    {

            DataInsertionInDatabase dataInsertionInDatabase=DataInsertionInDatabase.getInstance(getActivity());
            boolean b=false;
            String startTime=textViewStartTime.getText().toString().trim();
            String endTime=textViewEndTime.getText().toString().trim();


            String timeCreated=""+System.currentTimeMillis();
            if(reminder_type.equals(AppConstants.AUDIO_REMINDER)){
                if(lesson_id==null){
                    lesson_id=audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getLesson_id();
                    lesson_title=audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getLesson_name();
                }
                b=  dataInsertionInDatabase.insertDataInReminderTable(lesson_id,lesson_title,startTime,endTime
                        ,AppConstants.AUDIO_REMINDER,interval_value,"","",audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getAudio_path(),
                        audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getTime_created(),"true",timeCreated);
            }else if(reminder_type.equals(AppConstants.TEXT_REMINDER)){
                if(lesson_id==null){
                    lesson_id=textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getLesson_id();
                    lesson_title=textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getLesson_title();
                }
                b=  dataInsertionInDatabase.insertDataInReminderTable(lesson_id,lesson_title,startTime,endTime
                        ,AppConstants.TEXT_REMINDER,interval_value,textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getAffirmation_text(),
                        textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getTime_created(),"","","true",timeCreated);
            }
            if(b){
                UtilsMethods.showToast(getActivity(),"Reminder set successfully.");
                DbGetDataFromDataBase dbGetDataFromDataBase=DbGetDataFromDataBase.getInstance(getActivity());
                int reminder_id=dbGetDataFromDataBase.getReminderId(timeCreated);
                if(reminder_type.equals(AppConstants.AUDIO_REMINDER)) {
                    NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class,reminder_id,lesson_id, lesson_title,startTime,endTime,
                            AppConstants.AUDIO_REMINDER,interval_value,"",audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getAudio_path(),"true",timeCreated );
                }else{
                    NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class,reminder_id,lesson_id,lesson_title,startTime,endTime
                            ,AppConstants.TEXT_REMINDER,interval_value,textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getAffirmation_text(),"","true",timeCreated);
                }
                FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
                fragmentManager1.popBackStack();

                UtilsMethods.replaceFragment(getActivity(),new ReminderListFragment());
            }else{
                UtilsMethods.showToast(getActivity(),"Failed to set reminder.");
            }

    }

   private void updateReminder(){
       DataInsertionInDatabase dataInsertionInDatabase=DataInsertionInDatabase.getInstance(getActivity());
       boolean b=false;
       String startTime=textViewStartTime.getText().toString().trim();
       String endTime=textViewEndTime.getText().toString().trim();

       if(reminder_type.equals(AppConstants.AUDIO_REMINDER)){
           b=  dataInsertionInDatabase.updateReminder(lesson_id,lesson_title,startTime,endTime
                   ,AppConstants.AUDIO_REMINDER,interval_value,"","",audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getAudio_path(),
                   audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getTime_created(),"true",prevTimeCreated);
       }else if(reminder_type.equals(AppConstants.TEXT_REMINDER)){
           b=  dataInsertionInDatabase.updateReminder(lesson_id,lesson_title,startTime,endTime
                   ,AppConstants.TEXT_REMINDER,interval_value,textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getAffirmation_text(),
                   textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getTime_created(),"","","true",prevTimeCreated);
       }
       if(b){
           UtilsMethods.showToast(getActivity(),"Reminder updated successfully.");

           if(reminder_type.equals(AppConstants.AUDIO_REMINDER)) {

             /*  NotificationScheduler.cancelReminder(getActivity(),AlarmReceiver.class,reminder_id,lesson_id,
                       lesson_title,prevStartTime,prevEndTime,
                       AppConstants.AUDIO_REMINDER,preRepeatInterval+"",
                       "",prevReminderAudio,prevStatus,
                      prevTimeCreated);
*/
             NotificationScheduler.cancelReminder(getActivity(),reminder_id);

               NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class,reminder_id,lesson_id, lesson_title,startTime,endTime,
                       AppConstants.AUDIO_REMINDER,interval_value,"",audioAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_AUDIO_POS).getAudio_path(),"true",prevTimeCreated );
           }else{


     /*          NotificationScheduler.cancelReminder(getActivity(),AlarmReceiver.class,reminder_id,lesson_id,
                       lesson_title,prevStartTime,prevEndTime,
                       AppConstants.TEXT_REMINDER,preRepeatInterval+"",
                       prevReminderText,"",prevStatus,
                       prevTimeCreated);*/

               NotificationScheduler.cancelReminder(getActivity(),reminder_id);

               NotificationScheduler.setReminder(getActivity(), AlarmReceiver.class,reminder_id,lesson_id,lesson_title,startTime,endTime
                       ,AppConstants.TEXT_REMINDER,interval_value,textAffirmationBeanArrayList.get(SELECTED_AFFIRMATION_TEXT_POS).getAffirmation_text(),"","true",prevTimeCreated);
           }
           FragmentManager fragmentManager1 = getActivity().getSupportFragmentManager();
          /* for (int i=0;i<1;i++){

           }*/
           int count=fragmentManager1.getBackStackEntryCount();
           if(count>=2){
               fragmentManager1.popBackStack();
           }
           fragmentManager1.popBackStack();

           UtilsMethods.replaceFragment(getActivity(),new ReminderListFragment());
       }else{
           UtilsMethods.showToast(getActivity(),"Failed to set reminder.");
       }
    }

    /*method to get the prev selected  written affirmation pos in case of update reminder
    * */
    private void getPrevWrittenAffirmationPosition(){


        for(int i=0;i<textAffirmationBeanArrayList.size();i++){
            if(textAffirmationBeanArrayList.get(i).getTime_created().equals(textAffirmationTimeCreated)){
                SELECTED_AFFIRMATION_TEXT_POS=i;
                reminderTextListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }

    /*method to get the prev selected  audio affirmation pos in case of update reminder
* */
    private void getPrevAudioAffirmationPosition(){
        for(int i=0;i<audioAffirmationBeanArrayList.size();i++){
            if(audioAffirmationBeanArrayList.get(i).getTime_created().equals(audioAffirmationTimeCReated)){
                SELECTED_AFFIRMATION_AUDIO_POS=i;
                reminderAudioListAdapter.notifyDataSetChanged();
                break;
            }
        }
    }
}
