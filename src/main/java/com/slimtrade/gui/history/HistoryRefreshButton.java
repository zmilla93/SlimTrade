package com.slimtrade.gui.history;

import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.PreloadedImage;

public class HistoryRefreshButton extends IconButton {

    public HistoryRefreshButton() {
        super(PreloadedImage.REFRESH.getImage(), HistoryRow.ROW_HEIGHT);
    }

    @Override
    public void updateColor() {
        super.updateColor();
        colorDefault = ColorManager.LOW_CONTRAST_1;
    }
}
