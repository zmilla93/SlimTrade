package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.custom.CustomCheckbox;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomSlider;
import com.slimtrade.gui.custom.CustomSpinner;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class MessageSettingsPanel extends ContainerPanel implements ISaveable {

    // Collapse
    private final JCheckBox collapseCheckbox;
    private final JLabel collapseSpinnerLabel;
    private final JSpinner collapseSpinner;

    // Opacity
    private final JCheckBox opacityCheckbox;
    private final JLabel opacityDelayLabel;
    private final JSpinner opacityDelaySpinner;
    private final JLabel opacitySliderLabel;
    private final JSlider opacitySlider;

    public MessageSettingsPanel() {

        /*
         * Create UI
         */

        // Create Collapse Toggle
        JLabel collapseCheckboxLabel = new CustomLabel("Collapse excessive messages");
        collapseCheckbox = new CustomCheckbox();
        collapseCheckbox.addActionListener(e -> updateCollapseToggle());

        // Create Collapse Spinner
        collapseSpinnerLabel = new CustomLabel("Messages shown before collapse");
        collapseSpinner = new CustomSpinner(new SpinnerNumberModel(3, 1, 10, 1));

        // Create Opacity Toggle
        JLabel opacityCheckboxLabel = new CustomLabel("Fade messages after duration");
        opacityCheckbox = new CustomCheckbox();
        opacityCheckbox.addActionListener(e -> updateOpacityToggle());

        // Create Opacity Delay
        opacityDelayLabel = new CustomLabel("Seconds before fading");
        opacityDelaySpinner = new CustomSpinner(new SpinnerNumberModel(3, 0.5, 8, 0.5));

        // Create Opacity Slider
        opacitySliderLabel = new CustomLabel();
        opacitySlider = new CustomSlider();
        opacitySlider.setMinimum(10);
        opacitySlider.setMaximum(90);
        opacitySlider.setMinorTickSpacing(5);
        opacitySlider.setMajorTickSpacing(20);
        opacitySlider.addChangeListener(e -> updateOpacityText());

        /*
         * Build UI
         */

        // Collapse Toggle
        gc.insets.bottom = 2;
        gc.anchor = GridBagConstraints.WEST;
        container.add(collapseCheckboxLabel, gc);
        gc.gridx += 2;
        gc.anchor = GridBagConstraints.EAST;
        container.add(collapseCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Collapse Limit
        gc.anchor = GridBagConstraints.WEST;
        container.add(collapseSpinnerLabel, gc);
        gc.gridx++;
        container.add(Box.createHorizontalStrut(10), gc);
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        container.add(collapseSpinner, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Opacity Toggle
        gc.anchor = GridBagConstraints.WEST;
        container.add(opacityCheckboxLabel, gc);
        gc.gridx += 2;
        gc.anchor = GridBagConstraints.EAST;
        container.add(opacityCheckbox, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Opacity Delay
        gc.anchor = GridBagConstraints.WEST;
        container.add(opacityDelayLabel, gc);
        gc.gridx += 2;
        gc.anchor = GridBagConstraints.EAST;
        container.add(opacityDelaySpinner, gc);
        gc.gridx = 0;
        gc.gridy++;

        // Opacity Slider
        gc.anchor = GridBagConstraints.WEST;
        container.add(opacitySliderLabel, gc);
        gc.gridx++;
        gc.gridwidth = 2;
        gc.anchor = GridBagConstraints.EAST;
        container.add(opacitySlider, gc);
        gc.gridx = 0;
        gc.gridy++;

        load();

    }

    private void updateCollapseToggle() {
        if (collapseCheckbox.isSelected()) {
            collapseSpinnerLabel.setVisible(true);
            collapseSpinner.setVisible(true);
        } else {
            collapseSpinnerLabel.setVisible(false);
            collapseSpinner.setVisible(false);
        }
    }

    private void updateOpacityToggle() {
        if (opacityCheckbox.isSelected()) {
            opacityDelayLabel.setVisible(true);
            opacityDelaySpinner.setVisible(true);
            opacitySliderLabel.setVisible(true);
            opacitySlider.setVisible(true);
        } else {
            opacityDelayLabel.setVisible(false);
            opacityDelaySpinner.setVisible(false);
            opacitySliderLabel.setVisible(false);
            opacitySlider.setVisible(false);
        }
    }

    private void updateOpacityText() {
        opacitySliderLabel.setText("Faded Opacity (" + opacitySlider.getValue() + "%)");
    }

    @Override
    public void updateColor() {
        super.updateColor();
        this.setBackground(ColorManager.BACKGROUND);
    }

    @Override
    public void save() {
        App.saveManager.saveFile.collapseExcessiveMessages = collapseCheckbox.isSelected();
        App.saveManager.saveFile.messageCountBeforeCollapse = (int) collapseSpinner.getValue();
        App.saveManager.saveFile.fadeAfterDuration = opacityCheckbox.isSelected();
        App.saveManager.saveFile.secondsBeforeFading = (double) opacityDelaySpinner.getValue();
        App.saveManager.saveFile.fadeOpacityPercent = opacitySlider.getValue();
    }

    @Override
    public void load() {
        collapseCheckbox.setSelected(App.saveManager.saveFile.collapseExcessiveMessages);
        collapseSpinner.setValue(App.saveManager.saveFile.messageCountBeforeCollapse);
        opacityCheckbox.setSelected(App.saveManager.saveFile.fadeAfterDuration);
        opacityDelaySpinner.setValue(App.saveManager.saveFile.secondsBeforeFading);
        opacitySlider.setValue(App.saveManager.saveFile.fadeOpacityPercent);
        updateCollapseToggle();
        updateOpacityToggle();
        updateOpacityText();
    }
}