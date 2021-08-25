package com.infoicon.acim.settings;

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
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

/**
 * Created by sumit on 28/9/17.
 */

public class FragmentManageNotes extends BaseFragment implements View.OnClickListener {



    private View rootView;
    private CustomTextView title;
    private ImageButton btnBack;
    private CustomButton btnTextNotes, btnWorkbookNotes, btnManualNotes;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_manage_notes, container, false);
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
        title.setText(R.string.manage_notes);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack = (ImageButton) getActivity().findViewById(R.id.btnBack);
        btnBack.setVisibility(View.VISIBLE);
        btnTextNotes = (CustomButton) rootView.findViewById(R.id.btnTextNotes);
        btnWorkbookNotes = (CustomButton) rootView.findViewById(R.id.btnWorkbookNotes);
        btnManualNotes = (CustomButton) rootView.findViewById(R.id.btnManualNotes);

    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        btnTextNotes.setOnClickListener(this);
        btnWorkbookNotes.setOnClickListener(this);
        btnManualNotes.setOnClickListener(this);


    }

    private void setUpBackGround() {
        RelativeLayout main = (RelativeLayout) getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.white));
        View toolbar = (View) getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnTextNotes:

                replaceFragment("Text Notes",Keys.NOTES_TYPE_TEXT);
                break;
            case R.id.btnWorkbookNotes:
                replaceFragment("Workbook Notes",Keys.NOTES_TYPE_WORKBOOK);
                break;
            case R.id.btnManualNotes:
                replaceFragment("Manual Notes",Keys.NOTES_TYPE_TEACHER_MANUAL);
                break;

            case R.id.btnBack:
                UtilsMethods.hideSoftKeyboard(getActivity());
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();

                fragmentManager.popBackStack();
                break;
        }
    }

    private void replaceFragment(String title,String notes_type){
        FragmentNotesList fragmentNotesList = new FragmentNotesList();
        Bundle bundle = new Bundle();
        bundle.putString(Keys.TITLE,title);
        bundle.putString(Keys.NOTES_TYPE, notes_type);
        fragmentNotesList.setArguments(bundle);
        UtilsMethods.replaceFragment(getActivity(), fragmentNotesList);
    }
}
