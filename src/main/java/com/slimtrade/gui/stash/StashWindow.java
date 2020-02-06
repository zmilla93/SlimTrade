package com.slimtrade.gui.stash;

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
import javax.swing.JPanel;

import com.slimtrade.App;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.stash.helper.ItemHighlighter;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.HideableDialog;

public class StashWindow extends AbstractResizableWindow implements ISaveable {

	private static final long serialVersionUID = 1L;

	private StashGridPanel gridPanel;
	private boolean vis = false;

	public StashWindow() {
		super("Stash Overlay", false);
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
		JButton resetButton = new JButton("Cancel");
		JButton saveButton = new JButton("Save");

		buttonPanel.setLayout(new GridBagLayout());

		gc.gridx = 0;
		gc.gridy = 0;
		Insets inset = new Insets(10, 0, 10, 60);
		gc.insets = inset;

		buttonPanel.add(infoButton, gc);
		gc.gridx++;
		buttonPanel.add(resetButton, gc);
		gc.gridx++;
		inset.right = 0;
		buttonPanel.add(saveButton, gc);
		container.add(buttonPanel, BorderLayout.SOUTH);

		HideableDialog local = this;
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
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				vis = true;
				load();
				local.setShow(false);
				FrameManager.showVisibleFrames();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				save();
				FrameManager.stashHelperContainer.updateBounds();
				local.setShow(false);
				FrameManager.showVisibleFrames();
				App.saveManager.saveToDisk();
			}
		});

		// closeButton.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent e) {
		// FrameManager.showVisibleFrames();
		// }
		// });
		// load();
		// ItemHighlighter
        this.updateColor();

	}

	public void save() {
		Point winPos = this.getLocation();
		Dimension winSize = this.getSize();
		Dimension gridSize = gridPanel.getSize();
		App.saveManager.saveFile.stashX = winPos.x;
		App.saveManager.saveFile.stashY = winPos.y;
		App.saveManager.saveFile.stashWidth = winSize.width;
		App.saveManager.saveFile.stashHeight = winSize.height;
		ItemHighlighter.saveGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());

	}

	public void load() {
        this.setLocation(App.saveManager.saveFile.stashX, App.saveManager.saveFile.stashY);
        this.setSize(App.saveManager.saveFile.stashWidth, App.saveManager.saveFile.stashHeight);
//		if (App.saveManager.hasEntry("stashOverlay")) {
//			// System.out.println("Loading Grid Panel");
//
//
//
//			// System.out.println("WIDTH " + gridPanel.getWidth());
//			this.setVisible(true);
//			ItemHighlighter.saveGridInfo(gridPanel.getLocationOnScreen().x, gridPanel.getLocationOnScreen().y, gridPanel.getWidth(), gridPanel.getHeight());
//
//			this.setVisible(vis);
//			vis = false;
//		}
	}

    @Override
    public void updateColor() {
        // Note, this is also changed in
        pullRight.setBackground(pullbarColor);
        pullBottom.setBackground(pullbarColor);
//        pullRight.setBackground(Color.BLACK);
//        pullBottom.setBackground(Color.BLACK);
//        this.repaint();
    }


}
