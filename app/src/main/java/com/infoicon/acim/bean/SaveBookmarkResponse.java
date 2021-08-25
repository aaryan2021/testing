package com.infoicon.acim.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaveBookmarkResponse {

    @SerializedName("success")
    private  String success;
    @SerializedName("msg")
    private  String msg;

    @SerializedName("data")
    List<DataList> data;

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

    public List<DataList> getData() {
        return data;
    }

    public void setData(List<DataList> data) {
        this.data = data;
    }
}
