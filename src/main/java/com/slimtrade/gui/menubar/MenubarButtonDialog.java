package com.slimtrade.gui.menubar;

import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IFontChangeListener {

    public MenubarButtonDialog() {
        JButton iconButton = new IconButton(DefaultIcon.TAG);
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(iconButton, BorderLayout.CENTER);
        pack();
        ThemeManager.addFontListener(this);
    }

    @Override
    public void onFontChanged() {
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }
}
