package com.slimtrade.gui.scanner.old;

import com.slimtrade.core.SaveSystem.MacroButton;
import com.slimtrade.core.observing.DocumentUpdateListener;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.ColorPanel;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.CustomScrollPane;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.macros.CustomMacroRow;
import com.slimtrade.gui.options.macros.PresetMacroRow;
import com.slimtrade.gui.scanner.ScannerMessage;
import com.slimtrade.gui.stash.LimitTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.util.ArrayList;

public class ChatScannerWindowHMM extends AbstractResizableWindow implements ISaveable {

    private final int PADDING = 14;
    private final int BUFFER_SMALL = 8;
    private final int BUFFER_LARGE = 10;
    private final Dimension inputSize = new Dimension(400, 140);
    private final int HEIGHT = 70;
    private final int CUSTOM_MAX = 8;

    boolean searching = false;
//    volatile boolean refreshing = false;
    volatile boolean saving = false;

    //Arrays
    private ArrayList<ScannerMessage> messageList = new ArrayList<>();

    // Labels
    private JLabel searchNameLabel = new JLabel("Search Name");

    private JLabel searchTermsLabel = new JLabel("Search Terms");
    private JLabel ignoreTermsLabel = new JLabel("Ignore Terms");
    private JLabel presetMacrosLabel = new JLabel("Preset Macros");
    private JLabel customMacrosLabel = new JLabel("Custom Macros");

    private JPanel borderPanel = new JPanel(FrameManager.gridBag);
    // Panels
    private JPanel innerPanel = new ColorPanel(FrameManager.gridBag);
    private JScrollPane scrollPane = new CustomScrollPane(borderPanel);

    private JPanel upperPanel = new JPanel(FrameManager.gridBag);
    private JPanel buttonPanel = new JPanel(FrameManager.gridBag);
    private JPanel namePanel = new JPanel(FrameManager.gridBag);
    private JPanel lowerPanel = new JPanel(FrameManager.gridBag);
    private JPanel macroPanel = new JPanel(FrameManager.gridBag);

    // Upper Controls
    private JComboBox<ScannerMessage> searchCombo = new CustomCombo<>();
    private JButton searchButton = new BasicButton("Search");
    private JTextField saveTextField = new LimitTextField(32);
    private JButton saveButton = new BasicButton("Save");
    private JButton clearButton = new BasicButton("Clear");
    private JButton revertButton = new BasicButton("Revert");
    private JButton deleteButton = new BasicButton("Delete");

    // Lower Controls
    private JTextArea searchTermsInput = new JTextArea();
    private JTextArea ignoreTermsInput = new JTextArea();

    private AddRemovePanel addRemovePanel = new AddRemovePanel();
    private JButton addMacroButton = new BasicButton("Add Macro");
    private JTextField thankLeft;
    private JTextField thankRight;


