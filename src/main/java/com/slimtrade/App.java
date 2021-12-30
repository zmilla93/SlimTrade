package com.slimtrade;

import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.managers.LanguageManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.managers.FrameManager;
import com.slimtrade.gui.managers.SystemTrayManager;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class App {

    public static FrameManager frameManager;
    public static LanguageManager languageManager;
    public static SaveManager saveManager;
    public static AudioManager audioManager;

    public static void main(String[] args){

        // Managers
        saveManager = new SaveManager();
        SystemTrayManager systemTrayManager = new SystemTrayManager();
        languageManager = new LanguageManager();
        audioManager = new AudioManager();


        // This setting gets rid of some rendering issues with transparent frames
        System.setProperty("sun.java2d.noddraw", "true");

        try {
            SwingUtilities.invokeAndWait(() -> {
                // Initialize all GUI windows
                FrameManager.Init();
                // Load save file to GUI
                ColorManager.setTheme(saveManager.settingsSaveFile.colorTheme);
                saveManager.revertChanges();

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
        // Chat Parser Init
        parser.init();

        System.out.println("Slimtrade Rebuild!");
    }

}
