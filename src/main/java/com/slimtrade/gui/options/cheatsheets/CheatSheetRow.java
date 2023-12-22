package com.slimtrade.gui.options.cheatsheets;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.enums.SliderRange;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.*;

import javax.swing.*;
import java.awt.*;

public class CheatSheetRow extends AddRemovePanel {

    private CheatSheetData data;
    private HotkeyButton hotkeyButton = new HotkeyButton();
    private JSlider opacitySlider = new RangeSlider(SliderRange.CHEAT_SHEET_OPACITY);
    private JSlider scaleSlider = new RangeSlider(SliderRange.CHEAT_SHEET_SCALE);
    private JLabel opacityLabel = new JLabel();
    private JLabel scaleLabel = new JLabel();

    private static final int INSET = 0;

    public CheatSheetRow(AddRemoveContainer parent, CheatSheetData data) {
        super(parent);
        this.data = data;
        setupSlider(opacitySlider);
        setupSlider(scaleSlider);
        JLabel fileLabel = new JLabel(data.title);
        hotkeyButton.setData(data.hotkeyData);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.VERTICAL;
        gc.weighty = 1;
        gc.weightx = 1;
        add(new ButtonWrapper(hotkeyButton), gc);
        gc.weightx = 0;
        gc.gridx++;
        add(opacityLabel, gc);
        gc.gridx++;
        add(opacitySlider, gc);
        gc.gridx++;
//        gc.insets.left = INSET;
//        add(scaleLabel, gc);
//        gc.gridx++;
//        gc.insets.left = 0;
//        add(scaleSlider, gc);
//        gc.gridx++;
        gc.insets.left = INSET;
        add(fileLabel, gc);
        gc.gridx++;
        updateOpacityLabel();
        addListeners();
    }

    private void addListeners() {
        opacitySlider.addChangeListener(e -> updateOpacityLabel());
        scaleSlider.addChangeListener(e -> updateScaleLabel());
    }

    private void setupSlider(JSlider slider) {
        slider.setPreferredSize(new Dimension(80, slider.getPreferredSize().height));
    }

    private void updateOpacityLabel() {
        opacityLabel.setText("Opacity (" + opacitySlider.getValue() + "%)");
    }

    private void updateScaleLabel() {
        scaleLabel.setText("Scale (" + scaleSlider.getValue() + "%)");
    }

    public CheatSheetData getData() {
        data.hotkeyData = hotkeyButton.getData();
        return data;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (opacityLabel == null) return;
        opacityLabel.setText("Opacity (100%)");
        opacityLabel.setPreferredSize(opacityLabel.getPreferredSize());
        scaleLabel.setText("Scale (100%)");
        scaleLabel.setPreferredSize(scaleLabel.getPreferredSize());
        updateOpacityLabel();
    }
}
