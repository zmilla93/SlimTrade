package com.slimtrade.gui.options;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.AddRemoveContainer;
import com.slimtrade.gui.components.ButtonPanel;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.options.cheatsheets.CheatSheetRow;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class CheatSheetsOptionPanel extends AbstractOptionPanel implements ISavable {

    private JButton browseButton = new JButton("Open Images Folder");
    private JButton refreshButton = new JButton("Refresh");
    private AddRemoveContainer cheatSheetContainer = new AddRemoveContainer();

    public CheatSheetsOptionPanel() {
        // Setup
        JPanel buttonPanel = new ButtonPanel();
        GridBagConstraints gc = ZUtil.getGC();
        buttonPanel.add(browseButton);
        gc.gridx++;
        buttonPanel.add(refreshButton);

        // Build
        addHeader("Setup");
        addPanel(new JLabel("Add images to the images folder, refresh, then set a hotkey for each cheat sheet."));
        addPanel(new JLabel("Hold SHIFT when moving a window to lock it to the current monitor."));
        addPanel(buttonPanel);
        addVerticalStrut();
        addHeader("Cheat Sheets");
        addPanel(cheatSheetContainer);
        addListeners();
        rebuildList();
    }

    private void rebuildList() {
        File imagesFolder = new File(SaveManager.getImagesDirectory());
        cheatSheetContainer.removeAll();
        if (imagesFolder.exists() && imagesFolder.isDirectory()) {
            File[] files = imagesFolder.listFiles();
            if (files == null) return;
            for (File file : files) {
                System.out.println(file.getName());
                cheatSheetContainer.add(new CheatSheetRow(cheatSheetContainer, new CheatSheetData(file.getName(), null)));
            }
        }
        cheatSheetContainer.revalidate();
        cheatSheetContainer.repaint();
    }

    private void addListeners() {
        browseButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getImagesDirectory()));
        refreshButton.addActionListener(e -> rebuildList());
    }

    @Override
    public void save() {
        ArrayList<CheatSheetData> cheatSheets = new ArrayList<>();
        for (Component c : cheatSheetContainer.getComponents()) {
            if (c instanceof CheatSheetRow) {
                cheatSheets.add(((CheatSheetRow) c).getData());
            }
        }
        SaveManager.settingsSaveFile.data.cheatSheets = cheatSheets;
        FrameManager.buildCheatSheetWindows();
    }

    @Override
    public void load() {

    }
}
