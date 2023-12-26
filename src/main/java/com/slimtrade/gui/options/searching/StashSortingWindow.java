package com.slimtrade.gui.options.searching;

import com.slimtrade.core.enums.StashTabColor;
import com.slimtrade.core.utility.AdvancedMouseListener;
import com.slimtrade.core.utility.POEInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.windows.CustomDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class StashSortingWindow extends CustomDialog {

    private final GridBagConstraints gc = ZUtil.getGC();

    /**
     * Mutual constructor, should be called by all other constructors.
     *
     * @param title Display title
     */
    private StashSortingWindow(String title) {
        super(title, true);
        setMinimumSize(null);
        setFocusable(false);
        setFocusableWindowState(false);
        setResizable(false);
    }

    /**
     * Constructor when using Separate windows.
     *
     * @param data Search Data
     */
    public StashSortingWindow(StashSearchGroupData data) {
        this(data.title());
        JPanel panel = buildButtonPanel(data);
        contentPanel.setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        contentPanel.add(panel, gc);
        pack();
    }

    /**
     * Constructor when using Combined windows.
     *
     * @param data ArrayList of Search Data
     */
    public StashSortingWindow(ArrayList<StashSearchGroupData> data) {
        this("Sorting");
    }

    private JPanel buildButtonPanel(StashSearchGroupData data) {
        JPanel panel = new JPanel(new GridBagLayout());
//        panel.setBackground(Color.GREEN);
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        int INSET_SIZE = 1;
        gc.insets = new Insets(INSET_SIZE, INSET_SIZE, INSET_SIZE, INSET_SIZE);
        for (StashSearchTermData term : data.terms()) {
            // FIXME : Make sure term title can't be only white space
            if (term.title().equals("")) continue;
            JButton button = createSearchButton(term);
            panel.add(button, gc);
            gc.insets.top = 0;
            gc.gridy++;
        }
        return panel;
    }

    private JButton createSearchButton(StashSearchTermData term) {
        JButton button = new JButton(term.title());
        Color borderColor = UIManager.getColor("Button.borderColor");
        if (term.colorIndex() > 0) {
            button.setBackground(StashTabColor.values()[term.colorIndex()].getBackground());
            button.setForeground(StashTabColor.values()[term.colorIndex()].getForeground());
            borderColor = StashTabColor.values()[term.colorIndex()].getForeground();
        }
        button.setBorder(BorderFactory.createLineBorder(borderColor, 1));
        button.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    POEInterface.searchInStash(term.searchTerm());
                } else if (e.getButton() == MouseEvent.BUTTON3) {
                    POEInterface.searchInStash("");
                }
            }
        });
        return button;
    }

}
