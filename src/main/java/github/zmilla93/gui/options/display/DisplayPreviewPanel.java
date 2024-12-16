package github.zmilla93.gui.options.display;

import github.zmilla93.App;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.options.TradeColorPreviewPanel;
import github.zmilla93.gui.utility.GuiUtil;
import github.zmilla93.modules.theme.ThemeColorVariant;
import github.zmilla93.modules.theme.ThemeColorVariantSetting;

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

        JLabel approveLabel = new JLabel("Green Label.");
        JLabel denyLabel = new JLabel("Red Label.");
        GuiUtil.setLabelColorKey(approveLabel, "Objects.GreenAndroid");
        GuiUtil.setLabelColorKey(denyLabel, "Objects.Red");

        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(new ComponentPanel(iconPreviewButton, fontPreviewLabel), gc);
        gc.gridy++;
        add(new ComponentPanel(approveLabel, denyLabel), gc);
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
