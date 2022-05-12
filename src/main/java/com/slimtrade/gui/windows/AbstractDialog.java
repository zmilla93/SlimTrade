package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractDialog extends JDialog {

    protected Container container;

    public AbstractDialog() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        container = getContentPane();
        ColorManager.addFrame(this);
    }

}