    public ChatScannerWindowHMM() {
        super("Chat Scanner");
        String either = "Either";
        String left = "Left Click";
        String right = "Right Click";
        JPanel presetMacroPanel = new JPanel(FrameManager.gridBag);
        PresetMacroRow refreshInPreset = new PresetMacroRow(PreloadedImage.REFRESH.getImage(), true);
        refreshInPreset.getRow(either, "Hi, are you still interested in my [item] listed for [price]?");
        PresetMacroRow closePreset = new PresetMacroRow(PreloadedImage.CLOSE.getImage());
        closePreset.getRow(left, "Close trade");
        closePreset.getRow(right, "Close trade + all similar trades");

        PresetMacroRow invitePreset = new PresetMacroRow(PreloadedImage.INVITE.getImage(), true);
        invitePreset.getRow(either, "Invite to Party");
        PresetMacroRow warpPreset = new PresetMacroRow(PreloadedImage.WARP.getImage(), true);
        warpPreset.getRow(either, "Warp to Player");
        PresetMacroRow tradePreset = new PresetMacroRow(PreloadedImage.CART.getImage(), true);
        tradePreset.getRow(either, "Send Trade Offer");
        PresetMacroRow thankPreset = new PresetMacroRow(PreloadedImage.THUMB.getImage());
        thankLeft = thankPreset.getRow(left, "", true);
        thankRight = thankPreset.getRow(right, "", true);
        PresetMacroRow leavePreset = new PresetMacroRow(PreloadedImage.LEAVE.getImage(), true);
        leavePreset.getRow(either, "Leave Party");
        PresetMacroRow homePreset = new PresetMacroRow(PreloadedImage.HOME.getImage(), true);
        homePreset.getRow(either, "Warp to Hideout");

        PresetMacroRow usernamePreset = new PresetMacroRow("Username");
        usernamePreset.getRow(left, "/whois [username]");
        usernamePreset.getRow(right, "Open empty whisper with buyer");
        PresetMacroRow itemPreset = new PresetMacroRow("Item Name");
        itemPreset.getRow(left, "Open Stash Highlighter");
        itemPreset.getRow(right, "Ignore Item");

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.weightx = 1;

        // Button Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.top = 10;
        buttonPanel.add(deleteButton, gc);
        gc.gridx++;
        gc.insets.left = 40;
		buttonPanel.add(revertButton, gc);
		gc.gridx++;
        buttonPanel.add(clearButton, gc);
        gc.gridx++;
        buttonPanel.add(saveButton, gc);
        gc.gridx++;
//		buttonPanel.add(searchButton, gc);
        gc.gridx = 0;
        gc.weightx = 1;
        gc.insets.left = 0;
        gc.insets.top = 0;

        // Name Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 5;
        namePanel.add(saveTextField, gc);
        gc.gridy++;
        gc.insets.bottom = 0;
        namePanel.add(searchCombo, gc);
        gc.gridy = 0;

        // Macro Panel
        gc.fill = GridBagConstraints.BOTH;
        gc.insets.bottom = 2;
        presetMacroPanel.add(invitePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(warpPreset, gc);
        gc.gridy++;
        presetMacroPanel.add(tradePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(thankPreset, gc);
        gc.gridy++;
        presetMacroPanel.add(leavePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(homePreset, gc);
        gc.gridy++;
        presetMacroPanel.add(usernamePreset, gc);
//		gc.gridy++;
        gc.gridy = 0;
        gc.insets.bottom = 5;
        gc.fill = GridBagConstraints.NONE;
        macroPanel.add(presetMacroPanel, gc);
        gc.gridy++;
        gc.insets.bottom = 2;
        macroPanel.add(addMacroButton, gc);
        gc.gridy++;
        macroPanel.add(addRemovePanel, gc);
        gc.insets.bottom = 0;
        gc.gridy = 0;

        // Upper Panel
        // Row 1
        gc.fill = GridBagConstraints.BOTH;
        upperPanel.add(searchNameLabel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridx++;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = new Insets(0, 0, 5, 0);
        upperPanel.add(searchButton, gc);
        gc.insets = new Insets(0, 0, 0, 0);
        gc.anchor = GridBagConstraints.CENTER;
        gc.gridx = 0;
        gc.gridy++;
        gc.gridwidth = 2;
        gc.fill = GridBagConstraints.BOTH;
        upperPanel.add(namePanel, gc);
        gc.fill = GridBagConstraints.NONE;
        gc.gridy++;
        upperPanel.add(Box.createHorizontalStrut(400), gc);
        gc.gridwidth = 1;


        // Row 2
        gc.gridx = 0;
        gc.gridwidth = 2;
        gc.gridy++;
        upperPanel.add(buttonPanel, gc);
        gc.gridwidth = 1;

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
//		upperPanel.setMinimumSize(upperPanel.getPreferredSize());
//		lowerPanel.setMinimumSize(lowerPanel.getPreferredSize());
//		innerPanel.setMinimumSize(innerPanel.getPreferredSize());

        this.pack();
        gc.gridx = 0;
        gc.gridy = 0;
        lowerPanel.add(Box.createHorizontalStrut(upperPanel.getWidth()), gc);
        searchInputPane.setPreferredSize(searchInputPane.getPreferredSize());

        //Finalize
//		this.setAlwaysOnTop(false);
        this.setFocusableWindowState(true);
        this.setFocusable(true);
//		this.setPreferredSize(new Dimension(400, 400));
        this.pack();
        this.setSize(650, 800);

//		searchTermsInput.setPreferredSize(searchTermsInput.getPreferredSize());
//		searchInputPane.setPreferredSize(searchInputPane.getPreferredSize());
        searchTermsInput.setLineWrap(true);
        searchTermsInput.setWrapStyleWord(true);


        FrameManager.centerFrame(this);
        updateColor();
        //TODO : TEMP
        refreshWindowState();

//        searchTermsInput.getDocument().addDocumentListener(new DocumentListener() {
//            public void changedUpdate(DocumentEvent e) {
//                System.out.println("Change");
//            }
//
//            public void insertUpdate(DocumentEvent e) {
//                System.out.println("Insert");
//            }
//
//            public void removeUpdate(DocumentEvent e) {
//                System.out.println("Remove");
//            }
//        });

        saveTextField.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                refreshWindowState();
            }
        });

        searchTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                refreshWindowState();
            }
        });

        ignoreTermsInput.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                refreshWindowState();
            }
        });

        thankLeft.getDocument().addDocumentListener(new DocumentUpdateListener(){
            public void update() {
                refreshWindowState();
            }
        });

        thankRight.getDocument().addDocumentListener(new DocumentUpdateListener(){
            public void update() {
                refreshWindowState();
            }
        });

