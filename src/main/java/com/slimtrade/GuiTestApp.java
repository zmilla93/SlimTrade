package com.slimtrade;

import com.slimtrade.core.enums.CurrencyType;
import com.slimtrade.core.managers.FontManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.windows.test.MessageTestWindow;

public class GuiTestApp {

    public static void main(String[] args) {
        SaveManager.init();
        FontManager.loadFonts();
        CurrencyType.initIconList();
        ZUtil.invokeAndWait(() -> {
            MessageTestWindow window = new MessageTestWindow();
            window.setVisible(true);
        });
    }

}
