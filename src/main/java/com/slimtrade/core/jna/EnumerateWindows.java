package com.slimtrade.core.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinDef.HWND;

// see http://java-native-access.github.io/jna/4.0/javadoc/

public class EnumerateWindows {
    private static final int MAX_TITLE_LENGTH = 1024;

    public static void printWindow() {
        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
        HWND hwnd = User32.INSTANCE.GetForegroundWindow();
        User32.INSTANCE.GetWindowText(hwnd, buffer, MAX_TITLE_LENGTH);
        System.out.println("Active window title: " + Native.toString(buffer));
        WinDef.RECT rect = new WinDef.RECT();
        User32.INSTANCE.GetWindowRect(hwnd, rect);
        System.out.println("rect = " + rect);
//        char[] buffer = new char[MAX_TITLE_LENGTH * 2];
//        GetWindowTextW(GetForegroundWindow(), buffer, MAX_TITLE_LENGTH);
//        System.out.println("Active window title: " + Native.toString(buffer));
//
//        PointerByReference pointer = new PointerByReference();
//        GetWindowThreadProcessId(GetForegroundWindow(), pointer);
//        Pointer process = OpenProcess(PROCESS_QUERY_INFORMATION | PROCESS_VM_READ, false, pointer.getValue());
//        GetModuleBaseNameW(process, null, buffer, MAX_TITLE_LENGTH);
//        System.out.println("Active window process: " + Native.toString(buffer));
    }

//    static class Psapi {
//        static { Native.register("psapi"); }
//        public static native int GetModuleBaseNameW(Pointer hProcess, Pointer hmodule, char[] lpBaseName, int size);
//    }
//
//    static class Kernel32 {
//        static { Native.register("kernel32"); }
//        public static int PROCESS_QUERY_INFORMATION = 0x0400;
//        public static int PROCESS_VM_READ = 0x0010;
//        public static native int GetLastError();
//        public static native Pointer OpenProcess(int dwDesiredAccess, boolean bInheritHandle, Pointer pointer);
//    }
//
//    static class User32DLL {
//        static { Native.register("user32"); }
//        public static native int GetWindowThreadProcessId(HWND hWnd, PointerByReference pref);
//        public static native HWND GetForegroundWindow();
//        public static native int GetWindowTextW(HWND hWnd, char[] lpString, int nMaxCount);
//    }
}