package main.java.com.slimtrade.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import main.java.com.slimtrade.core.FrameManager;
import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.dialog.BasicWindowDialog;

public class CharacterWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	private static int width = 400;
	private static int height = 150;
	private String activeCharacter = "";
	private String activeLeague = "";
//	private int rowHeight = 25;
	
	JTextField characterInput = new JTextField(20);
	private String[] league = {"Standard", "Standard Hardcore", "Betrayal", "Betrayal Hardcore"};
	private JComboBox<String> leagueSelect = new JComboBox<String>(league);
	JButton saveButton = new JButton("Save");
	
	public CharacterWindow(){
//		super("Character", width, height);
		super("Character");
//		Overlay.centerFrame(this);
		this.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
//		this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(10, 10));
		this.setSize(width, height);
//		this.setVisible(false);
		
		//Character
		characterInput.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		characterInput.setHorizontalAlignment(JTextField.CENTER);
		container.add(characterInput);
		
		//League
//		leagueSelect.list();
		leagueSelect.setFocusable(false);
		leagueSelect.setSelectedItem("Betrayal");
		container.add(leagueSelect);
		
		//Save Button
		saveButton.setFocusable(false);
		container.add(saveButton);
		
		FrameManager.centerFrame(this);
		
		saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
		    	Main.fileManager.saveCharacterData(characterInput.getText(), leagueSelect.getSelectedItem().toString());			
		    }
		});
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
