package com.infoicon.acim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by sumit on 8/9/17.
 */

public class GetTeacherManualResponse {

    /**
     * success : true
     * msg : Data fetch successfully
     * data : [{"id":"MNL-Intro","name":"Introduction"},{"id":"MNL-1","name":"1. Who Are God's Teachers?"},{"id":"MNL-2","name":"2. Who Are Their Pupils?"},{"id":"MNL-3","name":"3. What are the Levels of Teaching?"},{"id":"MNL-4","name":"4. What Are the Characteristics of God's Teachers?"},{"id":"MNL-5","name":"5. How is Healing Accomplished?"},{"id":"MNL-6","name":"6. Is Healing Certain?"},{"id":"MNL-7","name":"7. Should Healing Be Repeated?"},{"id":"MNL-8","name":"8. How Can the Perception of Order of Difficulties Be Avoided?"},{"id":"MNL-9","name":"9. Are Changes Required in the Life Situation of God's Teachers?"},{"id":"MNL-10","name":"10. How Is Judgment Relinquished?"},{"id":"MNL-11","name":"11. How Is Peace Possible in this World?"},{"id":"MNL-12","name":"12. How Many Teachers of God Are Needed to Save the World?"},{"id":"MNL-13","name":"13. What Is the Real Meaning of Sacrifice?"},{"id":"MNL-14","name":"14. How Will the World End?"},{"id":"MNL-15","name":"15. Is Each One to Be Judged in the End?"},{"id":"MNL-16","name":"16. How Should the Teacher Spend His Day?"},{"id":"MNL-17","name":"17. How do God's Teachers Deal With Their Pupils' Thoughts of Magic?"},{"id":"MNL-18","name":"18. How Is Correction Made?"},{"id":"MNL-19","name":"19. What Is Justice?"},{"id":"MNL-20","name":"20. What Is the Peace of God?"},{"id":"MNL-21","name":"21. What Is the Role of Words in Healing?"},{"id":"MNL-22","name":"22. How Are Healing and Atonement Related?"},{"id":"MNL-23","name":"23. Does Jesus Have a Special Place in Healing?"},{"id":"MNL-24","name":"24. Is Reincarnation True?"},{"id":"MNL-25","name":"25. Are \"Psychic\" Powers Desirable?"},{"id":"MNL-26","name":"26. Can God Be Reached Directly?"},{"id":"MNL-27","name":"27. What Is Death?"},{"id":"MNL-28","name":"28. What Is the Resurrection?"},{"id":"MNL-29","name":"29. As For the Rest"}]
     */

    private String success;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        /**
         * id : MNL-Intro
         * name : Introduction
         */

        private String id;
        private String name;
        private int isBookmarked = 0;

        protected DataBean(Parcel in) {
            id = in.readString();
            name = in.readString();
            isBookmarked = in.readInt();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

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

        public int getBookMarked() {
            return isBookmarked;
        }

        public void setBookMarked(int bookMarked) {
            isBookmarked = bookMarked;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(id);
            dest.writeString(name);
            dest.writeInt(isBookmarked);
        }
    }
}
