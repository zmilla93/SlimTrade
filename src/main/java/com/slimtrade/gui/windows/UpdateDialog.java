package com.slimtrade.gui.windows;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.utility.UpdateChecker;
import com.slimtrade.core.utility.VersionNumber;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;

public class UpdateDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	private JButton viewUpdateButton = new JButton("View on Github");

	public UpdateDialog(){
		this.setTitle("SlimTrade - Update");
		VersionNumber newVersion = null;
		if(App.updateChecker.isAllowPreReleases() && App.updateChecker.isNewPreReleaseAvailable()) {
		    newVersion = App.updateChecker.getLatestPreRelease();
        } else {
            newVersion = App.updateChecker.getLatestRelease();
        }
		viewUpdateButton.setFocusable(false);
		ContainerPanel containerPanel = new ContainerPanel();
		JPanel container = containerPanel.container;
		this.getContentPane().add(containerPanel);
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		JLabel info1 = new JLabel("New version available!");
		JLabel info2 = new JLabel("Currently Running: " + References.APP_VERSION);
		JLabel info3 = new JLabel("Latest Version: " + App.updateChecker.getNewestVersion());
		
		int bufferY = 10;
		container.add(info1, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;
		container.add(info2, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, bufferY), gc);
		gc.gridy++;
		container.add(info3, gc);
		gc.gridy++;
		container.add(new BufferPanel(0, 20), gc);
		gc.gridy++;
		container.add(viewUpdateButton, gc);

		viewUpdateButton.addActionListener(e -> {
            try {
                URI uri = new URI("https://github.com/zmilla93/SlimTrade/releases/latest");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException | IOException err) {
                err.printStackTrace();
            }
        });
		
		this.setAlwaysOnTop(true);
		this.toFront();
		this.setPreferredSize(new Dimension(400, 200));
		this.pack();
		FrameManager.centerFrame(this);
	}
	
}
