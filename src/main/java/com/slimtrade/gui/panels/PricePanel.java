package com.slimtrade.gui.panels;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.gui.basic.PaintedPanel;
import com.slimtrade.gui.enums.POEImage;

import javax.swing.*;
import java.awt.*;

public class PricePanel extends JPanel {

	private JLabel label = new JLabel();

	public PricePanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		this.add(label, gc);
	}

	public PricePanel(String price, double quant, boolean paren) {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		String num = quant > 0 ? TradeUtility.getFixedDouble(quant, paren) + " " : "" ;
		POEImage poeImage = null;
		if(price == null) {
			price = "Unpriced";
		} else {
			poeImage = TradeUtility.getPOEImage(price.toLowerCase());
		}
		if(poeImage!=null){
			label.setText(num);
			this.add(label, gc);
			gc.gridx++;
			IconPanel img = new IconPanel(poeImage.getImage(20), 25);
			this.add(img, gc);
		}else{
			label.setText(num + price);
			this.add(label, gc);
		}
	}

	public void setText(String text) {
		label.setText(text);
	}

	public void setTextColor(Color color) {
		label.setForeground(color);
	}
	
	public JLabel getLabel(){
		return this.label;
	}

}
