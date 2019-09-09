package com.slimtrade.gui.basic;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.panels.BufferPanel;

public abstract class AbstractWindow extends BasicMovableDialog {

	private static final long serialVersionUID = 1L;
	public final int TITLEBAR_HEIGHT = 20;
	public final int BORDER_THICKNESS = 1;

	private JPanel titlebarPanel = new JPanel();
	protected JPanel center = new JPanel();
	private JLabel titleLabel;

	protected IconButton closeButton;
	protected Container contentPane = this.getContentPane();

	private Color borderColor = ColorManager.PRIMARY;
	
	private GridBagConstraints gc = new GridBagConstraints();

	public AbstractWindow(String title, boolean makeCloseButton) {
		String fullTitle = "SlimTrade - " + title;
		this.setTitle(fullTitle);
//		this.createListeners(titlebarPanel);

		contentPane.setLayout(new BorderLayout());
		contentPane.setBackground(borderColor);
		titlebarPanel.setBackground(borderColor);
		center.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, borderColor));
		center.setBackground(Color.CYAN);

		titlebarPanel.setLayout(new BorderLayout());
		center.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		//TODO : Container color
		center.setBackground(borderColor);
		
		gc.gridx = 0;
		gc.gridy = 0;
		gc.ipadx = 0;
		gc.ipady = 0;
		gc.fill = GridBagConstraints.HORIZONTAL;
		titlebarPanel.add(new BufferPanel(5,0), BorderLayout.WEST);
		titleLabel = new JLabel(fullTitle);
		gc.anchor = GridBagConstraints.LINE_START;
		titlebarPanel.add(titleLabel, BorderLayout.CENTER);
		JPanel p = new JPanel();
		

		gc.insets = new Insets(0, 0, 0, 0);

		gc.gridx++;
		
		if (makeCloseButton) {
			gc.anchor = GridBagConstraints.LINE_END;
			closeButton = new IconButton(PreloadedImage.CLOSE.getImage(), TITLEBAR_HEIGHT);
//			titlebarPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
			titlebarPanel.add(closeButton, BorderLayout.EAST);
			AbstractWindow local = this;
			closeButton.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e) {
					new Thread(new Runnable(){
						public void run() {
							local.setShow(false);
							try {
								Thread.sleep(50);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							FrameManager.forceAllToTop();
						}
					}).start();;
				}
			});

		}
		
		contentPane.add(titlebarPanel, BorderLayout.NORTH);
		contentPane.add(center, BorderLayout.CENTER);

		this.setLocation(0, 0);
		titlebarPanel.setPreferredSize(new Dimension(50, TITLEBAR_HEIGHT));
		this.pack();
		if(closeButton != null && closeButton.getLocation().x%2 != 0){
			gc.insets = new Insets(0, 1, 0, 0);
			titlebarPanel.add(closeButton, gc);
		}
		this.createListeners(titlebarPanel);
	}

	public void setTitleText(String title){
	    this.setTitle(title);
	    titleLabel.setText(title);
    }

}
