package github.zmilla93.gui.components.poe.detection;

import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.jna.NativePoeWindow;
import github.zmilla93.core.jna.NativeWindow;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.poe.ResultLabel;
import github.zmilla93.gui.setup.PoeIdentificationFrame;

import javax.swing.*;
import java.util.ArrayList;

/**
 * Handles automatically detecting the Path of Exile game window.
 */
public class GameDetectionPanel extends ComponentPanel implements GameDetectionTestListener {

    private final JButton runTestButton = new JButton("Detect");
    private final ResultLabel resultLabel = new ResultLabel(GameDetectionResult.NOT_RUN.message);
    private final ArrayList<GameDetectionTestListener> listeners = new ArrayList<>();

    public GameDetectionPanel() {
        add(runTestButton);
        add(resultLabel);
        runTestButton.addActionListener(e -> runTest());
        addGameDetectionTestListener(this);
    }

    private void runTest() {
        GameDetectionResult result;
        WinDef.HWND handle = null;
        if (Platform.current == Platform.WINDOWS) {
            handle = NativePoeWindow.findPathOfExileWindow();
            if (handle == null) result = GameDetectionResult.FAIL;
            else {
                NativeWindow window = new NativeWindow(handle);
                if (window.minimized) result = GameDetectionResult.MINIMIZED;
                else result = GameDetectionResult.SUCCESS;
            }
        } else {
            result = GameDetectionResult.NOT_SUPPORTED;
        }
        for (GameDetectionTestListener listener : listeners) listener.onTestResult(result, handle);
    }

    public void addGameDetectionTestListener(GameDetectionTestListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onTestResult(GameDetectionResult result, WinDef.HWND handle) {
        resultLabel.setText(result.status, result.message);
        if (result == GameDetectionResult.SUCCESS) {
            NativeWindow window = new NativeWindow(handle);
            PoeIdentificationFrame.identify(window.clientBounds);
        }
    }

}
