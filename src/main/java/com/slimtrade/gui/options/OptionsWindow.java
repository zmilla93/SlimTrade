
package com.slimtrade.gui.options;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.*;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.AdvancedMouseAdapter;
import com.slimtrade.core.observing.improved.ColorUpdateListener;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.CustomScrollBarUI;
import com.slimtrade.gui.options.general.GeneralPanel;
import com.slimtrade.gui.options.ignore.ItemIgnorePanel;
import com.slimtrade.gui.options.macros.IncomingCustomizer;
import com.slimtrade.gui.options.macros.OutgoingCustomizer;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.buttons.BasicButton;
import com.slimtrade.gui.buttons.ConfirmButton;
import com.slimtrade.gui.buttons.DenyButton;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.stash.StashTabPanel;

public class OptionsWindow extends AbstractResizableWindow implements ColorUpdateListener {

	private static final long serialVersionUID = 1L;
	private final JPanel display = new JPanel();
	private final JScrollPane scrollDisplay;

	private final JPanel menuPanel = new JPanel(new GridBagLayout());
	private final JPanel menuPanelLower = new JPanel(new GridBagLayout());
	private BasicButton checkUpdateButton = null;

	private boolean updateAvailable;

	public OptionsWindow() {
	    super("Options");
	    this.setAlwaysOnTop(false);
	    if(App.debugMode){
	        this.setTitleText(this.getTitle() + " - DEBUG");
        }
		this.setFocusableWindowState(true);
		this.setFocusable(true);
		container.setLayout(new BorderLayout());
		JPanel menuBorder = new JPanel(new BorderLayout());

		menuBorder.add(menuPanel, BorderLayout.NORTH);
		menuBorder.add(menuPanelLower, BorderLayout.SOUTH);

		scrollDisplay = new JScrollPane(display);
		scrollDisplay.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		scrollDisplay.getVerticalScrollBar().setUI(new CustomScrollBarUI());
        scrollDisplay.getVerticalScrollBar().setUnitIncrement(14);
        scrollDisplay.getHorizontalScrollBar().setUI(new CustomScrollBarUI());
        scrollDisplay.getHorizontalScrollBar().setUnitIncrement(14);
//        scrollDisplay.setHorizontalScrollBarPolicy(JScrollBar.);
		display.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		// gc.fill = GridBagConstraints.BOTH;

		int buffer = 6;
		JPanel bottomPanel = new JPanel();
		bottomPanel.setOpaque(false);
		menuPanel.setOpaque(false);
		menuPanelLower.setOpaque(false);
		menuBorder.setOpaque(false);

		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 30, buffer));
		// JButton resizeButton = new JButton("RESIZE");
		DenyButton revertButton = new DenyButton("Revert Changes");
		ConfirmButton saveButton = new ConfirmButton("Save");
		// bottomPanel.add(resizeButton);
		bottomPanel.add(revertButton);
		bottomPanel.add(saveButton);

		ListButton generalButton = new ListButton("General");
		GeneralPanel generalPanel = new GeneralPanel();
		link(generalButton, generalPanel);
		display.add(generalPanel, gc);

		ListButton stashButton = new ListButton("Stash Tabs");
		StashTabPanel stashPanel = new StashTabPanel();
		link(stashButton, stashPanel);
		display.add(stashPanel, gc);

		ListButton incomingButton = new ListButton("Incoming Macros");
		IncomingCustomizer incomingPanel = new IncomingCustomizer(this);
		link(incomingButton, incomingPanel);
		display.add(incomingPanel, gc);

		ListButton outgoingButton = new ListButton("Outgoing Macros");
		OutgoingCustomizer outgoingPanel = new OutgoingCustomizer();
		link(outgoingButton, outgoingPanel);
		display.add(outgoingPanel, gc);

		ListButton ignoreButton = new ListButton("Ignore Items");
		ItemIgnorePanel ignorePanel = new ItemIgnorePanel();
		link(ignoreButton, ignorePanel);
		display.add(ignorePanel, gc);

		JButton contactButton = new ListButton("Information");
		InformationPanel contactPanel = new InformationPanel();
		link(contactButton, contactPanel);
		display.add(contactPanel, gc);

		// JButton updateButton = new BasicButton("Update Available!");
		// updateButton.setVisible(false);

        checkUpdateButton = new BasicButton("Check for Updates");
		checkUpdateButton.setPreferredSize(checkUpdateButton.getPreferredSize());
		// TODO : Remove stash
		gc = new GridBagConstraints();
		// gc.anchor = GridBagConstraints.NORTH;

		gc.weightx = 1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.insets.bottom = 10;
		gc.insets.left = 5;
		gc.insets.right = 5;
		gc.gridx = 0;
		gc.gridy = 0;
		menuPanel.add(generalButton, gc);
		gc.gridy++;
		menuPanel.add(stashButton, gc);
		gc.gridy++;
		menuPanel.add(incomingButton, gc);
		gc.gridy++;
		menuPanel.add(outgoingButton, gc);
		gc.gridy++;
		menuPanel.add(ignoreButton, gc);
		gc.gridy++;
		menuPanel.add(contactButton, gc);
		gc.gridy++;

		// Update Button
		gc.gridx = 0;
		gc.gridy = 0;
		gc.insets.bottom = 0;

		gc.fill = GridBagConstraints.NONE;
		menuPanelLower.add(new JLabel(References.APP_NAME + " v" + References.APP_VERSION), gc);
		gc.gridy++;
		gc.fill = GridBagConstraints.HORIZONTAL;
		gc.gridy++;
		menuPanelLower.add(checkUpdateButton, gc);

		container.add(new BufferPanel(0, buffer), BorderLayout.NORTH);
		container.add(new BufferPanel(buffer, 0), BorderLayout.EAST);
		container.add(bottomPanel, BorderLayout.SOUTH);
		container.add(menuBorder, BorderLayout.WEST);
		container.add(scrollDisplay, BorderLayout.CENTER);

		generalPanel.setVisible(true);
		generalButton.active = true;
		this.setPreferredSize(new Dimension(1000, 600));

		this.refresh();
		//TODO : Resize doesn't respect maximum size
		this.setMinimumSize(new Dimension(600, 500));
		this.setMaximumSize(new Dimension(1600, 900));
		FrameManager.centerFrame(this);

		checkUpdateButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO : allowPrerelease
                if (App.updateChecker.isUpdateAvailable()) {
					//UPDATE
					try {
						URI uri = new URI("https://github.com/zmilla93/SlimTrade/releases/latest");
						Desktop.getDesktop().browse(uri);
					} catch (URISyntaxException | IOException err) {
						err.printStackTrace();
					}
				} else {
					new Thread(new Runnable() {
						public void run() {
							checkUpdateButton.setText("Checking...");
							checkUpdateButton.setEnabled(false);
//							updateAvailable = App.updateChecker.checkForUpdates();
							App.updateChecker.checkForUpdates();
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
							}
							if (App.updateChecker.isUpdateAvailable()) {
								// updateButton.setVisible(true);
								checkUpdateButton.setText("Update Available!");
								checkUpdateButton.setColor(Color.GREEN);
							} else {
								checkUpdateButton.setText("Check for Updates");
							}
							checkUpdateButton.setEnabled(true);

						}
					}).start();
				}
			}
		});

		revertButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generalPanel.load();
				// incomingPanel.load();
				// incomingPanel.rever
				// stashPanel.load();
				incomingPanel.revertChanges();
				stashPanel.revertChanges();
				ignorePanel.revertChanges();
			}
		});

		saveButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				generalPanel.save();
				incomingPanel.save();
				stashPanel.save();
				ignorePanel.save();
