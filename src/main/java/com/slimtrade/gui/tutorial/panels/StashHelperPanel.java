package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.components.SectionHeader;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;

public class StashHelperPanel extends AbstractTutorialPanel {

    private JLabel info1 = new CustomLabel("Incoming trades cause an info window to appear above your stash.");
    private JLabel info2 = new CustomLabel("Hover to outline the item, left click to search the name, or right click to close the window.");
    private JLabel info3 = new CustomLabel("Stash tab names can be assigned a color or quad in the options window.");

    public StashHelperPanel() {
        gc.insets.bottom = 5;
        container.add(new SectionHeader("Stash Helper"), gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 5;
        container.add(info2, gc);
        gc.gridy++;
        container.add(new ImageLabel("images/stash.png"), gc);
        gc.gridy++;
        container.add(info3, gc);
    }

}
