package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.chatparser.IChatScannerToggleListener;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;
import com.slimtrade.gui.windows.CustomDialog;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;

public class ChatScannerWindow extends CustomDialog implements ISavable, IDefaultSizeAndLocation {

    private static final String START_SCANNING = "Start Scanning";
    private static final String STOP_SCANNING = "Stop Scanning";
    public static final int TEXT_COLUMNS = 12;

    private final JList<ChatScannerCustomizerPanel> entryList = new JList<>();
    private final JButton infoButton = new JButton("Info");
    private final JButton newEntryButton = new JButton("New Entry");
    private final JButton scanButton = new JButton(START_SCANNING);

    private final CardLayout cardLayout = new CardLayout();

    // Panels
    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final ChatScannerNewEntryPanel newEntryPanel = new ChatScannerNewEntryPanel();
    private final ChatScannerSearchingPanel searchingPanel = new ChatScannerSearchingPanel();
    private final ChatScannerInfoPanel infoPanel = new ChatScannerInfoPanel();
    private final ChatScannerRenamePanel renamePanel = new ChatScannerRenamePanel();
    private final ChatScannerDeletePanel deletePanel = new ChatScannerDeletePanel();

    // Panel Names
    private static final String ENTRY_PANEL_TITLE = "SLIMTRADE::NEW_ENTRY_PANEL";
    private static final String SEARCHING_PANEL_TITLE = "SLIMTRADE::SEARCHING_PANEL";
    private static final String INFO_PANEL_TITLE = "SLIMTRADE::INFO_PANEL";
    private static final String RENAME_PANEL_TITLE = "SLIMTRADE::RENAME_PANEL";
    private static final String DELETE_PANEL_TITLE = "SLIMTRADE::DELETE_PANEL";

    private final ArrayList<ChatScannerCustomizerPanel> panels = new ArrayList<>();
    private final ArrayList<IChatScannerToggleListener> toggleListeners = new ArrayList<>();

    private final JButton revertButton = new JButton("Revert Changes");
    private final JButton saveButton = new JButton("Save");

    public ChatScannerWindow() {
        super("Chat Scanner");
        cardPanel.setLayout(cardLayout);
        cardPanel.add(infoPanel, INFO_PANEL_TITLE);
        cardPanel.add(newEntryPanel, ENTRY_PANEL_TITLE);
        cardPanel.add(searchingPanel, SEARCHING_PANEL_TITLE);
        cardPanel.add(renamePanel, RENAME_PANEL_TITLE);
        cardPanel.add(deletePanel, DELETE_PANEL_TITLE);
        cardLayout.show(cardPanel, INFO_PANEL_TITLE);
        scanButton.setEnabled(false);

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(infoButton, BorderLayout.NORTH);
        buttonPanel.add(newEntryButton, BorderLayout.CENTER);
        buttonPanel.add(scanButton, BorderLayout.SOUTH);

        // Sidebar
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(entryList, BorderLayout.NORTH);
        sidebar.add(buttonPanel, BorderLayout.SOUTH);

        // Tabbed Panel

        // Button Panel
        JPanel mainButtonBuffer = new JPanel(new BorderLayout());
        GridBagConstraints gc = ZUtil.getGC();
        JPanel mainButtonPanel = new JPanel(new GridBagLayout());
        mainButtonPanel.add(revertButton, gc);
        gc.gridx++;
        mainButtonPanel.add(saveButton, gc);
        mainButtonBuffer.add(mainButtonPanel, BorderLayout.EAST);

        // Container
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(new JSeparator(), BorderLayout.NORTH);
        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(mainButtonBuffer, BorderLayout.SOUTH);

        // Finalize
        setTitle("Chat Scanner");
        pack();

        SaveManager.chatScannerSaveFile.registerSavableContainer(this);
        addListeners();

    }

