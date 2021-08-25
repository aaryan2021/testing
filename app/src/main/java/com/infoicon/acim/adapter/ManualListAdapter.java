package com.infoicon.acim.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetTeacherManualResponse;
import com.infoicon.acim.manual.ManualFragment;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.utils.interfaces.RecyclerViewClickListener;


import java.util.List;

import static com.infoicon.acim.utils.AppConstants.MANUAL_CHAPTER_CONTENT_DESCRIPTION;
import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER_CONTENT_DESCRIPTION;

/**
 * Created by sumit on 25/7/17.
 */

public class ManualListAdapter extends RecyclerView.Adapter<ManualListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private Context mContext;
    private List<GetTeacherManualResponse.DataBean> dataBeanArrayList;;
    private RecyclerViewClickListener itemListener;
    private ManualFragment manualFragment;

    public ManualListAdapter(Context mContext, List<GetTeacherManualResponse.DataBean> dataBeanArrayList,ManualFragment manualFragment){
        this.manualFragment = manualFragment;
        this.mContext=mContext;
        this.dataBeanArrayList=dataBeanArrayList;
    }

    public void setListener(RecyclerViewClickListener itemListener){
        this.itemListener=itemListener;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,final int position) {
        viewHolder = holder;
        viewHolder.name.setText(dataBeanArrayList.get(position).getName());
        if (User.getInstance(mContext).getBookmarkData().get(MANUAL_CHAPTER_CONTENT_DESCRIPTION) != null){
            if (User.getInstance(mContext).getBookmarkData().get(MANUAL_CHAPTER_CONTENT_DESCRIPTION).equalsIgnoreCase(
                    dataBeanArrayList.get(position).getId())) {
                viewHolder.bookmark_icon.setVisibility(View.VISIBLE);
                viewHolder.bookmark_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark_icon));
            } else {
                viewHolder.bookmark_icon.setVisibility(View.GONE);
                viewHolder.bookmark_icon.setImageDrawable(mContext.getResources().getDrawable(R.drawable.bookmark_icon_white));
            }
        }
        viewHolder.name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemListener.recyclerViewListClicked(v,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView name;
        private ImageView bookmark_icon;
        public ViewHolder(View itemView) {
            super(itemView);
            name=(CustomTextView) itemView.findViewById(R.id.textViewChapterNo);
            bookmark_icon = itemView.findViewById(R.id.bookmark_icon);

        }
    }
}
