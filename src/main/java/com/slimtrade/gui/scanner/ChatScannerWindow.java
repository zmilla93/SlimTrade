package com.slimtrade.gui.scanner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.slimtrade.App;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.basic.AbstractResizableWindow;
import com.slimtrade.gui.basic.CustomCombo;
import com.slimtrade.gui.basic.CustomTextField;
import com.slimtrade.gui.enums.PreloadedImage;
import com.slimtrade.gui.options.ISaveable;
import com.slimtrade.gui.panels.BufferPanel;
import com.slimtrade.gui.panels.ContainerPanel;
import com.slimtrade.gui.panels.IconPanel;

public class ChatScannerWindow extends AbstractResizableWindow implements ISaveable {

	private static final long serialVersionUID = 1L;

	private JComboBox searchCombo = new CustomCombo();
	private JTextField saveTextField = new CustomTextField();

	public ChatScannerWindow() {
		super("Chat Scanner");
		this.setAlwaysOnTop(false);
		this.setFocusableWindowState(true);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(400, 400));
		this.pack();
		FrameManager.centerFrame(this);
		updateColor();
	}


	@Override
	public void save() {

	}

	@Override
	public void load() {

	}
}
