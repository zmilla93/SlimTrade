package main.java.com.slimtrade.gui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.enums.CurrencyType;

public class PricePanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JLabel priceLabel = new JLabel();
	
	public PricePanel(String price, double quant, boolean paren) {
		this.setLayout(new GridBagLayout());
		this.setOpaque(false);
		GridBagConstraints gc = new GridBagConstraints();
		
		gc.gridx = 0;
		gc.gridy = 0;
		priceLabel = new JLabel();
		String num = quant > 1 ? TradeUtility.getFixedDouble(quant, paren) + " ": "" ;
		CurrencyType currency = TradeUtility.getCurrencyType(price);
//		System.out.println(currency + " ::: " + quant + " ::: " + num);
		if(currency!=null && !currency.getPath().equals("")){
			priceLabel.setText(num);
			this.add(priceLabel, gc);
			gc.gridx++;
			IconPanel img = new IconPanel(currency.getPath());
			this.add(img, gc);
		}else{
			priceLabel.setText(num + price);
			this.add(priceLabel, gc);
		}
	}
	
	public JLabel getLabel(){
		return this.priceLabel;
	}

}
