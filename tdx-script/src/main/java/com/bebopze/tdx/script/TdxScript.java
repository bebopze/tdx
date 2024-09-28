package com.bebopze.tdx.script;

import com.bebopze.tdx.script.utils.WinUtils;
import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.bebopze.tdx.script.utils.SleepUtils.winSleep;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_2;


@Slf4j
public class TdxScript {


    public static void main(String[] args) {


        // .902   -   [扩展数据管理器]
        task_902();


        winSleep(5000);


        // .921   -   [自动选股]
        task_921();
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


        // WinUtils.killApp("tdxw.exe");
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
        String appPath = "C:\\soft\\通达信\\v_2024\\new_tdx\\tdxw.exe";
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
     * .902   -   扩展数据管理器
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
     * .921   -   自动选股
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
    }

}
