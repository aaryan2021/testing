package com.infoicon.acim.bean;

/**
 * Created by sumit on 12/9/17.
 */

public class Notes {

    public String chapter_id,topic_id,notes_type,notes_title,notes_desc,time_created;


    public String getChapter_id() {
        return chapter_id;
    }

    public void setChapter_id(String chapter_id) {
        this.chapter_id = chapter_id;
    }

    public String getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(String topic_id) {
        this.topic_id = topic_id;
    }

    public String getNotes_type() {
        return notes_type;
    }

    public void setNotes_type(String notes_type) {
        this.notes_type = notes_type;
    }

    public String getNotes_title() {
        return notes_title;
    }

    public void setNotes_title(String notes_title) {
        this.notes_title = notes_title;
    }

    public String getNotes_desc() {
        return notes_desc;
    }

    public void setNotes_desc(String notes_desc) {
        this.notes_desc = notes_desc;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }


}
