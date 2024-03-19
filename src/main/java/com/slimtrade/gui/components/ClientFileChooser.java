package com.slimtrade.gui.components;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ClientFileChooser extends JFileChooser {

    public static String TITLE = "SlimTrade - Select Client.txt";

    public ClientFileChooser() {
        setDialogTitle(TITLE);
        setAcceptAllFileFilterUsed(false);
        setFileFilter(new FileNameExtensionFilter("Text Files", "txt", "text"));
    }

}
