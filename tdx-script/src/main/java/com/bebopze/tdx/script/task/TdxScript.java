package com.bebopze.tdx.script.task;

import com.bebopze.tdx.script.utils.WinUtils;
import com.bebopze.tdx.script.utils.WinUtils3;
import com.bebopze.tdx.script.utils.WindowTextReader;
import com.google.common.collect.Lists;
import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

import static com.bebopze.tdx.script.utils.SleepUtils.winSleep;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_2;


@Slf4j
public class TdxScript {


    public static void main(String[] args) {


        // .933   -   [盘后数据下载]
        task_933();


        winSleep(3000);
        // check
        // check_933();


        // .902   -   [扩展数据管理器]
        // task_902();


        // winSleep(3000);


        // .921   -   [自动选股]
        // task_921();
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


    public static void check_933() {


        // 下载完成
        boolean taskEnd = false;


        while (true) {


            // 切换 [tdx-主界面]   ->   选中[通达信]
            switchTdxWindow();


//            // 键盘输入   ->   [.933]   -   打开 [盘后数据下载]
//            ArrayList<Integer> keyList = Lists.newArrayList(VK_PERIOD, VK_9, VK_3, VK_3);
//            WinUtils.keyPress(keyList);
//            log.info("---------------------------- 键盘输入 [.933]     >>>     打开 [盘后数据下载]");
//
//
//            winSleep();


            String lpClassName1 = "#32770";
            String lpWindowName1 = "盘后数据下载";


            // ---------- [沪深京日线] 按钮
            // 获取 [按钮]
            // WinDef.HWND button1 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "沪深京日线*");
            // 开始下载  ->  取消下载（下载中）  ->  开始下载（下载完毕）
            WinDef.HWND button_取消下载 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "取消下载");
            WinDef.HWND button_开始下载 = WinUtils.findWindowsButton(lpClassName1, lpWindowName1, "开始下载");
            // 窗口切换
            // WinUtils.windowSwitcher(button_取消下载);
            // 点击 [按钮]
//            WinUtils.clickMouseLeft(button1);
//            log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线]");


            // winSleep();


            // 获取 [task-窗口]   -   [内容]


//            // 获取当前活动窗口的句柄
//            // WinDef.HWND hwnd = WindowTextReader.User32Ex.INSTANCE.GetForegroundWindow();
//            WinDef.HWND hwnd = button_取消下载 != null ? button_取消下载 : button_开始下载;
//
//
//            // 读取窗口标题
//            byte[] windowText = new byte[512];
//            WindowTextReader.User32Ex.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
//            System.out.println("Window Title: " + Native.toString(windowText));
//
//
//            // 通过 FindWindowEx 查找窗口中的控件，假设你知道  按钮的类名和名称
//            WinDef.HWND buttonHwnd = WindowTextReader.User32Ex.INSTANCE.FindWindowEx(hwnd, null, lpClassName1, lpWindowName1);
//            if (buttonHwnd != null) {
//                byte[] buttonText = new byte[512];
//                WindowTextReader.User32Ex.INSTANCE.GetWindowTextA(buttonHwnd, buttonText, 512);
//                System.out.println("Button Text: " + Native.toString(buttonText));
//            } else {
//                System.out.println("Button not found");
//            }


            if (button_取消下载 != null) {
                // 下载中
                taskEnd = false;
                // System.out.println("[盘后数据]   ->   ing");
                log.info("[盘后数据]   ->   ing");

                winSleep(30000);


            } else if (button_开始下载 != null) {
                // 下载完成
                taskEnd = true;
                // System.out.println("[盘后数据]   ->   end");
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
     * 执行task     ==>     .902   -   [扩展数据管理器]
     */
    public static void task_902() {


        // open [通达信]
        openTdx();


        // close [开屏广告]
        closeTdxAds();


        // .902   -   [扩展数据管理器]
        _902(); // TOOD 是/否


        // close [通达信]
        closeTdx();


        // killTdx();
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


        // close [通达信]
        closeTdx();
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


        // ---------- [沪深京日线] 按钮
//        // 获取 [按钮]
//        WinDef.HWND button1 = WinUtils.findWindowsButton(lpClassName, lpWindowName, "沪深京日线*");
//        // 窗口切换
//        WinUtils.windowSwitcher(button1);
//        // 点击 [按钮]
//        WinUtils.clickMouseLeft(button1);
//        log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线]");


        // 获取 [盘后数据下载]窗口   -   所有 [按钮]
        WinDef.HWND window = WinUtils.findWindow(lpClassName, lpWindowName);
        List<WinDef.HWND> hwndChildButtonList = WinUtils3.listAllChildButton(window);


        // 遍历 [按钮]列表
        // --------------------------------------------------

        // .933 - 盘后数据下载
        //
        // 沪深京日线              -   日线和实时行情数据                   /     下载所有AB股类品种的日线数据
        // 沪深京分钟线            -   1分钟线数据 / 5分钟线数据             /     下载所有AB股类品种的日线数据
        //
        // 沪深京分时图            -   当日分时图数据(仅供当天脱机分析使用)     /     添加品种 / 移出品种 / 清空品种
        // 扩展市场行情日线         -   日线数据                            /     添加品种 / 移出品种 / 清空品种
        // 扩展市场行情分钟线       -   1分钟线数据 / 5分钟线数据              /     添加品种 / 移出品种 / 清空品种
        //
        //
        // 开始下载 / 关闭


        for (WinDef.HWND childButton : hwndChildButtonList) {

            // [按钮] - 文本
            String buttonText = WinUtils3.printControlText(childButton);
            // 沪深京日线   -   日线和实时行情数据   -   开始下载
            if ("日线和实时行情数据".equals(buttonText)) {

                // 窗口切换
                WinUtils.windowSwitcher(childButton);
                // 点击 [按钮]
                WinUtils.clickMouseLeft(childButton);
                log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线 - 日线和实时行情数据]");


                // --------


                for (WinDef.HWND childButton2 : hwndChildButtonList) {

                    // [按钮] - 文本
                    String buttonText2 = WinUtils3.printControlText(childButton2);
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
        // .921 - 自动选股模式   -   一键全部选股 / 每板块单独选股 / 添加方案 / 修改方案 / 删除方案 / 前移 / 后移 / 执行方案 / 一键选股 / 打开板块 / 关闭

        // .933 - 盘后数据下载
        //
        // 沪深京日线              -   日线和实时行情数据                         （native@0x5d0582）   /   下载所有AB股类品种的日线数据（native@0x120984）
        // 沪深京分钟线            -   1分钟线数据（native@0x1208a8） / 5分钟线数据（native@0x150a02）    /   ~
        //
        // 沪深京分时图            -   当日分时图数据(仅供当天脱机分析使用)          （native@0x2a09c0）    /   添加品种（native@0x2c0314） / 移出品种（native@0x1e06ca） / 清空品种（native@0x5e008e）
        // 扩展市场行情日线         -   日线数据                                 （native@0x120a1e）    /   ~
        // 扩展市场行情分钟线       -   1分钟线数据（native@0x1309f2） / 5分钟线数据（native@0x1209f4）    /   ~
        //
        //
        // 沪深京日线/沪深京分钟线                       -   下载所有AB股类品种的日线数据（native@0x120984）
        // 沪深京分时图/扩展市场行情日线/扩展市场行情分钟线   -   添加品种（native@0x2c0314） / 移出品种（native@0x1e06ca） / 清空品种（native@0x5e008e）

        //  下载所有港股品种日线数据 / 下载所有美股品种日线数据（native@0x130a1e）
        //
        // 开始下载（native@0x6f0022） / 关闭（native@0x906ea）


//        winSleep();
//
//
//        // ---------- [开始下载] 按钮
//        // 获取 [按钮].933
//
//        WinDef.HWND button2 = WinUtils.findWindowsButton(lpClassName, lpWindowName, "开始下载");
//        // 窗口切换
//        WinUtils.windowSwitcher(button2);
//        // 点击 [按钮]
//        WinUtils.clickMouseLeft(button2);
//        log.info("---------------------------- 点击 [盘后数据下载 - 沪深京日线 - 开始下载]");


        winSleep();
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


        // ---------- [多路并行-是/否] 按钮
        // 获取 [按钮]
        WinDef.HWND button2 = WinUtils.findWindowsButton(lpClassName2, lpWindowName2, "取消");
        // 窗口切换
        WinUtils.windowSwitcher(button2);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button2);
        log.info("---------------------------- 点击 [扩展数据管理器 - 全部刷新  -  多路并行-是(Y)]");


        winSleep();
    }


    /**
     * .921   -   [自动选股]
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
