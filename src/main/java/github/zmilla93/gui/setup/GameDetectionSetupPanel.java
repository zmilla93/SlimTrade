package github.zmilla93.gui.setup;

import com.sun.jna.platform.win32.WinDef;
import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.core.jna.NativePoeWindow;
import github.zmilla93.core.jna.NativeWindow;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.GameDetectionMethod;
import github.zmilla93.core.utility.Platform;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.CardPanel;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.MonitorInfo;
import github.zmilla93.gui.components.MonitorPicker;
import github.zmilla93.gui.components.poe.ResultLabel;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final JRadioButton automaticRadioButton = new JRadioButton("Automatic");
    private final JRadioButton monitorRadioButton = new JRadioButton("Select a monitor");
    private final JRadioButton screenRegionRadioButton = new JRadioButton("Create a screen region");

    // Automatic
    private final JLabel automaticTestNotRun = new JLabel("Click to set the game window location.");
    private final ResultLabel automaticTestFail = new ResultLabel(ResultStatus.DENY, "No window found. Ensure Path of Exile 1 or 2 is running.");
    private final ResultLabel automaticTestMinimized = new ResultLabel(ResultStatus.INDETERMINATE, "Game is minimized.");
    private final ResultLabel automaticTestSuccess = new ResultLabel(ResultStatus.APPROVE, "Game window location set.");
    private final ResultLabel automaticTestNotSupported = new ResultLabel(ResultStatus.DENY, "Not supported on " + Platform.current + ".");
    private final CardPanel automaticResultsCardPanel = new CardPanel(automaticTestNotRun, automaticTestSuccess, automaticTestFail, automaticTestMinimized, automaticTestNotSupported);
    private final JButton automaticTestButton = new JButton("Detect");
    private boolean automaticTestResult = false;

    // Monitor
    private final MonitorPicker monitorPicker = new MonitorPicker();

    // Screen Region
    // TODO: Screen Region

    // Card Panel
    private final CardPanel cardPanel = new CardPanel();
    private final AbstractOptionPanel automaticPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel monitorPanel = new AbstractOptionPanel(false, false);
    private final AbstractOptionPanel screenRegionPanel = new AbstractOptionPanel(false, false);

    public GameDetectionSetupPanel() {
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(automaticRadioButton);
        buttonGroup.add(monitorRadioButton);
        buttonGroup.add(screenRegionRadioButton);

        // Detection Method Panel
        addHeader("Window Location");
        addComponent(new JLabel("How should SlimTrade determine the window location of Path of Exile?"));
        addComponent(new ResultLabel("This aligns the UI for both games."));
        addVerticalStrutSmall();
        if (Platform.current == Platform.WINDOWS)
            addComponent(automaticRadioButton);
        addComponent(monitorRadioButton);
        addComponent(screenRegionRadioButton);
        addVerticalStrut();

        // Automatic Panel
        automaticPanel.addHeader("Detect Window");
        automaticPanel.addComponent(new ComponentPanel(automaticTestButton, automaticResultsCardPanel));

        // Monitor Panel
        monitorPanel.addHeader("Monitor Selection");
        monitorPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Requires using Windowed Fullscreen"));
        monitorPanel.addComponent(monitorPicker);

        // Screen Region Panel
        screenRegionPanel.addHeader("Screen Region");
        screenRegionPanel.addComponent(new ResultLabel(ResultStatus.INDETERMINATE, "Coming soon (maybe)."));
        screenRegionPanel.addLabel("This is only required for Mac & Linux users playing in windowed mode.");
        screenRegionPanel.addLabel("If that is you, let me know on GitHub or Discord.");

        // Card Panel
        cardPanel.add(automaticPanel);
        cardPanel.add(monitorPanel);
        cardPanel.add(screenRegionPanel);
        addFullWidthComponent(cardPanel);

        if (Platform.current == Platform.WINDOWS) automaticRadioButton.setSelected(true);
        else monitorRadioButton.setSelected(true);
    }

    private void addListeners() {
        automaticRadioButton.addActionListener(e -> {
            cardPanel.showCard(automaticPanel);
            runSetupValidation();
        });
        monitorRadioButton.addActionListener(e -> {
            cardPanel.showCard(monitorPanel);
            runSetupValidation();
        });
        screenRegionRadioButton.addActionListener(e -> {
            cardPanel.showCard(screenRegionPanel);
            runSetupValidation();
        });
        automaticTestButton.addActionListener(e -> {
            automaticTestResult = false;
            if (Platform.current != Platform.WINDOWS) {
                automaticResultsCardPanel.showCard(automaticTestNotSupported);
                return;
            }
            WinDef.HWND handle = NativePoeWindow.findPathOfExileWindow();
            if (handle == null) automaticResultsCardPanel.showCard(automaticTestFail);
            else {
                NativeWindow window = new NativeWindow(handle);
                if (window.minimized) {
                    automaticResultsCardPanel.showCard(automaticTestMinimized);
                } else {
                    automaticResultsCardPanel.showCard(automaticTestSuccess);
                    PoeIdentificationFrame.identify(window.clientBounds);
                    automaticTestResult = true;
                }
            }
            runSetupValidation();
            ZUtil.packComponentWindow(GameDetectionSetupPanel.this);
        });
    }

    private void initializeComponents(GameDetectionMethod method) {
        switch (method) {
            case AUTOMATIC:
                automaticRadioButton.setSelected(true);
                cardPanel.showCard(automaticPanel);
                break;
            case MONITOR:
                monitorRadioButton.setSelected(true);
                cardPanel.showCard(monitorPanel);
                break;
            case SCREEN_REGION:
                screenRegionRadioButton.setSelected(true);
                cardPanel.showCard(screenRegionPanel);
                break;
            case UNSET:
            default:
                if (Platform.current == Platform.WINDOWS) {
                    automaticRadioButton.setSelected(true);
                    cardPanel.showCard(automaticPanel);
                } else {
                    monitorRadioButton.setSelected(true);
                    cardPanel.showCard(monitorPanel);
                }
        }
    }

    @Override
    public void initializeComponents() {
        GameDetectionMethod method = SaveManager.settingsSaveFile.data.gameDetectionMethod;
        initializeComponents(method);
        MonitorInfo monitor = SaveManager.settingsSaveFile.data.selectedMonitor;
        if (monitor != null) monitorPicker.setMonitor(monitor);
    }

    @Override
    public void addComponentListeners() {
        addListeners();
    }

    @Override
    public boolean isSetupValid() {
        if (automaticRadioButton.isSelected() && automaticTestResult) return true;
        else if (monitorRadioButton.isSelected()) return true;
            // TODO: implement screen region
        else if (screenRegionRadioButton.isSelected()) return false;
        return false;
    }

    @Override
    public void applyCompletedSetup() {
        if (automaticRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.AUTOMATIC;
        } else if (monitorRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.MONITOR;
            SaveManager.settingsSaveFile.data.selectedMonitor = monitorPicker.getSelectedMonitor();
        } else if (screenRegionRadioButton.isSelected()) {
            SaveManager.settingsSaveFile.data.gameDetectionMethod = GameDetectionMethod.SCREEN_REGION;
            // FIXME : Save region
        }
    }

}
