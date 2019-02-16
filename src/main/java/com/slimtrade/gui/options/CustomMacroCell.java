package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.gui.panels.BasicIcon_REMOVE;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class CustomMacroCell extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width = OptionsWindow_UNUSED.contentWidth;
	private int rowHeight = OptionsWindow_UNUSED.ROW_HEIGHT;
	
	private String saveKey;
	
	private int minWidth = 150;
	private int minHeight = 60;
	
	private JCheckBox enableCheckbox = new JCheckBox("Enable");
	private JCheckBox secondaryCheckbox = new JCheckBox("Secondary Macro");
	
//	JPanel clickPanel2 = new JPanel();
//	JPanel clickPanel1 = new JPanel();
	JPanel clickPanel = new JPanel();
	JPanel iconPanel = new JPanel();
	
	private JLabel clickLabel = new JLabel("Message");
	private JLabel clickLabelLMB = new JLabel("Left Mouse");
	private JLabel clickLabelRMB = new JLabel("Right Mouse");
	
	private JTextField clickTextFieldLMB = new JTextField(30);
	private JTextField clickTextFieldRMB = new JTextField(30);
	
	private MacroPanel parent;
	
	public CustomMacroCell(String title, String iconPath, String saveKey, MacroPanel parent){
		this.parent = parent;
		this.saveKey = saveKey;
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(600, 100));
		this.setPreferredSize(null);
		
		Border baseBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(baseBorder, title);
		this.setBorder(titleBorder);
		
		enableCheckbox.setFocusable(false);
		secondaryCheckbox.setFocusable(false);
		
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();

		BasicIcon_REMOVE buttonIcon = new BasicIcon_REMOVE(iconPath);
		JLabel iconLabel = new JLabel("Icon");
		iconPanel.add(buttonIcon);
		iconPanel.add(iconLabel);
		
		gc.weightx = 0.5;
		gc.weighty = 0.5;
		gc.gridx = 0;
		gc.gridy = 0;
		checkboxPanel.add(enableCheckbox, gc);
		gc.gridx = 1;
		checkboxPanel.add(secondaryCheckbox, gc);
		gc.gridx = 2;
		checkboxPanel.add(iconPanel, gc);
		
//		gc.weightx = 0;
//		gc.weighty = 1;
		gc.gridx = 0;
		gc.gridy = 0;
		clickPanel.setLayout(new GridBagLayout());

		
		clickPanel.add(clickLabel, gc);
		clickPanel.add(clickLabelLMB, gc);
		gc.gridx++;
		clickPanel.add(new BufferPanel(10, 0));
		gc.gridx++;
		clickPanel.add(clickTextFieldLMB, gc);
		
		gc.gridx = 0;
		gc.gridy = 1;

		clickPanel.add(clickLabelRMB, gc);
		gc.gridx++;
		clickPanel.add(new BufferPanel(10, 0));
		gc.gridx++;
		clickPanel.add(clickTextFieldRMB, gc);
		
		this.add(checkboxPanel);
		this.add(clickPanel);
	
		
		updateView();
		
		enableCheckbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				updateView();
			}
		});
		secondaryCheckbox.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				updateView();
			}
		});
	}
	
	public void applySavedSettings(){
		enableCheckbox.setSelected(Main.saveManager.getBool("macroButtons", this.saveKey, "enabled"));
		secondaryCheckbox.setSelected(Main.saveManager.getBool("macroButtons", this.saveKey, "secondaryEnabled"));
		clickTextFieldLMB.setText(Main.saveManager.getString("macroButtons", this.saveKey, "textLMB"));
		clickTextFieldRMB.setText(Main.saveManager.getString("macroButtons", this.saveKey, "textRMB"));
		updateView();
	}
	
	public void saveSettings(){
		Main.saveManager.putBool(enableCheckbox.isSelected(), "macroButtons", this.saveKey, "enabled");
		Main.saveManager.putBool(secondaryCheckbox.isSelected(), "macroButtons", this.saveKey, "secondaryEnabled");
		Main.saveManager.putString(clickTextFieldLMB.getText(), "macroButtons", this.saveKey, "textLMB");
		Main.saveManager.putString(clickTextFieldRMB.getText(), "macroButtons", this.saveKey, "textRMB");
	}
	
	public void setParent(MacroPanel parent){
		this.parent = parent;
	}
	
	public void updateView(){
		if(enableCheckbox.isSelected()){
			//Enabled
			secondaryCheckbox.setVisible(true);
			iconPanel.setVisible(true);
			clickPanel.setVisible(true);
			if(secondaryCheckbox.isSelected()){
				//Secondary Enabled
				clickLabel.setVisible(false);
				clickLabelLMB.setVisible(true);
				clickLabelRMB.setVisible(true);
				clickTextFieldRMB.setVisible(true);
			}else{
				//Single Button
				clickLabelLMB.setVisible(false);
				clickLabelRMB.setVisible(false);
				clickTextFieldRMB.setVisible(false);
//				clickTextField
				clickLabel.setVisible(true);
				
			}
		}else{
			//Disabled
			iconPanel.setVisible(false);
			clickPanel.setVisible(false);
			secondaryCheckbox.setVisible(false);
			
		}
		this.setPreferredSize(null);
		if(this.getPreferredSize().width < minWidth){
			this.setPreferredSize(new Dimension(minWidth, minHeight));
		}else{
			this.setPreferredSize(null);
		}
		parent.autoResize();
		this.revalidate();
		this.repaint();
	}
}
