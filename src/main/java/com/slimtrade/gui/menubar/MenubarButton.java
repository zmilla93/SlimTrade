package com.slimtrade.gui.menubar;

import java.awt.*;

import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.options.ListButton;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class MenubarButton extends BasicButton {

    private static final long serialVersionUID = 1L;
    public static int HEIGHT = 20;

    public MenubarButton() {
        this("");
    }

    public MenubarButton(String text) {
        super(text);
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, HEIGHT));
        this.setFocusable(false);
    }

}
