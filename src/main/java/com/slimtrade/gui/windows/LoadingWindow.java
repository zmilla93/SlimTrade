package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.modules.theme.ThemeManager;
import com.slimtrade.modules.updater.data.AppInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class LoadingWindow extends BasicDialog {

    private static final int INSET_HORIZONTAL = 25;
    private static final int INSET_VERTICAL = 20;

    public LoadingWindow(AppInfo appInfo) {
        ignoreVisibilitySystem(true);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.insets = new Insets(INSET_VERTICAL, INSET_HORIZONTAL, INSET_VERTICAL, INSET_HORIZONTAL);
        JLabel loadingLabel = new StyledLabel("Loading " + appInfo.fullName + "...").bold();
        contentPanel.add(loadingLabel, gc);
        contentPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        pack();
        setLocationRelativeTo(null);
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFrame(this);
    }

}
