package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.core.poe.Game;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;

public class POEFolderPicker extends FilePicker implements PathChangeListener {

    private final Game game;
    public final JCheckBox notInstalledCheckbox = new JCheckBox("Not Installed");

    public POEFolderPicker(Game game) {
        super("Select the '" + game + "' install folder.");
        this.game = game;
        add(notInstalledCheckbox, BorderLayout.SOUTH);
        addListener(this);
    }

    @Override
    public void onPathChanged(Path path) {
        boolean valid = path.endsWith(game.toString());
        // FIXME : Hide on valid
        if (valid) setErrorText("Looks good!");
        else setErrorText("Path should end with '" + game + "'.");
    }

}