//		addResponseButton.addActionListener(e -> {
//			refreshWindowState();
//		});

        searchCombo.addActionListener(e -> {
            if(saving) {
                System.out.println("\tIgnoreAction");
                return;
            }
            System.out.println("Search Action");
            if(searchCombo.getSelectedIndex() < 0) {
                return;
            }
            ScannerMessage msg = (ScannerMessage) searchCombo.getSelectedItem();
            saveTextField.setText(msg.name);
            searchTermsInput.setText(msg.searchTermsRaw);
            ignoreTermsInput.setText(msg.ignoreTermsRaw);
            addRemovePanel.removeAll();
            for(MacroButton b : msg.macroButtons) {
                CustomMacroRow row = new CustomMacroRow(addRemovePanel);
                row.setButtonRow(b.row);
                row.setButtonImage(b.image);
                row.setTextRMB(b.leftMouseResponse);
                row.setTextRMB(b.rightMouseResponse);
                addRemovePanel.addRemoveablePanel(row);
            }
        });

        addRemovePanel.addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                System.out.println("ADDREMOVE");
                refreshWindowState();
            }

            @Override
            public void componentMoved(ComponentEvent e) {
            }

            @Override
            public void componentShown(ComponentEvent e) {
            }

            @Override
            public void componentHidden(ComponentEvent e) {
//
            }
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


        addMacroButton.addActionListener(e -> {
            addNewMacro();
        });

        revertButton.addActionListener(e -> {
            if(searchCombo.getSelectedIndex() < 0) {
                return;
            }
            ScannerMessage msg = (ScannerMessage) searchCombo.getSelectedItem();
            saveTextField.setText(msg.name);
            searchCombo.setSelectedItem(msg);
            searchTermsInput.setText(msg.searchTermsRaw);
            ignoreTermsInput.setText(msg.ignoreTermsRaw);
            thankLeft.setText(msg.thankLeft);
            thankRight.setText(msg.thankRight);
            addRemovePanel.removeAll();
            for(MacroButton b : msg.macroButtons) {
                CustomMacroRow row = new CustomMacroRow(addRemovePanel);
                row.setButtonRow(b.row);
                row.setButtonImage(b.image);
                row.setTextLMB(b.leftMouseResponse);
                row.setTextRMB(b.rightMouseResponse);
                row.setTextRMB(b.rightMouseResponse);
                addRemovePanel.addRemoveablePanel(row);
            }
            refreshWindowState();
//			this.revalidate();
//			this.repaint();
        });

        clearButton.addActionListener(e -> {
            saveTextField.setText("");
            searchCombo.setSelectedIndex(-1);
            searchTermsInput.setText("");
            ignoreTermsInput.setText("");
            addRemovePanel.removeAll();
            refreshWindowState();
//			this.revalidate();
//			this.repaint();
		});

        saveButton.addActionListener(e -> {
            saving = true;
            System.out.println("Starting Save Action...");
            if (saveTextField.getText().matches("\\s*") || searchTermsInput.getText().matches("\\s*")) {
                System.out.println("EMPTY");
                return;
            }
            System.out.println("Save1");
            addRemovePanel.saveChanges();
            ArrayList<MacroButton> macros = new ArrayList();
            for (Component c : addRemovePanel.getComponents()) {
                if (c.isVisible() && c instanceof CustomMacroRow) {
                    macros.add(((CustomMacroRow) c).getMacroData());
                }
            }
            System.out.println("Save2");
            ScannerMessage message = new ScannerMessage(saveTextField.getText(), searchTermsInput.getText(), ignoreTermsInput.getText(), thankLeft.getText(), thankRight.getText(), macros);
            if (searchCombo.getSelectedIndex() > 0) {
                System.out.println("Matching Messages : " + matchingMessages(message, (ScannerMessage) searchCombo.getSelectedItem()));
            }
            System.out.println("Save3");
            int i = 0;
            boolean dupe = false;
            while (i < searchCombo.getItemCount()) {
                System.out.println("SaveL" + i);
                System.out.println(searchCombo.getItemAt(i).getName() + " ::: " + message.name);
                if (searchCombo.getItemAt(i).getName().toLowerCase().equals(message.name.toLowerCase())) {
                    System.out.println("Dupe");
                    dupe = true;
                    boolean buttonMatch = true;
                    if (searchCombo.getItemAt(i).macroButtons.size() == message.macroButtons.size()) {
                        int n = 0;
                        for (MacroButton b : searchCombo.getItemAt(i).macroButtons) {
                            if (!MacroButton.doButtonsMatch(b, message.macroButtons.get(n))) {
                                buttonMatch = false;
                                break;
                            }
                            n++;
                        }
                    } else {
                        buttonMatch = false;
                    }
                    System.out.println("MATCH:" + buttonMatch);
                    messageList.set(i, message);
                    break;
                }
                i++;
            }
            System.out.println("SaveR");
            if (!dupe) {
                messageList.add(message);
            }
            System.out.println("SaveQ");
            refreshCombo(message);
            System.out.println("Save Button");
            refreshWindowState(message);
            refreshListeners();
            saving = false;
        });

    }

    private void refreshWindowState() {
        ArrayList<MacroButton> macros = new ArrayList();
        for (Component c : addRemovePanel.getComponents()) {
            if (c.isVisible() && c instanceof CustomMacroRow) {
                macros.add(((CustomMacroRow) c).getMacroData());
            }
        }
        ScannerMessage message = new ScannerMessage(saveTextField.getText(), searchTermsInput.getText(), thankLeft.getText(), thankRight.getText(), ignoreTermsInput.getText(), macros);
        refreshWindowState(message);
    }

    // Enabled/disabled the window controls based on how they match the saved messages
    private void refreshWindowState(ScannerMessage currentMessage) {
        System.out.println("Refreshing window...");
        // Search - currentMessage == selectedMessage
        // If !Searching
        // Delete - currentMessage == selectedMessage
        // Clear - Always
        // Save - name + terms not null
        // Revert - combo.len > 0 and not valid
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
        if(!currentMessage.name.matches("\\s*") || currentMessage.searchTermsRaw.length() > 0 || currentMessage.ignoreTermsRaw.length() > 0  || currentMessage.macroButtons.size() > 0) {
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
            }
            boolean matchingMessages = matchingMessages(currentMessage, selectedMessage);
            if (matchingMessages) {
                searchButton.setEnabled(true);
                deleteButton.setEnabled(true);
            } else {
                if(searchCombo.getSelectedIndex() > -1) {
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

    private boolean matchingMessages(ScannerMessage msg1, ScannerMessage msg2) {
        // Name
		if(msg1 == null || msg2 == null) {
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
        if(!msg1.thankLeft.equals(msg2.thankLeft)) {
            return false;
        }
        if(!msg1.thankRight.equals(msg2.thankRight)) {
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

//        for (ScannerMessage m : messageList) {
//            searchCombo.addItem(m);
//        }

//		if (msg != null) {
//			for(int i = 0; i<messageCombo.getItemCount();i++){
//				if(messageCombo.getItemAt(i).name.equals(msg.name)){
//					messageCombo.setSelectedIndex(i);
//				}
//			}
//		}
    }

    private CustomMacroRow addNewMacro() {
        System.out.println("NEWMACRO");
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
//        row.rowCombo.addItemListener(e -> {
//            System.out.println("!ROW!");
//        });
////        row.rowCombo
//        row.rowCombo.addActionListener(e -> {
//            System.out.println("#ROW");
//            refreshWindowState();
//        });
//        System.out.println(row.iconCombo.getSelectedItem());
//        row.iconCombo.addItemListener(e -> {
//            System.out.println("#ICON");
//            refreshWindowState();
//        });
//        row.iconCombo.addActionListener(e -> {
//            System.out.println("#ICON CHANGE");
//            refreshWindowState();
//        });
//        row.iconCombo.addItemListener(e -> {
//            System.out.println("#ICON CHANGE");
//            refreshWindowState();
//        });
//        thankLeft.addActionListener(e -> {
//            System.out.println("GO");
//        });
//        row.m1Text.addActionListener(e -> {
//            System.out.println("GO");
//        });
        thankLeft.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                System.out.println("#t1");
                refreshWindowState();
            }
        });
        thankRight.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                System.out.println("#t2");
                refreshWindowState();
            }
        });
        row.m1Text.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                System.out.println("#LMB");
                refreshWindowState();
            }
        });
        row.m2Text.getDocument().addDocumentListener(new DocumentUpdateListener() {
            public void update() {
                System.out.println("#RMB");
                refreshWindowState();
            }
        });
