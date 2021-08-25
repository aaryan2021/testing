package com.infoicon.acim.workbook;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.affirmation.TextAffirmationFragment;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.retrofit.ApiService;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceUrls;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Database;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.manualNetworkCall.INetworkResponse;
import com.infoicon.acim.utils.manualNetworkCall.WebServiceCall;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.sufficientlysecure.htmltextview.HtmlResImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.Manifest.permission.READ_PHONE_STATE;

import static com.infoicon.acim.utils.AppConstants.MANUAL_CHAPTER_CONTENT_DESCRIPTION;
import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID;

import static com.infoicon.acim.utils.AppConstants.WORKBOOK_CHAPTER_PART_TExXTESCRIPTION_ID;

/**
 * Created by sumit on 17/8/17.
 */

public class WorkbookLessonDescFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private CustomTextView title, textViewHeading;
    private WebView textViewDesc /*textViewBuffering*/;
    private ImageView record;
    private ImageButton btnBack;
    Context context;
    private CustomButton btnViewNotes;
    private String topic_id, title_name;
    private List<String> musicArrayList;
    String status="1";
    // private GetWorkbookLessonDesc getWorkbookLessonDesc;
    private String description;
    private ImageView imageViewPlay,bookmark_icon;
    WebSettings webSettings;
    private RelativeLayout main_layout;
    private LinearLayout retry;
    private CustomButton btnNext, btnPrevious;
    private List<GetWorkbookLessonResponse.DataBean.DatachildBean.LessonTopicBean> lessonTopicBeen;
    private int topic_pos,group_pos,part_no;
    private ImageView reminder, rewind, forward, increase, decrease;
    private float size;

    private MediaPlayer mediaPlayer;
    /**
     * help to toggle between play and pause.
     */
    private boolean playPause;
    /**
     * remain false till media is not completed, inside OnCompletionListener make it true.
     */
    private boolean intialStage = true;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        musicArrayList = new ArrayList<>();
        getBundleData();
        context=getActivity();
        callWorkbookLessonDescWebService(getJson());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_workbook_lesson_desc, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        setData();

    }


    private void getBundleData() {
        lessonTopicBeen = getArguments().getParcelableArrayList(Keys.ARRAY);
        topic_pos = getArguments().getInt(Keys.POSITION);
        group_pos = getArguments().getInt(Keys.GROUP_POSITION);
        part_no = getArguments().getInt(Keys.PART_NUMBER);
        topic_id = lessonTopicBeen.get(topic_pos).getId();
        title_name = getArguments().getString(Keys.TITLE);
    }

    @Override
    public void initViews() {
        fragment = getTargetFragment();
        RelativeLayout main = (RelativeLayout) rootView.findViewById(R.id.main);
        main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(title_name + " | " + lessonTopicBeen.get(topic_pos).getName());
        bookmark_icon = rootView.findViewById(R.id.bookmark_icon);
        title.setMaxLines(2);
        title.setGravity(Gravity.LEFT);

        textViewHeading = (CustomTextView) rootView.findViewById(R.id.textViewHeading);
        //     textViewHeading.setText(lessonTopicBeen.get(topic_pos).getName());

        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        textViewDesc = (WebView) rootView.findViewById(R.id.textViewDesc);
        webSettings=textViewDesc.getSettings();

        record = (ImageView) rootView.findViewById(R.id.record);
        btnViewNotes = (CustomButton) rootView.findViewById(R.id.btnViewNotes);
        imageViewPlay = (ImageView) rootView.findViewById(R.id.imageViewPlay);
        btnNext = (CustomButton) rootView.findViewById(R.id.btnNext);
        btnPrevious = (CustomButton) rootView.findViewById(R.id.btnPrevious);
        reminder = (ImageView) rootView.findViewById(R.id.reminder);
        rewind = (ImageView) rootView.findViewById(R.id.rewind);
        forward = (ImageView) rootView.findViewById(R.id.forward);
        increase = (ImageView) rootView.findViewById(R.id.increase);
        decrease = (ImageView) rootView.findViewById(R.id.decrease);
        /*textViewBuffering = (CustomTextView) rootView.findViewById(R.id.textViewBuffering);
        textViewBuffering.setVisibility(View.GONE);*/
        main_layout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        main_layout.setVisibility(View.VISIBLE);
        retry = (LinearLayout) rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);

        if (lessonTopicBeen.size() == 1 || ((topic_pos) < 1)) {
            btnPrevious.setVisibility(View.GONE);
        } else {
            btnPrevious.setVisibility(View.VISIBLE);
        }
        if ((topic_pos) < (lessonTopicBeen.size() - 1)) {
            btnNext.setVisibility(View.VISIBLE);
        } else {
            btnNext.setVisibility(View.GONE);
        }

        size = User.getInstance(getActivity()).getSize();
        webSettings.setDefaultFontSize((int)size);
        textViewHeading.setTextSize(size);
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        record.setOnClickListener(this);
        btnViewNotes.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        retry.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        rewind.setOnClickListener(this);
        forward.setOnClickListener(this);
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
        reminder.setOnClickListener(this);
        bookmark_icon.setOnClickListener(this);
    }


    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    /**
     * method to handle click events
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                if (getActivity() != null) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }

                break;
            case R.id.btnNext:
                if (topic_pos < (lessonTopicBeen.size() - 1)) {
                    status="1";
                    if(isAdded()) {
                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    }topic_pos++;
                    topic_id = lessonTopicBeen.get(topic_pos).getId();
                    callWorkbookLessonDescWebService(getJson());
                    releaseMediaPlayer();
                }

                break;
            case R.id.btnPrevious:
                if (topic_pos > 0) {
                    status="1";if(isAdded()) {

                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    } topic_pos--;
                    topic_id = lessonTopicBeen.get(topic_pos).getId();
                    callWorkbookLessonDescWebService(getJson());
                    releaseMediaPlayer();
                }
                break;
            case R.id.btnViewNotes:
                releaseMediaPlayer();
                FragmentNotes fragmentNotes = new FragmentNotes();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.TOPIC_ID, topic_id);
                bundle.putString(Keys.NOTES_TYPE, Keys.NOTES_TYPE_WORKBOOK);
                fragmentNotes.setArguments(bundle);
                UtilsMethods.replaceFragment(getActivity(), fragmentNotes);
                break;

            case R.id.record:
                releaseMediaPlayer();
                TextAffirmationFragment textAffirmationFragment = new TextAffirmationFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString(Keys.LESSON_ID, topic_id);
                bundle1.putString(Keys.LESSON_TITLE, title_name);
                textAffirmationFragment.setArguments(bundle1);
                UtilsMethods.replaceFragment(getActivity(), textAffirmationFragment);

                break;
            case R.id.reminder:
               scheduleReminder();
                break;

            case R.id.retry:
                callWorkbookLessonDescWebService(getJson());
                break;
            case R.id.imageViewPlay:
                if (musicArrayList.size() > 0) {

                    if (mediaPlayer == null) {
                        initializeMediaPlayer();
                    }

                    if (!playPause) {
                        imageViewPlay.setImageResource(R.drawable.play_icon_active);
                        if (intialStage)
                            new Player()
                                    .execute(musicArrayList.get(0));
                        else {
                            if (!mediaPlayer.isPlaying())
                                mediaPlayer.start();
                        }
                        playPause = true;
                    } else {
                        imageViewPlay.setImageResource(R.drawable.play_icon);
                        if (mediaPlayer.isPlaying())
                            mediaPlayer.pause();
                        playPause = false;
                    }
                } else {
                    //UtilsMethods.showToast(getActivity(), "Music not found.");
                    UtilsMethods.getToast(getActivity(),"Music not found.");
                    imageViewPlay.setImageResource(R.drawable.play_icon);
                    playPause = false;

                }


                break;

            case R.id.rewind:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        int current_dur = mediaPlayer.getCurrentPosition();
                        int dur = mediaPlayer.getDuration();
                        int new_dur = current_dur - AppConstants.FORWARD_BACKWARD_TIME;
                        Log.e("curr_dur", mediaPlayer.getCurrentPosition() + "");
                        Log.e("durr", mediaPlayer.getDuration() + "");
                        if (new_dur > 0) {
                            mediaPlayer.seekTo(new_dur);
                        }
                    }
                }
                break;
            case R.id.forward:
                if (mediaPlayer != null) {
                    if (mediaPlayer.isPlaying()) {
                        int current_dur = mediaPlayer.getCurrentPosition();
                        int dur = mediaPlayer.getDuration();
                        int new_dur = current_dur + AppConstants.FORWARD_BACKWARD_TIME;
                        Log.e("curr_dur", mediaPlayer.getCurrentPosition() + "");
                        Log.e("durr", mediaPlayer.getDuration() + "");
                        if (new_dur <= dur) {
                            mediaPlayer.seekTo(new_dur);
                        }
                    }

                }
                break;
            case R.id.increase:
                size = size + 1;
                User.getInstance(getActivity()).setSize(size);
                webSettings.setDefaultFontSize((int)size);
                textViewHeading.setTextSize(size);
                break;

            case R.id.decrease:
                if (size > 5) {
                    size = size - 1;
                    User.getInstance(getActivity()).setSize(size);
                    webSettings.setDefaultFontSize((int)size);
                    textViewHeading.setTextSize(size);
                }

                break;
            case R.id.bookmark_icon:
                try {
                    setDataInHaspMap();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));


        }
    }
    private Fragment fragment;
    private void sendData(Boolean isRemove, HashMap<String, String> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, topic_pos);
        intent.putExtra(Keys.GROUP_POSITION, group_pos);
        intent.putExtra(Keys.PART_NUMBER, part_no);
        User.getInstance(getContext()).saveBookmarkData(hashMap);
        if (isRemove) {
            fragment.onActivityResult(147, Activity.RESULT_CANCELED, intent);
        } else {
            fragment.onActivityResult(147, Activity.RESULT_OK, intent);
        }

    }
    private void setDataInHaspMap() throws JSONException {
      /*  HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
        if (hashMap.get(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID) != null)
        {
            hashMap.remove(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID);
            hashMap.remove(WORKBOOK_CHAPTER_PART_TExXTESCRIPTION_ID);
            hashMap.remove(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID);
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
            sendData(true,hashMap);
        }
        else
            {
            hashMap.put(WORKBOOK_CHAPTER_CONTENT_DESCRIPTION_ID, title_name);
            hashMap.put(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID, topic_id);
            hashMap.put(WORKBOOK_CHAPTER_PART_TExXTESCRIPTION_ID,String.valueOf(part_no));
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
            sendData(false, hashMap);
        }*/




        String deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);



        Log.e("DeviceID",deviceId);

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("chapter_Id",lessonTopicBeen.get(topic_pos).getId());
        jsonObject.addProperty("chapter_Type","descriptiontype");
        jsonObject.addProperty("device_Token",deviceId);
        jsonObject.addProperty("name",getArguments().getString(Keys.TITLE));
        jsonObject.addProperty("status",status);
        jsonObject.addProperty("type","bookmark");
        JsonArray jsonArray1 = new JsonArray();

        for (int  i = 0; i < lessonTopicBeen.size(); i++)// change to list1.size()
        {
            JsonObject jsonObject1=new JsonObject();
            jsonObject1.addProperty("id",lessonTopicBeen.get(i).getId());
            jsonObject1.addProperty("name",lessonTopicBeen.get(i).getName());
            jsonArray1.add(jsonObject1);
        }
        jsonObject.add("chapter_Array",jsonArray1);


        ApiService apiService= AppRetrofit.getAppRetrofitInstance().getApiServices();
        Call<SaveBookmarkResponse> call=apiService.SAVE_BOOKMARK_RESPONSE_CALL(jsonObject);
        call.enqueue(new Callback<SaveBookmarkResponse>() {
            @Override
            public void onResponse(Response<SaveBookmarkResponse> response, Retrofit retrofit) {
                Log.e("NotificationMsg",response.body().getMsg());
                if(response.body().getMsg().contains("Unmarked")){
                    status="1";
                    if(isAdded()) {
                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    }
                    UtilsMethods.getToast(context,response.body().getMsg());
                }
                else {
                    status="0";
                    if(isAdded()) {
                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
                    }

                    UtilsMethods.getToast(context,response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("NotificationMsg",t.getMessage());
                UtilsMethods.getToast(context,t.getMessage());
                if(isAdded()) {
                    bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                }
            }
        });


    }
    /**
     * webservice call for getting workbook lesson description
     */
    private void callWorkbookLessonDescWebService(JSONObject jsonObject)
    {
        Log.e("jsonObject", jsonObject.toString());

        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    retry.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);


                    if (jsonObject.getString("success").equals("true")) {
                        HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                        if (hashMap.containsKey(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID)) {
                            if (hashMap.get(WORKBOOK_CHAPTER_CHILD_ID_DESCRIPTION_ID).equalsIgnoreCase(topic_id)) {
                                if(isAdded()) {
                                    bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
                                } } else {
                                if(isAdded()) {
                                    bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                                } }
                        }

                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("musicfiles");
                        if (jsonArray != null) {
                            musicArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                musicArrayList.add(jsonArray.getString(i));
                                Log.e("music_array_size", musicArrayList.size() + "");
                            }
                        }
                        if (lessonTopicBeen.size() == 1 || ((topic_pos) < 1)) {
                            btnPrevious.setVisibility(View.GONE);
                        } else {
                            btnPrevious.setVisibility(View.VISIBLE);
                        }
                        if ((topic_pos) < (lessonTopicBeen.size() - 1)) {
                            btnNext.setVisibility(View.VISIBLE);
                        } else {
                            btnNext.setVisibility(View.GONE);
                        }
                        description = jsonObject1.getString("description");

                        title_name = lessonTopicBeen.get(topic_pos).getChapter_title();
                        title.setText(title_name + " | " + lessonTopicBeen.get(topic_pos).getName());
                        String header = lessonTopicBeen.get(topic_pos).getName();
                        if (header.contains("Review")) {
                            textViewHeading.setText(title_name);
                        } else {
                            textViewHeading.setText(header);
                        }

                        if (Build.VERSION.SDK_INT >= 24) {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);
                            //textViewDesc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);

                          //  textViewDesc.setText(Html.fromHtml(description));
                        }

                    } else {
                        UtilsMethods.getToast(getActivity(),jsonObject.getString("msg"));
                       // UtilsMethods.showToast(getActivity(), jsonObject.getString("msg"));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {
                UtilsMethods.showToast(getActivity(), "Error= " + error);
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
        webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.WORKBOOK_LESSON_DESC);

    }


   /* private void callWorkbookLessonDescWebService(JsonObject jsonObject) {
                  Log.e("jsonObject",jsonObject.toString());
        final retrofit.Call<GetWorkbookLessonDesc> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getWorkbookLessonDescCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call, true,new Callback<GetWorkbookLessonDesc>() {

            @Override
            public void onResponse(Response<GetWorkbookLessonDesc> response, Retrofit retrofit) {
                retry.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                getWorkbookLessonDesc = response.body();
                if (getWorkbookLessonDesc.getSuccess().equals("true")) {

                    musicArrayList = getWorkbookLessonDesc.getData().getMusicfiles();
                    Log.e("music_file", musicArrayList.size() + "");
                    if (lessonTopicBeen.size() == 1 || ((topic_pos) < 1)) {
                        btnPrevious.setVisibility(View.GONE);
                    } else {
                        btnPrevious.setVisibility(View.VISIBLE);
                    }
                    if ((topic_pos) < (lessonTopicBeen.size() - 1)) {
                        btnNext.setVisibility(View.VISIBLE);
                    } else {
                        btnNext.setVisibility(View.GONE);
                    }
                    title_name=lessonTopicBeen.get(topic_pos).getChapter_title();
              //      title.setText(lessonTopicBeen.get(topic_pos).getChapter_title());
                    title.setText(title_name +" | "+lessonTopicBeen.get(topic_pos).getName());
                    String header=lessonTopicBeen.get(topic_pos).getName();
                    if(header.contains("Review")){
                        textViewHeading.setText(title_name);
                    }else{
                        textViewHeading.setText(header);
                    }

                    if (Build.VERSION.SDK_INT >= 24) {
                        textViewDesc.setText(Html.fromHtml(getWorkbookLessonDesc.getData().getDescription(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textViewDesc.setText(Html.fromHtml(getWorkbookLessonDesc.getData().getDescription()));
                    }

                } else {
                    UtilsMethods.showToast(getActivity(), getWorkbookLessonDesc.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
    }*/

  /*  private JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.TYPE, Keys.DESCRIPTION_WORKBOOK);
        jsonObject.addProperty(Keys.TOPIC_ID, topic_id);

        return jsonObject;
    }*/

    private JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put(Keys.TYPE, Keys.DESCRIPTION_WORKBOOK);
            jsonObject.put(Keys.TOPIC_ID, topic_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    /*
      * method to initialize mediaplayer
      * */
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    /*method to release media player
    * */
    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        imageViewPlay.setImageResource(R.drawable.play_icon);
        playPause = false;
        intialStage = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void setData() {
        title.setText(title_name + " | " + lessonTopicBeen.get(topic_pos).getName());
        String header = lessonTopicBeen.get(topic_pos).getName();
        if (header.contains("Review")) {
            textViewHeading.setText(title_name);
        } else {
            textViewHeading.setText(header);
        }
        if (description != null) {
            if (Build.VERSION.SDK_INT >= 24) {
                textViewDesc.loadData(description, "text/html", null);
                 //textViewDesc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
            } else {
                textViewDesc.loadData(description, "text/html", null);


                //textViewDesc.setText(Html.fromHtml(description));
            }
        }
    }


    private void scheduleReminder(){

        if(UtilsMethods.isUserHaveAnAffirmation(getActivity())){
            releaseMediaPlayer();
            ScheduleReminderFragment scheduleReminderFragment = new ScheduleReminderFragment();
            Bundle bundle2 = new Bundle();
            bundle2.putString(Keys.LESSON_ID, topic_id);
            bundle2.putString(Keys.LESSON_TITLE, title_name);
            scheduleReminderFragment.setArguments(bundle2);
            UtilsMethods.replaceFragment(getActivity(), scheduleReminderFragment);
        }else{
            UtilsMethods.scheduleReminderAlertDialog(getActivity(),getString(R.string.schedule_reminder_error_msg));
        }


    }
    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;


        public Player() {
            progress = new ProgressDialog(getActivity());
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);
                Log.e("audio_path", params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause = false;

                        imageViewPlay.setImageResource(R.drawable.play_icon);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();
            intialStage = false;

        }



    }




}
