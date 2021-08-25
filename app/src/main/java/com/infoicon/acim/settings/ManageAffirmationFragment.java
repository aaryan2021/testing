package com.infoicon.acim.settings;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.AudioListAdapter;
import com.infoicon.acim.adapter.TextAffirmationAdapter;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;

/**
 * Created by sumit on 5/10/17.
 */

public class ManageAffirmationFragment extends BaseFragment implements View.OnClickListener,MediaPlayer.OnCompletionListener {

    private View rootView;
    private RecyclerView recyclerViewTextAffirmation,recyclerViewAudioAffirmation;
    private NestedScrollView nestedScroll;
    private CustomTextView textViewTextAffirmation,textViewAudioAffirmation;
    private LinearLayout layoutEmptyNotes;
    private ArrayList<TextAffirmationBean> textAffirmationBeanArrayList;
    private ArrayList<AudioAffirmationBean> audioAffirmationBeanArrayList;
    private TextAffirmationAdapter textAffirmationAdapter;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private AudioListAdapter audioListAdapter;
    private MediaPlayer mediaPlayer;
    private ImageView imageViewPlay;
    private ImageButton btnBack;
    private int prev_pos=-1;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_manage_affirmation,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
        setAffirmationList();

    }

    @Override
    public void initViews() {
        dbGetDataFromDataBase= DbGetDataFromDataBase.getInstance(getActivity());
        CustomTextView title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.affirmation_list);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        textViewTextAffirmation=(CustomTextView)rootView.findViewById(R.id.textViewTextAffirmation);
        textViewAudioAffirmation=(CustomTextView)rootView.findViewById(R.id.textViewAudioAffirmation);
        layoutEmptyNotes=(LinearLayout) rootView.findViewById(R.id.layoutEmptyNotes);
        nestedScroll=(NestedScrollView)rootView.findViewById(R.id.nestedScroll);

        recyclerViewTextAffirmation=(RecyclerView)rootView.findViewById(R.id.recyclerViewTextAffirmation);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewTextAffirmation.setLayoutManager(linearLayoutManager);
        recyclerViewTextAffirmation.setNestedScrollingEnabled(false);

        recyclerViewAudioAffirmation=(RecyclerView)rootView.findViewById(R.id.recyclerViewAudioAffirmation);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerViewAudioAffirmation.setLayoutManager(linearLayoutManager1);
        recyclerViewAudioAffirmation.setNestedScrollingEnabled(false);

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
    }
    /*
          * method to initialize media player
          * */
    private void initializeMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
        }
        }

        private void setAffirmationList(){

        textAffirmationBeanArrayList = dbGetDataFromDataBase.getAllTextAffirmation();
        if(textAffirmationBeanArrayList !=null){
            textViewTextAffirmation.setVisibility(View.VISIBLE);
            textAffirmationAdapter=new TextAffirmationAdapter(getActivity(),getActivity().getSupportFragmentManager(), textAffirmationBeanArrayList, AppConstants.MANAGE_AFFIRMATION);
            recyclerViewTextAffirmation.setAdapter(textAffirmationAdapter);

        }else {
            textViewTextAffirmation.setVisibility(View.GONE);
        }



        audioAffirmationBeanArrayList =
                dbGetDataFromDataBase.getAllAudioAffirmation();
        if(audioAffirmationBeanArrayList !=null){
            if(audioAffirmationBeanArrayList.size()>0){
                textViewAudioAffirmation.setVisibility(View.VISIBLE);
                setUpListView();
            }else{
                textViewAudioAffirmation.setVisibility(View.GONE);
            }
        }else{
            textViewAudioAffirmation.setVisibility(View.GONE);
        }


        if(textAffirmationBeanArrayList ==null && audioAffirmationBeanArrayList ==null){
            nestedScroll.setVisibility(View.GONE);
            layoutEmptyNotes.setVisibility(View.VISIBLE);
        }else{
                if(audioAffirmationBeanArrayList!=null & textAffirmationBeanArrayList!=null){
                    if(audioAffirmationBeanArrayList.size()==0 & textAffirmationBeanArrayList.size()==0){
                        nestedScroll.setVisibility(View.GONE);
                        layoutEmptyNotes.setVisibility(View.VISIBLE);
                    }else{
                        nestedScroll.setVisibility(View.VISIBLE);
                        layoutEmptyNotes.setVisibility(View.GONE);
                    }
                }else{
                    nestedScroll.setVisibility(View.VISIBLE);
                    layoutEmptyNotes.setVisibility(View.GONE);
                }

        }
    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    private void  setUpListView(){
            if (audioAffirmationBeanArrayList.size() > 0) {
                audioListAdapter = new AudioListAdapter(getActivity(), audioAffirmationBeanArrayList);
                recyclerViewAudioAffirmation.setAdapter(audioListAdapter);
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
    public void playSong(int pos) {
        if (audioAffirmationBeanArrayList.size() > 0) {

            if (mediaPlayer == null) {
                initializeMediaPlayer();
            }
            try {

                mediaPlayer.setDataSource((audioAffirmationBeanArrayList.get(pos).getAudio_path())); // setup song from https://www.hrupin.com/wp-content/uploads/mp3/testsong_20_sec.mp3 URL to mediaplayer data source
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
    }
}