//        row.m2Text.getDocument().addDocumentListener(new DocumentUpdateListener() {
//            public void update() {
//                System.out.println("#RMB");
//                refreshWindowState();
//            }
//        });
        addRemovePanel.addRemoveablePanel(row);
        return row;
    }

    private void refreshListeners() {
        // Reset Listeners
        for(Component c : addRemovePanel.getComponents()) {
            if(c instanceof CustomMacroRow) {
                CustomMacroRow row = ((CustomMacroRow) c);
                MacroButton b = row.getMacroData();
                if(row.rowCombo.getListeners(ActionListener.class).length < 1) {
                    ((CustomMacroRow) c).rowCombo.addActionListener(e -> {
                        refreshWindowState();
                    });
                }
                if(row.iconCombo.getListeners(ActionListener.class).length < 1) {
                    ((CustomMacroRow) c).iconCombo.addActionListener(e -> {
                        refreshWindowState();
                    });
                }

//                if(row.m1Text..getDocument().get.removeDocumentListener(ActionListener.class).length == 0) {
//                    ((CustomMacroRow) c).rowCombo.addActionListener(e -> {
//                        refreshWindowState();
//                    });
//                }
//                if(row.rowCombo.getListeners(ActionListener.class).length == 0) {
//                    ((CustomMacroRow) c).rowCombo.addActionListener(e -> {
//                        refreshWindowState();
//                    });
//                }

                System.out.println("\t\tLC2:" + ((CustomMacroRow) c).rowCombo.getListeners(ActionListener.class).length);
            }
        }
    }

    @Override
    public void save() {

    }

    @Override
    public void load() {

    }
}
