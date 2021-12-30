package com.slimtrade.gui.managers;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.windows.DebugWindow;
import com.slimtrade.gui.windows.HistoryWindow;
import com.slimtrade.gui.windows.OptionsWindow;

public class FrameManager {

    public static MessageManager messageManager;
    public static DebugWindow debugWindow;
    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;

    public static void Init(){
        messageManager = new MessageManager();
        debugWindow = new DebugWindow();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        ColorManager.addFrame(messageManager);
        ColorManager.addFrame(debugWindow);
        ColorManager.addFrame(optionsWindow);
        ColorManager.addFrame(historyWindow);
    }

}
