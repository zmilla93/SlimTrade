package com.slimtrade.gui.options.ignore;

import com.slimtrade.core.data.IgnoreItemData;
import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.windows.CustomDialog;

import java.awt.*;

public class ItemIgnoreWindow extends CustomDialog {

    IgnoreInputPanel inputPanel = new IgnoreInputPanel();

    public ItemIgnoreWindow() {
        super("Ignore Item");
        setMinimumSize(new Dimension(0, 0));
        pinButton.setVisible(false);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        int inset = 20;
        gc.insets = new Insets(inset, inset, inset, inset);
        contentPanel.add(inputPanel);
        pack();
        Dimension size = getSize();
        size.width += 120;
        size.height += 40;
        setSize(size);
        setMinimumSize(getSize());
        addListeners();
    }

    private void addListeners() {
        inputPanel.getIgnoreButton().addActionListener(e -> {
            FrameManager.optionsWindow.getIgnorePanel().tryAddIgnoreItem(inputPanel.getIgnoreItem());
            FrameManager.optionsWindow.getIgnorePanel().save();
            SaveManager.ignoreSaveFile.saveToDisk();
            setVisible(false);
        });
    }

    public void setItemName(String itemName) {
        inputPanel.setIgnoreItem(new IgnoreItemData(itemName, MatchType.EXACT_MATCH, 60));
        setLocationRelativeTo(null);
        pack();
        setVisible(true);
        revalidate();
        repaint();
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) inputPanel.getIgnoreButton().requestFocus();
    }

}
