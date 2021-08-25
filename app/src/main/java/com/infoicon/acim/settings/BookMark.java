package com.infoicon.acim.settings;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.infoicon.acim.R;
import com.infoicon.acim.adapter.BookmarkAdapter;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.bean.getBookmarkResponse;
import com.infoicon.acim.retrofit.ApiService;
import com.infoicon.acim.retrofit.AppRetrofit;
import com.infoicon.acim.utils.Keys;
import com.infoicon.acim.utils.UtilsMethods;

import java.util.List;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookMark extends Fragment {

    private RecyclerView bookmarkRecycelerview;
    Context context;

    public BookMark() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root= inflater.inflate(R.layout.fragment_book_mark, container, false);
        bookmarkRecycelerview=(RecyclerView)root.findViewById(R.id.bookmark_recyclerview);
        initRecyler();
        context = getActivity();
        getBookmarkdata();
        return root;
    }


    public  void  initRecyler(){
        bookmarkRecycelerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        bookmarkRecycelerview.setHasFixedSize(true);
    }


    public void getBookmarkdata() {
        String deviceId = Settings.Secure.getString(getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        JsonObject jsonObject=new JsonObject();
        jsonObject.addProperty("device_Token",deviceId);
        jsonObject.addProperty(Keys.TYPE,"get_bookmarks");

        ApiService apiService= AppRetrofit.getAppRetrofitInstance().getApiServices();
        final Call<getBookmarkResponse> getBookmark=apiService.getBookmarkdata(jsonObject);
        getBookmark.enqueue(new Callback<getBookmarkResponse>() {
            @Override
            public void onResponse(Response<getBookmarkResponse> response, Retrofit retrofit) {
               // Log.e("ListSIZEOFBOOKMARK",response.body().getData().get(0).getChapter_arrays().size()+"");

                 List<getBookmarkResponse.BookmarkData> lessonTopicBeen=response.body().getData();
                if (lessonTopicBeen == null || lessonTopicBeen.isEmpty()) {
                    UtilsMethods.getToast(context,"No Bookmarks Available");

                }
                 else {
                     bookmarkRecycelerview.setAdapter(new BookmarkAdapter(getActivity(),lessonTopicBeen));


                 }

            }

            @Override
            public void onFailure(Throwable t) {

            }
        });

    }


}
