package main.java.com.slimtrade.gui.stashtab;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;

public class StashTabWindow extends AbstractWindowDialog {

	private static final long serialVersionUID = 1L;
	
	JPanel rowContainer;
	GridBagConstraints gcRow;
	
	public StashTabWindow(){
		super("Stash Tab Manager");
		this.setFocusableWindowState(true);
//		container.setLayout(new BoxLayout(container, BoxLayout.PAGE_AXIS));
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gcRow = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gcRow.gridx = 0;
		gcRow.gridy = 0;
		gcRow.insets = new Insets(0, 0, 5, 0);
		
		rowContainer = new JPanel(new GridBagLayout());
		rowContainer.setBackground(Color.RED);
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JButton addButton = new JButton("Add");
		JButton saveButton = new JButton("Save");
		JButton revertButton = new JButton("Revert");
		buttonPanel.add(addButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(revertButton);
		
		addNewRow();
		addNewRow();
		addNewRow();
//		rowContainer.add();
//		gcRow.gridy++;
//		rowContainer.add(new StashTabRow(), gcRow);
//		gcRow.gridy++;
//		rowContainer.add(new StashTabRow(), gcRow);
//		gcRow.gridy++;
		
		container.add(rowContainer, gc);
		gc.gridy++;
		container.add(buttonPanel, gc);
		
		addButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				addNewRow();
			}
		});
		
		saveButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				saveComponents();
			}
		});
		
		revertButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				for(Component c : rowContainer.getComponents()){
					StashTabRow row = (StashTabRow)c;
					if(row.isNewRow()){
						rowContainer.remove(row);
					}
					if(row.isDelete()){
						row.setDelete(false);
						row.setVisible(true);
					}
				}
				rowContainer.revalidate();
				rowContainer.repaint();
			}
		});
		
		this.setVisible(true);
	}
	
	private void addSavedRow(){
		
	}
	
	private void addNewRow(){
		StashTabRow row = new StashTabRow();
		rowContainer.add(row, gcRow);
		gcRow.gridy++;
		row.getDeleteButton().addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				row.setVisible(false);
				row.setDelete(true);
			}
		});
		rowContainer.revalidate();
		rowContainer.repaint();
	}
	
	private void saveComponents(){
		for(Component c : rowContainer.getComponents()){
			StashTabRow row = (StashTabRow)c;
			row.setNewRow(false);
			if(row.isDelete()){
				rowContainer.remove(c);
			}
		}
		rowContainer.revalidate();
		rowContainer.repaint();
	}
	
	
}
