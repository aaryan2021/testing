package com.infoicon.acim.workbook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.infoicon.acim.adapter.WorkbookLessonListAdapter;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.R;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.retrofit.ServiceCreator;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.SectionedRecyclerViewAdapter;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by sumit on 16/8/17.
 */

public class WorkbookFragment extends BaseFragment implements View.OnClickListener{


    private View rootView;
    private CustomTextView title;
    private LinearLayout layout_workbook;
    private SectionedRecyclerViewAdapter sectionAdapter;
    private List<GetWorkbookLessonResponse.DataBean> dataChildBeanList;
    private RecyclerView recyclerView;
    private WorkbookLessonListAdapter workbookLessonListAdapter;
    private LinearLayout retry;
    private ImageButton btnBack;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataChildBeanList=new ArrayList<>();
        callWorkbookLessonsWebservice();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView=inflater.inflate(R.layout.fragment_workbook,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        setUpRecyclerView();
        setUpBackGround();
    }

   
    @Override
    public void onStart() {
        super.onStart();
        if (workbookLessonListAdapter != null && dataChildBeanList.size() > 0) {
            if (dataChildBeanList.size() > 0) {
                for(int i=0;i<dataChildBeanList.size();i++){
                    workbookLessonListAdapter=new WorkbookLessonListAdapter(getActivity(),dataChildBeanList.get(i).getHeading(), dataChildBeanList.get(i).getDatachild());
                    sectionAdapter.addSection(workbookLessonListAdapter);
                }
                recyclerView.setAdapter(sectionAdapter);
            }
        }
    }

    @Override
    public void initViews() {
        title=(CustomTextView)getActivity().findViewById(R.id.title);
        title.setText("Workbook");
        layout_workbook=(LinearLayout)getActivity().findViewById(R.id.layout_workbook);
        sectionAdapter = new SectionedRecyclerViewAdapter();
        retry=(LinearLayout) rootView.findViewById(R.id.retry);
        retry.setVisibility(View.GONE);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.GONE);
    }

    @Override
    public void initListeners() {
        layout_workbook.setOnClickListener(this);
        retry.setOnClickListener(this);
    }

    private void setUpRecyclerView(){
         recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_workbook:

                break;
            case R.id.retry:
                callWorkbookLessonsWebservice();
                break;
        }
    }

    private void callWorkbookLessonsWebservice(){
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty(Keys.TYPE,Keys.WORKBOOK);

        final retrofit.Call<GetWorkbookLessonResponse> call = AppRetrofit.getAppRetrofitInstance()
                .getApiServices().getWorkbookLessonResponseCall(jsonObject);
        new ServiceCreator(getActivity()).enqueueCall(call,true, new Callback<GetWorkbookLessonResponse>() {

            @Override
            public void onResponse(Response<GetWorkbookLessonResponse> response, Retrofit retrofit) {
                retry.setVisibility(View.GONE);
                recyclerView.setVisibility(View.VISIBLE);
                GetWorkbookLessonResponse getWorkbookLessonResponse=response.body();
                if(getWorkbookLessonResponse.getSuccess().equals("true")){

                    dataChildBeanList=getWorkbookLessonResponse.getData();
                    if (dataChildBeanList.size() > 0) {
                        for(int i=0;i<dataChildBeanList.size();i++){
                            workbookLessonListAdapter=new WorkbookLessonListAdapter(getActivity(),dataChildBeanList.get(i).getHeading(), dataChildBeanList.get(i).getDatachild());
                            sectionAdapter.addSection(workbookLessonListAdapter);
                        }
                        recyclerView.setAdapter(sectionAdapter);
                    }
                }else{
                    UtilsMethods.getToast(getActivity(),getWorkbookLessonResponse.getMsg());
                    //UtilsMethods.showToast(getActivity(),getWorkbookLessonResponse.getMsg());
                }
            }

            @Override
            public void onFailure(Throwable t) {
               // UtilsMethods.showToast(getActivity(),"Error...");
                retry.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        });

    }


}
