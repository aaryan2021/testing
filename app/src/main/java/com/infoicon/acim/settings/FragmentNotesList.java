package com.infoicon.acim.settings;

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
import com.infoicon.acim.adapter.NoteListAdapter;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.Notes;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;

/**
 * Created by sumit on 28/9/17.
 */

public class FragmentNotesList extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private RecyclerView notesList;
    private NoteListAdapter noteListAdapter;
    private ArrayList<Notes> notesArrayList;
    private ImageButton btnBack;
    private String heading,notes_type;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private LinearLayout emptyNotes;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        notes_type=getArguments().getString(Keys.NOTES_TYPE);
        heading=getArguments().getString(Keys.TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_notes_list, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        initListeners();
        getNotesList();
        setUpBackGround();
    }

    @Override
    public void initViews() {
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText(heading);
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);
        notesList = (RecyclerView) rootView.findViewById(R.id.notesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notesList.setLayoutManager(linearLayoutManager);
        notesList.setNestedScrollingEnabled(false);
        emptyNotes=(LinearLayout)rootView.findViewById(R.id.layoutEmptyNotes);
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
    }
    private void setUpBackGround() {
        RelativeLayout main=(RelativeLayout)getActivity().findViewById(R.id.main);
        main.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.white));
        View toolbar=(View)getActivity().findViewById(R.id.toolbar);
        toolbar.setVisibility(View.VISIBLE);
    }

    private void getNotesList(){
        dbGetDataFromDataBase=DbGetDataFromDataBase.getInstance(getActivity());
        notesArrayList= dbGetDataFromDataBase.getNotes(notes_type);
        if(notesArrayList!=null){
            noteListAdapter=new NoteListAdapter(getActivity(),getActivity().getSupportFragmentManager(),notesArrayList, AppConstants.MANAGE_NOTES);
            notesList.setAdapter(noteListAdapter);
        }else{
            emptyNotes.setVisibility(View.VISIBLE);
            notesList.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                UtilsMethods.hideSoftKeyboard(getActivity());
                FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
                break;


        }
    }
}


