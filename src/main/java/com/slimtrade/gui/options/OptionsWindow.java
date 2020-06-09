
package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.options.general.GeneralPanel;
import com.slimtrade.gui.options.general.HistorySettingsPanel;
import com.slimtrade.gui.options.hotkeys.HotkeyPanel;
import com.slimtrade.gui.options.ignore.ItemIgnorePanel;
import com.slimtrade.gui.options.macro.MacroPanel;
import com.slimtrade.gui.options.stashsearch.StashSearchPanel;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.stash.StashTabPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class OptionsWindow extends AbstractResizableWindow implements IColorable {

    private static final long serialVersionUID = 1L;
    private final JPanel display = new JPanel();
    private final JScrollPane scrollDisplay;

    private final JPanel menuPanel = new JPanel(new GridBagLayout());
    private final JPanel menuPanelLower = new JPanel(new GridBagLayout());
    private BasicButton checkUpdateButton;

    private GeneralPanel generalPanel;
    public MacroPanel macroPanelIncoming;
    public MacroPanel macroPanelOutgoing;
//    private HotkeyPanel hotkeyPanel;

    public OptionsWindow() {
        super("Options");
        this.setAlwaysOnTop(false);
        this.setFocusable(true);
        this.setFocusableWindowState(true);

        container.setLayout(new BorderLayout());
        JPanel menuBorder = new JPanel(new BorderLayout());

        menuBorder.add(menuPanel, BorderLayout.NORTH);
        menuBorder.add(menuPanelLower, BorderLayout.SOUTH);

        scrollDisplay = new CustomScrollPane(display);
        scrollDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        display.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        int buffer = 6;
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        menuPanel.setOpaque(false);
        menuPanelLower.setOpaque(false);
        menuBorder.setOpaque(false);

        bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, buffer));
        DenyButton revertButton = new DenyButton("Revert Changes");
        ConfirmButton saveButton = new ConfirmButton("Save");
        bottomPanel.add(revertButton);
        bottomPanel.add(saveButton);

        ListButton generalButton = new ListButton("General");
        generalPanel = new GeneralPanel();
        link(generalButton, generalPanel);
//        display.add(generalPanel, gc);

        ListButton stashButton = new ListButton("Stash Tabs");
        StashTabPanel stashPanel = new StashTabPanel();
        link(stashButton, stashPanel);
//        display.add(stashPanel, gc);

        ListButton macroIncomingButton = new ListButton("Incoming Macros");
        macroPanelIncoming = new MacroPanel(MessageType.INCOMING_TRADE);
        link(macroIncomingButton, macroPanelIncoming);
//        display.add(macroPanelIncoming, gc);

        ListButton macroOutgoingButton = new ListButton("Outgoing Macros");
        macroPanelOutgoing = new MacroPanel(MessageType.OUTGOING_TRADE);
        link(macroOutgoingButton, macroPanelOutgoing);
//        display.add(macroPanelOutgoing, gc);

//        ListButton incomingButton = new ListButton("old in");
//        IncomingCustomizer incomingPanel = new IncomingCustomizer(this);
//        link(incomingButton, incomingPanel);
//        display.add(incomingPanel, gc);
//
//        ListButton outgoingButton = new ListButton("old out");
//        OutgoingCustomizer outgoingPanel = new OutgoingCustomizer(this);
//        link(outgoingButton, outgoingPanel);
//        display.add(outgoingPanel, gc);

        ListButton ignoreButton = new ListButton("Ignore Items");
        ItemIgnorePanel ignorePanel = new ItemIgnorePanel();
        link(ignoreButton, ignorePanel);
//        display.add(ignorePanel, gc);

        ListButton hotKeyButton = new ListButton("Hotkeys");
        HotkeyPanel hotkeyPanel = new HotkeyPanel();
        link(hotKeyButton, hotkeyPanel);
//        display.add(hotkeyPanel, gc);

        ListButton stashSearcherButton = new ListButton("Stash Searcher");
        StashSearchPanel stashSearchPanel = new StashSearchPanel();
        link(stashSearcherButton, stashSearchPanel);
//        display.add(stashSearchPanel, gc);

        JButton contactButton = new ListButton("Information");
        InformationPanel contactPanel = new InformationPanel();
        link(contactButton, contactPanel);
//        display.add(contactPanel, gc);

        // JButton updateButton = new BasicButton("Update Available!");
        // updateButton.setVisible(false);

        checkUpdateButton = new CheckUpdateButton("Check for Updates");
        checkUpdateButton.setPreferredSize(checkUpdateButton.getPreferredSize());
        // TODO : Remove stash
        gc = new GridBagConstraints();
        // gc.anchor = GridBagConstraints.NORTH;

        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.insets.bottom = 10;
        gc.insets.left = 5;
        gc.insets.right = 5;
        gc.gridx = 0;
        gc.gridy = 0;
        menuPanel.add(generalButton, gc);
        gc.gridy++;
        menuPanel.add(stashButton, gc);
        gc.gridy++;
        menuPanel.add(macroIncomingButton, gc);
        gc.gridy++;
        menuPanel.add(macroOutgoingButton, gc);
        gc.gridy++;
