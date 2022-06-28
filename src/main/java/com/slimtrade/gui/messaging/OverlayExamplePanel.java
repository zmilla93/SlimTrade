package com.slimtrade.gui.messaging;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.awt.*;

public class OverlayExamplePanel extends JPanel {

    private final String COLOR_KEY = "Separator.foreground";
    private Anchor anchor = null;

    public OverlayExamplePanel(JPanel panel, String text) {
        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
        setPreferredSize(getPreferredSize());
        remove(panel);
        setLayout(new GridBagLayout());
        JLabel label = new JLabel(text);
        add(label);
        updateUI();
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
        repaint();
    }

    @Override
    public void updateUI() {
        setBorder(BorderFactory.createLineBorder(UIManager.getColor(COLOR_KEY), 2));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
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
