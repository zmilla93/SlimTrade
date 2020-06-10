package com.slimtrade.gui.overlay;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.OverlaySaveFile;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicMovableDialog;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.messaging.AbstractMessagePanel;
import com.slimtrade.gui.options.ISaveable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Objects;

public class OverlayManager implements ISaveable, IColorable {

    private OverlayInfoDialog helpDialog;
    private BasicMovableDialog menubarDialog = new BasicMovableDialog(true);
    private BasicMovableDialog messageDialog = new BasicMovableDialog(true);
    private GridBagConstraints gc = new GridBagConstraints();

    private Color backgroundDefault = new Color(0.5f, 0.6f, 0.5f, 0.8f);
    private Color backgroundLocked = new Color(0.6f, 0.5f, 0.5f, 0.8f);

    private JLabel menubarLabel = new CustomLabel("Menubar");
    private JLabel messageLabel = new CustomLabel("Message Panel");

    //Input
    private JComboBox<MenubarButtonLocation> menubarCombo;
    private JComboBox<ExpandDirection> messageCombo;

    //Other
    JPanel menubarButtonDummy = new JPanel();
    private Color borderColor = ColorManager.OVERLAY_EDIT_TEXT;
    private Color borderColorLock = ColorManager.OVERLAY_EDIT_TEXT;

    private final int BORDER_SIZE = 2;

    private boolean messageScreenLock = true;
    private boolean menubarScreenLock = true;

