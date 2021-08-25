package com.infoicon.acim.affirmation;

import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.AudioListAdapter;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.AudioBean;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DataInsertionInDatabase;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sumit on 17/8/17.
 */

public class AudioAffirmationFragment extends BaseFragment implements View.OnClickListener,MediaPlayer.OnCompletionListener {


    private View rootView;
    private CustomTextView title;
    private CustomButton btnManageReminders;
    private RecyclerView audioList;
    private AudioListAdapter audioListAdapter;
    private ArrayList<AudioAffirmationBean> audioBeanArrayList;
    private ImageButton btnBack;
    private MediaRecorder myAudioRecorder;
    private ImageView recordStart, recordStop;
    private boolean isRecording;
    private String audioFilePath;
    private MediaPlayer mediaPlayer;
    private ImageView imageViewPlay;
    private SeekBar seekBar;
    private String lesson_id,lesson_title;
    private long duration;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private int prev_pos=-1;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getBundleData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_audio_affirmation, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initViews();
        initListeners();
       // initializeMediaPlayer();
        setUpBackGround();


    }

    private void getBundleData() {
        lesson_id = getArguments().getString(Keys.LESSON_ID);
        lesson_title = getArguments().getString(Keys.LESSON_TITLE);
    }
    @Override
    public void initViews() {
        dbGetDataFromDataBase= DbGetDataFromDataBase.getInstance(getActivity());
        title = getActivity().findViewById(R.id.title);
        title.setText(R.string.audio_affirmation);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack = getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        recordStart = rootView.findViewById(R.id.recordStart);
        recordStop = rootView.findViewById(R.id.recordStop);
        recordStart.setEnabled(true);
        recordStop.setEnabled(false);
        btnManageReminders= rootView.findViewById(R.id.btnManageReminders);
        seekBar= rootView.findViewById(R.id.seekBar);
        seekBar.setMax(20);
        audioList = rootView.findViewById(R.id.audioList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        audioList.setLayoutManager(linearLayoutManager);
        audioList.setNestedScrollingEnabled(false);


        if (!UtilsMethods.hasPermissions(getActivity(), AppConstants.READ_STORAGE_PERMISSION)) {
            requestPermissions(
                    new String[]{AppConstants.READ_STORAGE_PERMISSION},
                    AppConstants.REQUEST_READ_STORAGE_CODE);
        } else {
            createDirectory();

           setUpListView();

        }


    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        recordStart.setOnClickListener(this);
        recordStop.setOnClickListener(this);
        btnManageReminders.setOnClickListener(this);
    }


    /**
     * method to initialize media recorder
     */

    private void initializeMediaRecorder() {
        duration=0;
        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        audioFilePath = AppConstants.PATH + "/audio" + "_" + System.currentTimeMillis() + ".3gp";
        myAudioRecorder.setOutputFile(audioFilePath);
        try {
            myAudioRecorder.prepare();
        } catch (IOException e) {
            e.printStackTrace();
            isRecording = false;
            recordStart.setEnabled(true);
            recordStop.setEnabled(false);
        }
    }
    /*
        * method to initialize mediaplayer
        * */
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
    }

    private void createDirectory() {
        File dir = new File(AppConstants.PATH);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                Log.d(AppConstants.TAG, "Oops! Failed create  directory");
                return;
            }
        }
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
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;

            case R.id.recordStart:
                if (!UtilsMethods.hasPermissions(getActivity(), AppConstants.WRITE_STORAGE_PERMISSION)) {
                    requestPermissions(
                            new String[]{AppConstants.WRITE_STORAGE_PERMISSION},
                            AppConstants.REQUEST_WRITE_STORAGE_CODE);
                    return;
                }

                if (!UtilsMethods.hasPermissions(getActivity(), AppConstants.READ_STORAGE_PERMISSION)) {
                    requestPermissions(
                            new String[]{AppConstants.READ_STORAGE_PERMISSION},
                            AppConstants.REQUEST_READ_STORAGE_CODE);
                }

                else {
                    if (!UtilsMethods.hasPermissions(getActivity(), AppConstants.RECORD_AUDIO)) {
                        requestPermissions(
                                new String[]{AppConstants.RECORD_AUDIO},
                                AppConstants.REQUEST_RECORD_AUDIO_CODE);
                    } else {
                        if (!isRecording) {
                            initializeMediaRecorder();
                            myAudioRecorder.start();
                            UtilsMethods.showToast(getActivity(), "Recording Started..");
                            isRecording = true;
                            recordStart.setEnabled(false);
                            recordStop.setEnabled(true);
                            startCountDownTimer();
                        }
                    }
                }

                break;
            case R.id.recordStop:
                if (myAudioRecorder != null) {
                    if (isRecording) {
                        myAudioRecorder.stop();
                        myAudioRecorder.release();
                        UtilsMethods.showToast(getActivity(), "Recording Stopped..");
                        cancelCountDownTimer();
                        recordStart.setEnabled(true);
                        recordStop.setEnabled(false);
                        isRecording = false;

                        refreshStorage(new File(audioFilePath));


                    }
                }

                break;
            case R.id.btnManageReminders:
                if(UtilsMethods.isLessonHaveAnAffirmation(getActivity(),lesson_id)){
                    ScheduleReminderFragment scheduleReminderFragment= new ScheduleReminderFragment();
                    Bundle bundle=new Bundle();
                    bundle.putString(Keys.LESSON_ID,lesson_id);
                    bundle.putString(Keys.LESSON_TITLE,lesson_title);
                    scheduleReminderFragment.setArguments(bundle);
                    UtilsMethods.replaceFragment(getActivity(),scheduleReminderFragment);
                }else{
                    UtilsMethods.scheduleReminderAlertDialog(getActivity(),getString(R.string.schedule_reminder_lesson_error_msg));
                }


                break;
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case AppConstants.REQUEST_READ_STORAGE_CODE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.e("permission_granted", "storage_permission_granted");
                    createDirectory();
                 //   audioBeanArrayList = getAudioFileList();
                  // setUpListView();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!showRationale) {
                        // user denied flagging NEVER ASK AGAIN
                        Log.e("user denied ", "flagging NEVER ASK AGAIN");
                        UtilsMethods.promptSettings(getActivity(), getString(R.string.storage_permission), getResources().getString(R.string.read_storage_permission_denied_message));

                    } else {
                        // user denied WITHOUT never ask again
                        Log.e("user denied ", "without NEVER ASK AGAIN");
                    }

                }


                break;

            case AppConstants.REQUEST_RECORD_AUDIO_CODE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Log.e("permission_granted", "record_audio_permission_granted");

                   initializeMediaRecorder();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                    boolean showRationale = shouldShowRequestPermissionRationale(permissions[0]);
                    if (!showRationale) {
                        // user denied flagging NEVER ASK AGAIN
                        Log.e("user denied ", "flagging NEVER ASK AGAIN");
                        UtilsMethods.promptSettings(getActivity(), getString(R.string.record_audio_permission), getResources().getString(R.string.record_audio_permission_denied_message));

                    } else {
                        // user denied WITHOUT never ask again
                        Log.e("user denied ", "without NEVER ASK AGAIN");
                    }

                }


                break;

            case AppConstants.REQUEST_WRITE_STORAGE_CODE:

                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    if (!UtilsMethods.hasPermissions(getActivity(), AppConstants.READ_STORAGE_PERMISSION)) {
                        requestPermissions(
                                new String[]{AppConstants.READ_STORAGE_PERMISSION},
                                AppConstants.REQUEST_READ_STORAGE_CODE);
                    }

                }
                break;


        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (myAudioRecorder != null) {
            if (isRecording) {
                myAudioRecorder.stop();
                myAudioRecorder.release();
                UtilsMethods.showToast(getActivity(), "Recording Stopped..");
                cancelCountDownTimer();
                recordStart.setEnabled(true);
                recordStop.setEnabled(false);
                isRecording = false;
                UtilsMethods.refreshStorage(getActivity(), new File(audioFilePath));
            }
        }
        releaseMediaPlayer();
    }

    /**
     * method to get list of all recorded audio
     */

    private ArrayList<AudioBean> getAudioFileList() {
        String[] projection = {MediaStore.Audio.Media._ID,
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION};
        Cursor audioCursor = getActivity().getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                projection,
                MediaStore.Audio.Media.DATA + " like ? ",
                new String[]{"%" + AppConstants.PATH + "%"},
                null);

        if (audioCursor != null && audioCursor.moveToFirst()) {
            ArrayList<AudioBean> audioBeanArrayList = new ArrayList<>();

            do {
                AudioBean audioBean = new AudioBean();
                String title = audioCursor.getString(audioCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                audioBean.setAudio_path(AppConstants.PATH + "/" + title);
                String[] name = title.split("_");
                audioBean.setTitle(name[0]);
                String id = audioCursor.getString(audioCursor.getColumnIndex(MediaStore.Audio.Media._ID));
                long duration = audioCursor.getLong(audioCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

                audioBean.setDuration(UtilsMethods.getAudioDuration(duration));

                Log.e(AppConstants.TAG, "audio_name=" + title + ",dur" + duration);
                if(duration>1000){
                    audioBeanArrayList.add(audioBean);
                }


            } while (audioCursor.moveToNext());
            Collections.reverse(audioBeanArrayList);
            return audioBeanArrayList;
        } else {
            return null;
        }
    }

    /**method to refresh storage
     * @param file
     * */
    private void refreshStorage(File file) {
        MediaScannerConnection.scanFile(getActivity(),
                new String[]{file.toString()}, null,
                new MediaScannerConnection.OnScanCompletedListener() {
                    public void onScanCompleted(String path, Uri uri) {
                        Log.i("ExternalStorage", "Scanned " + path + ":");
//                        audioBeanArrayList.isEmpty();



                        if (getActivity() != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seekBar.setProgress(0);
                                    if(duration>1000) {
                                        DataInsertionInDatabase dataInsertionInDatabase = DataInsertionInDatabase.getInstance(getActivity());
                                        boolean success = dataInsertionInDatabase.insertDataInAudioAffirmationTable(lesson_id, lesson_title, audioFilePath, duration + "", System.currentTimeMillis() + "");
                                        if (success) {
                                            Log.e("audio_affirmation", "audio affirmation added successfully");
                                            setUpListView();
                                        } else {
                                            Log.e("audio_affirmation", "audio affirmation failed");
                                        }
                                    }
                                }
                            });
                        }
                    }
                });
    }
  private void  setUpListView(){
      audioBeanArrayList= dbGetDataFromDataBase.getAudioAffirmation(lesson_id);
      if (audioBeanArrayList != null) {
          if (audioBeanArrayList.size() > 0) {
              audioListAdapter = new AudioListAdapter(getActivity(), audioBeanArrayList);
              audioList.setAdapter(audioListAdapter);
              audioListAdapter.setListener(new RecyclerViewClickListener() {
                  @Override
                  public void recyclerViewListClicked(View v, int position) {

                      if(mediaPlayer!=null){
                          if(mediaPlayer.isPlaying()){
                              mediaPlayer.pause();
                        //      mediaPlayer.reset();
                              imageViewPlay.setImageResource(R.drawable.play_icon_small);
                              if(prev_pos!=position){
                                      mediaPlayer.reset();
                                      imageViewPlay=(ImageView)v;
                                      playSong(position);
                              }

                          }else{
                              imageViewPlay=(ImageView)v;
                           //   playSong(position);
                              if (!mediaPlayer.isPlaying())
                                  mediaPlayer.start();
                                 imageViewPlay.setImageResource(R.drawable.pause_icon);
                          }
                      }else{
                          imageViewPlay=(ImageView)v;
                          playSong(position);
                      }
                      prev_pos=position;
                  }
              });
          }
      }
    }


    public void playSong(int pos) {
        if (audioBeanArrayList.size() > 0) {

            if (mediaPlayer == null) {
                initializeMediaPlayer();
            }
            try {

                mediaPlayer.setDataSource((audioBeanArrayList.get(pos).getAudio_path())); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
                mediaPlayer.prepare(); // you must call this method after setup the datasource in setDataSource method. After calling prepare() the instance of MediaPlayer starts load data from URL to internal buffer.

            } catch (Exception e) {
                e.printStackTrace();
            }
            // gets the song length in milliseconds from URL
            if (!mediaPlayer.isPlaying()) {
                mediaPlayer.start();
                imageViewPlay.setImageResource(R.drawable.pause_icon);


            } else {
                mediaPlayer.pause();
                imageViewPlay.setImageResource(R.drawable.play_icon_small);

            }
        } else {
            UtilsMethods.showToast(getActivity(), "Audio not found.");
            imageViewPlay.setImageResource(R.drawable.play_icon_small);
        }
    }

    /**
     * method is called when song is completed
     *
     * @param mp
     */

    @Override
    public void onCompletion(MediaPlayer mp) {
       imageViewPlay.setImageResource(R.drawable.play_icon_small);
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer=null;
    }


    private void releaseMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.isPlaying();
            {
                mediaPlayer.pause();
                mediaPlayer.reset();

            }

        }
    }

    CountDownTimer countDownTimer;
    long millisInFuture=20000;
    long countDownInterval = 1000; //1 second
    boolean isRunning = false;

    private void startCountDownTimer() {

        countDownTimer = new CountDownTimer(millisInFuture, countDownInterval) {
            public void onTick(long millisUntilFinished) {
                //do something in every tick
                isRunning = true;
                duration=duration+1000;
                seekBar.setProgress((int) (duration/1000));
                Log.e("dur",duration+"");
            }

            public void onFinish() {
                duration=duration+1000;
                isRunning= false;
                seekBar.setProgress((int) (duration/1000));
                if (myAudioRecorder != null) {
                    if (isRecording) {
                        myAudioRecorder.stop();
                        myAudioRecorder.release();
                        UtilsMethods.showToast(getActivity(), "Recording Stopped..");
                        cancelCountDownTimer();
                        recordStart.setEnabled(true);
                        recordStop.setEnabled(false);
                        isRecording = false;

                        refreshStorage(new File(audioFilePath));


                    }
                }
            }
        }.start();

    }

private void cancelCountDownTimer(){
    if(isRunning && countDownTimer!=null){
        countDownTimer.cancel();
        seekBar.setProgress(0);
    }
}
}
