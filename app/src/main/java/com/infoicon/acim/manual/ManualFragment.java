package com.infoicon.acim.manual;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.adapter.ManualListAdapter;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.GetTeacherManualResponse;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sumit on 16/8/17.
 */

public class ManualFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private RecyclerView manualList;
    private ManualListAdapter manualListAdapter;
    private List<GetTeacherManualResponse.DataBean> dataBeanArrayList;
    private ImageButton btnBack;
    private LinearLayout retry;
    public  int MANUAL_CLICKED_POS;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MANUAL_CLICKED_POS=0;
        dataBeanArrayList=new ArrayList<>();
        callTeacherManualWebService();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView=inflater.inflate(R.layout.fragment_manual,container,false);
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
    public void onStart() {
        super.onStart();
        if (manualListAdapter != null && dataBeanArrayList.size() > 0) {
            manualListAdapter=new ManualListAdapter(getActivity(),dataBeanArrayList,this);
            manualList.setAdapter(manualListAdapter);
            manualList.scrollToPosition(MANUAL_CLICKED_POS);
            manualListAdapter.setListener(new RecyclerViewClickListener() {
                @Override
                public void recyclerViewListClicked(View v, int position) {
                    MANUAL_CLICKED_POS=position;
                    ManualDescFragment manualDescFragment=new ManualDescFragment();
                    Bundle bundle=new Bundle();
                    manualDescFragment.setTargetFragment(ManualFragment.this, 146);

                    bundle.putString(Keys.TITLE,dataBeanArrayList.get(position).getName());
                    bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) dataBeanArrayList);
                    bundle.putInt(Keys.POSITION,position);
                    manualDescFragment.setArguments(bundle);
                    UtilsMethods.replaceFragment(getActivity(), manualDescFragment);
                }
            });
        }
    }
    void switchs(int position)
    {

    }
    @Override
    public void initViews() {
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText("Manual for teachers");
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.GONE);
        manualList = (RecyclerView) rootView.findViewById(R.id.manualList);
        manualList.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        manualList.setLayoutManager(linearLayoutManager);
        retry=(LinearLayout) rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
    }

    @Override
    public void initListeners() {
        retry.setOnClickListener(this);


    }
    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    private void callTeacherManualWebService(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty(Keys.TYPE,Keys.TEACHER_MANUAL);

        final retrofit.Call<GetTeacherManualResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getTeacherManualResponseCall(jsonObject);


        new ServiceCreator(getActivity()).enqueueCall(call, true,new Callback<GetTeacherManualResponse>() {

            @Override
            public void onResponse(Response<GetTeacherManualResponse> response, Retrofit retrofit) {
                manualList.setVisibility(View.VISIBLE);
                retry.setVisibility(View.GONE);
                GetTeacherManualResponse getTeacherManualResponse=response.body();
                if(getTeacherManualResponse.getSuccess().equals("true")){
                    dataBeanArrayList=getTeacherManualResponse.getData();

                    manualListAdapter=new ManualListAdapter(getActivity(),dataBeanArrayList,ManualFragment.this);
                    manualList.setAdapter(manualListAdapter);
                    manualListAdapter.setListener(new RecyclerViewClickListener() {
                        @Override
                        public void recyclerViewListClicked(View v, int position) {
                            MANUAL_CLICKED_POS=position;
                            ManualDescFragment manualDescFragment=new ManualDescFragment();
                            manualDescFragment.setTargetFragment(ManualFragment.this,146);
                            Bundle bundle=new Bundle();
                            bundle.putString(Keys.TITLE,dataBeanArrayList.get(position).getName());
                            bundle.putParcelableArrayList(Keys.ARRAY, (ArrayList<? extends Parcelable>) dataBeanArrayList);
                            bundle.putInt(Keys.POSITION,position);
                            manualDescFragment.setArguments(bundle);
                            UtilsMethods.replaceFragment(getActivity(), manualDescFragment);
                        }
                    });
                }else{
                    UtilsMethods.getToast(getActivity(),getTeacherManualResponse.getMsg());
                    //UtilsMethods.showToast(getActivity(),getTeacherManualResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                //UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                manualList.setVisibility(View.GONE);
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.retry:
                callTeacherManualWebService();
                break;
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 146) {
            if (resultCode == Activity.RESULT_OK) {
                dataBeanArrayList.get(data.getIntExtra(Keys.POSITION, -1)).setBookMarked(1);
                manualListAdapter.notifyDataSetChanged();
                UtilsMethods.getToast(getActivity(),"Ok");
                //Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
//                HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
//                hashMap.put(TEXT_CHAPTER, name);
//                User.getInstance(getContext()).saveBookmarkData(hashMap);
//                Intent intent = new Intent();
//                intent.putExtra(Keys.POSITION,lastPosition);
                //fragment.onActivityResult(155, Activity.RESULT_OK, intent);
            }

        }
    }
}

