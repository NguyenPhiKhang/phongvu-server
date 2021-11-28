package com.kltn.phongvuserver.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class StringUtil {
    public static String convertTimestampToString(Timestamp timestamp) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timestamp.getTime());

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return dateFormat.format(calendar.getTime());
    }

    public static String convertDateToString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static String incognitoName(String str){
        int len = str.length();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len-1; i++){
            if(i>len/3)
                sb.append('*');
            else sb.append(str.charAt(i));
        }
        return sb.toString();
    }
}
