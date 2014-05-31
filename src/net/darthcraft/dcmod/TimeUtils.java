package net.darthcraft.dcmod;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils {

    public final int second = 1;
    public final int minute = second * 60;
    public final int hour = minute * 60;
    public final int day = hour * 24;
    public final int week = day * 7;
    public final int month = week * 4;
    public final int year = month * 12;

    public String getDate() {
        DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM, Locale.ENGLISH);
        String date = df.format(new Date());
        return date;
    }
    
    public String getStandardDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        return date;
    }
    
    public String getLongDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm aa");
        String date = sdf.format(new Date());
        return date;
    }

    public long getUnixTimestamp() {
        long unixTime = System.currentTimeMillis() / 1000L;
        return unixTime;
    }
}