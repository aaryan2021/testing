package com.infoicon.acim.text;

import android.Manifest;
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
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
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
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.affirmation.TextAffirmationFragment;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.bean.getBookmarkResponse;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.notes.FragmentNotes;
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
import com.infoicon.acim.workbook.WorkbookFragment;
import com.infoicon.acim.workbook.WorkbookPartFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

import static android.Manifest.permission.READ_PHONE_STATE;
import static android.content.Context.TELEPHONY_SERVICE;
import static com.infoicon.acim.main.MainActivity.tabLayout;
import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

/**
 * Created by sumit on 5/9/17.
 */

public class TextChapterDescFragment extends BaseFragment implements View.OnClickListener/*, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener*/ {

    private static final int PERMISSION_REQUEST_CODE = 200;
    private View rootView;
    private CustomTextView title, textViewHeading;
    private WebView textViewDesc;
    private CustomButton btnViewNotes;
    private ImageButton btnBack;
    private String flag;
    private String topic_id, title_name;
    private List<String> musicArrayList;
    //  private GetChapterDescResponse getChapterDescResponse;
    private String description, chapter_title = "";
    private ImageView imageViewPlay, bookmark_icon;
    private RelativeLayout main_layout;
    private LinearLayout retry;
    private Fragment fragment;
    private CustomButton btnNext, btnPrevious;
    String status="1";
    Context context;
    WebSettings webSettings;
    private List<GetChapterApiResponse.DataBean.ChaptersBean.TopicsBean> lessonTopicBeen;
    private List<getBookmarkResponse.BookmarkData> lessonTopic;
    private List<getBookmarkResponse.BookmarkData.chapter_array> chapter_arrayList;
    private int topic_pos,chapter_topic_position;

    private ImageView rewind, forward, increase, decrease;
    private float size;


    //mediaplayer
    private MediaPlayer mediaPlayer;

