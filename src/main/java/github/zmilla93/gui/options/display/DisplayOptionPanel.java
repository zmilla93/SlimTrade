package github.zmilla93.gui.options.display;

import github.zmilla93.App;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.LimitCombo;
import github.zmilla93.gui.options.AbstractOptionPanel;
import github.zmilla93.gui.options.general.DisplaySettingsPanel;
import github.zmilla93.modules.saving.ISavable;
import github.zmilla93.modules.theme.Theme;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class DisplayOptionPanel extends AbstractOptionPanel implements ISavable {

    private final JComboBox<Theme> themeCombo = new LimitCombo<>();
    private final JCheckBox colorBlindCheckBox = new JCheckBox("Color Blind Mode");

    public DisplayOptionPanel() {
        for (Theme theme : Theme.values()) themeCombo.addItem(theme);

        DisplayPreviewPanel previewPanel = new DisplayPreviewPanel();
        DisplaySettingsPanel displaySettingsPanel = new DisplaySettingsPanel(previewPanel);

        JPanel themePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        themePanel.add(new JLabel("Color Theme"), gc);
        gc.gridx++;
        themePanel.add(themeCombo, gc);
        gc.gridx++;

        // Build Panel
        addHeader("Font Settings");
        add(displaySettingsPanel);
        addVerticalStrut();

        addHeader("Theme");
        add(themePanel);
        add(colorBlindCheckBox);
        addVerticalStrut();

        addHeader("Display Preview");
        add(previewPanel);
        if (App.debug) addComponent(new DisplayDebugPanel());

        addListeners();
    }

    private void addListeners() {
        themeCombo.addActionListener(e -> SwingUtilities.invokeLater(() -> ThemeManager.setTheme((Theme) themeCombo.getSelectedItem())));
        colorBlindCheckBox.addItemListener(e -> ThemeManager.setColorblindMode(colorBlindCheckBox.isSelected()));
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
    }

}
