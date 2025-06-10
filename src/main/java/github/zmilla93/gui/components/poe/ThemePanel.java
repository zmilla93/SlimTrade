package github.zmilla93.gui.components.poe;

import github.zmilla93.modules.theme.OLD_ThemeColor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that sets its background using a {@link OLD_ThemeColor}.
 * Also applies a corresponding text color to all labels added to ensure legibility.
 */
public class ThemePanel extends JPanel {

    private final OLD_ThemeColor color;
    private boolean useHighContrastTextColor;

    public ThemePanel(OLD_ThemeColor color) {
        this(color, false);
    }

    public ThemePanel(OLD_ThemeColor color, boolean useHighContrastTextColor) {
        this.color = color;
        this.useHighContrastTextColor = useHighContrastTextColor;
        updateUI();
    }

    public ThemePanel(OLD_ThemeColor color, LayoutManager layoutManager) {
        super(layoutManager);
        this.color = color;
        updateUI();
    }

    public ThemePanel(OLD_ThemeColor color, LayoutManager layoutManager, boolean useHighContrastTextColor) {
        super(layoutManager);
        this.color = color;
        this.useHighContrastTextColor = useHighContrastTextColor;
        updateUI();
    }

    public void useHighContrastTextColor(boolean useHighContractTextColor) {
        this.useHighContrastTextColor = useHighContractTextColor;
    }

    private void applyHighContractTextColor(Component component) {
        if (!useHighContrastTextColor) return;
        if (!(component instanceof JLabel)) return;
        component.setForeground(color.getReadableTextColor());
    }

    @Override
    public Component add(Component comp) {
        applyHighContractTextColor(comp);
        return super.add(comp);
    }

    @Override
    public Component add(String name, Component comp) {
        applyHighContractTextColor(comp);
        return super.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        applyHighContractTextColor(comp);
        return super.add(comp, index);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        applyHighContractTextColor(comp);
        super.add(comp, constraints);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        applyHighContractTextColor(comp);
        super.add(comp, constraints, index);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (this.color == null) return;
        setBackground(color.current());
        for (Component comp : getComponents()) applyHighContractTextColor(comp);
    }

}
