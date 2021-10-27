package com.slimtrade;

import com.slimtrade.core.managers.FontManager;
import com.slimtrade.gui.dialogs.testing.FontTestWindow;

import javax.swing.*;

public class Tester {

    public static void main(String[] args) {

        FontManager.loadFonts();

        SwingUtilities.invokeLater(() -> {
            JFrame fontTestWindow = new FontTestWindow();
            fontTestWindow.setVisible(true);
        });


    }

}
