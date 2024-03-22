package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.listening.IColorBlindChangeListener;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class TradeColorPreviewPanel extends JPanel implements IThemeListener, IColorBlindChangeListener {

    private final ThemeColorVariantSetting variantSetting;
    private final JLabel label;
    private boolean colorBlind;

    public TradeColorPreviewPanel(String text, ThemeColorVariantSetting variantSetting) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 4));
        label = new JLabel(text);
        this.variantSetting = variantSetting;
        add(label);
        SaveManager.settingsSaveFile.data.addColorBlindListener(this);
        setBackground(Color.RED);
        colorBlind = SaveManager.settingsSaveFile.data.colorBlindMode;
        ThemeManager.addThemeListener(this);
    }

    public JLabel label() {
        return label;
    }

    public void updatePreview() {
        variantSetting.setColorBlind(colorBlind);
        Color color = ThemeColorVariant.getColorVariant(variantSetting.variant(), false, colorBlind);
        setBackground(color);
    }

    @Override
    public void onThemeChange() {
        updatePreview();
    }

    @Override
    public void onColorBlindChange(boolean colorBlind) {
        this.colorBlind = colorBlind;
        updatePreview();
    }

}
