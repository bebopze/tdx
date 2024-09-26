package com.bebopze.tdx.script;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TdxScript {


    public static void main(String[] args) throws Exception {


        // openAndCloseMacApp();

        // openAndCloseWinApp();


        sshWin();


    }


    public static void openApp(String appPath) throws Exception {


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

    public static void openAndCloseWinApp() throws Exception {


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
