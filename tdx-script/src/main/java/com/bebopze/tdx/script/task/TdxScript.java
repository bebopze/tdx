package com.bebopze.tdx.script.task;

import com.bebopze.tdx.script.utils.WinUtils;
import com.bebopze.tdx.script.utils.WinUtils3;
import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bebopze.tdx.script.utils.DateUtils.formatMillis;
import static com.bebopze.tdx.script.utils.SleepUtils.winSleep;
import static java.awt.event.KeyEvent.*;


@Slf4j
public class TdxScript {


    public static void main(String[] args) {


        // .933   -   [盘后数据下载]
        task_933();


        winSleep(3000);


        // .902   -   [扩展数据管理器]
        task_902();


        winSleep(3000);


        // .921   -   [自动选股设置]
        task_921();
    }


    /**
     * 执行task     ==>     .933   -   [盘后数据下载]
     */
    public static void task_933() {


        // open [通达信]
        openTdx();


        // close [开屏广告]
        closeTdxAds();


        // .933   -   [盘后数据下载]
        _933();

        // check
        check_933();


        // close [通达信]
        closeTdx();


        // killTdx();
    }


    /**
     * 执行task     ==>     .902   -   [扩展数据管理器]
     */
    public static void task_902() {


        // open [通达信]
        openTdx();


        // close [开屏广告]
        closeTdxAds();


        // .902   -   [扩展数据管理器]
        _902();

        // check
        check_902();


        // close [通达信]
        closeTdx();
    }


    /**
     * 执行task     ==>     .921   -   [自动选股]
     */
    public static void task_921() {


        // open [通达信]
        openTdx();


        // close [开屏广告]
        closeTdxAds();


        // .921   -   [自动选股]
        _921();

        // check
        check_921();


        // close [通达信]
        closeTdx();
    }


