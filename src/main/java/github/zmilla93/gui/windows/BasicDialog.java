package github.zmilla93.gui.windows;

import github.zmilla93.core.References;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * An undecorated, always on top, dialog window.
 */
public class BasicDialog extends VisibilityDialog {

    private static final int DEFAULT_BUFFERED_BOUNDS_SIZE = 2;

    protected final JPanel contentPanel = new JPanel();
    private Rectangle bufferedBounds;
    private int bufferedBoundsSize = DEFAULT_BUFFERED_BOUNDS_SIZE;

    public BasicDialog() {
        setTitle(References.APP_PREFIX);
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        // FIXME: Could switch to JFrame and create a toggle to show task bar button.
//        setType(JDialog.Type.UTILITY);
        setContentPane(contentPanel);
    }

    /**
     * The same as getBounds(), but with a small buffer in each direction. Useful for
     * eliminating edge cases when comparing mouse position to window bounds.
     *
     * @return Buffered bounds
     */
    public Rectangle getBufferedBounds() {
        return bufferedBounds;
    }

    /**
     * Adjusts the size of the buffer for the bufferedBounds.
     *
     * @param size Size of buffer in pixels
     */
    public void setBufferedBoundsSize(int size) {
        bufferedBoundsSize = size;
        updateBufferedBounds();
    }

    protected void updateBufferedBounds() {
        Rectangle bounds = getBounds();
        bounds.x -= bufferedBoundsSize;
        bounds.y -= bufferedBoundsSize;
        bounds.width += bufferedBoundsSize * 2;
        bounds.height += bufferedBoundsSize * 2;
        bufferedBounds = bounds;
    }

    @Override
    public void pack() {
        assert SwingUtilities.isEventDispatchThread();
        super.pack();
        updateBufferedBounds();
    }

    @Override
    public void setLocation(int x, int y) {
        assert SwingUtilities.isEventDispatchThread();
        super.setLocation(x, y);
        updateBufferedBounds();
    }

    @Override
    public void setLocation(@NotNull Point p) {
        assert SwingUtilities.isEventDispatchThread();
        super.setLocation(p);
        updateBufferedBounds();
    }

    @Override
    public void setLocationRelativeTo(Component c) {
        assert SwingUtilities.isEventDispatchThread();
        super.setLocationRelativeTo(c);
        updateBufferedBounds();
    }

    @Override
    public void setMinimumSize(Dimension minimumSize) {
        super.setMinimumSize(minimumSize);
        updateBufferedBounds();
    }

    @Override
    public void setSize(Dimension d) {
        assert SwingUtilities.isEventDispatchThread();
        super.setSize(d);
        updateBufferedBounds();
    }

    @Override
    public void setSize(int width, int height) {
        assert SwingUtilities.isEventDispatchThread();
        super.setSize(width, height);
        updateBufferedBounds();
    }

    @Override
    public void setBounds(int x, int y, int width, int height) {
        assert SwingUtilities.isEventDispatchThread();
        super.setBounds(x, y, width, height);
        updateBufferedBounds();
    }

    @Override
    public void setBounds(Rectangle r) {
        assert SwingUtilities.isEventDispatchThread();
        super.setBounds(r);
        updateBufferedBounds();
    }

}
