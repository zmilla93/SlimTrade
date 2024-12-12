package github.zmilla93.gui.components.poe;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;

public class DenyLabel extends JLabel {

    @Override
    public void updateUI() {
        super.updateUI();
        setForeground(ThemeManager.getCurrentExtensions().deny);
    }

}
