package com.slimtrade.gui.windows;

import com.slimtrade.App;
import com.slimtrade.core.trading.TradeOffer;
import com.slimtrade.core.utility.ColorManager;
import com.slimtrade.core.utility.ColorTheme;

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
            App.frameManager.messageManager.addMessage(TradeOffer.getExampleTrade(TradeOffer.TradeOfferType.INCOMING));
//                App.audioManager.playSoundRaw(App.audioManager.soundfiles.get(0), 0);
        });
        container.add(testMessageButton);


        combo.addItemListener(e -> {
            ColorManager.setTheme((ColorTheme) combo.getSelectedItem());
        });
    }

}