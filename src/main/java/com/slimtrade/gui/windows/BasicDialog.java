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

    protected void updateBufferedBounds() {
        bufferedBounds = TradeUtil.getBufferedBounds(getBounds());
    }

    public Rectangle getBufferedBounds() {
        return bufferedBounds;
    }

    @Override
    public void pack() {
        super.pack();
        updateBufferedBounds();
    }

    @Override
    public void setLocation(int x, int y) {
        super.setLocation(x, y);
        updateBufferedBounds();
    }

    @Override
    public void setLocation(@NotNull Point p) {
        super.setLocation(p);
        updateBufferedBounds();
    }

    @Override
    public void setLocationRelativeTo(Component c) {
        super.setLocationRelativeTo(c);
        updateBufferedBounds();
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
        updateBufferedBounds();
    }

    @Override
    public void setSize(Dimension d) {
        super.setSize(d);
        updateBufferedBounds();
    }

    @Override
    public void setSize(int width, int height) {
        super.setSize(width, height);
        updateBufferedBounds();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        updateBufferedBounds();
    }

    @Override
    public void setBounds(Rectangle r) {
        super.setBounds(r);
        updateBufferedBounds();
    }

}
