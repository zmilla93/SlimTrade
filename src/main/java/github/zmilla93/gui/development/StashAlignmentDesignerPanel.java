package github.zmilla93.gui.development;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.saving.ISaveListener;

import javax.swing.*;
import java.awt.*;

/**
 * Draws cells above the stash window.
 * Used during development for recording location of stash UI elements.
 */
public class StashAlignmentDesignerPanel extends JPanel implements ISaveListener {

    private int initialX;
    private int initialY;
    private int width;
    private int height;

    private int countX;
    private int offsetX;
    private int countY;
    private int offsetY;

    public StashAlignmentDesignerPanel() {
        setBorder(BorderFactory.createLineBorder(Color.RED));
        updateSize();
        SaveManager.stashSaveFile.addListener(this);
    }

    public void applyProperties(int x, int y, int with, int height) {
        this.initialX = x;
        this.initialY = y;
        this.width = with;
        this.height = height;
        repaint();
    }

    public void applyOffsets(int countX, int offsetX, int countY, int offsetY) {
        this.countX = countX;
        this.offsetX = offsetX;
        this.countY = countY;
        this.offsetY = offsetY;
    }

    private void updateSize() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        if (rect == null) return;
        setMinimumSize(rect.getSize());
        setPreferredSize(rect.getSize());
        setSize(rect.getSize());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ZUtil.clearTransparentComponent(g, this);
        g.setColor(Color.GREEN);
        for (int y = 0; y < countY; y++) {
            for (int x = 0; x < countX; x++) {
                g.drawRect(initialX + (x * width + x * offsetX), initialY + (y * height + y * offsetY), width, height);
            }
        }
    }

    @Override
    public void onSave() {
        updateSize();
    }

}
