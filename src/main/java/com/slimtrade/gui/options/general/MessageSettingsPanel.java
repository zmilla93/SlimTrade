package com.slimtrade.gui.options.general;

import com.slimtrade.core.enums.SliderRange;
import com.slimtrade.core.enums.SpinnerRange;
import com.slimtrade.core.enums.SpinnerRangeFloat;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.RangeSlider;
import com.slimtrade.gui.components.RangeSpinner;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class MessageSettingsPanel extends JPanel implements ISavable {

    private final JCheckBox collapseCheckBox = new JCheckBox("Collapse Messages");
    private final JSpinner messagesBeforeCollapseSpinner = new RangeSpinner(SpinnerRange.MESSAGES_BEFORE_COLLAPSE);

    private final JCheckBox fadeCheckBox = new JCheckBox("Fade Messages");
    private final JSpinner secondsBeforeFadeSpinner = new RangeSpinner(SpinnerRangeFloat.SECONDS_BEFORE_FADE);
    private final JLabel fadedOpacityLabel = new JLabel();
    private final JSlider fadeOpacitySlider = new RangeSlider(SliderRange.FADED_OPACITY);

    private final JPanel collapsePanel;
    private final JPanel fadePanel;
    private static final int INSET = 20;

    public MessageSettingsPanel() {

        collapsePanel = createCollapsePanel();
        fadePanel = createFadePanel();
        fadeOpacitySlider.setPreferredSize(new Dimension(Math.round(getPreferredSize().width * 0.75f), fadeOpacitySlider.getPreferredSize().height));

        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(collapseCheckBox, gc);
        gc.gridy++;
        add(collapsePanel, gc);
        gc.gridy++;
        add(fadeCheckBox, gc);
        gc.gridy++;
        add(fadePanel, gc);
        gc.gridy++;
        addListeners();
    }

    private void addListeners() {
        fadeOpacitySlider.addChangeListener(e -> updateOpacityLabel());
        collapseCheckBox.addActionListener(e -> updatePanelVisibility());
        fadeCheckBox.addActionListener(e -> updatePanelVisibility());
    }

    private JPanel createCollapsePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(new JLabel("Messages Before Collapse"), gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.fill = GridBagConstraints.NONE;
        gc.insets.left = INSET;
        panel.add(messagesBeforeCollapseSpinner, gc);
        gc.fill = GridBagConstraints.HORIZONTAL;
        return panel;
    }

    private JPanel createFadePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.anchor = GridBagConstraints.EAST;
        panel.add(new JLabel("Seconds Before Fading"), gc);
        gc.gridx++;
        gc.fill = GridBagConstraints.NONE;
        gc.insets.left = INSET;
        panel.add(secondsBeforeFadeSpinner, gc);
        gc.insets.left = 0;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridx = 0;
        gc.gridy++;
        panel.add(fadedOpacityLabel, gc);
        gc.gridx++;
        panel.add(fadeOpacitySlider, gc);
        return panel;
    }

    private void updatePanelVisibility() {
        collapsePanel.setVisible(collapseCheckBox.isSelected());
        fadePanel.setVisible(fadeCheckBox.isSelected());
    }

    private void updateOpacityLabel() {
        fadedOpacityLabel.setText("Faded Opacity (" + fadeOpacitySlider.getValue() + "%)");
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.collapseMessages = collapseCheckBox.isSelected();
        SaveManager.settingsSaveFile.data.messageCountBeforeCollapse = (int) messagesBeforeCollapseSpinner.getValue();
        SaveManager.settingsSaveFile.data.fadeMessages = fadeCheckBox.isSelected();
        SaveManager.settingsSaveFile.data.secondsBeforeFading = (float) secondsBeforeFadeSpinner.getValue();
        SaveManager.settingsSaveFile.data.fadedOpacity = fadeOpacitySlider.getValue();
    }

    @Override
    public void load() {
        collapseCheckBox.setSelected(SaveManager.settingsSaveFile.data.collapseMessages);
        messagesBeforeCollapseSpinner.setValue(SaveManager.settingsSaveFile.data.messageCountBeforeCollapse);
        fadeCheckBox.setSelected(SaveManager.settingsSaveFile.data.fadeMessages);
        secondsBeforeFadeSpinner.setValue(SaveManager.settingsSaveFile.data.secondsBeforeFading);
        fadeOpacitySlider.setValue(SaveManager.settingsSaveFile.data.fadedOpacity);
        updatePanelVisibility();
        updateOpacityLabel();
    }
}
