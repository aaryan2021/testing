package com.infoicon.acim.retrofit;


import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.infoicon.acim.bean.GetChapterApiResponse;
import com.infoicon.acim.bean.GetChapterDescResponse;
import com.infoicon.acim.bean.GetIntroductionResponse;
import com.infoicon.acim.bean.GetManualDescResponse;
import com.infoicon.acim.bean.GetTeacherManualResponse;
import com.infoicon.acim.bean.GetWorkbookLessonDesc;
import com.infoicon.acim.bean.GetWorkbookLessonResponse;
import com.infoicon.acim.bean.SaveBookmarkResponse;
import com.infoicon.acim.bean.getBookmarkResponse;

import org.json.JSONObject;

import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.Call;


public interface ApiService {

    @POST(ServiceUrls.CHAPTER_URL)
    Call<GetChapterApiResponse> getChapterApiResponseCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.WORKBOOK_LESSON)
    Call<GetWorkbookLessonResponse>getWorkbookLessonResponseCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.CHAPTER_DESC)
    Call<GetChapterDescResponse>getChapterDescResponseCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.WORKBOOK_LESSON_DESC)
    Call<GetWorkbookLessonDesc>getWorkbookLessonDescCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.TEACHER_MANUAL)
     Call<GetTeacherManualResponse>getTeacherManualResponseCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.TEACHER_MANUAL_DESC)
    Call<GetManualDescResponse>getManualDescResponseCall(@Body JsonObject jsonObject);

    @POST(ServiceUrls.INTRO_URL)
    Call<GetIntroductionResponse>getIntroductionResponseCall(@Body JsonObject jsonObject);

   @POST(ServiceUrls.SAVE_BOOKMARK_)
   Call<SaveBookmarkResponse> SAVE_BOOKMARK_RESPONSE_CALL(@Body JsonObject jsonObject);

   @POST(ServiceUrls.CHAPTER_URL)
    Call<getBookmarkResponse> getBookmarkdata(@Body JsonObject jsonObject);
}
