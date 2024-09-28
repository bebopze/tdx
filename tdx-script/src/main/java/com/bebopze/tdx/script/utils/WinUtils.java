package com.bebopze.tdx.script.utils;

import com.google.common.collect.Lists;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static com.bebopze.tdx.script.utils.SleepUtils.sleep;
import static java.awt.event.KeyEvent.*;


@Slf4j
public class WinUtils {


    private static final int WM_LBUTTONDOWN = 0x0201;
    private static final int WM_LBUTTONUP = 0x0202;


    public static void main(String[] args) throws Exception {


        openAndCloseWinApp();


        // Thread.sleep(3000);


        // getWindowsButton2();


        // [窗口-按钮] - 坐标
        winButtonCoordinate();


        // openAndCloseMacApp();

        // sshWin();
    }


    /**
     * [窗口-按钮] - 坐标
     */
    public static void winButtonCoordinate() throws Exception {

        // 获取  [窗口] - [按钮]
        HWND hButton = getWindowsButton(null, "扩展数据管理器", "全部刷新");


        if (hButton != null) {

            WinUser.RECT rect = new WinUser.RECT();
            User32.INSTANCE.GetWindowRect(hButton, rect);


            int x = rect.left;
            int y = rect.top;
            int width = rect.right - rect.left;
            int height = rect.bottom - rect.top;

            System.out.println("Button Coordinates: (" + x + ", " + y + "), Width: " + width + ", Height: " + height);
        } else {
            System.out.println("Button not found");
        }
    }


    /**
     * Java 获取  指定软件窗口 的 某个按钮       - chatgpt
     */
    public static void getWindowsButton2() {
        // 查找指定窗口
        HWND hwnd = User32.INSTANCE.FindWindow(null, "通达信金融客户端");

        if (hwnd != null) {
            System.out.println("找到窗口句柄: " + hwnd);

            // 枚举窗口中的子窗口（如按钮）

            User32.INSTANCE.EnumChildWindows(hwnd, new WinUser.WNDENUMPROC() {
                @Override
                public boolean callback(HWND hwndChild, Pointer arg1) {
                    char[] windowText = new char[512];
                    User32.INSTANCE.GetWindowText(hwndChild, windowText, 512);
                    String wText = Native.toString(windowText);
                    if (wText.contains("保存")) {
                        System.out.println("找到按钮: " + wText);

                        // 模拟点击按钮


//                        User32.INSTANCE.PostMessage(hwndChild, WinUser.BM_CLICK, null, null);
                        User32.INSTANCE.PostMessage(hwndChild, 1, null, null);
                        return false; // 找到后终止枚举
                    }
                    return true; // 继续查找子窗口
                }
            }, null);
        } else {
            System.out.println("未找到指定窗口");
        }
    }


    /**
     * Java 获取  指定软件窗口 的 某个按钮       - kimi
     *
     * @param lpClassName  窗口类名
     * @param lpWindowName 窗口标题
     * @param buttonTitle  窗口 - 按钮标题
     * @return
     * @throws Exception
     */
    public static HWND getWindowsButton(String lpClassName, String lpWindowName, String buttonTitle) {


        HWND hWnd = User32.INSTANCE.FindWindow(lpClassName, lpWindowName);

        if (hWnd != null) {
            System.out.println("找到窗口: " + hWnd);

            HWND hButton = User32.INSTANCE.FindWindowEx(hWnd, null, null, buttonTitle);

            if (hButton != null) {
                System.out.println("找到按钮: " + hButton);

                return hButton;

            } else {
                System.out.println("未找到按钮");
            }
        } else {


            System.out.println("未找到窗口");
        }


        return null;
    }


    public static void getWindowInfo(HWND hwnd) {
        WinUser.WINDOWINFO windowInfo = new WinUser.WINDOWINFO();
        User32.INSTANCE.GetWindowInfo(hwnd, windowInfo);


        // 这里可以获取窗口的信息，但按钮通常没有文本标题
        // 你可以使用 Spy++ 工具来获取按钮的更多信息

        // 如果你需要模拟点击操作，可以使用 SendInput 方法
        // 例如，模拟点击关闭按钮可能需要找到关闭按钮的确切位置
    }


