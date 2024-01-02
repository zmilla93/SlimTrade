package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.IVisibilityFrame;
import com.slimtrade.gui.components.Visibility;
import com.slimtrade.gui.messaging.NotificationIconButton;
import com.slimtrade.gui.messaging.PinButton;
import com.slimtrade.gui.pinning.IPinnable;
import com.slimtrade.gui.pinning.PinManager;
import com.slimtrade.modules.theme.IThemeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

/**
 * A resizable window with a title, close button, and pin button.
 * Unlike a normal JDialog, content can be made transparent without affecting the title bar and border.
 * Add content to the contentPanel variable (or use getContentPane()).
 */
public abstract class CustomDialog extends VisibilityDialog implements IPinnable, IThemeListener, IVisibilityFrame {

    // Sizes
    private static final int RESIZER_PANEL_SIZE = 8;
    private static final int BORDER_SIZE = 1;
    private static final int TITLE_INSET = 4;

    // State
    private boolean pinned;
    private Visibility visibility;
    protected boolean pinRespectsSize = true;
    private String title;

    // Movement
    private Point startLocation;
    private Point clickedWindowPoint;
    private Dimension startSize;
    private int maxWidthAdjust;
    private int maxHeightAdjust;

    // Resizers
    private final JPanel resizerTop = new JPanel(new BorderLayout());
    private final JPanel resizerBottom = new JPanel(new BorderLayout());
    private final JPanel resizerLeft = new JPanel(new BorderLayout());
    private final JPanel resizerRight = new JPanel(new BorderLayout());

    // Panels
    private final JPanel titleBarPanel = new JPanel(new BorderLayout());
    private final JLabel titleLabel = new JLabel();
    private final JPanel contentBorderPanel;
    protected JPanel contentPanel = new JPanel();

    // Buttons
    protected final NotificationIconButton closeButton = new NotificationIconButton("/icons/default/closex64.png");
    protected final PinButton pinButton = new PinButton();

    public CustomDialog(String title) {
        this(title, false);
    }

    public CustomDialog(String title, boolean thin) {
        this(title, thin, true);
    }

    public CustomDialog(String title, boolean thin, boolean isDefaultPin) {
        setTitle(title);
        setMinimumSize(new Dimension(400, 400));
        setUndecorated(true);
        setAlwaysOnTop(true);
        getRootPane().setOpaque(false);
        ThemeManager.addFrame(this);
        ThemeManager.addThemeListener(this);
        if (isDefaultPin) PinManager.addAppWindow(this);

        // Title Bar
        if (!thin) {
            closeButton.setInset(TITLE_INSET);
            pinButton.setInset(TITLE_INSET);
        }
        closeButton.setFocusable(false);
        pinButton.setFocusable(false);
        closeButton.setAllowedMouseButtons(1);
        pinButton.setAllowedMouseButtons(1);
        JPanel titlePanel = new JPanel(new GridBagLayout());
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        int inset = thin ? 0 : TITLE_INSET;
        gc.insets = new Insets(inset, TITLE_INSET, inset, TITLE_INSET);
        titlePanel.add(titleLabel, gc);
        gc.gridx = 0;
        gc.insets = new Insets(0, 0, 0, 0);
        gc.fill = GridBagConstraints.VERTICAL;
        gc.weighty = 1;
        buttonPanel.add(pinButton, gc);
        gc.gridx++;
        buttonPanel.add(closeButton, gc);
        titleBarPanel.add(titlePanel, BorderLayout.WEST);
        titleBarPanel.add(buttonPanel, BorderLayout.EAST);

        // Resize Panels
        resizerTop.add(Box.createVerticalStrut(RESIZER_PANEL_SIZE), BorderLayout.CENTER);
        resizerBottom.add(Box.createVerticalStrut(RESIZER_PANEL_SIZE), BorderLayout.CENTER);
        resizerLeft.add(Box.createHorizontalStrut(RESIZER_PANEL_SIZE), BorderLayout.CENTER);
        resizerRight.add(Box.createHorizontalStrut(RESIZER_PANEL_SIZE), BorderLayout.CENTER);
        resizerTop.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
        resizerBottom.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
        resizerLeft.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
        resizerRight.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
        resizerTop.setBackground(ThemeManager.TRANSPARENT_CLICKABLE);
        resizerBottom.setBackground(ThemeManager.TRANSPARENT_CLICKABLE);
        resizerLeft.setBackground(ThemeManager.TRANSPARENT_CLICKABLE);
        resizerRight.setBackground(ThemeManager.TRANSPARENT_CLICKABLE);

        // Inner Panel
        contentBorderPanel = new JPanel(new BorderLayout());
        contentBorderPanel.add(contentPanel, BorderLayout.CENTER);
        contentBorderPanel.setOpaque(false);
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(titleBarPanel, BorderLayout.NORTH);
        innerPanel.add(contentBorderPanel, BorderLayout.CENTER);
        innerPanel.setBackground(Color.ORANGE);

        // Outer Panel
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        outerPanel.add(resizerTop, BorderLayout.NORTH);
        outerPanel.add(resizerBottom, BorderLayout.SOUTH);
        outerPanel.add(resizerLeft, BorderLayout.WEST);
        outerPanel.add(resizerRight, BorderLayout.EAST);
        outerPanel.setOpaque(false);
        innerPanel.setOpaque(false);

        // Container
        super.setContentPane(outerPanel);
        setBackground(ThemeManager.TRANSPARENT);

        addWindowMover();
        addResizerListeners();
        colorBorders();
        addListeners();
    }

