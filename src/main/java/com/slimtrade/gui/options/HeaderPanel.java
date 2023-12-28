package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

/**
 * A JPanel with a header label and a separate below.
 *
 * @see AbstractOptionPanel
 */
public class HeaderPanel extends JPanel {

    public final JLabel label;
    public final JSeparator separator;

    public HeaderPanel(String title) {
        if (App.debugUIBorders >= 2) setBorder(BorderFactory.createLineBorder(Color.RED));
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        label = new JLabel(title);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        add(label, gc);
        gc.gridy++;
        add(separator, gc);
    }

}
