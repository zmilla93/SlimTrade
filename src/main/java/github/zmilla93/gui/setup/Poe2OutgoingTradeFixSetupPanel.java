package github.zmilla93.gui.setup;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.gui.components.poe.Poe2OutgoingTradeHotkeyPanel;

import javax.swing.*;

public class Poe2OutgoingTradeFixSetupPanel extends AbstractSetupPanel {

    private Poe2OutgoingTradeHotkeyPanel fixerPanel = new Poe2OutgoingTradeHotkeyPanel();
    private JCheckBox notInstalledCheckbox = new JCheckBox("Not Installed");

    public Poe2OutgoingTradeFixSetupPanel() {
        addFullWidthComponent(fixerPanel);
        addComponent(notInstalledCheckbox);
    }

    @Override
    protected void initializeComponents() {
        fixerPanel.hotkeyButton.setData(SaveManager.settingsSaveFile.data.poe2OutgoingTradeHotkey);
    }

    @Override
    protected void addComponentListeners() {
        fixerPanel.hotkeyButton.addHotkeyChangeListener(data -> runSetupValidation());
        notInstalledCheckbox.addActionListener(e -> runSetupValidation());
    }

    @Override
    public boolean isSetupValid() {
        boolean validHotkey = fixerPanel.hotkeyButton.getData() != null;
        boolean validCheckbox = notInstalledCheckbox.isSelected();
        return validHotkey || validCheckbox;
    }

    @Override
    protected void applyCompletedSetup() {
        SaveManager.settingsSaveFile.data.poe2OutgoingTradeHotkey = fixerPanel.hotkeyButton.getData();
    }

}
