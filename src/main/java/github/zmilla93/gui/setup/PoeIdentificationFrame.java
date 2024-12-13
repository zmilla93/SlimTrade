package github.zmilla93.gui.setup;

import javax.swing.*;
import java.awt.*;

// FIXME : Should inherit from DrawWindow
public class PoeIdentificationFrame extends JDialog {

    private static PoeIdentificationFrame frame;

    private static Timer hideFramesTimer;
    private static Timer disposeFramesTimer;
    private static final int BORDER_SIZE = 8;
    private static final Color BORDER_COLOR = new Color(225, 54, 54);

    static {
        setupTimers();
    }

    public PoeIdentificationFrame() {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setBackground(new Color(0, 0, 0, 0));
        add(createBorderComponent());
    }

    public static void identify(Rectangle rect) {
        assert SwingUtilities.isEventDispatchThread();
        restartTimers();
        if (frame == null) frame = new PoeIdentificationFrame();
        frame.setBounds(rect);
        frame.setVisible(true);
    }

    private static void setupTimers() {
        hideFramesTimer = new Timer(2500, e -> {
            hideFramesTimer.stop();
            frame.setVisible(false);
        });
        disposeFramesTimer = new Timer(10000, e -> {
            disposeFramesTimer.stop();
            frame.dispose();
            System.gc();
        });
    }

    private static void restartTimers() {
        hideFramesTimer.restart();
        disposeFramesTimer.restart();
    }

    private JComponent createBorderComponent() {
        return new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(BORDER_COLOR);
                g2d.setStroke(new BasicStroke(BORDER_SIZE));
                g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
            }
        };
    }

}
