package github.zmilla93.core.jna;

import com.sun.jna.platform.WindowUtils;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinUser;

import java.awt.*;

public class NativeWindow {

    private static final String EMPTY_WINDOW_TITLE = "";

    // Constant Info
    public final String title;
    public final WinDef.HWND handle;

    // Dynamic Info
    public Rectangle bounds;
    public Rectangle clientBounds;
    public WindowMode mode;
    public boolean borderless;
    public boolean minimized;

    public NativeWindow(WinDef.HWND handle) {
        this.handle = handle;
        this.title = WindowUtils.getWindowTitle(handle);
        refreshInfo();
    }

    @Deprecated
    public NativeWindow(String title, WinDef.HWND handle) {
        this.title = title;
        this.handle = handle;
        refreshInfo();
    }

    public void refreshInfo() {
        bounds = WindowUtils.getWindowLocationAndSize(handle);
        minimized = CustomUser32.INSTANCE.IsIconic(handle);
        int style = User32.INSTANCE.GetWindowLong(handle, WinUser.GWL_STYLE);
        this.borderless = (style & WinUser.WS_POPUP) != 0;
        WinDef.RECT clientRect = new WinDef.RECT();
        boolean foundClientRect = false;
        if (User32.INSTANCE.GetClientRect(handle, clientRect)) {
            WinDef.POINT topLeft = new WinDef.POINT(clientRect.left, clientRect.top);
            if (CustomUser32.INSTANCE.ClientToScreen(handle, topLeft)) {
                int clientX = topLeft.x;
                int clientY = topLeft.y;
                int clientWidth = clientRect.right - clientRect.left;
                int clientHeight = clientRect.bottom - clientRect.top;
                clientBounds = new Rectangle(clientX, clientY, clientWidth, clientHeight);
                foundClientRect = true;
            }
        }
        if (!foundClientRect) clientBounds = bounds;
    }

    public static String getFocusedWindowTitle() {
        WinDef.HWND handle = User32.INSTANCE.GetForegroundWindow();
        if (handle == null) return null;
        return WindowUtils.getWindowTitle(handle);
    }

    public static WinDef.HWND getFocusedWindow() {
        return User32.INSTANCE.GetForegroundWindow();
    }

    // FIXME : Forcing POE to front probably isn't necessary at all?
    //  Could just have actions fail instead, might fix this disappearing frames issue.
    public static void toFront(WinDef.HWND handle) {
        User32.INSTANCE.ShowWindow(handle, User32.SW_SHOW);
        User32.INSTANCE.SetForegroundWindow(handle);
        User32.INSTANCE.SetFocus(handle);
    }

}