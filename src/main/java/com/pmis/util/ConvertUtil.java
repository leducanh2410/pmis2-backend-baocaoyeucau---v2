package com.pmis.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ConvertUtil {
    public static String convertDateString(String before) {
        return before.replace("T", " ").replace(".000Z", "");
    }

    public static String convertDateTimeString(String date) {
        try {
            date = convertDateString(date);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
            Date dateDate = formatter.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateDate);
            calendar.add(Calendar.HOUR, 7);
            return formatter.format(calendar.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
