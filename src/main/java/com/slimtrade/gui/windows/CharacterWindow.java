package main.java.com.slimtrade.gui.windows;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.observing.AdvancedMouseAdapter;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicWindowDialog;

public class CharacterWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	private static int width = 400;
	private static int height = 80;
	private String activeCharacter = "";
	private String activeLeague = "";
//	private int rowHeight = 25;
	
	JTextField characterInput = new JTextField(20);
	private String[] league = {"Standard", "Standard Hardcore", "Betrayal", "Betrayal Hardcore"};
	private JComboBox<String> leagueSelect = new JComboBox<String>(league);
	JButton saveButton = new JButton("Save");
	
	public CharacterWindow(){
		super("Character");
		this.setFocusableWindowState(true);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		container.setLayout(new BorderLayout());
		this.setMinimumSize(new Dimension(10, 10));
		this.resizeWindow(width, height);
		
		JPanel characterPanel = new JPanel();
		JPanel savePanel = new JPanel();
		
		//Character
		characterInput.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		characterInput.setHorizontalAlignment(JTextField.CENTER);
		
		//League
//		leagueSelect.setFocusable(false);
		leagueSelect.setSelectedItem("Betrayal");

		//Save Button
		saveButton.setFocusable(false);
		savePanel.add(saveButton);
		
		//Container
		characterPanel.add(characterInput);
		characterPanel.add(leagueSelect);
		container.add(characterPanel, BorderLayout.CENTER);
		container.add(savePanel, BorderLayout.PAGE_END);
		
		characterInput.getDocument().addDocumentListener(new DocumentListener(){
			public void changedUpdate(DocumentEvent e){
//				System.out.println("CHANGE");
//				System.out.println(characterInput.getText());
			}
			public void removeUpdate(DocumentEvent e){
//				System.out.println("REMOVE");
//				System.out.println(characterInput.getText());
			}
			public void insertUpdate(DocumentEvent e){
//				System.out.println("INSERT");
//				System.out.println(characterInput.getText());
			}
		});
		
		leagueSelect.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        //LEAGE CHANGE
		    }
		});
		
		saveButton.addMouseListener(new AdvancedMouseAdapter() {
		    public void click(MouseEvent evt) {
		    	Main.fileManager.saveCharacterData(characterInput.getText(), leagueSelect.getSelectedItem().toString());			
		    }
		});
		
//		this.setVisible(true);
		FrameManager.centerFrame(this);
		
	}
	
	public void setCharacter(String character, String league){
		this.activeCharacter = character;
		this.characterInput.setText(character);
		this.activeLeague = league;
		this.leagueSelect.setSelectedItem(league);
	}
	
	public String getCharacterName(){
		return this.activeCharacter;
	}
	
}
