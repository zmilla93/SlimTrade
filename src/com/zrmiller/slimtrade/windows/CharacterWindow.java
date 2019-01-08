package com.zrmiller.slimtrade.windows;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTextField;

import com.zrmiller.slimtrade.Overlay;
import com.zrmiller.slimtrade.dialog.BasicWindowDialog;

public class CharacterWindow extends BasicWindowDialog{

	private static final long serialVersionUID = 1L;
	private static int width = 300;
	private static int height = 100;
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
		this.setMinimumSize(new Dimension(10, 10));
		this.setVisible(false);
		
		//Character
		characterInput.setLayout(Overlay.flowCenter);
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
		
		saveButton.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseClicked(java.awt.event.MouseEvent evt) {
				try {
					String c = characterInput.getText();
					if(c.length()>=3 || c.length()<=23){
						setCharacter(c, leagueSelect.getSelectedItem().toString());
						ObjectOutputStream charSave = new ObjectOutputStream(new FileOutputStream("char.pref"));
						charSave.writeObject(c);
						charSave.writeObject(leagueSelect.getSelectedItem());
						charSave.close();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}				
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
