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

public class OptionsWindow extends BasicWindowDialog {

	private static final long serialVersionUID = 1L;
	public static int width = 800;
	public final static int contentWidth = (int) (width * 0.95);
	public static int height = 800;
	public static int rowHeight = 25;
	private int bufferHeight = 10;

	private GridBagConstraints gc = new GridBagConstraints();
//	private OptionContentPanel basicsPanel = new OptionContentPanel(contentWidth, 600);
//	private ContentPanel buttonPanel = new ContentPanel(contentWidth, 600);

	private JPanel optionsContainer = new JPanel();
	// TODO : cleanup size variables for better resizing

	public OptionsWindow() {
		super("Options");
		this.resizeWindow(width, height);

		// JPanel optionsContainer = new JPanel();
		JScrollPane scrollPane = new JScrollPane(optionsContainer);
		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));

		// Switch to gridbaglayout
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.PAGE_AXIS));
		optionsContainer.setMaximumSize(new Dimension(contentWidth, height));

		scrollPane.setPreferredSize(new Dimension(width, height));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);

		this.addContainerBuffer();
		
		//BASICS
		OptionTitlePanel basicsTitle = new OptionTitlePanel("Basics");
		BasicsPanel basicsPanel = new BasicsPanel(contentWidth, height);
		
		optionsContainer.add(basicsTitle);
		optionsContainer.add(basicsPanel);
		linkToggle(basicsTitle, basicsPanel);
		this.addContainerBuffer();
		
		// MACRO BUTTONS
		
		OptionTitlePanel macroTitle = new OptionTitlePanel("Button Macros");
		MacroPanel macroPanel = new MacroPanel(contentWidth, 600);
		linkToggle(macroTitle, macroPanel);
		
		gc.gridx = 0;
		gc.gridy = 0;
//		gc.weightx = 1;
		// TODO : Callback button
//		buttonPanel.add(new MacroOptionPanel("Wait Button", "/resources/icons/clock1.png", ""), gc);
//		this.addButtonBuffer();
//		// TODO : Refresh Button
//		buttonPanel.add(new MacroOptionPanel("Close Button", "/resources/icons/close.png", ""), gc);
//		this.addButtonBuffer();
//		// TODO : Warp Button
//		buttonPanel.add(new MacroOptionPanel("Invite Button", "/resources/icons/invite.png", ""), gc);
//		this.addButtonBuffer();
//		buttonPanel.add(new MacroOptionPanel("Trade Button", "/resources/icons/cart.png", ""), gc);
//		this.addButtonBuffer();
//		buttonPanel.add(new MacroOptionPanel("Thank Button", "/resources/icons/thumb1.png", ""), gc);
//		this.addButtonBuffer();
//		
//		linkToggle(buttonTitle, buttonPanel);
//		
//		buttonPanel.revalidate();
//		buttonPanel.repaint();
		optionsContainer.add(macroTitle);
		optionsContainer.add(macroPanel);

		this.addContainerBuffer();
//		buttonPanel.autoResize();
		
		// Sweet
		OptionTitlePanel sweetTitle = new OptionTitlePanel("Test Title");
		ContentPanel sweetPanel = new ContentPanel(contentWidth, 900);
		sweetPanel.setBackground(Color.green);
		linkToggle(sweetTitle, sweetPanel);
		optionsContainer.add(sweetTitle);
		optionsContainer.add(sweetPanel);

		// optionsContainer.add(new ButtonOptionPanel());

		// Container Stuff
		container.add(scrollPane);

		FrameManager.centerFrame(this);
		this.setVisible(true);
	}

	private void linkToggle(JPanel title, JPanel content) {
		title.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent evt) {
				if (content.isVisible()) {
					content.setVisible(false);
				} else {
					content.setVisible(true);
				}
			}
		});
	}

	private void addContainerBuffer() {
		optionsContainer.add(new BufferPanel(0, bufferHeight));
	}

	private void addButtonBuffer() {
		gc.gridy++;
//		buttonPanel.add(new BufferPanel(0, bufferHeight), gc);
		gc.gridy++;
	}

	public void refresh() {
		this.revalidate();
		this.repaint();
	}

}
