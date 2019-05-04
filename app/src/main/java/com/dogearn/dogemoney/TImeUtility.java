package com.dogearn.dogemoney;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TImeUtility {
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
    long time;
    Date lastTime;
    String TAG = TImeUtility.class.getName();


    public TImeUtility(long time) {
        this.time = time;
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+6"));

        Date date = new Date(time);
        String temp = "23:59:59" + timeFormat.format(date).substring(8);

        try {
            lastTime = timeFormat.parse(temp);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static Date getLastTimeToday(String date) throws ParseException {
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT+6"));
        String temp = "23:59:59" + timeFormat.format(new Date(date)).substring(8);
        return timeFormat.parse(temp);
    }
}
