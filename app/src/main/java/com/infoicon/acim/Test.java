package com.infoicon.acim;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by sumit on 21/11/17.
 */

public class Test {

    public static void main(String[] args){
        Test test=new Test();
        test.getTimeDifference("11:45 AM","12:48 PM");
    }


    public  int getTimeDifference(String startTime, String endTime) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm aa",Locale.ENGLISH);
        Date date1=null,date2=null;
        try {
            date1 = simpleDateFormat.parse(startTime);
            date2 = simpleDateFormat.parse(endTime);
            long difference = date2.getTime() - date1.getTime();
            int  days = (int) (difference / (1000 * 60 * 60 * 24));
            int   hours = (int) ((difference - (1000 * 60 * 60 * 24 * days)) / (1000 * 60 * 60));
            int    min = (int) (difference - (1000 * 60 * 60 * 24 * days) - (1000 * 60 * 60 * hours)) / (1000 * 60);
            hours = (hours < 0 ? -hours : hours);
            if(hours>0){
                min=min+hours*60;
            }

            System.out.print("Hours==="+hours+" mins=="+min);
            return min;
        } catch (ParseException e) {
            e.printStackTrace();
        }



        //      Log.i("======= Hours", " :: " + hours);
        return 0;
    }
}
