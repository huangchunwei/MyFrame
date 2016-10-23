package com.mevv.myframe.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created by VV on 2016/9/14.
 * 日期工具类
 */
public class DateUtils {

    /**
     * 日期字符串转时间戳
     *
     * @param dateStr
     * @return
     */
    public static long getTimestamp(String fromatType, String dateStr) {
        long timeStamp = 0;
        try {
            SimpleDateFormat format = new SimpleDateFormat(fromatType);
            timeStamp = format.parse(dateStr).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeStamp;
    }

    public static String getDate(long timestamp, String formatType) {
        if (timestamp <= 0)
            return "";
        //时间戳转化为Sting或Date
        SimpleDateFormat format = new SimpleDateFormat(formatType);
        String d = format.format(timestamp);
        return d;
    }


}
