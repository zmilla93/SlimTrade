package com.zrmiller.slimtrade.panels;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;

import com.zrmiller.slimtrade.Overlay;

public class HistoryPanel extends JPanel{

	public static int width = 400;
	public static int height = 400;
	
	public HistoryPanel(){
		this.setVisible(false);
		this.setLayout(Overlay.flowCenter);
		this.setPreferredSize(new Dimension(width, height));
		this.setBounds(0, 0, width, height);
		
		
		BasicTitlebar titlebar = new BasicTitlebar("History", this, width);
		
		String[] columnNames = {"One", "Two"};
		Object[][] data = {
				{"F", "L"},
				{"F", "L"},
				{"F", "L"},
		};
		JTable table = new JTable(data, columnNames);
		//table.columnAdded();
		this.add(titlebar);
		this.add(table);
	}
	
}
