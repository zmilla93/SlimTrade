package com.slimtrade.core.observing.improved;

import com.slimtrade.App;

import java.awt.*;

public interface IColorable {

	void updateColor();
	default void removeListener() {
        App.eventManager.removeColorListener(this);
        for(Component c : ((Container)this).getComponents()) {
            if(c instanceof IColorable) {
                ((IColorable) c).removeListener();
            }
        }
    }

}
