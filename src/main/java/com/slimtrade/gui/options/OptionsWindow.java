
package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.saving.elements.PinElement;
import com.slimtrade.enums.MessageType;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.options.cheatsheet.CheatSheetPanel;
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

public class OptionsWindow extends AbstractResizableWindow implements IColorable, ISaveable {

    private static final long serialVersionUID = 1L;
    private final JPanel display = new JPanel();
    private final JScrollPane scrollDisplay;

    private final JPanel menuPanel = new JPanel(new GridBagLayout());
    private final JPanel menuPanelLower = new JPanel(new GridBagLayout());
    private BasicButton checkUpdateButton;

    private GeneralPanel generalPanel;
    public MacroPanel macroPanelIncoming;
    public MacroPanel macroPanelOutgoing;

    public OptionsWindow() {
        super("Options", true, true);
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

        SelectorButton generalButton = new SelectorButton("General");
        generalPanel = new GeneralPanel();
        link(generalButton, generalPanel);

        SelectorButton hotKeyButton = new SelectorButton("Hotkeys");
        HotkeyPanel hotkeyPanel = new HotkeyPanel();
        link(hotKeyButton, hotkeyPanel);

        SelectorButton macroIncomingButton = new SelectorButton("Incoming Macros");
        macroPanelIncoming = new MacroPanel(MessageType.INCOMING_TRADE);
        link(macroIncomingButton, macroPanelIncoming);

        SelectorButton macroOutgoingButton = new SelectorButton("Outgoing Macros");
        macroPanelOutgoing = new MacroPanel(MessageType.OUTGOING_TRADE);
        link(macroOutgoingButton, macroPanelOutgoing);

        SelectorButton ignoreButton = new SelectorButton("Ignore Items");
        ItemIgnorePanel ignorePanel = new ItemIgnorePanel();
        link(ignoreButton, ignorePanel);

        SelectorButton stashButton = new SelectorButton("Stash Tabs");
        StashTabPanel stashPanel = new StashTabPanel();
        link(stashButton, stashPanel);

        SelectorButton stashSearcherButton = new SelectorButton("Stash Sorting");
        StashSearchPanel stashSearchPanel = new StashSearchPanel();
        link(stashSearcherButton, stashSearchPanel);

        SelectorButton cheatSheetButton = new SelectorButton("Cheat Sheets");
        CheatSheetPanel cheatSheetPanel = new CheatSheetPanel();
        link(cheatSheetButton, cheatSheetPanel);

        JButton contactButton = new SelectorButton("Information");
        InformationPanel contactPanel = new InformationPanel();
        link(contactButton, contactPanel);

        checkUpdateButton = new CheckUpdateButton("Check for Updates");
        checkUpdateButton.setPreferredSize(checkUpdateButton.getPreferredSize());
        gc = new GridBagConstraints();

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
        menuPanel.add(hotKeyButton, gc);
        gc.gridy++;
        menuPanel.add(macroIncomingButton, gc);
        gc.gridy++;
        menuPanel.add(macroOutgoingButton, gc);
        gc.gridy++;
        menuPanel.add(ignoreButton, gc);
        gc.gridy++;
        menuPanel.add(stashButton, gc);
        gc.gridy++;
        menuPanel.add(stashSearcherButton, gc);
        gc.gridy++;
        menuPanel.add(cheatSheetButton, gc);
        gc.gridy++;
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
        generalButton.selected = true;
        this.setDefaultSize(new Dimension(1000, 720));

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

        load();

    }

    private void link(JButton b, JPanel p) {
        b.addMouseListener(new AdvancedMouseAdapter() {
            public void click(MouseEvent e) {
                GridBagConstraints gc = new GridBagConstraints();
                gc.gridx = 0;
                gc.gridy = 0;
                display.removeAll();
                display.add(p, gc);
                ColorManager.recursiveColor(p);
                display.revalidate();
                display.repaint();
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

    public void recolorUpdateButton() {
        checkUpdateButton.setText("Update Available!");
        checkUpdateButton.updateColor();
    }

    public void reloadGeneralPanel() {
        SaveManager.recursiveLoad(generalPanel);
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

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
        save();
    }

    @Override
    public void save() {
        App.saveManager.pinSaveFile.optionsPin = getPinElement();
        App.saveManager.savePinsToDisk();
    }

    @Override
    public void load() {
        App.saveManager.loadPinsFromDisk();
        PinElement pin = App.saveManager.pinSaveFile.optionsPin;
        this.pinned = pin.pinned;
        if (this.pinned) {
            applyPinElement(pin);
        }
        updatePullbars();
    }
}
