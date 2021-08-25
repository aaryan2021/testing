package com.infoicon.acim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.reminder.ScheduleReminderFragment;
import com.infoicon.acim.utils.UtilsMethods;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;

import java.util.ArrayList;

/**
 * Created by sumit on 7/10/17.
 */

public class ReminderAudioListAdapter extends RecyclerView.Adapter<ReminderAudioListAdapter.ViewHolder> {


    private ViewHolder viewHolder;
    private Context mContext;
    private ArrayList<AudioAffirmationBean> arrayList;
    private RecyclerViewClickListener itemListener;
   // Integer selected_position = -1;
    public ReminderAudioListAdapter(Context mContext, ArrayList<AudioAffirmationBean> arrayList){
        this.mContext=mContext;
        this.arrayList=arrayList;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_reminder_audio, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        viewHolder.tvSerialNo.setText((position+1)+".");
        String title=arrayList.get(position).getAudio_path();
        String str[]=title.split("ACIM/");
        String[] name = str[1].split("_");
        viewHolder.tvAudioName.setText(name[0]+" "+(position+1));
        viewHolder.tvDuration.setText((UtilsMethods.getAudioDuration(Long.parseLong(arrayList.get(position).getDuration()))));
        holder.checkBox.setOnCheckedChangeListener(null);
        viewHolder.checkBox.setChecked(position==ScheduleReminderFragment.SELECTED_AFFIRMATION_AUDIO_POS);
        viewHolder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if(isChecked)
               {
                  // selected_position =  position;
                   ScheduleReminderFragment.SELECTED_AFFIRMATION_AUDIO_POS=position;
               }
               else{
                   //selected_position = -1;
                   ScheduleReminderFragment.SELECTED_AFFIRMATION_AUDIO_POS=-1;
               }
               notifyDataSetChanged();
           }
       });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView tvSerialNo,tvAudioName,tvDuration;
        private CheckBox checkBox;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSerialNo=(CustomTextView) itemView.findViewById(R.id.tvSerialNo);
            tvAudioName=(CustomTextView) itemView.findViewById(R.id.tvAudioName);
            tvDuration=(CustomTextView) itemView.findViewById(R.id.tvDuration);
            checkBox=(CheckBox)itemView.findViewById(R.id.checkBox);

        }
    }


}
