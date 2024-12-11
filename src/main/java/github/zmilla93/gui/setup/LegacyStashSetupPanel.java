package github.zmilla93.gui.setup;

import github.zmilla93.core.enums.AppState;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;

@Deprecated
public class LegacyStashSetupPanel extends AbstractSetupPanel {

    public LegacyStashSetupPanel() {
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
    public void initializeComponents() {
        // Legacy class, do nothing
    }

    @Override
    public void addComponentListeners() {
        // Legacy class, do nothing
    }

    @Override
    public boolean isSetupValid() {
        return SaveManager.stashSaveFile.data.gridRect != null;
    }

    @Override
    public void applyCompletedSetup() {
        // Legacy class, do nothing
    }

}
