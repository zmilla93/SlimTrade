package com.slimtrade.gui.components.slimtrade;

import javax.swing.*;

public class POEFileChooser extends JFileChooser {

    public static String TITLE = "SlimTrade - FIXME";

    public POEFileChooser() {
        setDialogTitle(TITLE);
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//        setAcceptAllFileFilterUsed(false);
//        setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));

    }

}