    /**
     * help to toggle between play and pause.
     */
    private boolean playPause;
    /**
     *
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


        rootView = inflater.inflate(R.layout.fragment_manual_for_teachers, container,
                false);

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

        flag=getArguments().getString("FLAG");

          if(flag.equals("1")) {
             chapter_arrayList=getArguments().getParcelableArrayList(Keys.Chapter_ARRAY);
             lessonTopic = getArguments().getParcelableArrayList(Keys.ARRAY);
             //topic_pos = getArguments().getInt(Keys.POSITION);
             topic_pos=getIndex(lessonTopic.get(getArguments().getInt(Keys.POSITION)).chapter_id);
             topic_id = chapter_arrayList.get(topic_pos).getId();
             title_name = getArguments().getString(Keys.TITLE);
             fragment = getTargetFragment();

         }
        else{
            lessonTopicBeen = getArguments().getParcelableArrayList(Keys.ARRAY);
            topic_pos = getArguments().getInt(Keys.POSITION);
            topic_id = lessonTopicBeen.get(topic_pos).getId();
            title_name = getArguments().getString(Keys.TITLE);
            fragment = getTargetFragment();
        }


    }

    @Override
    public void initViews() {
        RelativeLayout main = rootView.findViewById(R.id.main);
        main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        title = getActivity().findViewById(R.id.title);
        title.setMaxLines(2);
        title.setGravity(Gravity.LEFT);

        textViewHeading = rootView.findViewById(R.id.textViewHeading);
        bookmark_icon = rootView.findViewById(R.id.bookmark_icon);
        textViewHeading.setVisibility(View.GONE);
        textViewDesc = rootView.findViewById(R.id.textViewDesc);
        webSettings = textViewDesc.getSettings();




        //textViewDesc.setMovementMethod(LinkMovementMethod.getInstance());

        btnBack = getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        btnViewNotes = rootView.findViewById(R.id.btnViewNotes);
        imageViewPlay = rootView.findViewById(R.id.imageViewPlay);
        btnNext = rootView.findViewById(R.id.btnNext);
        btnPrevious = rootView.findViewById(R.id.btnPrevious);
        rewind = rootView.findViewById(R.id.rewind);
        forward = rootView.findViewById(R.id.forward);
        increase = rootView.findViewById(R.id.increase);
        decrease = rootView.findViewById(R.id.decrease);
        main_layout = rootView.findViewById(R.id.relativeLayout);
        main_layout.setVisibility(View.VISIBLE);
        retry = rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        //textViewDesc.setMovementMethod(LinkMovementMethod.getInstance());

        if(flag.equals("1")){
            if(isAdded()) {
                status="0";
                bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));

            } if (chapter_arrayList.size() == 1 || ((topic_pos) < 1)) {
                btnPrevious.setVisibility(View.GONE);
            } else {
                btnPrevious.setVisibility(View.VISIBLE);
            }
            if ((topic_pos) < (chapter_arrayList.size() - 1)) {
                btnNext.setVisibility(View.VISIBLE);
            } else {
                btnNext.setVisibility(View.GONE);
            }
            size = User.getInstance(getActivity()).getSize();
            webSettings.setDefaultFontSize((int)size);
            textViewHeading.setTextSize(size);
        }

        else{
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
           // textViewDesc.setTextSize(size);
            webSettings.setDefaultFontSize((int)size);
            textViewHeading.setTextSize(size);
        }



    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        btnViewNotes.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        retry.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnPrevious.setOnClickListener(this);
        rewind.setOnClickListener(this);
        forward.setOnClickListener(this);
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
        bookmark_icon.setOnClickListener(this);
    }

    /*
     * method to initialize mediaplayer
     * */
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }

    private void setUpBackGround() {
        RelativeLayout main = getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                String str;
                if(flag.equalsIgnoreCase("1")){
                    str = chapter_arrayList.get(topic_pos).getId().substring(0,3);
                }
                else {
                    str = lessonTopicBeen.get(topic_pos).getId().substring(0,3);
                }


                if(str.equals("Txt")){
                    TextFragment textFragment = new TextFragment();
                    UtilsMethods.replaceFragment(getActivity(), textFragment);
                    TabLayout.Tab tab = tabLayout.getTabAt(1);
                    tab.select();

                }
                else if(str.equals("WKB")){
                    WorkbookPartFragment workbookFragment = new WorkbookPartFragment();
                    UtilsMethods.replaceFragment(getActivity(), workbookFragment);
                    TabLayout.Tab tab = tabLayout.getTabAt(2);
                    tab.select();
                }
                else if(str.equals("MNL")){
                    ManualFragment manualFragment = new ManualFragment();
                    UtilsMethods.replaceFragment(getActivity(), manualFragment);
                    TabLayout.Tab tab = tabLayout.getTabAt(3);
                    tab.select();
                }
                else {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    fragmentManager.popBackStack();
                }

                break;
            case R.id.btnNext:

                if(flag.equals("1")){

                if (topic_pos < (chapter_arrayList.size() - 1)) {
                    status="1";
                    if(isAdded()) {
                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    }topic_pos++;
                    topic_id = chapter_arrayList.get(topic_pos).getId();
                    releaseMediaPlayer();
                    textViewDesc.scrollTo(0,0);
                    callWorkbookLessonDescWebService(getJson());
                }
              }
                else{

                    if (topic_pos < (lessonTopicBeen.size() - 1)) {
                        status="1";
                        if(isAdded()) {
                            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                        }topic_pos++;
                        topic_id = lessonTopicBeen.get(topic_pos).getId();
                        releaseMediaPlayer();
                        textViewDesc.scrollTo(0,0);
                        callWorkbookLessonDescWebService(getJson());
                    }
                }

                break;
            case R.id.btnPrevious:
                if (topic_pos > 0) {
                    topic_pos--;
                       if(flag.equals("1")) {
                        status="1";
                        if(isAdded()) {
                            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                        }topic_id = chapter_arrayList.get(topic_pos).getId();
                    }

                    else {
                        status="1";
                        if(isAdded()) {
                            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                        } topic_id = lessonTopicBeen.get(topic_pos).getId();
                    }


                    releaseMediaPlayer();
                    callWorkbookLessonDescWebService(getJson());
                }
                break;
            case R.id.record:
                releaseMediaPlayer();
                UtilsMethods.replaceFragment(getActivity(), new TextAffirmationFragment());

                break;
            case R.id.btnViewNotes:
                releaseMediaPlayer();
                FragmentNotes fragmentNotes = new FragmentNotes();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.TOPIC_ID, topic_id);
                bundle.putString(Keys.NOTES_TYPE, Keys.NOTES_TYPE_TEXT);
                fragmentNotes.setArguments(bundle);
                UtilsMethods.replaceFragment(getActivity(), fragmentNotes);
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
                    UtilsMethods.showToast(getActivity(), "Music not found.");
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
                Log.e("SIZEIN",size+"");
                User.getInstance(getActivity()).setSize(size);
                webSettings.setDefaultFontSize((int)size);
                textViewHeading.setTextSize(size);

                break;

            case R.id.decrease:


                if (size > AppConstants.MIN_SIZE) {
                    size = size - 1;
                    User.getInstance(getActivity()).setSize(size);
                    webSettings.setDefaultFontSize((int)size);
                    textViewHeading.setTextSize(size);
                }
                break;

            case R.id.bookmark_icon:
                try {
                    if(flag.equals("1")){
                        setDataInHaspMap();
                        //Toast.makeText(getActivity(), "Will not work from here", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        setDataInHaspMap();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
        }
    }

  /*  private void callWorkbookLessonDescWebService(JsonObject jsonObject) {


        final retrofit.Call<GetChapterDescResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getChapterDescResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call,true, new Callback<GetChapterDescResponse>() {

            @Override
            public void onResponse(Response<GetChapterDescResponse> response, Retrofit retrofit) {

                retry.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                getChapterDescResponse = response.body();
                if (getChapterDescResponse.getSuccess().equals("true")) {
                    musicArrayList = getChapterDescResponse.getData().getMusicfiles();
                //    title_name = "CHAPTER " + (topic_pos + 1);
                    if (lessonTopicBeen.size() == 1 || ((topic_pos) < 1)) {
                        btnPrevious.setVisibility(View.GONE);
                    } else {
                        btnPrevious.setVisibility(View.VISIBLE);
                    }
                    if ((topic_pos)<(lessonTopicBeen.size() - 1)) {
                        btnNext.setVisibility(View.VISIBLE);
                    } else {
                        btnNext.setVisibility(View.GONE);
                    }
                    title_name=getChapterDescResponse.getData().getTitle();
                    String [] heading=title_name.split(":");
                    title.setText(heading[0]+" \n"+heading[1].trim());


                   if (Build.VERSION.SDK_INT >= 24) {
                        textViewDesc.setText(Html.fromHtml(getChapterDescResponse.getData().getDescription(), Html.FROM_HTML_MODE_LEGACY));

                       textViewHeading.setText(Html.fromHtml(getChapterDescResponse.getData().getTitle(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textViewDesc.setText(Html.fromHtml(getChapterDescResponse.getData().getDescription()));
                       textViewHeading.setText(Html.fromHtml(getChapterDescResponse.getData().getTitle()));
                    }

                } else {
                    UtilsMethods.showToast(getActivity(), getChapterDescResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
    }
*/

    private void callWorkbookLessonDescWebService(final JSONObject jsonObject) {

        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try {

                    HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                    if (hashMap.containsKey(TEXT_CHAPTER_CONTENT_DESCRIPTION)) {
                        if (hashMap.get(TEXT_CHAPTER_CONTENT_DESCRIPTION).equalsIgnoreCase(topic_id)) {
                            if(isAdded()) {
                                bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
                            }
                        } else {
                            if(isAdded()) {
                            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                        }}
                    }

                    JSONObject jsonObject = new JSONObject(response);
                    retry.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);
                    //     getChapterDescResponse = jsonObject.body();
                    if (jsonObject.getString("success").equals("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("musicfiles");
                        if (jsonArray != null) {
                            musicArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                musicArrayList.add(jsonArray.getString(i));
                                Log.e("music_array_size", musicArrayList.size() + "");
                            }
                        }
                        //    title_name = "CHAPTER " + (topic_pos + 1);
                        if(flag.equals("1")) {
                            // Log.e("CHapter_Array",lessonTopic.get(0).getChapter_arrays().size()+"");
                            if (chapter_arrayList.size() == 1 || ((topic_pos) < 1)) {
                                btnPrevious.setVisibility(View.GONE);
                            } else {
                                btnPrevious.setVisibility(View.VISIBLE);
                            }
                            if ((topic_pos) < (chapter_arrayList.size() - 1)) {
                                btnNext.setVisibility(View.VISIBLE);
                            } else {
                                btnNext.setVisibility(View.GONE);
                            }
                        }
                        else {
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
                        }

                        if(flag.equals("1")){
                            String str = chapter_arrayList.get(topic_pos).getId().substring(0,3);

                            if(str.equals("WKB")){
                                title.setText(chapter_arrayList.get(topic_pos).id + " | " + chapter_arrayList.get(topic_pos).getName());
                                String header = chapter_arrayList.get(topic_pos).getName();
                                if (header.contains("Review")) {
                                    textViewHeading.setText(title_name);
                                } else {
                                    textViewHeading.setText(header);
                                }

                            }
                            else {
                                String header = chapter_arrayList.get(topic_pos).getName();
                                if (header.contains("Review")) {
                                    textViewHeading.setText(title_name);
                                } else {
                                    textViewHeading.setText(header);
                                }

                                chapter_title = jsonObject1.getString("title");
                                String[] heading = chapter_title.split(":");
                                if(heading.length==1){
                                    title.setText(heading[0].trim());
                                }
                                else {
                                    title.setText(heading[0] + " \n" + heading[1].trim());
                                }

                            }
                        }

                        else {

                                chapter_title = jsonObject1.getString("title");
                                String[] heading = chapter_title.split(":");
                                if(heading.length==1){
                                    title.setText(heading[0].trim());
                                }
                                else {
                                    title.setText(heading[0] + " \n" + heading[1].trim());
                                }

                            String header = lessonTopicBeen.get(topic_pos).getName();
                            if (header.contains("Review")) {
                                textViewHeading.setText(title_name);
                            } else {
                                textViewHeading.setText(header);
                            }



                        }





                        description = jsonObject1.getString("description");
                        Log.e("Textdescription",description);
                        if (Build.VERSION.SDK_INT >= 24) {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);

                        } else {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);
                        }
                    } else {
                        UtilsMethods.showToast(getActivity(), jsonObject.getString("msg"));
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
        if(flag.equals("1")){
            String str = chapter_arrayList.get(topic_pos).getId().substring(0,3);
            if(str.equals("Txt")){
                webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.CHAPTER_DESC);
            }
            else if(str.equals("WKB")){
                webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.WORKBOOK_LESSON_DESC);
            }
            else if(str.equals("MNL")){
                webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.TEACHER_MANUAL_DESC);
            }
            else {
                webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.CHAPTER_DESC);
            }
        }
        else {
            webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.CHAPTER_DESC);

        }


    }

    private void setDataInHaspMap() throws JSONException {




      /*  HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
        if (hashMap.get(TEXT_CHAPTER_CONTENT_DESCRIPTION) != null) {
            hashMap.remove(TEXT_CHAPTER_CONTENT_DESCRIPTION);
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
            sendData(true,hashMap);
        } else {
            hashMactivityap.put(TEXT_CHAPTER_CONTENT_DESCRIPTION, topic_id);
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
            sendData(false, hashMap);
        }


        */


        String deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e("DeviceID",deviceId);

        JsonObject jsonObject=new JsonObject();


        if(flag.equals("1")){
            jsonObject.addProperty("chapter_Id",chapter_arrayList.get(topic_pos).getId());
            jsonObject.addProperty("chapter_Type","descriptiontype");
            jsonObject.addProperty("device_Token",deviceId);
            jsonObject.addProperty("name",chapter_arrayList.get(topic_pos).getName());
            jsonObject.addProperty("status",status);
            jsonObject.addProperty("type","bookmark");
            JsonArray jsonArray1 = new JsonArray();

            for (int  i = 0; i < chapter_arrayList.size(); i++)// change to list1.size()
            {
                JsonObject jsonObject1=new JsonObject();
                jsonObject1.addProperty("id",chapter_arrayList.get(i).getId());
                jsonObject1.addProperty("name",chapter_arrayList.get(i).getName());
                jsonArray1.add(jsonObject1);
            }
            jsonObject.add("chapter_Array",jsonArray1);

        }
        else {
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

            //jsonObject.addProperty("chapter_Array", String.valueOf(outerArray));
        }




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

            }
        });



    }

    private void sendData(Boolean isRemove, HashMap<String, String> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, topic_pos);

        User.getInstance(getContext()).saveBookmarkData(hashMap);
        if (isRemove) {
            fragment.onActivityResult(145, Activity.RESULT_CANCELED, intent);
        } else {
            fragment.onActivityResult(145, Activity.RESULT_OK, intent);
        }
    }


   /* private JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.TYPE, Keys.CHAPTER_DESC);
        jsonObject.addProperty(Keys.TOPIC_ID, topic_id);
        jsonObject.addProperty(Keys.RTYPE, Keys.TEXT);
        return jsonObject;
    }*/

    private JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
             if(flag.equalsIgnoreCase("1")) {
                String str = chapter_arrayList.get(topic_pos).getId().substring(0,3);
                if(str.equals("Txt"))
                {
                    jsonObject.put(Keys.TYPE, Keys.CHAPTER_DESC);
                    jsonObject.put(Keys.TOPIC_ID, topic_id);
                    jsonObject.put(Keys.RTYPE, Keys.TEXT);
                    jsonObject.put("device_type", "ios");
                }
                else if(str.equals("WKB")){
                    jsonObject.put(Keys.TYPE, Keys.DESCRIPTION_WORKBOOK);
                    jsonObject.put(Keys.TOPIC_ID, topic_id);
                    jsonObject.put("device_type", "ios");
                }
                else if(str.equals("MNL")){
                    jsonObject.put(Keys.TYPE, Keys.MANUAL_DESC);
                    jsonObject.put(Keys.TOPIC_ID, topic_id);
                    jsonObject.put("device_type", "ios");
                }



            }

            else {
                jsonObject.put(Keys.TYPE, Keys.CHAPTER_DESC);
                jsonObject.put(Keys.TOPIC_ID, topic_id);
                jsonObject.put(Keys.RTYPE, Keys.TEXT);
                jsonObject.put("device_type", "ios");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

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

    private void setData() {
           if(flag.equalsIgnoreCase("1")) {
            String str = chapter_arrayList.get(topic_pos).getId().substring(0,3);
            String str1= lessonTopic.get(getArguments().getInt(Keys.POSITION)).getChapter_id().substring(0,3);

            if(str.equals("WKB")|| str1.equals("WKB")){
                title.setText(title_name + " | " + chapter_arrayList.get(topic_pos).getName());
                String header = chapter_arrayList.get(topic_pos).getName();
                if (header.contains("Review")) {
                    textViewHeading.setText(title_name);
                } else {
                    textViewHeading.setText(header);
                }

            }
            else {

                if (chapter_title.length() > 0) {
                    String[] heading = chapter_title.split(":");
                    if (heading.length == 1) {
                        title.setText(heading[0].trim());
                    } else {
                        title.setText(heading[0] + " \n" + heading[1].trim());

                    }

                }

                String header = chapter_arrayList.get(topic_pos).getName();
                if (header.contains("Review")) {
                    textViewHeading.setText(title_name);
                } else {
                    textViewHeading.setText(header);
                }
            }


        }



        else{
            if (chapter_title.length() > 0) {
                String[] heading = chapter_title.split(":");
                if (heading.length == 1) {
                    title.setText(heading[0].trim());
                } else {
                    title.setText(heading[0] + " \n" + heading[1].trim());

                }

            }
        }




            title.setMaxLines(2);
            title.setGravity(Gravity.LEFT);
            if (description != null) {
                if (Build.VERSION.SDK_INT >= 24) {
                    textViewDesc.scrollTo(0,0);
                    textViewDesc.loadData(description, "text/html", null);

                    // textViewDesc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
                    //    textViewHeading.setText(Html.fromHtml(chapter_title,Html.FROM_HTML_MODE_LEGACY));
                } else {
                    textViewDesc.loadData(description, "text/html", null);

                    // textViewDesc.setText(Html.fromHtml(description));
                    //    textViewHeading.setText(Html.fromHtml(chapter_title));
                }
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
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();
            intialStage = false;

        }

    }


    public int getIndex(String itemName)
    {
        for (int i = 0; i < chapter_arrayList.size(); i++)
        {
            String auction = chapter_arrayList.get(i).getId();
            if (itemName.equals(auction))
            {
                return i;
            }
        }

        return -1;
    }








}

