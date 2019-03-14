
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
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.general.AdvancedPanel;
import main.java.com.slimtrade.gui.options.general.AudioPanel;
import main.java.com.slimtrade.gui.options.macros.IncomingCustomizer;
import main.java.com.slimtrade.gui.options.macros.OutgoingCustomizer;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.stash.ResizableWindow;
import main.java.com.slimtrade.gui.stash.StashTabPanel;

public class OptionsWindow extends ResizableWindow {
	
	private static final long serialVersionUID = 1L;
	private JPanel display = new JPanel();
	JScrollPane scrollDisplay;
	
	JPanel menuPanel = new JPanel(new GridBagLayout());
	
	public OptionsWindow(){
		super("Options");
		this.setFocusableWindowState(true);
		this.setFocusable(true);
		container.setLayout(new BorderLayout());
		JPanel menuBorder = new JPanel(new BorderLayout());
		
//		container.setBackground(Color.red);
//		container.setBackground(Color.LIGHT_GRAY);
		
		menuBorder.add(menuPanel, BorderLayout.NORTH);
		
		
		scrollDisplay = new JScrollPane(display);
//		display.setBackground(Color.ORANGE);
		
		display.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		
		int buffer = 6;
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		menuPanel.setOpaque(false);
		menuBorder.setOpaque(false);
		
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, buffer));
//		JButton resizeButton = new JButton("RESIZE");
		JButton revertButton = new JButton("Revert Changes");
		JButton saveButton = new JButton("Save");
//		bottomPanel.add(resizeButton);
		bottomPanel.add(revertButton);
		bottomPanel.add(saveButton);
		
		
		ListButton generalButton = new ListButton("General");
		GeneralPanel generalPanel = new GeneralPanel();
		link(generalButton, generalPanel);
		display.add(generalPanel, gc);
		
		JButton stashButton = new ListButton("Stash Tabs");
		StashTabPanel stashPanel = new StashTabPanel();
		link(stashButton, stashPanel);
		display.add(stashPanel, gc);
		
		JButton incomingButton = new ListButton("Incoming Macros");
		IncomingCustomizer incomingPanel = new IncomingCustomizer(this);
		link(incomingButton, incomingPanel);
		display.add(incomingPanel, gc);
		
		JButton outgoingButton = new ListButton("Outgoing Macros");
		OutgoingCustomizer outgoingPanel = new OutgoingCustomizer();
		link(outgoingButton, outgoingPanel);
		display.add(outgoingPanel, gc);
		
//		JButton audioButton = new ListButton("Audio");
//		AudioPanel audioPanel = new AudioPanel();
//		link(audioButton, audioPanel);
//		display.add(audioPanel, gc);
		
//		JButton advancedButton = new ListButton("Advanced");
//		AdvancedPanel advancedPanel = new AdvancedPanel();
//		link(advancedButton, advancedPanel);
//		display.add(advancedPanel, gc);
		
		JButton contactButton = new ListButton("Contact");
		ContactPanel contactPanel = new ContactPanel();
		link(contactButton, contactPanel);
		display.add(contactPanel, gc);
		
		//TODO : Remove stash
		gc = new GridBagConstraints();
//		gc.anchor = GridBagConstraints.NORTH;
		
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets.bottom = 10;
		gc.insets.left=5;
		gc.insets.right=5;
		gc.gridx = 0;
		gc.gridy = 0;
		menuPanel.add(generalButton, gc);
		gc.gridy++;
		menuPanel.add(stashButton, gc);
		gc.gridy++;
		menuPanel.add(incomingButton, gc);
		gc.gridy++;
		menuPanel.add(outgoingButton, gc);
		gc.gridy++;
//		menuPanel.add(audioButton, gc);
//		gc.gridy++;
//		menuPanel.add(advancedButton, gc);
//		gc.gridy++;
		menuPanel.add(contactButton, gc);
		
		
		
		
//		container.setLayout(new BorderLayout());
		container.add(new BufferPanel(0,buffer), BorderLayout.NORTH);
		container.add(new BufferPanel(buffer,0), BorderLayout.EAST);
		container.add(bottomPanel, BorderLayout.SOUTH);
		container.add(menuBorder, BorderLayout.WEST);
		container.add(scrollDisplay, BorderLayout.CENTER);
		
		
		generalPanel.setVisible(true);
		generalButton.active = true;
		this.setMinimumSize(new Dimension(900,600));
		this.refresh();
		this.setMinimumSize(new Dimension(300,300));
		this.setMaximumSize(new Dimension(1600,900));
//		this.setVisible(true);
		FrameManager.centerFrame(this);
		
		//TODO : Resize
		ResizableWindow local = this;
//		resizeButton.addActionListener(new ActionListener(){
//			public void actionPerformed(ActionEvent e) {
//				local.autoResize();
//				local.pack();
//
//			}
//		});
		
		revertButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generalPanel.load();
				incomingPanel.load();
//				audioPanel.load();
				stashPanel.load();
			}
		});
		
		saveButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				generalPanel.save();
				incomingPanel.save();
//				audioPanel.save();
				stashPanel.save();
				Main.saveManager.saveToDisk();
			}
		});
		
	}
	
	//TODO : Make on press down?
	//switch type to list button for less casting
	private void link(JButton b, JPanel p){
		OptionsWindow local = this;
		b.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e) {
				ListButton lb;
				for(Component c : menuPanel.getComponents()){
					lb = (ListButton)c;
					lb.active = false;
				}
				lb = (ListButton)b;
				lb.active = true;
				hideAllWindows();
				p.setVisible(true);
//				lb.repaint();
				local.repaint();
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
