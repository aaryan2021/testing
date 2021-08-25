package com.infoicon.acim.notes;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.Keys;

/**
 * Created by sumit on 12/9/17.
 */

public class FragmentNoteDetails extends BaseFragment implements View.OnClickListener {

    private View rootView;
    private CustomTextView title;
    private ImageButton btnBack;
    private CustomTextView textViewNoteTitle,textViewNoteDesc;
    private String note_details,note_title;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        note_title=getArguments().getString(Keys.NOTE_TITLE);
        note_details=getArguments().getString(Keys.NOTE_DESC);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_note_details, container, false);
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
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText("Note Details");
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        textViewNoteTitle=(CustomTextView)rootView.findViewById(R.id.textViewNoteTitle);
        textViewNoteTitle.setText(note_title);
        textViewNoteDesc=(CustomTextView)rootView.findViewById(R.id.textViewNoteDesc);
        textViewNoteDesc.setText(note_details);
    }

    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }
    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
        }
    }
}
