package com.infoicon.acim.bean;

/**
 * Created by sumit on 7/10/17.
 */

public class ReminderBean {


    int id;
    String lesson_id;
    String lesson_title;
    String start_time;
    String end_time;
    String reminder_type;
    String repeat_interval;
    String reminder_text;

    public String getReminder_text_time_created() {
        return reminder_text_time_created;
    }

    public void setReminder_text_time_created(String reminder_text_time_created) {
        this.reminder_text_time_created = reminder_text_time_created;
    }

    public String getReminder_audio_time_created() {
        return reminder_audio_time_created;
    }

    public void setReminder_audio_time_created(String reminder_audio_time_created) {
        this.reminder_audio_time_created = reminder_audio_time_created;
    }

    String reminder_text_time_created;
    String reminder_audio;
    String reminder_audio_time_created;
    String timeCreated;
    String status;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getRepeat_interval() {
        return repeat_interval;
    }

    public void setRepeat_interval(String repeat_interval) {
        this.repeat_interval = repeat_interval;
    }


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

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getReminder_type() {
        return reminder_type;
    }

    public void setReminder_type(String reminder_type) {
        this.reminder_type = reminder_type;
    }

    public String getReminder_text() {
        return reminder_text;
    }

    public void setReminder_text(String reminder_text) {
        this.reminder_text = reminder_text;
    }

    public String getReminder_audio() {
        return reminder_audio;
    }

    public void setReminder_audio(String reminder_audio) {
        this.reminder_audio = reminder_audio;
    }

    public String getTimeCreated() {
        return timeCreated;
    }

    public void setTimeCreated(String timeCreated) {
        this.timeCreated = timeCreated;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
