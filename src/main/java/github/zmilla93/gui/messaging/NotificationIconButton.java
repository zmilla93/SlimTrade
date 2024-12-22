package github.zmilla93.gui.messaging;

import github.zmilla93.gui.buttons.IIcon;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.components.AdvancedButton;

import javax.swing.*;

public class NotificationIconButton extends AdvancedButton {

    private static final int DEFAULT_INSET = NotificationButton.DEFAULT_INSET;
    protected String path;
    private int inset;

    public NotificationIconButton(IIcon icon) {
        super();
        this.path = icon.path();
        setIcon(ThemeManager.getColorIcon(path));
        updateUI();
    }

    public void setInset(int inset) {
        this.inset = inset;
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        int curInset = inset == 0 ? DEFAULT_INSET : inset;
        setBorder(BorderFactory.createEmptyBorder(curInset, curInset, curInset, curInset));
        if (path != null) {
            setIcon(ThemeManager.getColorIcon(path));
        }
    }

}
