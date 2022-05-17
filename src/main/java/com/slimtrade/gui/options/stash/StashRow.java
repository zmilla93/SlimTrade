package com.slimtrade.gui.options.stash;

import com.slimtrade.core.enums.MatchType;
import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.modules.colortheme.components.ColorCombo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class StashRow extends JPanel {

    private Container parent;
    private JButton removeButton = new IconButton("/icons/default/closex64.png");
    private JTextField nameInput = new JTextField(20);
    private JComboBox<MatchType> matchTypeCombo = new JComboBox<>();
    private JComboBox<StashTabType> stashTypeCombo = new JComboBox<>();
    private JComboBox<Color> colorCombo = new ColorCombo();

    public StashRow(Container parent) {
        this.parent = parent;
        setLayout(new GridBagLayout());

        for (MatchType matchType : MatchType.values()) matchTypeCombo.addItem(matchType);
        for (StashTabType stashType : StashTabType.values()) stashTypeCombo.addItem(stashType);
        for (StashTabColor stashColor : StashTabColor.values()) colorCombo.addItem(stashColor.getBackground());
        colorCombo.setRenderer(new StashTabCellRenderer());

        GridBagConstraints gc = ZUtil.getGC();
        add(removeButton, gc);
        gc.gridx++;
        add(nameInput, gc);
        gc.gridx++;
        add(matchTypeCombo, gc);
        gc.gridx++;
        add(stashTypeCombo, gc);
        gc.gridx++;
        add(colorCombo, gc);
        gc.gridx++;
        addListeners();
    }

    private void addListeners() {
        JPanel self = this;
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                parent.remove(self);
                parent.revalidate();
                parent.repaint();
            }
        });
    }

}
