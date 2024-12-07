package com.slimtrade.core.jna;

import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.Platform;
import com.sun.jna.Native;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;

/**
 * Stores a JNA native window handle & title for the Windows platform.
 */
public class NativeWindow {

    private static final int MAX_TITLE_LENGTH = 1024;
    private static NativeWindow gameWindow;

    public final String title;
    public final WinDef.HWND handle;

    public NativeWindow(String title, WinDef.HWND handle) {
        this.title = title;
        this.handle = handle;
    }

    public void focus() {
        focus(this);
    }

    public static void setGameWindow(NativeWindow window) {
        gameWindow = window;
    }

    public static String getWindowTitle(WinDef.HWND handle) {
        return WindowUtils.getWindowTitle(handle);
    }

    public static NativeWindow getFocusedWindow() {
        if (Platform.current == Platform.WINDOWS) {
            WinDef.HWND handle = User32.INSTANCE.GetForegroundWindow();
            String title = getWindowTitle(handle);
            return new NativeWindow(title, handle);
        }
        return null;
    }

    public static void focus(NativeWindow window) {
        WinDef.HWND handle = window.handle;
        setGameWindow(window);
        User32.INSTANCE.SetForegroundWindow(handle);
        User32.INSTANCE.SetFocus(handle);
        User32.INSTANCE.ShowWindow(handle, User32.SW_SHOW);
    }

    /**
     * Focuses the Path of Exile game window on the Windows platform.
     */
    public static void focusPathOfExileNativeWindow() {
        // Use cached window handle if available
        if (gameWindow != null) {
            focus(gameWindow);
            return;
        }
        // Enumerate through all windows. Loop continues until the callback returns false
        User32.INSTANCE.EnumWindows((handle, arg1) -> {
            char[] classNameBuffer = new char[512];
            User32.INSTANCE.GetClassName(handle, classNameBuffer, 512);
            String className = Native.toString(classNameBuffer);
            System.out.println(className);
            if (className.isEmpty()) return true;
            // Path of Exile 1 & 2 windows have the class name POEWindowClass
            if (className.equals("POEWindowClass")) {
                String title = getWindowTitle(handle);
                focus(new NativeWindow(title, handle));
                return false;
            }
            // GeForce Now has the class name CEFCLIENT. Unsure if this is unique, so the window title is also checked.
            if (className.equals("CEFCLIENT")) {
                String title = getWindowTitle(handle);
                if (POEInterface.gameTitleSet.contains(title)) {
                    focus(new NativeWindow(title, handle));
                    return false;
                }
            }
            return true;
        }, null);
    }

}
