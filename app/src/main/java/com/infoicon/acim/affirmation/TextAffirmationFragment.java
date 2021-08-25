package com.infoicon.acim.affirmation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.TextAffirmationAdapter;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.database.DataInsertionInDatabase;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomEditText;
import com.infoicon.acim.utils.customviews.CustomTextView;

import java.util.ArrayList;

/**
 * Created by sumit on 17/8/17.
 */

public class TextAffirmationFragment extends BaseFragment implements View.OnClickListener {


    boolean isUpdate;
    private View rootView;
    private CustomTextView title;
    private CustomButton btnManageAudio, btnSave, btnManageReminders;
    private ImageButton btnBack;
    private CustomEditText editTextAffirmation;
    private String text_affirmation;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private String lesson_id, lesson_title;
    private ArrayList<TextAffirmationBean> textAffirmationBeanArrayList;
    private RecyclerView affirmationList;
    private TextAffirmationAdapter textAffirmationAdapter;
    private String time;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_text_affirmation, container, false);
        rootView.setOnClickListener(null);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
        getTextAffirmationList();

    }

    @Override
    public void initViews() {
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.affirmation);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnManageAudio = (CustomButton) rootView.findViewById(R.id.btnManageAudio);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        editTextAffirmation = (CustomEditText) rootView.findViewById(R.id.editTextAffirmation);
        btnSave = (CustomButton) rootView.findViewById(R.id.btnSave);
        btnSave.setText(getResources().getString(R.string.save));
        btnManageReminders = (CustomButton) rootView.findViewById(R.id.btnManageReminders);
        affirmationList = (RecyclerView) rootView.findViewById(R.id.affirmationList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        affirmationList.setLayoutManager(linearLayoutManager);
        affirmationList.setNestedScrollingEnabled(false);
        if (isUpdate) {
            editTextAffirmation.setText(text_affirmation);
            btnSave.setText(getResources().getString(R.string.update));
        } else {
            isUpdate = false;
            btnSave.setText(getResources().getString(R.string.save));
        }
    }

    @Override
    public void initListeners() {
        btnManageReminders.setOnClickListener(this);
        btnManageAudio.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
      /*  editTextAffirmation.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
                return false;
            }
        });*/
    }

    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    private void getBundleData() {
        if (getArguments().containsKey(Keys.TIME)) {
            lesson_id = getArguments().getString(Keys.LESSON_ID);
            lesson_title = getArguments().getString(Keys.LESSON_TITLE);
            text_affirmation = getArguments().getString(Keys.TEXT);
            time = getArguments().getString(Keys.TIME);
            isUpdate = true;

        } else {
            lesson_id = getArguments().getString(Keys.LESSON_ID);
            lesson_title = getArguments().getString(Keys.LESSON_TITLE);

        }

    }

    private void getTextAffirmationList() {

        dbGetDataFromDataBase = DbGetDataFromDataBase.getInstance(getActivity());
        textAffirmationBeanArrayList = dbGetDataFromDataBase.getTextAffirmation(lesson_id);
        if (textAffirmationBeanArrayList != null) {
            textAffirmationAdapter = new TextAffirmationAdapter(getActivity(), getActivity().getSupportFragmentManager(), textAffirmationBeanArrayList, AppConstants.ADD_AFFIRMATION);
            affirmationList.setAdapter(textAffirmationAdapter);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnManageAudio:
                AudioAffirmationFragment audioAffirmationFragment = new AudioAffirmationFragment();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.LESSON_ID, lesson_id);
                bundle.putString(Keys.LESSON_TITLE, lesson_title);
                audioAffirmationFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(getActivity(), audioAffirmationFragment);
                break;
            case R.id.btnBack:
                UtilsMethods.hideSoftKeyboard(getActivity());
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;
            case R.id.btnSave:
                UtilsMethods.hideSoftKeyboard(getActivity());
                if (isUpdate) {
                    if (isValidData()) {
                        DataInsertionInDatabase dataInsertionInDatabase = DataInsertionInDatabase.getInstance(getActivity());
                        boolean success = dataInsertionInDatabase.updateDataInTextAffirmationTable(lesson_id, lesson_title, text_affirmation, time);
                        if (success) {

                            UtilsMethods.showToast(getActivity(), getString(R.string.affirmation_updated_successfully));
                            editTextAffirmation.setText("");
                            editTextAffirmation.setText("");
                            getTextAffirmationList();
                            isUpdate = false;
                            btnSave.setText(getResources().getString(R.string.save));
                        } else {
                            UtilsMethods.showToast(getActivity(), getString(R.string.failed_to_update_affirmation));
                        }
                    }
                } else {
                    if (isValidData()) {
                        DataInsertionInDatabase dataInsertionInDatabase = DataInsertionInDatabase.getInstance(getActivity());
                        boolean success = dataInsertionInDatabase.insertDataInTextAffirmationTable(lesson_id, lesson_title, text_affirmation, System.currentTimeMillis() + "");
                        if (success) {
                            UtilsMethods.showToast(getActivity(), getString(R.string.affirmation_added_successfully));
                            editTextAffirmation.setText("");
                            editTextAffirmation.setText("");
                            getTextAffirmationList();
                        } else {
                            UtilsMethods.showToast(getActivity(), getString(R.string.failed_to_add_affirmation));
                        }
                    }
                }

                break;

            case R.id.btnManageReminders:
                if (UtilsMethods.isLessonHaveAnAffirmation(getActivity(), lesson_id)) {
                    ScheduleReminderFragment scheduleReminderFragment = new ScheduleReminderFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString(Keys.LESSON_ID, lesson_id);
                    bundle1.putString(Keys.LESSON_TITLE, lesson_title);
                    scheduleReminderFragment.setArguments(bundle1);
                    UtilsMethods.replaceFragment(getActivity(), scheduleReminderFragment);
                } else {
                    UtilsMethods.scheduleReminderAlertDialog(getActivity(),getString(R.string.schedule_reminder_lesson_error_msg));
                }
                break;

        }
    }


    private boolean isValidData() {
        text_affirmation = editTextAffirmation.getText().toString().trim();

        if (editTextAffirmation.getText().toString().isEmpty()) {
            editTextAffirmation.requestFocus();
            editTextAffirmation.setError(getString(R.string.write_an_affirmation));
            return false;
        }
        return true;
    }
}
