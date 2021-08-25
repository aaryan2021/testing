package com.infoicon.acim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class getBookmarkResponse {
    @SerializedName("success")
    private  String success;
    @SerializedName("msg")
    private  String msg;

    @SerializedName("data")
    List<BookmarkData> data;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<BookmarkData> getData() {
        return data;
    }

    public void setData(List<BookmarkData> data) {
        this.data = data;
    }

    public static class BookmarkData implements Parcelable {
        public static final Creator<BookmarkData> CREATOR = new Creator<getBookmarkResponse.BookmarkData>() {
            @Override
            public BookmarkData createFromParcel(Parcel in) {
                return new BookmarkData(in);
            }

            @Override
            public BookmarkData[] newArray(int size) {
                return new BookmarkData[size];
            }
        };

        @SerializedName("chapter_id")
        public  String chapter_id;

        @SerializedName("name")
        public  String name;

        @SerializedName("chapter_Type")
        public  String chapter_Type;

        @SerializedName("chapter_array")
        public List<chapter_array> chapter_arrays;

        public BookmarkData(Parcel in) {

            chapter_id = in.readString();
            name = in.readString();
        }


        public String getChapter_id() {
            return chapter_id;
        }

        public void setChapter_id(String chapter_id) {
            this.chapter_id = chapter_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChapter_Type() {
            return chapter_Type;
        }

        public void setChapter_Type(String chapter_Type) {
            this.chapter_Type = chapter_Type;
        }

        public List<chapter_array> getChapter_arrays() {
            return chapter_arrays;
        }

        public void setChapter_arrays(List<chapter_array> chapter_arrays) {
            this.chapter_arrays = chapter_arrays;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {

        }
        public  static class  chapter_array implements Parcelable {
            public static final Creator<BookmarkData> CREATOR = new Creator<getBookmarkResponse.BookmarkData>() {
                @Override
                public BookmarkData createFromParcel(Parcel in) {
                    return new BookmarkData(in);
                }

                @Override
                public BookmarkData[] newArray(int size) {
                    return new BookmarkData[size];
                }
            };


            @SerializedName("id")
            public  String id;

            @SerializedName("name")
            public  String name;

            public chapter_array(Parcel in) {

                id = in.readString();
                name = in.readString();
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {

            }
        }

    }



    }

