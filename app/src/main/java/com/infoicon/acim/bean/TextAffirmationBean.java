package com.infoicon.acim.bean;

/**
 * Created by sumit on 5/10/17.
 */

public class TextAffirmationBean {
   // String id;
    String lesson_id;
    String lesson_title;

    /*public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
*/
    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_title() {
        return lesson_title;
    }

    public void setLesson_title(String lesson_title) {
        this.lesson_title = lesson_title;
    }

    public String getAffirmation_text() {
        return affirmation_text;
    }

    public void setAffirmation_text(String affirmation_text) {
        this.affirmation_text = affirmation_text;
    }

    public String getTime_created() {
        return time_created;
    }

    public void setTime_created(String time_created) {
        this.time_created = time_created;
    }

    String affirmation_text;
    String time_created;
}