    public OverlayManager() {
        helpDialog = new OverlayInfoDialog();
        menubarCombo = helpDialog.menubarCombo;
        messageCombo = helpDialog.messageCombo;

        // Sizes
        menubarDialog.setSize(FrameManager.menubar.getWidth(), FrameManager.menubar.getHeight());
        messageDialog.setSize(AbstractMessagePanel.totalWidth, AbstractMessagePanel.totalHeight);
        menubarButtonDummy.setPreferredSize(new Dimension(FrameManager.menubarToggle.getSize().width - BORDER_SIZE, FrameManager.menubarToggle.getSize().height - BORDER_SIZE));

        // Add Elements
        for (MenubarButtonLocation b : MenubarButtonLocation.values()) {
            menubarCombo.addItem(b);
        }
        for (ExpandDirection d : ExpandDirection.values()) {
            messageCombo.addItem(d);
        }

        // Build Dialog
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;

        // MenuBar
        gc.anchor = GridBagConstraints.SOUTHEAST;
        menubarDialog.getContentPane().setLayout(new GridBagLayout());
        menubarDialog.getContentPane().add(menubarButtonDummy, gc);
        gc.anchor = GridBagConstraints.CENTER;

        // Message
        messageDialog.getContentPane().setLayout(new GridBagLayout());
        menubarDialog.getContentPane().add(menubarLabel, gc);
        messageDialog.getContentPane().add(messageLabel, gc);
        helpDialog.registerMessageDialog(messageDialog);

        //Buttons
        menubarCombo.addActionListener(e -> {
            updateMenubarButton(((MenubarButtonLocation) Objects.requireNonNull(menubarCombo.getSelectedItem())));
        });

        messageDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
            @Override
            public void click(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    messageScreenLock = !messageScreenLock;
                    messageDialog.setScreenLock(messageScreenLock);
                    updateColor();
                }
            }
        });

        menubarDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
            @Override
            public void click(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    menubarScreenLock = !menubarScreenLock;
                    menubarDialog.setScreenLock(menubarScreenLock);
                    updateColor();
                }
            }
        });

        // CANCEL BUTTON
        helpDialog.cancelButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                FrameManager.windowState = WindowState.NORMAL;
                load();
                updateMenubarButton(App.saveManager.overlaySaveFile.menubarButtonLocation);
                hideAll();
                FrameManager.showVisibleFrames();
                FrameManager.showOptionsWindow();
            }
        });

        // SAVE BUTTON
        helpDialog.saveButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                FrameManager.windowState = WindowState.NORMAL;
                save();
                // Update UI
                FrameManager.menubar.setLocation(menubarDialog.getLocation());
                FrameManager.menubarToggle.updateLocation();
                FrameManager.menubar.reorder();

                FrameManager.messageManager.setMessageIncrease(helpDialog.messageSizeSlider.getValue() * 2);
                FrameManager.messageManager.setAnchorPoint(messageDialog.getLocation());
                FrameManager.messageManager.refreshPanelLocations();
                FrameManager.optionsWindow.macroPanelIncoming.resizeMessage();
                FrameManager.optionsWindow.macroPanelOutgoing.resizeMessage();
                FrameManager.chatScannerWindow.resizeMessage();

                hideAll();
                FrameManager.showVisibleFrames();
                FrameManager.showOptionsWindow();
            }
        });

        helpDialog.defaultsButton.addActionListener(e -> {
            this.resetToDefault();
        });

        //Finish
        load();
        this.updateMenubarButton(((MenubarButtonLocation) Objects.requireNonNull(menubarCombo.getSelectedItem())));
        helpDialog.updateColor();
        helpDialog.pack();
        FrameManager.centerFrame(helpDialog);
    }

    public void showAll() {
        helpDialog.setVisible(true);
        menubarDialog.setVisible(true);
        messageDialog.setVisible(true);
        this.allToFront();
    }

    public void hideAll() {
        helpDialog.setVisible(false);
        menubarDialog.setVisible(false);
        messageDialog.setVisible(false);
    }

    public void allToFront() {
        helpDialog.setAlwaysOnTop(false);
        menubarDialog.setAlwaysOnTop(false);
        messageDialog.setAlwaysOnTop(false);

        helpDialog.setAlwaysOnTop(true);
        menubarDialog.setAlwaysOnTop(true);
        messageDialog.setAlwaysOnTop(true);
    }

    private void updateMenubarButton(MenubarButtonLocation location) {
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        switch (location) {
            case NW:
                gc.anchor = GridBagConstraints.NORTHWEST;
                menubarButtonDummy.setBorder(BorderFactory.createMatteBorder(0, 0, BORDER_SIZE, BORDER_SIZE, borderColor));
                menubarDialog.getContentPane().add(menubarButtonDummy, gc);
                break;
            case NE:
                gc.anchor = GridBagConstraints.NORTHEAST;
                menubarButtonDummy.setBorder(BorderFactory.createMatteBorder(0, BORDER_SIZE, BORDER_SIZE, 0, borderColor));
                menubarDialog.getContentPane().add(menubarButtonDummy, gc);
                break;
            case SW:
                gc.anchor = GridBagConstraints.SOUTHWEST;
                menubarButtonDummy.setBorder(BorderFactory.createMatteBorder(BORDER_SIZE, 0, 0, BORDER_SIZE, borderColor));
                menubarDialog.getContentPane().add(menubarButtonDummy, gc);
                break;
            case SE:
                gc.anchor = GridBagConstraints.SOUTHEAST;
                menubarButtonDummy.setBorder(BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, 0, 0, borderColor));
                menubarDialog.getContentPane().add(menubarButtonDummy, gc);
                break;
        }
    }

    public void resetToDefault() {
        OverlaySaveFile defaults = new OverlaySaveFile();
        menubarDialog.setLocation(defaults.menubarX, defaults.menubarY);
        messageDialog.setLocation(defaults.messageX, defaults.messageY);
        menubarCombo.setSelectedItem(defaults.menubarButtonLocation);
        messageCombo.setSelectedItem(defaults.messageExpandDirection);
        this.menubarScreenLock = defaults.menubarScreenLock;
        this.messageScreenLock = defaults.messageScreenLock;
        updateMenubarButton(defaults.menubarButtonLocation);
    }

    // TODO : Switch to recursive coloring
    @Override
    public void updateColor() {
        ColorManager.recursiveColor(helpDialog);
        menubarButtonDummy.setBackground(borderColor);
        menubarDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
        messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
        // Message Screen Lock
        if (messageScreenLock) {
            messageDialog.setBackground(backgroundLocked);
        } else {
            messageDialog.setBackground(backgroundDefault);
        }
        // Menubar Screen Lock
        if (menubarScreenLock) {
            menubarDialog.setBackground(backgroundLocked);
        } else {
            menubarDialog.setBackground(backgroundDefault);
        }
        menubarLabel.setForeground(borderColor);
        messageLabel.setForeground(borderColor);
        // Menubar Button

        updateMenubarButton((MenubarButtonLocation) menubarCombo.getSelectedItem());
    }

    @Override
    public void save() {
        App.saveManager.overlaySaveFile.menubarScreenLock = menubarScreenLock;
        App.saveManager.overlaySaveFile.messageScreenLock = messageScreenLock;
        App.saveManager.overlaySaveFile.menubarButtonLocation = (MenubarButtonLocation) menubarCombo.getSelectedItem();
        App.saveManager.overlaySaveFile.messageExpandDirection = (ExpandDirection) messageCombo.getSelectedItem();
        App.saveManager.overlaySaveFile.menubarX = menubarDialog.getX();
        App.saveManager.overlaySaveFile.menubarY = menubarDialog.getY();
        App.saveManager.overlaySaveFile.menubarWidth = menubarDialog.getWidth();
        App.saveManager.overlaySaveFile.menubarHeight = menubarDialog.getHeight();
        App.saveManager.overlaySaveFile.messageX = messageDialog.getX();
        App.saveManager.overlaySaveFile.messageY = messageDialog.getY();
        App.saveManager.overlaySaveFile.messageSizeIncrease = helpDialog.messageSizeSlider.getValue() * 2;
        App.saveManager.saveOverlayToDisk();
    }

    @Override
    public void load() {
        this.menubarScreenLock = App.saveManager.overlaySaveFile.menubarScreenLock;
        this.menubarDialog.setScreenLock(menubarScreenLock);
        this.messageScreenLock = App.saveManager.overlaySaveFile.messageScreenLock;
        this.messageDialog.setScreenLock(messageScreenLock);
        menubarDialog.setLocation(App.saveManager.overlaySaveFile.menubarX, App.saveManager.overlaySaveFile.menubarY);
        messageDialog.setLocation(App.saveManager.overlaySaveFile.messageX, App.saveManager.overlaySaveFile.messageY);
        menubarCombo.setSelectedItem(App.saveManager.overlaySaveFile.menubarButtonLocation);
        messageCombo.setSelectedItem(App.saveManager.overlaySaveFile.messageExpandDirection);
        helpDialog.messageSizeSlider.setValue(App.saveManager.overlaySaveFile.messageSizeIncrease / 2);
        updateMenubarButton(App.saveManager.overlaySaveFile.menubarButtonLocation);
    }

}
