package com.slimtrade.gui.setup;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.poe.GameDetectionMethod;

import javax.swing.*;

public class GameDetectionSetupPanel extends AbstractSetupPanel {

    private final JLabel info1 = new JLabel("SlimTrade needs to know where the Path of Exile game window is.");

    public GameDetectionSetupPanel(JButton nextButton) {
        super(nextButton);
    }

    @Override
    public boolean isSetupValid() {
        return SaveManager.settingsSaveFile.data.gameDetectionMethod != GameDetectionMethod.UNSET;
    }

}
