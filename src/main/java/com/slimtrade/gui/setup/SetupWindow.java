package main.java.com.slimtrade.gui.setup;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.java.com.slimtrade.gui.basic.AbstractResizableWindow;
import main.java.com.slimtrade.gui.buttons.BasicButton;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.setup.panels.AbstractSetupPanel;
import main.java.com.slimtrade.gui.setup.panels.CharacterPanel;
import main.java.com.slimtrade.gui.setup.panels.PathPanel;

public class SetupWindow extends AbstractResizableWindow {

	private static final long serialVersionUID = 1L;
	
	private int panelIndex = 0;
	private ArrayList<AbstractSetupPanel> panels = new ArrayList<AbstractSetupPanel>();
	
	private JButton previousButton = new BasicButton("< Back");
	private JButton nextButton = new BasicButton("Next >");

	public SetupWindow() {
		super("Setup");
		this.setFocusable(true);
		this.setFocusableWindowState(true);

		JLabel panelLabel = new JLabel("Client Path");
		AbstractSetupPanel pathPanel = new PathPanel(this);
		AbstractSetupPanel characterPanel = new CharacterPanel(this);
		JPanel buttonPanel = new JPanel(new BorderLayout());
		
		JLabel infoLabel = new JLabel("Settings can be changed anytime from the Options Menu.");
		
		panels.add(pathPanel);
		panels.add(characterPanel);
		

		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets.bottom = 10;
		
		//Bottom Panel
		buttonPanel.add(previousButton, BorderLayout.WEST);
		buttonPanel.add(new BufferPanel(120, 0), BorderLayout.CENTER);
		buttonPanel.add(nextButton, BorderLayout.EAST);
		
		//Container
		container.add(panelLabel, gc);
		gc.gridy++;
		container.add(pathPanel, gc);
		container.add(characterPanel, gc);
		gc.gridy++;
		container.add(infoLabel, gc);
		gc.gridy++;
		gc.insets.bottom = 0;
		container.add(buttonPanel, gc);
		
		for(JPanel p : panels){
			p.setVisible(false);
		}
		panels.get(0).setVisible(true);


		previousButton.setEnabled(false);
		nextButton.setEnabled(false);
		if(panels.get(0).isComplete()){
			nextButton.setEnabled(true);
		}
		
		previousButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panels.get(panelIndex).setVisible(false);
				panelIndex--;
				panels.get(panelIndex).setVisible(true);
				updateButtonState();
			}
		});
		
		nextButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panels.get(panelIndex).setVisible(false);
				panelIndex++;
				panels.get(panelIndex).setVisible(true);
				updateButtonState();
			}
		});
		
		this.setPreferredSize(new Dimension(800, 400));
		this.pack();
		
	}
	
	public void updateButtonState(){
		if(panelIndex>0){
			previousButton.setEnabled(true);
		}else{
			previousButton.setEnabled(false);
		}
		if(panels.get(panelIndex).isComplete()){
			nextButton.setEnabled(true);
		}else{
			nextButton.setEnabled(false);
		}
	}

}
