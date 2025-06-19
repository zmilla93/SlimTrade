package github.zmilla93.gui.stash;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Displays a grid to match Path of Exile's stash grid.
 */
public class GridPanel extends JPanel {

    private Color lineColor = Color.WHITE;
    private GridType gridType = GridType.Normal;

    private enum GridType {None, Normal, Quad}

    public void cycleGrid() {
        int index = gridType.ordinal() + 1;
        if (index >= GridType.values().length) index = 0;
        gridType = GridType.values()[index];
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(lineColor);
        g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
        if (gridType == GridType.None) return;
        int lineCount = 12;
        if (gridType == GridType.Quad) lineCount = 24;
        for (int i = 1; i < lineCount; i++) {
            float mult = i / (float) lineCount;
            int x = Math.round(getWidth() * mult);
            int y = Math.round(getHeight() * mult);
            g.drawLine(x, 0, x, getHeight());
            g.drawLine(0, y, getWidth(), y);
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        Color c1 = UIManager.getColor("Panel.background");
        Color c2 = UIManager.getColor("Label.foreground");
        lineColor = ThemeManager.getLighterColor(c1, c2);
    }

}
