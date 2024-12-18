package github.zmilla93.gui.options;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.modules.theme.ThemeColorVariant;
import github.zmilla93.modules.theme.ThemeColorVariantSetting;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IThemeListener;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class TradeColorPreviewPanel extends JPanel implements IThemeListener {

    private final ThemeColorVariantSetting variantSetting;
    private final JLabel label;
    private boolean colorBlind;

    public TradeColorPreviewPanel(String text, ThemeColorVariantSetting variantSetting) {
        setLayout(new FlowLayout(FlowLayout.CENTER, 10, 4));
        label = new JLabel(text);
        this.variantSetting = variantSetting;
        add(label);
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

}
