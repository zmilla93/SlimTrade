package github.zmilla93.gui.windows;

import github.zmilla93.App;
import github.zmilla93.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AbstractMovableDialog extends BasicDialog {

    private int mouseWindowX;
    private int mouseWindowY;
    private boolean mouseDown;
    private boolean screenLock;
    private final Runnable windowMover;
    int borderOffset = 0;

    private Rectangle screenBounds;

    private static final Executor executor = Executors.newSingleThreadExecutor();

    public AbstractMovableDialog() {
        super();
        // Set up the window mover
        windowMover = () -> {
            while (mouseDown) {
                // The window's target location before applying screen lock
                int targetX = MouseInfo.getPointerInfo().getLocation().x - mouseWindowX - borderOffset;
                int targetY = MouseInfo.getPointerInfo().getLocation().y - mouseWindowY - borderOffset;
                // If shift was pressed, start applying screen lock
                if (App.globalKeyboardListener.isShiftPressed() && !screenLock) {
                    screenBounds = ZUtil.getScreenBoundsFromPoint(MouseInfo.getPointerInfo().getLocation());
                    if (screenBounds != null) screenLock = true;
                }
                // If shift was released, disable screen lock
                if (!App.globalKeyboardListener.isShiftPressed() && screenLock || screenBounds == null)
                    screenLock = false;
                // If using screen lock, restrict the window's target location to within the screen bounds
                if (screenLock) {
                    if (targetX > screenBounds.x + screenBounds.width - getWidth() - borderOffset * 2)
                        targetX = screenBounds.x + screenBounds.width - getWidth() - borderOffset * 2;
                    if (targetX < screenBounds.x) targetX = screenBounds.x;
                    if (targetY > screenBounds.y + screenBounds.height - getHeight() - borderOffset * 2)
                        targetY = screenBounds.y + screenBounds.height - getHeight() - borderOffset * 2;
                    if (targetY < screenBounds.y) targetY = screenBounds.y;
                }
                // If the window's target location has changed, update the window's location
                Point targetLocation = new Point(targetX, targetY);
                if (!getLocation().equals(targetLocation)) {
                    SwingUtilities.invokeLater(() -> setLocation(targetLocation));
                }
            }
        };

        // Add a mouse listener to the glass pane to toggle window movement
        getGlassPane().setVisible(true);
        getGlassPane().addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Snapshot the mouse position in window space so it can be used as an offset when adjusting the window position
                    mouseWindowX = e.getX();
                    mouseWindowY = e.getY();
                    mouseDown = true;
                    executor.execute(windowMover);
                }
            }
        });
        getGlassPane().addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) mouseDown = false;
            }
        });
    }

}
