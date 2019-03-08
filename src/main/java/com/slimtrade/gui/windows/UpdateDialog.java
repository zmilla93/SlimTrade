package main.java.com.slimtrade.gui.windows;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
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

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.References;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.panels.BufferPanel;
import main.java.com.slimtrade.gui.panels.ContainerPanel;

public class UpdateDialog extends JDialog {

	JButton laterButton = new JButton("Update Later");
	JButton viewButton = new JButton("View on Github");
	
	
	
	public UpdateDialog(){
		this.setTitle("SlimTrade - Update");
		laterButton.setFocusable(false);
		viewButton.setFocusable(false);
//		this.setModal(true);
		ContainerPanel containerPanel = new ContainerPanel();
		JPanel container = containerPanel.container;
		this.getContentPane().add(containerPanel);
		
		container.setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		gc.gridx = 0;
		gc.gridy = 0;
		
		JLabel info1 = new JLabel("New version available!");
		JLabel info2 = new JLabel("Current Version : " + References.APP_VERSION);
		JLabel info3 = new JLabel("Latest Version : " + Main.updateChecker.getLatestVersion());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 0));
		buttonPanel.add(laterButton);
		buttonPanel.add(viewButton);
		
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
		container.add(buttonPanel, gc);
		
		JDialog local = this;
		laterButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				local.dispose();
			}
		});
		
		viewButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				try {
					URI uri = new URI("https://github.com/zmilla93/SlimTrade/releases");
					Desktop.getDesktop().browse(uri);
				} catch (URISyntaxException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		this.setAlwaysOnTop(true);
		this.toFront();
		this.setPreferredSize(new Dimension(400, 200));
		this.pack();
		FrameManager.centerFrame(this);
	}
	
}
