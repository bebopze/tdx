package com.bebopze.tdx.script.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.bebopze.tdx.script.utils.SleepUtils.winSleep;


/**
 * win系统  [窗口/按钮]       -       查询/获取  /  选中/点击
 */
@Slf4j
public class WinUtils {


    /**
     * 按下↓ - 鼠标左键
     */
    private static final int WM_LBUTTONDOWN = 0x0201;
    /**
     * 松开↑ - 鼠标左键
     */
    private static final int WM_LBUTTONUP = 0x0202;


    // -------------------------------------------- [查询 窗口/按钮] ------------------------------------------------------


    /**
     * 查询 [窗口]
     *
     * @param lpClassName  窗口 - 类名
     * @param lpWindowName 窗口 - 标题
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


    /**
     * 查询 [窗口-按钮]
     *
     * @param lpClassName  窗口 - 类名
     * @param lpWindowName 窗口 - 标题
     * @param buttonTitle  窗口 - 按钮标题
     * @return
     * @throws Exception
     */
    public static HWND findWindowsButton(String lpClassName, String lpWindowName, String buttonTitle) {


        HWND hWnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);
        if (hWnd != null) {
            System.out.println("找到窗口：[" + lpWindowName + "]");


            HWND hButton = User32.INSTANCE.FindWindowEx(hWnd, null, null, buttonTitle);
            if (hButton != null) {
                System.out.println("找到按钮：[" + buttonTitle + "]");

                return hButton;
            }


            System.out.println("未找到按钮");
        }


        System.out.println("未找到窗口");
        return null;
    }


    // -------------------------------------------- [切换 窗口/按钮] ------------------------------------------------------


    /**
     * 窗口切换     -     通过 [窗口类名/窗口标题]   查询并切换 窗口
     *
     * @param lpClassName  窗口类名
     * @param lpWindowName 窗口标题
     */
    public static void windowSwitcher(String lpClassName, String lpWindowName) {

        // 查询窗口
        HWND hwnd = findWindow(lpClassName, lpWindowName);


        // 切换窗口
        windowSwitcher(hwnd);
    }


    /**
     * 窗口切换（选中 -> 窗口）
     *
     * @param hwnd 窗口
     */
    public static void windowSwitcher(HWND hwnd) {

        if (hwnd != null) {
            // 将窗口 置于前台
            User32.INSTANCE.SetForegroundWindow(hwnd);


            // win系统 反应时间
            winSleep();
        }
    }


    public static void test01(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 最小化 [窗口]
        minimizesWindow(hwnd);

        // 最大化 [窗口]
        maximizesWindow(hwnd);

        // 关闭 [窗口]
        closeWindow(hwnd);
    }


    // ---------------------------------------- [窗口 最小化/最大化/关闭] --------------------------------------------------


    /**
     * 最小化 [窗口]
     *
     * @param hwnd
     */
    public static void minimizesWindow(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 发送 [最小化]消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.SC_MINIMIZE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 最小化");
        winSleep();
    }


    /**
     * 最大化 [窗口]
     *
     * @param hwnd
     */
    public static void maximizesWindow(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 发送 [最大化]消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.SC_MAXIMIZE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 最大化");
        winSleep();
    }


    /**
     * 关闭 [窗口]
     *
     * @param hwnd
     */
    public static void closeWindow(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 发送 [关闭]消息
        User32.INSTANCE.SendMessageTimeout(hwnd, WinUser.WM_CLOSE, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
        System.out.println("---------------------------- 关闭");


        // 需 大于   [msg] - timeout
        winSleep();
    }


    /**
     * 关闭 [窗口]
     *
     * @param hwnd
     */
    @Deprecated
    public static void closeWindow2(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 发送 [关闭]消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.WM_CLOSE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 关闭");


        winSleep();
    }


    // ------------------------------------------------ [键盘] ----------------------------------------------------------


    /**
     * 键盘输入
     *
     * @param keyList [按键] 列表
     */
    public static void keyPress(List<Integer> keyList) {


        try {
            Robot robot = new Robot();

            // .902
            for (Integer key : keyList) {
                robot.keyPress(key);
                winSleep(100);
            }


            // [回车] -> 确认
            robot.keyPress(KeyEvent.VK_ENTER);


            log.info("键盘输入  :  [{}]     ->     suc", keyList);


        } catch (Exception e) {

            log.error("键盘输入  :  [{}]     ->     fail : {}", keyList, e.getMessage(), e);
        }


        winSleep();
    }


    // ------------------------------------------------ [鼠标] ----------------------------------------------------------


    /**
     * 鼠标左键  -  点击[按钮] -> 松开[按钮]
     *
     * @param hwnd 窗口/按钮
     */
    public static void clickMouseLeft(HWND hwnd) {


        // 你可以使用 SendMessage/PostMessage     来模拟点击等操作


        /**
         * https://learn.microsoft.com/zh-cn/windows/win32/api/winuser/nf-winuser-sendmessage
         *
         *
         * https://learn.microsoft.com/zh-cn/windows/win32/winmsg/about-messages-and-message-queues#message-types
         *
         * https://learn.microsoft.com/zh-cn/windows/win32/controls/bumper-button-control-reference-notifications
         *
         *
         * ------ https://learn.microsoft.com/zh-cn/windows/win32/inputdev/mouse-input-notifications
         * ------ https://zhiyong.wang/archives/131#0x04-%E4%BD%BF%E7%94%A8%E5%8F%A5%E6%9F%84%E6%93%8D%E4%BD%9C%E7%AA%97%E5%8F%A3
         * ------ https://www.cnblogs.com/co.902
         * de1992/p/11239881.html
         *
         * --
         * -- https://stackoverflow.com/questions/5713730/c-sharp-press-a-button-from-a-window-using-user32-dll
         */


        // int MK_LBUTTON = 0x0001;
        // WinDef.WPARAM wparam = new WinDef.WPARAM(MK_LBUTTON);


        // 点击 [按钮]
        User32.INSTANCE.SendMessageTimeout(hwnd, WM_LBUTTONDOWN, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
        System.out.println("鼠标左键 - 点击 [按钮]");


        // 需 大于   [msg] - timeout
        winSleep();


        // 松开 [按钮]
        User32.INSTANCE.SendMessageTimeout(hwnd, WM_LBUTTONUP, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
        System.out.println("鼠标左键 - 松开 [按钮]");


        // 需 大于   [msg] - timeout
        winSleep();
    }


    // ------------------------------------------------ [cmd] ----------------------------------------------------------


    /**
     * 打开App
     *
     * @param appPath app路径
     */
    public static void openApp(String appPath) {

        try {
            // 打开App
            Process process = Runtime.getRuntime().exec(appPath);


            // 等待5s
            process.waitFor(5, TimeUnit.SECONDS);


            log.info("---------------------------- 打开App [{}]", appPath);


        } catch (Exception e) {

            log.error("openApp - fail     >>>     appPath : {}   ，  errMsg : {}   ,   ex : ", appPath, e.getMessage(), e);
        }
    }


    /**
     * kill App 进程
     *
     * @param exeName 进程名称     如：tdxw.exe
     */
    public static void killApp(String exeName) {

        // 获取 app进程  并关闭

        try {
            ProcessBuilder processBuilder = new ProcessBuilder("taskkill", "/IM", exeName, "/F");
            processBuilder.start();

            log.info("关闭App   ->   suc   ------------------------------");


        } catch (IOException e) {

            log.error("关闭App   ->   异常 : {}", e.getMessage(), e);
        }


        winSleep();
    }

}