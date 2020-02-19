package com.slimtrade.gui.tutorial.panels;

import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.tutorial.TutorialWindow;

import javax.swing.*;
import java.awt.*;

public class AbstractTutorialPanel extends JPanel {

    protected GridBagConstraints gc = new GridBagConstraints();
    protected JPanel container = new JPanel(FrameManager.gridBag);
    private final int BUFFER = 20;

    public AbstractTutorialPanel() {
        super(FrameManager.gridBag);
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets = new Insets(BUFFER, BUFFER, BUFFER, BUFFER);
        this.add(container, gc);
        gc.insets = new Insets(0, 0, 0, 0);

        this.setBackground(TutorialWindow.BACKGROUND_COLOR);
        container.setBackground(TutorialWindow.BACKGROUND_COLOR);
    }

}
