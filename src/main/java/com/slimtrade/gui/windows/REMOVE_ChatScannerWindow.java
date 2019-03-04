package main.java.com.slimtrade.gui.windows;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.AbstractWindowDialog;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class REMOVE_ChatScannerWindow extends AbstractWindowDialog {

	private static final long serialVersionUID = 1L;
	private String savedText = "";
	
	JTextArea searchArea;
	JScrollPane searchScrollPane;
	
	
	private Border unsavedBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED);
	private Border defaultBorder = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);

	private JButton searchButton;
	
	private int bufferWidth = 40;
	private int bufferHeight = 20;
	private int rowBuffer = 10;
	private boolean running = false;
	
	public REMOVE_ChatScannerWindow(){
		super("Chat Scanner");
		this.setFocusableWindowState(true);
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		JPanel helpPanelUpper = new JPanel(new GridBagLayout());
		ArrayList<JLabel> helpLabelUpper = new ArrayList<JLabel>();
		helpLabelUpper.add(new JLabel("Enter words or phrases you would like to scan for."));
		helpLabelUpper.add(new JLabel("Seperate phrases with a comma, semicolon, or line break."));
		helpLabelUpper.add(new JLabel("Search is not case sensitive."));
		helpLabelUpper.add(new JLabel("A red outline indicates unsaved changes."));
		
		searchArea = new JTextArea(10, 30);
		searchScrollPane = new JScrollPane(searchArea);
		
		JPanel helpPanelLower = new JPanel(new GridBagLayout());
		ArrayList<JLabel> helpLabelLower = new ArrayList<JLabel>();
		helpLabelLower.add(new JLabel("Right clicking \"Chat Scanner\" in the menubar toggles searching."));
		helpLabelLower.add(new JLabel("A green button indicates the search is running."));
		
		searchArea.setLineWrap(true);
		searchArea.setWrapStyleWord(true);
		
//		System.out.println(searchScrollPane.getBorder());
		searchScrollPane.setBorder(defaultBorder);
//		searchScrollPane.setBorder(BorderFactory.createEmptyBorder());
		
		JPanel buttonPanel = new JPanel();
		JButton clearButton = new JButton("Clear");
		JButton saveButton = new JButton("Save");
		searchButton = new JButton("Search");
		
		clearButton.setFocusable(false);
		saveButton.setFocusable(false);
		searchButton.setFocusable(false);
		
		gc.gridx=0;
		gc.gridy=0;
		buttonPanel.add(clearButton, gc);
		gc.gridx++;
		buttonPanel.add(saveButton, gc);
		gc.gridx++;
		buttonPanel.add(searchButton, gc);
		
		gc.gridx=0;
		gc.gridy=0;
		for(JLabel l : helpLabelUpper){
			helpPanelUpper.add(l, gc);
			gc.gridy++;
		}
		gc.gridy=0;
		for(JLabel l : helpLabelLower){
			helpPanelLower.add(l, gc);
			gc.gridy++;
		}
		
		//Build Dialog
		gc.gridx=0;
		gc.gridy=0;
		container.add(helpPanelUpper, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, rowBuffer), gc);
		gc.gridy++;
		container.add(searchScrollPane, gc);
		gc.gridy++;
		container.add(buttonPanel, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, rowBuffer), gc);
		gc.gridy++;
		container.add(helpPanelLower, gc);
		
		clearButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				searchArea.setText("");
				validateSearchBorder();
			}
		});
		
		saveButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				savedText = searchArea.getText();
				//TODO Remove empty strings
				String[] terms = savedText.split("\n|,|;");
				
				for(int i = 0;i<terms.length;i++){
					String s = terms[i].trim();
					terms[i] = s.replaceAll("\\s+", " ");
				}
				//save terms to file here
				for(int i = 0;i<terms.length;i++){
					terms[i] = terms[i].toLowerCase();
				}
				for(String s : terms){
					System.out.println("TERM : " + s + ";");
				}
				Main.chatParser.setSearchTerms(terms);
				validateSearchBorder();
			}
		});
		
		searchButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				running = !running;
				Main.chatParser.setChatScannerRunning(running);
				updateSearchButton();
			}
		});
		
		searchArea.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e) {
			}

			public void insertUpdate(DocumentEvent e) {
				validateSearchBorder();
			}

			public void removeUpdate(DocumentEvent e) {
				validateSearchBorder();
			}
			
		});
		
		this.closeButton.addMouseListener(new AdvancedMouseAdapter(){
			public void click(MouseEvent e){
				searchArea.setText(savedText);
				validateSearchBorder();
			}
		});
		
		container.setPreferredSize(null);
		Dimension size = container.getPreferredSize();
//		System.out.println(size);
		this.resizeWindow(size.width+bufferWidth, size.height+bufferHeight);
		FrameManager.centerFrame(this);
//		this.revalidate();
//		this.repaint();
		
//		helpPanel.add();
		
	}
	
	private void validateSearchBorder(){
		String text = searchArea.getText();
		if(text.equals(savedText)){
			searchScrollPane.setBorder(defaultBorder);
		}else{
			searchScrollPane.setBorder(unsavedBorder);
		}
	}
	
	private void updateSearchButton(){
		if(running){
			searchButton.setBackground(Color.GREEN);
		}else{
			searchButton.setBackground(null);
		}
	}
	
	
}
