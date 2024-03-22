package com.slimtrade.gui.menubar;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.components.IconLabel;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IFontChangeListener {

    public MenubarButtonDialog() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(new IconLabel(DefaultIcon.TAG, 1), BorderLayout.CENTER);
        pack();
        ThemeManager.addFontListener(this);
    }

    @Override
    public void onFontChanged() {
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }
}
