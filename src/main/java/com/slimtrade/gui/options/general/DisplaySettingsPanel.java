package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.FontListCellRenderer;
import com.slimtrade.gui.components.RangeSpinner;
import com.slimtrade.modules.saving.ISavable;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class DisplaySettingsPanel extends JPanel implements ISavable {

    private final JLabel fontPreviewLabel = new JLabel("You are captured, stupid beast!");
    private final IconButton iconPreviewButton = new IconButton("/icons/default/tagx64.png");
    private final JSpinner fontSizeSpinner = new RangeSpinner(SpinnerRange.FONT_SIZE);
    private final JSpinner iconSizeSpinner = new RangeSpinner(SpinnerRange.ICON_SIZE);
    private final JComboBox<String> fontCombo = new JComboBox<>();
    private final JButton defaultsButton = new JButton("Restore Default Scale");
    private String currentFontName;

    public DisplaySettingsPanel() {
        GridBagConstraints gc = ZUtil.getGC();
        JLabel fontSizeLabel = new JLabel("Font Size");
        JLabel iconSizeLabel = new JLabel("Icon Size");
        iconPreviewButton.setFocusable(false);

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

        // Preview Panel
        JPanel previewPanel = new JPanel(new FlowLayout());
        previewPanel.add(iconPreviewButton);
        gc.gridx++;
        previewPanel.add(fontPreviewLabel);

        // Main Panel
        setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        add(fontCombo, gc);
        gc.gridy++;
        add(sizePanel, gc);
        gc.gridy++;
        add(previewPanel, gc);
        gc.gridy++;
        add(defaultsButton, gc);
        gc.gridy++;
        addListeners();
    }

    private void addListeners() {
        fontCombo.addActionListener(e -> {
            Font previousPreviewFont = fontPreviewLabel.getFont();
            Font previousComboFont = fontCombo.getFont();
            String fontName = (String) fontCombo.getSelectedItem();
            Font previewFont = new Font(fontName, previousPreviewFont.getStyle(), previousPreviewFont.getSize());
            Font comboFont = new Font(fontName, previousComboFont.getStyle(), previousComboFont.getSize());
            fontPreviewLabel.setFont(previewFont);
            fontCombo.setFont(comboFont);
        });
        fontSizeSpinner.addChangeListener(e -> updateTextPreview());
        iconSizeSpinner.addChangeListener(e -> updateIconPreview());
        defaultsButton.addActionListener(e -> {
            fontSizeSpinner.setValue(SpinnerRange.FONT_SIZE.START);
            iconSizeSpinner.setValue(SpinnerRange.ICON_SIZE.START);
        });
    }

    private void updateTextPreview() {
        int size = (int) fontSizeSpinner.getValue();
        Font font = fontPreviewLabel.getFont();
        font = new Font(font.getName(), font.getStyle(), size);
        fontPreviewLabel.setFont(font);
        revalidate();
        repaint();
    }

    private void updateIconPreview() {
        int size = (int) iconSizeSpinner.getValue();
        iconPreviewButton.setIconSize(size);
    }

    @Override
    public void save() {
        if (SaveManager.settingsSaveFile.data.fontSize != (int) fontSizeSpinner.getValue()) {
            SaveManager.settingsSaveFile.data.fontSizeChanged = true;
        }
        if (SaveManager.settingsSaveFile.data.iconSize != (int) iconSizeSpinner.getValue()) {
            SaveManager.settingsSaveFile.data.iconSizeChanged = true;
        }
        SaveManager.settingsSaveFile.data.fontSize = (int) fontSizeSpinner.getValue();
        SaveManager.settingsSaveFile.data.iconSize = (int) iconSizeSpinner.getValue();
        String preferredFont = (String) fontCombo.getSelectedItem();
        SaveManager.settingsSaveFile.data.preferredFontName = preferredFont;
        ThemeManager.setFont(preferredFont);
    }

    @Override
    public void load() {
        fontSizeSpinner.setValue(SaveManager.settingsSaveFile.data.fontSize);
        iconSizeSpinner.setValue(SaveManager.settingsSaveFile.data.iconSize);
        if (SaveManager.settingsSaveFile.data.preferredFontName != null)
            fontCombo.setSelectedItem(SaveManager.settingsSaveFile.data.preferredFontName);
        updateTextPreview();
        updateIconPreview();
    }

}
