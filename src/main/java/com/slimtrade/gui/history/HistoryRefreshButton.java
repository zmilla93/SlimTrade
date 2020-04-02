package com.slimtrade.gui.history;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.CustomIcons;

public class HistoryRefreshButton extends IconButton {

    public HistoryRefreshButton() {
        super(CustomIcons.REFRESH, HistoryRow.ROW_HEIGHT);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        colorDefault = ColorManager.LOW_CONTRAST_1;
    }
}
