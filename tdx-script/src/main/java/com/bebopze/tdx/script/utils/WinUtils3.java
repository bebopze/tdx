package com.bebopze.tdx.script.utils;

import com.google.common.collect.Lists;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import lombok.extern.slf4j.Slf4j;

import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;

import java.util.List;


/**
 * test
 */
@Slf4j
public class WinUtils3 {


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

        List<HWND> hwndList = listAllWindows();


        WinUtils.windowSwitcher("TdxW_MainFrame_Class", null);
        // windowSwitcher("TdxW_MainFrame_Class", "通达信金融终端V7.65 - [行情报价-月多]");
    }


    /**
     * 获取 Win系统   -   所有窗口
     */
    public static List<HWND> listAllWindows() {

        List<HWND> hwndList = Lists.newArrayList();


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


                // 过滤 没有标题的窗口
                if (!wText.isEmpty()) {

                    System.out.println("窗口句柄: " + hwnd);
                    System.out.println("窗口标题: " + wText);
                    System.out.println("窗口类名: " + wClassName);

                    System.out.println("----------");
                }


                hwndList.add(hwnd);


                // 返回 true 继续枚举下一个窗口
                return true;
            }
        }, null);


        return hwndList;
    }


    /**
     * Java 获取  指定软件窗口 的 某个按钮       - chatgpt
     */
    public static void getWindowsButton() {


        // 查找指定窗口
        HWND hwnd = User32.INSTANCE.FindWindow("TdxW_MainFrame_Class", null);


        if (hwnd != null) {
            System.out.println("找到窗口句柄: " + hwnd);


            // 枚举窗口中的  子窗口（如按钮）
            User32.INSTANCE.EnumChildWindows(hwnd, new WNDENUMPROC() {


                @Override
                public boolean callback(HWND hwndChild, Pointer arg1) {


                    char[] windowText = new char[512];

                    User32.INSTANCE.GetWindowText(hwndChild, windowText, 512);
                    String wText = Native.toString(windowText);


                    if (wText.contains("功能")) {
                        System.out.println("找到按钮: " + wText);


                        // 模拟 [点击按钮]
                        WinUtils.clickMouseLeft(hwndChild);

                        //User32.INSTANCE.PostMessage(hwndChild, WinUser.BM_CLICK, null, null);
                        // User32.INSTANCE.PostMessage(hwndChild, 1, null, null);


                        // 找到后  终止枚举
                        return false;
                    }


                    // 继续查找 子窗口
                    return true;
                }


            }, null);
        }


        System.out.println("未找到指定窗口");
    }


//    /**
//     * 无效     -   todo   DEL
//     */
//    @Deprecated
//    public static void windowSwitcher2() {
//
//
//        // 获取当前活跃的窗口   ->   无效   -   windows 为null
//        Window[] windows = Window.getWindows();
//        for (Window window : windows) {
//            if (window.isActive()) {
//
//                // System.out.println("当前活跃的窗口: " + window.getTitle());
//                // 将当前窗口置于非活跃状态
//                window.dispose(); // 或者 window.setExtendedState(Frame.ICONIFIED);
//            }
//        }
//
//
//        // 假设我们知道微信窗口的标题，我们可以通过标题来找到并激活微信窗口
//        // 注意：这里的标题需要根据实际情况进行调整
//        String wechatTitle = "微信"; // 假设这是微信窗口的标题
//        try {
//            // 等待微信窗口出现
//            Thread.sleep(2000);
//            // 遍历所有窗口，找到微信窗口并使其活跃
//            for (Window window : windows) {
//
//                System.out.println(JSON.toJSONString(window));
//
////                if (wechatTitle.equals(window.getTitle())) {
////                    window.toFront(); // 将窗口带到前台
////                    window.requestFocus(); // 请求焦点
////                    window.setState(Frame.NORMAL); // 确保窗口是正常状态
////                    System.out.println("已切换到微信窗口");
////                    break;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }


}
