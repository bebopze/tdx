package com.bebopze.tdx.script;

import com.bebopze.tdx.script.utils.WinUtils;
import com.bebopze.tdx.script.utils.WinUtils2;
import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.WinDef;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;

import static com.bebopze.tdx.script.utils.SleepUtils.sleep;
import static java.awt.event.KeyEvent.*;
import static java.awt.event.KeyEvent.VK_2;


@Slf4j
public class TdxScript {


    public static void main(String[] args) {


        // open [通达信]
        String appPath = "C:\\soft\\通达信\\v_2024\\new_tdx\\tdxw.exe";
        WinUtils.openApp(appPath);


        // close [开屏广告]
        closeTdxAds();


        // .902   -   [扩展数据管理器]
        _902(); // TOOD 是/否


        // close [通达信]
        closeTdx();


        // WinUtils.killApp("tdxw.exe");
    }


    /**
     * .902   -   扩展数据管理器
     */
    private static void _902() {

        // 键盘输入   ->   [.902]   -   打开 [扩展数据管理器]
        ArrayList<Integer> keyList = Lists.newArrayList(VK_PERIOD, VK_9, VK_0, VK_2);
        WinUtils.keyPress(keyList);
        log.info("---------------------------- 键盘输入 [.902]     >>>     打开 [扩展数据管理器]");


        String lpClassName1 = "#32770";
        String lpWindowName1 = "扩展数据管理器";

        String lpClassName2 = "#32770";
        String lpWindowName2 = "TdxW";


        // ---------- [全部刷新] 按钮
        // 获取 [按钮]
        WinDef.HWND button1 = WinUtils.getWindowsButton(lpClassName1, lpWindowName1, "全部刷新");
        // 窗口切换
        WinUtils2.windowSwitcher(button1);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button1);
        log.info("---------------------------- 点击 [扩展数据管理器 - 全部刷新]");


        // ---------- [是] 按钮
        // 获取 [按钮]
        WinDef.HWND button2 = WinUtils.getWindowsButton(lpClassName2, lpWindowName2, "否");
        // 窗口切换
        WinUtils2.windowSwitcher(button2);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button2);
        log.info("---------------------------- 点击 [扩展数据管理器 - 全部刷新 - 是]");
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
        String lpWindowName1 = "通达信金融终端V7.65 - [行情报价-中期信号]";

        String lpClassName2 = "#32770";
        String lpWindowName2 = "通达信金融终端";


        // ---------- [关闭] 主界面-窗口
        // 获取 [主界面-窗口]
        WinDef.HWND window1 = WinUtils2.findWindow(lpClassName1, lpWindowName1);
        // 窗口切换
        WinUtils2.windowSwitcher(window1);
        // 点击 [关闭窗口]
        WinUtils.closeWindow(window1);
        log.info("---------------------------- 点击 [主界面 - 关闭]");


        // ---------- [退出] 按钮
        // 获取 [按钮]
        WinDef.HWND button2 = WinUtils.getWindowsButton(lpClassName2, lpWindowName2, "退出");
        // 窗口切换
        WinUtils2.windowSwitcher(button2);
        // 点击 [按钮]
        WinUtils.clickMouseLeft(button2);
        log.info("---------------------------- 点击 [主界面 - 关闭 - 退出]");
    }


    /**
     * 关闭 [开屏广告]
     */
    private static void closeTdxAds() {

        String lpClassName = "#32770";
        String lpWindowName = "通达信信息";


        // 获取 [开屏广告-窗口]
        WinDef.HWND window = WinUtils2.findWindow(lpClassName, lpWindowName);
        // 窗口切换
        WinUtils2.windowSwitcher(window);
        // 关闭 [窗口]
        WinUtils.closeWindow(window);
        log.info("---------------------------- 点击 [开屏广告 - 关闭]");


        sleep(500);
    }


}
