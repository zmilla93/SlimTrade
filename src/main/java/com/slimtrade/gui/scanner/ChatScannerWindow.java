package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.observing.ComponentResizeAdapter;
import com.slimtrade.core.observing.DocumentUpdateListener;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.CustomScrollPane;
import com.slimtrade.gui.enums.ButtonRow;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.macros.CustomMacroRow;
import com.slimtrade.gui.options.macros.PresetMacroRow;
import com.slimtrade.gui.scanner.old.ScannerMessage_OLD;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ChatScannerWindow extends AbstractResizableWindow implements ISaveable {

    private final int PADDING = 14;
    private final int BUFFER_SMALL = 8;
    private final int BUFFER_LARGE = 10;
    private final Dimension inputSize = new Dimension(400, 140);
    private final int HEIGHT = 70;
    private final int CUSTOM_MAX = 8;

    boolean searching = false;
    volatile boolean saving = false;

    //Arrays
    private ArrayList<ScannerMessage> messageList = new ArrayList<>();
    private ArrayList<Document> docList = new ArrayList<>();
    private ArrayList<DocumentListener> docListenerList = new ArrayList<>();

    // Labels
//    private JLabel searchNameLabel = new JLabel("Search Name");
    private JLabel searchTermsLabel = new JLabel("Search Terms");
    private JLabel ignoreTermsLabel = new JLabel("Ignore Terms");
    private JLabel presetMacrosLabel = new JLabel("Preset Macros");
    private JLabel customMacrosLabel = new JLabel("Custom Macros");

    private JPanel borderPanel = new JPanel(FrameManager.gridBag);
    // Panels
    private JPanel innerPanel = new ColorPanel(FrameManager.gridBag);
    private JScrollPane scrollPane = new CustomScrollPane(borderPanel);

//    private JPanel upperPanel = new JPanel(FrameManager.gridBag);
    private SearchNamePanel upperPanel = new SearchNamePanel();
//    private JPanel buttonPanel = new JPanel(FrameManager.gridBag);
//    private JPanel namePanel = new JPanel(FrameManager.gridBag);
    private JPanel lowerPanel = new JPanel(FrameManager.gridBag);
    //TODO : THIS
//    private JPanel macroPanel = new JPanel(FrameManager.gridBag);
    private SearchMacroPanel macroPanel = new SearchMacroPanel();

    // Upper Controls
//    private JButton searchButton = new BasicButton("Search");
//    private JComboBox<ScannerMessage> searchCombo = new CustomCombo<>();
//    private JTextField saveTextField = new LimitTextField(32);
//    private JButton saveButton = new BasicButton("Save");
//    private JButton clearButton = new BasicButton("Clear");
//    private JButton revertButton = new BasicButton("Revert");
//    private JButton deleteButton = new BasicButton("Delete");
    private JButton searchButton;
    private JComboBox<ScannerMessage> searchCombo;
    private JTextField saveTextField;
    private JButton saveButton;
    private JButton clearButton;
    private JButton revertButton;
    private JButton deleteButton;

    // Lower Controls
    private JTextArea searchTermsInput = new JTextArea();
    private JTextArea ignoreTermsInput = new JTextArea();

    private AddRemovePanel addRemovePanel;
    private JButton addMacroButton;
    private JTextField thankLeft;
    private JTextField thankRight;

    // Checks
    private ScannerMessage selectedMessage;
    private boolean checkName;
    private boolean checkSearchTerms;
    private boolean checkIgnoreTerms;
    private boolean checkThankLeft;
    private boolean checkThankRight;
    private boolean checkMacros;

    public ChatScannerWindow() {
        super("Chat Scanner");

        // Get controls from child panels
        addRemovePanel = macroPanel.addRemovePanel;
        addMacroButton = macroPanel.addMacroButton;
        thankLeft = macroPanel.thankLeft;
        thankRight = macroPanel.thankRight;

        searchButton = upperPanel.searchButton;
        searchCombo = upperPanel.searchCombo;
        saveTextField = upperPanel.saveTextField;
        saveButton = upperPanel.saveButton;
        clearButton = upperPanel.clearButton;
        revertButton = upperPanel.revertButton;
        deleteButton = upperPanel.deleteButton;

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

//        // Button Panel
//        gc.fill = GridBagConstraints.BOTH;
//        gc.insets.top = 10;
//        buttonPanel.add(deleteButton, gc);
//        gc.gridx++;
//        gc.insets.left = 40;
//        buttonPanel.add(revertButton, gc);
//        gc.gridx++;
//        buttonPanel.add(clearButton, gc);
//        gc.gridx++;
//        buttonPanel.add(saveButton, gc);
//        gc.gridx++;
//        gc.gridx = 0;
//        gc.weightx = 1;
//        gc.insets.left = 0;
//        gc.insets.top = 0;
//
//        // Name Panel
//        gc.fill = GridBagConstraints.BOTH;
//        gc.insets.bottom = 5;
//        namePanel.add(saveTextField, gc);
//        gc.gridy++;
//        gc.insets.bottom = 0;
//        namePanel.add(searchCombo, gc);
//        gc.gridy = 0;
//
//        // Upper Panel
//        // Row 1
//        gc.fill = GridBagConstraints.BOTH;
//        upperPanel.add(searchNameLabel, gc);
//        gc.fill = GridBagConstraints.NONE;
//        gc.gridx++;
//        gc.anchor = GridBagConstraints.EAST;
//        gc.insets = new Insets(0, 0, 5, 0);
//        upperPanel.add(searchButton, gc);
//        gc.insets = new Insets(0, 0, 0, 0);
//        gc.anchor = GridBagConstraints.CENTER;
//        gc.gridx = 0;
//        gc.gridy++;
//        gc.gridwidth = 2;
//        gc.fill = GridBagConstraints.BOTH;
//        upperPanel.add(namePanel, gc);
//        gc.fill = GridBagConstraints.NONE;
//        gc.gridy++;
//        upperPanel.add(Box.createHorizontalStrut(400), gc);
//        gc.gridwidth = 1;
//
//
//        // Row 2
//        gc.gridx = 0;
//        gc.gridwidth = 2;
//        gc.gridy++;
//        upperPanel.add(buttonPanel, gc);
//        gc.gridwidth = 1;

        // Lower Panel
        gc.gridx = 0;
        gc.gridy = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        lowerPanel.add(searchTermsLabel, gc);
        gc.gridy++;
        JPanel p1 = new JPanel(new BorderLayout());
        p1.add(searchTermsInput, BorderLayout.CENTER);
        JScrollPane searchInputPane = new CustomScrollPane(p1);
        lowerPanel.add(searchInputPane, gc);
        gc.gridx++;
        lowerPanel.add(Box.createVerticalStrut(HEIGHT), gc);
        gc.gridx = 0;
        gc.gridy++;

//		gc.fill = GridBagConstraints.BOTH;
        lowerPanel.add(ignoreTermsLabel, gc);
        gc.gridy++;
        lowerPanel.add(ignoreTermsInput, gc);
        gc.gridx++;
        lowerPanel.add(Box.createVerticalStrut(HEIGHT), gc);
        gc.fill = GridBagConstraints.NONE;
//		gc.fill = GridBagConstraints.NONE;

        upperPanel.setBorder(BorderFactory.createLineBorder(Color.RED));
        lowerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));

        // Full Window
        innerPanel.setLayout(FrameManager.gridBag);
