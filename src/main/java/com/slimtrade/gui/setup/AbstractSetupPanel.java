package com.slimtrade.gui.setup;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.managers.FrameManager;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractSetupPanel extends JPanel {

    public static final int INSET_SIZE = 20;

    protected JPanel contentPanel = new JPanel(new BorderLayout());

    public AbstractSetupPanel() {
        setLayout(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(this, INSET_SIZE);
        add(contentPanel, BorderLayout.CENTER);
    }

    /**
     * Determine if the current panel contains completely valid information.
     */
    public abstract boolean isSetupValid();

    /**
     * Override this to apply the values that this panel is responsible for.
     */
    public abstract void applyCompletedSetup();

    protected void runSetupValidation() {
        assert SwingUtilities.isEventDispatchThread();
        FrameManager.setupWindow.nextButton.setEnabled(isSetupValid());
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) runSetupValidation();
    }

}
