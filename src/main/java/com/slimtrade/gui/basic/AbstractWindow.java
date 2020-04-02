package com.slimtrade.gui.basic;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.buttons.IconButton;
import com.slimtrade.gui.custom.CustomLabel;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.panels.BufferPanel;

import javax.swing.*;
import java.awt.*;

public abstract class AbstractWindow extends BasicMovableDialog implements IColorable {

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
		titleLabel = new CustomLabel(fullTitle);
		gc.anchor = GridBagConstraints.LINE_START;
		titlebarPanel.add(titleLabel, BorderLayout.CENTER);
		JPanel p = new JPanel();
		

		gc.insets = new Insets(0, 0, 0, 0);

		gc.gridx++;
		
		if (makeCloseButton) {
			gc.anchor = GridBagConstraints.LINE_END;
			closeButton = new IconButton(DefaultIcons.CLOSE, TITLEBAR_HEIGHT);
			titlebarPanel.add(closeButton, BorderLayout.EAST);
			AbstractWindow local = this;
			closeButton.addActionListener(e -> {
				local.setShow(false);
				FrameManager.refreshMenuFrames();
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

	@Override
	public void updateColor() {
		super.updateColor();
		titlebarPanel.setBackground(ColorManager.PRIMARY);
		titleLabel.setForeground(ColorManager.TEXT);
		center.setBorder(BorderFactory.createMatteBorder(0, 1, 1, 1, ColorManager.PRIMARY));
	}
}
