package com.slimtrade.gui.windows;

import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;
import com.slimtrade.modules.colortheme.components.AdvancedButton;

import javax.swing.*;
import java.awt.*;

public class DebugWindow extends JFrame {

    public DebugWindow() {
        super("Slimtrade Debug");
        setAlwaysOnTop(true);
        setMinimumSize(new Dimension(400, 600));
        Container container = getContentPane();
        container.setLayout(new FlowLayout());

        JComboBox<ColorTheme> combo = new JComboBox<>();
//        container.add(combo);
        for (ColorTheme theme : ColorTheme.values()) {
            combo.addItem(theme);
        }

        JButton testMessageButton = new JButton("Test Message");
        testMessageButton.addActionListener(e -> {
//                App.frameManager.addMessage();
//            App.frameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//                AudioManager.playSoundRaw(AudioManager.soundfiles.get(0), 0);
        });
        container.add(testMessageButton);

        JButton button = new JButton("Regular Button");
        AdvancedButton advancedButton = new AdvancedButton("Advanced Button");
        container.add(button);
        container.add(advancedButton);

        combo.addItemListener(e -> {
            ColorManager.setTheme((ColorTheme) combo.getSelectedItem());
        });
    }

}