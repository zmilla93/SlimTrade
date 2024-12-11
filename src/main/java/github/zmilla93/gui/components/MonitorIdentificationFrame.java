package github.zmilla93.gui.components;

import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.windows.BasicDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;

public class MonitorIdentificationFrame extends BasicDialog {

    private static final ArrayList<MonitorIdentificationFrame> identificationFrames = new ArrayList<>();

    private static Timer hideFramesTimer;
    private static Timer disposeFramesTimer;

    private final JLabel label = new JLabel();

    static {
        setupTimers();
    }

    public MonitorIdentificationFrame() {
        assert SwingUtilities.isEventDispatchThread();
        int colorInt = 0;
        setBackground(new Color(colorInt, colorInt, colorInt, 200));
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int insetX = 120;
        int insetY = 60;
        gc.insets = new Insets(insetY, insetX, insetY, insetX);
        contentPanel.add(label, gc);
        Font font = label.getFont();
        label.setFont(font.deriveFont(font.getStyle(), 72));
    }

    public void setInfo(MonitorInfo info) {
        assert SwingUtilities.isEventDispatchThread();
        int windowOffset = 20;
        Point windowPos = info.bounds.getLocation();
        windowPos.x += windowOffset;
        windowPos.y += windowOffset;
        setLocation(windowPos);
        label.setText("Monitor " + info.id);
        pack();
    }

    private static void setupTimers() {
        hideFramesTimer = new Timer(2500, e -> {
            hideFramesTimer.stop();
            for (MonitorIdentificationFrame frame : identificationFrames)
                frame.setVisible(false);
        });
        disposeFramesTimer = new Timer(10000, e -> {
            disposeFramesTimer.stop();
            for (MonitorIdentificationFrame frame : identificationFrames)
                frame.dispose();
            identificationFrames.clear();
            System.gc();
        });
    }

    private static void restartTimers() {
        hideFramesTimer.restart();
        disposeFramesTimer.restart();
    }

    /**
     * Creates and displays an info frame on each monitor.
     * Hides all frames after 2.5 seconds, disposes all frames after 10 seconds of not being used.
     */
    public static ArrayList<MonitorInfo> visuallyIdentifyMonitors() {
        assert SwingUtilities.isEventDispatchThread();
        restartTimers();
        ArrayList<MonitorInfo> monitors = MonitorInfo.getAllMonitors();
        // Make sure enough frames exist
        while (identificationFrames.size() < monitors.size())
            identificationFrames.add(new MonitorIdentificationFrame());
        // Set info & show frames
        for (int i = 0; i < monitors.size(); i++) {
            MonitorInfo info = monitors.get(i);
            MonitorIdentificationFrame frame = identificationFrames.get(i);
            frame.setInfo(info);
            frame.setVisible(true);
        }
        return monitors;
    }

    @Override
    public void pack() {
        super.pack();
        int arcSize = 20;
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcSize, arcSize));
    }

    @Override
    public void dispose() {
        super.dispose();
    }

}
