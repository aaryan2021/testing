package com.infoicon.acim.adapter;

import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.infoicon.acim.R;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;


/**
 * Created by sumit on 6/10/17.
 */

public class RepeatIntervalAdapter extends RecyclerView.Adapter<RepeatIntervalAdapter.ViewHolder>{

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private String[] interval_list;
    private RecyclerViewClickListener itemListener;


    public RepeatIntervalAdapter(FragmentActivity activity,String[] interval_list){
        this.activity=activity;
        this.interval_list=interval_list;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_repeat_interval, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        if(position<6){
            viewHolder.textView.setText(interval_list[position]+ " mins");
        }else {
            int hours=(Integer.parseInt(interval_list[position]))/60;
            if(position==6){
                viewHolder.textView.setText(hours+ " hour");
            }else {
                viewHolder.textView.setText(hours+ " hours");
            }

        }
       /* if((position+1)==interval_list.length){
            viewHolder.textView.setText(interval_list[position]);
        }else{
            viewHolder.textView.setText(interval_list[position]+ " mins");
        }*/
        viewHolder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                itemListener.recyclerViewListClicked(buttonView,position);
            }
        }) ;

    }

    @Override
    public int getItemCount() {
        return interval_list.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CheckBox checkbox;
        private CustomTextView textView;
        public ViewHolder(View itemView) {
            super(itemView);
            checkbox=(CheckBox) itemView.findViewById(R.id.checkbox);
            textView=(CustomTextView)itemView.findViewById(R.id.textView);

        }
    }
}
