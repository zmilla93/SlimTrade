package com.slimtrade.gui.setup.panels;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.DocumentUpdateListener;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomLabel;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;

public class CharacterPanel extends  AbstractSetupPanel implements ISetupValidator, IColorable {

    private JPanel namePanel = new JPanel(FrameManager.gridBag);

    private JLabel info1 = new CustomLabel("Your character name is used to leave the party during certain trades.");
    private JLabel info2 = new CustomLabel("Be sure to update it when creating a new character.");
    private JLabel nameLabel = new CustomLabel("Character Name");

    private JTextField nameInput = new LimitTextField(32);

    public CharacterPanel() {

        // Name Panel
        gc.insets.right = 5;
        namePanel.add(nameLabel, gc);
        gc.insets.right = 0;
        gc.gridx++;
        gc.fill = GridBagConstraints.BOTH;
        gc.gridwidth = 1;
        gc.weightx = 1;
        namePanel.add(nameInput, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.weightx = 0;

        container.add(info1, gc);
        gc.gridy++;
        gc.insets.bottom = 10;
        container.add(info2, gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 0;
        container.add(namePanel, gc);

        nameInput.getDocument().addDocumentListener(new DocumentUpdateListener(){
            @Override
            public void update(){
                FrameManager.setupWindow.refreshButtons();
            }
        });

//        namePanel.setBackground(SetupWindow.BACKGROUND_COLOR);

    }

    @Override
    public boolean isValidInput() {
        if(nameInput.getText().matches("\\s*")) {
            return false;
        }
        return true;
    }

    @Override
    public void save() {
        App.saveManager.saveFile.characterName = nameInput.getText();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
        container.setBackground(ColorManager.LOW_CONTRAST_1);
        namePanel.setBackground(ColorManager.LOW_CONTRAST_1);
    }
}
