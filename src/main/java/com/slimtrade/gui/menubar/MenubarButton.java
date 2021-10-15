package com.slimtrade.gui.menubar;

import com.slimtrade.gui.buttons.BasicButton;

import javax.swing.*;
import java.awt.*;

public class MenubarButton extends BasicButton {

    private static final long serialVersionUID = 1L;
    public static int HEIGHT = 20;
    public static int WIDTH = 120;

    public MenubarButton() {
        this("");
    }

    public MenubarButton(String text) {
        super(text);
        assert(SwingUtilities.isEventDispatchThread());
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        if(!SwingUtilities.isEventDispatchThread()) System.out.println("ERR : menubarbutton");
    }

}
