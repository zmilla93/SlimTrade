package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.StyledLabel;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.options.*;
import com.slimtrade.gui.options.searching.StashSearchOptionPanel;
import com.slimtrade.gui.options.stash.StashOptionPanel;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends CustomDialog {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel cardPanel = new JPanel(cardLayout);

    private final AbstractMacroOptionPanel incomingMacroPanel;
    private final AbstractMacroOptionPanel outgoingMacroPanel;
    private final IgnoreItemOptionPanel ignorePanel = new IgnoreItemOptionPanel();
    private final GeneralOptionPanel generalOptionPanel = new GeneralOptionPanel();
    private final OptionPanel donate = new OptionPanel("Donate", new DonationPanel());
    private final JList<OptionPanel> optionsList;

    private final JButton donateButton = new JButton("Donate");
    private final JButton saveButton = new JButton("Save");
    private final JButton revertButton = new JButton("Revert Changes");

    public OptionsWindow() {
        super("Options");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        incomingMacroPanel = new IncomingMacroPanel();
        outgoingMacroPanel = new OutgoingMacroPanel();

        // Panels
        OptionPanel general = new OptionPanel("General", generalOptionPanel);
        OptionPanel display = new OptionPanel("Display", new DisplayOptionPanel());
        OptionPanel audio = new OptionPanel("Audio", new AudioOptionPanel());
        OptionPanel stash = new OptionPanel("Stash Tabs", new StashOptionPanel());
        OptionPanel information = new OptionPanel("Information", new InformationOptionPanel());
        OptionPanel incomingMacros = new OptionPanel("Incoming Macros", incomingMacroPanel);
        OptionPanel outgoingMacros = new OptionPanel("Outgoing Macros", outgoingMacroPanel);
        OptionPanel hotkeys = new OptionPanel("Hotkeys", new HotkeyOptionPanel());
        OptionPanel ignoreItems = new OptionPanel("Ignore Items", ignorePanel);
        OptionPanel cheatSheets = new OptionPanel("Cheat Sheets", new CheatSheetsOptionPanel());
        OptionPanel stashSearch = new OptionPanel("Searching", new StashSearchOptionPanel());
        OptionPanel debug = new OptionPanel("Debug", new DebugOptionPanel());
        OptionPanel[] panelList = new OptionPanel[]{general, display, audio, stash, incomingMacros, outgoingMacros, hotkeys, ignoreItems, cheatSheets, stashSearch, information, donate};
        if (App.debug) {
            OptionPanel[] newList = new OptionPanel[panelList.length + 1];
            System.arraycopy(panelList, 0, newList, 0, panelList.length);
            newList[newList.length - 1] = debug;
            panelList = newList;
        }
        optionsList = new JList<>(panelList);
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

        for (OptionPanel panel : panelList) {
            cardPanel.add(panel.panel, panel.title);
        }
        cardPanel.add(donate.panel, donate.title);
        displayPanel.add(cardPanel, gc);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(saveRevertPanel, BorderLayout.SOUTH);
        contentPanel.add(cardPanel, BorderLayout.CENTER);

        // Finalize
//        showPanel(stashSearch);
        setMinimumSize(new Dimension(500, 400));
        pack();
        // FIXME : Add max size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.round(screenSize.width * 0.5), (int) Math.round(screenSize.height * 0.6));
        setLocationRelativeTo(null);
        SaveManager.settingsSaveFile.registerSavableContainer(this);
        addListeners();
    }

    private void addListeners() {
        saveButton.addActionListener(e ->
        {
            SaveManager.settingsSaveFile.saveToDisk();
            SaveManager.ignoreSaveFile.saveToDisk();
            HotkeyManager.loadHotkeys();
            reloadExampleTrades();
            revalidate();
        });
        revertButton.addActionListener(e -> SaveManager.settingsSaveFile.revertChanges());
        donateButton.addActionListener(e -> showDonationPanel());
        optionsList.addListSelectionListener(e -> showPanel(optionsList.getSelectedValue()));
    }

    private JPanel createSidebar() {
        JPanel sidebar = new JPanel(new BorderLayout());
        // Top Button Panel
        JPanel topButtonPanel = new JPanel(new BorderLayout());

        optionsList.setSelectedIndex(0);
//        if (App.debug) optionsList.setSelectedIndex(panelList.length - 1);
        topButtonPanel.add(optionsList, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        bottomButtonPanel.add(new StyledLabel(App.appInfo.fullName).bold(), gc);
        gc.gridy++;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        bottomButtonPanel.add(donateButton, gc);
        gc.gridy++;
//        bottomButtonPanel.add(new JButton("Check for Updates"), gc);
        sidebar.add(topButtonPanel, BorderLayout.NORTH);
        sidebar.add(bottomButtonPanel, BorderLayout.SOUTH);

        return sidebar;
    }

    private void showPanel(OptionPanel panel) {
        if (panel == null) return;
        cardLayout.show(cardPanel, panel.title);
    }

    public void reloadExampleTrades() {
        incomingMacroPanel.reloadExampleTrade();
        outgoingMacroPanel.reloadExampleTrade();
    }

    public void showDonationPanel() {
        optionsList.clearSelection();
        optionsList.setSelectedValue(donate, true);
        showPanel(donate);
        ((DonationPanel) donate.panel).getDonateButton().requestFocus();
    }

    public void refreshCharacterName() {
        generalOptionPanel.getBasicsPanel().refreshCharacterName();
    }

    public IgnoreItemOptionPanel getIgnorePanel() {
        return ignorePanel;
    }

}

class OptionPanel {
    String title;
    JPanel panel;

    public OptionPanel(String title, JPanel panel) {
        this.title = title;
        this.panel = panel;
    }

    @Override
    public String toString() {
        return title;
    }
}
