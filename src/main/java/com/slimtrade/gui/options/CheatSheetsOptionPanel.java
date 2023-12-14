package com.slimtrade.gui.options;

import com.slimtrade.core.data.CheatSheetData;
import com.slimtrade.core.hotkeys.HotkeyData;
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
        addComponent(new JLabel("Add images to the images folder, refresh, set a hotkey, then save."));
        addComponent(new JLabel("Supports png, jpg, and gif files."));
        addComponent(new JLabel("Hold SHIFT when moving a window to lock it to the current monitor."));
        addComponent(buttonPanel);
        addVerticalStrut();
        addHeader("Cheat Sheets");
        addComponent(cheatSheetContainer);
        addListeners();
    }

    private void rebuildFromFolder() {
        File imagesFolder = new File(SaveManager.getImagesDirectory());
        cheatSheetContainer.removeAll();
        if (imagesFolder.exists() && imagesFolder.isDirectory()) {
            File[] files = imagesFolder.listFiles();
            if (files == null) return;
            for (File file : files) {
                HotkeyData hotkeyData = null;
                for (CheatSheetData existing : SaveManager.settingsSaveFile.data.cheatSheets) {
                    if (existing.fileName.equals(file.getName())) {
                        hotkeyData = existing.hotkeyData;
                        break;
                    }
                }
                CheatSheetData data = new CheatSheetData(file.getName(), hotkeyData);
                if (!data.isValid()) continue;
                cheatSheetContainer.add(new CheatSheetRow(cheatSheetContainer, data));
            }
        }
        cheatSheetContainer.revalidate();
        cheatSheetContainer.repaint();
    }

    private void addListeners() {
        browseButton.addActionListener(e -> ZUtil.openExplorer(SaveManager.getImagesDirectory()));
        refreshButton.addActionListener(e -> rebuildFromFolder());
    }

    @Override
    public void save() {
        ArrayList<CheatSheetData> cheatSheets = new ArrayList<>();
        int matchingWindows = 0;
        for (Component c : cheatSheetContainer.getComponents()) {
            if (c instanceof CheatSheetRow) {
                CheatSheetData data = ((CheatSheetRow) c).getData();
                if (FrameManager.cheatSheetWindows.containsKey(data.title)) matchingWindows++;
                cheatSheets.add(((CheatSheetRow) c).getData());
            }
        }
        SaveManager.settingsSaveFile.data.cheatSheets = cheatSheets;
        // Don't rebuild cheat sheets if files have not changed
        if (matchingWindows == FrameManager.cheatSheetWindows.size() && matchingWindows == cheatSheetContainer.getComponentCount()) {
            return;
        }
        FrameManager.buildCheatSheetWindows();
    }

    @Override
    public void load() {
        cheatSheetContainer.removeAll();
        for (CheatSheetData data : SaveManager.settingsSaveFile.data.cheatSheets) {
            cheatSheetContainer.add(new CheatSheetRow(cheatSheetContainer, data));
        }
    }

}
