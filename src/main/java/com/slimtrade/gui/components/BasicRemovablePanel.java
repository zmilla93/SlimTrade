package com.slimtrade.gui.components;

import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.DefaultIcons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicRemovablePanel extends JPanel implements IColorable {

    private GridBagConstraints gc = new GridBagConstraints();

    private JPanel buttonPanel = new JPanel(new FlowLayout(0, 0, 0));
    private JButton shiftUpButton;
    private JButton shiftDownButton;
    private IRemovablePanelData data;

    public BasicRemovablePanel() {
        this(false);
    }

    public BasicRemovablePanel(boolean shiftButtons) {
        super(new GridBagLayout());
        buttonPanel.setOpaque(false);
        JButton closeButton = new IconButton(DefaultIcons.CLOSE, References.DEFAULT_IMAGE_SIZE);
        if (shiftButtons) {
            shiftUpButton = new IconButton(DefaultIcons.ARROW_UP, References.DEFAULT_IMAGE_SIZE);
            shiftDownButton = new IconButton(DefaultIcons.ARROW_DOWN, References.DEFAULT_IMAGE_SIZE);
        }
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        // Build UI
        buttonPanel.add(closeButton);
        if (shiftButtons) {
            buttonPanel.add(shiftDownButton);
            buttonPanel.add(shiftUpButton);
        }
        gc.gridx = 0;
        gc.gridy = 0;
        gc.insets = new Insets(1, 1, 1, 20);
        gc.anchor = GridBagConstraints.WEST;

        add(buttonPanel, gc);
        gc.gridx++;
        gc.insets = new Insets(0, 0, 0, 0);

        gc.weightx = 0;
        gc.insets.left = 20;
        gc.insets.right = 20;
        gc.anchor = GridBagConstraints.CENTER;
        gc.fill = GridBagConstraints.BOTH;
    }

    public void addItem(Component c, int width) {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        panel.add(c);
        add(panel, gc);
        panel.setPreferredSize(new Dimension(width, panel.getPreferredSize().height));
        gc.gridx++;
        revalidate();
        repaint();
    }

    public void addCenteredItem(Component c, int width) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        panel.add(c, BorderLayout.CENTER);
        add(panel, gc);
        panel.setPreferredSize(new Dimension(width, panel.getPreferredSize().height));
        gc.gridx++;
        revalidate();
        repaint();
    }

    public void bindShiftButtons(AddRemovePanel panel) {
        JPanel local = this;
        shiftDownButton.addActionListener(e -> panel.shiftDown(local));
        shiftUpButton.addActionListener(e -> panel.shiftUp(local));
    }

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
        this.setBorder(ColorManager.BORDER_LOW_CONTRAST_2);
    }

    public void setData(IRemovablePanelData data) {
        this.data = data;
    }

    public IRemovablePanelData getData() {
        return data;
    }
}
