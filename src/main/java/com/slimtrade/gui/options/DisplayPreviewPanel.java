package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.modules.theme.ThemeColorVariant;
import com.slimtrade.modules.theme.ThemeColorVariantSetting;

import javax.swing.*;
import java.awt.*;

public class DisplayPreviewPanel extends JPanel {

    private final JLabel fontPreviewLabel = new JLabel("You are captured, stupid beast!");
    private final IconButton iconPreviewButton = new IconButton(DefaultIcon.TAG);

    private final TradeColorPreviewPanel incomingTradePreview;
    private final TradeColorPreviewPanel outgoingTradePreview;
    private final TradeColorPreviewPanel scannerPreview;

    private final JPanel bottomPanel;

    public DisplayPreviewPanel() {
        setLayout(new GridBagLayout());
        iconPreviewButton.setFocusable(false);

        incomingTradePreview = new TradeColorPreviewPanel("Incoming Trade", new ThemeColorVariantSetting(ThemeColorVariant.GREEN));
        outgoingTradePreview = new TradeColorPreviewPanel("Outgoing Trade", new ThemeColorVariantSetting(ThemeColorVariant.RED));
        scannerPreview = new TradeColorPreviewPanel("Scanner Message", new ThemeColorVariantSetting(ThemeColorVariant.ORANGE));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(iconPreviewButton);
        topPanel.add(fontPreviewLabel);

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.add(incomingTradePreview);
        bottomPanel.add(outgoingTradePreview);
        bottomPanel.add(scannerPreview);

        JPanel debugPanel = new JPanel(new FlowLayout());
        JButton disabledButton = new JButton("Button");
        disabledButton.setEnabled(false);
        debugPanel.add(new JButton("Button"));
        debugPanel.add(disabledButton);

        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(topPanel, gc);
        gc.gridy++;
        add(bottomPanel, gc);
        gc.gridy++;
        if (App.debug) {
            add(debugPanel, gc);
            gc.gridy++;
        }
        updateUI();
    }

    public void setPreviewFontStyle(Font font) {
        Font previousFont = fontPreviewLabel.getFont();
        Font newFont = font.deriveFont(previousFont.getStyle(), previousFont.getSize());
        fontPreviewLabel.setFont(newFont);
        incomingTradePreview.label().setFont(newFont);
        outgoingTradePreview.label().setFont(newFont);
        scannerPreview.label().setFont(newFont);
    }

    public void setPreviewFontSize(int fontSize) {
        Font font = fontPreviewLabel.getFont();
        font = font.deriveFont(font.getStyle(), fontSize);
        fontPreviewLabel.setFont(font);
        incomingTradePreview.label().setFont(font);
        outgoingTradePreview.label().setFont(font);
        scannerPreview.label().setFont(font);
    }

    public void setPreviewIconSize(int iconSize) {
        iconPreviewButton.setIconSize(iconSize);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (bottomPanel == null) return;
        bottomPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Button.borderColor"), 2));
    }

}
