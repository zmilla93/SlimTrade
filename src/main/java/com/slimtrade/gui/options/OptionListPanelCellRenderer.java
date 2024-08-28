package com.slimtrade.gui.options;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.StyledLabel;

import javax.swing.*;
import java.awt.*;

public class OptionListPanelCellRenderer implements ListCellRenderer<OptionListPanel> {

    public static final int TEXT_INSET_HORIZONTAL = 6;
    private static final int INSET_VERTICAL = 1;
    private static final int SEPARATOR_INSET_HORIZONTAL = 4;
    private static final int SEPARATOR_INSET_VERTICAL = 2;

    private final Color background;
    private final Color backgroundSelected;

    // Normal Item
    private final JLabel label = new JLabel();
    private final JPanel labelPanel = createLabelPanel();

    // Plain Separator
    private final JPanel separatorPanel = createSeparatorPanel();

    // Separator with Title
    private final JLabel separatorLabel = new StyledLabel().bold();
    private final JPanel separatorTitlePanel = createSeparatorTitlePanel();

    public OptionListPanelCellRenderer() {
        background = UIManager.getColor("List.background");
        backgroundSelected = UIManager.getColor("List.selectionBackground");
    }

    private JPanel createLabelPanel() {
        JPanel panel = new JPanel(new BorderLayout());


        ZUtil.addStrutsToBorderPanel(panel, new Insets(INSET_VERTICAL, TEXT_INSET_HORIZONTAL, INSET_VERTICAL, TEXT_INSET_HORIZONTAL));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSeparatorTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(titlePanel, new Insets(8, TEXT_INSET_HORIZONTAL, 0, TEXT_INSET_HORIZONTAL));
        titlePanel.add(separatorLabel, BorderLayout.CENTER);

        JPanel separatorPanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(separatorPanel, new Insets(SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL, SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL));
        separatorPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(titlePanel, BorderLayout.NORTH);
        wrapperPanel.add(separatorPanel, BorderLayout.SOUTH);

        return wrapperPanel;
    }

    private JPanel createSeparatorPanel() {
        JPanel separatorPanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(separatorPanel, new Insets(SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL, SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL));
        separatorPanel.add(new JSeparator(JSeparator.HORIZONTAL), BorderLayout.CENTER);
        return separatorPanel;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends OptionListPanel> list, OptionListPanel value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value.isSeparator) {
            separatorLabel.setText(value.title);
            if (value.title == null) return separatorPanel;
            else return separatorTitlePanel;
        }
        label.setText(value.title);
        if (isSelected) labelPanel.setBackground(backgroundSelected);
        else labelPanel.setBackground(background);
        return labelPanel;
    }

}
