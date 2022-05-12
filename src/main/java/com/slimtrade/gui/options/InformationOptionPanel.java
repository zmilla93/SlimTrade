package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class InformationOptionPanel extends AbstractOptionPanel {

    public InformationOptionPanel() {
        contentPanel.add(new JLabel("Info!"));

        JButton saveFolderButton = new JButton("Open Install Folder");

        saveFolderButton.addActionListener(e -> {
            File file = new File(SaveManager.getSaveDirectory());
            try {
                Desktop.getDesktop().open(file);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        contentPanel.add(saveFolderButton);

    }

}
