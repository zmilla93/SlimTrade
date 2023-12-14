package com.slimtrade.gui.windows;

import com.slimtrade.gui.components.IVisibilityFrame;
import com.slimtrade.gui.components.Visibility;
import com.slimtrade.modules.theme.ThemeManager;

import java.awt.*;

public class AbstractWindow extends VisibilityDialog implements IVisibilityFrame {

    protected Container container;
    private Visibility visibility;

    public AbstractWindow(String title) {
        setTitle(title);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setAlwaysOnTop(true);
        this.container = getContentPane();
        ThemeManager.addFrame(this);
    }

    @Override
    public void showOverlay() {
        if (visibility == Visibility.SHOW) setVisible(true);
        visibility = Visibility.UNSET;
    }

    @Override
    public void hideOverlay() {
        visibility = isVisible() ? Visibility.SHOW : Visibility.HIDE;
        setVisible(false);
    }

}
