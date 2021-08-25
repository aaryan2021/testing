package com.infoicon.acim.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.adapter.TextChapterListAdapter;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.introduction.FragmentIntroduction;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
/**
 * Created by sumit on 16/8/17.
 */

public class TextFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private RecyclerView textList;
    private TextChapterListAdapter chapterListAdapter;
    private List<GetChapterApiResponse.DataBean.ChaptersBean> chaptersBeanArrayList;
    private LinearLayout linearLayout;
    private RelativeLayout foreword,introduction;
    private LinearLayout retry;
    private ImageButton btnBack;
    public static int CLICKED_POSITION=0;
    private NestedScrollView nestedScroll;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CLICKED_POSITION=0;
        chaptersBeanArrayList=new ArrayList<>();
        callChapterWebservice();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_text,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpBackGround();
    }
    private void setUpBackGround() {
        RelativeLayout main= getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar= getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (chapterListAdapter != null && chaptersBeanArrayList.size() > 0) {
            if (chaptersBeanArrayList.size() > 0) {
                linearLayout.setVisibility(View.VISIBLE);
                chapterListAdapter=new TextChapterListAdapter(getActivity(),this,chaptersBeanArrayList);
                textList.setAdapter(chapterListAdapter);
                textList.smoothScrollToPosition(CLICKED_POSITION);

            }
        }
    }


    @Override
    public void initViews() {
        nestedScroll= rootView.findViewById(R.id.nestedScroll);
        title= getActivity().findViewById(R.id.title);
        title.setText("Text");
        title.setGravity(Gravity.CENTER);
        title.setMaxLines(1);
        btnBack= getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.GONE);
        linearLayout= rootView.findViewById(R.id.linearLayout);
        foreword= rootView.findViewById(R.id.foreword);
        introduction= rootView.findViewById(R.id.introduction);

        textList = rootView.findViewById(R.id.textList);
        textList.setNestedScrollingEnabled(false);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        textList.setLayoutManager(linearLayoutManager);

        retry= rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        linearLayout.setVisibility(View.GONE);

    }

    @Override
    public void initListeners() {
        foreword.setOnClickListener(this);
        introduction.setOnClickListener(this);
        retry.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.foreword:
                callIntroductionFragment("Foreword", AppConstants.TEXT_FOREWARD);
               // UtilsMethods.showToast(getActivity(),"Yet to implement.");
                break;
            case R.id.introduction:
                callIntroductionFragment("Introduction", AppConstants.TEXT_INTRO);
               // UtilsMethods.showToast(getActivity(),"Yet to implement.");
                break;
            case R.id.retry:
                callChapterWebservice();
                break;
        }
    }
    private void callChapterWebservice(){

        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty(Keys.TYPE,Keys.CHAPTERS);

        final retrofit.Call<GetChapterApiResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getChapterApiResponseCall(jsonObject);


        new ServiceCreator(getActivity()).enqueueCall(call, true,new Callback<GetChapterApiResponse>() {

            @Override
            public void onResponse(Response<GetChapterApiResponse> response, Retrofit retrofit) {
                retry.setVisibility(View.GONE);
                GetChapterApiResponse getChapterApiResponse=response.body();
                if(getChapterApiResponse.getSuccess().equals("true")){
                    linearLayout.setVisibility(View.VISIBLE);
                    chaptersBeanArrayList=getChapterApiResponse.getData().getChapters();
                    chapterListAdapter=new TextChapterListAdapter(getActivity(),TextFragment.this,chaptersBeanArrayList);
                    textList.setAdapter(chapterListAdapter);
                }else{
                    UtilsMethods.showToast(getActivity(),getChapterApiResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                linearLayout.setVisibility(View.GONE);
            }
        });
    }


    /*method to open introduction fragment
    * */
    private void callIntroductionFragment(String title, String id){
        FragmentIntroduction fragmentIntroduction=new FragmentIntroduction();
        Bundle bundle=new Bundle();
        bundle.putString(Keys.TITLE,title);
        bundle.putString(Keys.TOPIC_ID,id);
        fragmentIntroduction.setArguments(bundle);
        UtilsMethods.replaceFragment(getActivity(),fragmentIntroduction);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 155){
            if (resultCode == Activity.RESULT_OK){
                if (data != null){
                    chaptersBeanArrayList.get(data.getIntExtra(Keys.POSITION,-1)).setBookmarked(true);
                    chapterListAdapter.notifyDataSetChanged();
                }
            }
        }
    }
}
