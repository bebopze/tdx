package com.bebopze.tdx.script.utils;


import lombok.extern.slf4j.Slf4j;


@Slf4j
public class SleepUtils {


    public static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }
    }


    /**
     * win系统 反应时间
     */
    public static void winSleep() {
        sleep(1000);
    }

    /**
     * win系统 反应时间
     *
     * @param millis
     */
    public static void winSleep(long millis) {
        sleep(millis);
    }

}