    private void addListeners() {
        infoButton.addActionListener(e -> cardLayout.show(cardPanel, INFO_PANEL_TITLE));
        newEntryButton.addActionListener(e -> {
            newEntryPanel.setError(null);
            newEntryPanel.clearName();
            cardLayout.show(cardPanel, ENTRY_PANEL_TITLE);
        });
        scanButton.addActionListener(e -> toggleSearch());
        entryList.addListSelectionListener(e -> {
            ChatScannerCustomizerPanel listPanel = entryList.getSelectedValue();
            if (listPanel == null) {
                scanButton.setEnabled(false);
            } else {
                scanButton.setEnabled(true);
                cardLayout.show(cardPanel, listPanel.getTitle());
            }
        });
        newEntryPanel.getCreateEntryButton().addActionListener(e -> {
            String name = newEntryPanel.getInputText();
            String error = tryCreateEntry(name);
            newEntryPanel.setError(error);
            if (error == null) showEntry(name);
        });
        newEntryPanel.getCancelButton().addActionListener(e -> {
            cardLayout.show(cardPanel, INFO_PANEL_TITLE);
            rootPane.requestFocus();
        });
        saveButton.addActionListener(e -> SaveManager.chatScannerSaveFile.saveToDisk());
        revertButton.addActionListener(e -> revertAll());

        // Clear List Selection
        ActionListener clearListSelectionListener = e -> entryList.clearSelection();
        infoButton.addActionListener(clearListSelectionListener);
        newEntryButton.addActionListener(clearListSelectionListener);
    }

    private void revertAll() {
        int selectedIndex = entryList.getSelectedIndex();
        entryList.clearSelection();
        SaveManager.chatScannerSaveFile.revertChanges();
        if (selectedIndex > -1) entryList.setSelectedIndex(selectedIndex);
    }

    /**
     * Tries to create a new entry, checking for duplicate names.
     *
     * @param name Entry name
     * @return Error message, null if none
     */
    public String tryCreateEntry(String name) {
        name = ZUtil.cleanString(name);
        if (name.length() == 0) return "Enter a name for your new search entry!";
        if (isDuplicateName(name)) return "An entry with that name already exists!";
        newEntryPanel.clearName();
        ChatScannerCustomizerPanel panel = new ChatScannerCustomizerPanel(name);
        panels.add(panel);
        updateList();
        cardPanel.add(panel, name);
        return null;
    }

    public void toggleSearch() {
        if (SaveManager.chatScannerSaveFile.data.searching) stopSearch();
        else tryStartSearch();
    }

    public void tryStartSearch() {
        int[] values = entryList.getSelectedIndices();
        if (values.length == 0) return;
        ArrayList<ChatScannerEntry> activeEntries = new ArrayList<>(values.length);
        for (int i : values) {
            ChatScannerCustomizerPanel panel = entryList.getModel().getElementAt(i);
            activeEntries.add(panel.getData());
        }
        SaveManager.chatScannerSaveFile.data.searching = true;
        SaveManager.chatScannerSaveFile.data.activeSearches = activeEntries;
        SaveManager.chatScannerSaveFile.data.selectedIndexes = values;
        SaveManager.chatScannerSaveFile.saveToDisk();
        cardLayout.show(cardPanel, SEARCHING_PANEL_TITLE);
        enableComponents(false);
        for (IChatScannerToggleListener listener : toggleListeners) listener.onChatScannerToggle(true);
    }

    public void stopSearch() {
        SaveManager.chatScannerSaveFile.data.searching = false;
        SaveManager.chatScannerSaveFile.saveToDisk(false);
        ChatScannerCustomizerPanel selectedPanel = entryList.getSelectedValue();
        if (selectedPanel == null) cardLayout.show(cardPanel, SEARCHING_PANEL_TITLE);
        else cardLayout.show(cardPanel, selectedPanel.getTitle());
        enableComponents(true);
        for (IChatScannerToggleListener listener : toggleListeners) listener.onChatScannerToggle(false);
    }

    private void enableComponents(boolean enable) {
        if (enable) scanButton.setText(START_SCANNING);
        else scanButton.setText(STOP_SCANNING);
        entryList.setEnabled(enable);
        infoButton.setEnabled(enable);
        newEntryButton.setEnabled(enable);
        saveButton.setEnabled(enable);
        revertButton.setEnabled(enable);
    }

    public void showRenamePanel() {
        ChatScannerCustomizerPanel selectedPanel = entryList.getSelectedValue();
        if (selectedPanel == null) return;
        cardLayout.show(cardPanel, RENAME_PANEL_TITLE);
        entryList.clearSelection();
        getRootPane().requestFocus();
        renamePanel.setCurrentName(selectedPanel.getTitle());
        renamePanel.setError(null);
    }

