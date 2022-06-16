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

    private final JLabel textPreviewLabel = new JLabel("You are captured, stupid beast!");
    private final IconButton iconPreviewButton = new IconButton("/icons/default/tagx64.png");
    private final JSpinner textSizeSpinner = new RangeSpinner(SpinnerRange.FONT_SIZE);
    private final JSpinner iconSizeSpinner = new RangeSpinner(SpinnerRange.ICON_SIZE);

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
        inputPanel.add(textSizeSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;
        inputPanel.add(iconSizeLabel, gc);
        gc.gridx++;
        inputPanel.add(iconSizeSpinner, gc);
        gc.gridx++;

        // Preview Panel
        JPanel previewPanel = new JPanel(new FlowLayout());
        previewPanel.add(iconPreviewButton);
        gc.gridx++;
        previewPanel.add(textPreviewLabel);

        // Main Panel
        setLayout(new BorderLayout());
        add(inputPanel, BorderLayout.WEST);
        add(previewPanel, BorderLayout.SOUTH);
        addListeners();
    }

    private void addListeners() {
        textSizeSpinner.addChangeListener(e -> updateTextPreview());
        iconSizeSpinner.addChangeListener(e -> updateIconPreview());
    }

    private void updateTextPreview() {
        int size = (int) textSizeSpinner.getValue();
        Font font = textPreviewLabel.getFont();
        font = new Font(font.getName(), font.getStyle(), size);
        textPreviewLabel.setFont(font);
        revalidate();
        repaint();
    }

    private void updateIconPreview() {
        int size = (int) iconSizeSpinner.getValue();
        iconPreviewButton.setIconSize(size);
    }

    @Override
    public void save() {
        if (SaveManager.settingsSaveFile.data.fontSize != (int) textSizeSpinner.getValue()) {
            SaveManager.settingsSaveFile.data.fontSizeChanged = true;
        }
        if (SaveManager.settingsSaveFile.data.iconSize != (int) iconSizeSpinner.getValue()) {
            SaveManager.settingsSaveFile.data.iconSizeChanged = true;
        }
        SaveManager.settingsSaveFile.data.fontSize = (int) textSizeSpinner.getValue();
        SaveManager.settingsSaveFile.data.iconSize = (int) iconSizeSpinner.getValue();
    }

    @Override
    public void load() {
        textSizeSpinner.setValue(SaveManager.settingsSaveFile.data.fontSize);
        iconSizeSpinner.setValue(SaveManager.settingsSaveFile.data.iconSize);
        updateTextPreview();
        updateIconPreview();
    }

}
