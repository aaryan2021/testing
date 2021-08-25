package com.infoicon.acim.manual;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.retrofit.ApiService;
import com.infoicon.acim.retrofit.ServiceUrls;
import com.infoicon.acim.utils.Database;
import com.infoicon.acim.utils.HomeMenuItemClickListener;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.GetManualDescResponse;
import com.infoicon.acim.bean.GetTeacherManualResponse;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.affirmation.TextAffirmationFragment;
import com.infoicon.acim.utils.manualNetworkCall.INetworkResponse;
import com.infoicon.acim.utils.manualNetworkCall.WebServiceCall;
import com.infoicon.acim.workbook.WorkbookLessonDescFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by sumit on 18/8/17.
 */

public class ManualDescFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private static final int PERMISSION_REQUEST_CODE = 200;
    private CustomTextView title, textViewHeading;
    private WebView textViewDesc;/*textViewBuffering*/;
    private CustomButton btnViewNotes/*btnManageNotes*/;
    private ImageButton btnBack;
    Context context;
    private String topic_id, title_name;
    private List<String> musicArrayList;
    String status="1";
  //  private GetManualDescResponse getManualDescResponse;
  private String description,header_title="";

    private Fragment fragment;
    private ImageView imageViewPlay,bookmark_icon;
    private int song_pos = 0;
    private boolean playState = true, isPlayBefore = false;
    private RelativeLayout main_layout;
    private LinearLayout retry;
    WebSettings webSettings;
    private CustomButton btnNext, btnPrevious;
    private List<GetTeacherManualResponse.DataBean> manualDataBeanList;
    private int topic_pos;
    private ImageView rewind, forward, increase, decrease;
    private float size;

    //mediaplayer
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
        context=getActivity();
        getBundleData();
        callManualDescWebService(getJson());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_manual_for_teachers, container, false);
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
        fragment = getTargetFragment();
        RelativeLayout main=(RelativeLayout)rootView.findViewById(R.id.main) ;
        main.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(title_name);
        title.setMaxLines(2);
        title.setGravity(Gravity.LEFT);
        bookmark_icon = rootView.findViewById(R.id.bookmark_icon);
        textViewHeading = (CustomTextView) rootView.findViewById(R.id.textViewHeading);
        textViewHeading.setVisibility(View.VISIBLE);
        textViewDesc = (WebView) rootView.findViewById(R.id.textViewDesc);
        webSettings = textViewDesc.getSettings();
        //textViewDesc.setMovementMethod(LinkMovementMethod.getInstance());

        //textViewDesc.setLinkTextColor(Color.BLUE);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        btnViewNotes = (CustomButton) rootView.findViewById(R.id.btnViewNotes);
      //  btnManageNotes=(CustomButton)rootView.findViewById(R.id.btnManageNotes);
        imageViewPlay = (ImageView) rootView.findViewById(R.id.imageViewPlay);
        btnNext = (CustomButton) rootView.findViewById(R.id.btnNext);
        btnPrevious = (CustomButton) rootView.findViewById(R.id.btnPrevious);
        rewind = (ImageView) rootView.findViewById(R.id.rewind);
        forward = (ImageView) rootView.findViewById(R.id.forward);
        increase = (ImageView) rootView.findViewById(R.id.increase);
        decrease = (ImageView) rootView.findViewById(R.id.decrease);
       /* textViewBuffering = (CustomTextView) rootView.findViewById(R.id.textViewBuffering);
        textViewBuffering.setVisibility(View.GONE);*/
        main_layout = (RelativeLayout) rootView.findViewById(R.id.relativeLayout);
        main_layout.setVisibility(View.VISIBLE);
        retry = (LinearLayout) rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        if (manualDataBeanList.size() == 1 || ((topic_pos) < 1)) {
            btnPrevious.setVisibility(View.GONE);
        } else {
            btnPrevious.setVisibility(View.VISIBLE);
        }
        if ((topic_pos)<(manualDataBeanList.size() - 1)) {
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

    @Override
    public void onStart() {
        super.onStart();
        setData();

    }

    private void getBundleData() {
        manualDataBeanList = getArguments().getParcelableArrayList(Keys.ARRAY);
        topic_pos = getArguments().getInt(Keys.POSITION);
        topic_id = manualDataBeanList.get(topic_pos).getId();
        title_name = getArguments().getString(Keys.TITLE);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
            case R.id.btnNext:
                if (topic_pos < (manualDataBeanList.size() - 1)) {
                    status="1";
                    if(isAdded()) {
                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    } topic_pos++;
                    topic_id = manualDataBeanList.get(topic_pos).getId();
                    callManualDescWebService(getJson());
                    releaseMediaPlayer();
                }

                break;
            case R.id.btnPrevious:
                if (topic_pos > 0) {
                    status="1";if(isAdded()) {

                        bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                    } topic_pos--;
                    topic_id = manualDataBeanList.get(topic_pos).getId();
                    callManualDescWebService(getJson());
                    releaseMediaPlayer();

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
                bundle.putString(Keys.NOTES_TYPE, Keys.NOTES_TYPE_TEACHER_MANUAL);
                fragmentNotes.setArguments(bundle);
                UtilsMethods.replaceFragment(getActivity(), fragmentNotes);
                break;
            case R.id.retry:
                callManualDescWebService(getJson());
                break;
            case R.id.imageViewPlay:

                if (musicArrayList.size() > 0) {

                    if(mediaPlayer==null){
                        initializeMediaPlayer();
                    }

                    if (!playPause) {
                        imageViewPlay.setImageResource(R.drawable.play_icon_active);
                        if (intialStage)
                            new Player().execute(musicArrayList.get(0));
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
                User.getInstance(getActivity()).setSize(size);
                webSettings.setDefaultFontSize((int)size);
                textViewHeading.setTextSize(size);
                break;

            case R.id.decrease:
                if(size>AppConstants.MIN_SIZE){
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

/**
 * method to get manual details
 * @param jsonObject
 * */
    private void callManualDescWebService(JSONObject jsonObject) {


        WebServiceCall webServiceCall=new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                    if (hashMap.containsKey(MANUAL_CHAPTER_CONTENT_DESCRIPTION)) {
                        if (hashMap.get(MANUAL_CHAPTER_CONTENT_DESCRIPTION).equalsIgnoreCase(topic_id)) {
                            if(isAdded()) {
                                bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
                            }
                        } else {
                            if(isAdded()) {
                                bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
                            } }
                    }

                    retry.setVisibility(View.GONE);
                    main_layout.setVisibility(View.VISIBLE);
                    if (jsonObject.getString("success").equals("true")) {
                        JSONObject jsonObject1 = jsonObject.getJSONObject("data");
                        JSONArray jsonArray = jsonObject1.getJSONArray("musicfiles");
                        if (jsonArray != null) {
                            musicArrayList.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                musicArrayList.add(jsonArray.getString(i));
                                Log.e("music_array_size",musicArrayList.size()+"");
                            }
                        }
                        if (manualDataBeanList.size() == 1 || ((topic_pos) < 1)) {
                            btnPrevious.setVisibility(View.GONE);
                        } else {
                            btnPrevious.setVisibility(View.VISIBLE);
                        }
                        if ((topic_pos)<(manualDataBeanList.size() - 1)) {
                            btnNext.setVisibility(View.VISIBLE);
                        } else {
                            btnNext.setVisibility(View.GONE);
                        }

                        description = jsonObject1.getString("description");
                        Log.e("DES",description);
                        header_title= jsonObject1.getString("title");
//description.replace("<td>","&#x20;");
                        title.setText(header_title);
                        if (Build.VERSION.SDK_INT >= 24) {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);
                        //    textViewDesc.setText(Html.fromHtml(description, Html.FROM_HTML_MODE_LEGACY));
                            textViewHeading.setText(Html.fromHtml(header_title, Html.FROM_HTML_MODE_LEGACY));
                        } else {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);
                            //textViewDesc.setText(Html.fromHtml(description));
                            textViewHeading.setText(Html.fromHtml(header_title));
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
                UtilsMethods.showToast(getActivity(),"Error= "+error);
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
        webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL+ServiceUrls.TEACHER_MANUAL_DESC);



    }



  /*  private void callManualDescWebService(JsonObject jsonObject) {


        final retrofit.Call<GetManualDescResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getManualDescResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call,true, new Callback<GetManualDescResponse>() {

            @Override
            public void onResponse(Response<GetManualDescResponse> response, Retrofit retrofit) {
                retry.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                getManualDescResponse = response.body();
                if (getManualDescResponse.getSuccess().equals("true")) {
                    musicArrayList = getManualDescResponse.getData().getMusicfiles();

                    if (manualDataBeanList.size() == 1 || ((topic_pos) < 1)) {
                        btnPrevious.setVisibility(View.GONE);
                    } else {
                        btnPrevious.setVisibility(View.VISIBLE);
                    }
                    if ((topic_pos)<(manualDataBeanList.size() - 1)) {
                        btnNext.setVisibility(View.VISIBLE);
                    } else {
                        btnNext.setVisibility(View.GONE);
                    }

                    title_name = getManualDescResponse.getData().getTitle();
                    title.setText(title_name);
                    if (Build.VERSION.SDK_INT >= 24) {
                        textViewDesc.setText(Html.fromHtml(getManualDescResponse.getData().getDescription().trim(), Html.FROM_HTML_MODE_LEGACY));
                           textViewHeading.setText(Html.fromHtml(getManualDescResponse.getData().getTitle(), Html.FROM_HTML_MODE_LEGACY));
                    } else {
                        textViewDesc.setText(Html.fromHtml(getManualDescResponse.getData().getDescription().trim()));
                           textViewHeading.setText(Html.fromHtml(getManualDescResponse.getData().getTitle()));
                    }

                } else {
                    UtilsMethods.showToast(getActivity(), getManualDescResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                // UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
    }*/

  /*  private JsonObject getJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.TYPE, Keys.MANUAL_DESC);
        jsonObject.addProperty(Keys.TOPIC_ID, topic_id);

        return jsonObject;
    }
*/
    private JSONObject getJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.TYPE, Keys.MANUAL_DESC);
            jsonObject.put(Keys.TOPIC_ID, topic_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
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
        playPause=false;
        intialStage=true;
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
                Log.e("audio_path",params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause=false;

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void setData(){
        if (header_title.length()>0) {
            title.setText(header_title);
            if (Build.VERSION.SDK_INT >= 24) {
                textViewDesc.loadData(description, "text/html", null);
                textViewHeading.setText(Html.fromHtml(header_title, Html.FROM_HTML_MODE_LEGACY));
                //textViewDesc.setText(Html.fromHtml(description,Html.FROM_HTML_MODE_LEGACY));
            } else {
                textViewHeading.setText(Html.fromHtml(header_title));
               // textViewDesc.setText(Html.fromHtml(description));
                textViewDesc.loadData(description, "text/html", null);

            }



        }
    }


    private void sendData(Boolean isRemove, HashMap<String, String> hashMap) {
        Intent intent = new Intent();
        intent.putExtra(Keys.POSITION, topic_pos);
        User.getInstance(getContext()).saveBookmarkData(hashMap);
        if (isRemove) {
            fragment.onActivityResult(146, Activity.RESULT_CANCELED, intent);
        } else {
            fragment.onActivityResult(146, Activity.RESULT_OK, intent);
        }

    }

    private void setDataInHaspMap() throws JSONException {
       /* HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
        if (hashMap.get(MANUAL_CHAPTER_CONTENT_DESCRIPTION) != null) {
            hashMap.remove(MANUAL_CHAPTER_CONTENT_DESCRIPTION);
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon_white));
            sendData(true,hashMap);
        } else {
            hashMap.put(MANUAL_CHAPTER_CONTENT_DESCRIPTION, topic_id);
            bookmark_icon.setImageDrawable(getResources().getDrawable(R.drawable.bookmark_icon));
            sendData(false, hashMap);
        }*/



        /*String deviceId = "";

        if(checkPermission()){
            TelephonyManager telephonyManager;
            telephonyManager = (TelephonyManager) getContext().getSystemService(getActivity().TELEPHONY_SERVICE);
            deviceId = telephonyManager.getDeviceId();
        }
        else {
            requestPermission();
        }*/
         String deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);

        Log.e("DeviceID",deviceId);

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("chapter_Id",manualDataBeanList.get(topic_pos).getId());
        jsonObject.addProperty("chapter_Type","descriptiontype");
        jsonObject.addProperty("device_Token",deviceId);
        jsonObject.addProperty("name",getArguments().getString(Keys.TITLE));
        jsonObject.addProperty("status",status);
        jsonObject.addProperty("type","bookmark");
        JsonArray jsonArray1 = new JsonArray();

        for (int  i = 0; i < manualDataBeanList.size(); i++)// change to list1.size()
        {
            JsonObject jsonObject1=new JsonObject();
            jsonObject1.addProperty("id",manualDataBeanList.get(i).getId());
            jsonObject1.addProperty("name",manualDataBeanList.get(i).getName());
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

                } }
        });




    }





}