    public void showDeletePanel() {
        ChatScannerCustomizerPanel selectedPanel = entryList.getSelectedValue();
        if (selectedPanel == null) return;
        cardLayout.show(cardPanel, DELETE_PANEL_TITLE);
        entryList.clearSelection();
        getRootPane().requestFocus();
        deletePanel.setCurrentName(selectedPanel.getTitle());
    }

    public void showEntry(String name) {
        for (ChatScannerCustomizerPanel listPanel : panels) {
            if (listPanel.getTitle().equals(name)) {
                cardLayout.show(cardPanel, listPanel.getTitle());
                entryList.setSelectedIndex(panels.indexOf(listPanel));
                getRootPane().requestFocus();
                return;
            }
        }
    }

    public String tryRenameEntry(String oldName, String newName) {
        newName = ZUtil.cleanString(newName);
        if (newName.equals("")) return "Please enter a valid name!";
        if (isDuplicateName(newName, oldName)) return "An entry with that name already exists!";
        ChatScannerCustomizerPanel panel = null;
        for (ChatScannerCustomizerPanel listPanel : panels) {
            if (listPanel.getTitle().equals(oldName)) {
                panel = listPanel;
                break;
            }
        }
        if (panel == null) return null;
        panels.remove(panel);
        cardPanel.remove(panel);
        cardLayout.show(cardPanel, RENAME_PANEL_TITLE);
        panel.setTitle(newName);
        panels.add(panel);
        cardPanel.add(panel, panel.getTitle());
        updateList();
        showEntry(newName);
        return null;
    }

    public void deleteEntry(String name) {
        ChatScannerCustomizerPanel panel = null;
        for (ChatScannerCustomizerPanel listPanel : panels) {
            if (listPanel.getTitle().equals(name)) {
                panel = listPanel;
                break;
            }
        }
        if (panel == null) return;
        panels.remove(panel);
        cardPanel.remove(panel);
        cardLayout.show(cardPanel, INFO_PANEL_TITLE);
        updateList();
        getRootPane().requestFocus();
    }

    private void updateList() {
        ChatScannerCustomizerPanel[] sortedPanels = panels.toArray(new ChatScannerCustomizerPanel[0]);
        Arrays.sort(sortedPanels);
        panels.clear();
        panels.addAll(Arrays.asList(sortedPanels));
        entryList.setListData(sortedPanels);
        entryList.revalidate();
        entryList.repaint();
    }

    private boolean isDuplicateName(String name) {
        return isDuplicateName(name, null);
    }

    private boolean isDuplicateName(String name, String nameToIgnore) {
        name = name.toLowerCase();
        if (nameToIgnore != null) nameToIgnore = nameToIgnore.toLowerCase();
        for (ChatScannerCustomizerPanel listPanel : panels) {
            String listPanelName = listPanel.getTitle().toLowerCase();
            if (listPanelName.equals(nameToIgnore)) continue;
            if (listPanelName.equals(name)) return true;
        }
        return false;
    }

    public void addChatScannerToggleListener(IChatScannerToggleListener listener) {
        toggleListeners.add(listener);
    }

    @Override
    public void save() {
        ArrayList<ChatScannerEntry> scannerEntries = new ArrayList<>();
        for (ChatScannerCustomizerPanel panel : panels) {
            ChatScannerEntry entry = panel.getData();
            scannerEntries.add(entry);
            panel.reloadExample();
        }
        SaveManager.chatScannerSaveFile.data.scannerEntries = scannerEntries;
    }

    @Override
    public void load() {
        panels.clear();
        for (ChatScannerEntry entry : SaveManager.chatScannerSaveFile.data.scannerEntries) {
            ChatScannerCustomizerPanel panel = new ChatScannerCustomizerPanel(entry);
            panels.add(panel);
            cardPanel.add(panel, entry.title);
        }
        updateList();
        if (SaveManager.chatScannerSaveFile.data.selectedIndexes != null)
            entryList.setSelectedIndices(SaveManager.chatScannerSaveFile.data.selectedIndexes);
        if (SaveManager.chatScannerSaveFile.data.searching) tryStartSearch();
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

}
