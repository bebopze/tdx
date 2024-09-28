package com.bebopze.tdx.script.utils;

import com.google.common.collect.Lists;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.platform.win32.WinUser;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.awt.event.KeyEvent.*;


/**
 * Win系统   -   鼠标坐标
 */
@Slf4j
public class WinUtils2 {


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


    public static void openAndCloseWinApp() {


        // 打开App
        WinUtils.openApp("tdx path");


        // 获取窗口


        // 键盘输入   ->   [.902]
        ArrayList<Integer> list = Lists.newArrayList(VK_PERIOD, VK_9, VK_0, VK_2);
        WinUtils.keyPress(list);


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
     * [窗口-按钮] - 坐标
     */
    public static void winButtonCoordinate() throws Exception {

        // 获取  [窗口] - [按钮]
        HWND hButton = WinUtils.findWindowsButton(null, "扩展数据管理器", "全部刷新");


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


//    public static void openApp2(String appPath) throws Exception {
//
//
//        // 获取Mac应用程序的可执行文件路径
//        // String appPath = "/Applications/YourApp.app/Contents/MacOS/YourApp";
//
//        // 构建执行命令的进程
//        ProcessBuilder processBuilder = new ProcessBuilder(appPath);
//
//        // 启动进程
//        Process process = processBuilder.start();
//
//        // 获取进程的输出流
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        String line;
//        while ((line = reader.readLine()) != null) {
//            // 处理应用程序的输出信息
//            System.out.println(line);
//        }
//
//        // 等待进程执行结束
//        int exitCode = process.waitFor();
//        System.out.println("应用程序执行结束，退出码：" + exitCode);
//    }





    //    /**
//     * [窗口-按钮] - 坐标
//     */
//    public static void winButtonCoordinate() throws Exception {
//
//        // 获取  [窗口] - [按钮]
//        HWND hButton = getWindowsButton(null, "扩展数据管理器", "全部刷新");
//
//
//        if (hButton != null) {
//
//            WinUser.RECT rect = new WinUser.RECT();
//            User32.INSTANCE.GetWindowRect(hButton, rect);
//
//
//            int x = rect.left;
//            int y = rect.top;
//            int width = rect.right - rect.left;
//            int height = rect.bottom - rect.top;
//
//            System.out.println("Button Coordinates: (" + x + ", " + y + "), Width: " + width + ", Height: " + height);
//        } else {
//            System.out.println("Button not found");
//        }
//    }





    // -----------------------------------------------------------------------------------------------------------------


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

}
