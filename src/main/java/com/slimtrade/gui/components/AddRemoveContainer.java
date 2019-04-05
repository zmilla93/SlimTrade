package main.java.com.slimtrade.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;

public class AddRemoveContainer extends JPanel {

	private static final long serialVersionUID = 1L;

	public AddRemoveContainer(){
		this.setPreferredSize(new Dimension(400,100));
		this.setBackground(Color.RED);
	}
	
	public void addPanel(){
		RemovablePanel p = new RemovablePanel();
		this.add(p);
		this.revalidate();
		this.repaint();
	}
	
	public void saveChanges(){
		for(Component c : this.getComponents()){
			if(c instanceof RemovablePanel){
				RemovablePanel panel = (RemovablePanel)c;
				if(panel.isToBeDeleted()){
					this.remove(panel);
				}else if(panel.isNewPanel()){
					panel.setNewPanel(false);
				}
			}
		}
	}
	
	public void revertChanges(){
		for(Component c : this.getComponents()){
			if(c instanceof RemovablePanel){
				RemovablePanel panel = (RemovablePanel)c;
				if(panel.isNewPanel()){
					this.remove(panel);
				}else if(panel.isToBeDeleted()){
					panel.setVisible(true);
					panel.setToBeDeleted(false);
				}
				
			}
		}
	}
	
}
