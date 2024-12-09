package com.slimtrade.gui.setup;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ClientFileChooser;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class LegacyClientSetupPanel extends AbstractSetupPanel {

    private final JButton browseButton = new JButton("Browse");
    private final JTextField clientTextField = new JTextField(25);
    private final JFileChooser fileChooser = new ClientFileChooser();

    public LegacyClientSetupPanel(JButton button) {
        super(button);
        contentPanel.setLayout(new GridBagLayout());
        clientTextField.setEditable(false);

        JPanel selectionPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        ArrayList<String> paths = SaveManager.getPotentialClients();
        for (String s : paths) {
            JButton selectButton = new JButton("Select");
            selectionPanel.add(selectButton, gc);
            gc.gridx++;
            gc.fill = GridBagConstraints.HORIZONTAL;
            selectionPanel.add(new JLabel(s), gc);
            gc.fill = GridBagConstraints.NONE;
            gc.gridx = 0;
            gc.gridy++;
            selectButton.addActionListener(e -> {
                clientTextField.setText(s);
                validateNextButton();
            });
        }

        selectionPanel.add(browseButton, gc);
        gc.gridx++;
        gc.fill = GridBagConstraints.HORIZONTAL;
        selectionPanel.add(clientTextField, gc);
        gc.fill = GridBagConstraints.NONE;

        contentPanel.add(new JLabel("Select Path of Exile's Client.txt file."), gc);
        gc.gridy++;
        contentPanel.add(new JLabel("This is located in the logs folder of Path of Exile's install folder."), gc);
        gc.gridy++;

        gc.insets.top = 10;
        contentPanel.add(selectionPanel, gc);
        gc.insets.top = 0;
        gc.gridy++;

        addListeners();
    }

    private void addListeners() {
        browseButton.addActionListener(e -> {
            int result = fileChooser.showOpenDialog(LegacyClientSetupPanel.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                clientTextField.setText(fileChooser.getSelectedFile().getPath());
                validateNextButton();
            }
        });
    }

    public String getClientPath() {
        return clientTextField.getText();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (fileChooser != null) fileChooser.updateUI();
    }

    @Override
    public boolean isSetupValid() {
        String text = clientTextField.getText();
        return !text.trim().equals("");
    }

}
