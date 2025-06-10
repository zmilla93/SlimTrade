package github.zmilla93.gui.setup;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.gui.components.poe.ThemeLabel;
import github.zmilla93.modules.theme.OLD_ThemeColor;

import javax.swing.*;

public class StashFolderSetupPanel extends AbstractSetupPanel {

    private final JRadioButton poe1YesRadioButton = new JRadioButton("Yes");
    private final JRadioButton poe2YesRadioButton = new JRadioButton("Yes");
    private final JRadioButton poe1NoRadioButton = new JRadioButton("No");
    private final JRadioButton poe2NoRadioButton = new JRadioButton("No");

    public StashFolderSetupPanel() {
        ButtonGroup poe1ButtonGroup = new ButtonGroup();
        poe1ButtonGroup.add(poe1YesRadioButton);
        poe1ButtonGroup.add(poe1NoRadioButton);

        ButtonGroup poe2ButtonGroup = new ButtonGroup();
        poe2ButtonGroup.add(poe2YesRadioButton);
        poe2ButtonGroup.add(poe2NoRadioButton);

        addHeader("Stash Tab Folders");
        addComponent(new JLabel("Do you use any stash tab folders? Doing so affects UI alignment."));
        addComponent(new ThemeLabel(OLD_ThemeColor.INDETERMINATE, "This settings needs to be updated manually if changed."));
        addVerticalStrut();
        addHeader(Game.PATH_OF_EXILE_1.explicitName);
        addComponent(poe1YesRadioButton);
        addComponent(poe1NoRadioButton);
        addVerticalStrut();
        addHeader(Game.PATH_OF_EXILE_2.toString());
        addComponent(poe2YesRadioButton);
        addComponent(poe2NoRadioButton);
    }

    @Override
    protected void addComponentListeners() {
        poe1YesRadioButton.addActionListener(e -> runSetupValidation());
        poe2YesRadioButton.addActionListener(e -> runSetupValidation());
        poe1NoRadioButton.addActionListener(e -> runSetupValidation());
        poe2NoRadioButton.addActionListener(e -> runSetupValidation());
    }

    @Override
    public void initializeComponents() {
        if (SaveManager.settingsSaveFile.data.hasInitUsingStashFolders) {
            if (SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder) poe1YesRadioButton.setSelected(true);
            else poe1NoRadioButton.setSelected(true);
            if (SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder) poe2YesRadioButton.setSelected(true);
            else poe2NoRadioButton.setSelected(true);
        }
    }

    @Override
    public boolean isSetupValid() {
        boolean poe1Valid = poe1YesRadioButton.isSelected() || poe1NoRadioButton.isSelected();
        boolean poe2Valid = poe2YesRadioButton.isSelected() || poe2NoRadioButton.isSelected();
        return poe1Valid && poe2Valid;
    }

    @Override
    public void applyCompletedSetup() {
        SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder = poe1YesRadioButton.isSelected();
        SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder = poe2YesRadioButton.isSelected();
        SaveManager.settingsSaveFile.data.hasInitUsingStashFolders = true;
    }

}
