package com.slimtrade.gui.managers;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.gui.windows.DebugWindow;
import com.slimtrade.gui.windows.DummyWindow;
import com.slimtrade.gui.windows.HistoryWindow;
import com.slimtrade.gui.windows.OptionsWindow;

import java.awt.*;

public class FrameManager {

    public static MessageManager messageManager;
    public static DebugWindow debugWindow;
    public static OptionsWindow optionsWindow;
    public static HistoryWindow historyWindow;
    public static DummyWindow dummyWindow;

    public static void init(){
        messageManager = new MessageManager();
        debugWindow = new DebugWindow();
        optionsWindow = new OptionsWindow();
        historyWindow = new HistoryWindow();
        dummyWindow = new DummyWindow();
        ColorManager.addFrame(messageManager);
        ColorManager.addFrame(debugWindow);
        ColorManager.addFrame(optionsWindow);
        ColorManager.addFrame(historyWindow);
    }

    public static void centerWindow(Window window){
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension windowSize = window.getSize();
        window.setLocation(new Point(screenSize.width/2 - windowSize.width/2, screenSize.height/2 - windowSize.height/2));
    }

}
