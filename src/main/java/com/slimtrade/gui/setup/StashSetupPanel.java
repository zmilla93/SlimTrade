package com.slimtrade.gui.setup;

import com.slimtrade.core.enums.AppState;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StashSetupPanel extends AbstractSetupPanel {

    private final JButton editStashButton = new JButton("Edit Stash Overlay");

    public StashSetupPanel(JButton button) {
        super(button);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int inset = 20;
        gc.insets = new Insets(0, inset, 0, inset);
        add(new JLabel("Resize the overlay until it aligns with the in game stash."), gc);
        gc.gridy++;
        add(editStashButton, gc);
        editStashButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FrameManager.setWindowVisibility(AppState.EDIT_STASH);
            }
        });
    }

    @Override
    public boolean isSetupValid() {
        return SaveManager.stashSaveFile.data.windowRect != null && SaveManager.stashSaveFile.data.gridRect != null;
    }

}
