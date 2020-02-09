package com.slimtrade.gui.messaging;

import com.slimtrade.App;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.basic.PaintedPanel;

import java.awt.Color;

import javax.swing.BorderFactory;

public class ItemClickPanel extends PaintedPanel {

	private static final long serialVersionUID = 1L;

	public ItemClickPanel() {
//		backgroudDefault = Color.black;
		backgroundHover = Color.yellow;
		textDefault = Color.yellow;
		textHover = Color.black;
		borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
		borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
//        App.eventManager.addColorListener(this);
//        this.updateColor();
		
//		this.addMouseListener();
	}

//    @Override
//    public void updateColor() {
//        this.setBackground(Color.RED);
//        this.setBackground(Color.BLUE);
//    }
}
