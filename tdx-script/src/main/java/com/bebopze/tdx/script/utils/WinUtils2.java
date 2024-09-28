package com.bebopze.tdx.script.utils;

import com.alibaba.fastjson.JSON;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import lombok.extern.slf4j.Slf4j;


import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;


import java.awt.*;


@Slf4j
public class WinUtils2 {


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


    // ----------
    // 窗口句柄: native@0x1a05e0
    // 窗口标题: GDI+ Window (tdxw.exe)
    // 窗口类名: GDI+ Hook Window Class


    // ----------
    // 窗口句柄: native@0x3e086e
    // 窗口标题: 新标签页 - Google Chrome
    // 窗口类名: Chrome_WidgetWin_1

    public static void main(String[] args) {

        listAllWindows();


        windowSwitcher("TdxW_MainFrame_Class", "通达信金融终端V7.65 - [行情报价-中期信号]");
    }

    public static void listAllWindows() {


        // 枚举所有窗口
        User32.INSTANCE.EnumWindows(new WNDENUMPROC() {

            @Override
            public boolean callback(HWND hwnd, Pointer pointer) {
                char[] windowText = new char[512];
                char[] className = new char[512];

                // 获取窗口标题
                User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
                String wText = Native.toString(windowText);

                // 获取窗口类名
                User32.INSTANCE.GetClassName(hwnd, className, 512);
                String wClassName = Native.toString(className);

                // 过滤掉没有标题的窗口
                if (!wText.isEmpty()) {
                    System.out.println("窗口句柄: " + hwnd);
                    System.out.println("窗口标题: " + wText);
                    System.out.println("窗口类名: " + wClassName);
                    System.out.println("----------");
                }


                // 返回 true 继续枚举下一个窗口
                return true;
            }
        }, null);
    }


//    public static void main(String[] args) {
//        // 查找 QQ 和 微信窗口句柄
//        WinDef.HWND hwnd = User32.INSTANCE.FindWindow(null, "");
//        WinDef.HWND qqHwnd = User32.INSTANCE.FindWindow(null, "Google Chrome");
//        WinDef.HWND tdxHwnd = User32.INSTANCE.FindWindow(null, "通达信");
//
//        if (tdxHwnd != null) {
//            System.out.println("找到[通达信]窗口句柄: " + tdxHwnd);
//
//            // 激活微信窗口，使其成为前台窗口
//            User32.INSTANCE.SetForegroundWindow(tdxHwnd);
//
//            // 验证当前激活的窗口是否是微信
//            WinDef.HWND activeWindow = User32.INSTANCE.GetForegroundWindow();
//            char[] windowText = new char[512];
//            User32.INSTANCE.GetWindowText(activeWindow, windowText, 512);
//            String activeWindowTitle = Native.toString(windowText);
//            if (activeWindowTitle.contains("通达信")) {
//                System.out.println("成功切换到[通达信]窗口!");
//            } else {
//                System.out.println("切换窗口失败，当前窗口: " + activeWindowTitle);
//            }
//        } else {
//            System.out.println("未找到[通达信]窗口");
//        }
//    }


    /**
     * 窗口切换
     *
     * @param hwnd 窗口
     */
    public static void windowSwitcher(HWND hwnd) {

        if (hwnd != null) {
            // 将窗口 置于前台
            User32.INSTANCE.SetForegroundWindow(hwnd);
        }
    }

    /**
     * 窗口切换     -     通过 [窗口类名/窗口标题]   查询并切换 窗口
     *
     * @param lpClassName  窗口类名
     * @param lpWindowName 窗口标题
     */
    public static void windowSwitcher(String lpClassName, String lpWindowName) {

        // 查询窗口
        HWND hwnd = findWindow(lpClassName, lpWindowName);

        if (hwnd != null) {
            // 将窗口 置于前台
            User32.INSTANCE.SetForegroundWindow(hwnd);
        }
    }


    /**
     * 查询窗口     -     通过 [窗口类名/窗口标题]   查询 窗口
     *
     * @param lpClassName  窗口类名
     * @param lpWindowName 窗口标题
     */
    public static HWND findWindow(String lpClassName, String lpWindowName) {

        HWND hwnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);


        char[] windowText = new char[512];
        char[] className = new char[512];


        // 获取窗口标题
        User32.INSTANCE.GetWindowText(hwnd, windowText, 512);
        String wText = Native.toString(windowText);

        // 获取窗口类名
        User32.INSTANCE.GetClassName(hwnd, className, 512);
        String wClassName = Native.toString(className);

        // 过滤掉没有标题的窗口
        if (!wText.isEmpty()) {
            System.out.println("窗口句柄: " + hwnd);
            System.out.println("窗口标题: " + wText);
            System.out.println("窗口类名: " + wClassName);
            System.out.println("----------");
        }


        return hwnd;
    }


    // WindowSwitcher
    public static void windowSwitcher2() {


        // 获取当前活跃的窗口
        Window[] windows = Window.getWindows();
        for (Window window : windows) {
            if (window.isActive()) {

                // System.out.println("当前活跃的窗口: " + window.getTitle());
                // 将当前窗口置于非活跃状态
                window.dispose(); // 或者 window.setExtendedState(Frame.ICONIFIED);
            }
        }


        // 假设我们知道微信窗口的标题，我们可以通过标题来找到并激活微信窗口
        // 注意：这里的标题需要根据实际情况进行调整
        String wechatTitle = "微信"; // 假设这是微信窗口的标题
        try {
            // 等待微信窗口出现
            Thread.sleep(2000);
            // 遍历所有窗口，找到微信窗口并使其活跃
            for (Window window : windows) {

                System.out.println(JSON.toJSONString(window));

//                if (wechatTitle.equals(window.getTitle())) {
//                    window.toFront(); // 将窗口带到前台
//                    window.requestFocus(); // 请求焦点
//                    window.setState(Frame.NORMAL); // 确保窗口是正常状态
//                    System.out.println("已切换到微信窗口");
//                    break;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


}
