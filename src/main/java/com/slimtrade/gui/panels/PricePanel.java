package com.slimtrade.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.CurrencyType;
import com.slimtrade.gui.basic.PaintedPanel;

public class PricePanel extends PaintedPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

//	private JLabel label = new JLabel();
	
	public PricePanel(String price, double quant, boolean paren) {
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		label = new JLabel();
		String num = quant > 0 ? TradeUtility.getFixedDouble(quant, paren) + " ": "" ;
		CurrencyType currency = TradeUtility.getCurrencyType(price);
//		System.out.println(currency + " ::: " + quant + " ::: " + num);
		if(currency!=null && !currency.getPath().equals("")){
			label.setText(num);
			this.add(label, gc);
			gc.gridx++;
			IconPanel img = new IconPanel(currency.getPath());
			this.add(img, gc);
		}else{
			label.setText(num + price);
			this.add(label, gc);
		}
	}
	
	public JLabel getLabel(){
		return this.label;
	}

}