    public static void controlSystemButtons(HWND hwnd) {
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


        try {


            // 点击 [按钮]
            User32.INSTANCE.SendMessageTimeout(hwnd, WinUser.WM_CLOSE, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
            System.out.println("---------------------------- 关闭");


            Thread.sleep(50);


        } catch (Exception e) {

            log.error("鼠标点击   ->   异常 : {}", e.getMessage());
        }
    }

    /**
     * 关闭 [窗口]
     *
     * @param hwnd
     */
    public static void closeWindow2(HWND hwnd) {
        if (hwnd == null) {
            System.out.println("未找到窗口");
            return;
        }


        // 发送关闭消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.WM_CLOSE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 关闭");
        sleep(1500);
    }

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


        // 发送 最小化消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.SC_MINIMIZE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 最小化");
        sleep(1500);
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


        // 发送最大化消息
        User32.INSTANCE.PostMessage(hwnd, WinUser.WM_SYSCOMMAND, new WinDef.WPARAM(WinUser.SC_MAXIMIZE), new WinDef.LPARAM(0));
        System.out.println("---------------------------- 最大化");
        sleep(1500);
    }


    /**
     * 鼠标左键  -  点击[按钮] -> 松开[按钮]
     *
     * @param hwnd
     */
    public static void clickMouseLeft(HWND hwnd) {


        try {

            // 获取  [窗口] - [按钮]
            // HWND hButton = getWindowsButton(null, "扩展数据管理器", "全部刷新");


            // 你可以使用hButton来模拟点击等操作

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


            sleep(100);


            // 点击 [按钮]
            User32.INSTANCE.SendMessageTimeout(hwnd, WM_LBUTTONDOWN, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
            System.out.println("鼠标左键 - 点击 [按钮]");


            sleep(50);


            // 松开 [按钮]
            User32.INSTANCE.SendMessageTimeout(hwnd, WM_LBUTTONUP, null, null, WinUser.SMTO_ABORTIFHUNG, 50, null);
            System.out.println("鼠标左键 - 松开 [按钮]");


        } catch (Exception e) {

            log.error("鼠标点击   ->   异常 : {}", e.getMessage());
        }
    }


    public static void openAndCloseWinApp() {


        // 打开App
        openApp("tdx path");


        // 获取窗口


        // 键盘输入   ->   [.902]
        ArrayList<Integer> list = Lists.newArrayList(VK_PERIOD, VK_9, VK_0, VK_2);
        keyPress(list);


        // 获取当前 鼠标-[坐标点]
        getMousePointerInfo();


        // 移动鼠标 - 指定[坐标点]
        mouseMove(2078, 702);


        // 获取当前 鼠标-[坐标点]
        getMousePointerInfo();


        // 点击鼠标 - 左键
        clickMouseLeft2();


        // 关闭App
        // closeApp();
    }


    /**
     * 打开App
     *
     * @param appPath app路径
     */
    public static void openApp(String appPath) {

        try {
            // 打开App
            Process process = Runtime.getRuntime().exec(appPath);


            // 等待10s
            process.waitFor(5, TimeUnit.SECONDS);


            log.info("---------------------------- 打开App [{}]", appPath);


        } catch (Exception e) {

            log.error("openApp - fail     >>>     appPath : {}   ，  errMsg : {}   ,   ex : ", appPath, e.getMessage(), e);
        }
    }


    /**
     * 键盘输入
     *
     * @param keyList [按键] 列表
     */
    public static void keyPress(ArrayList<Integer> keyList) {


        try {
            Robot robot = new Robot();

            // .902
            for (Integer key : keyList) {
                robot.keyPress(key);
                sleep(100);
            }


            // [回车] -> 确认
            robot.keyPress(KeyEvent.VK_ENTER);


            log.info("键盘输入  :  [{}]     ->     suc", keyList);


        } catch (Exception e) {
            log.error("键盘输入  :  [{}]     ->     fail : {}", keyList, e.getMessage());
        }


        sleep(500);
    }


    /**
     * 移动鼠标   ->   指定 [坐标点]
     *
     * @param x
     * @param y
     */
    public static void mouseMove(int x, int y) {

        try {
            Robot robot = new Robot();
            robot.delay(1000);


            Point point1 = new Point(1, 1).getLocation();
            Point point2 = new Point(0, 0).getLocation();
            Point point3 = new Point(x, y).getLocation();


            robot.mouseMove(point1.x, point1.y); // 将鼠标移动到屏幕的(100, 100)位置
            Thread.sleep(500);
            getMousePointerInfo();

            robot.mouseMove(point2.x, point2.y);
            Thread.sleep(500);
            getMousePointerInfo();

            robot.mouseMove(point3.x, point3.y);
            getMousePointerInfo();


            log.info("鼠标移动  :  [{},{}]     ->     suc   ------------------------------", x, y);


            Thread.sleep(2000);


        } catch (Exception e) {
            log.error("鼠标移动   ->   异常 : {}", e.getMessage());
        }
    }


    /**
     * 获取当前 鼠标的 坐标点
     */
    public static void getMousePointerInfo() {


        PointerInfo pointerInfo = MouseInfo.getPointerInfo();
        Point point = pointerInfo.getLocation();


        // [全部刷新] 坐标   -   [2143, 730]
        int x = point.x;
        int y = point.y;


        log.info("鼠标位置 : [{},{}]   ->   suc   ------------------------------", x, y);


    }


    /**
     * 点击鼠标 - 左键
     */
    public static void clickMouseLeft2() {


        try {
            Robot robot = new Robot();
            robot.delay(1000);


            // 按下鼠标左键
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(50);

            // 释放鼠标左键
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);


            log.info("鼠标点击   ->   suc   ------------------------------");
            getMousePointerInfo();


            Thread.sleep(500);


        } catch (Exception e) {
            log.error("鼠标点击   ->   异常 : {}", e.getMessage());
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
            log.error("关闭App   ->   异常 : {}", e.getMessage());
        }
    }

