package com.bebopze.tdx.script.utils;

import com.google.common.collect.Lists;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import lombok.extern.slf4j.Slf4j;

import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser.WNDENUMPROC;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;


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
    // 窗口句柄: native@0xac058a
    // 窗口标题: 盘后数据下载
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

        // List<HWND> hwndList = listAllWindows();


        // List<HWND> buttonList = listAllChildButton("#32770", "盘后数据下载");
        // List<HWND> buttonList = listAllChildButton("#32770", "扩展数据管理器");
        // List<HWND> buttonList = listAllChildButton("#32770", "自动选股设置");


        // List<HWND> buttonList = listAllChildButton(null, "自动选股");
        // List<HWND> buttonList = listAllChildButton(null, "刷新数据");


        // List<HWND> buttonList = listAllChildButton("#32770", null);
        // List<HWND> buttonList = listAllChildButton(null, "TdxW");


        // WinUtils.windowSwitcher("TdxW_MainFrame_Class", null);
        // windowSwitcher("TdxW_MainFrame_Class", "通达信金融终端V7.65 - [行情报价-月多]");
    }


    public static List<HWND> findWindowList(String lpClassName, String lpWindowName) {

        List<HWND> windowList = Lists.newArrayList();


        // 使用 EnumWindows 枚举所有窗口
        User32.INSTANCE.EnumWindows(new WNDENUMPROC() {


            @Override
            public boolean callback(HWND hwnd, Pointer arg) {


                // 获取每个窗口的 标题/类名
                String windowText = getWindowText(hwnd);
                String className = getClassName(hwnd);

                // 如果窗口的类名符合目标类名，则加入到列表
                if ((className != null && Objects.equals(className, lpClassName)) || (windowText != null && Objects.equals(windowText, lpWindowName))) {
                    windowList.add(hwnd);
                    System.out.println("找到 窗口标题: [" + lpWindowName + "]   窗口类名: [" + className + "]");
                }


                // 继续枚举下一个窗口
                return true;
            }

        }, null);


        return windowList;
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
                User32.INSTANCE.GetWindowText(hwnd, windowText, windowText.length);
                String wText = Native.toString(windowText);

                // 获取窗口类名
                User32.INSTANCE.GetClassName(hwnd, className, className.length);
                String wClassName = Native.toString(className);


                // 过滤 没有标题的窗口
                if (!wText.isEmpty()) {

                    System.out.println("窗口句柄: " + hwnd);
                    System.out.println("窗口标题: " + wText);
                    System.out.println("窗口类名: " + wClassName);


                    // 获取窗口的所有子控件并遍历它们
                    List<HWND> hwndChildButtonList = listAllChildButton(hwnd);


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
     * 获取 All[窗口]   的   All [按钮]
     *
     * @return
     */
    public static List<HWND> listAllWinChildButton() {

        // List<HWND> allWinList = Lists.newArrayList();
        List<HWND> allWinChildButtonList = Lists.newArrayList();


        // 枚举所有窗口
        User32.INSTANCE.EnumWindows(new WNDENUMPROC() {


            @Override
            public boolean callback(HWND hwnd, Pointer pointer) {


                char[] windowText = new char[512];
                char[] className = new char[512];


                // 获取窗口标题
                User32.INSTANCE.GetWindowText(hwnd, windowText, windowText.length);
                String wText = Native.toString(windowText);

                // 获取窗口类名
                User32.INSTANCE.GetClassName(hwnd, className, className.length);
                String wClassName = Native.toString(className);


                // 过滤 没有标题的窗口
                if (!wText.isEmpty()) {

                    System.out.println("窗口句柄: " + hwnd);
                    System.out.println("窗口标题: " + wText);
                    System.out.println("窗口类名: " + wClassName);


                    // 获取窗口的所有子控件并遍历它们
                    List<HWND> hwndChildButtonList = listAllChildButton(hwnd);
                    allWinChildButtonList.addAll(hwndChildButtonList);


                    System.out.println("----------");
                }


                // allWinList.add(hwnd);


                // 返回 true 继续枚举下一个窗口
                return true;
            }


        }, null);


        return allWinChildButtonList;
    }


    public static List<HWND> listAllChildButton(List<HWND> windowList) {

        List<HWND> hwndChildList = Lists.newArrayList();


        for (HWND win : windowList) {
            List<HWND> childButtonList = listAllChildButton(win);
            if (!CollectionUtils.isEmpty(childButtonList)) {
                hwndChildList.addAll(childButtonList);
            }
        }

        return hwndChildList;
    }


    /**
     * Java 获取  指定软件窗口 的 某个按钮       - chatgpt
     *
     * @param lpClassName  窗口 - 类名
     * @param lpWindowName 窗口 - 标题
     */
    public static List<HWND> listAllChildButton(String lpClassName, String lpWindowName) {

        // 查找指定窗口
        HWND hwnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);


        // 枚举窗口的所有子控件
        return listAllChildButton(hwnd);
    }


    public static List<HWND> listAllChildWin(List<HWND> windowList) {

        List<HWND> hwndChildList = Lists.newArrayList();


        for (HWND win : windowList) {
            List<HWND> childButtonList = listAllChildWin(win);
            if (!CollectionUtils.isEmpty(childButtonList)) {
                hwndChildList.addAll(childButtonList);
            }
        }

        return hwndChildList;
    }


//    public static List<HWND> listAllChildWin(String lpClassName, String lpWindowName) {
//
//        // 查找指定窗口
//        HWND hwnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);
//
//
//        // 枚举窗口的所有子控件
//        return listAllChildWin(hwnd);
//    }


    /**
     * 枚举窗口的所有   子控件
     *
     * @param hwnd -   指定窗口
     * @return
     */
    public static List<HWND> listAllChildWin(HWND hwnd) {

        List<HWND> hwndChildList = Lists.newArrayList();


        User32.INSTANCE.EnumChildWindows(hwnd, new WNDENUMPROC() {


            @Override
            public boolean callback(HWND hwndChild, Pointer arg) {


                System.out.println("---");

                // 获取每个子控件的文本
                String controlText = getWindowText(hwndChild);


                // 如果是按钮控件，获取按钮 - 文本/类名/句柄
                if (controlText != null && !controlText.isEmpty()) {
                    String className = getClassName(hwndChild);
                    // if ("Button".equals(className)) {
                    //   System.out.println("按钮文本: " + controlText);
                    //   System.out.println("按钮类名: " + className);
                    //   System.out.println("按钮句柄: " + hwndChild);
                    // }

                    hwndChildList.add(hwndChild);
                }


                System.out.println("---");


                // 继续枚举
                return true;
            }


        }, null);


        return hwndChildList;
    }


    /**
     * 枚举窗口的所有   子 button
     *
     * @param hwnd -   指定窗口
     * @return
     */
    public static List<HWND> listAllChildButton(HWND hwnd) {

        List<HWND> hwndChildList = Lists.newArrayList();


        User32.INSTANCE.EnumChildWindows(hwnd, new WNDENUMPROC() {


            @Override
            public boolean callback(HWND hwndChild, Pointer arg) {


                System.out.println("---");

                // 获取每个子控件的文本
                String controlText = getWindowText(hwndChild);


                // 如果是按钮控件，获取按钮 - 文本/类名/句柄
                if (controlText != null && !controlText.isEmpty()) {
                    String className = getClassName(hwndChild);
                    if ("Button".equals(className)) {
                        // System.out.println("按钮文本: " + controlText);
                        // System.out.println("按钮类名: " + className);
                        // System.out.println("按钮句柄: " + hwndChild);

                        hwndChildList.add(hwndChild);
                    }
                }


                System.out.println("---");


                // 继续枚举
                return true;
            }


        }, null);


        return hwndChildList;
    }


    /**
     * 获取控件的文本（适用于按钮和其他文本控件）
     *
     * @param hwnd
     * @return
     */
    public static String getWindowText(HWND hwnd) {
        char[] buffer = new char[512];
        int result = User32.INSTANCE.GetWindowText(hwnd, buffer, buffer.length);

        if (result > 0) {
            String windowText = Native.toString(buffer);
            System.out.println("按钮文本: " + windowText);

            return windowText;
        }

        return null;
    }


    /**
     * 获取控件的类名
     *
     * @param hwnd
     * @return
     */
    public static String getClassName(HWND hwnd) {
        char[] buffer = new char[256];
        User32.INSTANCE.GetClassName(hwnd, buffer, buffer.length);
        String className = Native.toString(buffer).trim();

        System.out.println("按钮类名: " + className);
        return className;
    }


    /**
     * 获取 - 父级窗口
     *
     * @param hwndChild 子按钮
     * @return
     */
    public static HWND getParentWindow(HWND hwndChild) {
        return User32.INSTANCE.GetParent(hwndChild);
    }


    /**
     * 获取窗口句柄 (根据窗口标题来查找)
     *
     * @param windowTitle
     * @return
     */
    public static HWND findWindow(String windowTitle) {
        User32 user32 = User32.INSTANCE;
        HWND hwnd = user32.FindWindow(null, windowTitle);
        if (hwnd == null) {
            System.out.println("窗口未找到");
        } else {
            System.out.println("窗口句柄: " + hwnd);
        }
        return hwnd;
    }

}