package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicWindowDialog;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class OptionsWindow extends BasicWindowDialog{
	
	private static final long serialVersionUID = 1L;
	public static int width = 800;
	public static int contentWidth = (int)(width*0.95);
	public static int height = 800;
	public static int rowHeight = 25;
	private int bufferHeight = 10;
	
	private GridBagConstraints gc = new GridBagConstraints();
	private OptionContentPanel buttonPanel = new OptionContentPanel(contentWidth, 600);
	
	private JPanel optionsContainer = new JPanel();
	//TODO : cleanup size variables for better resizing
	
	public OptionsWindow(){
		super("Options");
		this.resizeWindow(width, height);
		
//		JPanel optionsContainer = new JPanel();
		JScrollPane scrollPane = new JScrollPane(optionsContainer);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		//Switch to gridbaglayout
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.PAGE_AXIS));
		optionsContainer.setMaximumSize(new Dimension(contentWidth, height));
		
		scrollPane.setPreferredSize(new Dimension(width, height));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);
		
		this.addContainerBuffer();
		
		//Buttons
		OptionTitlePanel buttonTitle = new OptionTitlePanel("Buttons");
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		//TODO : Callback button
		buttonPanel.add(new ButtonOptionPanel("Wait Button", "/resources/icons/clock1.png", ""), gc);
		this.addButtonBuffer();
		//TODO : Refresh Button
		buttonPanel.add(new ButtonOptionPanel("Close Button", "/resources/icons/close.png", ""), gc);
		this.addButtonBuffer();
		//TODO : Warp Button
		buttonPanel.add(new ButtonOptionPanel("Invite Button", "/resources/icons/invite.png", ""), gc);
		this.addButtonBuffer();
		buttonPanel.add(new ButtonOptionPanel("Trade Button", "/resources/icons/cart.png", ""), gc);
		this.addButtonBuffer();
		buttonPanel.add(new ButtonOptionPanel("Thank Button", "/resources/icons/thumb1.png", ""), gc);
		this.addButtonBuffer();
		//TODO : Leave/Kick Button
		//TODO : Home Button
		linkToggle(buttonTitle, buttonPanel);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		optionsContainer.add(buttonTitle);
		optionsContainer.add(buttonPanel);
		
		this.addContainerBuffer();
		
		//Sweet
		OptionTitlePanel sweetTitle = new OptionTitlePanel("Test Title");
		OptionContentPanel sweetPanel = new OptionContentPanel(contentWidth, 900);
		sweetPanel.setBackground(Color.green);
		linkToggle(sweetTitle, sweetPanel);
		optionsContainer.add(sweetTitle);
		optionsContainer.add(sweetPanel);
		
//		optionsContainer.add(new ButtonOptionPanel());

		//Container Stuff
		container.add(scrollPane);

		FrameManager.centerFrame(this);
//		this.setVisible(true);
	}
	
	private void linkToggle(JPanel title, JPanel content){
		title.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if(content.isVisible()){
					content.setVisible(false);
				}else{
					content.setVisible(true);
				}
			}
		});
	}
	
	private void addContainerBuffer(){
		optionsContainer.add(new BufferPanel(0, bufferHeight));
	}
	
	private void addButtonBuffer(){
		gc.gridy++;
		buttonPanel.add(new BufferPanel(0, bufferHeight), gc);
		gc.gridy++;
	}
	
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
