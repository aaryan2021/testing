package com.infoicon.acim.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.AudioAffirmationBean;
import com.infoicon.acim.database.DbGetDataFromDataBase;
import com.infoicon.acim.reminder.NotificationScheduler;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.database.DbDataDeleteFromDatabase;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;
import com.infoicon.acim.utils.UtilsMethods;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by sumit on 18/8/17.
 */

public class AudioListAdapter extends RecyclerView.Adapter<AudioListAdapter.ViewHolder> {


    private ViewHolder viewHolder;
    private Context mContext;
    private ArrayList<AudioAffirmationBean> arrayList;
    private RecyclerViewClickListener itemListener;

    public AudioListAdapter(Context mContext, ArrayList<AudioAffirmationBean> arrayList){
        this.mContext=mContext;
        this.arrayList=arrayList;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_audio, null, false);
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
        viewHolder.imageViewPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.recyclerViewListClicked(v,position);
            }
        });
        viewHolder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteAlertDialog(mContext,position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView tvSerialNo,tvAudioName,tvDuration;
        private ImageView imageViewDelete,imageViewPlay;
        public ViewHolder(View itemView) {
            super(itemView);
            tvSerialNo=(CustomTextView) itemView.findViewById(R.id.tvSerialNo);
            tvAudioName=(CustomTextView) itemView.findViewById(R.id.tvAudioName);
            tvDuration=(CustomTextView) itemView.findViewById(R.id.tvDuration);
            imageViewDelete=(ImageView) itemView.findViewById(R.id.imageViewDelete);
            imageViewPlay=(ImageView) itemView.findViewById(R.id.imageViewPlay);

        }
    }

    private void showDeleteAlertDialog(final Context mContext,final int position){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(R.string.alert);
        builder.setMessage(R.string.delete_alert_message_audio);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                File file=new File(arrayList.get(position).getAudio_path());
                if(file.exists()){
                    file.delete();
                    DbDataDeleteFromDatabase dbDataDeleteFromDatabase=DbDataDeleteFromDatabase.getInstance(mContext);
                    DbGetDataFromDataBase dbGetDataFromDataBase=DbGetDataFromDataBase.getInstance(mContext);
                   ArrayList<Integer> reminder_id_array_list= dbGetDataFromDataBase.getReminderIdArrayForAudioAffirmation(arrayList.get(position).getTime_created());
                    if(reminder_id_array_list!=null){
                        for(int i=0;i<reminder_id_array_list.size();i++){
                            NotificationScheduler.cancelReminder(mContext,reminder_id_array_list.get(i));
                            dbDataDeleteFromDatabase.deleteReminder(reminder_id_array_list.get(i));
                        }
                    }

                    dbDataDeleteFromDatabase.deleteAudioAffirmation(arrayList.get(position).getTime_created());
                    arrayList.remove(position);
                    UtilsMethods.showToast(mContext,"Audio deleted successfully.");
                    UtilsMethods.refreshStorage(mContext,file);
                    notifyDataSetChanged();

                }

                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, null);
        builder.show();
    }
}
