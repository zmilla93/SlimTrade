package com.slimtrade.gui.stashsearch;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.IColorable;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.enums.StashTabColor;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractWindow;
import com.slimtrade.gui.basic.PaintedPanel;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.options.stashsearch.StashSearchData;

import java.awt.*;
import java.awt.event.MouseEvent;

public class StashSearchWindow extends AbstractWindow implements IColorable {

    //    private Container container;
    private GridBagConstraints gc = new GridBagConstraints();

    public StashSearchWindow() {
        super("Sorting", true, true);
        this.setFocusable(false);
        this.setFocusableWindowState(false);
        this.setMinimumSize(new Dimension(100, 40));
        center.setLayout(new GridBagLayout());
        gc.gridx = 0;
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        refresh();
        FrameManager.centerFrame(this);
    }

    public void refresh() {
        gc.gridy = 0;
        center.removeAll();
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        if (App.saveManager.settingsSaveFile.stashSearchData.size() == 0) {
            center.add(new CustomLabel("No Stash Data Found"), gc);
            gc.gridy++;
            center.add(new CustomLabel("Options > Stash Sorter"), gc);
            gc.gridy++;
        } else {
            gc.insets = new Insets(2, 2, 2, 2);
            for (StashSearchData data : App.saveManager.settingsSaveFile.stashSearchData) {
                addPanel(data);
                gc.insets.top = 0;
                gc.gridy++;
            }
        }
        ColorManager.recursiveColor(this);
        pack();
    }

    private void addPanel(StashSearchData data) {
        DataPanel panel = new DataPanel(data);
        center.add(panel, gc);
        gc.gridy++;
    }

    @Override
    public void updateColor() {
        super.updateColor();
        for (Component c : center.getComponents()) {
            if (c instanceof DataPanel) {
                DataPanel panel = (DataPanel) c;
                if (panel.data.color == StashTabColor.ZERO) {
                    panel.setBackgroundColor(ColorManager.MESSAGE_NAME_BG);
                    panel.setTextColor(ColorManager.TEXT);
                    panel.backgroundHover = ColorManager.PRIMARY;
                } else {
                    panel.setBackgroundColor(panel.data.color.getBackground());
                    panel.setTextColor(panel.data.color.getForeground());
                    panel.backgroundHover = ColorManager.lighter(panel.data.color.getBackground(), 20);
                }
                panel.borderHover = ColorManager.TEXT;
                panel.borderDefault = ColorManager.TEXT;
                panel.borderClick = ColorManager.TEXT;
            }
        }
    }

    @Override
    public void pinAction(MouseEvent e) {
        super.pinAction(e);
        FrameManager.saveWindowPins();
    }

    private class DataPanel extends PaintedPanel {
        public StashSearchData data;

        private DataPanel(StashSearchData data) {
            this.data = data;
            setText(data.searchName);
            this.setPreferredSize(new Dimension(this.getPreferredSize().width, this.getPreferredSize().height + 4));
            addMouseListener(new AdvancedMouseAdapter() {
                @Override
                public void click(MouseEvent e) {
                    PoeInterface.findInStash(data.searchTerms);
                }
            });
        }
    }
}
