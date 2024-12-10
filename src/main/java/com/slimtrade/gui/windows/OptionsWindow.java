package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.listening.IDefaultSizeAndLocation;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.options.*;
import com.slimtrade.gui.options.display.DisplayOptionPanel;
import com.slimtrade.gui.options.searching.StashSearchOptionPanel;
import com.slimtrade.gui.options.stash.StashOptionPanel;
import com.slimtrade.modules.saving.ISaveListener;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends CustomDialog implements ISaveListener, IDefaultSizeAndLocation {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final AbstractMacroOptionPanel incomingMacroPanel;
    private final AbstractMacroOptionPanel outgoingMacroPanel;
    private final IgnoreItemOptionPanel ignorePanel = new IgnoreItemOptionPanel();
    private final HotkeyOptionPanel hotkeyPanel = new HotkeyOptionPanel();
    private final OptionListPanel donationPanel = new OptionListPanel("Donate", new DonationPanel());
    private final JList<OptionListPanel> optionsList;

    private final JButton donateButton = new JButton("Donate");
    private final JButton updateButton = new JButton("Install Update");
    private final JButton saveButton = new JButton("Save");
    private final JButton revertButton = new JButton("Revert Changes");
    //    public static final String debugPanel = "Stash Tabs";
    public static final String debugPanel = null;

    public OptionsWindow() {
        super("Options");

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        incomingMacroPanel = new IncomingMacroPanel();
        outgoingMacroPanel = new OutgoingMacroPanel();

        // Panels
        OptionListPanel general = new OptionListPanel("General", new GeneralOptionPanel());
        OptionListPanel display = new OptionListPanel("Display", new DisplayOptionPanel());
        OptionListPanel audio = new OptionListPanel("Audio", new AudioOptionPanel());
        OptionListPanel stash = new OptionListPanel("Stash Tabs", new StashOptionPanel());
        OptionListPanel information = new OptionListPanel("Information", new InformationOptionPanel());
        OptionListPanel incomingMacros = new OptionListPanel("Incoming Macros", incomingMacroPanel);
        OptionListPanel outgoingMacros = new OptionListPanel("Outgoing Macros", outgoingMacroPanel);
        OptionListPanel hotkeys = new OptionListPanel("Hotkeys", hotkeyPanel);
        OptionListPanel ignoreItems = new OptionListPanel("Ignore Items", ignorePanel);
        OptionListPanel cheatSheets = new OptionListPanel("Cheat Sheets", new CheatSheetsOptionPanel());
        OptionListPanel stashSearch = new OptionListPanel("POE Searching", new StashSearchOptionPanel());
        OptionListPanel kalguurHelper = new OptionListPanel("Kalguur Helper", new KalguurOptionPanel());
        OptionListPanel debug = new OptionListPanel("Debug", new DebugOptionPanel());
        OptionListPanel[] panelList = new OptionListPanel[]{
                general, display, audio, hotkeys,
                new OptionListPanel("Trading"),
                incomingMacros, outgoingMacros, stash, ignoreItems,
                new OptionListPanel("Tools"),
                cheatSheets, stashSearch, kalguurHelper,
                new OptionListPanel(),
                information, donationPanel
        };
        if (App.debug) {
            OptionListPanel[] newList = new OptionListPanel[panelList.length + 1];
            System.arraycopy(panelList, 0, newList, 0, panelList.length);
            newList[newList.length - 1] = debug;
            panelList = newList;
        }
        optionsList = new JList<>(panelList);
        optionsList.setCellRenderer(new OptionListPanelCellRenderer());
        optionsList.setSelectionModel(new OptionListSelectionModel(panelList));
        JPanel sidebar = createSidebar();

        // Save & Revert Panel
        JPanel saveRevertPanel = new JPanel(new BorderLayout());
        JPanel saveRevertInnerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.anchor = GridBagConstraints.EAST;
        gc.weightx = 0;
        gc.fill = GridBagConstraints.NONE;
        gc.gridx = 0;
        gc.insets = new Insets(5, 0, 5, 20);
        saveRevertInnerPanel.add(revertButton, gc);
        gc.gridx++;
        saveRevertInnerPanel.add(saveButton, gc);
        saveRevertPanel.add(saveRevertInnerPanel, BorderLayout.EAST);
        JPanel displayPanel = new JPanel(new GridBagLayout());
        gc = new GridBagConstraints();
        gc.insets = new Insets(1, 1, 1, 1);

        for (OptionListPanel panel : panelList) {
            if (panel == null || panel.isSeparator) continue;
            cardPanel.add(panel.panel, panel.title);
        }
        cardPanel.add(donationPanel.panel, donationPanel.title);
        displayPanel.add(cardPanel, gc);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(saveRevertPanel, BorderLayout.SOUTH);
        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Finalize
        setMinimumSize(new Dimension(500, 400));
        pack();
        SaveManager.settingsSaveFile.registerSavableContainer(this);
        SaveManager.settingsSaveFile.addListener(this);
        addListeners();
        showDebugPanel();

    }

    private void addListeners() {
        saveButton.addActionListener(e ->
        {
            SaveManager.settingsSaveFile.saveToDisk();
            SaveManager.ignoreSaveFile.saveToDisk();
            HotkeyManager.loadHotkeys();
            revalidate();
        });
        revertButton.addActionListener(e -> SaveManager.settingsSaveFile.revertChanges());
        donateButton.addActionListener(e -> showDonationPanel());
        updateButton.addActionListener(e -> App.updateManager.runUpdateProcessFromSwing());
        optionsList.addListSelectionListener(e -> showPanel(optionsList.getSelectedValue()));
    }

    private void showDebugPanel() {
        if (debugPanel != null) {
            ListModel<OptionListPanel> model = optionsList.getModel();
            for (int i = 0; i < model.getSize(); i++) {
                OptionListPanel panel = model.getElementAt(i);
                if (panel.title.equals(debugPanel)) {
                    optionsList.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        // Top Button Panel
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        optionsList.setSelectedIndex(0);
//        if (App.debug) optionsList.setSelectedIndex(panelList.length - 1);
        topButtonPanel.add(Box.createVerticalStrut(4), BorderLayout.NORTH);
        topButtonPanel.add(optionsList, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        String appName = App.getAppInfo().fullName;
        if (App.debug) appName += "-DEV";
        bottomButtonPanel.add(new StyledLabel(appName).bold(), gc);
        gc.gridy++;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        bottomButtonPanel.add(updateButton, gc);
        gc.gridy++;
        bottomButtonPanel.add(donateButton, gc);
        gc.gridy++;
//        bottomButtonPanel.add(new JButton("Check for Updates"), gc);
        sidebar.add(topButtonPanel, BorderLayout.NORTH);
        sidebar.add(bottomButtonPanel, BorderLayout.SOUTH);
        updateButton.setVisible(false);

        return sidebar;
    }

    private void showPanel(OptionListPanel panel) {
        if (panel == null) return;
        cardLayout.show(cardPanel, panel.title);
    }

    public void reloadExampleTrades() {
        incomingMacroPanel.reloadExampleTrade();
        outgoingMacroPanel.reloadExampleTrade();
    }

    public void showDonationPanel() {
        optionsList.clearSelection();
        optionsList.setSelectedValue(donationPanel, true);
        showPanel(donationPanel);
        ((DonationPanel) donationPanel.panel).getButtonToFocus().requestFocus();
    }

    public IgnoreItemOptionPanel getIgnorePanel() {
        return ignorePanel;
    }

    public HotkeyOptionPanel getHotkeyPanel() {
        return hotkeyPanel;
    }

    public void showUpdateButton() {
        updateButton.setVisible(true);
    }

    @Override
    public void onSave() {
        SaveManager.settingsSaveFile.data.buildMacroCache();
        reloadExampleTrades();
    }

    @Override
    public void applyDefaultSizeAndLocation() {
        setSize(1000, 650);
        setLocationRelativeTo(null);
    }

}

