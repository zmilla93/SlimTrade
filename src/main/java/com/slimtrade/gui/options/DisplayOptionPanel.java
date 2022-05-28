package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.options.general.DisplaySettingsPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class DisplayOptionPanel extends AbstractOptionPanel implements ISavable {

    private JComboBox<ColorTheme> themeCombo = new LimitCombo<>();
    private JCheckBox colorBlindCheckBox = new JCheckBox("Color Blind Mode");

    public DisplayOptionPanel() {
        for (ColorTheme theme : ColorTheme.values()) themeCombo.addItem(theme);
        addHeader("UI Scale");
        addPanel(new DisplaySettingsPanel());
        addVerticalStrut();

        JPanel themePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        themePanel.add(new JLabel("Color Theme"), gc);
        gc.gridx++;
        themePanel.add(themeCombo, gc);
        gc.gridx++;

        addHeader("Theme");
        addPanel(themePanel);
        addPanel(colorBlindCheckBox);
        themeCombo.addActionListener(e -> SwingUtilities.invokeLater(() -> ColorManager.setTheme((ColorTheme) themeCombo.getSelectedItem())));
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.colorTheme = (ColorTheme) themeCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.colorBlindMode = colorBlindCheckBox.isSelected();
    }

    @Override
    public void load() {
        ColorTheme theme = SaveManager.settingsSaveFile.data.colorTheme;
        if (theme == null) theme = ColorTheme.getDefaultColorTheme();
        themeCombo.setSelectedItem(theme);
        colorBlindCheckBox.setSelected(SaveManager.settingsSaveFile.data.colorBlindMode);
    }
}
