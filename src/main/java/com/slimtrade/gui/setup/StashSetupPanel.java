package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;

public class StashSetupPanel extends AbstractSetupPanel {

    public StashSetupPanel(JButton button) {
        super(button);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
//        int inset = 20;
//        gc.insets = new Insets(0, inset, 0, inset);
        contentPanel.add(new JLabel("Resize the overlay until it aligns with the in game stash."), gc);
        gc.gridy++;
        JButton editStashButton = new JButton("Edit Stash Overlay");
        contentPanel.add(editStashButton, gc);
        editStashButton.addActionListener(e -> FrameManager.setWindowVisibility(AppState.EDIT_STASH));
    }

    @Override
    public boolean isSetupValid() {
        return SaveManager.stashSaveFile.data.windowRect != null && SaveManager.stashSaveFile.data.gridRect != null;
    }

}
