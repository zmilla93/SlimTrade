package github.zmilla93.gui.components.poe;

import github.zmilla93.core.enums.ThemeColor;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * A panel that sets its background using a {@link ThemeColor}.
 * Also applies a corresponding text color to all labels added to ensure legibility.
 */
public class ThemePanel extends JPanel {

    private final ThemeColor color;

    public ThemePanel(ThemeColor color) {
        this.color = color;
        updateUI();
    }

    public ThemePanel(ThemeColor color, LayoutManager layoutManager) {
        super(layoutManager);
        this.color = color;
        updateUI();
    }

    private void applyLabelColor(Component component) {
        if (!(component instanceof JLabel)) return;
        component.setForeground(color.getReadableTextColor());
    }

    @Override
    public Component add(Component comp) {
        applyLabelColor(comp);
        return super.add(comp);
    }

    @Override
    public Component add(String name, Component comp) {
        applyLabelColor(comp);
        return super.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        applyLabelColor(comp);
        return super.add(comp, index);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        applyLabelColor(comp);
        super.add(comp, constraints);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        applyLabelColor(comp);
        super.add(comp, constraints, index);
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (this.color == null) return;
        setBackground(color.current());
        for (Component comp : getComponents()) applyLabelColor(comp);
    }

}
