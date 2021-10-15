package com.slimtrade.gui.menubar;

import com.slimtrade.App;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class MenubarDialog extends BasicDialog {

//    private static int buttonCount = 4;
//    private static int spacerCount = 1;
//    private static int spacerHeight = (int) (MenubarButton.HEIGHT * 0.3);

//    public static final int WIDTH = MenubarButton.WIDTH;
//    public static final int HEIGHT = (MenubarButton.HEIGHT * buttonCount) + (spacerHeight * spacerCount);

    private MenubarButton historyButton;
    private MenubarButton chatScannerButton;
    private MenubarButton testButton;
    private MenubarButton optionsButton;
    private MenubarButton quitButton;

    private ArrayList<JButton> buttons = new ArrayList<>();

    //    private Component[] componentList;
    private Container container = this.getContentPane();

    public MenubarDialog() {
        assert(SwingUtilities.isEventDispatchThread());
        // TODO : Modify constructor of menubar buttons

//        this.setBounds(0, TradeUtility.screenSize.height - HEIGHT, WIDTH, HEIGHT);
        container.setLayout(new GridBagLayout());

        // TODO : Update Locale
        historyButton = new MenubarButton("");
        chatScannerButton = new MenubarButton("Chat Scanner");
        testButton = new MenubarButton("");
        optionsButton = new MenubarButton("");
        quitButton = new MenubarButton("");

        // Anything added to buttons appears on the Menubar
        buttons.add(historyButton);
        buttons.add(chatScannerButton);
        buttons.add(optionsButton);
        // Test and Quit buttons are added automatically


        // History
        historyButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                if (!FrameManager.historyWindow.isVisible()) {
                    FrameManager.historyWindow.setShow(true);
                }
                FrameManager.historyWindow.toFront();
            }
        });

        // Chat Scanner
        chatScannerButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                if (!FrameManager.chatScannerWindow.isVisible()) {
//                    FrameManager.hideMenuFrames();
                    FrameManager.chatScannerWindow.setShow(true);
                }
                FrameManager.chatScannerWindow.toFront();
            }
        });

        // TEST BUTTON
        testButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                Random rng = new Random();
                TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "IncomingTrader123", "Item Name", 1, "chaos", 60, "sale", 1, 1, "", "");
                TradeOffer t2 = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "HighlightTrader123", "Item Name", 1, "chaos", 60.5, "sale", 1, 1, "", "");
                TradeOffer t3 = new TradeOffer("", "", MessageType.OUTGOING_TRADE, "<GLD>", "OutgoingTrader456", "Item Name", 1, "chaos", 5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
                TradeOffer t5 = new TradeOffer("", "", MessageType.CHAT_SCANNER, "<GLD>", "ScannerTrader789", "Search Name", "Chat message text. Lorem ipsum dolor sit amet, consectetur adipiscing elit");
                FrameManager.messageManager.addMessage(t, false);
                FrameManager.messageManager.addMessage(t2, false);
                FrameManager.messageManager.addMessage(t3, false);

                FrameManager.messageManager.addMessage(new TradeOffer(MessageType.NOTIFICATION, "Update Available!", "Check the options menu to install."));
            }
        });

        // Options
        optionsButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                System.out.println("OptionsClick:" + SwingUtilities.isEventDispatchThread());
                if (!FrameManager.optionsWindow.isVisible()) {
//                    FrameManager.hideMenuFrames();
                    FrameManager.optionsWindow.setShow(true);
                    FrameManager.optionsWindow.toFront();
                }
            }
        });

        // Quit Program
        quitButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                System.exit(0);
            }
        });

        // Listeners
        addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                Rectangle bounds = FrameManager.menubar.getBounds();
                if (!bounds.contains(MouseInfo.getPointerInfo().getLocation())) {
                    FrameManager.menubar.setShow(false);
                    if (App.saveManager.settingsSaveFile.enableMenubar) {
                        revalidate();
                        FrameManager.menubarToggle.repaint();
                        FrameManager.menubarToggle.setShow(true);
                    }
                }
            }
        });
        refreshButtonText();
    }

    private void refreshButtonText() {
        assert(SwingUtilities.isEventDispatchThread());
        optionsButton.setText("Options");
        historyButton.setText("History");
        testButton.setText("Test");
        quitButton.setText("Quit");
    }

    public void updateLocation() {
        assert(SwingUtilities.isEventDispatchThread());
        this.setLocation(App.saveManager.overlaySaveFile.menubarX, App.saveManager.overlaySaveFile.menubarY);
        if (FrameManager.menubarToggle != null) {
            FrameManager.menubarToggle.updateLocation();
        }
    }

    // If the MenuBar changes sizes, the anchor point is shifted based on the location
    // of the expand button to ensure that the expand button never moves off screen.
    public void init() {
        assert(SwingUtilities.isEventDispatchThread());
        this.reorder();
        boolean hasChanges = false;
        if (this.getWidth() != App.saveManager.overlaySaveFile.menubarWidth) {
            if (App.saveManager.overlaySaveFile.menubarButtonLocation == MenubarButtonLocation.NE || App.saveManager.overlaySaveFile.menubarButtonLocation == MenubarButtonLocation.SE) {
                App.saveManager.overlaySaveFile.menubarX += App.saveManager.overlaySaveFile.menubarWidth - this.getWidth();
            }
            App.saveManager.overlaySaveFile.menubarWidth = this.getWidth();
            hasChanges = true;
        }
        if (this.getHeight() != App.saveManager.overlaySaveFile.menubarHeight) {
            if (App.saveManager.overlaySaveFile.menubarButtonLocation == MenubarButtonLocation.SE || App.saveManager.overlaySaveFile.menubarButtonLocation == MenubarButtonLocation.SW) {
                App.saveManager.overlaySaveFile.menubarY += App.saveManager.overlaySaveFile.menubarHeight - this.getHeight();
            }
            App.saveManager.overlaySaveFile.menubarHeight = this.getHeight();
            hasChanges = true;
        }
        if (hasChanges) {
            App.saveManager.saveOverlayToDisk();
        }
        this.updateLocation();
    }


    public void reorder() {
        assert(SwingUtilities.isEventDispatchThread());
        MenubarButtonLocation loc = App.saveManager.overlaySaveFile.menubarButtonLocation;
        boolean flip = false;
        int y = 0;
        int modY = 1;
        int count = buttons.size() + 1;
        if (loc == MenubarButtonLocation.SW || loc == MenubarButtonLocation.SE) {
            flip = true;
            y = App.testFeatures ? count + 1 : count;
            modY = -1;
        }
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = y;
        gc.fill = GridBagConstraints.BOTH;
        if (flip) {
            gc.insets = new Insets(0, 2, 2, 2);
        } else {
            gc.insets = new Insets(2, 2, 0, 2);
        }
        for (JButton b : buttons) {
            container.add(b, gc);
            gc.gridy += modY;
        }
        if (App.testFeatures) {
            container.add(testButton, gc);
            gc.gridy += modY;
        }
        if (flip) {
            gc.insets = new Insets(2, 2, 12, 2);
        } else {
            gc.insets = new Insets(12, 2, 2, 2);
        }
        container.add(quitButton, gc);
        this.pack();
    }

    private void addMouseExitListener(Component c) {
        JDialog local = this;
        c.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                Rectangle bounds = local.getBounds();
                System.out.println("BOUNDS" + bounds);
                if (!bounds.contains(MouseInfo.getPointerInfo().getLocation())) {
                    FrameManager.menubarToggle.setShow(true);
                    FrameManager.menubar.setShow(false);
                    FrameManager.menubarToggle.repaint();
                }
            }
        });
    }

}
