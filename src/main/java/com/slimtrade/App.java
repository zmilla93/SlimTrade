package com.slimtrade;

import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.jna.EnumerateWindows;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.LanguageManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.SystemTrayManager;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class App {

    public static FrameManager frameManager;
    public static LanguageManager languageManager;
    public static SaveManager saveManager;
    public static AudioManager audioManager;
    public static SystemTrayManager systemTrayManager;

    public static boolean initialized;

    public static void main(String[] args) {

        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        // Managers
        saveManager = new SaveManager();
        languageManager = new LanguageManager();
        audioManager = new AudioManager();
        PoeInterface.init();

        // Swing
        try {
            SwingUtilities.invokeAndWait(() -> {
                // Init System Tray Button
                systemTrayManager = new SystemTrayManager();
                // Initialize all GUI windows
                FrameManager.Init();
                // Load save file to GUI
                ColorManager.setTheme(saveManager.settingsSaveFile.colorTheme);
                saveManager.revertChanges();
                FrameManager.optionsWindow.reloadExampleTrades();
                FrameManager.optionsWindow.revalidate();

                // Show Windows
                FrameManager.messageManager.setVisible(true);
                FrameManager.debugWindow.setVisible(true);
                FrameManager.optionsWindow.setVisible(true);
//                FrameManager.historyWindow.setVisible(true);
            });
        } catch (InterruptedException | InvocationTargetException e) {
            e.printStackTrace();
        }

        // Chat Parser
        ChatParser parser = new ChatParser("C:/Program Files (x86)/Grinding Gear Games/Path of Exile/logs/Client.txt");
        // Chat Parser Listeners
        parser.addPreloadTradeListener(FrameManager.historyWindow);
        parser.addTradeListener(FrameManager.historyWindow);
        parser.addOnLoadedCallback(FrameManager.historyWindow);
        parser.addTradeListener(FrameManager.messageManager);
        // Chat Parser Init
        parser.init();

        try {
            EnumerateWindows.printWindow();
        } catch (Exception e) {
            e.printStackTrace();
        }

        initialized = true;
        System.out.println("Slimtrade Rebuild!");
    }

}
