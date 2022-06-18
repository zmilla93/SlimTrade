package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.data.ListPanel;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.options.IncomingMacroPanel;
import com.slimtrade.gui.windows.CustomDialog;
import com.slimtrade.modules.colortheme.components.AdvancedButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ChatScannerWindow extends CustomDialog implements ISavable {

    private static final String START_SCANNING = "Start Scanning";
    private static final String STOP_SCANNING = "Stop Scanning";

    //    private final JTabbedPane tabbedPane = new JTabbedPane();
    private final JList<ListPanel<ChatScannerCustomizerPanel>> entryList = new JList<>();
    private final JButton infoButton = new JButton("Info");
    private final JButton newSearchButton = new JButton("New Entry");
    private final AdvancedButton scanButton = new AdvancedButton(START_SCANNING);

    private CardLayout cardLayout = new CardLayout();

    // Panels
    private final JPanel cardPanel = new JPanel(new CardLayout());
    private final ChatScannerNewEntryPanel newEntryPanel = new ChatScannerNewEntryPanel();
    private final ChatScannerSearchingPanel searchingPanel = new ChatScannerSearchingPanel();
    private final ChatScannerInfoPanel infoPanel = new ChatScannerInfoPanel();

    // Panel Names
    private static final String ENTRY_PANEL_TITLE = "SLIMTRADE::NEW_ENTRY_PANEL";
    private static final String SEARCHING_PANEL_TITLE = "SLIMTRADE::SEARCHING_PANEL";
    private static final String INFO_PANEL_TITLE = "SLIMTRADE::INFO_PANEL";

    private final ArrayList<ListPanel<ChatScannerCustomizerPanel>> panels = new ArrayList<>();

    private final JPanel mainButtonPanel = new JPanel(new GridBagLayout());
    private JButton revertButton = new JButton("Revert Changes");
    private JButton saveButton = new JButton("Save");
    private boolean searching;

    public ChatScannerWindow() {
        super("Chat Scanner");
        cardPanel.setLayout(cardLayout);
        cardPanel.add(newEntryPanel, ENTRY_PANEL_TITLE);
        cardPanel.add(searchingPanel, SEARCHING_PANEL_TITLE);
        cardPanel.add(infoPanel, INFO_PANEL_TITLE);

        // Button Panel
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.add(infoButton, BorderLayout.NORTH);
        buttonPanel.add(newSearchButton, BorderLayout.CENTER);
        buttonPanel.add(scanButton, BorderLayout.SOUTH);

        // Sidebar
        JPanel sidebar = new JPanel(new BorderLayout());
        sidebar.add(entryList, BorderLayout.NORTH);
        sidebar.add(buttonPanel, BorderLayout.SOUTH);

        // Tabbed Panel
        IncomingMacroPanel p = new IncomingMacroPanel();
        p.reloadExampleTrade();

        // Button Panel
        JPanel mainButtonBuffer = new JPanel(new BorderLayout());
        GridBagConstraints gc = ZUtil.getGC();
        mainButtonPanel.add(revertButton, gc);
        gc.gridx++;
        mainButtonPanel.add(saveButton, gc);
        mainButtonBuffer.add(mainButtonPanel, BorderLayout.EAST);

        // Container
//        Container contentPanel = getContentPane();
        contentPanel.setLayout(new BorderLayout());
//        contentPanel.add(new ChatScannerCustomizerPanel(), BorderLayout.CENTER);
        contentPanel.add(cardPanel, BorderLayout.CENTER);
        contentPanel.add(new JSeparator(), BorderLayout.NORTH);
        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(mainButtonBuffer, BorderLayout.SOUTH);

        // Finalize
        setTitle("Chat Scanner");
        pack();
        setSize(800, 600);
        SaveManager.chatScannerSaveFile.registerSavableContainer(this);
        addListeners();
    }

    private void addListeners() {
        ChatScannerWindow self = this;
        infoButton.addActionListener(e -> cardLayout.show(cardPanel, INFO_PANEL_TITLE));
        newSearchButton.addActionListener(e -> cardLayout.show(cardPanel, ENTRY_PANEL_TITLE));
        scanButton.addActionListener(e -> {
            toggleSearch();
        });
        entryList.addListSelectionListener(e -> {
            ListPanel<ChatScannerCustomizerPanel> listPanel = entryList.getSelectedValue();
            if (listPanel == null) {
                scanButton.setEnabled(false);
            } else {
                scanButton.setEnabled(true);
                cardLayout.show(cardPanel, listPanel.title);
            }
        });
        newEntryPanel.getCreateEntryButton().addActionListener(e -> newEntryPanel.setError(tryCreateEntry(newEntryPanel.getInputName())));
        saveButton.addActionListener(e -> SaveManager.chatScannerSaveFile.saveToDisk());
        revertButton.addActionListener(e -> revertAll());

        // Clear List Selection
        ActionListener clearListSelectionListener = e -> entryList.clearSelection();
        infoButton.addActionListener(clearListSelectionListener);
        newSearchButton.addActionListener(clearListSelectionListener);
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
        name = name.trim();
        if (name.length() == 0) return null;
        for (ListPanel<ChatScannerCustomizerPanel> listPanel : panels) {
            if (listPanel.title.equals(name)) {
                return "An entry with that name already exists!";
            }
        }
        ChatScannerCustomizerPanel panel = new ChatScannerCustomizerPanel(name);
        ListPanel<ChatScannerCustomizerPanel> listPanel = new ListPanel<>(name, panel);
        panels.add(listPanel);
        entryList.setListData((ListPanel<ChatScannerCustomizerPanel>[]) panels.toArray());
        cardPanel.add(panel, name);
        return null;
    }

    public void toggleSearch() {
        if (SaveManager.chatScannerSaveFile.data.searching) {
            stopSearch();
        } else {
            tryStartSearch();
        }
    }

    public void tryStartSearch() {
        int[] values = entryList.getSelectedIndices();
        if (values.length == 0) {
            // FIXME :
            return;
        }
        ArrayList<ChatScannerEntry> activeEntries = new ArrayList<>(values.length);
        for (int i = 0; i < values.length; i++) {
            activeEntries.add(panels.get(i).component.getData());
        }
        SaveManager.chatScannerSaveFile.data.searching = true;
        SaveManager.chatScannerSaveFile.data.activeSearches = activeEntries;

        scanButton.setActive(!scanButton.isActive());
//        entryList.setEnabled(!scanButton.isActive());
        cardLayout.show(cardPanel, SEARCHING_PANEL_TITLE);
        enableComponents(false);
    }

    public void stopSearch() {
        SaveManager.chatScannerSaveFile.data.searching = false;

        scanButton.setActive(!scanButton.isActive());
//        entryList.setEnabled(!scanButton.isActive());
        cardLayout.show(cardPanel, SEARCHING_PANEL_TITLE);
        enableComponents(true);
    }

    private void enableComponents(boolean enable) {
        if (enable) scanButton.setText(START_SCANNING);
        else scanButton.setText(STOP_SCANNING);
        scanButton.setActive(!enable);
        entryList.setEnabled(enable);
        infoButton.setEnabled(enable);
        newSearchButton.setEnabled(enable);
        saveButton.setEnabled(enable);
        revertButton.setEnabled(enable);
        if (enable) {
            cardLayout.show(cardPanel, INFO_PANEL_TITLE);
        } else {
            cardLayout.show(cardPanel, SEARCHING_PANEL_TITLE);
        }

    }

    public boolean isSearching() {
        return searching;
    }

    @Override
    public void save() {
        ArrayList<ChatScannerEntry> scannerEntries = new ArrayList<>();
        for (ListPanel<ChatScannerCustomizerPanel> listPanel : panels) {
            scannerEntries.add(listPanel.component.getData());
            System.out.println(listPanel.component.getData().title);
            System.out.println(listPanel.component.getData().searchTermsRaw);
            System.out.println(listPanel.component.getData().ignoreTermsRaw);
            listPanel.component.reloadExample();
        }
        SaveManager.chatScannerSaveFile.data.scannerEntries = scannerEntries;
    }

    @Override
    public void load() {
        panels.clear();
        for (ChatScannerEntry entry : SaveManager.chatScannerSaveFile.data.scannerEntries) {
            ChatScannerCustomizerPanel panel = new ChatScannerCustomizerPanel(entry);
            panels.add(new ListPanel<>(entry.title, panel));
            cardPanel.add(panel, entry.title);
        }
        entryList.setListData(panels.toArray(ListPanel[]::new));
    }
}
