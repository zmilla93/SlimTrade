package github.zmilla93.gui.options.general;

import github.zmilla93.core.enums.SpinnerRange;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.FontListCellRenderer;
import github.zmilla93.gui.components.RangeSpinner;
import github.zmilla93.gui.options.display.DisplayPreviewPanel;
import github.zmilla93.modules.saving.ISavable;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class DisplaySettingsPanel extends JPanel implements ISavable {

    private final JSpinner fontSizeSpinner = new RangeSpinner(SpinnerRange.FONT_SIZE);
    private final JSpinner iconSizeSpinner = new RangeSpinner(SpinnerRange.ICON_SIZE);
    private final JComboBox<String> fontCombo = new JComboBox<>();
    private final JButton defaultsButton = new JButton("Restore Defaults");
    private final DisplayPreviewPanel previewPanel;

    public DisplaySettingsPanel(DisplayPreviewPanel previewPanel) {
        this.previewPanel = previewPanel;
        GridBagConstraints gc = ZUtil.getGC();
        JLabel fontSizeLabel = new JLabel("Font Size");
        JLabel iconSizeLabel = new JLabel("Icon Size");

        // Font Panel
        fontCombo.setRenderer(new FontListCellRenderer());
        for (String fontName : FontManager.getAllFonts())
            fontCombo.addItem(fontName);

        // Input Panel
        JPanel sizePanel = new JPanel(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        sizePanel.add(fontSizeLabel, gc);
        gc.gridx++;
        sizePanel.add(fontSizeSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;
        sizePanel.add(iconSizeLabel, gc);
        gc.gridx++;
        sizePanel.add(iconSizeSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Main Panel
        setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        add(fontCombo, gc);
        gc.gridy++;
        add(sizePanel, gc);
        gc.gridy++;
        add(defaultsButton, gc);
        gc.gridy++;
        addListeners();
    }

    private void addListeners() {
        fontCombo.addActionListener(e -> {
            Font previousComboFont = fontCombo.getFont();
            String fontName = (String) fontCombo.getSelectedItem();
            Font comboFont = new Font(fontName, previousComboFont.getStyle(), previousComboFont.getSize());
            previewPanel.setPreviewFontStyle(comboFont);
            fontCombo.setFont(comboFont);
        });
        fontSizeSpinner.addChangeListener(e -> updateTextPreview());
        iconSizeSpinner.addChangeListener(e -> updateIconPreview());
        defaultsButton.addActionListener(e -> {
            fontCombo.setSelectedItem(FontManager.DEFAULT_FONT);
            fontSizeSpinner.setValue(SpinnerRange.FONT_SIZE.START);
            iconSizeSpinner.setValue(SpinnerRange.ICON_SIZE.START);
        });
    }

    private void updateTextPreview() {
        int size = (int) fontSizeSpinner.getValue();
        previewPanel.setPreviewFontSize(size);
    }

    private void updateIconPreview() {
        int size = (int) iconSizeSpinner.getValue();
        previewPanel.setPreviewIconSize(size);
    }

    @Override
    public void save() {
        String preferredFont = (String) fontCombo.getSelectedItem();
        int fontSize = (int) fontSizeSpinner.getValue();
        int iconSize = (int) iconSizeSpinner.getValue();

        SaveManager.settingsSaveFile.data.preferredFontName = preferredFont;
        SaveManager.settingsSaveFile.data.fontSize = fontSize;
        SaveManager.settingsSaveFile.data.iconSize = iconSize;

        ThemeManager.setFont(preferredFont);
        ThemeManager.setFontSize(fontSize);
        ThemeManager.setIconSize(iconSize);
        FontManager.INSTANCE.setFontSize(fontSize);
    }

    @Override
    public void load() {
        fontSizeSpinner.setValue(SaveManager.settingsSaveFile.data.fontSize);
        iconSizeSpinner.setValue(SaveManager.settingsSaveFile.data.iconSize);
        if (SaveManager.settingsSaveFile.data.preferredFontName == null) {
            System.out.println(FontManager.getPreferredFont().getFontName());
            fontCombo.setSelectedItem(FontManager.getPreferredFont().getFontName());
        } else {
            fontCombo.setSelectedItem(SaveManager.settingsSaveFile.data.preferredFontName);

        }


        updateTextPreview();
        updateIconPreview();
    }

}
