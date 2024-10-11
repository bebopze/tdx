package com.bebopze.tdx.script;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;


@SpringBootApplication
public class TdxScriptApp {


    public static void main(String[] args) {


        SpringApplication application = new SpringApplicationBuilder(TdxScriptApp.class)
                // .web(WebApplicationType.NONE)

                // 问题描述：在使用Robot来模拟键盘事件时，启动报错java.awt.AWTException: headless environment
                // https://blog.csdn.net/weixin_44216706/article/details/107138556
                // https://blog.csdn.net/qq_35607651/article/details/106055160
                .headless(false)

                .build(args);


        application.run(args);


//        // 阻止程序启动后停止，如果应用内 存在@Scheduled注解的定时任务，则无需手动阻止程序停止
//        new Thread(() -> {
//            synchronized (TdxScriptApp.class) {
//                try {
//                    TdxScriptApp.class.wait();
//                } catch (Throwable e) {
//
//                }
//            }
//        }).start();
    }

}