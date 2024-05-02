package com.slimtrade.gui.menubar;

import com.slimtrade.core.chatparser.IDndListener;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.enums.DefaultIcon;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.TradeUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.windows.BasicDialog;
import com.slimtrade.modules.theme.IFontChangeListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IFontChangeListener, IParserLoadedListener, IDndListener {

    private final IconButton iconLabel = new IconButton(DefaultIcon.TAG);

    public MenubarButtonDialog() {
        contentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contentPanel.add(iconLabel);
        pack();
        ThemeManager.addFontListener(this);
    }

    private void updateIcon(boolean dnd) {
        SwingUtilities.invokeLater(() -> {
            if (dnd) iconLabel.setIcon(DefaultIcon.VOLUME_MUTE);
            else iconLabel.setIcon(DefaultIcon.TAG);
        });

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
    public void onParserLoaded(boolean dnd) {
        updateIcon(dnd);
    }

}
