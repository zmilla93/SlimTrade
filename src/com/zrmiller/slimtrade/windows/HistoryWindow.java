package com.zrmiller.slimtrade.windows;

import javax.swing.JTable;

public class HistoryWindow extends BasicMenuWindow{

	private static final long serialVersionUID = 1L;
	public static int width = 400;
	public static int height = 400;
	
	public HistoryWindow(String title){
		super("History", width, height);
		this.setVisible(false);
//		this.setSize(width, height);
//		container.setPreferredSize(new Dimension(width, height));
//		this.setBounds(0, 0, width, height);
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
