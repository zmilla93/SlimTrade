package main.java.com.slimtrade.gui.options.ignore;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import main.java.com.slimtrade.gui.components.AddRemovePanel;
import main.java.com.slimtrade.gui.components.RemovablePanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class ItemIgnorePanel extends ContainerPanel {

	private static final long serialVersionUID = 1L;

	AddRemovePanel addRemoveContainer = new AddRemovePanel();
	
	public ItemIgnorePanel(){
		this.setVisible(false);
		
		container.add(addRemoveContainer);
		
		JButton addButton = new JButton("ADD");
		JButton tempRevert = new JButton("REVERT");
		JButton tempSave = new JButton("SAVE");
		
		container.add(addButton);
		container.add(tempRevert);
		container.add(tempSave);
		
		addButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.addPanel(new RemovablePanel());
			}
		});
		
		tempRevert.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.revertChanges();
			}
		});
		
		tempSave.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				addRemoveContainer.saveChanges();
			}
		});
		
		
		
	}
	
}
