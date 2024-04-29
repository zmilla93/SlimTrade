package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.modules.theme.ThemeManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class BasicDialog extends VisibilityDialog {

    protected final JPanel contentPanel = new JPanel();
    private Rectangle bufferedBounds;

    public BasicDialog() {
        setUndecorated(true);
        setContentPane(contentPanel);
        setFocusable(false);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);
        setType(JDialog.Type.UTILITY);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ThemeManager.addFrame(this);
    }

    protected void updateBounds() {
        bufferedBounds = TradeUtil.getBufferedBounds(getBounds());
    }

    public Rectangle getBufferedBounds() {
        return bufferedBounds;
    }

    @Override
    public void pack() {
        super.pack();
        updateBounds();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        updateBounds();
    }

    @Override
    public void setLocation(@NotNull Point p) {
        super.setLocation(p);
        updateBounds();
    }

    @Override
    public void setLocationRelativeTo(Component c) {
        super.setLocationRelativeTo(c);
        updateBounds();
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
        updateBounds();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        updateBounds();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        updateBounds();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateBounds();
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        updateBounds();
    }

}
