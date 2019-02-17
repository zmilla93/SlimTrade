package main.java.com.slimtrade.gui.options.stash;

import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.stash.StashTabPanel;

public class StashPanel extends ContentPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JLabel overlayLabel = new JLabel("Stash Grid Alignment");
	JButton editButton = new JButton("Edit");
	
	public StashPanel(){
		JPanel overlayPanel = new JPanel();
		
		overlayPanel.setLayout(new GridBagLayout());
		overlayPanel.add(overlayLabel, gc);
		gc.gridx++;
		overlayPanel.add(editButton, gc);
		
//		ContentPanel stashtabPanel = new ContentPanel();
		StashTabPanel stash = new StashTabPanel();
		stash.setMinimumSize(new Dimension(400, 400));
//		stash.autoResize();
		stash.revalidate();
		
		ContentPanel pane = new ContentPanel();
		pane.setPreferredSize(new Dimension(400,400));
		
		
		gc.gridx = 0;
		this.addRow(overlayPanel, gc);
		this.addRow(stash, gc);
		this.addRow(pane, gc);
		
		pane.setPreferredSize(null);
		pane.setPreferredSize(pane.getPreferredSize());
		this.setPreferredSize(null);
		this.setPreferredSize(this.getPreferredSize());
		this.setPreferredSize(new Dimension(500,500));
//		
//		pane.autoResize();
//		this.autoResize();
//		this.setPreferredSize(null);
		
		
		
		
		
//		this.autoResize();
	}
	
}
