package main.java.com.slimtrade.gui.options;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import main.java.com.slimtrade.gui.panels.BasicIcon_REMOVE;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class InbuiltMacroCell extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width = OptionsWindow.contentWidth;
	private int rowHeight = OptionsWindow.rowHeight;
	
	private int minWidth = 150;
	private int minHeight = 60;
	
	
//	JPanel clickPanel = new JPanel();
	private JPanel iconPanel = new JPanel();
	private JLabel helpLabel = new JLabel();
	
	GridBagConstraints gc = new GridBagConstraints();
	private MacroPanel parent;
	
	public InbuiltMacroCell(String title, String iconPath, MacroPanel parent){
		this.parent = parent;
		this.setLayout(new GridBagLayout());
//		this.setMinimumSize(new Dimension(600, 100));
//		this.setPreferredSize(null);
		
		Border baseBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(baseBorder, title);
		this.setBorder(titleBorder);

		
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new GridBagLayout());
		

		BasicIcon_REMOVE buttonIcon = new BasicIcon_REMOVE(iconPath);
		JLabel iconLabel = new JLabel("Icon");
		iconPanel.add(buttonIcon);
		iconPanel.add(iconLabel);

		gc.gridx = 0;
		gc.gridy = 0;


		this.add(iconPanel, gc);
		gc.gridy++;
	
		this.setPreferredSize(null);
		if(this.getPreferredSize().width < minWidth){
			this.setPreferredSize(new Dimension(minWidth, minHeight));
		}else{
			this.setPreferredSize(null);
		}
	}
	
	public void setLabelText(String text){
		this.helpLabel.setText(text);
		autoResize(20);
	}
	
	public void addText(String text){
		JLabel label = new JLabel(text);
		this.add(label, gc);
		gc.gridy++;
		autoResize(20);
	}
	
	public void setParent(MacroPanel parent){
		this.parent = parent;
	}
	
	public void autoResize(int buffer){
		this.setPreferredSize(null);
		Dimension size = this.getPreferredSize();
		this.setPreferredSize(new Dimension(size.width+buffer, size.height));
	}
}
