package main.java.com.slimtrade.panels.options;

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

import main.java.com.slimtrade.panels.BasicIcon;
import main.java.com.slimtrade.windows.OptionsWindow;

public class UNUSED_ButtonOptionPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private int width = OptionsWindow.contentWidth;
	private int rowHeight = OptionsWindow.rowHeight;
	
	private int minWidth = 150;
	private int minHeight = 60;
	
	private JCheckBox enableCheckbox = new JCheckBox("Enable");
	private JCheckBox secondaryCheckbox = new JCheckBox("Secondary Macro");
	
	JPanel clickPanel2 = new JPanel();
	JPanel clickPanel1 = new JPanel();
	JPanel iconPanel = new JPanel();
	
	private JLabel clickLabel = new JLabel("Message");
	private JLabel clickLMB = new JLabel("Left Mouse");
	JLabel clickRMB = new JLabel("Right Mouse");
	
	
	public UNUSED_ButtonOptionPanel(){
		this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setMinimumSize(new Dimension(600, 100));
		this.setPreferredSize(null);
		
		Border baseBorder = BorderFactory.createLineBorder(Color.BLACK);
		Border titleBorder = BorderFactory.createTitledBorder(baseBorder, "Thank Button");
		this.setBorder(titleBorder);
		
		enableCheckbox.setFocusable(false);
		secondaryCheckbox.setFocusable(false);
		
		JPanel checkboxPanel = new JPanel();
		checkboxPanel.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
//		enableCheckbox.setBackground(Color.yellow);
//		enableCheckbox.setSelected(true);
		
//		secondaryCheckbox.setBackground(Color.green);
//		secondaryCheckbox.setSelected(false);
		
		//TODO : Add icon selector?
		BasicIcon buttonIcon = new BasicIcon("/resources/icons/thumb1.png");
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

		gc.weightx = 0.1;
		gc.weighty = 0.9;
		gc.gridx = 0;
		gc.gridy = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		clickPanel1.setLayout(new GridBagLayout());
		JTextField clickTextField1 = new JTextField(30);
		clickPanel1.add(clickLabel, gc);
		clickPanel1.add(clickLMB, gc);
		gc.anchor = GridBagConstraints.LINE_START;
		gc.gridx = 1;
		clickPanel1.add(clickTextField1, gc);
		
		gc.gridx = 0;
		gc.anchor = GridBagConstraints.LINE_END;
		clickPanel2.setLayout(new GridBagLayout());
		JTextField clickTextField2 = new JTextField(30);
		clickPanel2.add(clickRMB, gc);
		gc.gridx = 1;
		gc.anchor = GridBagConstraints.LINE_START;
		clickPanel2.add(clickTextField2, gc);
		
		this.add(checkboxPanel);
		this.add(clickPanel1);
		this.add(clickPanel2);
		
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
	
	public void updateView(){
		if(enableCheckbox.isSelected()){
			//Enabled
			secondaryCheckbox.setVisible(true);
			iconPanel.setVisible(true);
			clickPanel1.setVisible(true);
			if(secondaryCheckbox.isSelected()){
				//Secondary Enabled
				clickLabel.setVisible(false);
				clickLMB.setVisible(true);
				clickPanel2.setVisible(true);
			}else{
				//Single Button
				clickLMB.setVisible(false);
				clickLabel.setVisible(true);
				clickPanel1.setVisible(true);
				clickPanel2.setVisible(false);
			}
		}else{
			//Disabled
			iconPanel.setVisible(false);
			clickPanel1.setVisible(false);
			clickPanel2.setVisible(false);
			secondaryCheckbox.setVisible(false);
			
		}
		if(this.getPreferredSize().width < minWidth){
			this.setPreferredSize(new Dimension(minWidth, minHeight));
		}else{
			this.setPreferredSize(null);
		}
		this.revalidate();
		this.repaint();
	}
}
