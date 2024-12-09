package com.slimtrade.gui.components.slimtrade;

import javax.swing.*;
import java.awt.*;

public class POEFolderPicker extends FilePickerComponent {

    public final JCheckBox notInstalledCheckbox = new JCheckBox("Not Installed");

    public POEFolderPicker() {
        add(notInstalledCheckbox, BorderLayout.SOUTH);
    }

}
