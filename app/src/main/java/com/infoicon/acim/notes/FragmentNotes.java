package com.infoicon.acim.notes;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infoicon.acim.R;
import com.infoicon.acim.adapter.NoteListAdapter;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.baseclasses.BaseFragment;
import com.infoicon.acim.bean.Notes;
import com.infoicon.acim.utils.customviews.CustomButton;
import com.infoicon.acim.utils.customviews.CustomEditText;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DataInsertionInDatabase;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;

/**
 * Created by sumit on 17/8/17.
 */

public class FragmentNotes extends BaseFragment implements View.OnClickListener {


    private View rootView;
    private CustomTextView title;
    private RecyclerView notesList;
    private NoteListAdapter noteListAdapter;
    private ArrayList<Notes> notesArrayList;
    private ImageButton btnBack;
    private EditText editTextTitle,editTextDesc;
    private CustomButton btnSave;
    private String topic_id,notes_type;
    private String note_title,note_desc;
    private DbGetDataFromDataBase dbGetDataFromDataBase;
    private String time;
    boolean isUpdate;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
          getBundleData();
     }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        rootView = inflater.inflate(R.layout.fragment_notes, container, false);
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

    private void getBundleData() {
        if (getArguments().containsKey(Keys.TIME_CREATED)) {
            note_desc = getArguments().getString(Keys.NOTE_DESC);
            note_title = getArguments().getString(Keys.NOTE_TITLE);
            notes_type=getArguments().getString(Keys.NOTES_TYPE);
            time=getArguments().getString(Keys.TIME_CREATED);
            isUpdate=true;

        }else{
            topic_id=getArguments().getString(Keys.TOPIC_ID);
            notes_type=getArguments().getString(Keys.NOTES_TYPE);

        }

    }
    @Override
    public void initViews() {
        title = (CustomTextView) getActivity().findViewById(R.id.title);
        title.setText("Notes");
        title.setMaxLines(1);
        title.setGravity(Gravity.CENTER);
        btnBack=(ImageButton)getActivity().findViewById(R.id.btnBack) ;
        btnBack.setVisibility(View.VISIBLE);

        editTextTitle=(CustomEditText)rootView.findViewById(R.id.editTextTitle);
        editTextTitle.setSingleLine(true);
        editTextDesc=(CustomEditText)rootView.findViewById(R.id.editTextDesc);
        btnSave=(CustomButton)rootView.findViewById(R.id.btnSave);

        notesList = (RecyclerView) rootView.findViewById(R.id.notesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        notesList.setLayoutManager(linearLayoutManager);
        notesList.setNestedScrollingEnabled(false);
        if(isUpdate){
            editTextDesc.setText(note_desc);
            editTextTitle.setText(note_title);
            btnSave.setText(getResources().getString(R.string.update));
        }else {
            isUpdate=false;
            btnSave.setText(getResources().getString(R.string.save));
        }

      /*  editTextDesc.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    saveNotes();
                    return true;
                }
                return false;
            }
        });*/
    }

    @Override
    public void initListeners() {
        btnBack.setOnClickListener(this);
        btnSave.setOnClickListener(this);
       /* editTextTitle.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);
                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
                return false;
            }
        });*/
       /* editTextDesc.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {

                v.getParent().requestDisallowInterceptTouchEvent(true);

                switch (event.getAction() & MotionEvent.ACTION_MASK){
                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        return true;
                }
                return false;
            }
        });*/
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
            noteListAdapter=new NoteListAdapter(getActivity(),getActivity().getSupportFragmentManager(),notesArrayList, AppConstants.ADD_NOTES);
            notesList.setAdapter(noteListAdapter);
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
            case R.id.btnSave:
                saveNotes();

                break;

        }
    }


    private boolean isValidData(){
        note_title=editTextTitle.getText().toString().trim();
        note_desc=editTextDesc.getText().toString().trim();

        if(editTextTitle.getText().toString().isEmpty()){
            editTextTitle.requestFocus();
            editTextTitle.setError(getString(R.string.add_note_title));
            return false;
        }else if(editTextDesc.getText().toString().isEmpty()){
            editTextDesc.requestFocus();
            editTextDesc.setError(getString(R.string.add_note_description));
            return false;
        }
        return true;
    }
    private void saveNotes(){
        UtilsMethods.hideSoftKeyboard(getActivity());

        if(isUpdate){
            if(isValidData()){
                DataInsertionInDatabase dataInsertionInDatabase=DataInsertionInDatabase.getInstance(getActivity());
                boolean success=dataInsertionInDatabase.updateDataInNotesTable(notes_type,note_title,note_desc,time);
                if(success){

                    UtilsMethods.showToast(getActivity(),getString(R.string.notes_updated_successfully));
                    editTextTitle.setText("");
                    editTextDesc.setText("");
                    getNotesList();
                    isUpdate=false;
                    btnSave.setText(getResources().getString(R.string.save));
                }else{
                    UtilsMethods.showToast(getActivity(),getString(R.string.failed_to_update_notes));
                }
            }
        }else{
            if(isValidData()) {
                DataInsertionInDatabase dataInsertionInDatabase = DataInsertionInDatabase.getInstance(getActivity());
                boolean success = dataInsertionInDatabase.insertDataInNotesTable(notes_type, note_title, note_desc, System.currentTimeMillis() + "");
                if (success) {
                    UtilsMethods.showToast(getActivity(), getString(R.string.notes_added_successfully));
                    editTextTitle.setText("");
                    editTextDesc.setText("");
                    getNotesList();
                } else {
                    UtilsMethods.showToast(getActivity(), getString(R.string.failed_to_add_notes));
                }
            }
        }
    }

}