//				App.saveManager.saveToDisk();
                App.saveManager.saveToDisk();
//                System.out.println(App.saveFile.characterName);
			}
		});

		updateColor();
		recolorUpdateButton();
//		this.setVisible(true);
//		this.setShow(false);
		App.eventManager.addListener(this);

	}

	// TODO : Make on press down?
	// switch type to list button for less casting
	private void link(JButton b, JPanel p) {
		OptionsWindow local = this;
		b.addMouseListener(new AdvancedMouseAdapter() {
			public void click(MouseEvent e) {
				ListButton lb;
				for (Component c : menuPanel.getComponents()) {
					lb = (ListButton) c;
					lb.active = false;
				}
				lb = (ListButton) b;
				lb.active = true;
				hideAllWindows();
				p.setVisible(true);
				// lb.repaint();
				local.repaint();
			}
		});
	}

	private void hideAllWindows() {
		for (Component c : display.getComponents()) {
			c.setVisible(false);
		}
	}

	public void refresh() {

		display.revalidate();
		scrollDisplay.revalidate();
		this.pack();
		this.repaint();
	}

	@Override
	public void updateColor() {
	    super.updateColor();
		container.setBackground(ColorManager.BACKGROUND);
		display.setBackground(ColorManager.BACKGROUND);
//        pullRight.setBackground(AbstractResizableWindow.pullbarColor);
//        pullBottom.setBackground(AbstractResizableWindow.pullbarColor);
	}

	private void recolorUpdateButton(){
        if(App.updateChecker.isUpdateAvailable()){
            this.checkUpdateButton.setText("Update Available!");
            this.checkUpdateButton.setColor(Color.GREEN);
        }
    }

}
