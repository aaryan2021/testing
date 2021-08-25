package com.infoicon.acim.adapter;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.infoicon.acim.R;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.text.TextFragment;
import com.infoicon.acim.utils.User;
import com.infoicon.acim.utils.customviews.CustomTextView;
import com.infoicon.acim.text.TextChapterFragment;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.ArrayList;
import java.util.List;

import static com.infoicon.acim.utils.AppConstants.TEXT_CHAPTER;

/**
 * Created by sumit on 1/9/17.
 */

public class TextChapterListAdapter extends RecyclerView.Adapter<TextChapterListAdapter.ViewHolder> {

    private ViewHolder viewHolder;
    private FragmentActivity activity;
    private List<GetChapterApiResponse.DataBean.ChaptersBean> chaptersBeanArrayList;
    private TextFragment textFragment;

    public TextChapterListAdapter(FragmentActivity activity,TextFragment textFragment, List<GetChapterApiResponse.DataBean.ChaptersBean> chaptersBeanArrayList){
        this.activity=activity;
        this.textFragment = textFragment;
        this.chaptersBeanArrayList=chaptersBeanArrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_text_chapter, null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        viewHolder = holder;
        String str=chaptersBeanArrayList.get(position).getChaptername();
        String[] title=str.split("@");
        viewHolder.textViewChapterNo.setText(title[0]);
        viewHolder.textViewTitle.setText(title[1].trim());

        if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER) != null) {
            if (User.getInstance(activity).getBookmarkData().get(TEXT_CHAPTER).equalsIgnoreCase(chaptersBeanArrayList.get(position).getChaptername())) {
                viewHolder.bookmark_icon.setVisibility(View.VISIBLE);
                viewHolder.bookmark_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bookmark_icon));
            } else {
                viewHolder.bookmark_icon.setVisibility(View.GONE);
                viewHolder.bookmark_icon.setImageDrawable(activity.getResources().getDrawable(R.drawable.bookmark_icon_white));
            }
        }

        viewHolder.item_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextFragment.CLICKED_POSITION=position;
                TextChapterFragment textChapterFragment=new TextChapterFragment();
                textChapterFragment.setTargetFragment(textFragment,155);
                Bundle bundle=new Bundle();
                bundle.putString(Keys.TITLE,chaptersBeanArrayList.get(position).getChaptername());
                bundle.putInt(Keys.POSITION,position);
                bundle.putParcelableArrayList("array", (ArrayList<? extends Parcelable>) chaptersBeanArrayList.get(position).getTopics());
                textChapterFragment.setArguments(bundle);
                UtilsMethods.replaceFragment(activity, textChapterFragment);
            }
        });

    }

    @Override
    public int getItemCount() {
        return chaptersBeanArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CustomTextView textViewChapterNo,textViewTitle;
        private RelativeLayout item_layout;
        private ImageView bookmark_icon;
        public ViewHolder(View itemView) {
            super(itemView);
            textViewChapterNo= itemView.findViewById(R.id.textViewChapterNo);
            textViewTitle= itemView.findViewById(R.id.textViewTitle);
            item_layout= itemView.findViewById(R.id.item_layout);
            bookmark_icon= itemView.findViewById(R.id.bookmark_icon);

        }
    }
}

