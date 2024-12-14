package github.zmilla93.gui.windows.test;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * A simple class for drawing {@link Rectangle}s to the screen.
 */
public class DrawWindow extends JDialog {

    private static final int HIDE_FRAME_DELAY_MS = 2500;
    private static final int DISPOSE_FRAME_DELAY_MS = 10000;
    private static final int BORDER_SIZE = 1;
    private static final Color DEFAULT_BORDER_COLOR = new Color(225, 54, 54);
    private Color borderColor = DEFAULT_BORDER_COLOR;
    private static final HashMap<WindowId, DrawWindow> windowMap = new HashMap<>();

    private final WindowId id;
    private Timer hideFrameTimer;
    private Timer disposeFrameTimer;

    public enum WindowId {
        GAME_BOUNDS,
        GAME_BOUNDS_DEBUG,
        STASH_BOUNDS_POE_1,
        STASH_BOUNDS_POE_2,
    }

    private DrawWindow(WindowId id) {
        this.id = id;
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setBackground(new Color(0, 0, 0, 0));
        add(createBorderComponent());
        setupTimers();
    }

    public void setColor(Color color) {
        this.borderColor = color;
    }

    public static void draw(WindowId windowId, Rectangle rect) {
        draw(windowId, rect, Color.RED);
    }

    /**
     * Use this function draw a rectangle to the screen that represents a given window.
     */
    public static void draw(WindowId windowId, Rectangle rect, Color color) {
        if (SwingUtilities.isEventDispatchThread()) updateWindow(windowId, rect, color);
        else SwingUtilities.invokeLater(() -> updateWindow(windowId, rect, color));
    }

    private static void updateWindow(WindowId windowId, Rectangle rect, Color color) {
        assert SwingUtilities.isEventDispatchThread();
        DrawWindow window = windowMap.get(windowId);
        if (window == null) {
            window = new DrawWindow(windowId);
            windowMap.put(windowId, window);
        }
        window.setColor(color);
        window.draw(rect);
    }

    private void draw(Rectangle rect) {
        restartTimers();
        setBounds(rect);
        setVisible(true);
    }

    private JComponent createBorderComponent() {
        return new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(borderColor);
                g2d.setStroke(new BasicStroke(BORDER_SIZE));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
    }

    private void restartTimers() {
        hideFrameTimer.restart();
        disposeFrameTimer.restart();
    }

    private void setupTimers() {
        hideFrameTimer = new Timer(HIDE_FRAME_DELAY_MS, e -> {
            hideFrameTimer.stop();
            setVisible(false);
        });
        disposeFrameTimer = new Timer(DISPOSE_FRAME_DELAY_MS, e -> {
            disposeFrameTimer.stop();
            dispose();
            System.gc();
        });
    }

    @Override
    public void dispose() {
        windowMap.remove(id);
        setVisible(false);
        super.dispose();
    }

}
