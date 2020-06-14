package com.slimtrade.gui.options;

import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.buttons.BasicButton;

import java.awt.*;
import java.awt.event.MouseEvent;

public class SelectorButton extends BasicButton implements IColorable {

    public SelectorButton(String title) {
        super(title);
        useGradient = false;
        addMouseListener(new AdvancedMouseAdapter() {
            @Override
            public void click(MouseEvent e) {
                for (Component c : getParent().getComponents()) {
                    if (c instanceof SelectorButton) {
                        SelectorButton b = (SelectorButton) c;
                        if (b.selected) {
                            b.selected = false;
                            b.repaint();
                        }
                    }
                }
                selected = true;
                repaint();
            }
        });
    }

}
