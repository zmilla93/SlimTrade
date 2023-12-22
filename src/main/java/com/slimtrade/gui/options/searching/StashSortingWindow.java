package com.slimtrade.gui.options.searching;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.windows.CustomDialog;

import java.awt.*;

public class StashSortingWindow extends CustomDialog {

    private final GridBagConstraints gc = ZUtil.getGC();

    public StashSortingWindow() {
        super("Sorting", true);
        setMinimumSize(null);
        setFocusable(false);
        setFocusableWindowState(false);
        contentPanel.setLayout(new GridBagLayout());
        refreshButtons();
    }

    public void refreshButtons() {
        contentPanel.removeAll();
        gc.gridy = 0;
        gc.fill = GridBagConstraints.BOTH;
        gc.weightx = 1;
        // FIXME : update this to new data model
//        for (StashSortData data : SaveManager.settingsSaveFile.data.stashSortData) {
//            JButton button = new NotificationButton(data.title);
//            contentPanel.add(button, gc);
//            if (data.colorIndex > 0) {
//                button.setBackground(StashTabColor.values()[data.colorIndex].getBackground());
//                button.setForeground(StashTabColor.values()[data.colorIndex].getForeground());
//            }
//            button.addMouseListener(new AdvancedMouseListener() {
//                @Override
//                public void click(MouseEvent e) {
//                    if (e.getButton() == MouseEvent.BUTTON1) {
//                        POEInterface.searchInStash(data.searchTerm);
//                    } else if (e.getButton() == MouseEvent.BUTTON3) {
//                        POEInterface.searchInStash("");
//                    }
//                }
//            });
//            gc.gridy++;
//        }
        setResizable(false);
        revalidate();
        repaint();
        pack();
    }

}
