package com.slimtrade.gui.scanner.old;

import com.slimtrade.App;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.panels.IconPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class ChatScannerWindow_OLD extends AbstractResizableWindow implements ISaveable {

	private static final long serialVersionUID = 1L;

	int maxSaves = 50;

	private JTextField nameInput = new JTextField(10);
	private JTextArea termsText = new JTextArea();
	private JTextArea ignoreText = new JTextArea();
	// private JTextArea activeTermsText = new JTextArea();
	// private JTextArea ActiveIgnoreText = new JTextArea();
	private JTextField lmbInput = new JTextField(10);
	private JTextField rmbInput = new JTextField(10);

	private ArrayList<ScannerMessage_OLD> messages = new ArrayList<>();
	private JComboBox<ScannerMessage_OLD> messageCombo = new JComboBox<>();

	private int bufferX = 20;
	private int bufferY = 10;

	private boolean searching = false;

	JButton clearButton = new JButton("Clear");
	JButton saveButton = new JButton("Save");
	JButton searchButton = new JButton("Search");
	JButton deleteButton = new JButton("Delete");

	public ChatScannerWindow_OLD() {
		super("Chat Scanner");
        this.setAlwaysOnTop(false);
		this.setFocusableWindowState(true);
		this.setFocusable(true);

		// TODO : Switch to nested panels
		ContainerPanel borderPanel = new ContainerPanel();
		borderPanel.container.setLayout(new GridBagLayout());
		borderPanel.setBorder(null);

		ContainerPanel criteriaPanel = new ContainerPanel();
		JPanel topPanel = criteriaPanel.container;
		topPanel.setLayout(new GridBagLayout());

		ContainerPanel savePanel = new ContainerPanel();
		JPanel bottomPanel = savePanel.container;
		bottomPanel.setLayout(new GridBagLayout());

		JLabel nameLabel = new JLabel("Save Name");
		JLabel termsLabel = new JLabel("Search Terms");
		JLabel ignoreLabel = new JLabel("Ignore Terms");

		JLabel responseLabel = new JLabel("Response Button");
		JLabel lmbLabel = new JLabel("Left Mouse");
		JLabel rmbLabel = new JLabel("Right Mouse");

		JPanel responsePanel = new JPanel(new BorderLayout());
		IconPanel responseButton = new IconPanel(PreloadedImage.REPLY.getImage(), 20);
		responsePanel.add(responseLabel, BorderLayout.CENTER);
		responsePanel.add(responseButton, BorderLayout.EAST);
		
		JScrollPane termsScroll = new JScrollPane(termsText);
		JScrollPane ignoreScroll = new JScrollPane(ignoreText);

		messageCombo.setFocusable(false);

		clearButton.setFocusable(false);
		saveButton.setFocusable(false);
		searchButton.setFocusable(false);
		deleteButton.setFocusable(false);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
		buttonPanel.add(clearButton);
		buttonPanel.add(saveButton);

		JPanel searchPanel = new JPanel();
		searchPanel.add(searchButton);
		searchPanel.add(messageCombo);

		// container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 0;
		gc.weighty = 0;

		topPanel.add(nameLabel, gc);
		gc.gridx = 1;
		topPanel.add(new BufferPanel(bufferX, 0), gc);
		gc.gridx = 2;
		topPanel.add(nameInput, gc);
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
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		// gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.NORTH;
		topPanel.add(ignoreLabel, gc);
		gc.anchor = GridBagConstraints.CENTER;
		gc.gridx = 2;
		gc.weightx = 5;
		gc.weighty = 3;
		gc.fill = GridBagConstraints.BOTH;
		topPanel.add(ignoreScroll, gc);
		gc.fill = GridBagConstraints.HORIZONTAL;
		// gc.fill = GridBagConstraints.NONE;
		gc.weightx = 0;
		gc.weighty = 0;
		gc.gridx = 0;
		gc.gridy++;

		// gc.gridwidth = 3;
		topPanel.add(responsePanel, gc);
		// gc.gridwidth = 1;
		gc.gridy++;

		topPanel.add(lmbLabel, gc);
		gc.gridx = 2;
		topPanel.add(lmbInput, gc);
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY / 2), gc);
		gc.gridy++;

		topPanel.add(rmbLabel, gc);
		gc.gridx = 2;
		topPanel.add(rmbInput, gc);
		gc.gridx = 0;
		gc.gridy++;

		topPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		gc.gridwidth = 3;
		topPanel.add(buttonPanel, gc);
		gc.gridy++;

		// Bottom Panel
		gc = new GridBagConstraints();
		gc.weightx = 2;
		gc.gridx = 0;
		gc.gridy = 0;

		gc.insets.left = 20;
		gc.insets.right = 20;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridwidth = 3;
		bottomPanel.add(messageCombo, gc);
		gc.gridwidth = 1;
		gc.fill = GridBagConstraints.NONE;
		// gc.insets.left = 0;
		// gc.insets.right = 0;
		gc.gridx = 0;
		gc.gridy++;

		bottomPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		gc.fill = GridBagConstraints.HORIZONTAL;
		bottomPanel.add(deleteButton, gc);
		gc.gridx = 1;
		gc.weightx = 1;
		bottomPanel.add(new BufferPanel(10, 0), gc);
		gc.gridx = 2;
		gc.weightx = 4;
		bottomPanel.add(searchButton, gc);
		gc.fill = GridBagConstraints.NONE;
		gc.gridx = 0;
		gc.gridy++;

		bottomPanel.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;

		// bottomPanel.add(new BufferPanel(0, bufferY), gc);
		// gc.gridy++;

		topPanel.setBackground(Color.green);
		bottomPanel.setBackground(Color.ORANGE);

		// Container
		// gc.insets.top = 40;
		gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		gc.weightx = 1;
		gc.weighty = 4;
		gc.fill = GridBagConstraints.BOTH;
		gc.insets.bottom = 20;
		borderPanel.container.add(criteriaPanel, gc);
		gc.insets.bottom = 0;
		gc.gridy++;

		gc.weighty = 1;
		borderPanel.container.add(savePanel, gc);
		gc.gridy++;

		gc = new GridBagConstraints();
		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.BOTH;
		container.setLayout(new GridBagLayout());
		container.add(borderPanel, gc);

		clearButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearWindow();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});

		JDialog local = this;
		messageCombo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				updateText((ScannerMessage_OLD) messageCombo.getSelectedItem());
				local.revalidate();
				local.repaint();
			}
		});

		deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
				if (messageCombo.getItemCount() == 0) {
					clearWindow();
				}
			}
		});

		searchButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				searching = !searching;
				if (messageCombo.getItemCount() == 0) {
					searching = false;
				}
				if (searching) {
					ScannerMessage_OLD msg = (ScannerMessage_OLD) messageCombo.getSelectedItem();
					App.chatParser.setSearchName(msg.name);
					App.chatParser.setSearchTerms(msg.searchTermsArray);
					App.chatParser.setSearchIgnoreTerms(msg.ignoreTermsArray);
//					App.chatParser.setResponseText(msg.clickLeft, msg.clickRight);
					System.out.println(Arrays.toString(msg.searchTermsArray));
				}
				updateSearchButton();
				App.chatParser.setChatScannerRunning(searching);
			}
		});

		load();

		updateCombo(null);
		updateColor();

		this.setMinimumSize(this.getPreferredSize());
		this.setPreferredSize(new Dimension(400, 450));
		this.pack();
		// System.out.println(this.getSize());
		FrameManager.centerFrame(this);

	}

	private void clearWindow() {
		nameInput.setText("");
		termsText.setText("");
		ignoreText.setText("");
		lmbInput.setText("");
		rmbInput.setText("");
	}

	private void updateCombo(ScannerMessage_OLD msg) {
		messageCombo.removeAllItems();
		for (ScannerMessage_OLD m : messages) {
			messageCombo.addItem(m);
		}
		if (msg != null) {
			for(int i = 0; i<messageCombo.getItemCount();i++){
				if(messageCombo.getItemAt(i).name.equals(msg.name)){
					messageCombo.setSelectedIndex(i);
				}
			}
//			messageCombo.setSelectedItem(msg);
		}
	}

	private void updateText(ScannerMessage_OLD msg) {
		// for (ScannerMessage msg : messages) {
		// if (msg.name.equals(name)) {
		if (msg == null) {
			return;
		}
		nameInput.setText(msg.name);
		termsText.setText(msg.searchTermsRaw);
		ignoreText.setText(msg.ignoreTermsRaw);
		lmbInput.setText(msg.clickLeft);
		rmbInput.setText(msg.clickRight);
		// }
		// }
	}

	private void updateSearchButton() {
		if (searching) {
			searchButton.setText("Searching \"" + messageCombo.getSelectedItem() + "\"");
		} else {
			searchButton.setText("Search");
		}
	}

	public void save() {
		String name = nameInput.getText();
		String terms = termsText.getText();
		String ignore = ignoreText.getText();
		String lmb = lmbInput.getText();
		String rmb = rmbInput.getText();
		if (name.replaceAll("\\s+", "").equals("") || terms.replaceAll("\\s+", "").equals("")) {
			return;
		}
		int i = 0;
		boolean saved = false;
		for (ScannerMessage_OLD msg : messages) {
			if (msg.name != null && msg.name.equals(name)) {
				messages.set(i, new ScannerMessage_OLD(name, terms, ignore));
				saved = true;
				// messages.add();
			}
			i++;
		}
		ScannerMessage_OLD msg = new ScannerMessage_OLD(name, terms, ignore);
		if (!saved) {
			messages.add(msg);
		}
		localSaveToDisk();
		updateCombo(msg);

	}

	private void delete() {
		// TODO : Remove this check once enable/disable is added?
		if (messageCombo.getItemCount() > 0) {
			messages.remove(messageCombo.getSelectedIndex());
			messageCombo.removeItem(messageCombo.getSelectedItem());
			localSaveToDisk();
		}
	}

	public void load() {
	    for(ScannerMessage_OLD scan : App.saveManager.saveFile.scannerMessages) {
            messages.add(scan);
        }
	}

	private void localSaveToDisk() {
		Collections.sort(messages, Comparator.comparing(ScannerMessage_OLD::getName));
        App.saveManager.saveFile.scannerMessages.clear();
		for (ScannerMessage_OLD msg : messages) {
			App.saveManager.saveFile.scannerMessages.add(msg);
		}
		App.saveManager.saveToDisk();
	}

}
