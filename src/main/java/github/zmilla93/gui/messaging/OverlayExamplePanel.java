package github.zmilla93.gui.messaging;

import github.zmilla93.core.enums.Anchor;
import github.zmilla93.core.enums.ExpandDirection;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.gui.components.InsetBorder;

import javax.swing.*;
import java.awt.*;

public class OverlayExamplePanel extends JPanel {

    private static final int BORDER_SIZE = 2;
    private final String BORDER_COLOR_KEY = "Separator.foreground";
    private Anchor anchor = null;
    private ExpandDirection expandDirection = null;

    public OverlayExamplePanel(JPanel sizePanel, String text) {
        JPanel labelPanel = new JPanel(new GridBagLayout());
        labelPanel.add(new JLabel(text));
        labelPanel.setOpaque(false);

        CardLayout cardLayout = new CardLayout();
        setLayout(cardLayout);
        add(labelPanel, "0");
        add(sizePanel, "1");
        setBorder(new InsetBorder(BORDER_SIZE, BORDER_COLOR_KEY));
    }

    public void addPanel(JPanel panel) {
        add(panel, Integer.toString(getComponentCount()));
    }

    public void removePanel(JPanel panel) {
        remove(panel);
    }

    public void setAnchor(Anchor anchor) {
        this.anchor = anchor;
        repaint();
    }

    public void setExpandDirection(ExpandDirection expandDirection) {
        this.expandDirection = expandDirection;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (anchor == null && expandDirection == null) return;
        g.setColor(UIManager.getColor(BORDER_COLOR_KEY));
        int iconSize = SaveManager.settingsSaveFile.data.iconSize;
        int x = 0;
        int y = 0;
        if (anchor == Anchor.TOP_RIGHT || anchor == Anchor.BOTTOM_RIGHT) x = getPreferredSize().width - iconSize;
        if (anchor == Anchor.BOTTOM_LEFT || anchor == Anchor.BOTTOM_RIGHT || expandDirection == ExpandDirection.UPWARDS)
            y = getSize().height - iconSize;
        g.fillRect(x, y, iconSize, iconSize);
    }

}
