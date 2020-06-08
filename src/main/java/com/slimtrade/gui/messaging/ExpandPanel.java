package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.basic.BasicDialog;
import com.slimtrade.gui.basic.PaintedPanel;

import javax.swing.*;
import java.awt.*;

public class ExpandPanel extends BasicDialog implements IColorable {

    private final JPanel outerPanel = new JPanel(new BorderLayout());
    private JPanel innerPanel = new JPanel(new BorderLayout());
    private PaintedPanel labelPanel = new PaintedPanel();

    public ExpandPanel() {

        Container container = getContentPane();
        container.setLayout(new BorderLayout());

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;

        gc.insets = new Insets(AbstractMessagePanel.BORDER_SIZE, AbstractMessagePanel.BORDER_SIZE, AbstractMessagePanel.BORDER_SIZE, AbstractMessagePanel.BORDER_SIZE);

        outerPanel.add(Box.createVerticalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.NORTH);
        outerPanel.add(Box.createVerticalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.SOUTH);
        outerPanel.add(Box.createHorizontalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.EAST);
        outerPanel.add(Box.createHorizontalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.WEST);
        outerPanel.setBackground(Color.RED);

        innerPanel.add(Box.createVerticalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.NORTH);
        innerPanel.add(Box.createVerticalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.SOUTH);
        innerPanel.add(Box.createHorizontalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.EAST);
        innerPanel.add(Box.createHorizontalStrut(AbstractMessagePanel.BORDER_SIZE), BorderLayout.WEST);
        innerPanel.setBackground(Color.GREEN);

        container.add(outerPanel, BorderLayout.CENTER);
        outerPanel.add(innerPanel, BorderLayout.CENTER);
        innerPanel.add(labelPanel, BorderLayout.CENTER);
        labelPanel.setText("Expand Panel");

        pack();

    }

    public JPanel getLabelPanel() {
        return labelPanel;
    }

    public void updateMessage(int tradeCount, boolean expanded) {
        if (expanded) {
            labelPanel.setText("Hide Extra Messages");
        } else {
            String s = "";
            int i = tradeCount - App.saveManager.saveFile.messageCountBeforeCollapse;
            if (i > 1) {
                s = "s";
            }
            labelPanel.setText("+" + i + " More Message" + s);
        }
    }

    @Override
    public void updateColor() {
        super.updateColor();
        outerPanel.setBackground(ColorManager.MESSAGE_BORDER);
        innerPanel.setBackground(ColorManager.TEXT);
        labelPanel.setBackgroundColor(ColorManager.MESSAGE_NAME_BG);
        labelPanel.backgroundHover = ColorManager.PRIMARY;
        labelPanel.borderDefault = ColorManager.MESSAGE_NAME_BG;
        labelPanel.borderHover = ColorManager.MESSAGE_NAME_BG;
        labelPanel.borderClick = ColorManager.MESSAGE_NAME_BG;
        labelPanel.setTextColor(ColorManager.TEXT);
    }

}
