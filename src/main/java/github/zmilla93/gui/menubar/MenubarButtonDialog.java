package github.zmilla93.gui.menubar;

import github.zmilla93.core.chatparser.DndListener;
import github.zmilla93.core.chatparser.ParserLoadedListener;
import github.zmilla93.core.enums.DefaultIcon;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.TradeUtil;
import github.zmilla93.gui.buttons.IconButton;
import github.zmilla93.gui.windows.BasicDialog;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.theme.listeners.IFontChangeListener;

import javax.swing.*;
import java.awt.*;

public class MenubarButtonDialog extends BasicDialog implements IFontChangeListener, ParserLoadedListener, DndListener {

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
