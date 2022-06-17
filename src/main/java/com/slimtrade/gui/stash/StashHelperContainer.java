package com.slimtrade.gui.stash;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IThemeListener;

import javax.swing.*;
import java.awt.*;

public class StashHelperContainer extends BasicDialog implements IThemeListener {

    private static final int OFFSET = 30;
    private static final int FOLDER_OFFSET = 65;
    private final GridBagConstraints gc = ZUtil.getGC();
    public static final int INSET = 4;

    public StashHelperContainer() {
        contentPanel.setLayout(new GridBagLayout());
        gc.anchor = GridBagConstraints.SOUTH;
        gc.insets.right = INSET;

        setBackground(ColorManager.TRANSPARENT);
        setVisible(true);
        updateLocation();
        ColorManager.addThemeListener(this);
    }

    public void updateLocation() {
        Point target = SaveManager.stashSaveFile.data.gridRect.getLocation();
        target.y -= getHeight() + OFFSET;
        setLocation(target);
    }

    public Component addHelper(TradeOffer offer) {
        Component panel = new StashHelperPanel(offer);
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(panel, gc);
        refresh();
        return panel;
    }

    public void addHelper(Component component) {
        gc.gridx = contentPanel.getComponentCount();
        contentPanel.add(component, gc);
        refresh();
    }

    public void refresh() {
        revalidate();
        repaint();
        pack();
        updateLocation();
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public void onThemeChange() {
        refresh();
    }
}
