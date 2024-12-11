package github.zmilla93.core.jna;

import com.sun.jna.Native;
import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.GameDetectionMethod;
import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.POEInterface;

/**
 * Handles interacting with native windows on the Windows platform.
 * See <a href="https://java-native-access.github.io/jna/4.2.1/com/sun/jna/platform/win32/User32.html">User32</a> &
 * <a href="https://java-native-access.github.io/jna/4.2.0/com/sun/jna/platform/WindowUtils.html">WindowUtils</a>.
 * Updates {@link POEWindow} to control the game bounds when using {@link GameDetectionMethod#AUTOMATIC}.
 */
public class NativePoeWindow extends NativeWindow {

    // Static windows
    private static WinDef.HWND poeWindowHandle;
    private static WinDef.HWND enumeratingHandle;
    private static WinDef.HWND returnHandle;
//    private static NativePoeWindow poeWindow;
//    private static NativePoeWindow enumerationWindow;
//    private static NativePoeWindow foundWindow;

    // FIXME : These get created a lot, make jna calls lazy, only create them for when the window changes, and reuse when same window is used
    public NativePoeWindow(WinDef.HWND handle) {
        super(handle);
        System.out.println("created native window obj: " + title);
        refreshInfo();
    }

    public static void setPOEGameWindow(WinDef.HWND handle) {
        poeWindowHandle = handle;
        System.out.println("POE Process Path: " + WindowUtils.getProcessFilePath(handle));
        if (SaveManager.settingsSaveFile.data.gameDetectionMethod == GameDetectionMethod.AUTOMATIC)
            POEWindow.setBoundsByWindowHandle(handle);
    }

    public static String getWindowTitle(WinDef.HWND handle) {
        return WindowUtils.getWindowTitle(handle);
    }

//    public static void focus(NativePoeWindow window) {
//        if (window == null) return;
//        setPOEGameWindow(window);
//        WinDef.HWND handle = window.handle;
//        User32.INSTANCE.ShowWindow(handle, User32.SW_SHOW);
//        User32.INSTANCE.SetForegroundWindow(handle);
//        User32.INSTANCE.SetFocus(handle);
//    }

    /**
     * Focuses the Path of Exile game window on the Windows platform.
     */
    public static void focusPathOfExileNativeWindow() {
        // Use cached window handle if available
        if (poeWindowHandle != null) {
            NativeWindow.toFront(poeWindowHandle);
            return;
        }
        WinDef.HWND handle = findPathOfExileWindow();
        if (handle != null) NativeWindow.toFront(handle);
    }

    public static synchronized WinDef.HWND findPathOfExileWindow() {
        System.out.println("Finding POE Window...");
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
                    System.out.println("gfn window");
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
