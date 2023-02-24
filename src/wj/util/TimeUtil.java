package wj.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    public static String getTime() {
        try {
            Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.format(d);
        } catch (Exception e) {
            return "0 0 0";
        }
    }

    public static String getTimes() {
        try {
            Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
            return "[" + sdf.format(d) + "]";
        } catch (Exception e) {
            return "0 0 0";
        }
    }

    public static String getTimeHH() {
        try {
            Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("HH");
            return sdf.format(d);
        } catch (Exception e) {
            return "0 0 0";
        }
    }

    public static String getTimeToday() {
        try {
            Date d = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(d);
        } catch (Exception e) {
            return "0 0 0";
        }
    }

    public static Long parseTime(String timeStr) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return simpleDateFormat.parse(timeStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    public static void main(String[] args) {
        //00
        System.out.println(TimeUtil.getTimeHH());
    }
}
