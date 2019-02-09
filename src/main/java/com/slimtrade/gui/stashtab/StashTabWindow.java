package main.java.com.slimtrade.gui.stashtab;

import javax.swing.BoxLayout;
import javax.swing.JLabel;

import main.java.com.slimtrade.enums.StashTabColor;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;

public class StashTabWindow extends AbstractWindowDialog {

	private static final long serialVersionUID = 1L;

	public StashTabWindow(){
		super("Stash Tab Manager");
		this.setFocusableWindowState(true);
		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.add(new StashTabRow());
		container.add(new StashTabRow());
		container.add(new StashTabRow());
		
		for(StashTabColor color : StashTabColor.values()){
			JLabel label = new JLabel("STASH TAB COLORS");
			label.setOpaque(true);
			label.setBackground(color.getBackground());
			label.setForeground(color.getForeground());
			container.add(label);
		}
	}
	
	
}
