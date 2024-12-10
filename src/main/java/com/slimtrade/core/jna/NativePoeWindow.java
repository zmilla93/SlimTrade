package com.slimtrade.core.jna;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.GameDetectionMethod;
import com.slimtrade.core.poe.POEWindow;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.Platform;
import com.sun.jna.Native;
import com.sun.jna.NativeLong;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.awt.*;

/**
 * Handles interacting with native windows on the Windows platform.
 * See <a href="https://java-native-access.github.io/jna/4.2.1/com/sun/jna/platform/win32/User32.html">User32</a> &
 * <a href="https://java-native-access.github.io/jna/4.2.0/com/sun/jna/platform/WindowUtils.html">WindowUtils</a>.
 * Updates {@link POEWindow} to control the game bounds when using {@link com.slimtrade.core.poe.GameDetectionMethod#AUTOMATIC}.
 */
public class NativePoeWindow extends WindowInfo {

    // Static windows
    private static NativePoeWindow poeWindow;
    private static NativePoeWindow enumerationWindow;
    private static NativePoeWindow foundWindow;

    // FIXME : These get created a lot, make jna calls lazy, only create them for when the window changes, and reuse when same window is used
    public NativePoeWindow(String title, WinDef.HWND handle) {
        super(title, handle);
        System.out.println("created native window obj: " + title);
        refreshInfo();
    }

    public void focus() {
        focus(this);
    }

    public static void setPOEGameWindow(NativePoeWindow window) {
        assert POEInterface.gameTitleSet.contains(window.title);
        poeWindow = window;
        System.out.println("POE Process Path: " + WindowUtils.getProcessFilePath(window.handle));
        if (SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.AUTOMATIC)
            POEWindow.setBoundsByNativeWindow(window);
    }

    public static String getWindowTitle(WinDef.HWND handle) {
        return WindowUtils.getWindowTitle(handle);
    }

    public static NativePoeWindow getFocusedWindow() {
        if (Platform.current == Platform.WINDOWS) {
            WinDef.HWND handle = User32.INSTANCE.GetForegroundWindow();
            if (handle == null) return null;
            String title = getWindowTitle(handle);
            return new NativePoeWindow(title, handle);
        }
        return null;
    }

    public static void focus(NativePoeWindow window) {
        if (window == null) return;
        setPOEGameWindow(window);
        WinDef.HWND handle = window.handle;
        User32.INSTANCE.SetForegroundWindow(handle);
        User32.INSTANCE.SetFocus(handle);
        User32.INSTANCE.ShowWindow(handle, User32.SW_SHOW);
    }

    /**
     * Focuses the Path of Exile game window on the Windows platform.
     */
    public static void focusPathOfExileNativeWindow() {
        // Use cached window handle if available
        if (poeWindow != null) {
            focus(poeWindow);
            return;
        }
        findPathOfExileWindow(window -> focus(window));
    }

    public static synchronized NativePoeWindow findPathOfExileWindow() {
        foundWindow = null;
        findPathOfExileWindow(window -> foundWindow = window);
        return foundWindow;
    }

    /**
     * Enumerates through all open windows, looking for the Path of Exile 1 or 2 window.
     * Uses the same callback pattern used by jna.
     */
    public static synchronized void findPathOfExileWindow(WindowCallback callback) {
        enumerationWindow = null;
        User32.INSTANCE.EnumWindows((enumeratingHandle, arg1) -> {
            // The class name string is truncated if it is longer than the buffer.
            int BUFFER_SIZE = 64;
            char[] classNameBuffer = new char[BUFFER_SIZE];
            User32.INSTANCE.GetClassName(enumeratingHandle, classNameBuffer, BUFFER_SIZE);
            String className = Native.toString(classNameBuffer);
            // NOTE : Can print class name here for debugging/finding new window handles for cloud gaming
//            System.out.println(className);
            if (className.isEmpty()) return true;
            // Path of Exile 1 & 2 windows have the class name POEWindowClass
            if (className.equals("POEWindowClass")) {
                String title = getWindowTitle(enumeratingHandle);
                NativePoeWindow window = new NativePoeWindow(title, enumeratingHandle);
                callback.onWindowFound(window);
                enumerationWindow = window;
                return false;
            }
            // GeForce Now has the class name CEFCLIENT. Unsure if this is unique, so the window title is also checked.
            if (className.equals("CEFCLIENT")) {
                String title = getWindowTitle(enumeratingHandle);
                if (POEInterface.gameTitleSet.contains(title)) {
                    NativePoeWindow window = new NativePoeWindow(title, enumeratingHandle);
                    callback.onWindowFound(window);
                    System.out.println("gfn window");
                    enumerationWindow = window;
                    callback.onWindowFound(window);
                    return false;
                }
            }
            return true;
        }, null);
        if (enumerationWindow == null) callback.onWindowFound(null);
    }

//    private static void setEnumerationSuccess(boolean success) {
//        enumerationSuccessFlag = success;
//    }

}
