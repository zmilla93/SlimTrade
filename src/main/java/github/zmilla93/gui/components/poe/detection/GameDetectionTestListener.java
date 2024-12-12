package github.zmilla93.gui.components.poe.detection;

import com.sun.jna.platform.win32.WinDef;

/**
 * Listens for tests run by a {@link GameDetectionPanel}.
 */
public interface GameDetectionTestListener {

    void onTestResult(GameDetectionResult result, WinDef.HWND handle);

}
