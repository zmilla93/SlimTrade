package com.slimtrade.gui.setup.panels;

import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractSetupPanel extends JPanel {

    protected GridBagConstraints gc = new GridBagConstraints();
    protected JPanel container = new JPanel(new GridBagLayout());
    private final int BUFFER = 20;

    public AbstractSetupPanel() {
        super(new GridBagLayout());
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(BUFFER, BUFFER, BUFFER, BUFFER);
        this.add(container, gc);
        gc.insets = new Insets(0, 0, 0, 0);
        container.setBackground(ColorManager.BACKGROUND);
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(ColorManager.BORDER_TEXT);
    }

}
