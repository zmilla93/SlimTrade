package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.ComponentResizeAdapter;
import com.slimtrade.core.observing.DocumentUpdateListener;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.ToggleButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.custom.CustomScrollPane;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.CustomIcons;
import com.slimtrade.gui.messaging.MessagePanel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.macro.MacroCustomizerRow;
import com.slimtrade.gui.options.macro.MacroPanel;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ChatScannerWindow extends AbstractResizableWindow implements ISaveable, IColorable {

    // TODO : inner panel could be containerpanel for consistency
    public static final int bufferOuter = 8;
    public static final int bufferInner = 5;

    //Arrays
    private ArrayList<ScannerMessage> messageList = new ArrayList<>();
    private ArrayList<Document> docList = new ArrayList<>();
    private ArrayList<DocumentListener> docListenerList = new ArrayList<>();

    // Labels
    private JPanel borderPanel = new JPanel(new GridBagLayout());
    private ContainerPanel containerPanel = new ContainerPanel();

    private JScrollPane scrollPane = new CustomScrollPane(borderPanel);
    private SearchNamePanel searchSelectorPanel = new SearchNamePanel();

    private SearchTermsPanel termsPanel = new SearchTermsPanel();
    private MacroPanel macroPanel = new MacroPanel(MessageType.CHAT_SCANNER){
        @Override
        public void updateColor() {
            super.updateColor();
            this.setBackground(ColorManager.BACKGROUND);
        }
    };

    private ToggleButton searchButton;
    private JComboBox<ScannerMessage> searchCombo;
    private JTextField saveTextField;

    // Controls from child panels
    private JButton saveButton, clearButton, revertButton, deleteButton;
    private JTextArea searchTermsInput, ignoreTermsInput;


    private AddRemovePanel addRemovePanel;
    private JButton addMacroButton;
//    private JTextField thankLeft;
//    private JTextField thankRight;

    // Internal
    private ScannerMessage selectedMessage;
    private MessagePanel sampleMessage;

    private boolean checkName;
    private boolean checkSearchTerms;
    private boolean checkIgnoreTerms;
    private boolean checkMacros;

    private final int MAX_MACROS = 8;
    private boolean searching = false;
    // TODO : Saving?
    private volatile boolean saving = false;


    public ChatScannerWindow() {
        super("Chat Scanner");

        addMacroButton = macroPanel.addButton;
        // Save Panel Controls
        searchButton = searchSelectorPanel.searchButton;
        searchCombo = searchSelectorPanel.searchCombo;
        saveTextField = searchSelectorPanel.saveTextField;
        saveButton = searchSelectorPanel.saveButton;
        clearButton = searchSelectorPanel.clearButton;
        revertButton = searchSelectorPanel.revertButton;
        deleteButton = searchSelectorPanel.deleteButton;

        // Terms Panel Controls
        searchTermsInput = termsPanel.searchTermsInput;
        ignoreTermsInput = termsPanel.ignoreTermsInput;

        // Macros
        macroPanel.setVisible(true);
//        MacroPanel macroPanel = new MacroPanel(MessageType.CHAT_SCANNER);

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        // Full Window

        // Search Selector Panel
        borderPanel.add(containerPanel, gc);
        gc.insets = new Insets(bufferInner, 20, 0, 20);
        containerPanel.container.add(searchSelectorPanel, gc);
        gc.gridy++;

        // Search Terms Panel
        containerPanel.container.add(termsPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 20;

        // Macro Panel
        addRemovePanel = macroPanel.addRemovePanel;
        containerPanel.container.add(macroPanel, gc);
        gc.gridy = 0;

        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);


        //Finalize
        scrollPane.setBorder(null);

        this.setFocusableWindowState(true);
        this.setFocusable(true);
        this.pack();
        this.setSize(750, 900);
        searchTermsInput.setLineWrap(true);
        searchTermsInput.setWrapStyleWord(true);
        FrameManager.centerFrame(this);
        refreshMessage("");
        updateColor();
        refreshWindowState();
        load();

        // Listeners
        saveTextField.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkName = selectedMessage != null && saveTextField.getText().equals(selectedMessage.name);
                refreshWindowState();
            }
        });

        searchTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkSearchTerms = selectedMessage != null && searchTermsInput.getText().equals(selectedMessage.searchTermsRaw);
                refreshWindowState();
            }
        });

        ignoreTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkIgnoreTerms = selectedMessage != null && ignoreTermsInput.getText().equals(selectedMessage.ignoreTermsRaw);
                refreshWindowState();
            }
        });

        addRemovePanel.addComponentListener(new ComponentResizeAdapter() {
            public void componentResized(ComponentEvent e) {
                checkMacros = checkMacros();
                refreshWindowState();
            }
        });

        addMacroButton.addActionListener(e -> {
//            addNewMacro();
            refreshListeners();
        });

        searchCombo.addActionListener(e -> {
            if (searchCombo.getSelectedIndex() >= 0) {
                loadMessage((ScannerMessage) searchCombo.getSelectedItem());
                selectedMessage = (ScannerMessage) searchCombo.getSelectedItem();
            } else {
                selectedMessage = null;
            }
            refreshListeners();
            runAllChecks();
            refreshWindowState();
        });

        searchButton.addActionListener(e -> {
            searching = !searching;
            searchButton.setState(searching);
            if (searching && selectedMessage != null) {
                App.chatParser.setSearchName(selectedMessage.name);
                App.chatParser.setSearchTerms(selectedMessage.searchTermsArray);
                App.chatParser.setSearchIgnoreTerms(selectedMessage.ignoreTermsArray);
            }
            App.chatParser.setChatScannerRunning(searching);
            if (searching) {
                saveTextField.setEnabled(false);
                searchCombo.setEnabled(false);
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
                deleteButton.setEnabled(false);
                searchTermsInput.setEnabled(false);
                ignoreTermsInput.setEnabled(false);
                addRemovePanel.setEnabledAll(false);
            } else {
                saveTextField.setEnabled(true);
                searchCombo.setEnabled(true);
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
                deleteButton.setEnabled(true);
                searchTermsInput.setEnabled(true);
                ignoreTermsInput.setEnabled(true);
                addRemovePanel.setEnabledAll(true);
            }
            refreshListeners();
            refreshWindowState();
        });

        deleteButton.addActionListener(e -> {
            if (searchCombo.getSelectedIndex() >= 0) {
                System.out.println("Deleting...");
                for (ScannerMessage m : messageList) {
                    if (selectedMessage.name.equals(m.name)) {
                        messageList.remove(m);
                        break;
                    }
                }
                App.saveManager.scannerSaveFile.messages = messageList.toArray(new ScannerMessage[0]);
                App.saveManager.saveScannerToDisk();
                refreshMessage("");
                refreshCombo();
                clearWindow();
                runAllChecks();
                refreshWindowState();
                System.out.println("Deleted!...");
            }
        });

        revertButton.addActionListener(e -> {
            saving = true;
            if (searchCombo.getSelectedIndex() < 0) {
                return;
            }
            ScannerMessage msg = (ScannerMessage) searchCombo.getSelectedItem();
            loadMessage(msg);
            refreshMessage(msg.name);
            runAllChecks();
            refreshWindowState();
            refreshListeners();
            saving = false;
        });

        clearButton.addActionListener(e -> {
            clearWindow();
            addDefaults();
            runAllChecks();
            refreshWindowState();
            refreshMessage("");
        });

        saveButton.addActionListener(e -> {
            saving = true;
            if (saveTextField.getText().matches("\\s*") || searchTermsInput.getText().matches("\\s*")) {
                return;
            }
            addRemovePanel.clearHiddenPanels();
            ArrayList<MacroButton> macros = new ArrayList();
            // Get list of macros
            for (Component c : addRemovePanel.getComponents()) {
                if (c.isVisible() && c instanceof MacroCustomizerRow) {
                    macros.add(((MacroCustomizerRow) c).getMacroData());
                }
            }
            // Get current scanner window message
            ScannerMessage message = new ScannerMessage(saveTextField.getText().trim(), searchTermsInput.getText(), ignoreTermsInput.getText(), macros.toArray(new MacroButton[0]));
            // Delete old duplicate
            for (ScannerMessage m : messageList) {
                if (message.name.toLowerCase().equals(m.name.toLowerCase())) {
                    messageList.remove(m);
                    break;
                }
            }
            messageList.add(message);
            Collections.sort(messageList, Comparator.comparing(ScannerMessage::getNameLower));
            refreshCombo(message);
            loadMessage(message);
            refreshWindowState();
            refreshListeners();

            App.saveManager.scannerSaveFile.messages = messageList.toArray(new ScannerMessage[0]);
            App.saveManager.saveScannerToDisk();

            refreshMessage(message.name);

            macroPanel.revalidate();
            macroPanel.repaint();

            saving = false;
        });
        addDefaults();
    }

    private void runAllChecks() {
        checkName = selectedMessage != null && saveTextField.getText().equals(selectedMessage.name);
        checkSearchTerms = selectedMessage != null && searchTermsInput.getText().equals(selectedMessage.searchTermsRaw);
        checkIgnoreTerms = selectedMessage != null && ignoreTermsInput.getText().equals(selectedMessage.ignoreTermsRaw);
        checkMacros = checkMacros();
    }

    private void refreshWindowState() {
        // TODO : Optimize?
        boolean matchingMessage = checkName && checkSearchTerms && checkIgnoreTerms && checkMacros;
        if (searching) {
            searchButton.setEnabled(false);
            deleteButton.setEnabled(false);
            revertButton.setEnabled(false);
            clearButton.setEnabled(false);
            saveButton.setEnabled(false);
            searchTermsInput.setEnabled(false);
            ignoreTermsInput.setEnabled(false);
            addMacroButton.setEnabled(false);
            searchButton.setEnabled(true);
        } else {
            searchTermsInput.setEnabled(true);
            ignoreTermsInput.setEnabled(true);
            addMacroButton.setEnabled(true);
            if (matchingMessage) {
                deleteButton.setEnabled(true);
                searchButton.setEnabled(true);
                revertButton.setEnabled(false);
                saveButton.setEnabled(false);
            } else {
                if (!saveTextField.getText().matches("\\s*") && !searchTermsInput.getText().matches("\\s*")) {
                    saveButton.setEnabled(true);
                } else {
                    saveButton.setEnabled(false);
                }
                if (searchCombo.getSelectedIndex() >= 0) {
                    revertButton.setEnabled(true);
                } else {
                    revertButton.setEnabled(false);
                }
                deleteButton.setEnabled(false);
                searchButton.setEnabled(false);
            }
            // Clear Button
            if (!saveTextField.getText().equals("") ||
                    !searchTermsInput.getText().equals("") ||
                    !ignoreTermsInput.getText().equals("") ||
                    addRemovePanel.getComponentCount() > 0) {
                clearButton.setEnabled(true);
            } else {
                clearButton.setEnabled(false);
            }
            // TODO : Clear button
        }
    }

    private boolean checkMacros() {
        if (selectedMessage == null) {
            return false;
        }
        ArrayList<MacroButton> buttons = new ArrayList<>();
        for (Component c : addRemovePanel.getComponents()) {
            if (c.isVisible() && c instanceof MacroCustomizerRow) {
                buttons.add(((MacroCustomizerRow) c).getMacroData());
            }
        }
        if (selectedMessage.macroButtons.length != buttons.size()) {
            return false;
        }
        int i = 0;
        for (MacroButton b1 : selectedMessage.macroButtons) {
            MacroButton b2 = buttons.get(i);
            if (b1.row != b2.row
                    || b1.image != b2.image
                    || !b1.leftMouseResponse.equals(b2.leftMouseResponse)
                    || !b1.rightMouseResponse.equals(b2.rightMouseResponse)
                    || !(b1.closeOnClick == b2.closeOnClick)
                    || !(b1.hotkeyData == b2.hotkeyData)
            ) {
                return false;
            }
            i++;
        }
        return true;
    }

    private void refreshCombo() {
        refreshCombo(null);
    }


    private void refreshCombo(ScannerMessage msg) {
        searchCombo.removeAllItems();
        for (ScannerMessage m : messageList) {
            searchCombo.addItem(m);
        }
        if (msg != null) {
            searchCombo.setSelectedItem(msg);
        }
    }

    private void refreshListeners() {
        // Reset Listeners
        for (Component c : addRemovePanel.getComponents()) {
            // Remove All Document Listeners
            int i = 0;
            for (Document d : docList) {
                d.removeDocumentListener(docListenerList.get(i));
                i++;
            }
            for(ComponentListener l : addRemovePanel.getComponentListeners()) {
                addRemovePanel.removeComponentListener(l);
            }
            docList.clear();
            docListenerList.clear();
            // Change Listeners
            if (c instanceof MacroCustomizerRow) {
                MacroCustomizerRow row = ((MacroCustomizerRow) c);
                MacroButton b = row.getMacroData();
                if (row.rowCombo.getListeners(ActionListener.class).length < 1) {
                    ((MacroCustomizerRow) c).rowCombo.addActionListener(e -> {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    });
                }
                if (row.iconCombo.getListeners(ActionListener.class).length < 1) {
                    ((MacroCustomizerRow) c).iconCombo.addActionListener(e -> {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    });
                }
                row.textLMB.getDocument().addDocumentListener(new DocumentUpdateListener() {
                    public void update() {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }
                });
                row.textLMB.getDocument().addDocumentListener(new DocumentUpdateListener() {
                    public void update() {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }
                });
                addRemovePanel.addComponentListener(new ComponentListener() {
                    @Override
                    public void componentResized(ComponentEvent e) {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }

                    @Override
                    public void componentMoved(ComponentEvent e) {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }

                    @Override
                    public void componentShown(ComponentEvent e) {

                    }

                    @Override
                    public void componentHidden(ComponentEvent e) {

                    }
                });
                row.closeCheckbox.addActionListener(e -> {
                    checkMacros = checkMacros();
                    refreshWindowState();
                });
            }
        }
    }

    private void refreshMessage(String searchName) {
        TradeOffer trade = new TradeOffer(MessageType.CHAT_SCANNER, "ExampleUser123", "item", 0, "", 0);
        trade.searchName = searchName;
        trade.searchMessage = "Example chat message. Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        macroPanel.setExampleMessage(trade);
    }

    public void resizeMessage(){
        macroPanel.resizeMessage();
    }

    private void loadMessage(ScannerMessage message) {
        saveTextField.setText(message.name);
        searchTermsInput.setText(message.searchTermsRaw);
        ignoreTermsInput.setText(message.ignoreTermsRaw);
        addRemovePanel.removeAll();
        for (MacroButton b : message.macroButtons) {
            MacroCustomizerRow row = new MacroCustomizerRow();
            row.rowCombo.setSelectedItem(b.row);
            boolean found = false;
            for (int j = 0; j < CustomIcons.values().length; j++) {
                if (CustomIcons.values()[j] == b.image) {
                    row.iconCombo.setSelectedIndex(j);
                    found = true;
                    break;
                }
            }
            if (!found && row.iconCombo.getItemCount() > 0) {
                row.iconCombo.setSelectedIndex(0);
            }
            row.textLMB.setText(b.leftMouseResponse);
            row.textRMB.setText(b.rightMouseResponse);
            row.hotkeyInput.updateHotkey(b.hotkeyData);
            row.closeCheckbox.setSelected(b.closeOnClick);
            addRemovePanel.addRemoveablePanel(row);
            refreshMessage(message.name);
        }

    }

    private void clearWindow() {
        selectedMessage = null;
        saveTextField.setText(null);
        searchCombo.setSelectedIndex(-1);
        searchTermsInput.setText(null);
        ignoreTermsInput.setText(null);
        addRemovePanel.removeAll();
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {
        App.saveManager.loadScannerFromDisk();
        messageList.clear();
        messageList.addAll(Arrays.asList(App.saveManager.scannerSaveFile.messages));
        Collections.sort(messageList, Comparator.comparing(ScannerMessage::getNameLower));
        searchCombo.removeAllItems();
        for (ScannerMessage msg : messageList) {
            searchCombo.addItem(msg);
        }
        searchCombo.setSelectedIndex(-1);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        containerPanel.updateColor();
        borderPanel.setBackground(ColorManager.BACKGROUND);
        containerPanel.setBorder(ColorManager.BORDER_TEXT);
    }

    private void addDefaults() {
        addRemovePanel.removeAll();
        MacroButton[] defaultMacros = {
                new MacroButton(ButtonRow.TOP, "hello!", "", CustomIcons.REPLY, null, false),
                new MacroButton(ButtonRow.BOTTOM, "/invite {player}", "", CustomIcons.INVITE, null, false),
                new MacroButton(ButtonRow.BOTTOM, "/tradewith {player}", "", CustomIcons.CART, null, false),
                new MacroButton(ButtonRow.BOTTOM, "thanks", "", CustomIcons.THUMB, null, false),
                new MacroButton(ButtonRow.BOTTOM, "/kick {player}", "", CustomIcons.LEAVE, null, false),
        };
        for (MacroButton b : defaultMacros) {
            MacroCustomizerRow row = new MacroCustomizerRow();
            row.setMacroData(b);
            addRemovePanel.addRemoveablePanel(row);
        }
    }

}