    /**
     * .933   -   [盘后数据下载]
     */
    private static void _933() {


        // 切换 [tdx-主界面]   ->   选中[通达信]
        switchTdxWindow();


        // 键盘输入   ->   [.933]   -   打开 [盘后数据下载]
        ArrayList<Integer> keyList = Lists.newArrayList(VK_PERIOD, VK_9, VK_3, VK_3);
        WinUtils.keyPress(keyList);
        log.info("---------------------------- 键盘输入 [.933]     >>>     打开 [盘后数据下载]");


        winSleep();


        String lpClassName = "#32770";
        String lpWindowName = "盘后数据下载";


        // 获取 [盘后数据下载]窗口   -   所有 [按钮]
        List<WinDef.HWND> hwndChildButtonList = WinUtils3.listAllChildButton(lpClassName, lpWindowName);


        // 遍历 [按钮]列表
        // --------------------------------------------------

        // .933 - 盘后数据下载
        //
        // 沪深京日线              -   日线和实时行情数据                   /     下载所有AB股类品种的日线数据
        // 沪深京分钟线            -   1分钟线数据 / 5分钟线数据             /     下载所有AB股类品种的日线数据
        //
        // 沪深京分时图            -   当日分时图数据(仅供当天脱机分析使用)     /     添加品种 / 移出品种 / 清空品种
        // 扩展市场行情日线         -   日线数据                            /     添加品种 / 移出品种 / 清空品种     /     下载所有港股品种日线数据 / 下载所有美股品种日线数据
        // 扩展市场行情分钟线       -   1分钟线数据 / 5分钟线数据              /     添加品种 / 移出品种 / 清空品种     /     下载所有港股品种日线数据 / 下载所有美股品种日线数据
        //
        //
        // 开始下载 / 关闭


        for (WinDef.HWND childButton : hwndChildButtonList) {

            // [按钮] - 文本
            String buttonText = WinUtils3.getWindowText(childButton);
            // 沪深京日线   -   日线和实时行情数据
            if ("日线和实时行情数据".equals(buttonText)) {

                // 窗口切换
                WinUtils.windowSwitcher(childButton);
                // 点击 [按钮]
                WinUtils.clickMouseLeft(childButton);
                log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线 - 日线和实时行情数据]");


                // --------


                for (WinDef.HWND childButton2 : hwndChildButtonList) {

                    // [按钮] - 文本
                    String buttonText2 = WinUtils3.getWindowText(childButton2);
                    // 沪深京日线   -   日线和实时行情数据   -   开始下载
                    if ("开始下载".equals(buttonText2)) {

                        // 窗口切换
                        WinUtils.windowSwitcher(childButton2);
                        // 点击 [按钮]
                        WinUtils.clickMouseLeft(childButton2);
                        log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线 - 日线和实时行情数据 - 开始下载]");


                        return;
                    }
                }
            }
        }


        // .902 - 扩展数据管理器   -   全部刷新 / 刷新 / 删除 / 修改 / 关闭


        // .921 - 自动选股模式   -   一键全部选股 / 每板块单独选股 / 添加方案 / 修改方案 / 删除方案 / 前移 / 后移 / 执行方案 / 一键选股 / 打开板块 / 关闭


        // .933 - 盘后数据下载
        //
        // 沪深京日线              -   日线和实时行情数据                   /     下载所有AB股类品种的日线数据
        // 沪深京分钟线            -   1分钟线数据 / 5分钟线数据             /     下载所有AB股类品种的日线数据
        //
        // 沪深京分时图            -   当日分时图数据(仅供当天脱机分析使用)     /     添加品种 / 移出品种 / 清空品种
        // 扩展市场行情日线         -   日线数据                            /     添加品种 / 移出品种 / 清空品种     /     下载所有港股品种日线数据 / 下载所有美股品种日线数据
        // 扩展市场行情分钟线       -   1分钟线数据 / 5分钟线数据              /     添加品种 / 移出品种 / 清空品种     /     下载所有港股品种日线数据 / 下载所有美股品种日线数据
        //
        //
        // 开始下载 / 关闭


        winSleep();
    }

    /**
     * check .933   -   [盘后数据下载]
     */
    private static void check_933() {

        // 下载完成
        boolean taskEnd = false;


        while (true) {


            // 切换 [tdx-主界面]   ->   选中[通达信]
            switchTdxWindow();


            String lpClassName1 = "#32770";
            String lpWindowName1 = "盘后数据下载";


            // ---------- [沪深京日线] 按钮
            // 获取 [按钮]
            // 开始下载（初始状态）  ->  取消下载（下载中）  ->  开始下载（下载完毕）
            WinDef.HWND button_取消下载 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "取消下载");
            WinDef.HWND button_开始下载 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "开始下载");


            if (button_取消下载 != null) {
                // 下载中
                taskEnd = false;
                log.info("[盘后数据]   ->   ing");


                // check频率  -  1次/30s
                winSleep(30000);


            } else if (button_开始下载 != null) {
                // 下载完成
                taskEnd = true;
                log.info("[盘后数据]   ->   end");

            } else {

                taskEnd = true;
                log.info("[盘后数据]   ->   error");
            }


            if (taskEnd) {

                // 关闭窗口 - [盘后收据下载]


                // ---------- [沪深京日线] 按钮
                // 获取 [按钮]
                WinDef.HWND button_关闭 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "关闭");
                // 窗口切换
                WinUtils.windowSwitcher(button_关闭);
                // 点击 [按钮]
                WinUtils.clickMouseLeft(button_关闭);
                log.info("---------------------------- 点击 [盘后数据下载 - 关闭]");


                return;
            }
        }
    }


    /**
     * .902   -   [扩展数据管理器]
     */
    private static void _902() {


        // 切换 [tdx-主界面]   ->   选中[通达信]
        switchTdxWindow();


        // 键盘输入   ->   [.902]   -   打开 [扩展数据管理器]
        ArrayList<Integer> keyList = Lists.newArrayList(VK_PERIOD, VK_9, VK_0, VK_2);
        WinUtils.keyPress(keyList);
        log.info("---------------------------- 键盘输入 [.902]     >>>     打开 [扩展数据管理器]");


        // win系统 反应时间
        winSleep();


        String lpClassName1 = "#32770";
        String lpWindowName1 = "扩展数据管理器";

        String lpClassName2 = "#32770";
        String lpWindowName2 = "TdxW";


        // ---------- [全部刷新] 按钮
        // 获取 [按钮]
        WinDef.HWND button1 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "全部刷新");
        // 窗口切换
        WinUtils.windowSwitcher(button1);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button1);
        log.info("---------------------------- 点击 [扩展数据管理器 - 全部刷新]");


        winSleep();


        // ---------- [多路并行 - 是(&Y)/否(&N)/取消] 按钮
        // 获取 [按钮]
        WinDef.HWND button2 = WinUtils.findWindowsButton(lpClassName2, lpWindowName2, "是(&Y)");
        // 窗口切换
        WinUtils.windowSwitcher(button2);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button2);
        log.info("---------------------------- 点击 [扩展数据管理器 - 全部刷新  -  多路并行-是(Y)]");


        winSleep();
    }

    /**
     * check .902   -   [扩展数据管理器]
     */
    private static void check_902() {

        long startTime = System.currentTimeMillis();
        // 2.5h
        double MAX_LIMIT_TIME = 2.5 * 60 * 60 * 1000;


        // 扩展数据 - 刷新完成
        boolean taskEnd = false;


        winSleep();


        while (true) {


            String lpClassName = "#32770";
            // String lpWindowName = "扩展数据管理器";


            // 获取 [#32770] - 所有 [窗口]   -   所有 [按钮]
            List<WinDef.HWND> windowList = WinUtils3.findWindowList(lpClassName, null);
            List<WinDef.HWND> allWinChildButtonList = WinUtils3.listAllChildButton(windowList);

            // 当前系统  全部[按钮]
            // List<WinDef.HWND> allWinChildButtonList = WinUtils3.listAllWinChildButton();


            // 遍历 [按钮]列表
            // --------------------------------------------------


            for (WinDef.HWND childButton : allWinChildButtonList) {


                // button  ->  父[窗口]

                WinDef.HWND parentWindow = null;
                String childButtonClassName = WinUtils3.getClassName(childButton);
                if ("Button".equals(childButtonClassName)) {
                    parentWindow = WinUtils3.getParentWindow(childButton);
                }


                if (parentWindow == null) {
                    break;
                }

                String parentWinClassName = WinUtils3.getClassName(parentWindow);
                String parentWinText = WinUtils3.getWindowText(parentWindow);


                // ---------------------------------


                // [按钮] - 文本
                String buttonText = WinUtils3.getWindowText(childButton);
//                if (StringUtils.isEmpty(buttonText)) {
//
//                    taskEnd = true;
//                    log.info("[扩展数据管理器]   ->   err     -     {} , {}", parentWinText, buttonText);
//                    // break;
//                }


                // 异常中断 - 弹窗
                boolean refreshBreakWin = "#32770".equals(parentWinClassName) && "TdxW".equals(parentWinText);
                if (refreshBreakWin && !StringUtils.isEmpty(buttonText) && (buttonText.contains("取消扩展数据刷新可能导致该数据异常，是否取消？") || buttonText.contains("是(&Y)") || buttonText.contains("否(&N))"))) {


                    // 中断 -> [否]
                    WinDef.HWND button = findButton(allWinChildButtonList, "TdxW", "#32770", "否(&N)");

                    // ----------
                    // 窗口句柄: native@0x730592
                    // 窗口标题: TdxW
                    // 窗口类名: #32770
                    // 按钮文本: 是(&Y)
                    // 按钮类名: Button
                    // ---
                    // 按钮文本: 否(&N)
                    // 按钮类名: Button
                    // ---
                    // 按钮文本: 取消扩展数据刷新可能导致该数据异常，是否取消？
                    // 按钮类名: Static
                    // ---
                    // ----------


                    // 异常中断
                    taskEnd = false;

                    // 窗口切换
                    WinUtils.windowSwitcher(button);
                    // 点击 [按钮]
                    WinUtils.clickMouseLeft(button);
                    log.info("---------------------------- 点击 [取消扩展数据刷新可能导致该数据异常，是否取消？ - 否]");
                    log.info("[扩展数据管理器]   ->   ing     -     {}", parentWinText);
                }


                // 正在刷新当前行情   /   正在计算/正在排名/正在存盘
                else if (Objects.requireNonNull(parentWinText).contains("路并行") || (!StringUtils.isEmpty(buttonText) && (buttonText.contains("正在刷新当前行情") || buttonText.contains("正在计算") || buttonText.contains("正在排名") || buttonText.contains("正在存盘")))) {


                    // ----------
                    // 窗口句柄: native@0x450314
                    // 窗口标题: 刷新数据
                    // 窗口类名: #32770
                    // 按钮文本: 正在刷新当前行情...[16%]
                    // 按钮类名: Static
                    // ----------

                    // ----------
                    // 窗口句柄: native@0x2406ea
                    // 窗口标题: 3路并行 1-3     /     2路并行 4-5     /     1路并行 6-6
                    // 窗口类名: #32770
                    // 按钮文本: 正在计算(4426/5312) 嵘泰股份   /   正在排名(583/1763) 20230216   /   正在存盘(4693/5307) 芯导科技
                    // 按钮类名: Static
                    // ----------


                    // ing
                    taskEnd = false;
                    log.info("[扩展数据管理器]   ->   ing     -     {}", parentWinText);


                    // --------------------------------------

                    // 超过 - 最大任务 num     ->     任务[结束]


                    // 1路并行 101-101     /     3路并行 111-113
                    if (!StringUtils.isEmpty(parentWinText) && parentWinText.contains("路并行")) {

                        // 任务编号
                        String numStr = parentWinText.split("路并行")[1].trim().split("-")[0];
                        Integer num = Integer.valueOf(numStr);


                        // 任务编号 >= 101     ->     [end]
                        if (num >= 101) {
                            taskEnd = true;
                            log.info("[扩展数据管理器]   ->   end     -     {} , {}", parentWinText, buttonText);

                            // break;
                        }
                    }


                }/** else {


                 taskEnd = true;
                 log.info("[扩展数据管理器]   ->   err     -     {} , {}", parentWinText, buttonText);
                 }*/


                // ------------------------------------ time limit


                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                if (totalTime > MAX_LIMIT_TIME) {
                    taskEnd = true;
                    log.info("[扩展数据管理器]   ->   err : [task_902 - 超时]     -     startTime : {} , endTime : {} , totalTime : {} , maxTime : {}", formatMillis(startTime), formatMillis(endTime), formatMillis(totalTime), formatMillis((long) MAX_LIMIT_TIME));
                }


                // ------------------------------------ taskEnd


                if (taskEnd) {


                    // 关闭窗口 - [扩展数据管理器]


                    // ----------
                    // 窗口句柄: native@0x72098c
                    // 窗口标题: 3路并行 114-116
                    // 窗口类名: #32770
                    // 按钮文本: 正在计算(1/1) 上证指数
                    // 按钮类名: Static
                    // ---
                    // 按钮文本: 取消
                    // 按钮类名: Button
                    // ---
                    // ----------


                    // ---------- [扩展数据管理器 - 取消]

                    // 获取 [按钮]
                    WinDef.HWND button_取消 = WinUtils.findWindowsButton(lpClassName, parentWinText, "取消");
                    // 窗口切换
                    WinUtils.windowSwitcher(button_取消);
                    // 点击 [按钮]
                    WinUtils.clickMouseLeft(button_取消);
                    log.info("---------------------------- 点击 [扩展数据管理器 - 取消]");


                    winSleep(3000);


                    // ----------
                    // 窗口句柄: native@0x8f042c
                    // 窗口标题: TdxW
                    // 窗口类名: #32770
                    // 按钮文本: 是(&Y)
                    // 按钮类名: Button
                    // ---
                    // 按钮文本: 否(&N)
                    // 按钮类名: Button
                    // ---
                    // ---
                    // 按钮文本: 取消扩展数据刷新可能导致该数据异常，是否取消？
                    //
                    //
                    //
                    // 按钮类名: Static
                    // ---
                    // ----------


                    // ---------- [扩展数据管理器 - 取消 - 是(&Y) / 否(&N)]


                    // 获取 [按钮]
                    WinDef.HWND button_取消_是 = WinUtils.findWindowsButton(lpClassName, "TdxW", "是(&Y)");
                    // 窗口切换
                    WinUtils.windowSwitcher(button_取消_是);
                    // 点击 [按钮]
                    WinUtils.clickMouseLeft(button_取消_是);
                    log.info("---------------------------- 点击 [扩展数据管理器 - 取消 - 是]");


                    return;
                }
            }


            // TODO   check频率  -  1次/3min
            winSleep(60000 * 3);
        }
    }


    /**
     * .921   -   [自动选股设置]
     */
    private static void _921() {


        // 切换 [tdx-主界面]   ->   选中[通达信]
        switchTdxWindow();


        // 键盘输入   ->   [.921]   -   打开 [自动选股]
        ArrayList<Integer> keyList = Lists.newArrayList(VK_PERIOD, VK_9, VK_2, VK_1);
        WinUtils.keyPress(keyList);
        log.info("---------------------------- 键盘输入 [.921]     >>>     打开 [自动选股设置]");


        winSleep();


        String lpClassName = "#32770";
        String lpWindowName = "自动选股设置";


        // ---------- [一键选股] 按钮
        // 获取 [按钮]
        WinDef.HWND button1 = WinUtils.findWindowsButton(lpClassName, lpWindowName, "一键选股");
        // 窗口切换
        WinUtils.windowSwitcher(button1);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button1);
        log.info("---------------------------- 点击 [自动选股设置 - 一键选股]");


        winSleep(5000);
    }

    /**
     * check .921   -   [自动选股设置]
     */
    private static void check_921() {

        long startTime = System.currentTimeMillis();
        // 2h
        double MAX_LIMIT_TIME = 2 * 60 * 60 * 1000;


        // 自动选股 完成
        boolean taskEnd = false;


        winSleep();


        while (true) {


            // String lpClassName = "#32770";
            String lpWindowName = "自动选股";


            // 获取 [#32770] - 所有 [窗口]   -   所有 [按钮]
            List<WinDef.HWND> windowList = WinUtils3.findWindowList(null, lpWindowName);
            List<WinDef.HWND> allWinChildWinList = WinUtils3.listAllChildWin(windowList);

            List<WinDef.HWND> allWinChildWinList2 = WinUtils3.listAllChildButton(null, "刷新数据");
            if (!CollectionUtils.isEmpty(allWinChildWinList2)) {
                allWinChildWinList.addAll(allWinChildWinList2);
            }


            // 当前系统  全部[按钮]
            // List<WinDef.HWND> allWinChildButtonList = WinUtils3.listAllWinChildButton();


            // 遍历 [按钮]列表
            // --------------------------------------------------


            // ---
            // 按钮文本: 正在刷新当前行情...[57%]
            // 按钮类名: Static
            // ---
            // 按钮文本: 取消
            // 按钮类名: Button
            // ---


            // ---
            // 按钮文本: 正在选股->25 [月多],请稍等...
            // 按钮类名: Static
            // ---
            // 按钮文本: 品种数
            // 按钮类名: Static
            // ---
            // 按钮文本: 3408
            // 按钮类名: Static
            // ---
            // 按钮文本: 选中数
            // 按钮类名: Static
            // ---
            // 按钮文本: 6路并行: 103/3.0%
            // 按钮类名: Static
            // ---
            // 按钮文本: 取消
            // 按钮类名: Button
            // ---


            if (CollectionUtils.isEmpty(allWinChildWinList)) {
                taskEnd = true;
                log.info("[自动选股]   ->   err     -     [找不到窗口-按钮] : {} , {}", windowList.size(), allWinChildWinList.size());
                return;
            }


            for (WinDef.HWND childButton : allWinChildWinList) {


                // button  ->  父[窗口]

                WinDef.HWND parentWindow = WinUtils3.getParentWindow(childButton);
                String childButtonClassName = WinUtils3.getClassName(childButton);


                if (parentWindow == null) {
                    break;
                }

                String parentWinClassName = WinUtils3.getClassName(parentWindow);
                String parentWinText = WinUtils3.getWindowText(parentWindow);


                // ---------------------------------


                // [按钮] - 文本
                String buttonText = WinUtils3.getWindowText(childButton);


                // 正在刷新当前行情...[57%]   /   正在选股->25 [月多],请稍等...   /   6路并行: 103/3.0%
                if (Objects.requireNonNull(parentWinText).contains("自动选股") && (!StringUtils.isEmpty(buttonText) && (buttonText.contains("正在刷新当前行情...") || buttonText.contains("正在选股->") || buttonText.contains("],请稍等...") || buttonText.contains("路并行")))) {

                    // ing
                    taskEnd = false;
                    log.info("[自动选股]   ->   ing     -     {} , {}", parentWinText, buttonText);


                    // --------------------------------------

                    // 超过 - 最大任务 num     ->     任务[结束]


                    // 正在选股->150 [美股],请稍等...
                    if (buttonText.contains("正在选股->")) {

                        // 任务编号
                        String numStr = buttonText.split("正在选股->")[1].trim().split(" \\[")[0].trim();
                        Integer num = Integer.valueOf(numStr);


                        // todo 任务编号 >= 145     ->     [end]
                        if (num >= 145) {
                            taskEnd = true;
                            log.info("[自动选股]   ->   end     -     {} , {}", parentWinText, buttonText);

                            // break;
                        }
                    }

                }


                // ------------------------------------ time limit


                long endTime = System.currentTimeMillis();
                long totalTime = endTime - startTime;

                if (totalTime > MAX_LIMIT_TIME) {
                    taskEnd = true;
                    log.info("[自动选股]   ->   err : [task_921 - 超时]     -     startTime : {} , endTime : {} , totalTime : {} , maxTime : {}", formatMillis(startTime), formatMillis(endTime), formatMillis(totalTime), formatMillis((long) MAX_LIMIT_TIME));
                }


                // ------------------------------------ taskEnd


                if (taskEnd) {


                    // 关闭窗口 - [自动选股]


                    // ----------
                    // 窗口标题: 自动选股
                    // 窗口类名: #32770
                    // ---
                    // 按钮文本: 取消
                    // 按钮类名: Button
                    // ---
                    // ----------


                    // ---------- [自动选股 - 取消]

                    // 获取 [按钮]
                    WinDef.HWND button_取消 = WinUtils.findWindowsButton(parentWinClassName, "自动选股", "取消");
                    // 窗口切换
                    WinUtils.windowSwitcher(button_取消);
                    // 点击 [按钮]
                    WinUtils.clickMouseLeft(button_取消);
                    log.info("---------------------------- 点击 [自动选股 - 取消]");


                    winSleep(3000);


                    // 未知bug   -   关闭-按钮【自动选股-取消】  点击无效
                    button_取消 = WinUtils.findWindowsButton(parentWinClassName, "自动选股", "取消");
                    if (null == button_取消) {
                        log.info("---------------------------- 点击 [自动选股 - 取消]   -   suc");
                        return;
                    }


                    log.error("---------------------------- 点击 [自动选股 - 取消]   -   err");
                }
            }


            // TODO   check频率  -  1次/5min
            winSleep(60000 * 5);
        }
    }


    /**
     * 遍历 buttonList   -   根据 buttonText  ->  查找 button
     *
     * @param hwndChildButtonList
     * @param winText             父级窗口 - 标题
     * @param winClassName        父级窗口 - 类名
     * @param buttonText          按钮 - 类名
     * @return
     */
    private static WinDef.HWND findButton(List<WinDef.HWND> hwndChildButtonList, String winText, String winClassName, String buttonText) {

        for (WinDef.HWND childButton : hwndChildButtonList) {


            WinDef.HWND parentWin = WinUtils3.getParentWindow(childButton);
            if (null == parentWin) {
                return null;
            }


            String parentWindowText = WinUtils3.getWindowText(parentWin);
            String parentClassName = WinUtils3.getClassName(parentWin);


            String childButtonText = WinUtils3.getWindowText(childButton);
            String className = WinUtils3.getClassName(childButton);


            if (Objects.equals(parentWindowText, winText) && Objects.equals(parentClassName, winClassName) && Objects.equals(childButtonText, buttonText)) {

                return childButton;
            }
        }

        return null;
    }


    /**
     * 打开 - [通达信]
     */
    private static void openTdx() {

        // tdx
        String appPath = "C:\\soft\\通达信\\v_2024\\new_tdx\\tdxw.exe";
        // tdx - 中信证券
        // String appPath2 = "C:\\soft\\通达信\\中信证券\\zd_zxzq_gm\\TdxW.exe";

        WinUtils.openApp(appPath);
    }


    /**
     * 关闭 - [开屏广告]
     */
    private static void closeTdxAds() {

        String lpClassName = "#32770";
        String lpWindowName = "通达信信息";


        // 获取 [开屏广告-窗口]
        WinDef.HWND window = WinUtils.findWindow(lpClassName, lpWindowName);
        // 窗口切换
        WinUtils.windowSwitcher(window);
        // 关闭 [窗口]
        WinUtils.closeWindow(window);
        log.info("---------------------------- 点击 [开屏广告 - 关闭]");


        winSleep();
    }


    /**
     * 切换 [tdx-主界面]   ->   选中[通达信]
     */
    private static void switchTdxWindow() {

        // 主界面 - 类名   ->   唯一
        String lpClassName = "TdxW_MainFrame_Class";

        // 窗口标题 不唯一   ->   设置为null
        String lpWindowName = null;
        // String lpWindowName = "通达信金融终端V7.65 - [行情报价-中期信号/月多/...]";


        WinUtils.windowSwitcher(lpClassName, lpWindowName);
    }


    /**
     * close [通达信]
     */
    private static void closeTdx() {


        // ----------  通达信 - [主程序]
        // 窗口句柄: native@0x3f0116
        // 窗口标题: 通达信金融终端V7.65 - [行情报价-中期信号]
        // 窗口类名: TdxW_MainFrame_Class


        // ----------  通达信 - [开屏广告]
        // 窗口句柄: native@0x1607f4
        // 窗口标题: 通达信信息
        // 窗口类名: #32770


        // ----------  通达信 - [主界面 / 默认界面]
        // 窗口句柄: native@0x210554
        // 窗口标题: 刷新行情
        // 窗口类名: #32770


        //----------  通达信 - [退出/重新登陆]
        // 窗口句柄: native@0x20079e
        // 窗口标题: 通达信金融终端
        // 窗口类名: #32770


        // ----------
        // 窗口句柄: native@0x2707a0
        // 窗口标题: 扩展数据管理器
        // 窗口类名: #32770

        // ----------
        // 窗口句柄: native@0x8607de
        // 窗口标题: TdxW
        // 窗口类名: #32770

        // ----------
        // 窗口句柄: native@0x210870
        // 窗口标题: 自动选股设置
        // 窗口类名: #32770


        String lpClassName1 = "TdxW_MainFrame_Class";
        String lpWindowName1 = null;
        // String lpWindowName1 = "通达信金融终端V7.65 - [行情报价-中期信号]";

        String lpClassName2 = "#32770";
        String lpWindowName2 = "通达信金融终端";


        // ---------- [关闭] 主界面-窗口
        // 获取 [主界面-窗口]
        WinDef.HWND window1 = WinUtils.findWindow(lpClassName1, lpWindowName1);
        // 窗口切换
        WinUtils.windowSwitcher(window1);
        // 点击 [关闭窗口]
        WinUtils.closeWindow(window1);
        log.info("---------------------------- 点击 [主界面 - 关闭]");


        // ---------- [退出] 按钮
        // 获取 [按钮]
        WinDef.HWND button2 = WinUtils.findWindowsButton(lpClassName2, lpWindowName2, "退出");
        // 窗口切换
        WinUtils.windowSwitcher(button2);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button2);
        log.info("---------------------------- 点击 [主界面 - 关闭 - 退出]");


        winSleep(5000);
    }


    /**
     * kill   ->   [通达信] exe进程
     */
    private static void killTdx() {
        WinUtils.killApp("tdxw.exe");
    }

}