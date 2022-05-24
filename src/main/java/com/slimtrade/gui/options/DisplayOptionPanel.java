package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.CheckBoxPanel;
import com.slimtrade.gui.components.LimitCombo;
import com.slimtrade.gui.options.general.DisplaySettingsPanel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class DisplayOptionPanel extends AbstractOptionPanel implements ISavable {

    private JComboBox<ColorTheme> themeCombo = new LimitCombo<>();

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
        addPanel(new CheckBoxPanel("Color Blind Mode"));
        themeCombo.addActionListener(e -> SwingUtilities.invokeLater(() -> ColorManager.setTheme((ColorTheme) themeCombo.getSelectedItem())));
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.colorTheme = (ColorTheme) themeCombo.getSelectedItem();
    }

    @Override
    public void load() {
        themeCombo.setSelectedItem(SaveManager.settingsSaveFile.data.colorTheme);
    }
}
