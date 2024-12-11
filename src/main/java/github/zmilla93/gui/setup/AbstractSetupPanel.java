package github.zmilla93.gui.setup;

import github.zmilla93.gui.managers.FrameManager;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

public abstract class AbstractSetupPanel extends AbstractOptionPanel {

    public AbstractSetupPanel() {
        super(false, false);
    }

    /**
     * Called once after the component is created.
     */
    protected abstract void initializeComponents();

    /**
     * Called once after initializeComponents is called, in order to avoid premature setup checks
     */
    protected abstract void addComponentListeners();

    /**
     * Determine if the current panel contains completely valid information.
     */
    public abstract boolean isSetupValid();

    /**
     * Override this to apply the values that this panel is responsible for.
     */
    protected abstract void applyCompletedSetup();

    protected void runSetupValidation() {
        assert SwingUtilities.isEventDispatchThread();
//        if (!isVisible()) return;
//        System.err.println("Ran setup validation: " + getClass().getSimpleName());
//        System.out.println("Visible: " + isVisible());
//        ZUtil.printStackTrace();
        FrameManager.setupWindow.nextButton.setEnabled(isSetupValid());
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) runSetupValidation();
    }

}
