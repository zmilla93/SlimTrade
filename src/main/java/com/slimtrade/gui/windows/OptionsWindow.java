package com.slimtrade.gui.windows;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.options.*;
import com.slimtrade.gui.options.stash.StashOptionPanel;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends AbstractWindow {

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    private final AbstractMacroOptionPanel incomingMacroPanel;
    private final AbstractMacroOptionPanel outgoingMacroPanel;
    private IgnoreItemOptionPanel ignorePanel = new IgnoreItemOptionPanel();

    public OptionsWindow() {
        super("Options");
        setSize(900, 600);
        setLocationRelativeTo(null);
        container.setLayout(new BorderLayout());

        incomingMacroPanel = new IncomingMacroPanel();
        outgoingMacroPanel = new OutgoingMacroPanel();

        // Panels
        OptionPanel general = new OptionPanel("General", new GeneralOptionPanel());
        OptionPanel display = new OptionPanel("Display", new JPanel());
        OptionPanel audio = new OptionPanel("Audio", new AudioOptionPanel());
        OptionPanel stash = new OptionPanel("Stash Tabs", new StashOptionPanel());
//        OptionPanel history = new OptionPanel("History", new HistoryOptionPanel());
        OptionPanel information = new OptionPanel("Information", new InformationOptionPanel());
        OptionPanel incomingMacros = new OptionPanel("Incoming Macros", incomingMacroPanel);
        OptionPanel outgoingMacros = new OptionPanel("Outgoing Macros", outgoingMacroPanel);
        OptionPanel hotkeys = new OptionPanel("Hotkeys", new HotkeyOptionPanel());
        OptionPanel ignoreItems = new OptionPanel("Ignore Items", ignorePanel);
        OptionPanel cheatSheets = new OptionPanel("Cheat Sheets", new CheatSheetsOptionPanel());
        OptionPanel stashSorting = new OptionPanel("Stash Sorting", new StashSortingOptionPanel());
        OptionPanel[] panelList = new OptionPanel[]{general, display, audio, stash, incomingMacros, outgoingMacros, hotkeys, ignoreItems, cheatSheets, stashSorting, information};

        JPanel sidebar = createSidebar(panelList);

        // Save & Revert Panel
        JPanel saveRevertPanel = new JPanel(new BorderLayout());
        JPanel saveRevertInnerPanel = new JPanel(new GridBagLayout());
        JButton revertButton = new JButton("Revert Changes");
        JButton saveButton = new JButton("Save");
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
        displayPanel.add(cardPanel, gc);
        container.add(sidebar, BorderLayout.WEST);
        container.add(saveRevertPanel, BorderLayout.SOUTH);
        container.add(cardPanel, BorderLayout.CENTER);

        // Listeners
        saveButton.addActionListener(e ->
        {
            SaveManager.settingsSaveFile.saveToDisk();
            SaveManager.ignoreSaveFile.saveToDisk();
            HotkeyManager.loadHotkeys();
            reloadExampleTrades();
            revalidate();
        });

        revertButton.addActionListener(e -> SaveManager.settingsSaveFile.revertChanges());

        // Finalize
        setMinimumSize(new Dimension(300, 200));
        pack();
        setSize(900, 600);
        SaveManager.settingsSaveFile.registerSavableContainer(this);
    }

    private JPanel createSidebar(OptionPanel[] panelList) {
        JPanel sidebar = new JPanel(new BorderLayout());
        // Top Button Panel
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        JList<OptionPanel> optionsList = new JList(panelList);
        optionsList.setSelectedIndex(0);
        topButtonPanel.add(optionsList, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        bottomButtonPanel.add(new JLabel("SlimTrade v0.4.0"), gc);
        gc.gridy++;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        bottomButtonPanel.add(new JButton("Donate"), gc);
        gc.gridy++;
        bottomButtonPanel.add(new JButton("Check for Updates"), gc);

        sidebar.add(topButtonPanel, BorderLayout.NORTH);
        sidebar.add(bottomButtonPanel, BorderLayout.SOUTH);

        // Listeners
        optionsList.addListSelectionListener(e -> ShowPanel(optionsList.getSelectedValue()));
        return sidebar;
    }

    private void ShowPanel(OptionPanel panel) {
        cardLayout.show(cardPanel, panel.title);
    }

    public void reloadExampleTrades() {
        incomingMacroPanel.reloadExampleTrade();
        outgoingMacroPanel.reloadExampleTrade();
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
