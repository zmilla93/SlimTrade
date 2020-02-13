package com.slimtrade.gui.scanner;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;

import javax.swing.*;
import java.awt.*;

public class SearchTermsPanel extends JPanel implements IColorable {

    private JLabel searchTermsLabel = new JLabel("Search Terms");
    private JLabel ignoreTermsLabel = new JLabel("Ignore Terms");
    private JLabel info1 = new JLabel("Separate terms using commas, semicolons, or new lines.");
    private JLabel info2 = new JLabel("Scanning is case insensitive. Irregular spacing is ignored.");

    public JTextArea searchTermsInput = new JTextArea(4, 45);
    public JTextArea ignoreTermsInput = new JTextArea(4, 45);

    private final int bufferOuter = 8;
    private final int bufferInner = 5;


    public SearchTermsPanel() {
        super(FrameManager.gridBag);
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(bufferOuter, bufferOuter, 0, bufferOuter);
        this.add(info1, gc);
        gc.insets.top = 0;
        gc.gridy++;
        gc.insets.bottom = bufferInner*2;
        this.add(info2, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        this.add(searchTermsLabel, gc);
        gc.gridy++;
        gc.insets.bottom = bufferInner;
        this.add(searchTermsInput, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        this.add(ignoreTermsLabel, gc);
        gc.gridy++;
//        gc.insets = new Insets(0, 5, 5, 5);
        gc.insets.bottom = bufferOuter;
        this.add(ignoreTermsInput, gc);
        gc.gridx++;
        App.eventManager.addColorListener(this);
        this.updateColor();
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        searchTermsInput.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        ignoreTermsInput.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
        searchTermsLabel.setForeground(ColorManager.TEXT);
        ignoreTermsLabel.setForeground(ColorManager.TEXT);
        info1.setForeground(ColorManager.TEXT);
        info2.setForeground(ColorManager.TEXT);
    }
}
