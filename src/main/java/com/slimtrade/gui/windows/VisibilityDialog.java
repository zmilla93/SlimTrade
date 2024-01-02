package com.slimtrade.gui.windows;

import com.slimtrade.gui.managers.VisibilityManager;

import javax.swing.*;

public class VisibilityDialog extends JDialog {

    private boolean visible;

    public VisibilityDialog() {
        VisibilityManager.addFrame(this);
    }

    public void showOverlay() {
        setVisible(visible);
    }

    public void hideOverlay() {
        boolean vis = isVisible();
        setVisible(false);
        visible = vis;
    }

    @Override
    public void setVisible(boolean b) {
        super.setVisible(b);
        assert (SwingUtilities.isEventDispatchThread());
        visible = b;
    }

}
