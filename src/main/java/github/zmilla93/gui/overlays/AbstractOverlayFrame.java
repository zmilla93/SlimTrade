package github.zmilla93.gui.overlays;

import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.gui.messaging.NotificationPanel;
import github.zmilla93.gui.messaging.OverlayExamplePanel;
import github.zmilla93.gui.windows.AbstractMovableDialog;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;

import javax.swing.*;
import java.awt.*;

/**
 * Used for windows that need are a part of the Overlay editor (IE Menu Bar and MessageManager).
 * Can be given a template panel to set its size and a piece of text to display in the center.
 */
public class AbstractOverlayFrame extends AbstractMovableDialog implements IFontChangeListener {

    protected final JPanel panel;
    private final OverlayExamplePanel overlayPanel;

    public AbstractOverlayFrame(JPanel panel, String text) {
        this.panel = panel;
        overlayPanel = new OverlayExamplePanel(panel, text);
        setOpacity(0.75f);
        contentPanel.setBackground(Color.orange);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.add(overlayPanel);
        pack();
        setLocation(0, 0);
        ThemeManager.addFontListener(this);
    }

    public void addPanel(JPanel panel) {
        overlayPanel.addPanel(panel);
    }

    public void removePanel(JPanel panel) {
        overlayPanel.removePanel(panel);
    }

    public void setAnchor(Anchor anchor) {
        overlayPanel.setAnchor(anchor);
    }

    public void setExpandDirection(ExpandDirection expandDirection) {
        overlayPanel.setExpandDirection(expandDirection);
    }

    public Point getAnchorPoint(Anchor anchor) {
        Point point = getLocation();
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT) point.x += getWidth();
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT) point.y += getHeight();
        return point;
    }

    public Point getAnchorPoint(ExpandDirection expandDirection) {
        Point point = getLocation();
        if (expandDirection == ExpandDirection.UPWARDS) point.y += getHeight();
        return point;
    }

    @Override
    public void onFontChanged() {
        if (panel instanceof NotificationPanel) ((NotificationPanel) panel).updateSize();
        pack();
    }

}
