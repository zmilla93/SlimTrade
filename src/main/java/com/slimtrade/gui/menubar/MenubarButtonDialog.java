package com.slimtrade.gui.menubar;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.colortheme.IUIResizeListener;

import javax.swing.*;
import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IUIResizeListener {

    public MenubarButtonDialog() {
        JButton iconButton = new IconButton("/icons/default/tagx64.png");
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(iconButton, BorderLayout.CENTER);
        pack();
        ColorManager.addFontListener(this);
    }

    @Override
    public void onFontSizeChanged() {

    }

    @Override
    public void onIconSizeChanged() {
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

}
