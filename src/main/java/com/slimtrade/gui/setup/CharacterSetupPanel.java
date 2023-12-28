package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.listening.TextChangeListener;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import java.awt.*;

public class CharacterSetupPanel extends AbstractSetupPanel {

    private final JTextField characterNameInput = new JTextField(20);

    public CharacterSetupPanel(JButton button) {
        super(button);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();

        contentPanel.add(new JLabel("Enter your character name."), gc);
        gc.gridy++;
        contentPanel.add(characterNameInput, gc);
        gc.gridy++;

        characterNameInput.getDocument().addDocumentListener(new TextChangeListener() {
            @Override
            public void onTextChange(DocumentEvent e) {
                validateNextButton();
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
