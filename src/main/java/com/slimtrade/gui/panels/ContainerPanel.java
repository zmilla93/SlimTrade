package com.slimtrade.gui.panels;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class ContainerPanel extends JPanel implements IColorable {

	private static final long serialVersionUID = 1L;
	
	public JPanel container = new JPanel();
	private static final int defaultBorderSize = 15;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.black);
	
	public ContainerPanel(){
		this(defaultBorderSize);
	}
	
	private ContainerPanel(int borderSize){
		
//		this.setBackground(ColorManager.BACKGROUND);
//		container.setOpaque(false);
		container.setOpaque(false);
		this.setBorder(defaultBorder);
		this.setLayout(new BorderLayout());
		this.add(new BufferPanel(0, borderSize), BorderLayout.NORTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.WEST);
		this.add(new BufferPanel(0, borderSize), BorderLayout.SOUTH);
		this.add(new BufferPanel(borderSize, 0), BorderLayout.EAST);
		this.add(container, BorderLayout.CENTER);
	}

    @Override
    public void updateColor() {
        this.setBackground(ColorManager.LOW_CONSTRAST_1);
    }
}
