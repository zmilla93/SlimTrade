package github.zmilla93.gui.options.display;

import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.poe.ThemeLabel;
import github.zmilla93.gui.components.poe.ThemePanel;
import github.zmilla93.gui.options.AbstractOptionPanel;
import github.zmilla93.modules.theme.ThemeColor;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.ColorblindChangeListener;

import javax.swing.*;
import java.awt.*;

public class DisplayPreviewPanel extends AbstractOptionPanel implements ColorblindChangeListener {

    private final JLabel fontPreviewLabel = new JLabel("You are captured, stupid beast!");
    private final IconButton iconPreviewButton = new IconButton(DefaultIcon.TAG);

    private final JLabel approveLabel = new ThemeLabel(ThemeColor.APPROVE, "Green Label.").bold();
    private final JLabel denyLabel = new ThemeLabel(ThemeColor.DENY, "Red Label.").bold();
    private final JLabel indeterminateLabel = new ThemeLabel(ThemeColor.INDETERMINATE, "Yellow Label.").bold();

//    private final JPanel bottomPanel;

    public DisplayPreviewPanel() {
        super(false, false);
        setLayout(new GridBagLayout());
        iconPreviewButton.setFocusable(false);
        add(new ComponentPanel(iconPreviewButton, fontPreviewLabel));
        add(createColorPreviewPanel());
        updateColorblindLabels();
        ThemeManager.addColorblindChangeListener(this);
    }

    private JPanel createColorPreviewPanel() {
        JPanel incomingPanel = new ThemePanel(ThemeColor.INCOMING_MESSAGE, true);
        incomingPanel.add(new JLabel("Incoming Trade"));
        JPanel outgoingPanel = new ThemePanel(ThemeColor.OUTGOING_MESSAGE, true);
        outgoingPanel.add(new JLabel("Outgoing Trade"));
        JPanel scannerPanel = new ThemePanel(ThemeColor.SCANNER_MESSAGE, true);
        scannerPanel.add(new JLabel("Scanner Message"));

        int horizontalGap = 4;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        panel.add(approveLabel, gc);
        gc.insets.left = horizontalGap;
        gc.gridx++;
        panel.add(denyLabel, gc);
        gc.gridx++;
        panel.add(indeterminateLabel, gc);
        gc.gridx = 0;
        gc.gridy++;
        gc.insets.left = 0;
        panel.add(incomingPanel, gc);
        gc.gridx++;
        gc.insets.left = horizontalGap;
        panel.add(outgoingPanel, gc);
        gc.gridx++;
        panel.add(scannerPanel, gc);
        return panel;
    }

    public void setPreviewFontStyle(Font font) {
        Font previousFont = fontPreviewLabel.getFont();
        Font newFont = font.deriveFont(previousFont.getStyle(), previousFont.getSize());
        fontPreviewLabel.setFont(newFont);
//        incomingTradePreview.label().setFont(newFont);
//        outgoingTradePreview.label().setFont(newFont);
//        scannerPreview.label().setFont(newFont);
    }

    public void setPreviewFontSize(int fontSize) {
        Font font = fontPreviewLabel.getFont();
        font = font.deriveFont(font.getStyle(), fontSize);
        fontPreviewLabel.setFont(font);
//        incomingTradePreview.label().setFont(font);
//        outgoingTradePreview.label().setFont(font);
//        scannerPreview.label().setFont(font);
    }

    public void setPreviewIconSize(int iconSize) {
        iconPreviewButton.setIconSize(iconSize);
    }

    private void updateColorblindLabels() {
        updateColorblindLabels(SaveManager.settingsSaveFile.data.colorBlindMode);
    }

    private void updateColorblindLabels(boolean colorblindMode) {
        if (colorblindMode) {
            approveLabel.setText("Blue Label.");
            denyLabel.setText("Pink Label.");
        } else {
            approveLabel.setText("Green Label.");
            denyLabel.setText("Red Label.");
        }
    }

    @Override
    public void onColorblindChange(boolean colorblindMode) {
        updateColorblindLabels(colorblindMode);
    }

}
