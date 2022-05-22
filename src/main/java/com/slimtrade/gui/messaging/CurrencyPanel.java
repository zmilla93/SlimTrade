package com.slimtrade.gui.messaging;

import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class CurrencyPanel {

    private String text;

    public CurrencyPanel(String text) {
        this.text = text;
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();


    }

}
