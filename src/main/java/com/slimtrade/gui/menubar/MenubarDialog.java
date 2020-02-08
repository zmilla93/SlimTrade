package com.slimtrade.gui.menubar;

import com.slimtrade.App;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MenubarButtonLocation;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.BasicPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import java.util.ResourceBundle;

public class MenubarDialog extends BasicDialog {

    private static final long serialVersionUID = 1L;
    private boolean test = false;
    private static int buttonCount = 4;
    private static int spacerCount = 1;
    private static int spacerHeight = (int) (MenubarButton.HEIGHT * 0.3);

    public static final int WIDTH = MenubarButton.WIDTH;
    public static final int HEIGHT = (MenubarButton.HEIGHT * buttonCount) + (spacerHeight * spacerCount);

    private MenubarButton historyButton;
    // private MenubarButton stashTabButton;
    private MenubarButton chatScannerButton;
    // private MenubarButton characterButton;
    private MenubarButton testButton;
    private MenubarButton optionsButton;
    private MenubarButton quitButton;
//	private MenubarButton minimizeButton;

    private boolean visible = false;
    private boolean order = false;

    private Component[] componentList;
    // private ArrayList<Component> componentList = new ArrayList<Component>();
    Container container = this.getContentPane();
//	private ExpandDirection expandDirection = ExpandDirection.DOWN;

    public MenubarDialog() {
        // TODO : Modify constructor of menubar buttons
        // TODO : Switch to gridbag

        this.setBounds(0, TradeUtility.screenSize.height - HEIGHT, WIDTH, HEIGHT);
//		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        container.setLayout(new GridBagLayout());

        // TODO : Update Locale
        historyButton = new MenubarButton("");
        // stashTabButton = new MenubarButton("");
        chatScannerButton = new MenubarButton("Chat Scanner");
        // characterButton = new MenubarButton("");
        testButton = new MenubarButton("");
        optionsButton = new MenubarButton("");
        quitButton = new MenubarButton("");
//		minimizeButton = new MenubarButton("");

//		testButton.setToolTipText("This is a test.");

        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridwidth = 1;
        gc.gridheight = 1;
        gc.insets = new Insets(20, 20, 0, 2);

        container.add(historyButton, gc);
        gc.gridy++;
        // container.add(stashTabButton);
        container.add(chatScannerButton, gc);
        gc.gridy++;
        // container.add(characterButton);
        if (test) {
            container.add(testButton, gc);
            gc.gridy++;
        }
        container.add(optionsButton, gc);
        gc.gridy++;
        container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
        gc.gridy++;
        gc.insets = new Insets(2, 2, 2, 2);
        container.add(quitButton);
//		container.add(new BasicPanel(MenubarButton.WIDTH, spacerHeight));
//		container.add(minimizeButton);

        this.refreshButtonText();

        componentList = container.getComponents();

        // TODO : Move button actions

        // History
        historyButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                if (!FrameManager.historyWindow.isVisible()) {
                    FrameManager.hideMenuFrames();
                    FrameManager.historyWindow.setShow(true);
                }
            }
        });

        // Chat Scanner
        chatScannerButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                if (!FrameManager.chatScannerWindow.isVisible()) {
                    FrameManager.hideMenuFrames();
                    FrameManager.chatScannerWindow.setShow(true);
                }
            }
        });

        // TEST BUTTON
        testButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                Random rng = new Random();
                TradeOffer t = new TradeOffer("", "", MessageType.INCOMING_TRADE, "<GLD>", "SmashyMcFireBalls", "Superior Item Name", 3, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
                TradeOffer t2 = new TradeOffer("", "", MessageType.OUTGOING_TRADE, "<GLD>", "SmashyMcFireBalls", "Superior Item Name", 3, "chaos", 5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
                TradeOffer t3 = new TradeOffer("", "", MessageType.CHAT_SCANNER, "<GLD>", "SmashyMcFireBalls", "Search Name", "example text", null, null);
                FrameManager.messageManager.addMessage(t, false);
                FrameManager.messageManager.addMessage(t2, false);
                FrameManager.messageManager.addMessage(t3, false);
            }
        });

        // Options
        optionsButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent evt) {
                if (!FrameManager.optionsWindow.isVisible()) {
                    FrameManager.hideMenuFrames();
                    FrameManager.optionsWindow.setShow(true);
                }
            }
        });

        // Quit Program
        quitButton.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                System.exit(0);
            }
        });

        //


        // Listeners
        this.addMouseListener(new MouseAdapter() {
            public void mouseExited(MouseEvent e) {
                Rectangle bounds = FrameManager.menubar.getBounds();
                if (!bounds.contains(MouseInfo.getPointerInfo().getLocation())) {
                    FrameManager.menubarToggle.setShow(true);
                    FrameManager.menubar.setShow(false);
                    FrameManager.menubarToggle.repaint();
                }
            }
        });

        this.pack();
        this.reorder();
        this.setBounds(0, TradeUtility.screenSize.height - HEIGHT, WIDTH, HEIGHT);
    }

    private void refreshButtonText() {
        ResourceBundle lang = ResourceBundle.getBundle("lang");
        optionsButton.setText(lang.getString("optionsButton"));
        historyButton.setText(lang.getString("historyButton"));
//        testButton.setText(lang.getString("testButton"));
        testButton.setText("TEST TEST TEST TEST TEST TEST");
        quitButton.setText(lang.getString("quitButton"));
    }

    public void updateLocation() {
        System.out.println("UPDATE MENUBAR");
//        this.(App.saveManager.overlaySaveFile.menubarX, App.saveManager.overlaySaveFile.menubarY);
        this.setBounds(App.saveManager.overlaySaveFile.menubarX, App.saveManager.overlaySaveFile.menubarY, this.getWidth(), this.getHeight());
    }

    public void init() {
//        System.out.println("INIT X:" + this.getX());
        System.out.println("INIT X:" + App.saveManager.overlaySaveFile.menubarX);
        boolean hasChanges = false;
        int diff;
        if (this.getWidth() != App.saveManager.overlaySaveFile.menubarWidth) {
            System.out.println("DIFF WIDTH");
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
        System.out.println("INIT X:" + App.saveManager.overlaySaveFile.menubarX);
        this.updateLocation();
    }

    public void reorder() {
        System.out.println("REORDER : " + App.saveManager.overlaySaveFile.menubarButtonLocation);
        int buffer = 2;
        MenubarButtonLocation loc = App.saveManager.overlaySaveFile.menubarButtonLocation == null ? MenubarButtonLocation.NW : App.saveManager.overlaySaveFile.menubarButtonLocation;
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets = new Insets(3, buffer, 0, buffer);
        int componentCount = componentList.length;
        switch (loc) {
            case NE:
            case NW:
                for (Component c : componentList) {
                    container.add(c, gc);
                    gc.gridy++;
                    if (gc.gridy < componentCount - 1) {
                        gc.insets.bottom = 0;
                        gc.insets.top = buffer;
                    } else {
                        gc.insets.top = 0;
                        gc.insets.bottom = 3;
                    }
                }
                break;
            case SE:
            case SW:
                for (Component c : componentList) {
                    container.add(c, gc);
                    gc.gridy++;
                    if (gc.gridy < componentCount - 1) {
                        gc.insets.bottom = 0;
                        gc.insets.top = buffer;
                    } else {
                        gc.insets.top = 0;
                        gc.insets.bottom = 3;
                    }
                }
                break;
        }
        this.setPreferredSize(null);
        this.setPreferredSize(this.getPreferredSize());
        this.revalidate();
        this.repaint();
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
