package com.infoicon.acim.text;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.TextChapterAdapter;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;

import java.util.HashMap;
import java.util.List;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER;

/**
 * Created by sumit on 18/8/17.
 */

public class
TextChapterFragment extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private RecyclerView recyclerView;
    private String name;
    private ImageButton btnBack;
    private List<GetChapterApiResponse.DataBean.ChaptersBean.TopicsBean> lessonTopicBeen;
    private Fragment fragment;
    private int lastPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            name = getArguments().getString(Keys.TITLE);
            lastPosition = getArguments().getInt(Keys.POSITION);
            lessonTopicBeen = getArguments().getParcelableArrayList("array");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_workbook, container, false);
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
    public void initViews() {
        title = getActivity().findViewById(R.id.title);
        String[] title_name = name.split("@");
        title.setText(title_name[0] + "\n" + title_name[1].trim());
        title.setMaxLines(2);
        title.setGravity(Gravity.LEFT);
        btnBack = getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        if (getTargetFragment() != null) {
            fragment = getTargetFragment();
        }

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
    }

    private void setUpRecyclerView() {
        recyclerView = rootView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new TextChapterAdapter(getActivity(), this, lessonTopicBeen));
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
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 145) {
            if (resultCode == Activity.RESULT_OK) {
                lessonTopicBeen.get(data.getIntExtra(Keys.POSITION, -1)).setBookMarked(1);
                recyclerView.getAdapter().notifyDataSetChanged();
                Toast.makeText(getActivity(), "Ok", Toast.LENGTH_SHORT).show();
                HashMap<String, String> hashMap = User.getInstance(getContext()).getBookmarkData();
                hashMap.put(TEXT_CHAPTER, name);
                User.getInstance(getContext()).saveBookmarkData(hashMap);
                Intent intent = new Intent();
                intent.putExtra(Keys.POSITION,lastPosition);
                fragment.onActivityResult(155, Activity.RESULT_OK, intent);
            }

        }
    }
}

