package com.slimtrade.core.jna;

import com.slimtrade.core.poe.POEWindow;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.Platform;
import com.slimtrade.modules.updater.ZLogger;
import com.sun.jna.Native;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.Win32Exception;
import com.sun.jna.platform.win32.WinDef;

import java.awt.*;

/**
 * Handles interacting with native windows on the Windows platform.
 * See <a href="https://java-native-access.github.io/jna/4.2.1/com/sun/jna/platform/win32/User32.html">User32</a> &
 * <a href="https://java-native-access.github.io/jna/4.2.0/com/sun/jna/platform/WindowUtils.html">WindowUtils</a>.
 */
public class NativeWindow {

    public static NativeWindow gameWindow;

    public final String title;
    public final WinDef.HWND handle;
    public Rectangle bounds;

    public NativeWindow(String title, WinDef.HWND handle) {
        this.title = title;
        this.handle = handle;
        updateBounds();
        POEWindow.setWindow(title, bounds);
    }

    public void updateBounds() {
        if (handle == null) {
            ZLogger.err("Null window handle with title: " + title);
        }
        try {
            bounds = WindowUtils.getWindowLocationAndSize(handle);
        } catch (Win32Exception e) {
            ZLogger.err("Win32 Exception on handle '" + handle + "' with title " + title);
        }
        System.out.println("Bounds: " + bounds);
        POEWindow.setGameBounds(bounds);
    }

    public void focus() {
        focus(this);
    }

    public static void setGameWindow(NativeWindow window) {
        assert POEInterface.gameTitleSet.contains(window.title);
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
