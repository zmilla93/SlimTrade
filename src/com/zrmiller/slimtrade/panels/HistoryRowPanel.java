package com.zrmiller.slimtrade.panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;

public class HistoryRowPanel extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private int totalWidth = 600;
	private int innerWidth = 500;
	private int height = 25;
	
	private double nameWidthPercent = 0.4;
	private double itemWidthPercent = 0.4;
	private double priceWidthPercent = 0.2;
	
	public HistoryRowPanel(String name, String item, String priceType, double priceQuantity){
		
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		this.setPreferredSize(new Dimension(totalWidth, height));
		this.setBackground(Color.green);
		
		Border border = BorderFactory.createBevelBorder(BevelBorder.RAISED);
		JPanel sizeContainer = new JPanel();
		JPanel namePanel = new JPanel();
		JPanel itemPanel = new JPanel();
		JPanel pricePanel = new JPanel();
		
		sizeContainer.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		sizeContainer.setPreferredSize(new Dimension(innerWidth, height));
		namePanel.setPreferredSize(new Dimension((int)(innerWidth*nameWidthPercent), height));
		itemPanel.setPreferredSize(new Dimension((int)(innerWidth*itemWidthPercent), height));
		pricePanel.setPreferredSize(new Dimension((int)(innerWidth*priceWidthPercent), height));
		
		namePanel.setBorder(border);
		itemPanel.setBorder(border);
		pricePanel.setBorder(border);
		
		JLabel nameLabel = new JLabel(name);
		JLabel itemLabel = new JLabel(item);
		JLabel priceLabel = new JLabel("PRICE");
		
		namePanel.add(nameLabel);
		itemPanel.add(itemLabel);
		pricePanel.add(priceLabel);
		
		sizeContainer.add(namePanel);
		sizeContainer.add(itemPanel);
		sizeContainer.add(pricePanel);
		this.add(sizeContainer);
	}

}
