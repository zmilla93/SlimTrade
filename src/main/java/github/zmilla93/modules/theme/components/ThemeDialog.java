package github.zmilla93.modules.theme.components;

import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;

public class ThemeDialog extends JDialog {

    public ThemeDialog(){
        ThemeManager.addFrame(this);
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFrame(this);
    }

}
