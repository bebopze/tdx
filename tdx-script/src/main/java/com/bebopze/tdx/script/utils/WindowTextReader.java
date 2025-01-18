package com.bebopze.tdx.script.utils;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import com.sun.jna.win32.W32APIOptions;


public class WindowTextReader {


    // 定义 User32 接口
    public interface User32Ex extends User32 {
        User32Ex INSTANCE = Native.load("user32", User32Ex.class, W32APIOptions.DEFAULT_OPTIONS);

        // 获取窗口的文本内容
        int GetWindowTextA(HWND hWnd, byte[] lpString, int nMaxCount);

        // 获取窗口句柄
        HWND FindWindowA(String lpClassName, String lpWindowName);

        // 获取窗口中的控件句柄
        HWND FindWindowEx(HWND hwndParent, HWND hwndChildAfter, String lpszClass, String lpszWindow);

        // 获取当前活动窗口句柄
        HWND GetForegroundWindow();
    }


    public static void main(String[] args) {


        //


        // 获取当前活动窗口的句柄
        HWND hwnd = User32Ex.INSTANCE.GetForegroundWindow();

        // 读取窗口标题
        byte[] windowText = new byte[512];
        User32Ex.INSTANCE.GetWindowTextA(hwnd, windowText, 512);
        System.out.println("Window Title: " + Native.toString(windowText));

        // 通过 FindWindowEx 查找窗口中的控件，假设你知道按钮的类名和名称
        HWND buttonHwnd = User32Ex.INSTANCE.FindWindowEx(hwnd, null, null, "ButtonText");
        if (buttonHwnd != null) {
            byte[] buttonText = new byte[512];
            User32Ex.INSTANCE.GetWindowTextA(buttonHwnd, buttonText, 512);
            System.out.println("Button Text: " + Native.toString(buttonText));
        } else {
            System.out.println("Button not found");
        }
    }
}
