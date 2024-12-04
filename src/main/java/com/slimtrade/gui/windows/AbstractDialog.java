package com.slimtrade.gui.windows;

import com.slimtrade.core.References;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

// FIXME : Switch to using BasicDialog instead.
@Deprecated
public abstract class AbstractDialog extends VisibilityDialog {

    protected final JPanel contentPanel = new JPanel();

    public AbstractDialog() {
        setTitle(References.APP_PREFIX);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
    }

}
