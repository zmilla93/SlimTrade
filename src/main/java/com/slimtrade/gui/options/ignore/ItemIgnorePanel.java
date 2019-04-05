package main.java.com.slimtrade.gui.options.ignore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.com.slimtrade.gui.components.AddRemoveContainer;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class ItemIgnorePanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;

	AddRemoveContainer addRemoveContainer = new AddRemoveContainer();
	
	public ItemIgnorePanel(){
		this.setVisible(false);
		
		container.add(addRemoveContainer);
		
		JButton addButton = new JButton("ADD");
		JButton tempRevert = new JButton("REVERT");
		JButton tempSave = new JButton("SAVE");
		
		container.add(addButton);
		container.add(tempRevert);
		container.add(tempSave);
//		this.revalidate();
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.addPanel();
				addRemoveContainer.revalidate();
				addRemoveContainer.repaint();
			}
		});
		
		tempRevert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.revertChanges();
				addRemoveContainer.revalidate();
				addRemoveContainer.repaint();
			}
		});
		
		tempSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.saveChanges();
				addRemoveContainer.revalidate();
				addRemoveContainer.repaint();
			}
		});
		
		
		
	}
	
}
