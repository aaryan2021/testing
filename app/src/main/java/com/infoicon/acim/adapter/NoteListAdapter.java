package com.infoicon.acim.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.Notes;
import com.infoicon.acim.database.DbDataDeleteFromDatabase;
import com.infoicon.acim.notes.FragmentNoteDetails;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by sumit on 18/8/17.
 */

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private ArrayList<Notes> notesArrayList;
    private RecyclerViewClickListener itemListener;
    private String type;
    private FragmentManager fragmentManager;

    public NoteListAdapter(FragmentActivity activity, FragmentManager fragmentManager, ArrayList<Notes> arrayList, String type) {
        this.activity = activity;
        this.fragmentManager=fragmentManager;
        this.notesArrayList = arrayList;
        this.type = type;
    }

    public void setListener(RecyclerViewClickListener itemListener) {
        this.itemListener = itemListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notes, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        viewHolder.notesTitle.setText(notesArrayList.get(position).getNotes_title());

       /* if(type.equals(AppConstants.MANAGE_NOTES)){
            viewHolder.btnDetails.setImageResource(R.drawable.edit);
        }else{
            viewHolder.btnDetails.setImageResource(R.drawable.eye_icon);
        }*/
        viewHolder.textViewDate.setText("Created on: " + UtilsMethods.parseNotesCreatedDate(notesArrayList.get(position).getTime_created()));
        viewHolder.btnDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FragmentNoteDetails fragmentNoteDetails = new FragmentNoteDetails();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.NOTE_TITLE, notesArrayList.get(position).getNotes_title());
                bundle.putString(Keys.NOTE_DESC, notesArrayList.get(position).getNotes_desc());
                fragmentNoteDetails.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, fragmentNoteDetails);


            }
        });
        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals(AppConstants.ADD_NOTES)) {
                    fragmentManager.popBackStack();
                }
                FragmentNotes fragmentNotes = new FragmentNotes();
                Bundle bundle = new Bundle();
                bundle.putString(Keys.NOTE_TITLE, notesArrayList.get(position).getNotes_title());
                bundle.putString(Keys.NOTE_DESC, notesArrayList.get(position).getNotes_desc());
                bundle.putString(Keys.NOTES_TYPE, notesArrayList.get(position).getNotes_type());
                bundle.putString(Keys.TIME_CREATED, notesArrayList.get(position).getTime_created());
                fragmentNotes.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, fragmentNotes);
            }
        });
        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(activity, position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    private void showDeleteAlertDialog(final Context mContext, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.delete_alert_message_notes);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DbDataDeleteFromDatabase dbDataDeleteFromDatabase = DbDataDeleteFromDatabase.getInstance(activity);
                boolean success = dbDataDeleteFromDatabase.deleteNotes(notesArrayList.get(position).getTime_created());
                if (success) {
                    notesArrayList.remove(position);
                    notifyDataSetChanged();
                    UtilsMethods.showToast(activity, "Notes deleted successfully.");
                } else {
                    UtilsMethods.showToast(activity, "Failed to delete notes.");
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView notesTitle, textViewDate;
        private ImageView btnDetails, btnEdit, btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);
            notesTitle = (CustomTextView) itemView.findViewById(R.id.notesTitle);
            textViewDate = (CustomTextView) itemView.findViewById(R.id.textViewDate);
            btnDetails = (ImageView) itemView.findViewById(R.id.btnDetails);
            btnEdit = (ImageView) itemView.findViewById(R.id.btnEdit);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);

        }
    }


}

