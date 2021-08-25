package com.infoicon.acim.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.reminder.ReminderListFragment;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;

/**
 * Created by sumit on 14/11/17.
 */

public class ManageReminders extends Fragment implements View.OnClickListener{




    private View rootView;
    private String page_type;
    private CustomButton btnScheduleReminders,btnReminderList;
    private ImageButton btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page_type=  getArguments().getString(Keys.PAGE_TYPE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView =inflater.inflate(R.layout.fragment_manage_reminders,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initializeViews();
        setUpBackGround();

    }

    private void initViews() {
        btnScheduleReminders=(CustomButton)rootView.findViewById(R.id.btnScheduleReminders);
        btnReminderList=(CustomButton)rootView.findViewById(R.id.btnReminderList);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        CustomTextView     title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        if(page_type.equals(AppConstants.MANAGE_REMINDER)){
            title.setText(R.string.manage_reminder);
            btnReminderList.setText(R.string.edit_reminders);
        }else{
            title.setText(R.string.manage_affirmations);
            btnReminderList.setText(R.string.affirmation_list);
        }
    }

    private void initializeViews(){
        btnBack.setOnClickListener(this);
        btnScheduleReminders.setOnClickListener(this);
        btnReminderList.setOnClickListener(this);
    }
    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
          if(v==btnScheduleReminders){
              if(UtilsMethods.isUserHaveAnAffirmation(getActivity())) {
                  Bundle bundle = new Bundle();
                  bundle.putString(Keys.PAGE_TYPE, AppConstants.SCHEDULE_REMINDER);
                  ScheduleReminderFragment scheduleReminderFragment = new ScheduleReminderFragment();
                  scheduleReminderFragment.setArguments(bundle);
                  UtilsMethods.replaceFragment(getActivity(), scheduleReminderFragment);
              } else{
                  UtilsMethods.scheduleReminderAlertDialog(getActivity(),getString(R.string.schedule_reminder_error_msg));
              }
          }else if(v==btnReminderList){
              if(page_type.equals(AppConstants.MANAGE_REMINDER)){
                  UtilsMethods.replaceFragment(getActivity(),new ReminderListFragment());
              }else{

                  UtilsMethods.replaceFragment(getActivity(),new ManageAffirmationFragment());
              }

          }else if(v==btnBack){
              FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
              fragmentManager.popBackStack();
          }
    }
}
