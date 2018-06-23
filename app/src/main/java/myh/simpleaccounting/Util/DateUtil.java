package myh.simpleaccounting.Util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static String getTime(long times){

        SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm");
        return formatTime.format(new Date(times));

    }

    public static String getDate(){
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
        return formatDate.format(new Date());
    }

    public static String setDateFormat(int year,int monthOfYear,int dayOfMonth){

        //使格式符合yyyy-MM-dd
        String m, d;
        if(monthOfYear < 10){

            monthOfYear = monthOfYear + 1;
            m = "0" + monthOfYear;
        }else{
            monthOfYear = monthOfYear + 1;
            m = String.valueOf(monthOfYear);}

        if(dayOfMonth < 10){

            d = "0" + dayOfMonth ;
        }else{d = String.valueOf(dayOfMonth);}
        return String.valueOf(year) + "-"
                + m + "-"
                + d;

    }

}
