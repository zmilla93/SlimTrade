package github.zmilla93.core.utility;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public abstract class AdvancedMouseListener extends MouseAdapter {
    private MouseHoverState hoverState;

    public enum MouseHoverState {INBOUNDS, OUT_OF_BOUNDS}

    public void mousePressed(MouseEvent e) {
        hoverState = MouseHoverState.INBOUNDS;
    }

    public void mouseReleased(MouseEvent e) {
        if (hoverState == MouseHoverState.INBOUNDS) {
            click(e);
        }
    }

    public void mouseEntered(MouseEvent e) {
        hoverState = MouseHoverState.INBOUNDS;
    }

    public void mouseExited(MouseEvent e) {
        hoverState = MouseHoverState.OUT_OF_BOUNDS;
    }

    public abstract void click(MouseEvent e);

}
