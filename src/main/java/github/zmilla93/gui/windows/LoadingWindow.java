package github.zmilla93.gui.windows;

import github.zmilla93.core.poe.POEWindow;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.gui.listening.IDefaultSizeAndLocation;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.updater.data.AppInfo;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import java.awt.*;

public class LoadingWindow extends BasicDialog implements IDefaultSizeAndLocation {

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
        applyDefaultSizeAndLocation();
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFrame(this);
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        pack();
        POEWindow.centerWindow(this);
    }

}
