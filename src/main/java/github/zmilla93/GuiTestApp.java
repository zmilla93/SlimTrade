package github.zmilla93;

import github.zmilla93.core.enums.CurrencyType;
import github.zmilla93.core.managers.FontManager;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.windows.test.MessageTestWindow;

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
