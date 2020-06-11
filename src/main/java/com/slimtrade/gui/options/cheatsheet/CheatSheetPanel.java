package com.slimtrade.gui.options.cheatsheet;

import com.slimtrade.App;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.FrameManager;
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
    private boolean disposeFrames = false;

    public CheatSheetPanel() {

        // Components
        JLabel info1 = new CustomLabel("Add images to the image folder, then refresh.");
        JLabel info2 = new CustomLabel("Assign a hotkey to view the image. Clear a hotkey with escape.");
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

    private void refreshFromFolder() {
        for(CheatSheetWindow w : FrameManager.cheatSheetWindows) {
            w.dispose();
        }
        disposeFrames = true;
        addRemovePanel.removeAll();
        File file = new File(App.saveManager.getImageFolder());
        if (file.exists()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                if(!TradeUtility.isValidImagePath(f.getAbsolutePath())) {
                    continue;
                }
                CheatSheetData data = new CheatSheetData(f.getAbsolutePath());
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
        revalidate();
        repaint();
    }

    @Override
    public void save() {
        if(disposeFrames) {
            FrameManager.disposeCheatSheets();
        }
        App.saveManager.saveFile.cheatSheetData.clear();
        for (Component c : addRemovePanel.getComponents()) {
            if (c instanceof DataPanel) {
                DataPanel panel = (DataPanel) c;
                panel.data.hotkeyData = panel.hotkeyInputPane.getHotkeyData();
                App.saveManager.saveFile.cheatSheetData.add(panel.data);
            }
        }
        if(disposeFrames) {
            FrameManager.generateCheatSheets();
            disposeFrames = false;
        }
    }

    @Override
    public void load() {
        refreshFromFolder();
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