    private void addListeners() {
        closeButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    if (getDefaultCloseOperation() == EXIT_ON_CLOSE) {
                        System.exit(0);
                    } else {
                        setVisible(false);
                    }
                }
            }
        });
        pinButton.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    pinned = !pinned;
                    pinButton.setPinned(pinned);
                    PinManager.save();
                    SaveManager.pinSaveFile.saveToDisk();
                }
            }
        });
    }

    private void addWindowMover() {
        titleBarPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPinned()) return;
                super.mousePressed(e);
                startLocation = getLocation();
                clickedWindowPoint = e.getPoint();
                if (isResizable()) {
                    clickedWindowPoint.x += getResizerSize();
                    clickedWindowPoint.y += getResizerSize();
                }
                if (getMinimumSize().width == 0) maxWidthAdjust = -1;
                else maxWidthAdjust = getSize().width - getMinimumSize().width;
            }
        });
        titleBarPanel.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isPinned()) return;
                super.mouseDragged(e);
                Point targetPoint = MouseInfo.getPointerInfo().getLocation();
                targetPoint.x -= clickedWindowPoint.x;
                targetPoint.y -= clickedWindowPoint.y;

                Rectangle screenBounds = ZUtil.getScreenBoundsFromPoint(MouseInfo.getPointerInfo().getLocation());
                if (screenBounds != null && App.globalKeyboardListener.isShiftPressed()) {
                    if (targetPoint.x > screenBounds.x + screenBounds.width - getWidth() + getResizerSize())
                        targetPoint.x = screenBounds.x + screenBounds.width - getWidth() + getResizerSize();
                    if (targetPoint.x < screenBounds.x)
                        targetPoint.x = screenBounds.x - getResizerSize();
                    if (targetPoint.y > screenBounds.y + screenBounds.height - getHeight() + getResizerSize())
                        targetPoint.y = screenBounds.y + screenBounds.height - getHeight() + getResizerSize();
                    if (targetPoint.y < screenBounds.y)
                        targetPoint.y = screenBounds.y - getResizerSize();
                }
                setLocation(targetPoint);
            }
        });
    }

    private void addResizerListeners() {
        addResizeClickListener(resizerTop);
        addResizeClickListener(resizerBottom);
        addResizeClickListener(resizerLeft);
        addResizeClickListener(resizerRight);
        // Top
        resizerTop.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isPinned()) return;
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                int sizeAdjust = p.y - clickedWindowPoint.y;
                if (maxHeightAdjust != -1 && sizeAdjust > maxHeightAdjust) sizeAdjust = maxHeightAdjust;
                setLocation(startLocation.x, startLocation.y + sizeAdjust);
                setSize(new Dimension(startSize.width, startSize.height - sizeAdjust));
            }
        });
        // Bottom
        resizerBottom.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isPinned()) return;
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                int sizeAdjust = p.y - clickedWindowPoint.y;
                setSize(new Dimension(startSize.width, startSize.height + sizeAdjust));
            }
        });
        // Left
        resizerLeft.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isPinned()) return;
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                int widthAdjust = clickedWindowPoint.x - p.x;
                if (widthAdjust < -maxWidthAdjust) widthAdjust = -maxWidthAdjust;
                setLocation(startLocation.x - widthAdjust, startLocation.y);
                setSize(new Dimension(startSize.width + widthAdjust, startSize.height));
            }
        });
        // Right
        resizerRight.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isPinned()) return;
                super.mouseDragged(e);
                Point p = MouseInfo.getPointerInfo().getLocation();
                int widthAdjust = clickedWindowPoint.x - p.x;
                setSize(new Dimension(startSize.width - widthAdjust, startSize.height));
            }
        });
    }

    private void addResizeClickListener(JPanel panel) {
        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isPinned()) return;
                super.mousePressed(e);
                clickedWindowPoint = MouseInfo.getPointerInfo().getLocation();
                startSize = getSize();
                startLocation = getLocation();
                if (startSize.width == 0) maxWidthAdjust = -1;
                else maxWidthAdjust = startSize.width - getMinimumSize().width;
                if (startSize.height == 0) maxHeightAdjust = -1;
                else maxHeightAdjust = startSize.height - getMinimumSize().height;
            }
        });
    }

    public int getTitleBarHeight() {
        return titleBarPanel.getHeight();
    }

    public int getBorderSize() {
        return BORDER_SIZE;
    }

    private void colorBorders() {
        titleBarPanel.setBorder(BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, BORDER_SIZE, UIManager.getColor("Separator.foreground")));
        contentBorderPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground"), BORDER_SIZE));
    }

    protected int getResizerSize() {
        if (isResizable()) return RESIZER_PANEL_SIZE;
        return 0;
    }

    // Overrides

    @Override
    public void setTitle(String title) {
        super.setTitle(References.APP_PREFIX + title);
        this.title = title;
        titleLabel.setText(title);
    }

    public String getCleanTitle() {
        return this.title;
    }

    // FIXME :  This can't be called after the window is created without causing the size of the window to change.
    //          The window size and location should be recalculated, or the resizers should remain visible but set to
    //          be click through. Fixing this would clean up the way pinnables work.
    @Override
    public void setResizable(boolean state) {
        super.setResizable(state);
        resizerTop.setVisible(state);
        resizerLeft.setVisible(state);
        resizerBottom.setVisible(state);
        resizerRight.setVisible(state);
    }

    // Interfaces

    @Override
    public void onThemeChange() {
        colorBorders();
    }

    @Override
    public boolean isPinned() {
        return pinned;
    }

    @Override
    public void applyPin(Rectangle rectangle) {
        setLocation(rectangle.getLocation());
        if (pinRespectsSize) setSize(rectangle.getSize());
        pinned = true;
        pinButton.setPinned(true);
    }

    @Override
    public String getPinTitle() {
        return getCleanTitle();
    }

    @Override
    public Rectangle getPinRectangle() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    @Override
    public void showOverlay() {
        if (visibility == Visibility.SHOW) setVisible(true);
        visibility = Visibility.UNSET;
    }

    @Override
    public void hideOverlay() {
        visibility = isVisible() ? Visibility.SHOW : Visibility.HIDE;
        setVisible(false);
    }

    @Override
    public Container getContentPane() {
        return contentPanel;
    }

    @Override
    public void setContentPane(Container contentPane) {
        System.err.println("The contentPane of a CustomDialog cannot be changed! Use the contentPanel variable or getContentPane() instead.");
    }

    @Override
    public void dispose() {
        super.dispose();
        PinManager.removePinnable(this);
    }
    
}
