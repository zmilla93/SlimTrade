package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.InsetBorder;

import javax.swing.*;
import java.awt.*;

public class OverlayExamplePanel extends JPanel {

    private static final int BORDER_SIZE = 2;
    private final String COLOR_KEY = "Separator.foreground";
    private Anchor anchor = null;

    public OverlayExamplePanel(JPanel sizePanel, String text) {
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.add(new JLabel(text));
        labelPanel.setOpaque(false);

        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(sizePanel, "panel");
        add(labelPanel, "label");
        cardLayout.show(this, "label");
        setBorder(new InsetBorder(BORDER_SIZE, COLOR_KEY));
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (anchor == null) return;
        g.setColor(UIManager.getColor(COLOR_KEY));
        int iconSize = SaveManager.settingsSaveFile.data.iconSize;
        int x = 0;
        int y = 0;
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT) x = getPreferredSize().width - iconSize;
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT) y = getSize().height - iconSize;
        g.fillRect(x, y, iconSize, iconSize);
    }

}
