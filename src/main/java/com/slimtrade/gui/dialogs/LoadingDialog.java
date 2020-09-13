package com.slimtrade.gui.dialogs;

import com.slimtrade.core.References;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.custom.CustomLabel;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class LoadingDialog extends BasicDialog {

    public LoadingDialog() {

        setTitle(References.APP_NAME + " - Loading");
        setLayout(new GridBagLayout());
        getRootPane().setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        setSize(230, 90);

        Container container = this.getContentPane();
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        container.add(new CustomLabel("Loading " + References.APP_NAME + " v" + References.getAppVersion() + "..."), gc);

        FrameManager.centerFrame(this);
        setVisible(true);
    }

}
