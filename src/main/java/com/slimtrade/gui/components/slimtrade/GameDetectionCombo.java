package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.core.poe.GameDetectionMethod;

import javax.swing.*;

/**
 * JComboBox for displaying {@link GameDetectionMethod}s.
 */
public class GameDetectionCombo extends JComboBox<GameDetectionMethod> {

    public GameDetectionCombo() {
        for (int i = 1; i < GameDetectionMethod.values().length; i++) {
            addItem(GameDetectionMethod.values()[i]);
        }
    }

}
