package main.java.com.slimtrade.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.stash.ResizableWindow;

public class ImprovedChatScanner extends ResizableWindow implements Saveable {

	private static final long serialVersionUID = 1L;

	int maxSaves = 50;

	JTextField nameInput = new JTextField(10);
	JTextArea termsText = new JTextArea();
	JTextArea ignoreText = new JTextArea();
	JTextArea activeTermsText = new JTextArea();
	JTextArea ActiveIgnoreText = new JTextArea();
	JTextField lmbInput = new JTextField(10);
	JTextField rmbInputInput = new JTextField(10);

	ArrayList<ScannerMessage> messages = new ArrayList<ScannerMessage>();

	// JComboBox<String> saveCombo = new JComboBox<String>();
	private JComboBox<String> nameCombo = new JComboBox<String>();

	private int bufferX = 20;
	private int bufferY = 10;

	public ImprovedChatScanner() {
		super("Chat Scanner - MAIN");
		this.setFocusableWindowState(true);

		// TODO : Switch to nested panels
		JPanel topPanel = new JPanel(new GridBagLayout());
		JPanel bottomPanel = new JPanel(new GridBagLayout());

		JLabel nameLabel = new JLabel("Save Name");
		JLabel termsLabel = new JLabel("Search Terms");
		JLabel ignoreLabel = new JLabel("Ignore Terms");

		JLabel responseLabel = new JLabel("Quick Response");
		JLabel lmbLabel = new JLabel("Left Mouse");
		JLabel rmbLabel = new JLabel("Right Mouse");

		JScrollPane termsScroll = new JScrollPane(termsText);
		JScrollPane ignoreScroll = new JScrollPane(ignoreText);

		JButton clearButton = new JButton("Clear");
		JButton saveButton = new JButton("Save");
		JButton searchButton = new JButton("Search");
		JButton deleteButton = new JButton("Delete");

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
		buttonPanel.add(clearButton);
		buttonPanel.add(saveButton);

		JPanel searchPanel = new JPanel();
		searchPanel.add(searchButton);
		searchPanel.add(nameCombo);

		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0;
		topPanel.add(nameLabel, gc);
		gc.gridx = 1;
		topPanel.add(new BufferPanel(bufferX, 0), gc);
		gc.gridx = 2;
		gc.fill = GridBagConstraints.HORIZONTAL;
		topPanel.add(nameInput, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		gc.anchor = GridBagConstraints.NORTH;
		topPanel.add(termsLabel, gc);
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 2;
		gc.weightx = 6;
		gc.weighty = 5;
		gc.fill = GridBagConstraints.BOTH;
		topPanel.add(termsScroll, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		gc.anchor = GridBagConstraints.NORTH;
		topPanel.add(ignoreLabel, gc);
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 2;
		gc.weightx = 5;
		gc.weighty = 3;
		gc.fill = GridBagConstraints.BOTH;
		topPanel.add(ignoreScroll, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		gc.gridwidth = 3;
		topPanel.add(buttonPanel, gc);
		gc.gridy++;

//		container.add(new BufferPanel(0, bufferY * 2), gc);
//		gc.gridy++;

		//Bottom Panel
		gc = new GridBagConstraints();
		gc.weightx = 2;
		gc.gridx = 0;
		gc.gridy = 0;
		
		gc.insets.left = 20;
		gc.insets.right = 20;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 3;
		bottomPanel.add(nameCombo, gc);
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;
//		gc.insets.left = 0;
//		gc.insets.right = 0;
		gc.gridx = 0;
		gc.gridy++;
		
		bottomPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;
		
		gc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(deleteButton, gc);
		gc.gridx=1;
		gc.weightx = 1;
		bottomPanel.add(new BufferPanel(10,0), gc);
		gc.gridx=2;
		gc.weightx = 4;
		bottomPanel.add(searchButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy++;
		
		bottomPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		

//		bottomPanel.add(new BufferPanel(0, bufferY), gc);
//		gc.gridy++;

		
		topPanel.setBackground(Color.green);
		bottomPanel.setBackground(Color.ORANGE);
		
		//Container
//		gc.insets.top = 40;
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 4;
		gc.fill = GridBagConstraints.BOTH;
		container.add(topPanel, gc);
		
		gc.gridy++;
		gc.weighty = 1;
//		gc.fill = GridBagConstraints.HORIZONTAL;
		container.add(bottomPanel, gc);
		gc.gridy++;

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nameInput.setText("");
				termsText.setText("");
				ignoreText.setText("");
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		nameCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText((String) nameCombo.getSelectedItem());
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});

		load();

		updateCombo(null);

		this.setMinimumSize(this.getPreferredSize());
		this.setPreferredSize(new Dimension(400, 400));
		this.pack();

	}

	private void updateCombo(String selected) {
		nameCombo.removeAllItems();
		System.err.println("Updating Combo");
//		for(String s : nameCombo.list)
//		int count = nameCombo.getItemCount();
//		boolean newItem = true;
//		for(int i = 0;i<count;i++){
//			if(nameCombo.getItemAt(i).equals(nameInput)){
//				newItem = false;
//			}
//		}
////		if(newItem)
		for (ScannerMessage msg : messages) {
			nameCombo.addItem(msg.name);
		}
		if (selected != null) {
			nameCombo.setSelectedItem(selected);
		}

	}

	private void updateText(String name) {
		for (ScannerMessage msg : messages) {
			if (msg.name.equals(name)) {
				nameInput.setText(msg.name);
				termsText.setText(msg.searchTerms);
				ignoreText.setText(msg.ignoreTerms);
			}
		}

	}

	public void save() {
		String name = nameInput.getText();
		String terms = termsText.getText();
		String ignore = ignoreText.getText();
		if (name.replaceAll("\\s", "").equals("")) {
			return;
		}
		int i = 0;
		boolean saved = false;
		for (ScannerMessage msg : messages) {
			if (msg.name != null && msg.name.equals(name)) {
				messages.set(i, new ScannerMessage(name, terms, ignore));
				saved = true;
				// messages.add();
			}
			i++;
		}
		if (!saved) {
			messages.add(new ScannerMessage(name, terms, ignore));
		}

		localSaveToDisk();
		updateCombo(name);

	}

	private void delete() {
		//TODO : Remove this check once enable/disable is added?
		if (nameCombo.getItemCount() > 0) {
			messages.remove(nameCombo.getSelectedIndex());
			nameCombo.removeItem(nameCombo.getSelectedItem());
			localSaveToDisk();
		}
	}

	public void load() {
		for (int i = 0; i < maxSaves; i++) {
			if (Main.saveManager.hasEntry("chatScanner", "search" + i)) {
				String name = Main.saveManager.getString("chatScanner", "search" + i, "name");
				String terms = Main.saveManager.getString("chatScanner", "search" + i, "terms");
				String ignore = Main.saveManager.getString("chatScanner", "search" + i, "ignore");
				messages.add(new ScannerMessage(name, terms, ignore));
			}
		}
	}

	private void localSaveToDisk() {
		Main.saveManager.deleteArray("chatScanner");
		//TODO : Double check this sorting code
		Collections.sort(messages, new Comparator<ScannerMessage>() {
			public int compare(final ScannerMessage obj1, final ScannerMessage obj2) {
				return obj1.getName().compareTo(obj2.getName());
			}
		});
		int i = 0;
		for (ScannerMessage msg : messages) {
			Main.logger.log(Level.INFO, "Writing to index [" + i + "]\n");
			Main.saveManager.putString(msg.name, "chatScanner", "search" + i, "name");
			Main.saveManager.putString(msg.searchTerms, "chatScanner", "search" + i, "terms");
			Main.saveManager.putString(msg.ignoreTerms, "chatScanner", "search" + i, "ignore");
			i++;
		}
		Main.saveManager.saveToDisk();
	}

}
