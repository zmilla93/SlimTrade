package com.slimtrade.gui.setup.panels;

import com.slimtrade.App;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.options.general.AdvancedPanel;
import com.slimtrade.gui.setup.SetupWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Set;

public class ClientPanel extends AbstractSetupPanel implements ISetupValidator{

    private JPanel pathPanel = new JPanel(FrameManager.gridBag);

    private JLabel pathLabel = new JLabel("Client Path");
    public JTextField clientText = new CustomTextField();
    public JButton editButton = new BasicButton("Edit");

    private JLabel info1 = new JLabel("Enter the location of Path of Exile's Client.txt file.");
//    private JLabel info2 = new JLabel("If this file was recently deleted, launch POE to recreate it.");

    private JFrame parent;
    private JFileChooser fileChooser;

    public ClientPanel(JFrame parent) {
        this.parent = parent;
        clientText.setEditable(false);

        // Path Panel
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        pathPanel.add(clientText, gc);
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx++;
        pathPanel.add(editButton, gc);
        gc.gridx = 0;

        // This
        container.add(info1, gc);
        gc.gridy++;
//        this.add(info2, gc);
//        gc.gridy++;
        gc.fill = GridBagConstraints.HORIZONTAL;
        container.add(pathPanel, gc);
        gc.gridy++;
        container.add(Box.createHorizontalStrut(400), gc);


        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            fileChooser = new JFileChooser();
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException | ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            fileChooser = new JFileChooser();
        }

        editButton.addActionListener(e -> {
            int action = fileChooser.showOpenDialog(parent);
            if (action == JFileChooser.APPROVE_OPTION) {
                File clientFile = fileChooser.getSelectedFile();
                String path = clientFile.getPath();
                clientText.setText(path);
                FrameManager.setupWindow.refreshButtons();
            }
        });

        container.setBackground(SetupWindow.BACKGROUND_COLOR);
        pathPanel.setBackground(SetupWindow.BACKGROUND_COLOR);
//        clientText.setBackground(SetupWindow.BACKGROUND_COLOR);
    }

    @Override
    public boolean isValidInput() {
        File file = new File(clientText.getText());
        if(file.exists() && file.isFile()) {
            return true;
        }
        return false;
    }

    @Override
    public void save() {
        App.saveManager.saveFile.clientPath = clientText.getText();
//        File file = new File(clientText.getText());
//        App.saveManager.saveFile.clientDirectory = file.getParent();
//        App.saveManager.saveFile.validClientPath = true;
    }

}
