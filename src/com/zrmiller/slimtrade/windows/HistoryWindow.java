package com.zrmiller.slimtrade.windows;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.zrmiller.slimtrade.Overlay;

public class HistoryWindow extends BasicMenuWindow{

	public static int width = 400;
	public static int height = 400;
	
	public HistoryWindow(){
		super("History", width, height);
		this.setVisible(false);
		container.setPreferredSize(new Dimension(width, height));
		container.setBounds(0, 0, width, height);
		String[] columnNames = {"One", "Two"};
		Object[][] data = {
				{"F", "L"},
				{"F", "L"},
				{"F", "L"},
		};
		JTable table = new JTable(data, columnNames);
		container.add(table);
	}
	
}
