package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class CharacterSetupPanel extends AbstractSetupPanel {

    private final JTextField characterNameInput = new JTextField(25);

    public CharacterSetupPanel(JButton button) {
        super(button);
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

//        gc.anchor = GridBagConstraints.WEST;
        mainPanel.add(new JLabel("Enter your character name."), gc);
        gc.gridy++;
        mainPanel.add(characterNameInput, gc);
        gc.gridy++;

        gc = ZUtil.getGC();

        setLayout(new GridBagLayout());
        gc.insets = SetupWindow.OUTER_INSETS;
        add(mainPanel, gc);

        characterNameInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                validateNextButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateNextButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Do nothing
            }
        });
    }

    public String getCharacterName() {
        return characterNameInput.getText();
    }

    @Override
    public boolean isSetupValid() {
        return !characterNameInput.getText().trim().equals("");
    }

}
