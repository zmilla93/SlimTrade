package main.java.com.slimtrade.gui.options.customizer;

import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JSlider;

import main.java.com.slimtrade.core.utility.TradeOffer;
import main.java.com.slimtrade.enums.MessageType;
import main.java.com.slimtrade.gui.messaging.AbstractMessagePanel;
import main.java.com.slimtrade.gui.messaging.TradePanelA;
import main.java.com.slimtrade.gui.options.AbstractContentPanel;
import main.java.com.slimtrade.gui.options.MacroRow;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class MessageCustomizerPanel extends AbstractContentPanel {

	private static final long serialVersionUID = 1L;

	private JLabel incomingLabel = new JLabel("Incoming Trade");
	private JLabel outgoingLabel = new JLabel("Outgoing Trade");
	
	public MessageCustomizerPanel() {
		
		//TODO : Colors
		Random rng = new Random();
		TradeOffer tradeIn = new TradeOffer("", "", MessageType.INCOMING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");
		TradeOffer tradeOut = new TradeOffer("", "", MessageType.OUTGOING_TRADE, null, "SmashyMcFireBalls", "ITEM_NAME", 3.5, "chaos", 3.5, "STASH_TAB", rng.nextInt(12) + 1, rng.nextInt(12) + 1, "", "");

		
		AbstractMessagePanel exampleTradeIn = new TradePanelA(tradeIn, 40);
		AbstractMessagePanel exampleTradeOut = new TradePanelA(tradeOut, 40);
		
		PresetMacroRow inPreset1 = new PresetMacroRow("TEST", "/res");
		inPreset1.addRow("ROW!", "TESTTEXT");
		
		
		JSlider sizeSlider = new JSlider();
		sizeSlider.setMinorTickSpacing(1);
		sizeSlider.setMajorTickSpacing(5);
		sizeSlider.setPaintTicks(true);
		sizeSlider.setMinimum(30);
		sizeSlider.setMaximum(50);
		
		addRow(incomingLabel, gc);
		addRow(exampleTradeIn, gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		
		PresetMacroRow row1 = new PresetMacroRow("TEST", "/res");
		row1.addRow("ROW!", "TESTTEXT");
		row1.addRow("ROW!", "TESTTEXT");
		row1.addRow("ROW!", "TESTTEXT");
		row1.addRow("ROW!", "TESTTEXT");
		addRow(row1, gc);
		
		
		
		addRow(outgoingLabel, gc);
		addRow(exampleTradeOut, gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		addRow(new BufferPanel(0,10), gc);
		addRow(new MacroRow(), gc);
		
		
//		addRow(sizeSlider, gc);
//		sizeSlider.addChangeListener(new ChangeListener(){
//			public void stateChanged(ChangeEvent arg0) {
//				exampleTrade.resizeMessage(sizeSlider.getValue());
//				System.out.println(sizeSlider.getValue());
//			}
//		});
	}

}
