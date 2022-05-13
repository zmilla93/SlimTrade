package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;

public class PathOfExilePanel extends JPanel implements ISavable {

    private JLabel clientLabel = new JLabel("Client Path");
    private JTextField clientTextField = new JTextField(30);
    private JButton browseClientButton = new JButton("Browse");
    private JFileChooser fileChooser = new JFileChooser();

    public PathOfExilePanel() {
        clientTextField.setEditable(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));

        setLayout(new GridBagLayout());
        JPanel clientPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        clientPanel.add(clientLabel, gc);
        gc.gridx++;
        clientPanel.add(clientTextField, gc);
        gc.gridx++;
        clientPanel.add(browseClientButton, gc);
        gc.gridx++;


        gc = ZUtil.getGC();
        add(clientPanel, gc);
        addListeners();
    }

    private void addListeners() {
        JPanel self = this;
        browseClientButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(self) == JFileChooser.APPROVE_OPTION) {
                clientTextField.setText(fileChooser.getSelectedFile().getPath());
            }
        });
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.clientPath = clientTextField.getText();
        // FIXME:
        System.out.println("!" + App.chatParser.getPath());
        System.out.println("#" + clientTextField.getText());
        if (!App.chatParser.getPath().equals(clientTextField.getText())) {
            App.chatParser.open(clientTextField.getText());
        }
    }

    @Override
    public void load() {
        clientTextField.setText(SaveManager.settingsSaveFile.data.clientPath);
    }

    @Override
    public void updateUI() {
        if (fileChooser != null) fileChooser.updateUI();
    }
}
