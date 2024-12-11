package github.zmilla93.gui.buttons;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import github.zmilla93.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Base class for a button with resizable icons.
 * For solid colored icons that match the color theme, use IconButton instead.
 */
public class BasicIconButton extends JButton {

    protected String path;
    protected int size;

    public BasicIconButton(IIcon icon) {
        super();
        this.path = icon.path();
        updateUI();
    }

    public BasicIconButton(IIcon icon, int size) {
        super();
        this.path = icon.path();
        this.size = size;
        updateUI();
    }

    public void setIcon(IIcon icon) {
        this.path = icon.path();
        updateUI();
    }

    public void setIconSize(int size) {
        this.size = size;
        updateUI();
    }

    /**
     * Override this to modify how an icon is generated.
     *
     * @return icon
     */
    protected Icon createIcon() {
        return ThemeManager.getIcon(path, size);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (path != null)
            setIcon(createIcon());
        int borderInset = 3;
        setBorder(new FlatButtonBorder() {
            @Override
            public Insets getBorderInsets(Component c, Insets insets) {
                return new Insets(borderInset, borderInset, borderInset, borderInset);
            }
        });
    }

}
