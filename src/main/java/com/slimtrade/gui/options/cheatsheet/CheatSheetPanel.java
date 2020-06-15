package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.App;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class CheatSheetPanel extends ContainerPanel implements ISaveable {

    private AddRemovePanel addRemovePanel = new AddRemovePanel();
    private boolean disposeFrames = false;

    public CheatSheetPanel() {

        // Components
        JLabel info1 = new CustomLabel("Add images to the image folder, then refresh.");
        JLabel info2 = new CustomLabel("Assign a hotkey to view the image. Clear hotkeys with escape.");
        JButton openFolderButton = new BasicButton("Open Folder");
        JButton refreshButton = new BasicButton("Refresh");

        // Build UI
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setOpaque(false);
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        buttonPanel.add(openFolderButton, gc);
        gc.gridx++;
        buttonPanel.add(Box.createHorizontalStrut(20), gc);
        gc.gridx++;
        buttonPanel.add(refreshButton, gc);


        gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        container.add(info1, gc);
        gc.gridy++;
        container.add(info2, gc);
        gc.gridy++;
        gc.insets.top = 10;
        gc.weightx = 1;
        gc.fill = GridBagConstraints.BOTH;
        container.add(buttonPanel, gc);
        gc.gridy++;
        container.add(addRemovePanel, gc);

        openFolderButton.addActionListener(e -> {
            File file = new File(App.saveManager.getImageFolder());
            if (!file.exists()) {
                if (!(file.mkdirs())) {
                    return;
                }
            }
            try {
                Desktop.getDesktop().open(new File(App.saveManager.getImageFolder()));
            } catch (IOException er) {
                er.printStackTrace();
            }
        });

        refreshButton.addActionListener(e -> {
            refreshFromFolder();
        });

        load();

    }

    private void addPanel(CheatSheetData data) {
        CheatSheetRow panel = new CheatSheetRow(data);
        for (CheatSheetData d : App.saveManager.saveFile.cheatSheetData) {
            if (d.fileName.equals(data.fileName)) {
                panel.hotkeyInputPane.updateHotkey(d.hotkeyData);
            }
        }
        addRemovePanel.addRemovablePanel(panel);
    }

    private void refreshFromFolder() {
        for (CheatSheetWindow w : FrameManager.cheatSheetWindows) {
            w.dispose();
        }
        disposeFrames = true;
        addRemovePanel.removeAll();
        File file = new File(App.saveManager.getImageFolder());
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if (!TradeUtility.isValidImagePath(f.getAbsolutePath())) {
                    continue;
                }
                CheatSheetData data = new CheatSheetData(f.getAbsolutePath());
                addPanel(data);
            }
        }
        revalidate();
        repaint();
    }

    @Override
    public void save() {
        if (disposeFrames) {
            FrameManager.disposeCheatSheets();
        }
        App.saveManager.saveFile.cheatSheetData.clear();
        for (Component c : addRemovePanel.getComponents()) {
            if (c instanceof CheatSheetRow) {
                CheatSheetRow panel = (CheatSheetRow) c;
                panel.data.hotkeyData = panel.hotkeyInputPane.getHotkeyData();
                App.saveManager.saveFile.cheatSheetData.add(panel.data);
            }
        }
        if (disposeFrames) {
            FrameManager.generateCheatSheets();
            disposeFrames = false;
        }
    }

    @Override
    public void load() {
        refreshFromFolder();
    }

}