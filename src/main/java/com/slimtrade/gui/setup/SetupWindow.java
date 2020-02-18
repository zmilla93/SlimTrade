package com.slimtrade.gui.setup;

import com.slimtrade.App;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.enums.PreloadedImage;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

public class SetupWindow extends JFrame {

    private String nextText;
    private String finishText;

    private BasicButton previousButton = new BasicButton("<< Back");
    private BasicButton nextButton = new BasicButton();

    public SetupWindow() {
        this.setTitle("SlimTrade - Setup");
        Container container = this.getContentPane();
        container.setLayout(new GridBagLayout());
        this.setIconImage(new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource("icons/default/tagx64.png"))).getImage().getScaledInstance(64, 64, Image.SCALE_SMOOTH));
    }

}
