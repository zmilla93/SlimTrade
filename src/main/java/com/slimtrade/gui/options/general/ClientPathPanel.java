package com.slimtrade.gui.options.general;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;

public class ClientPathPanel extends JPanel implements ISavable {

    private final JTextField clientTextField = new JTextField(30);
    private final JButton browseClientButton = new JButton("Browse");
    private final JFileChooser fileChooser = new JFileChooser();

    public ClientPathPanel() {
        clientTextField.setEditable(false);
        fileChooser.setFileFilter(new FileNameExtensionFilter("TEXT FILES", "txt", "text"));
        setLayout(new GridBagLayout());
        JPanel clientPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        clientPanel.add(new JLabel("Client Path"), gc);
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
        if (App.chatParser.getPath() != null && !App.chatParser.getPath().equals(clientTextField.getText())) {
            App.initParsers();
        }
    }

    @Override
    public void load() {
        clientTextField.setText(SaveManager.settingsSaveFile.data.clientPath);
        if (SaveManager.settingsSaveFile.data.clientPath == null) return;
        File file = new File(SaveManager.settingsSaveFile.data.clientPath);
        if (file.isFile()) {
            fileChooser.setCurrentDirectory(file.getParentFile());
        }
    }

    @Override
    public void updateUI() {
        if (fileChooser != null) fileChooser.updateUI();
    }

}
