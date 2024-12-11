package github.zmilla93.gui.components.slimtrade;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;

public class ApproveLabel extends JLabel {

    @Override
    public void updateUI() {
        super.updateUI();
        setForeground(ThemeManager.getCurrentExtensions().approve);
    }

}
