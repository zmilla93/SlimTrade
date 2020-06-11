package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.App;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.components.AddRemovePanel;
import com.slimtrade.gui.components.BasicRemovablePanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.options.hotkeys.HotkeyInputPane;
import com.slimtrade.gui.panels.ContainerPanel;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Objects;

public class CheatSheetPanel extends ContainerPanel implements ISaveable {

    private AddRemovePanel addRemovePanel = new AddRemovePanel();

    public CheatSheetPanel() {

        // Components
        JLabel info1 = new CustomLabel("Add images to the image folder, refresh, add a hotkey, then save.");
        JButton openFolderButton = new BasicButton("Open Folder");
        JButton refreshButton = new BasicButton("Refresh");

        // Build UI
        container.add(info1, gc);
        gc.gridy++;
        container.add(openFolderButton, gc);
        gc.gridy++;
        container.add(refreshButton, gc);
        gc.gridy++;
        container.add(addRemovePanel, gc);

        openFolderButton.addActionListener(e -> {
            File file = new File(App.saveManager.getImageFolder());
            if (!file.exists()) {
                file.mkdirs();
            }
            try {
                Desktop.getDesktop().open(new File(App.saveManager.getImageFolder()));
            } catch (IOException er) {
                er.printStackTrace();
            }
        });

        refreshButton.addActionListener(e -> {
            refreshPanels();
        });

        load();

    }

    private void addPanel(CheatSheetData data) {
//        CheatSheetData data = new CheatSheetData(fileName);
        DataPanel panel = new DataPanel(data);
        panel.add(new CustomLabel(data.getCleanName()));
        panel.add(panel.hotkeyInputPane);
        for (CheatSheetData d : App.saveManager.saveFile.cheatSheetData) {
            if (d.fileName.equals(data.fileName)) {
                panel.hotkeyInputPane.updateHotkey(d.hotkeyData);
            }
        }
        addRemovePanel.addRemovablePanel(panel);
    }

    private void refreshPanels() {
        addRemovePanel.removeAll();
        File file = new File(App.saveManager.getImageFolder());
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                System.out.println("str" + f.toString());
                System.out.println("str" + f.getName());
                System.out.println("str" + f.getAbsolutePath());
                System.out.println("str" + f.getPath());
                CheatSheetData data = new CheatSheetData(f.toString());
                addPanel(data);
                System.out.println(f);
                try {
                    System.out.println(Files.probeContentType(f.toPath()));
                    System.out.println(f.getName().replaceFirst("\\.\\w+$", "").replaceAll("_", " "));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }
    }

    @Override
    public void save() {
        App.saveManager.saveFile.cheatSheetData.clear();
        for (Component c : addRemovePanel.getComponents()) {
            if (c instanceof DataPanel) {
                DataPanel panel = (DataPanel) c;
                panel.data.hotkeyData = panel.hotkeyInputPane.getHotkeyData();
                App.saveManager.saveFile.cheatSheetData.add(panel.data);
            }
        }
    }

    @Override
    public void load() {
        refreshPanels();
    }

    private class DataPanel extends BasicRemovablePanel {

        public CheatSheetData data;
        public HotkeyInputPane hotkeyInputPane = new HotkeyInputPane();

        public DataPanel(CheatSheetData data) {
            super();
            this.data = data;
        }

    }

}