//        menuPanel.add(incomingButton, gc);
//        gc.gridy++;
//        menuPanel.add(outgoingButton, gc);
//        gc.gridy++;
        menuPanel.add(ignoreButton, gc);
        gc.gridy++;
        menuPanel.add(hotKeyButton, gc);
        gc.gridy++;
        if (App.testFeatures) {
            menuPanel.add(stashSearcherButton, gc);
            gc.gridy++;
        }
        menuPanel.add(contactButton, gc);
        gc.gridy++;

        // Update Button
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets.bottom = 0;

        gc.fill = GridBagConstraints.NONE;
        menuPanelLower.add(new CustomLabel(References.APP_NAME + " v" + References.getAppVersion()), gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.gridy++;
        menuPanelLower.add(checkUpdateButton, gc);

        container.add(new BufferPanel(0, buffer), BorderLayout.NORTH);
        container.add(new BufferPanel(buffer, 0), BorderLayout.EAST);
        container.add(bottomPanel, BorderLayout.SOUTH);
        container.add(menuBorder, BorderLayout.WEST);
        container.add(scrollDisplay, BorderLayout.CENTER);

        display.add(generalPanel, gc);
        ColorManager.recursiveColor(generalPanel);
        generalButton.active = true;
        this.setPreferredSize(new Dimension(1000, 720));

        this.refresh();
        //TODO : Resize doesn't respect maximum size
        this.setMinimumSize(new Dimension(500, 550));
        this.setMaximumSize(new Dimension(1600, 900));
        FrameManager.centerFrame(this);

        checkUpdateButton.addActionListener(e -> {
            if (App.updateChecker.isUpdateAvailable()) {
                //UPDATE
                try {
                    URI uri = new URI("https://github.com/zmilla93/SlimTrade/releases/latest");
                    Desktop.getDesktop().browse(uri);
                } catch (URISyntaxException | IOException err) {
                    err.printStackTrace();
                }
            } else {
                new Thread(() -> {
                    SwingUtilities.invokeLater(() -> {
                        checkUpdateButton.setText("Checking...");
                        checkUpdateButton.setEnabled(false);
                    });
                    App.updateChecker.checkForUpdates();
                    try {
                        // Added delay to discourage people from spamming github
                        Thread.sleep(2000);
                    } catch (InterruptedException e1) {
                    }
                    SwingUtilities.invokeLater(() -> {
                        if (App.updateChecker.isUpdateAvailable()) {
                            recolorUpdateButton();
                        } else {
                            checkUpdateButton.setText("Check for Updates");
                        }
                        checkUpdateButton.setEnabled(true);
                    });
                }).start();
            }
        });

        revertButton.addActionListener(e -> {
            SaveManager.recursiveLoad(FrameManager.optionsWindow);
            FrameManager.optionsWindow.revalidate();
            FrameManager.optionsWindow.repaint();
        });

        saveButton.addActionListener(e -> {

            // Reload history window if needed
            // This is done on a new thread as it needs to reload the chat parser
            boolean reloadHistory = false;
            HistorySettingsPanel historySettingsPanel = FrameManager.optionsWindow.generalPanel.historyPanel;
            if (App.saveManager.saveFile.timeStyle != historySettingsPanel.getTimeStyle()
                    || App.saveManager.saveFile.dateStyle != historySettingsPanel.getDateStyle()
                    || App.saveManager.saveFile.orderType != historySettingsPanel.getOrderType()
                    || App.saveManager.saveFile.historyLimit != historySettingsPanel.getMessageLimit()
                    || !App.saveManager.saveFile.clientPath.equals(FrameManager.optionsWindow.generalPanel.getClientPath())) {
                FrameManager.historyWindow.setTimeStyle(historySettingsPanel.getTimeStyle());
                FrameManager.historyWindow.setDateStyle(historySettingsPanel.getDateStyle());
                FrameManager.historyWindow.setOrderType(historySettingsPanel.getOrderType());
                reloadHistory = true;
//                FrameManager.historyWindow.(historyOptionsPanel.getDateStyle());
            }

            // Save all windows
            SaveManager.recursiveSave(FrameManager.optionsWindow);
            App.saveManager.saveToDisk();

            // Refresh messages
            FrameManager.messageManager.refreshPanelLocations();

            // Update Clipboard Listener
            App.clipboardManager.setListeningState(App.saveManager.saveFile.quickPasteSetting == QuickPasteSetting.AUTOMATIC);

            // Set menubar visibility
            if (App.saveManager.saveFile.enableMenubar) {
                FrameManager.menubarToggle.setShow(true);
            } else {
                FrameManager.menubarToggle.setShow(false);
            }

            if (reloadHistory) {
                new Thread(() -> {

                    // Restart file monitor
                    App.fileMonitor.stopMonitor();
                    App.chatParser.init();
                    App.fileMonitor.startMonitor();

                }).start();
            }

        });


    }

    private void link(JButton b, JPanel p) {
        b.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                ListButton lb;
                for (Component c : menuPanel.getComponents()) {
                    lb = (ListButton) c;
                    lb.active = false;
                }
                lb = (ListButton) b;
                lb.active = true;
                GridBagConstraints gc = new GridBagConstraints();
                gc.gridx = 0;
                gc.gridy = 0;
                display.removeAll();
                display.add(p, gc);
                ColorManager.recursiveColor(p);
                revalidate();
                repaint();
            }
        });
    }

    private void hideAllWindows() {
        for (Component c : display.getComponents()) {
            c.setVisible(false);
        }
    }

    public void refresh() {
        display.revalidate();
        scrollDisplay.revalidate();
        this.pack();
        this.repaint();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        container.setBackground(ColorManager.BACKGROUND);
        display.setBackground(ColorManager.BACKGROUND);
        display.setBorder(ColorManager.BORDER_TEXT);
        display.setBorder(BorderFactory.createLineBorder(Color.RED));
        display.setBorder(null);
        scrollDisplay.setBorder(ColorManager.BORDER_TEXT);
    }

    public void recolorUpdateButton() {
        checkUpdateButton.setText("Update Available!");
        checkUpdateButton.updateColor();
    }

    public void reloadGeneralPanel() {
        SaveManager.recursiveLoad(generalPanel);
    }

}
