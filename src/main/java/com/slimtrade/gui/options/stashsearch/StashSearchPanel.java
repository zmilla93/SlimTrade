package com.slimtrade.gui.options.stashsearch;

import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.general.LabelComponentPanel;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;

public class StashSearchPanel extends ContainerPanel {

    JLabel info1 = new CustomLabel("Quickly search phrases inside your stash to help with sorting.");

    JButton addButton = new BasicButton("Add Terms");


    public StashSearchPanel() {

        JLabel toggleOverlayLabel = new CustomLabel("Toggle Search Overlay");
        HotkeyInputPane toggleInputPane = new HotkeyInputPane();
        LabelComponentPanel togglePanel = new LabelComponentPanel(toggleOverlayLabel, toggleInputPane);

        container.add(info1, gc);
        gc.gridy++;
        container.add(togglePanel, gc);


    }

}
