package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.components.Visibility;
import com.slimtrade.gui.managers.HotkeyManager;
import com.slimtrade.gui.options.*;
import com.slimtrade.gui.options.stash.StashOptionPanel;

import javax.swing.*;
import java.awt.*;

public class OptionsWindow extends CustomDialog {

    CardLayout cardLayout = new CardLayout();
    JPanel cardPanel = new JPanel(cardLayout);

    private final AbstractMacroOptionPanel incomingMacroPanel;
    private final AbstractMacroOptionPanel outgoingMacroPanel;
    private IgnoreItemOptionPanel ignorePanel = new IgnoreItemOptionPanel();
    private Visibility visibility;
    private GeneralOptionPanel generalOptionPanel = new GeneralOptionPanel();

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
//        OptionPanel history = new OptionPanel("History", new HistoryOptionPanel());
        OptionPanel information = new OptionPanel("Information", new InformationOptionPanel());
        OptionPanel incomingMacros = new OptionPanel("Incoming Macros", incomingMacroPanel);
        OptionPanel outgoingMacros = new OptionPanel("Outgoing Macros", outgoingMacroPanel);
        OptionPanel hotkeys = new OptionPanel("Hotkeys", new HotkeyOptionPanel());
        OptionPanel ignoreItems = new OptionPanel("Ignore Items", ignorePanel);
        OptionPanel cheatSheets = new OptionPanel("Cheat Sheets", new CheatSheetsOptionPanel());
        OptionPanel stashSorting = new OptionPanel("Stash Sorting", new StashSortingOptionPanel());
        OptionPanel debug = new OptionPanel("Debug", new DebugOptionPanel());
        OptionPanel[] panelList = new OptionPanel[]{general, display, audio, stash, incomingMacros, outgoingMacros, hotkeys, ignoreItems, cheatSheets, stashSorting, information};
        if (App.debug) {
            OptionPanel[] newList = new OptionPanel[panelList.length + 1];
            System.arraycopy(panelList, 0, newList, 0, panelList.length);
            newList[newList.length - 1] = debug;
            panelList = newList;
        }
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
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(sidebar, BorderLayout.WEST);
        contentPanel.add(saveRevertPanel, BorderLayout.SOUTH);
        contentPanel.add(cardPanel, BorderLayout.CENTER);

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
        if (App.debug) ShowPanel(debug);
        setMinimumSize(new Dimension(500, 400));
        pack();
        // FIXME : Add max size
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize((int) Math.round(screenSize.width * 0.5), (int) Math.round(screenSize.height * 0.6));
        setLocationRelativeTo(null);
        SaveManager.settingsSaveFile.registerSavableContainer(this);
    }

    private JPanel createSidebar(OptionPanel[] panelList) {
        JPanel sidebar = new JPanel(new BorderLayout());
        // Top Button Panel
        JPanel topButtonPanel = new JPanel(new BorderLayout());
        JList<OptionPanel> optionsList = new JList<>(panelList);
        optionsList.setSelectedIndex(0);
        if (App.debug) optionsList.setSelectedIndex(panelList.length - 1);
        topButtonPanel.add(optionsList, BorderLayout.CENTER);

        // Bottom Button Panel
        JPanel bottomButtonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        bottomButtonPanel.add(new JLabel("SlimTrade v0.4.0-DEV"), gc);
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
        if (panel == null) return;
        cardLayout.show(cardPanel, panel.title);
    }

    public void reloadExampleTrades() {
        incomingMacroPanel.reloadExampleTrade();
        outgoingMacroPanel.reloadExampleTrade();
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
