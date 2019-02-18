
package main.java.com.slimtrade.gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.options.advanced.AdvancedPanel;
import main.java.com.slimtrade.gui.options.audio.AudioPanel;
import main.java.com.slimtrade.gui.options.macros.IncomingCustomizer;
import main.java.com.slimtrade.gui.options.macros.OutgoingCustomizer;
import main.java.com.slimtrade.gui.options.stash.StashPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.stash.ResizableWindow;
import main.java.com.slimtrade.gui.windows.AbstractWindow;

public class OptionsWindow extends ResizableWindow {
	
	private static final long serialVersionUID = 1L;
	private JPanel display = new JPanel();
	JScrollPane scrollDisplay;
	
	public OptionsWindow(){
//		super("Fancy Window", false);
		super("Options");
		this.setFocusableWindowState(true);
		this.setFocusable(true);
		container.setLayout(new BorderLayout());
//		container.setBackground(Color.LIGHT_GRAY);
		JPanel menuPanel = new JPanel();
		menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.PAGE_AXIS));
		
		
		scrollDisplay = new JScrollPane(display);
		display.setBackground(Color.GREEN);
		
		display.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		int buffer = 6;
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, buffer));
		JButton resizeButton = new JButton("RESIZE");
		JButton cancelButton = new JButton("CANCEL");
		JButton saveButton = new JButton("SAVE");
		bottomPanel.add(resizeButton);
		bottomPanel.add(cancelButton);
		bottomPanel.add(saveButton);
		
		
		JButton basicsButton = new JButton("Basics");
		BasicsPanel basicsPanel = new BasicsPanel();
		link(basicsButton, basicsPanel);
		display.add(basicsPanel, gc);
		
		JButton stashButton = new JButton("Stash Manager");
		StashPanel stashPanel = new StashPanel();
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
		
		
		
		
//		container.setLayout(new BorderLayout());
		container.add(new BufferPanel(0,buffer), BorderLayout.NORTH);
		container.add(new BufferPanel(buffer,0), BorderLayout.EAST);
		container.add(bottomPanel, BorderLayout.SOUTH);
		container.add(menuPanel, BorderLayout.WEST);
		container.add(scrollDisplay, BorderLayout.CENTER);
//		this.setMinimumSize(new Dimension(500,900));
//		double modWidth = 0.4;
//		double modHeight = 0.6;
//		this.resizeWindow((int)(TradeUtility.screenSize.width*modWidth),(int)(TradeUtility.screenSize.height*modHeight));
		
		incomingPanel.setVisible(true);
//		advancedPanel.setVisible(true);
//		this.autoReisize();
		this.setMinimumSize(new Dimension(900,600));
		this.refresh();
		this.setMinimumSize(new Dimension(300,300));
		this.setVisible(true);
		
		//TODO : Resize
		ResizableWindow local = this;
		resizeButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
//				Dimension size = new Dimension(0, 0);
//				for(Component c : scrollDisplay.getComponents()){
//					Dimension newSize = c.getPreferredSize();
//					if(size.width<newSize.width){
//						size.width = newSize.width;
//					}
//					if(size.height<newSize.height){
//						size.height = newSize.height;
//					}
//				}
//				scrollDisplay.revalidate();
//				scrollDisplay.setPreferredSize(scrollDisplay.getPreferredSize());
//				scrollDisplay.setPreferredSize(size);
//				System.out.println(size);
//				display.setPreferredSize(size);
			
//				local.setPreferredSize(size);
//				local.pack();
//				local.resize
				local.autoResize();
				local.pack();

			}
		});
		
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				incomingPanel.reset();
//				local.autoReisize();
				local.pack();
			}
		});
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				incomingPanel.saveData();
				audioPanel.save();
				Main.saveManager.saveToDisk();
			}
		});
		
	}
	
	private void link(JButton b, JPanel p){
		OptionsWindow local = this;
		b.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				hideAllWindows();
				p.setVisible(true);
//				System.out.println("CONTENT" + contentPane.getPreferredSize());
//				local.autoReisize();
//				local.autore
//				local.setPreferredSize(null);
//				local.setPreferredSize(local.getPreferredSize());
//				refresh();
//				local.pack();
			}
		});
	}
	
	private void hideAllWindows(){
		for(Component c : display.getComponents()){
			c.setVisible(false);
		}
	}
	
	public void refresh(){
//		System.out.println("Refresh");
//		display.setPreferredSize(null);
		display.revalidate();
//		display.setPreferredSize(display.getPreferredSize());
//		scrollDisplay.setPreferredSize(null);
//		scrollDisplay.setPreferredSize(scrollDisplay.getPreferredSize());
		scrollDisplay.revalidate();
		this.pack();
		this.repaint();
	}
	
}
