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
}
