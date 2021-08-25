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
import com.infoicon.acim.affirmation.TextAffirmationFragment;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.notes.FragmentNoteDetails;
import com.infoicon.acim.notes.FragmentNotes;
import com.infoicon.acim.reminder.NotificationScheduler;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DbDataDeleteFromDatabase;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.utils.AppConstants;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;

/**
 * Created by sumit on 5/10/17.
 */

public class TextAffirmationAdapter extends RecyclerView.Adapter<TextAffirmationAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private ArrayList<TextAffirmationBean> textAffirmationBeanArrayList;
    private RecyclerViewClickListener itemListener;
    private String type;
    private FragmentManager fragmentManager;
    public TextAffirmationAdapter(FragmentActivity activity, FragmentManager fragmentManager,ArrayList<TextAffirmationBean> textAffirmationBeanArrayList,String type){
        this.activity=activity;
        this.textAffirmationBeanArrayList = textAffirmationBeanArrayList;
        this.fragmentManager=fragmentManager;
        this.type=type;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
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
        viewHolder.notesTitle.setText(textAffirmationBeanArrayList.get(position).getAffirmation_text());
        viewHolder.textViewDate.setText("Created on: "+ UtilsMethods.parseNotesCreatedDate(textAffirmationBeanArrayList.get(position).getTime_created()));
/*
        if(type.equals(AppConstants.MANAGE_AFFIRMATION)){
            viewHolder.btnDetails.setVisibility(View.VISIBLE);
            viewHolder.btnDetails.setImageResource(R.drawable.edit);
        }else{
            viewHolder.btnDetails.setVisibility(View.GONE);
        }*/

        viewHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals(AppConstants.ADD_AFFIRMATION)){
                    fragmentManager.popBackStack();

                }
                TextAffirmationFragment textAffirmationFragment=new TextAffirmationFragment();
                Bundle bundle=new Bundle();
                bundle.putString(Keys.LESSON_ID,textAffirmationBeanArrayList.get(position).getLesson_id());
                bundle.putString(Keys.LESSON_TITLE,textAffirmationBeanArrayList.get(position).getLesson_title());
                bundle.putString(Keys.TEXT,textAffirmationBeanArrayList.get(position).getAffirmation_text());
                bundle.putString(Keys.TIME,textAffirmationBeanArrayList.get(position).getTime_created());
                textAffirmationFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity,textAffirmationFragment);
            }
        });

        viewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showDeleteAlertDialog(activity,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return textAffirmationBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView notesTitle,textViewDate;
        private ImageView btnEdit,btnDelete,btnDetails;
        public ViewHolder(View itemView) {
            super(itemView);
            notesTitle=(CustomTextView) itemView.findViewById(R.id.notesTitle);
            textViewDate=(CustomTextView)itemView.findViewById(R.id.textViewDate);
            btnDetails=(ImageView)itemView.findViewById(R.id.btnDetails);
            btnDetails.setVisibility(View.GONE);
            btnDelete=(ImageView)itemView.findViewById(R.id.btnDelete);
            btnEdit=(ImageView)itemView.findViewById(R.id.btnEdit);
        }
    }


    private void showDeleteAlertDialog(final Context mContext, final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.delete_alert_message_text_affirmation);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DbDataDeleteFromDatabase dbDataDeleteFromDatabase=DbDataDeleteFromDatabase.getInstance(activity);
                DbGetDataFromDataBase dbGetDataFromDataBase=DbGetDataFromDataBase.getInstance(mContext);
                ArrayList<Integer> reminder_id_array_list= dbGetDataFromDataBase.getReminderIdArrayForWrittenAffirmation(textAffirmationBeanArrayList.get(position).getTime_created());
                if(reminder_id_array_list!=null){
                    for(int i=0;i<reminder_id_array_list.size();i++){
                        NotificationScheduler.cancelReminder(mContext,reminder_id_array_list.get(i));
                        dbDataDeleteFromDatabase.deleteReminder(reminder_id_array_list.get(i));
                    }
                }

                boolean success=dbDataDeleteFromDatabase.deleteTextAffirmation(textAffirmationBeanArrayList.get(position).getTime_created());
                if(success){
                    textAffirmationBeanArrayList.remove(position);
                    notifyDataSetChanged();
                    UtilsMethods.showToast(activity,"Affirmation deleted successfully.");
                }else{
                    UtilsMethods.showToast(activity,"Failed to delete affirmation.");
                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}

