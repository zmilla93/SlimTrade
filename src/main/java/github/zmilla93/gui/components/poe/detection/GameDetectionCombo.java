package github.zmilla93.gui.components.poe.detection;

import github.zmilla93.core.poe.GameWindowMode;

import javax.swing.*;

/**
 * JComboBox for displaying {@link GameWindowMode}s.
 */
public class GameDetectionCombo extends JComboBox<GameWindowMode> {

    public GameDetectionCombo() {
        for (int i = 1; i < GameWindowMode.values().length; i++) {
            addItem(GameWindowMode.values()[i]);
        }
    }

}