    public static void openApp2(String appPath) throws Exception {


        // 获取Mac应用程序的可执行文件路径
        // String appPath = "/Applications/YourApp.app/Contents/MacOS/YourApp";

        // 构建执行命令的进程
        ProcessBuilder processBuilder = new ProcessBuilder(appPath);

        // 启动进程
        Process process = processBuilder.start();

        // 获取进程的输出流
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line;
        while ((line = reader.readLine()) != null) {
            // 处理应用程序的输出信息
            System.out.println(line);
        }

        // 等待进程执行结束
        int exitCode = process.waitFor();
        System.out.println("应用程序执行结束，退出码：" + exitCode);
    }


    public static void openAndCloseMacApp() throws Exception {

        // 打开 Safari
        System.out.println("Opening Safari...");
        Runtime.getRuntime().exec("open -a Safari");

        // 等待 5 秒后关闭 Safari
        Thread.sleep(5000);

        // 使用 AppleScript 优雅关闭 Safari
        System.out.println("Closing Safari...");
        Runtime.getRuntime().exec("killall Safari");
        // Runtime.getRuntime().exec("osascript -e 'quit app \"Safari\"'");
    }

    public static void openAndCloseWinApp2() throws Exception {


        // 打开记事本
        System.out.println("Opening tdx...");
        Runtime.getRuntime().exec("/Volumes/[C] Windows 11.hidden/soft/通达信/v_2024/new_tdx/tdxw.exe");

        // 等待 5 秒后关闭记事本
        Thread.sleep(5000);

        // 关闭记事本
        System.out.println("Closing tdx...");
        Runtime.getRuntime().exec("taskkill /F /IM tdxw.exe");
    }


    public static void sshWin() throws Exception {

        String windowsIp = "192.168.0.1";  // Windows 虚拟机的 IP 地址
        String username = "xxx"; // Windows 虚拟机的用户名
        String password = ""; // Windows 虚拟机的密码
        String command = "C:\\soft\\通达信\\v_2024\\new_tdx\\tdxw.exe";    // 远程执行的命令

        // 构建 SSH 命令
        String sshCommand = String.format("ssh %s@%s %s", username, windowsIp, command);

        // 执行 SSH 命令
        Process process = Runtime.getRuntime().exec(sshCommand);
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        String line;
        while ((line = reader.readLine()) != null) {
            System.out.println(line);
        }

    }

    public static void test01() throws Exception {


        //
        Process exec = Runtime.getRuntime().exec("/Applications/NetEaseMusic.app/Contents/MacOS/NetEaseMusic");


        // 等待进程执行结束
//        int exitCode = exec.waitFor();
//        System.out.println("应用程序执行结束，退出码：" + exitCode);


//        InputStream is = exec.getInputStream();
//
//        int line;
//        while ((line = is.read()) != 0) {
//            // 处理应用程序的输出信息
//            System.out.println(line);
//        }


//        Thread.sleep(5000L);
        System.out.println("打开 Mac电脑💻   网易云音乐🎵App");


//        Robot robot = new Robot();
//        robot.delay(5000);

//        //先模拟输入主方法
//        robot.keyPress(KeyEvent.VK_P);
//        robot.keyRelease(KeyEvent.VK_P);
//        robot.delay(100);

//        robot.keyPress(KeyEvent.VK_S);
//        robot.keyRelease(KeyEvent.VK_S);
//        robot.delay(100);

//        robot.keyPress(KeyEvent.VK_V);
//        robot.keyRelease(KeyEvent.VK_V);
//        robot.delay(100);

//        robot.keyPress(KeyEvent.VK_M);
//        robot.keyRelease(KeyEvent.VK_M);

    }


}
