package main.java.com.slimtrade.gui.stash;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;

public class REMOVE_StashTabWindow extends AbstractWindowDialog {

	private static final long serialVersionUID = 1L;
	
	JPanel rowContainer;
	GridBagConstraints gcRow;
	int rowBuffer = 5;
	
	public REMOVE_StashTabWindow(){
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
//		gcRow.weightx = 1;
//		gcRow.weighty = 1;
//		gcRow.gridheight = 1;
		gcRow.anchor = GridBagConstraints.PAGE_START;
		gcRow.insets = new Insets(0, 0, rowBuffer, 0);
		
		rowContainer = new JPanel(new GridBagLayout());
		rowContainer.setBackground(Color.RED);
		
		//TEMP
		addNewRow();
		addNewRow();
		addNewRow();
		
		JScrollPane rowScrollPane = new JScrollPane(rowContainer);
		StashTabRow dummyRow = new StashTabRow();
//		rowContainer.revalidate();
		Dimension scrollPaneSize = dummyRow.getPreferredSize();
		scrollPaneSize.width+= 25;
		scrollPaneSize.height = scrollPaneSize.height*10 + rowBuffer*10;
		
//		System.out.println("PANE " + scrollPaneSize);
//		rowContainer.setMinimumSize(dummyRow.getPreferredSize());
//		rowScrollPane.setMinimumSize(scrollPaneSize);
		rowScrollPane.setPreferredSize(scrollPaneSize);
		
//		rowContainer.revalidate();
//		rowScrollPane.revalidate();
		
		rowScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		rowScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//		System.out.println(dummyRow.getPreferredSize());
//		rowScrollPane.revalidate();
		
		JPanel buttonPanel = new JPanel(new GridBagLayout());
		JButton addButton = new JButton("Add");
		JButton saveButton = new JButton("Save");
		JButton revertButton = new JButton("Revert");
		buttonPanel.add(addButton);
		buttonPanel.add(saveButton);
		buttonPanel.add(revertButton);
		
		
		
		container.add(rowScrollPane, gc);
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
					if(row.isFresh()){
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
		
		//TEMP
//		addNewRow();
//		addNewRow();
//		addNewRow();
//		container.setPreferredSize(null);
//		Dimension windowSize = container.getPreferredSize();
//		System.out.println(windowSize);
//		this.resizeWindow(windowSize.width, windowSize.height);
		this.autoReisize();
	}
	
	//TODO : this
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
			row.setFresh(false);
			if(row.isDelete()){
				rowContainer.remove(c);
			}
		}
		rowContainer.revalidate();
		rowContainer.repaint();
	}
	
	
}
