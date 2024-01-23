package com.slimtrade.gui.overlays;

import com.slimtrade.core.enums.Anchor;
import com.slimtrade.gui.messaging.NotificationPanel;
import com.slimtrade.gui.messaging.OverlayExamplePanel;
import com.slimtrade.gui.windows.AbstractMovableDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Used for windows that need are a part of the Overlay editor (IE Menu Bar and MessageManager).
 * Can be given a template panel to set its size and a piece of text to display in the center.
 */
public class AbstractOverlayFrame extends AbstractMovableDialog implements IFontChangeListener {

    private final JPanel panel;
    private final OverlayExamplePanel overlayPanel;

    public AbstractOverlayFrame(JPanel panel, String text) {
        this.panel = panel;
        overlayPanel = new OverlayExamplePanel(panel, text);
        setOpacity(0.75f);
        contentPanel.setBackground(Color.orange);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(overlayPanel, BorderLayout.CENTER);

        pack();
        setLocation(0, 0);
        ThemeManager.addFontListener(this);
    }

    public void setAnchor(Anchor anchor) {
        overlayPanel.setAnchor(anchor);
    }

    public Point getAnchorPoint(Anchor anchor) {
        Point point = getLocation();
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT) point.x += getWidth();
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT) point.y += getHeight();
        return point;
    }

    @Override
    public void onFontChanged() {
        if (panel instanceof NotificationPanel) ((NotificationPanel) panel).updateSize();
        pack();
    }

}
