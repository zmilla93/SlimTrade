package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.components.PlaceholderTextField;
import com.slimtrade.gui.options.general.DisplaySettingsPanel;
import com.slimtrade.modules.saving.ISavable;
import com.slimtrade.modules.theme.Theme;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class DisplayOptionPanel extends AbstractOptionPanel implements ISavable {

    private final JComboBox<Theme> themeCombo = new LimitCombo<>();
    private final JCheckBox colorBlindCheckBox = new JCheckBox("Color Blind Mode");

    public DisplayOptionPanel() {
        for (Theme theme : Theme.values()) themeCombo.addItem(theme);
        addHeader("UI Scale");
        addComponent(new DisplaySettingsPanel());
        addVerticalStrut();

        JPanel themePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        themePanel.add(new JLabel("Color Theme"), gc);
        gc.gridx++;
        themePanel.add(themeCombo, gc);
        gc.gridx++;

        addHeader("Theme");
        addComponent(themePanel);
        addComponent(colorBlindCheckBox);
        if (App.debug) {
            JTextField textField = new PlaceholderTextField("Testing...", 20);
            addComponent(textField);
        }
        addListeners();
    }

    private void addListeners() {
        themeCombo.addActionListener(e -> SwingUtilities.invokeLater(() -> ThemeManager.setTheme((Theme) themeCombo.getSelectedItem())));
        colorBlindCheckBox.addActionListener(e -> SaveManager.settingsSaveFile.data.triggerColorBlindModeChange(colorBlindCheckBox.isSelected()));
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.theme = (Theme) themeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.colorBlindMode = colorBlindCheckBox.isSelected();
    }

    @Override
    public void load() {
        Theme theme = SaveManager.settingsSaveFile.data.theme;
        if (theme == null) theme = Theme.getDefaultColorTheme();
        themeCombo.setSelectedItem(theme);
        colorBlindCheckBox.setSelected(SaveManager.settingsSaveFile.data.colorBlindMode);
        SaveManager.settingsSaveFile.data.triggerColorBlindModeChange(SaveManager.settingsSaveFile.data.colorBlindMode);
    }

}
