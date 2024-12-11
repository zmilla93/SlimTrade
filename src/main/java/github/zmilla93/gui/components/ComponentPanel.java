package github.zmilla93.gui.components;

import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.modules.updater.ZLogger;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * Lays out child components from left to right with the given inset in between each component.
 */
public class ComponentPanel extends JPanel {

    private static final int DEFAULT_HORIZONTAL_INSET = GUIReferences.SMALL_INSET;

    private final int gapBetweenComponents;
    private final GridBagConstraints gc = ZUtil.getGC();

    public ComponentPanel() {
        this(DEFAULT_HORIZONTAL_INSET);
    }

    public ComponentPanel(int gapBetweenComponents) {
        this(gapBetweenComponents, new JComponent[0]);
    }

    public ComponentPanel(JComponent... components) {
        this(DEFAULT_HORIZONTAL_INSET, components);
    }

    public ComponentPanel(int gapBetweenComponents, JComponent... components) {
        super(new GridBagLayout());
        this.gapBetweenComponents = gapBetweenComponents;
        for (JComponent component : components) add(component);
    }

    @Override
    public Component add(Component comp) {
        super.add(comp, gc);
        gc.gridx++;
        gc.insets.left = gapBetweenComponents;
        return comp;
    }

    private void incorrectAddMethod() {
        ZLogger.err("[" + getClass().getSimpleName() + "] Components should only be added using the default add(Component) function!");
        ZUtil.printCallingFunction(ComponentPanel.class);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        incorrectAddMethod();
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        incorrectAddMethod();
    }

    @Override
    public Component add(String name, Component comp) {
        incorrectAddMethod();
        return null;
    }

    @Override
    public Component add(Component comp, int index) {
        incorrectAddMethod();
        return null;
    }

}
