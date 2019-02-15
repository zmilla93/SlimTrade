package main.java.com.slimtrade.gui.options;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;
import main.java.com.slimtrade.gui.options.customizer.IncomingCustomizer;
import main.java.com.slimtrade.gui.options.customizer.OutgoingCustomizer;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class OptionsWindow_OLD extends AbstractWindowDialog {

	private static final long serialVersionUID = 1L;
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public final static int contentWidth = (int) (WIDTH * 0.95);
	public static final int ROW_HEIGHT = 25;
	private int bufferHeight = 10;

	// private GridBagConstraints gc = new GridBagConstraints();
	// private OptionContentPanel basicsPanel = new
	// OptionContentPanel(contentWidth, 600);
	// private ContentPanel buttonPanel = new ContentPanel(contentWidth, 600);

	private JPanel optionsContainer = new JPanel();
	private JButton resetButton = new JButton("Reset to Default");
	private JButton cancelButton = new JButton("Cancel");
	private JButton saveButton = new JButton("Save");

	// TODO : cleanup size variables for better resizing

	public OptionsWindow_OLD() {
		super("Options");
//		this.resizeWindow(WIDTH, HEIGHT);
		this.setFocusableWindowState(true);

		// JPanel optionsContainer = new JPanel();
		JScrollPane scrollPane = new JScrollPane(optionsContainer);

		container.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;

		// TODO : Switch to gridbaglayout
		optionsContainer.setLayout(new BoxLayout(optionsContainer, BoxLayout.PAGE_AXIS));

		// TEMP SIZE
		scrollPane.setPreferredSize(new Dimension(WIDTH, (int) (HEIGHT * 0.9)));
		// scrollPane.setPreferredSize(new Dimension(width, height));
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.getVerticalScrollBar().setUnitIncrement(8);

		this.addOptionBuffer();

		// BASICS
		OptionTitlePanel basicsTitle = new OptionTitlePanel("Basics");
		BasicsPanel basicsPanel = new BasicsPanel();
		optionsContainer.add(basicsTitle);
		optionsContainer.add(basicsPanel);
		linkToggle(basicsTitle, basicsPanel);
		this.addOptionBuffer();

		// MACRO BUTTONS

		OptionTitlePanel macroTitle = new OptionTitlePanel("Button Macros");
		MacroPanel macroPanel = new MacroPanel(contentWidth, 600);
		linkToggle(macroTitle, macroPanel);
		optionsContainer.add(macroTitle);
		optionsContainer.add(macroPanel);
		this.addOptionBuffer();
		
		//Message Customizer
		OptionTitlePanel customizerTitle = new OptionTitlePanel("Message Customizer");
//		CustomizerPanel customizerPanel = new CustomizerPanel();
		IncomingCustomizer customizerPanel = new IncomingCustomizer(this);
		linkToggle(customizerTitle, customizerPanel);
		optionsContainer.add(customizerTitle);
		optionsContainer.add(customizerPanel);
		this.addOptionBuffer();
		
		OptionTitlePanel customizerOutTitle = new OptionTitlePanel("Message Customizer");
//		CustomizerPanel customizerPanel = new CustomizerPanel();
		OutgoingCustomizer customizerOutPanel = new OutgoingCustomizer();
		linkToggle(customizerOutTitle, customizerOutPanel);
		optionsContainer.add(customizerOutTitle);
		optionsContainer.add(customizerOutPanel);
		this.addOptionBuffer();

		// Audio Panel
		OptionTitlePanel audioTitle = new OptionTitlePanel("Audio");
		AudioPanel audioPanel = new AudioPanel();
		linkToggle(audioTitle, audioPanel);
		optionsContainer.add(audioTitle);
		optionsContainer.add(audioPanel);
		this.addOptionBuffer();

		// Advanced Panel
		OptionTitlePanel advancedTitle = new OptionTitlePanel("Advanced");
		AdvancedPanel advancedPanel = new AdvancedPanel();
		linkToggle(advancedTitle, advancedPanel);
		optionsContainer.add(advancedTitle);
		optionsContainer.add(advancedPanel);
		this.addOptionBuffer();
		
		OptionTitlePanel contactTitle = new OptionTitlePanel("Contact");
		ContactPanel contactPanel = new ContactPanel();
		linkToggle(contactTitle, contactPanel);
		optionsContainer.add(contactTitle);
		optionsContainer.add(contactPanel);
		this.addOptionBuffer();

		// Ending Button Panel
		JPanel endPanel = new JPanel();
		endPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		endPanel.setOpaque(false);
		endPanel.add(resetButton);
		endPanel.add(new BufferPanel(30, 0));
		endPanel.add(saveButton);

		// optionsConta

		// optionsContainer.add(new ButtonOptionPanel());

		// Container Stuff
		container.add(scrollPane, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 10), gc);
		gc.gridy++;
		container.add(endPanel, gc);

		// TEMP RESIZING
		// TODO : Cleanup
//		Dimension cur = this.getSize();
//		this.setPreferredSize(null);
//		Dimension pref = this.getPreferredSize();
//		this.setSize(cur.width, pref.height);
//		this.setPreferredSize(null);
//		System.out.println(this.getPreferredSize());
		this.resizeWindow(WIDTH, HEIGHT);

		macroPanel.resetAll();

		FrameManager.centerFrame(this);

		saveButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				macroPanel.saveAll();
				// Main.saveManager.saveToDisk();
			}
		});

		resetButton.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				macroPanel.resetAll();
			}
		});

		// TODO : Temp
//		macroPanel.setVisible(true);
//		audioPanel.setVisible(true);
		customizerPanel.setVisible(true);

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

	private void addOptionBuffer() {
		optionsContainer.add(new BufferPanel(0, bufferHeight));
	}

	private void addButtonBuffer() {

	}

	public void refresh() {
		this.revalidate();
		this.repaint();
	}

}
