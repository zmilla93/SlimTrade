
package main.java.com.slimtrade.gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;
import main.java.com.slimtrade.gui.options.customizer.IncomingCustomizer;
import main.java.com.slimtrade.gui.options.customizer.OutgoingCustomizer;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.stashtab.StashTabPanel;

public class FancyWindow extends AbstractWindowDialog {

	private JPanel display = new JPanel();
	
	public FancyWindow(){
		super("Fancy Window");
		this.setFocusableWindowState(true);
		this.setFocusable(true);
		container.setLayout(new BorderLayout());
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
		
		
		
		
		
		
		
		
		
		
		JScrollPane scrollDisplay = new JScrollPane(display);
		display.setBackground(Color.GREEN);
		
		display.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		int buffer = 6;
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, buffer));
		JButton cancelButton = new JButton("CANCEL");
		JButton saveButton = new JButton("SAVE");
		bottomPanel.add(cancelButton);
		bottomPanel.add(saveButton);
		
		
		JButton basicsButton = new JButton("Basics");
		BasicsPanel basicsPanel = new BasicsPanel();
		link(basicsButton, basicsPanel);
		display.add(basicsPanel, gc);
		
		JButton stashButton = new JButton("Stash Manager");
		StashTabPanel stashPanel = new StashTabPanel();
		link(stashButton, stashPanel);
		display.add(stashPanel, gc);
		
		JButton incomingButton = new JButton("Incoming Macros");
		IncomingCustomizer incomingPanel = new IncomingCustomizer(this);
		link(incomingButton, incomingPanel);
		display.add(incomingPanel, gc);
		
		JButton outgoingButton = new JButton("Outgoing Macros");
		OutgoingCustomizer outgoingPanel = new OutgoingCustomizer();
		link(outgoingButton, outgoingPanel);
		display.add(outgoingPanel, gc);
		
		JButton audioButton = new JButton("Audio");
		AudioPanel audioPanel = new AudioPanel();
		link(audioButton, audioPanel);
		display.add(audioPanel, gc);
		
		JButton advancedButton = new JButton("ADVANCED");
		AdvancedPanel advancedPanel = new AdvancedPanel();
		link(advancedButton, advancedPanel);
		display.add(advancedPanel, gc);
		
		JButton contactButton = new JButton("CONTACT");
		ContactPanel contactPanel = new ContactPanel();
		link(contactButton, contactPanel);
		display.add(contactPanel, gc);
		
		
		menuPanel.add(basicsButton);
		menuPanel.add(stashButton);
		menuPanel.add(incomingButton);
		menuPanel.add(outgoingButton);
		menuPanel.add(audioButton);
		menuPanel.add(advancedButton);
		menuPanel.add(contactButton);
		
		
		
		
				
		
		container.add(new BufferPanel(0,buffer), BorderLayout.NORTH);
		container.add(new BufferPanel(buffer,0), BorderLayout.EAST);
		container.add(bottomPanel, BorderLayout.SOUTH);
		container.add(menuPanel, BorderLayout.WEST);
		container.add(scrollDisplay, BorderLayout.CENTER);
//		double modWidth = 0.4;
//		double modHeight = 0.6;
//		this.resizeWindow((int)(TradeUtility.screenSize.width*modWidth),(int)(TradeUtility.screenSize.height*modHeight));
		
		incomingPanel.setVisible(true);
//		advancedPanel.setVisible(true);
		this.autoReisize();
		
		AbstractWindowDialog local = this;
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				incomingPanel.reset();
				local.autoReisize();
			}
		});
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				incomingPanel.saveData();
			}
		});
	}
	
	private void link(JButton b, JPanel p){
		FancyWindow local = this;
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				p.setVisible(true);
				local.autoReisize();
			}
		});
	}
	
	private void hideAllWindows(){
		for(Component c : display.getComponents()){
			c.setVisible(false);
		}
	}
	
}
