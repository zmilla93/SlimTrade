package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.RangeSpinner;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class DisplaySettingsPanel extends JPanel implements ISavable {

    private final JLabel fontPreviewLabel = new JLabel("You are captured, stupid beast!");
    private final IconButton iconPreviewButton = new IconButton("/icons/default/tagx64.png");
    private final JSpinner fontSizeSpinner = new RangeSpinner(SpinnerRange.FONT_SIZE);
    private final JSpinner iconSizeSpinner = new RangeSpinner(SpinnerRange.ICON_SIZE);
    private final JButton defaultsButton = new JButton("Restore Default Scale");

    public DisplaySettingsPanel() {
        GridBagConstraints gc = ZUtil.getGC();
        JLabel textSizeLabel = new JLabel("Text Size");
        JLabel iconSizeLabel = new JLabel("Icon Size");
        iconPreviewButton.setFocusable(false);

        // Input Panel
        JPanel inputPanel = new JPanel(new GridBagLayout());
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        inputPanel.add(textSizeLabel, gc);
        gc.gridx++;
        inputPanel.add(fontSizeSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;
        inputPanel.add(iconSizeLabel, gc);
        gc.gridx++;
        inputPanel.add(iconSizeSpinner, gc);

        // Preview Panel
        JPanel previewPanel = new JPanel(new FlowLayout());
        previewPanel.add(iconPreviewButton);
        gc.gridx++;
        previewPanel.add(fontPreviewLabel);

        // Main Panel
        setLayout(new GridBagLayout());
        gc = ZUtil.getGC();
        gc.anchor = GridBagConstraints.WEST;
        add(inputPanel, gc);
        gc.gridy++;
        add(previewPanel, gc);
        gc.gridy++;
        add(defaultsButton, gc);
        addListeners();
    }

    private void addListeners() {
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
    }

    @Override
    public void load() {
        fontSizeSpinner.setValue(SaveManager.settingsSaveFile.data.fontSize);
        iconSizeSpinner.setValue(SaveManager.settingsSaveFile.data.iconSize);
        updateTextPreview();
        updateIconPreview();
    }

}
