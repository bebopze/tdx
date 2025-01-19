package com.bebopze.tdx.script.utils;

import java.text.DecimalFormat;


public class DateUtils {


    /**
     * 将毫秒值 格式化为 时分秒
     *
     * @param millis 毫秒值
     * @return 时分秒格式的字符串，例如 "01:02:03"
     */
    public static String formatMillis(long millis) {
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        long hours = minutes / 60;

        seconds = seconds % 60;
        minutes = minutes % 60;

        DecimalFormat df = new DecimalFormat("00");
        return df.format(hours) + ":" + df.format(minutes) + ":" + df.format(seconds);
    }


    public static void main(String[] args) {
        // 示例毫秒值
        long milliseconds = 3661000;
        String formattedTime = formatMillis(milliseconds);
        // 输出: 01:01:01
        System.out.println(formattedTime);
    }
}