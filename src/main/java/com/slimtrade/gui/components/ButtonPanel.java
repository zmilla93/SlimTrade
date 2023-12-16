package com.slimtrade.gui.components;

import com.slimtrade.core.utility.ZUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

public class ButtonPanel extends JPanel {

    private final GridBagConstraints gc = ZUtil.getGC();

    private static final int HORIZONTAL_INSET = 5;

    public ButtonPanel() {
        super(new GridBagLayout());
    }

    @Override
    public Component add(Component comp) {
        super.add(comp, gc);
        gc.gridx++;
        gc.insets.left = HORIZONTAL_INSET;
        return comp;
    }

    private void incorrectAddMethod() {
        System.err.println("[ButtonPanel] Components should only be added using the default add(Component) function!");
        ZUtil.printCallingFunction(ButtonPanel.class);
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
