package com.infoicon.acim.bean;

import java.util.List;

/**
 * Created by sumit on 5/9/17.
 */

public class GetWorkbookLessonDesc {


    /**
     * success : true
     * msg : Description Fetch successfully
     * data : {"musicfiles":["https://www.jcim.net/acimoe-audio/acimoe-workbook/03%20Lesson%201.mp3"],"description":"\r\nL e s s o n  1Nothing I see in this room\r\n  [on this street, from this window,\r\n  in this place] means anything.\r\n  1 Now look slowly around you,\r\n  and practice applying this idea very specifically to whatever you\r\n  see:\r\n\r\n  2 This table does not mean\r\n  anything.This chair does not mean anything.This hand does not mean anything.This foot does not mean anything.This pen does not mean anything.\r\n\r\n  3 Then look farther away from\r\n  your immediate area, and apply the idea to a wider range:\r\n\r\n  4 That door does not mean\r\n  anything.That body does not mean anything.That lamp does not mean anything.That sign does not mean anything.That shadow does not mean anything.\r\n\r\n  5 Notice that these\r\n  statements are not arranged in any order, and make no allowance for\r\n  differences in the kinds of things to which they are applied. That is\r\n  the purpose of the exercise. The statement is merely applied to\r\n  anything you see. As you practice applying the idea for the day, use\r\n  it totally indiscriminately. Do not attempt to apply it to everything\r\n  you see, for these exercises should not become ritualistic. Only be\r\n  sure that nothing you see is specifically excluded. One thing is like\r\n  another as far as the application of the idea is concerned.\r\n  ","title":"L e s s o n  1Nothing I see in this room\r"}
     */

    private String success;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * musicfiles : ["https://www.jcim.net/acimoe-audio/acimoe-workbook/03%20Lesson%201.mp3"]
         * description :
         L e s s o n  1Nothing I see in this room
         [on this street, from this window,
         in this place] means anything.
         1 Now look slowly around you,
         and practice applying this idea very specifically to whatever you
         see:

         2 This table does not mean
         anything.This chair does not mean anything.This hand does not mean anything.This foot does not mean anything.This pen does not mean anything.

         3 Then look farther away from
         your immediate area, and apply the idea to a wider range:

         4 That door does not mean
         anything.That body does not mean anything.That lamp does not mean anything.That sign does not mean anything.That shadow does not mean anything.

         5 Notice that these
         statements are not arranged in any order, and make no allowance for
         differences in the kinds of things to which they are applied. That is
         the purpose of the exercise. The statement is merely applied to
         anything you see. As you practice applying the idea for the day, use
         it totally indiscriminately. Do not attempt to apply it to everything
         you see, for these exercises should not become ritualistic. Only be
         sure that nothing you see is specifically excluded. One thing is like
         another as far as the application of the idea is concerned.

         * title : L e s s o n  1Nothing I see in this room
         */

        private String description;
        private String title;
        private List<String> musicfiles;

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public List<String> getMusicfiles() {
            return musicfiles;
        }

        public void setMusicfiles(List<String> musicfiles) {
            this.musicfiles = musicfiles;
        }
    }
}
