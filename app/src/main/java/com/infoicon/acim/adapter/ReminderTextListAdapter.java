package com.infoicon.acim.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.TextAffirmationBean;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by sumit on 7/10/17.
 */

public class ReminderTextListAdapter extends RecyclerView.Adapter<ReminderTextListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private ArrayList<TextAffirmationBean> textAffirmationBeanArrayList;
    private RecyclerViewClickListener itemListener;
    private String type;
    Integer selected_position = -1;

    public ReminderTextListAdapter(FragmentActivity activity, ArrayList<TextAffirmationBean> textAffirmationBeanArrayList){
        this.activity=activity;
        this.textAffirmationBeanArrayList = textAffirmationBeanArrayList;
        this.type=type;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reminder_text, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        viewHolder.reminderTitle.setText(textAffirmationBeanArrayList.get(position).getAffirmation_text());
        holder.checkBox.setOnCheckedChangeListener(null);
        if( ScheduleReminderFragment.SELECTED_AFFIRMATION_TEXT_POS == position)
            viewHolder.checkBox.setChecked(true);
        else{
            viewHolder.checkBox.setChecked(false);
        }

       viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                   // selected_position =  position;
                    ScheduleReminderFragment.SELECTED_AFFIRMATION_TEXT_POS=position;
                }
                else{
              //      selected_position = -1;
                    ScheduleReminderFragment.SELECTED_AFFIRMATION_TEXT_POS=-1;
                }
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return textAffirmationBeanArrayList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView reminderTitle;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            reminderTitle=(CustomTextView) itemView.findViewById(R.id.reminderTitle);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkBox);

        }
    }
}


