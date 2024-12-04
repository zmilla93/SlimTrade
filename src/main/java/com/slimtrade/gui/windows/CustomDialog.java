package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.enums.DefaultIcon;
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
import com.slimtrade.modules.updater.ZLogger;

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
    protected static final int RESIZER_PANEL_SIZE = 8; // If a child needs this value, use getResizerSize()
    protected static final int BORDER_SIZE = 1;
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
    private final JPanel[] resizerPanels = new JPanel[]{resizerTop, resizerBottom, resizerLeft, resizerRight};

    private final JPanel titleBarPanel = new JPanel(new BorderLayout());
    private final JLabel titleLabel = new JLabel();
    private final JPanel contentBorderPanel;
    protected JPanel contentPanel = new JPanel();

    // Buttons
    protected final NotificationIconButton closeButton = new NotificationIconButton(DefaultIcon.CLOSE);
    protected final PinButton pinButton = new PinButton();

    // FIXME : Dialogs now always use thin mode, could remove
    public CustomDialog(String title) {
        this(title, true);
    }

    public CustomDialog(String title, boolean thin) {
        this(title, thin, true);
    }

    public CustomDialog(String title, boolean thin, boolean isDefaultPin) {
        setTitle(title);
        setMinimumSize(new Dimension(400, 400));
        setUndecorated(true);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        getRootPane().setOpaque(false);
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
//        int inset = thin ? 0 : TITLE_INSET;
        int inset = TITLE_INSET;
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

        // Content Border Panel - Used for setting border
        contentBorderPanel = new JPanel(new BorderLayout());
        contentBorderPanel.add(contentPanel, BorderLayout.CENTER);
        contentBorderPanel.setOpaque(false);

        // Panels
        JPanel innerPanel = new JPanel(new BorderLayout());
        innerPanel.add(titleBarPanel, BorderLayout.NORTH);
        innerPanel.add(contentBorderPanel, BorderLayout.CENTER);

        // Resizer Container Panel
        JPanel resizerContainer = new JPanel(new BorderLayout());
        resizerContainer.add(innerPanel, BorderLayout.CENTER);
        resizerContainer.add(resizerTop, BorderLayout.NORTH);
        resizerContainer.add(resizerBottom, BorderLayout.SOUTH);
        resizerContainer.add(resizerLeft, BorderLayout.WEST);
        resizerContainer.add(resizerRight, BorderLayout.EAST);
        for (JPanel panel : resizerPanels) panel.setOpaque(false);
        resizerContainer.setOpaque(false);
        innerPanel.setOpaque(false);

        // Container
        super.setContentPane(resizerContainer);
        setBackground(ThemeManager.TRANSPARENT);

        addWindowMover();
        addResizerListeners();
        colorBorders();
        addListeners();
        updateResizeVisibility();
        pack();
        setLocation(-RESIZER_PANEL_SIZE, -RESIZER_PANEL_SIZE);
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
                    if (pinRespectsSize) setResizable(!pinned);
                    else updateResizeVisibility();
                    PinManager.saveAllPins();
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
                clickedWindowPoint.x += getResizerSize();
                clickedWindowPoint.y += getResizerSize();
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

    // FIXME: Since panels always have resizers now, this is a bit redundant
    public int getResizerSize() {
        return RESIZER_PANEL_SIZE;
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

    @Override
    public void setResizable(boolean resizable) {
        super.setResizable(resizable);
        updateResizeVisibility();
    }

    protected void updateResizeVisibility() {
        for (JPanel panel : resizerPanels) {
            if (isResizable()) {
                panel.setOpaque(true);
                panel.setBackground(ThemeManager.TRANSPARENT_CLICKABLE);
            } else {
                panel.setOpaque(false);
                panel.setBackground(null);
            }
        }
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
    public void unpin() {
        pinned = false;
        pinButton.setPinned(false);
        if (pinRespectsSize) setResizable(!pinned);
        else updateResizeVisibility();
        PinManager.saveAllPins();
    }

    @Override
    public void applyPin(Rectangle rectangle) {
        setLocation(rectangle.getLocation());
        pinned = true;
        pinButton.setPinned(true);
        if (pinRespectsSize) {
            setSize(rectangle.getSize());
            setResizable(!pinned);
        } else updateResizeVisibility();
    }

    @Override
    public String getPinTitle() {
        return getCleanTitle();
    }

    @Override
    public Rectangle getPinRect() {
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
        ZLogger.err("The contentPane of a CustomDialog cannot be changed! Use the contentPanel variable or getContentPane() instead.");
    }

    @Override
    public void dispose() {
        ThemeManager.removeThemeListener(this);
        PinManager.removePinnable(this);
        super.dispose();
    }

}