//		gc.fill = GridBagConstraints.BOTH;
        gc.gridx = 0;
        gc.gridy = 0;

//		gc.insets = new Insets(40, 40, 40, 40);
        borderPanel.add(innerPanel, gc);
        gc.insets = new Insets(20, 20, 0, 20);
        innerPanel.add(upperPanel, gc);
        gc.gridy++;
        gc.insets = new Insets(BUFFER_SMALL, 20, 0, 20);
        innerPanel.add(new JLabel("Separate terms using commas, semicolons, or new lines."), gc);
        gc.gridy++;
        gc.insets = new Insets(0, 20, 0, 20);
        gc.gridy++;
        innerPanel.add(new JLabel("Scanning is case insensitive. Irregular spacing will be ignored."), gc);
        gc.gridy++;
        gc.insets = new Insets(BUFFER_SMALL, 20, 0, 20);
        innerPanel.add(lowerPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 20;
        innerPanel.add(macroPanel, gc);
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;


        gc.insets = new Insets(0, 0, 0, 0);

        borderPanel.setBorder(BorderFactory.createLineBorder(Color.YELLOW));
        borderPanel.setBackground(Color.YELLOW);
        innerPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN));
        innerPanel.setBackground(Color.GREEN);

        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);

        this.pack();
        gc.gridx = 0;
        gc.gridy = 0;
        lowerPanel.add(Box.createHorizontalStrut(upperPanel.getWidth()), gc);
        searchInputPane.setPreferredSize(searchInputPane.getPreferredSize());

        //Finalize
        this.setAlwaysOnTop(true);
        this.setAlwaysOnTop(false);
        this.setFocusableWindowState(true);
        this.setFocusable(true);
        this.pack();
        this.setSize(650, 800);
        searchTermsInput.setLineWrap(true);
        searchTermsInput.setWrapStyleWord(true);
        FrameManager.centerFrame(this);
        updateColor();
        refreshWindowState();

        load();
        // Listeners

        saveTextField.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkName = selectedMessage == null ? false : saveTextField.getText().equals(selectedMessage.name);
                refreshWindowState();
            }
        });

        searchTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkSearchTerms = selectedMessage == null ? false : searchTermsInput.getText().equals(selectedMessage.searchTermsRaw);
                refreshWindowState();
            }
        });

        ignoreTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkIgnoreTerms = selectedMessage == null ? false : ignoreTermsInput.getText().equals(selectedMessage.ignoreTermsRaw);
                refreshWindowState();
            }
        });

        thankLeft.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkThankLeft = selectedMessage == null ? false : thankLeft.getText().equals(selectedMessage.thankLeft);
                refreshWindowState();
            }
        });

        thankRight.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkThankRight = selectedMessage == null ? false : thankRight.getText().equals(selectedMessage.thankRight);
                refreshWindowState();
            }
        });

        addRemovePanel.addComponentListener(new ComponentResizeAdapter() {
            public void componentResized(ComponentEvent e) {
                System.out.println("RES");
                checkMacros = checkMacros();
                refreshWindowState();
            }
        });

        addMacroButton.addActionListener(e -> {
            addNewMacro();
            refreshListeners();
        });

        searchCombo.addActionListener(e -> {
            System.out.println("Search Combo Changed");
            if (searchCombo.getSelectedIndex() >= 0) {
                loadMessage((ScannerMessage) searchCombo.getSelectedItem());
                selectedMessage = (ScannerMessage) searchCombo.getSelectedItem();
            } else {
                selectedMessage = null;
            }
            runAllChecks();
            refreshWindowState();
        });

        searchButton.addActionListener(e -> {
            searching = !searching;
            if (searching) {
//        		searchButton.setEnabled(true);
                saveTextField.setEnabled(false);
                searchCombo.setEnabled(false);
                saveButton.setEnabled(false);
                clearButton.setEnabled(false);
                deleteButton.setEnabled(false);
                searchTermsInput.setEnabled(false);
                ignoreTermsInput.setEnabled(false);
            } else {
//				searchButton.setEnabled(true);
                saveTextField.setEnabled(true);
                searchCombo.setEnabled(true);
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
                deleteButton.setEnabled(true);
                searchTermsInput.setEnabled(true);
                ignoreTermsInput.setEnabled(true);
            }
            refreshWindowState();
        });

        revertButton.addActionListener(e -> {
            System.out.println("Reverting...");
            saving = true;
            if (searchCombo.getSelectedIndex() < 0) {
                return;
            }
            ScannerMessage msg = (ScannerMessage) searchCombo.getSelectedItem();
            System.out.println("\tLoading Message...");
            loadMessage(msg);
            System.out.println("\tRefresh Message...");
            runAllChecks();
            refreshWindowState();
            System.out.println("\tRefresh Listeners...");
            refreshListeners();
            saving = false;

            System.out.println("Reverted!");
        });

        clearButton.addActionListener(e -> {
            selectedMessage = null;
            saveTextField.setText(null);
            searchCombo.setSelectedIndex(-1);
            searchTermsInput.setText(null);
            ignoreTermsInput.setText(null);
            addRemovePanel.removeAll();
            runAllChecks();
            refreshWindowState();
        });

        saveButton.addActionListener(e -> {
            System.out.println("Saving Scanner...");
            saving = true;
            if (saveTextField.getText().matches("\\s*") || searchTermsInput.getText().matches("\\s*")) {
                return;
            }
            addRemovePanel.saveChanges();
            ArrayList<MacroButton> macros = new ArrayList();
            // Get list of macros
            for (Component c : addRemovePanel.getComponents()) {
                if (c.isVisible() && c instanceof CustomMacroRow) {
                    macros.add(((CustomMacroRow) c).getMacroData());
                }
            }
            // Get current scanner window message
            ScannerMessage message = new ScannerMessage(saveTextField.getText(), searchTermsInput.getText(), ignoreTermsInput.getText(), thankLeft.getText(), thankRight.getText(), macros);
            // Delete old duplicate
            for (ScannerMessage m : messageList) {
                if (message.name.toLowerCase().equals(m.name.toLowerCase())) {
                    messageList.remove(m);
                    break;
                }
            }
            messageList.add(message);
            Collections.sort(messageList, Comparator.comparing(ScannerMessage::getName));
            refreshCombo(message);
            loadMessage(message);
            refreshWindowState();
            refreshListeners();

            Collections.sort(messageList, Comparator.comparing(ScannerMessage::getName));
            App.saveManager.scannerSaveFile.messages.clear();
            App.saveManager.scannerSaveFile.messages.addAll(messageList);
            App.saveManager.saveScannerToDisk();

            macroPanel.revalidate();
            macroPanel.repaint();

            saving = false;
            System.out.println("Scanner Saved!");
        });
    }

    private void runAllChecks() {
        checkName = selectedMessage == null ? false : saveTextField.getText().equals(selectedMessage.name);
        checkSearchTerms = selectedMessage == null ? false : searchTermsInput.getText().equals(selectedMessage.searchTermsRaw);
        checkIgnoreTerms = selectedMessage == null ? false : ignoreTermsInput.getText().equals(selectedMessage.ignoreTermsRaw);
        checkThankLeft = selectedMessage == null ? false : thankLeft.getText().equals(selectedMessage.thankLeft);
        checkThankRight = selectedMessage == null ? false : thankRight.getText().equals(selectedMessage.thankRight);
        checkMacros = checkMacros();
    }

    private void refreshWindowState() {
        // TEMP
        boolean matchingMessage = checkName && checkSearchTerms && checkIgnoreTerms && checkThankLeft && checkThankRight && checkMacros;
        System.out.print("MATCH : " + matchingMessage + "\t");
        System.out.print("checkName[" + checkName + "]\t");
        System.out.print("checkSearchTerms[" + checkSearchTerms + "]\t");
        System.out.print("checkIgnoreTerms[" + checkIgnoreTerms + "]\t");
        System.out.print("checkThankLeft[" + checkThankLeft + "]\t");
        System.out.print("checkThankRight[" + checkThankRight + "]\t");
        System.out.print("checkMacros[" + checkMacros + "]\t");
        System.out.println();
        if (searching) {
            searchButton.setEnabled(false);
            deleteButton.setEnabled(false);
            revertButton.setEnabled(false);
            clearButton.setEnabled(false);
            saveButton.setEnabled(false);
            searchTermsInput.setEnabled(false);
            ignoreTermsInput.setEnabled(false);
            thankLeft.setEnabled(false);
            thankRight.setEnabled(false);
            addMacroButton.setEnabled(false);
            searchButton.setEnabled(true);
        } else {
            searchTermsInput.setEnabled(true);
            ignoreTermsInput.setEnabled(true);
            thankLeft.setEnabled(true);
            thankRight.setEnabled(true);
            clearButton.setEnabled(true);
            addMacroButton.setEnabled(true);
            if (matchingMessage) {
                deleteButton.setEnabled(true);
                searchButton.setEnabled(true);
                revertButton.setEnabled(false);
                saveButton.setEnabled(false);
            } else {
                if (!saveTextField.getText().matches("\\s*") && !searchTermsInput.getText().matches("\\s*")) {
                    revertButton.setEnabled(true);
                    saveButton.setEnabled(true);
                } else {
                    revertButton.setEnabled(false);
                    saveButton.setEnabled(false);
                }
                deleteButton.setEnabled(false);
                searchButton.setEnabled(false);
            }
        }


    }

    // Enabled/disabled the window controls based on how they match the saved messages
    private void refreshWindowState(ScannerMessage currentMessage) {
        if (saving) {
            System.out.println("IGNORED");
            return;
        }
        System.out.println("\t\tWindow Refresh");
        searchButton.setEnabled(false);
        deleteButton.setEnabled(false);
        revertButton.setEnabled(false);
        clearButton.setEnabled(false);
        saveButton.setEnabled(false);
        addMacroButton.setEnabled(false);
        if (searching) {
            System.out.println("SEARCH");
            searchButton.setEnabled(true);
            return;
        }
        addMacroButton.setEnabled(true);
        if (!currentMessage.name.matches("\\s*") || currentMessage.searchTermsRaw.length() > 0 || currentMessage.ignoreTermsRaw.length() > 0 || currentMessage.macroButtons.size() > 0) {
            clearButton.setEnabled(true);
        }
        if (!currentMessage.name.matches("\\s*") && !currentMessage.searchTermsRaw.matches("\\s*")) {
            saveButton.setEnabled(true);
        }
        ScannerMessage selectedMessage = null;
        if (searchCombo.getItemCount() > 0) {
            if (searchCombo.getSelectedIndex() < 0) {
//                searchCombo.setSelectedIndex(0);
            } else {

                selectedMessage = (ScannerMessage) searchCombo.getSelectedItem();
                runAllChecks();
            }
            boolean matchingMessages = matchingMessages(currentMessage, selectedMessage);
            if (matchingMessages) {
                searchButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                if (searchCombo.getSelectedIndex() > -1) {
                    revertButton.setEnabled(true);
                }
            }
            searchButton.setEnabled(matchingMessages);
        } else {
            System.out.println("No selection");
            //TODO
        }

        // If everything is valid, enable search
//        refreshListeners();

    }

    private boolean checkMacros() {
        if (selectedMessage == null) {
            System.out.println("BMAC:MSG");
            return false;
        }
        ArrayList<MacroButton> buttons = new ArrayList<>();
        for (Component c : addRemovePanel.getComponents()) {
            if (c.isVisible() && c instanceof CustomMacroRow) {
                buttons.add(((CustomMacroRow) c).getMacroData());
            }
        }
        if (selectedMessage.macroButtons.size() != buttons.size()) {
            return false;
        }
        int i = 0;
        for (MacroButton b1 : selectedMessage.macroButtons) {
            MacroButton b2 = buttons.get(i);
            if (b1.row != b2.row ||
                    b1.image != b2.image ||
                    !b1.leftMouseResponse.equals(b2.leftMouseResponse) ||
                    !b1.rightMouseResponse.equals(b2.rightMouseResponse)
            ) {
                return false;
            }
            i++;
        }
        return true;
    }

    private boolean matchingMessages(ScannerMessage msg1, ScannerMessage msg2) {
        // Name
        if (msg1 == null || msg2 == null) {
            return false;
        }
        if (!(msg1.name.equals(msg2.name))) {
            return false;
        }
        // Macro Count
        if (msg1.macroButtons.size() != msg2.macroButtons.size()) {
            return false;
        }
        // Thank
        if (!msg1.thankLeft.equals(msg2.thankLeft)) {
            return false;
        }
        if (!msg1.thankRight.equals(msg2.thankRight)) {
            return false;
        }
        // Search Terms
        if (!(msg1.searchTermsRaw.equals(msg2.searchTermsRaw))) {
            return false;
        }
        // Ignore Terms
        if (!(msg1.ignoreTermsRaw.equals(msg2.ignoreTermsRaw))) {
            return false;
        }
        // Macros
        int i = 0;
        for (MacroButton b1 : msg1.macroButtons) {
            if (!MacroButton.doButtonsMatch(b1, msg2.macroButtons.get(i))) {
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

    private CustomMacroRow addNewMacro() {
        int i = 0;
        for (Component c : addRemovePanel.getComponents()) {
            if (c.isVisible()) {
                i++;
            }
        }
        if (i >= CUSTOM_MAX) {
            return null;
        }
        CustomMacroRow row = new CustomMacroRow(addRemovePanel);
        // Listeners
//        thankLeft.getDocument().addDocumentListener(new DocumentUpdateListener() {
//            public void update() {
//                System.out.println("#t1");
//                checkMacros();
//                refreshWindowState();
//            }
//        });
//        thankRight.getDocument().addDocumentListener(new DocumentUpdateListener() {
//            public void update() {
//                System.out.println("#t2");
//                checkMacros();
//                refreshWindowState();
//            }
//        });
        addRemovePanel.addRemoveablePanel(row);
        System.out.println("DOC:" + row.m2Text.getListeners(DocumentListener.class).length);
        return row;
    }

    private void refreshListeners() {
        // Reset Listeners
        for (Component c : addRemovePanel.getComponents()) {
            System.out.println("\t...");
            // Remove All Document Listeners
            int i = 0;
            for (Document d : docList) {
                d.removeDocumentListener(docListenerList.get(i));
                i++;
            }
            docList.clear();
            docListenerList.clear();
            if (c instanceof CustomMacroRow) {
                CustomMacroRow row = ((CustomMacroRow) c);
                MacroButton b = row.getMacroData();
                if (row.rowCombo.getListeners(ActionListener.class).length < 1) {
                    ((CustomMacroRow) c).rowCombo.addActionListener(e -> {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    });
                }
                if (row.iconCombo.getListeners(ActionListener.class).length < 1) {
                    ((CustomMacroRow) c).iconCombo.addActionListener(e -> {
                        System.out.println("iconIndex!!!" + ((CustomMacroRow) c).iconCombo.getSelectedIndex());
                        checkMacros = checkMacros();
                        refreshWindowState();
                    });
                }
                row.m1Text.getDocument().addDocumentListener(new DocumentUpdateListener() {
                    public void update() {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }
                });
                row.m2Text.getDocument().addDocumentListener(new DocumentUpdateListener() {
                    public void update() {
                        checkMacros = checkMacros();
                        refreshWindowState();
                    }
                });
                System.out.println("\t\tLC2:" + ((CustomMacroRow) c).rowCombo.getListeners(ActionListener.class).length);
            }
        }
    }

    private void loadMessage(ScannerMessage message) {
        saveTextField.setText(message.name);
        searchTermsInput.setText(message.searchTermsRaw);
        ignoreTermsInput.setText(message.ignoreTermsRaw);
        thankLeft.setText(message.thankLeft);
        thankRight.setText(message.thankRight);
        addRemovePanel.removeAll();
        for (MacroButton b : message.macroButtons) {
            CustomMacroRow row = new CustomMacroRow(addRemovePanel);
            row.rowCombo.setSelectedItem(b.row);
            System.out.println("\tLoding Combo Image... " + b.image);
            boolean found = false;
            for (int i = 0; i < row.iconCombo.getItemCount(); i++) {
                System.out.println("\t\tOPTION :: " + row.iconCombo.getItemAt(i).getImage().equals(b.image.getImage()));
                if (row.iconCombo.getItemAt(i).getImage().equals(b.image.getImage())) {
                    row.iconCombo.setSelectedIndex(i);
                    break;
                }
            }
            System.out.println("LOADED ::: " + row.iconCombo.getSelectedIndex());
            row.setTextLMB(b.leftMouseResponse);
            row.setTextRMB(b.rightMouseResponse);
            addRemovePanel.addRemoveablePanel(row);
        }
        addRemovePanel.saveChanges();
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {
        App.saveManager.loadScannerFromDisk();
        messageList.clear();
        messageList.addAll(App.saveManager.scannerSaveFile.messages);
        Collections.sort(messageList, Comparator.comparing(ScannerMessage::getName));
        searchCombo.removeAllItems();
        for (ScannerMessage msg : messageList) {
            searchCombo.addItem(msg);
        }
        if(messageList.size() > 0) {
            searchCombo.setSelectedIndex(0);
            loadMessage(messageList.get(0));
            selectedMessage = messageList.get(0);
        }


//        if(App.saveManager.scannerSaveFile.messages.size() > 0) {
//            searchCombo.setSelectedIndex(0);
//        }
    }
}

