package main.java.com.slimtrade.gui.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JPanel;

public class RemovablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private boolean newPanel = true;
	private boolean toBeDeleted = false;
	protected AddRemovePanel parent;
	
//	private JButton removeButton = new JButton();
	
	public RemovablePanel(AddRemovePanel parent){
		this.parent = parent;
		Random rand = new Random();
		int r = 50+rand.nextInt(150);
		int g = 50+rand.nextInt(150);
		int b = 50+rand.nextInt(150);
		this.setBackground(new Color(r, g, b));

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
		System.out.println("PARENT DISPOSE");
		markForDeletion();
	}
	
}
