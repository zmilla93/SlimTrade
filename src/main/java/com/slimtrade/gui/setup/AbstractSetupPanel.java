package com.slimtrade.gui.setup;

import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.AbstractOptionPanel;

import javax.swing.*;

public abstract class AbstractSetupPanel extends AbstractOptionPanel {

    public AbstractSetupPanel() {
        super(false, false);
    }

    /**
     * Called once after the component is created.
     */
    public abstract void initializeComponents();

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
