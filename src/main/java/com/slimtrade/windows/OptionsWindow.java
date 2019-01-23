package main.java.com.slimtrade.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.dialog.BasicWindowDialog;
import main.java.com.slimtrade.panels.BufferPanel;
import main.java.com.slimtrade.panels.options.ButtonOptionPanel;
import main.java.com.slimtrade.panels.options.OptionContentPanel;
import main.java.com.slimtrade.panels.options.OptionTitlePanel;

public class OptionsWindow extends BasicWindowDialog{
	
	private static final long serialVersionUID = 1L;
	public static int width = 800;
	public static int contentWidth = (int)(width*0.95);
	public static int height = 800;
	public static int rowHeight = 25;
	private int bufferHeight = 10;
	
	private JPanel optionsContainer = new JPanel();
	//TODO : cleanup size variables for better resizing
	
	public OptionsWindow(){
		super("Options");
		this.resizeWindow(width, height);
		
//		JPanel optionsContainer = new JPanel();
		JScrollPane scrollPane = new JScrollPane(optionsContainer);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.PAGE_AXIS));
		optionsContainer.setMaximumSize(new Dimension(contentWidth, height));
		
		scrollPane.setPreferredSize(new Dimension(width, height));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);
		
		//Toggle Panels
		
		//Basic
		this.addBuffer();
		
		//Buttons
		OptionTitlePanel buttonTitle = new OptionTitlePanel("Buttons");
		OptionContentPanel buttonPanel = new OptionContentPanel(contentWidth, 600);
		buttonPanel.add(new ButtonOptionPanel());
		linkToggle(buttonTitle, buttonPanel);
		buttonPanel.revalidate();
		buttonPanel.repaint();
		optionsContainer.add(buttonTitle);
		optionsContainer.add(buttonPanel);
		
		this.addBuffer();
		
		//Sweet
		OptionTitlePanel sweetTitle = new OptionTitlePanel("Test Title");
		OptionContentPanel sweetPanel = new OptionContentPanel(contentWidth, 900);
		sweetPanel.setBackground(Color.green);
		linkToggle(sweetTitle, sweetPanel);
		optionsContainer.add(sweetTitle);
		optionsContainer.add(sweetPanel);
		
		this.addBuffer();
		
//		optionsContainer.add(new ButtonOptionPanel());

		//Container Stuff
		container.add(scrollPane);

		FrameManager.centerFrame(this);
		this.setVisible(true);
	}
	
	private void linkToggle(JPanel title, JPanel content){
		title.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				if(content.isVisible()){
					content.setVisible(false);
				}else{
					content.setVisible(true);
				}
			}
		});
	}
	
	private void addBuffer(){
		optionsContainer.add(new BufferPanel(contentWidth, bufferHeight));
	}
	
	
	public void refresh(){
		this.revalidate();
		this.repaint();
	}
	
}
