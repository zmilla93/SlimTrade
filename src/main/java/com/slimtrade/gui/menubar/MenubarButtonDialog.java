package com.slimtrade.gui.menubar;

import com.slimtrade.App;
import com.slimtrade.core.chatparser.IDndListener;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.components.IconLabel;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IFontChangeListener, IParserLoadedListener, IDndListener {

    private final IconLabel iconLabel = new IconLabel(DefaultIcon.TAG, 1);

    public MenubarButtonDialog() {
        contentPanel.setLayout(new BorderLayout());
        contentPanel.add(iconLabel, BorderLayout.CENTER);
        pack();
        ThemeManager.addFontListener(this);
    }

    private void updateIcon(boolean dnd) {
        if (dnd) iconLabel.setIcon(DefaultIcon.DND);
        else iconLabel.setIcon(DefaultIcon.TAG);
    }

    @Override
    public void onFontChanged() {
        pack();
        TradeUtil.applyAnchorPoint(this, SaveManager.overlaySaveFile.data.menubarLocation, SaveManager.overlaySaveFile.data.menubarAnchor);
    }

    @Override
    public void onDndToggle(boolean state, boolean loaded) {
        if (!loaded) return;
        updateIcon(state);
    }

    @Override
    public void onParserLoaded() {
        updateIcon(App.chatParser.dndEnabled());
    }

}
