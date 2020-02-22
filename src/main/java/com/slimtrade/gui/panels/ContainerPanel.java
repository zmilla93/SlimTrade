package com.slimtrade.gui.panels;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.FrameManager;

import java.awt.*;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ContainerPanel extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	
	public JPanel container = new JPanel(FrameManager.gridBag);
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
	}

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONTRAST_1);
		this.setForeground(ColorManager.TEXT);
		this.setBorder(BorderFactory.createLineBorder(ColorManager.LOW_CONTRAST_2, 1));
    }
}
