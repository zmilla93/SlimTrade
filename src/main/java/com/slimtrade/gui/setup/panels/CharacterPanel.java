package com.slimtrade.gui.setup.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.slimtrade.gui.setup.SetupWindow;

public class CharacterPanel extends AbstractSetupPanel {

    private static final long serialVersionUID = 1L;

    private boolean complete;

    private JTextField inputText = new JTextField();

    public CharacterPanel(SetupWindow parent) {
        super(parent);
        JLabel characterLabel = new JLabel("Character Name");
        JLabel info1 = new JLabel("This is used to kick yourself from a party.");

        container.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.right = 40;
        gc.insets.bottom = 10;
        gc.fill = GridBagConstraints.BOTH;

        container.add(characterLabel, gc);
        gc.gridx++;
        gc.insets.right = 0;
        container.add(inputText, gc);
        gc.gridx = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.NONE;
        gc.insets.bottom = 0;

        gc.gridwidth = 2;
        container.add(info1, gc);

        inputText.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }

            public void insertUpdate(DocumentEvent e) {
                checkComplete();
            }

            public void removeUpdate(DocumentEvent e) {
                checkComplete();
            }
        });
    }

    private void checkComplete() {
        if (inputText.getText().replaceAll("\\s+", "").equals("")) {
            complete = false;
        } else {
            complete = true;
        }
        parent.updateButtonState();
    }

    @Override
    public boolean isComplete() {
        return complete;
    }

}
