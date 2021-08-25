package com.infoicon.acim.settings;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.reminder.ReminderListFragment;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.retrofit.ApiService;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.text.TextChapterFragment;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.UtilsMethods;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sumit on 16/8/17.
 */

public class SettingsFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private ImageButton btnBack;
    private CustomButton btnManageReminders, btnManageNotes, btnManageAffirmations, btnSearch, btnUserGuide, btnBookmark;
    private boolean isNotification;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            isNotification = getArguments().getBoolean(Keys.FROM_NOTIFICATION);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_settings, container, false);
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
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.setting);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);
        btnManageReminders = (CustomButton) rootView.findViewById(R.id.btnManageReminders);
        btnManageNotes = (CustomButton) rootView.findViewById(R.id.btnManageNotes);
        btnManageAffirmations = (CustomButton) rootView.findViewById(R.id.btnManageAffirmations);
        btnSearch = (CustomButton) rootView.findViewById(R.id.btnSearch);
        btnUserGuide = (CustomButton) rootView.findViewById(R.id.btnUserGuide);
        btnBookmark = (CustomButton) rootView.findViewById(R.id.btnbookmark);
        if (isNotification) {
            isNotification = false;
            //UtilsMethods.replaceFragment(getActivity(),new ReminderListFragment());
            UtilsMethods.replaceFragment(getActivity(), new ManageAffirmationFragment());
        }
    }

    @Override
    public void initListeners() {
        btnManageReminders.setOnClickListener(this);
        btnManageNotes.setOnClickListener(this);
        btnManageAffirmations.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnUserGuide.setOnClickListener(this);
        btnBookmark.setOnClickListener(this);
    }

    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnManageReminders:
                openManageReminder(AppConstants.MANAGE_REMINDER);
                break;
            case R.id.btnManageNotes:
                FragmentManageNotes fragmentManageNotes = new FragmentManageNotes();
                UtilsMethods.replaceFragment(getActivity(), fragmentManageNotes);
                break;

            case R.id.btnbookmark:
                title.setText("Bookmark");
                BookMark bookmark = new BookMark();
                UtilsMethods.replaceFragment(getActivity(), bookmark);
                break;
            case R.id.btnManageAffirmations:
                //  UtilsMethods.replaceFragment(getActivity(), new ManageAffirmationFragment());
                openManageReminder(AppConstants.MANAGE_AFFIRMATION);
                break;
            case R.id.btnSearch:
                String url = AppConstants.SEARCH_URL;
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);


                //  UtilsMethods.openCustomChrome(getActivity(), AppConstants.SEARCH_URL);
               /* SearchFragment searchFragment = new SearchFragment();
                UtilsMethods.replaceFragment(getActivity(), searchFragment);*/
                break;
            case R.id.btnUserGuide:
                UtilsMethods.replaceFragment(getActivity(), new UserGuide());
                break;
        }
    }

    /** method to open manager reminder screen
     * @param page_type */

    private void openManageReminder(String page_type) {
        Bundle bundle = new Bundle();
        bundle.putString(Keys.PAGE_TYPE, page_type);
        ManageReminders manageReminders = new ManageReminders();
        manageReminders.setArguments(bundle);
        UtilsMethods.replaceFragment(getActivity(), manageReminders);
    }




}

