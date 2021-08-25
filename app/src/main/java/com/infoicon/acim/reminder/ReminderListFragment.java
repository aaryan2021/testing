package com.infoicon.acim.reminder;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
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

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.ReminderAudioListAdapter;
import com.infoicon.acim.adapter.ReminderListAdapter;
import com.infoicon.acim.adapter.ReminderTextListAdapter;
import com.infoicon.acim.bean.ReminderBean;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;

import java.util.ArrayList;

import static com.infoicon.acim.R.id.btnBack;

/**
 * Created by sumit on 10/10/17.
 */

public class ReminderListFragment extends BaseFragment implements View.OnClickListener{

    private View rootView;
    private RecyclerView recyclerView;
    private LinearLayout layoutEmptyReminder;
    private ImageButton btnBack;
    private ArrayList<ReminderBean> reminderBeanArrayList;
    private ReminderListAdapter reminderListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.fragment_reminder_list,container,false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpBackGround();
        initViews();
        initListeners();
    }

    @Override
    public void initViews() {
        CustomTextView title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(R.string.reminder_list);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        layoutEmptyReminder=(LinearLayout)rootView.findViewById(R.id.layoutEmptyReminder);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        setUpListView();

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
    }

    private void setUpListView(){
        DbGetDataFromDataBase  dbGetDataFromDataBase= DbGetDataFromDataBase.getInstance(getActivity());
        reminderBeanArrayList = dbGetDataFromDataBase.getAllReminders();
            if(reminderBeanArrayList !=null){
                reminderListAdapter=new ReminderListAdapter(getActivity(),reminderBeanArrayList);
                recyclerView.setAdapter(reminderListAdapter);

            }else{
                layoutEmptyReminder.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }

    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;

        }
    }
}
