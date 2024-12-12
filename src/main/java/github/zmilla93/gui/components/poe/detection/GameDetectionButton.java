package github.zmilla93.gui.components.poe.detection;

import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.core.jna.NativePoeWindow;
import github.zmilla93.core.jna.NativeWindow;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.poe.ResultLabel;
import github.zmilla93.gui.setup.PoeIdentificationFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Handles automatically detecting the Path of Exile game window.
 */
public class GameDetectionButton extends ComponentPanel implements GameDetectionTestListener {

    // FIXME: Should switch to a card panel to avoid component resizing (remove the pack listener from setup if so)
    private final JButton runTestButton = new JButton("Detect");
    private final ResultLabel resultLabel = new ResultLabel(GameDetectionResult.NOT_RUN.message);
    private final ArrayList<GameDetectionTestListener> listeners = new ArrayList<>();
    private final Timer timer;
    private GameDetectionResult latestResult;
    private NativeWindow latestResultWindow;

    public GameDetectionButton() {
        add(runTestButton);
        add(resultLabel);
        runTestButton.addActionListener(e -> runTest());
        addGameDetectionTestListener(this);
        // Clears success message after 10 seconds to make it clear that this component can be reused.
        timer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timer.stop();
                resultLabel.setText(ResultStatus.NEUTRAL, GameDetectionResult.NOT_RUN.message);
            }
        });
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

    public boolean getLatestResultWasSuccess() {
        return getLatestResult() == GameDetectionResult.SUCCESS;
    }

    public GameDetectionResult getLatestResult() {
        return latestResult;
    }

    public NativeWindow getLatestResultWindow() {
        return latestResultWindow;
    }

    @Override
    public void onTestResult(GameDetectionResult result, WinDef.HWND handle) {
        latestResult = result;
        resultLabel.setText(result.status, result.message);
        if (result == GameDetectionResult.SUCCESS) {
            NativeWindow window = new NativeWindow(handle);
            latestResultWindow = window;
            PoeIdentificationFrame.identify(window.clientBounds);
            timer.start();
        }
    }

}
