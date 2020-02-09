package com.slimtrade.gui.overlay;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.OverlaySaveFile;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.BasicMovableDialog;
import com.slimtrade.gui.enums.ExpandDirection;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.messaging.AbstractMessagePanel;
import com.slimtrade.gui.options.ISaveable;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

public class OverlayManager implements ISaveable, IColorable {

    private OverlayInfoDialog helpDialog;
    private BasicMovableDialog menubarDialog = new BasicMovableDialog(true);
    private BasicMovableDialog messageDialog = new BasicMovableDialog(true);
    private GridBagConstraints gc = new GridBagConstraints();

    private Color backgroundDefault = new Color(0.5f, 0.6f, 0.5f, 0.8f);
    private Color backgroundLocked = new Color(0.6f, 0.5f, 0.5f, 0.8f);

    private JLabel menubarLabel = new JLabel("Menu bar");
    private JLabel messageLabel = new JLabel("Message Panel");

    //Input
    private JComboBox menubarCombo;
    private JComboBox messageCombo;

    //Other
    JPanel menubarButtonDummy = new JPanel();
    private Color borderColor = ColorManager.PRIMARY;
    private Color borderColorLock = ColorManager.PRIMARY;
    private Color dummyButtonColor = ColorManager.HIGH_CONTRAST_2;

    private final int BORDER_SIZE = 2;

    private boolean messageScreenLock = true;
    private boolean menubarScreenLock = true;

    public OverlayManager() {
        helpDialog = new OverlayInfoDialog();
        menubarCombo = helpDialog.menubarCombo;
        messageCombo = helpDialog.messageCombo;

        // Sizes
        Border borderDefault = BorderFactory.createMatteBorder(BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, BORDER_SIZE, borderColor);

        menubarDialog.setSize(App.frameManager.menubar.getWidth(), App.frameManager.menubar.getHeight());
        messageDialog.setBorderOffset(BORDER_SIZE);
        messageDialog.setSize(AbstractMessagePanel.totalWidth, AbstractMessagePanel.totalHeight);
        menubarButtonDummy.setPreferredSize(new Dimension(App.frameManager.menubarToggle.getSize().width - BORDER_SIZE, App.frameManager.menubarToggle.getSize().height - BORDER_SIZE));

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
        menubarDialog.getContentPane().setLayout(FrameManager.gridbag);
        menubarDialog.getContentPane().add(menubarButtonDummy, gc);
        gc.anchor = GridBagConstraints.CENTER;

        // Message
        messageDialog.getContentPane().setLayout(FrameManager.gridbag);
        menubarDialog.getContentPane().add(menubarLabel, gc);
        messageDialog.getContentPane().add(messageLabel, gc);




        //Buttons
        menubarCombo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateMenubarButton(((MenubarButtonLocation) menubarCombo.getSelectedItem()));
            }
        });

        messageDialog.getContentPane().addMouseListener(new AdvancedMouseAdapter() {
            @Override
            public void click(MouseEvent e) {
                super.mouseClicked(e);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    messageScreenLock = !messageScreenLock;
                    messageDialog.setScreenLock(messageScreenLock);
//                    messageDialog.forceOntoScreen();
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
//                menubarDialog.setLocation(FrameManager.menubar.getX(), FrameManager.menubar.getY());
//                messageDialog.setLocation(FrameManager.messageManager.getAnchorPoint());
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

//				FrameManager.messageManager.updateLocation();
                FrameManager.messageManager.setAnchorPoint(messageDialog.getLocation());
                FrameManager.messageManager.setExpandDirection((ExpandDirection)messageCombo.getSelectedItem());
                FrameManager.messageManager.refreshPanelLocations();

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
        this.updateMenubarButton(((MenubarButtonLocation) menubarCombo.getSelectedItem()));
        helpDialog.updateColor();
        helpDialog.pack();

//        helpDialog.setSize(helpDialog.getPreferredSize());
        FrameManager.centerFrame(helpDialog);
        App.eventManager.addColorListener(this);
        updateColor();

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
        Color color = borderColor;
        if (menubarScreenLock) {
            color = borderColorLock;
        }
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
        updateMenubarButton(App.saveManager.overlaySaveFile.menubarButtonLocation);
        System.out.println("LOCCC:" + menubarDialog.getLocation());

    }

    @Override
    public void updateColor() {
        System.out.println("COL");
        menubarButtonDummy.setBackground(borderColor);
        menubarDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
        messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
        // Message Screen Lock
        if (messageScreenLock) {
//            messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColorLock, BORDER_SIZE));
//            messageLabel.setForeground(borderColorLock);
            messageDialog.setBackground(backgroundLocked);
        } else {
            messageDialog.setBackground(backgroundDefault);
//            messageDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
//            messageLabel.setForeground(borderColor);
        }
        // Menubar Screen Lock
        if (menubarScreenLock) {
//            menubarDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColorLock, BORDER_SIZE));
//            menubarLabel.setForeground(borderColorLock);
            menubarDialog.setBackground(backgroundLocked);
        } else {
//            menubarDialog.getRootPane().setBorder(BorderFactory.createLineBorder(borderColor, BORDER_SIZE));
//            menubarLabel.setForeground(borderColor);
            menubarDialog.setBackground(backgroundDefault);
        }
        menubarLabel.setForeground(borderColor);
        messageLabel.setForeground(borderColor);
        // Menubar Button

        updateMenubarButton((MenubarButtonLocation) menubarCombo.getSelectedItem());
//        menubarDialog.setBackground(new Color(1, 1, 1, 0.5f));
//        messageDialog.setBackground(new Color(1, 1, 1, 0.5f));
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

}
