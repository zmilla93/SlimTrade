package github.zmilla93.modules.theme.components;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;

public class ThemeFrame extends JFrame {

    public ThemeFrame() {
        ThemeManager.addFrame(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFrame(this);
    }

}
