package github.zmilla93.core.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.poe.GameWindowMode;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.POEInterface;

/**
 * Handles interacting with native windows on the Windows platform.
 * See <a href="https://java-native-access.github.io/jna/4.2.1/com/sun/jna/platform/win32/User32.html">User32</a> &
 * <a href="https://java-native-access.github.io/jna/4.2.0/com/sun/jna/platform/WindowUtils.html">WindowUtils</a>.
 * Updates {@link POEWindow} to control the game bounds when using {@link GameWindowMode#DETECT}.
 */
public class NativePoeWindow extends NativeWindow {

    private static WinDef.HWND enumeratingHandle;
    private static WinDef.HWND returnHandle;

    // FIXME : These get created a lot, make jna calls lazy, only create them for when the window changes, and reuse when same window is used
    public NativePoeWindow(WinDef.HWND handle) {
        super(handle);
        System.out.println("created native window obj: " + title);
        refreshInfo();
    }

    // FIXME : Both this and POEWindow store handle, feels redundant and potentially buggy
    @Deprecated
    public static void setPOEGameWindow(WinDef.HWND handle) {
        // FIXME : This gets called too frequently.
//        System.out.println("Handle:" + handle);
//        System.out.println("POE Process Path: " + WindowUtils.getProcessFilePath(handle));
//        if (SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.DETECT)
//            POEWindow.setBoundsByWindowHandle(handle);
    }

    public static String getWindowTitle(WinDef.HWND handle) {
        return WindowUtils.getWindowTitle(handle);
    }

    /**
     * Focuses the Path of Exile game window on the Windows platform.
     */
    public static void focusPathOfExileNativeWindow() {
        if (POEWindow.getGameHandle() != null) {
            NativeWindow.toFront(POEWindow.getGameHandle());
            return;
        }
        WinDef.HWND handle = findPathOfExileWindow();
        if (handle != null) NativeWindow.toFront(handle);
    }

    public static synchronized WinDef.HWND findPathOfExileWindow() {
        returnHandle = null;
        findPathOfExileWindow(window -> returnHandle = window);
        return returnHandle;
    }

    /**
     * Enumerates through all open windows, looking for the Path of Exile 1 or 2 window.
     * Uses the same callback pattern used by jna.
     */
    public static synchronized void findPathOfExileWindow(WindowCallback callback) {
        enumeratingHandle = null;
        User32.INSTANCE.EnumWindows((handle, arg1) -> {
            // The class name string is truncated if it is longer than the buffer.
            int BUFFER_SIZE = 64;
            char[] classNameBuffer = new char[BUFFER_SIZE];
            User32.INSTANCE.GetClassName(handle, classNameBuffer, BUFFER_SIZE);
            String className = Native.toString(classNameBuffer);
            // NOTE : Can print class name here for debugging/finding new window handles for cloud gaming
//            System.out.println(className);
            if (className.isEmpty()) return true;
            // Path of Exile 1 & 2 windows have the class name POEWindowClass
            if (className.equals("POEWindowClass")) {
                enumeratingHandle = handle;
                callback.onWindowFound(handle);
                return false;
            }
            // GeForce Now has the class name CEFCLIENT. Unsure if this is unique, so the window title is also checked.
            if (className.equals("CEFCLIENT")) {
                String title = getWindowTitle(handle);
                if (POEInterface.gameTitleSet.contains(title)) {
                    enumeratingHandle = handle;
                    callback.onWindowFound(handle);
                    return false;
                }
            }
            return true;
        }, null);
        if (enumeratingHandle == null) callback.onWindowFound(null);
    }


}
