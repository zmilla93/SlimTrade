package github.zmilla93.gui.options.general;

import github.zmilla93.App;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ClientFileChooser;
import github.zmilla93.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class ClientPathPanel extends JPanel implements ISavable {

    private final JTextField clientTextField = new JTextField(30);
    private final JButton browseClientButton = new JButton("Browse");
    private final JFileChooser fileChooser = new ClientFileChooser();

    public ClientPathPanel() {
        clientTextField.setEditable(false);
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
        browseClientButton.addActionListener(e -> {
            if (fileChooser.showOpenDialog(ClientPathPanel.this) == JFileChooser.APPROVE_OPTION) {
                clientTextField.setText(fileChooser.getSelectedFile().getPath());
            }
        });
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.clientPath = clientTextField.getText();
        if (App.chatParser.getPath() != null && !App.chatParser.getPath().equals(clientTextField.getText())) {
            App.initParser();
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
