package com.scohong.utils;

import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @Author: scohong
 * @Date: 2019/7/16 15:19
 * @Description:
 */
@Slf4j
public class CommonUtils {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("mm:ss");

    /**
     * 获取格式化时间（String0
     *
     * @return string类型时间
     */
    public static String getCurrentTime() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        return sdf.format(timestamp);
    }

    public static String formatTime(String strDate)  {
        strDate = strDate.replace("GMT", "").replaceAll("\\(.*\\)", "");
        SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd yyyy hh:mm:ss", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        DateTime dateTime = new DateTime(date);
        String time = "";
        int min = dateTime.getMinuteOfDay();
        int second = dateTime.getSecondOfMinute();
        if (min < 10) {
            time = time.concat("0").concat(String.valueOf(min)).concat("'");
        } else {
            time = time.concat(String.valueOf(min)).concat("'");
        }
        if (second < 10) {
            time = time.concat("0").concat(String.valueOf(second)).concat("''");
        } else {
            time = time.concat(String.valueOf(second)).concat("''");
        }
        return time;
    }


}
