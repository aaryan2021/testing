package com.infoicon.acim.workbook;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.infoicon.acim.R;

import com.infoicon.acim.adapter.WorkBookLessonAdapter;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.baseclasses.BaseFragment;

import com.infoicon.acim.bean.GetWorkbookLessonResponse.DataBean;
import com.infoicon.acim.utils.customviews.CustomTextView;

import com.infoicon.acim.utils.AllSelectdTabs;
import com.infoicon.acim.utils.Keys;

import java.util.ArrayList;
import java.util.HashMap;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER;


/**
 * Created by sumit on 2/9/17.
 */

public class WorkBookLessonFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private LinearLayout layout_workbook;
    private AllSelectdTabs allSelectdTabs;
    private RecyclerView recyclerView;
    private String name;
    private ImageButton btnBack;
    private ArrayList<DataBean.DatachildBean.LessonTopicBean> lessonTopicBeen;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        getBundleData();
        initViews();
        initListeners();
        setUpRecyclerView();
        setUpBackGround();
    }

    private void getBundleData() {
        if(getArguments()!=null){
            name=  getArguments().getString(Keys.TITLE);
            lessonTopicBeen= getArguments().getParcelableArrayList("array");

        }
    }

    @Override
    public void initViews() {
        title=(CustomTextView)getActivity().findViewById(R.id.title);
        title.setText(name);
        layout_workbook=(LinearLayout)getActivity().findViewById(R.id.layout_workbook);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);

    }

    @Override
    public void initListeners() {
        layout_workbook.setOnClickListener(this);
        btnBack.setOnClickListener(this);
    }

    private void setUpRecyclerView(){
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new WorkBookLessonAdapter(WorkBookLessonFragment.this,
                getActivity(),lessonTopicBeen));

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
            case R.id.btnBack:
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;

            case R.id.layout_workbook:

                break;
        }
    }


}



