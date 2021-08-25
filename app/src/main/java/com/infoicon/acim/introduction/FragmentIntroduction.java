package com.infoicon.acim.introduction;

import android.app.ProgressDialog;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.Html;
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

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.affirmation.TextAffirmationFragment;
import com.infoicon.acim.bean.GetIntroductionResponse;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.retrofit.ServiceUrls;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.manualNetworkCall.INetworkResponse;
import com.infoicon.acim.utils.manualNetworkCall.WebServiceCall;
import com.infoicon.acim.workbook.WorkbookLessonDescFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sumit on 16/9/17.
 */

public class FragmentIntroduction extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title, textViewHeading, textViewBuffering;
    WebView textViewDesc;
    private ImageView record;
    private ImageButton btnBack;
    private CustomButton btnViewNotes;
    private String topic_id, title_name;
    private List<String> musicArrayList;

    private String description, header_title = "";

    private ImageView imageViewPlay;
    private RelativeLayout main_layout;
    private LinearLayout retry;
    WebSettings webSettings;
    String flag="";
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
        callIntroWebservice();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_introduction, container, false);
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
    public void onStart() {
        super.onStart();
        setData();
    }


    private void getBundleData() {
        topic_id = getArguments().getString(Keys.TOPIC_ID);
        title_name = getArguments().getString(Keys.TITLE);


    }

    @Override
    public void initViews() {
        title = getActivity().findViewById(R.id.title);
        title.setText(title_name);
        title.setMaxLines(1);
        textViewHeading = rootView.findViewById(R.id.textViewHeading);
        //textViewHeading.setText(title_name);
        btnBack = getActivity().findViewById(R.id.btnBack);

        btnBack.setVisibility(View.VISIBLE);
        textViewDesc = rootView.findViewById(R.id.textViewDesc);
        webSettings = textViewDesc.getSettings();
        record = rootView.findViewById(R.id.record);
        btnViewNotes = rootView.findViewById(R.id.btnViewNotes);
        imageViewPlay = rootView.findViewById(R.id.imageViewPlay);
        reminder = rootView.findViewById(R.id.reminder);
        rewind = rootView.findViewById(R.id.rewind);
        forward = rootView.findViewById(R.id.forward);
        increase = rootView.findViewById(R.id.increase);
        decrease = rootView.findViewById(R.id.decrease);
        textViewBuffering = rootView.findViewById(R.id.textViewBuffering);
        textViewBuffering.setVisibility(View.GONE);
        main_layout = rootView.findViewById(R.id.relativeLayout);
        main_layout.setVisibility(View.VISIBLE);
        retry = rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        size = User.getInstance(getActivity()).getSize();
        webSettings.setDefaultFontSize((int)size);


        textViewHeading.setTextSize(size);
        if (topic_id.equals(AppConstants.TEXT_FOREWARD) || topic_id.equals(AppConstants.TEXT_INTRO)) {
            record.setVisibility(View.GONE);
            reminder.setVisibility(View.GONE);
            title.setGravity(Gravity.CENTER);
        } else {
            title.setGravity(Gravity.LEFT);
        }
        if (topic_id.equals(AppConstants.TEXT_FOREWARD)) {
            rewind.setVisibility(View.GONE);
            forward.setVisibility(View.GONE);
            imageViewPlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        record.setOnClickListener(this);
        btnViewNotes.setOnClickListener(this);
        imageViewPlay.setOnClickListener(this);
        retry.setOnClickListener(this);
        reminder.setOnClickListener(this);
        rewind.setOnClickListener(this);
        forward.setOnClickListener(this);
        increase.setOnClickListener(this);
        decrease.setOnClickListener(this);
    }


    private void setUpBackGround() {
        RelativeLayout main = getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    /**
     * method to handle click events
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
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
            case R.id.btnViewNotes:
                releaseMediaPlayer();
                FragmentNotes fragmentNotes = new FragmentNotes();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.TOPIC_ID, topic_id);
                bundle.putString(Keys.NOTES_TYPE, Keys.NOTES_TYPE_WORKBOOK);
                fragmentNotes.setArguments(bundle);
                UtilsMethods.replaceFragment(getActivity(), fragmentNotes);
                break;
            case R.id.retry:
                callIntroWebservice();
                break;
            case R.id.imageViewPlay:

                if (musicArrayList.size() > 0) {

                    if (mediaPlayer == null) {
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

            case R.id.reminder:
                if (UtilsMethods.isUserHaveAnAffirmation(getActivity())) {
                    releaseMediaPlayer();
                    ScheduleReminderFragment scheduleReminderFragment = new ScheduleReminderFragment();
                    Bundle bundle2 = new Bundle();
                    bundle2.putString(Keys.LESSON_ID, topic_id);
                    bundle2.putString(Keys.LESSON_TITLE, title_name);
                    scheduleReminderFragment.setArguments(bundle2);
                    UtilsMethods.replaceFragment(getActivity(), scheduleReminderFragment);
                } else {
                    UtilsMethods.scheduleReminderAlertDialog(getActivity(), getString(R.string.schedule_reminder_lesson_error_msg));
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
                if (size > AppConstants.MIN_SIZE) {
                    size = size - 1;
                    User.getInstance(getActivity()).setSize(size);
                    webSettings.setDefaultFontSize((int)size);
                    textViewHeading.setTextSize(size);
                }

                break;
        }
    }

    /**
     * webservice call for getting workbook lesson description
     */
    private void callIntroWebservice() {


        WebServiceCall webServiceCall = new WebServiceCall(getActivity(), new INetworkResponse() {
            @Override
            public void onSuccess(String response) {

                retry.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                try {
                    JSONObject jsonObject = new JSONObject(response);

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
                        description = jsonObject1.getString("description");
                        header_title = jsonObject1.getString("title");

                        if (Build.VERSION.SDK_INT >= 24) {
                            textViewDesc.scrollTo(0,0);
                            textViewDesc.loadData(description, "text/html", null);
                           // textViewDesc.setText(Html.fromHtml(description));

                            textViewHeading.setText(Html.fromHtml(header_title));
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
                UtilsMethods.showToast(getActivity(), "Error= " + error);
                retry.setVisibility(View.VISIBLE);
                main_layout.setVisibility(View.GONE);
            }
        });
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put(Keys.TYPE, Keys.TEXT_INTRO);
            jsonObject.put("key", topic_id);
            jsonObject.put("device_type", "ios");
        } catch (JSONException e) {
            e.printStackTrace();
        }


        webServiceCall.execute(jsonObject, ServiceUrls.BASE_URL + ServiceUrls.INTRO_URL);

    }

/*

    private void callIntroWebservice() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(Keys.TYPE, Keys.TEXT_INTRO);
        jsonObject.addProperty("key", topic_id);

        final retrofit.Call<GetIntroductionResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getIntroductionResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call, true, new Callback<GetIntroductionResponse>() {

            @Override
            public void onResponse(Response<GetIntroductionResponse> response, Retrofit retrofit) {
                retry.setVisibility(View.GONE);
                main_layout.setVisibility(View.VISIBLE);
                getIntroductionResponse = response.body();
                if (getIntroductionResponse.getSuccess().equals("true")) {
                    musicArrayList = getIntroductionResponse.getData().getMusicfiles();
                    Log.e("music_file", musicArrayList.size() + "");


                    if (Build.VERSION.SDK_INT >= 24) {


                        textViewDesc.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription(), Html.FROM_HTML_MODE_LEGACY));
                        textViewHeading.setText(Html.fromHtml(getIntroductionResponse.getData().getTitle(), Html.FROM_HTML_MODE_LEGACY));
                    } else {

                        textViewDesc.setText(Html.fromHtml(getIntroductionResponse.getData().getDescription()));
                        textViewHeading.setText(Html.fromHtml(getIntroductionResponse.getData().getTitle()));
                    }

                } else {
                    UtilsMethods.showToast(getActivity(), getIntroductionResponse.getMsg());
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }

    private void setData() {
        if (header_title.length() > 0) {
            if (Build.VERSION.SDK_INT >= 24) {
                //textViewDesc.setText(Html.fromHtml(description));
                textViewDesc.scrollTo(0,0);
                textViewDesc.loadData(description, "text/html", null);
                textViewHeading.setText(Html.fromHtml(header_title));
            } else {
                //textViewDesc.setText(Html.fromHtml(description));
                textViewDesc.scrollTo(0,0);
                textViewDesc.loadData(description, "text/html", null);
                textViewHeading.setText(Html.fromHtml(header_title));
            }
        }


    }
}

