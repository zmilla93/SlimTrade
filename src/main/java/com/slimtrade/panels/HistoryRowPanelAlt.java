package main.java.com.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

import main.java.com.slimtrade.buttons.BasicIconButton;
import main.java.com.slimtrade.core.ColorManager;

public class HistoryRowPanelAlt extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int totalWidth = 750;
	private int height = 25;
	
	private double timeWidthPercent = 0.2;
	private double nameWidthPercent = 0.3;
	private double itemWidthPercent = 0.3;
	private double priceWidthPercent = 0.2;
	public BasicIconButton refreshButton = new BasicIconButton("/resources/icons/refresh1.png", height, height);

	Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
	Border borderHover = BorderFactory.createBevelBorder(BevelBorder.LOWERED);
	
	public HistoryRowPanelAlt(String name, String item, String priceType, double priceQuantity){
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(totalWidth, height));
		this.setBackground(Color.green);
		
		
		JPanel buttonPanel = new JPanel();
		JPanel timePanel = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JPanel pricePanel = new JPanel();
		
		int remainingWidth = totalWidth-height;
		buttonPanel.setPreferredSize(new Dimension(height, height));
		timePanel.setPreferredSize(new Dimension((int)(remainingWidth*timeWidthPercent), height));
		namePanel.setPreferredSize(new Dimension((int)(remainingWidth*nameWidthPercent), height));
		itemPanel.setPreferredSize(new Dimension((int)(remainingWidth*itemWidthPercent), height));
		pricePanel.setPreferredSize(new Dimension((int)(remainingWidth*priceWidthPercent), height));
		
		refreshButton.setBorder(border);
		buttonPanel.setBorder(border);
		timePanel.setBorder(border);
		namePanel.setBorder(border);
		itemPanel.setBorder(border);
		pricePanel.setBorder(border);
		
		refreshButton.setBackground(Color.LIGHT_GRAY);
		JLabel timeLabel = new JLabel("TIMESTAMP");
		JLabel nameLabel = new JLabel(name);
		JLabel itemLabel = new JLabel(item);
		JLabel priceLabel = new JLabel("PRICE");
		
		timePanel.add(timeLabel);
		namePanel.add(nameLabel);
		itemPanel.add(itemLabel);
		pricePanel.add(priceLabel);
		
		this.add(refreshButton);
		this.add(timePanel);
		this.add(namePanel);
		this.add(itemPanel);
		this.add(pricePanel);
		
		updateColor();

	}
	
	public void updateColor(){
//		refreshButton.bgColor = ColorManager.GenericWindow.buttonBG;
//		refreshButton.bgColor_hover = ColorManager.GenericWindow.buttonBG_hover;
		refreshButton.setColorPresets(ColorManager.GenericWindow.buttonBG, ColorManager.GenericWindow.buttonBG_hover);
		refreshButton.setBorderPresets(border, border);
		refreshButton.updateColorPresets();
	}
}
