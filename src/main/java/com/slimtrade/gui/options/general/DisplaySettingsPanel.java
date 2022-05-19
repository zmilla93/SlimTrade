package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.components.SpinnerRangeModel;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class DisplaySettingsPanel extends JPanel implements ISavable {

    private final JLabel textPreviewLabel = new JLabel("You are captured, stupid beast!");
    private IconButton iconPreviewButton = new IconButton("/icons/default/tagx64.png");
    private final JSpinner textSizeSpinner = new JSpinner();
    private final JSpinner iconSizeSpinner = new JSpinner();

    public DisplaySettingsPanel() {
        GridBagConstraints gc = ZUtil.getGC();
        JLabel textSizeLabel = new JLabel("Text Size");
        JLabel iconSizeLabel = new JLabel("Icon Size");
        iconPreviewButton.setFocusable(false);

        // Spinners
        SpinnerNumberModel textSpinnerModel = new SpinnerRangeModel(SpinnerRange.TEXT_SIZE);
        SpinnerNumberModel iconSpinnerModel = new SpinnerRangeModel(SpinnerRange.ICON_SIZE);
        textSizeSpinner.setModel(textSpinnerModel);
        iconSizeSpinner.setModel(iconSpinnerModel);

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
//        JPanel previewPanel = new JPanel(new GridBagLayout());
        JPanel previewPanel = new JPanel(new FlowLayout());
        previewPanel.add(iconPreviewButton);
//        previewPanel.add(iconPreviewButton, gc);
        gc.gridx++;
        previewPanel.add(textPreviewLabel);
//        previewPanel.add(textPreviewLabel, gc);

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
    }

    private void updateIconPreview() {
        int size = (int) iconSizeSpinner.getValue();
        iconPreviewButton.setIconSize(size);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.textSize = (int) textSizeSpinner.getValue();
        SaveManager.settingsSaveFile.data.iconSize = (int) iconSizeSpinner.getValue();
    }

    @Override
    public void load() {
        textSizeSpinner.setValue(SaveManager.settingsSaveFile.data.textSize);
        iconSizeSpinner.setValue(SaveManager.settingsSaveFile.data.iconSize);
        updateTextPreview();
        updateIconPreview();
    }
}
