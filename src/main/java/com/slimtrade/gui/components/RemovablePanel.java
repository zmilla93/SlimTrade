package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemovablePanel extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	
	private boolean newPanel = true;
	private boolean toBeDeleted = false;
	protected AddRemovePanel parent;
	
//	private JButton removeButton = new JButton();
	
	public RemovablePanel(AddRemovePanel parent){
		this.parent = parent;
        App.eventManager.addListener(this);
        updateColor();
	}
	
	//TODO : REMOVE THIS ADD DO ACTIONS LOCALLY
	public void setRemoveButton(JButton button){
		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
	}
	
	public void markForDeletion(){
		this.toBeDeleted = true;
		this.setVisible(false);
		parent.revalidate();
	}
	
	public void setToBeDeleted(boolean state){
		toBeDeleted = state;
	}
	
	public boolean isToBeDeleted(){
		return toBeDeleted;
	}
	
	public void setNewPanel(boolean state){
		newPanel = state;
	}
	
	public boolean isNewPanel(){
		return newPanel;
	}

	protected void dispose(){
		markForDeletion();
		parent.updateColor();
        parent.refreshPanels();
		parent.revalidate();
		parent.repaint();
	}

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.BACKGROUND);
    }

}
