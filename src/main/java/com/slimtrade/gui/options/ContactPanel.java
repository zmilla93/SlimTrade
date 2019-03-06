package main.java.com.slimtrade.gui.options;

import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;

import main.java.com.slimtrade.gui.panels.BufferPanel;

public class ContactPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;

	public ContactPanel(){
		//TODO : Might need to change background color of text areas
		super(false);
		JLabel gitTitle = new JLabel("GitHub");
		JTextField gitText = new JTextField("https://github.com/zmilla93/SlimTrade");
		Dimension gitSize = gitText.getPreferredSize();
		gitSize.width++;
		gitText.setPreferredSize(gitSize);
		gitText.setBorder(null);
		gitText.setEditable(false);
		
		JLabel emailTitle = new JLabel("E-Mail");
		JTextField emailText = new JTextField("slimtradepoe@gmail.com");
		Dimension emailSize = emailText.getPreferredSize();
		emailSize.width++;
		emailText.setPreferredSize(emailSize);
		emailText.setBorder(null);
		emailText.setEditable(false);
		
		JLabel info1 = new JLabel("Bugs can be reported on github or via e-mail");
		JLabel info2 = new JLabel("Design/feature feedback can be sent via e-mail.");
		JLabel info3 = new JLabel("Please read the github post before sending feedback to avoid redundancy.");
		
		gc.weightx = 1;
		
		this.add(gitTitle, gc);
		gc.gridx = 1;
		this.add(gitText, gc);
		gc.gridx = 0;
		gc.gridy++;
		this.add(emailTitle, gc);
		gc.gridx = 1;
		this.add(emailText, gc);
		gc.gridx = 0;
		gc.gridy++;
		gc.gridwidth = 2;
		this.add(new BufferPanel(0, 10), gc);
		gc.gridy++;
		this.add(info1, gc);
		gc.gridy++;
		this.add(info2, gc);
		gc.gridy++;
		this.add(info3, gc);
		
		
		
		this.setBuffer(40, -1);
		this.autoResize();
		
	}
	
	
}
