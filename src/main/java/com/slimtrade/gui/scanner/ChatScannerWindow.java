package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.ComponentResizeAdapter;
import com.slimtrade.core.observing.DocumentUpdateListener;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.CustomScrollPane;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.macros.CustomMacroRow;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
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
    private JPanel borderPanel = new JPanel(FrameManager.gridBag);
    private ContainerPanel containerPanel = new ContainerPanel();

    private JScrollPane scrollPane = new CustomScrollPane(borderPanel);
    private SearchNamePanel namePanel = new SearchNamePanel();

    private SearchTermsPanel termsPanel = new SearchTermsPanel();
    private SearchMacroPanel macroPanel = new SearchMacroPanel();

    private BasicButton searchButton;
    private JComboBox<ScannerMessage> searchCombo;
    private JTextField saveTextField;

    // Controls from child panels
    private JButton saveButton, clearButton, revertButton, deleteButton;
    private JTextArea searchTermsInput, ignoreTermsInput;


    private AddRemovePanel addRemovePanel;
    private JButton addMacroButton;
    private JTextField thankLeft;
    private JTextField thankRight;

    // Internal
    private ScannerMessage selectedMessage;

    private boolean checkName;
    private boolean checkSearchTerms;
    private boolean checkIgnoreTerms;
    private boolean checkThankLeft;
    private boolean checkThankRight;
    private boolean checkMacros;

    private final int MAX_MACROS = 8;
    private boolean searching = false;
    // TODO : Saving?
    private volatile boolean saving = false;


    public ChatScannerWindow() {
        super("Chat Scanner");
        // Get controls from child panels
        // Macro Panel Controls
        addRemovePanel = macroPanel.addRemovePanel;
        addMacroButton = macroPanel.addMacroButton;
        thankLeft = macroPanel.thankLeft;
        thankRight = macroPanel.thankRight;

        // Save Panel Controls
        searchButton = namePanel.searchButton;
        searchCombo = namePanel.searchCombo;
        saveTextField = namePanel.saveTextField;
        saveButton = namePanel.saveButton;
        clearButton = namePanel.clearButton;
        revertButton = namePanel.revertButton;
        deleteButton = namePanel.deleteButton;

        // Terms Panel Controls
        searchTermsInput = termsPanel.searchTermsInput;
        ignoreTermsInput = termsPanel.ignoreTermsInput;

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        // Full Window
        borderPanel.add(containerPanel, gc);
        gc.insets = new Insets(20, 20, 0, 20);

        containerPanel.container.add(namePanel, gc);
        gc.gridy++;
        gc.insets = new Insets(bufferInner, 20, 0, 20);
        gc.gridy++;
//        gc.insets = new Insets(bufferInner, 20, 0, 20);
        containerPanel.container.add(termsPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 20;
        containerPanel.container.add(macroPanel, gc);
        gc.gridy = 0;
//        gc.fill = GridBagConstraints.BOTH;


//        gc.insets = new Insets(0, 0, 0, 0);


        container.setLayout(new BorderLayout());
        container.add(scrollPane, BorderLayout.CENTER);


        //Finalize
        this.setFocusableWindowState(true);
        this.setFocusable(true);
        this.pack();
        this.setSize(650, 820);
        searchTermsInput.setLineWrap(true);
        searchTermsInput.setWrapStyleWord(true);
        FrameManager.centerFrame(this);
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

        thankLeft.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkThankLeft = selectedMessage != null && thankLeft.getText().equals(selectedMessage.thankLeft);
                refreshWindowState();
            }
        });

        thankRight.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                checkThankRight = selectedMessage != null && thankRight.getText().equals(selectedMessage.thankRight);
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
            addNewMacro();
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
                searchButton.setText("Cancel Search");
                searchButton.primaryColor = ColorManager.RED_DENY;
                searchButton.updateColor();
            } else {
                saveTextField.setEnabled(true);
                searchCombo.setEnabled(true);
                saveButton.setEnabled(true);
                clearButton.setEnabled(true);
                deleteButton.setEnabled(true);
                searchTermsInput.setEnabled(true);
                ignoreTermsInput.setEnabled(true);
                addRemovePanel.setEnabledAll(true);
                searchButton.setText("Search");
                searchButton.primaryColor = ColorManager.GREEN_APPROVE;
                searchButton.updateColor();
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
                App.saveManager.scannerSaveFile.messages.clear();
                App.saveManager.scannerSaveFile.messages.addAll(messageList);
                App.saveManager.saveScannerToDisk();
                refreshCombo();
                clearWindow();
                runAllChecks();
                refreshWindowState();
                System.out.println("Deleted!...");
            }
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
            clearWindow();
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
            ScannerMessage message = new ScannerMessage(saveTextField.getText().trim(), searchTermsInput.getText(), ignoreTermsInput.getText(), thankLeft.getText(), thankRight.getText(), macros);
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

//            Collections.sort(messageList, Comparator.comparing(ScannerMessage::getNameLower));
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
        checkName = selectedMessage != null && saveTextField.getText().equals(selectedMessage.name);
        checkSearchTerms = selectedMessage != null && searchTermsInput.getText().equals(selectedMessage.searchTermsRaw);
        checkIgnoreTerms = selectedMessage != null && ignoreTermsInput.getText().equals(selectedMessage.ignoreTermsRaw);
        checkThankLeft = selectedMessage != null && thankLeft.getText().equals(selectedMessage.thankLeft);
        checkThankRight = selectedMessage != null && thankRight.getText().equals(selectedMessage.thankRight);
        checkMacros = checkMacros();
    }

    private void refreshWindowState() {
        // TEMP
        boolean matchingMessage = checkName && checkSearchTerms && checkIgnoreTerms && checkThankLeft && checkThankRight && checkMacros;
//        System.out.print("MATCH : " + matchingMessage + "\t");
//        System.out.print("checkName[" + checkName + "]\t");
//        System.out.print("checkSearchTerms[" + checkSearchTerms + "]\t");
//        System.out.print("checkIgnoreTerms[" + checkIgnoreTerms + "]\t");
//        System.out.print("checkThankLeft[" + checkThankLeft + "]\t");
//        System.out.print("checkThankRight[" + checkThankRight + "]\t");
//        System.out.print("checkMacros[" + checkMacros + "]\t");
//        System.out.println();
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
                    !thankLeft.getText().equals("") ||
                    !thankRight.getText().equals("") ||
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
        if (i >= MAX_MACROS) {
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
            boolean found = false;
            for (int i = 0; i < row.iconCombo.getItemCount(); i++) {
                if (row.iconCombo.getItemAt(i).getImage().equals(b.image.getImage())) {
                    row.iconCombo.setSelectedIndex(i);
                    break;
                }
            }
            row.setTextLMB(b.leftMouseResponse);
            row.setTextRMB(b.rightMouseResponse);
            addRemovePanel.addRemoveablePanel(row);
        }
        addRemovePanel.saveChanges();
    }

    private void clearWindow() {
        selectedMessage = null;
        saveTextField.setText(null);
        searchCombo.setSelectedIndex(-1);
        searchTermsInput.setText(null);
        ignoreTermsInput.setText(null);
        thankLeft.setText(null);
        thankRight.setText(null);
        addRemovePanel.removeAll();
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {
        App.saveManager.loadScannerFromDisk();
        messageList.clear();
        messageList.addAll(App.saveManager.scannerSaveFile.messages);
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
}

