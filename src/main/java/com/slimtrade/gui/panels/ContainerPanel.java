package com.slimtrade.gui.panels;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import javax.swing.*;
import java.awt.*;

public class ContainerPanel extends JPanel implements IColorable {

	public JPanel container = new JPanel(new GridBagLayout());
	public GridBagConstraints gc = new GridBagConstraints();

	private static final int defaultBorderSize = 15;
	
	public ContainerPanel(){
		this(defaultBorderSize);
	}
	
	private ContainerPanel(int borderSize){
		container.setOpaque(false);
		this.setLayout(new BorderLayout());
		this.add(new BufferPanel(0, borderSize), BorderLayout.NORTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.WEST);
		this.add(new BufferPanel(0, borderSize), BorderLayout.SOUTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.EAST);
		this.add(container, BorderLayout.CENTER);
		gc.gridx = 0;
		gc.gridy = 0;
	}

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
		this.setForeground(ColorManager.TEXT);
		this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2, 1));
    }
}
