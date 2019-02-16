package main.java.com.slimtrade.gui.options.advanced;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;

import main.java.com.slimtrade.gui.options.ContentPanel;
import main.java.com.slimtrade.gui.panels.BufferPanel;

public class AdvancedPanel extends ContentPanel {

	private static final long serialVersionUID = 1L;
	private final int BUFFER_SIZE = 5;

	public AdvancedPanel() {

		AdvancedRow clientRow = new AdvancedRow("Client Path");
		AdvancedRow saveRow = new AdvancedRow("Save Directory");

		// TODO : hide save directory if valid
		this.addRow(clientRow, gc);
		this.addRow(new BufferPanel(0, BUFFER_SIZE), gc);
		this.addRow(saveRow, gc);

		clientRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = clientRow.getFileChooser().showOpenDialog(clientRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File clientFile = clientRow.getFileChooser().getSelectedFile();
					String path = clientFile.getPath();
					clientRow.getPathLabel().setText(path);
				}
			}
		});
		
		saveRow.getEditButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int action = saveRow.getFileChooser().showOpenDialog(saveRow);
				if (action == JFileChooser.APPROVE_OPTION) {
					File savePath = saveRow.getFileChooser().getSelectedFile();
					String path = savePath.getPath();
					saveRow.getPathLabel().setText(path);
				}
			}
		});

		this.autoResize();
	}

}
