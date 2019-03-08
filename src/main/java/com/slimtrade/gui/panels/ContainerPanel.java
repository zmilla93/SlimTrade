package main.java.com.slimtrade.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.border.Border;

import main.java.com.slimtrade.core.managers.ColorManager;

public class ContainerPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public JPanel container = new JPanel();
	private final static int defaultBorderSize = 10;
	private Border defaultBorder = BorderFactory.createLineBorder(Color.black);
	
	public ContainerPanel(){
		this(defaultBorderSize);
	}
	
	private ContainerPanel(int size){
		container.setOpaque(false);
		this.setBorder(defaultBorder);
		this.setLayout(new BorderLayout());
		this.add(new BufferPanel(0, size), BorderLayout.NORTH);
		this.add(new BufferPanel(size, 0), BorderLayout.WEST);
		this.add(new BufferPanel(0, size), BorderLayout.SOUTH);
		this.add(new BufferPanel(size, 0), BorderLayout.EAST);
		this.add(container, BorderLayout.CENTER);
	}
		
}
