package com.slimtrade.gui.scanner;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.custom.CustomTextArea;

import javax.swing.*;
import java.awt.*;

import static com.slimtrade.gui.scanner.ChatScannerWindow.bufferInner;
import static com.slimtrade.gui.scanner.ChatScannerWindow.bufferOuter;

public class SearchTermsPanel extends JPanel implements IColorable {

    private JPanel outerPanel = new JPanel(new GridBagLayout());

    private JLabel searchTermsLabel = new CustomLabel("Search Terms");
    private JLabel ignoreTermsLabel = new CustomLabel("Ignore Terms");
    private JLabel info1 = new CustomLabel("Separate terms using commas, semicolons, or new lines.");
    private JLabel info2 = new CustomLabel("Scanning is case insensitive. Irregular spacing is ignored.");

    public JTextArea searchTermsInput = new CustomTextArea(2, 45);
    public JTextArea ignoreTermsInput = new CustomTextArea(2, 45);

    public SearchTermsPanel() {
        super(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        outerPanel.add(info1, gc);
        gc.insets.top = 0;
        gc.gridy++;
        gc.insets.bottom = bufferInner*2;
        outerPanel.add(info2, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        gc.fill = GridBagConstraints.BOTH;
        outerPanel.add(searchTermsLabel, gc);
        gc.gridy++;
        gc.insets.bottom = bufferInner;
        outerPanel.add(searchTermsInput, gc);
        gc.insets.bottom = 0;
        gc.gridy++;
        outerPanel.add(ignoreTermsLabel, gc);
        gc.gridy++;
        outerPanel.add(ignoreTermsInput, gc);
        gc.gridx++;

        gc = new GridBagConstraints();
        gc.insets = new Insets(bufferOuter, bufferOuter, bufferOuter, bufferOuter);
        this.add(outerPanel, gc);

        outerPanel.setBackground(ColorManager.CLEAR);
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(BorderFactory.createLineBorder(ColorManager.TEXT));
    }
}
