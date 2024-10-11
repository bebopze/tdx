package com.bebopze.tdx.script.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;


// @Scheduled("")
@Slf4j
@Component
public class TdxTask {


    @Autowired
    private ConfigurableApplicationContext context;


    @PostConstruct
    public void execTask_933() {
        log.info("---------------------------- 任务 [task_933 - 盘后数据下载]   执行 start ---------------------------- ");


        // .933   -   [盘后数据下载]
        TdxScript.task_933();


        // 任务执行完毕后，优雅关闭Spring Boot应用
        // context.close();
        log.info("---------------------------- 任务 [task_933 - 盘后数据下载]   执行完毕，优雅关闭Spring Boot应用 ---------------------------- ");
    }


    @PostConstruct
    public void execTask_902() {
        log.info("---------------------------- 任务 [task_902 - 扩展数据管理器]   执行 start ---------------------------- ");


        // .902   -   [扩展数据管理器]
        TdxScript.task_902();


        // 任务执行完毕后，优雅关闭Spring Boot应用
        // context.close();
        log.info("---------------------------- 任务 [task_902 - 扩展数据管理器]   执行完毕，优雅关闭Spring Boot应用 ---------------------------- ");
    }


    @PostConstruct
    public void execTask_921() {
        log.info("---------------------------- 任务 [task_921 - 自动选股]   执行 start ---------------------------- ");


        // .921   -   [自动选股]
        TdxScript.task_921();


        // 任务执行完毕后，优雅关闭Spring Boot应用
        context.close();
        log.info("---------------------------- 任务 [task_921 - 自动选股]   执行 end，优雅关闭Spring Boot应用 ---------------------------- ");
    }


}
