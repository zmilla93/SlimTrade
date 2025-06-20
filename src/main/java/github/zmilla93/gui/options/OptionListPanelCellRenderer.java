package github.zmilla93.gui.options;

import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.StyledLabel;

import javax.swing.*;
import java.awt.*;

public class OptionListPanelCellRenderer extends JPanel implements ListCellRenderer<OptionListPanel> {

    public static final int TEXT_INSET_HORIZONTAL = 6;
    private static final int INSET_VERTICAL = 1;
    private static final int SEPARATOR_INSET_HORIZONTAL = 4;
    private static final int SEPARATOR_INSET_VERTICAL = 2;
    private static final int LARGE_VERTICAL_INSET = 8;

    // Normal Item
    private final JLabel label = new JLabel();
    private final JPanel labelPanel = createLabelPanel();

    // Plain Separator
    private final JPanel separatorPanel = createSeparatorPanel();
    private JSeparator plainSeparator;

    // Separator with Title
    private final JLabel separatorLabel = new StyledLabel().bold();
    private final JPanel separatorTitlePanel = createSeparatorTitlePanel();
    private JSeparator titleSeparator;
    private Component emptyPanel = Box.createHorizontalStrut(0);

    private JPanel createLabelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(panel, new Insets(INSET_VERTICAL, TEXT_INSET_HORIZONTAL, INSET_VERTICAL, TEXT_INSET_HORIZONTAL));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createSeparatorTitlePanel() {
        JPanel titlePanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(titlePanel, new Insets(LARGE_VERTICAL_INSET, TEXT_INSET_HORIZONTAL, 0, TEXT_INSET_HORIZONTAL));
        titlePanel.add(separatorLabel, BorderLayout.CENTER);
        titlePanel.setOpaque(false);

        JPanel separatorPanel = new JPanel(new BorderLayout());
        separatorPanel.setOpaque(false);
        ZUtil.addStrutsToBorderPanel(separatorPanel, new Insets(SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL, SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL));
        titleSeparator = new JSeparator(JSeparator.HORIZONTAL);
        separatorPanel.add(titleSeparator, BorderLayout.CENTER);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(titlePanel, BorderLayout.NORTH);
        wrapperPanel.add(separatorPanel, BorderLayout.SOUTH);

        return wrapperPanel;
    }

    private JPanel createSeparatorPanel() {
        JPanel separatorPanel = new JPanel(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(separatorPanel, new Insets(LARGE_VERTICAL_INSET, SEPARATOR_INSET_HORIZONTAL, SEPARATOR_INSET_VERTICAL, SEPARATOR_INSET_HORIZONTAL));
        plainSeparator = new JSeparator(JSeparator.HORIZONTAL);
        separatorPanel.add(plainSeparator, BorderLayout.CENTER);
        separatorPanel.setOpaque(false);
        return separatorPanel;
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends OptionListPanel> list, OptionListPanel value, int index, boolean isSelected, boolean cellHasFocus) {
        if (value == null) return emptyPanel;
        if (value.isSeparator) {
            separatorLabel.setForeground(list.getForeground());
            plainSeparator.setForeground(list.getForeground());
            titleSeparator.setForeground(list.getForeground());
            separatorLabel.setText(value.title);
            if (value.title == null) return separatorPanel;
            else return separatorTitlePanel;
        }
        label.setText(value.title);
        separatorPanel.setBackground(list.getBackground());
        separatorTitlePanel.setBackground(list.getBackground());
        if (isSelected) {
            labelPanel.setBackground(list.getSelectionBackground());
            label.setForeground(list.getSelectionForeground());
        } else {
            labelPanel.setBackground(list.getBackground());
            label.setForeground(list.getForeground());
        }
        return labelPanel;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        if (label != null) label.setFont(font);
    }

}
