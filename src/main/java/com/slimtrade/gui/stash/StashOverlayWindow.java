package main.java.com.slimtrade.gui.stash;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.options.Saveable;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.stash.helper.ItemHighlighter;

public class StashOverlayWindow extends ResizableWindow implements Saveable {

	private static final long serialVersionUID = 1L;

	private StashGridPanel gridPanel;
	private boolean vis = false;

	public StashOverlayWindow() {
		super("Stash Overlay");
		this.setMinimumSize(new Dimension(300, 300));
		container.setBackground(ColorManager.CLEAR);
		center.setBackground(ColorManager.CLEAR);
		this.setBackground(ColorManager.CLEAR);
		// this.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.4f));

		Logger.getAnonymousLogger().log(Level.ALL, "LOGGER");

		int buffer = 10;
		JPanel gridOuter = new JPanel();
		gridOuter.setLayout(new BorderLayout());
		gridOuter.setBackground(ColorManager.CLEAR);
		container.setLayout(new BorderLayout());
		gridOuter.add(new BufferPanel(0, 40), BorderLayout.NORTH);
		gridOuter.add(new BufferPanel(buffer, 0), BorderLayout.WEST);
		gridOuter.add(new BufferPanel(0, 40), BorderLayout.SOUTH);
		gridOuter.add(new BufferPanel(buffer, 0), BorderLayout.EAST);

		gridPanel = new StashGridPanel();
		gridOuter.add(gridPanel, BorderLayout.CENTER);

		container.add(gridOuter, BorderLayout.CENTER);

		GridBagConstraints gc = new GridBagConstraints();
		JPanel buttonPanel = new JPanel();
		JButton infoButton = new JButton("Grid");
		JButton resetButton = new JButton("Reset");
		JButton saveButton = new JButton("Save");

		buttonPanel.setLayout(new GridBagLayout());

		gc.gridx = 0;
		gc.gridy = 0;
		Insets inset = new Insets(10, 0, 10, 20);
		gc.insets = inset;

		buttonPanel.add(infoButton, gc);
		gc.gridx++;
		buttonPanel.add(resetButton, gc);
		gc.gridx++;
		inset.right = 0;
		buttonPanel.add(saveButton, gc);
		container.add(buttonPanel, BorderLayout.SOUTH);

		JDialog local = this;
		infoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int count = gridPanel.getGridCellCount();
				if (count == 12) {
					gridPanel.setGridCellCount(24);
				} else if (count == 24) {
					gridPanel.setGridCellCount(0);
				} else {
					gridPanel.setGridCellCount(12);
				}
				// gridPanel.pain
				local.repaint();
				// gridPanel.repaint();

			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				FrameManager.stashHelperContainer.updateBounds();
			}
		});
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis = true;
				load();
			}
		});
		// load();
		// ItemHighlighter

	}

	public void save() {
		Point winPos = this.getLocation();
		Dimension winSize = this.getSize();
		Dimension gridSize = gridPanel.getSize();
		Main.saveManager.putInt(winPos.x, "stashOverlay", "x");
		Main.saveManager.putInt(winPos.y, "stashOverlay", "y");
		Main.saveManager.putInt(winSize.width, "stashOverlay", "width");
		Main.saveManager.putInt(winSize.height, "stashOverlay", "height");
		ItemHighlighter.saveGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());

	}

	public void load() {
		if (Main.saveManager.hasEntry("stashOverlay")) {
			// System.out.println("Loading Grid Panel");

			this.setLocation(Main.saveManager.getInt("stashOverlay", "x"), Main.saveManager.getInt("stashOverlay", "y"));
			this.setSize(Main.saveManager.getInt("stashOverlay", "width"), Main.saveManager.getInt("stashOverlay", "height"));

			// System.out.println("WIDTH " + gridPanel.getWidth());
			this.setVisible(true);
			ItemHighlighter.saveGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());

			this.setVisible(vis);
			vis = false;
		}
	}

}
