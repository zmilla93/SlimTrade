package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.managers.ExternalFileManager;
import main.java.com.slimtrade.core.managers.SaveManager;
import main.java.com.slimtrade.core.observing.EventManager;
import main.java.com.slimtrade.core.observing.GlobalKeyboardListener;
import main.java.com.slimtrade.core.observing.GlobalMouseListener;
import main.java.com.slimtrade.core.utility.ChatParser;
import main.java.com.slimtrade.core.utility.FileMonitor;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.core.utility.TradeUtility;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;
import main.java.com.slimtrade.gui.messaging.AbstractMessagePanel;
import main.java.com.slimtrade.gui.messaging.TradePanelA;

public class Main {

	// TODO : move to invoke later?
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	public static EventManager eventManager = new EventManager();
	public static SaveManager saveManager = new SaveManager();
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;
	public static Logger logger;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
			// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}

		// Move to independent file, make static to load faster
		BasicDialog loadWindow = new BasicDialog();
		loadWindow.setLayout(new GridBagLayout());
		loadWindow.add(new JLabel("Loading SlimTrade..."), new GridBagConstraints());
		loadWindow.setSize(200, 80);
		// loadWindow.setSize(418, 169);
		FrameManager.centerFrame(loadWindow);
		loadWindow.setVisible(true);

		// JNativeHook Setup
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		GlobalMouseListener globalMouse = new GlobalMouseListener();
		GlobalKeyboardListener globalKeyboard = new GlobalKeyboardListener();
		GlobalScreen.addNativeMouseListener(globalMouse);
		GlobalScreen.addNativeKeyListener(globalKeyboard);

		// Locale
		// debug.log("Default Localization : " + Locale.getDefault());
		// Locale.setDefault(Locale.US);

		ColorManager.setMessageTheme();

		// POE Interface

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Locale swede = new Locale("sv", "SE");
				Locale.setDefault(Locale.US);
				// SAVE MANAGER DEFAULTS

				ImagePreloader imagePreloader = new ImagePreloader();

				// Wait Button
				saveManager.putBoolDefault(true, "macroButtons", "waitButton", "enabled");
				saveManager.putBoolDefault(true, "macroButtons", "waitButton", "secondaryEnabled");
				saveManager.putStringDefault("one sec", "macroButtons", "waitButton", "textLMB");
				saveManager.putStringDefault("one min", "macroButtons", "waitButton", "textRMB");

				// Thank Button
				saveManager.putBoolDefault(true, "macroButtons", "thankButton", "enabled");
				saveManager.putBoolDefault(false, "macroButtons", "thankButton", "secondaryEnabled");
				saveManager.putStringDefault("thanks", "macroButtons", "thankButton", "textLMB");
				saveManager.putStringDefault("", "macroButtons", "thankButton", "textRMB");

				// Overlay
				saveManager.putIntDefault(0, "overlayManager", "menubar", "x");
				saveManager.putIntDefault(TradeUtility.screenSize.height - MenubarDialog.HEIGHT, "overlayManager", "menubar", "y");
				saveManager.putIntDefault(1220, "overlayManager", "messageManager", "x");
				saveManager.putIntDefault(0, "overlayManager", "messageManager", "y");

				saveManager.putStringDefault("Bottom Left", "overlayManager", "menubar", "buttonLocation");

				// saveManager.deleteArray("overlayManager");
				// saveManager.deleteArray("overlayManager", "menubar", "y");

				saveManager.saveToDisk();

				// ColorManager.setMessageTheme();
				try {
					PoeInterface poe = new PoeInterface();
				} catch (AWTException e) {
					e.printStackTrace();
				}

				// SaveManager saveManager = new SaveManager();
				FrameManager frameManager = new FrameManager();

				chatParser.init();
				fileMonitor = new FileMonitor();

				// TEMP
//				JFrame tempFrame = new JFrame();
//				tempFrame.setLayout(new GridBagLayout());
//				JPanel cont = new JPanel(new GridBagLayout());
//				cont.setPreferredSize(new Dimension(1100, 700));
//				cont.setBackground(Color.LIGHT_GRAY);
//				GridBagConstraints gc = new GridBagConstraints();
//
//				tempFrame.add(cont);
//				tempFrame.setSize(1200, 900);
//				gc.gridx = 0;
//				gc.gridy = 0;
//				for (int i = 30; i < 40; i += 2) {
//					TradePanelA msgPanel = new TradePanelA(i);
//					msgPanel.getCloseButton().addActionListener(new ActionListener() {
//						public void actionPerformed(ActionEvent arg0) {
//
//							cont.remove(msgPanel);
//							cont.revalidate();
//							cont.repaint();
//						}
//					});
//					gc.gridy++;
//					msgPanel.startTimer();
//					cont.add(msgPanel, gc);
//				}
//				// TODO : Move to overlay manager
//				JSlider slider = new JSlider();
//				slider.setMinorTickSpacing(1);
//				slider.setMajorTickSpacing(10);
//				slider.setMinimum(0);
//				slider.setMaximum(20);
//				slider.setPaintTicks(true);
//				slider.setSnapToTicks(true);
//				AbstractMessagePanel trade = new TradePanelA(40);
//				slider.addChangeListener(new ChangeListener() {
//					public void stateChanged(ChangeEvent arg0) {
//						for (Component c : cont.getComponents()) {
//							AbstractMessagePanel m = (AbstractMessagePanel)c;
//							m.stopTimer();
//							cont.remove(m);
//							m = null;
//						}
//						int value = slider.getValue();
//						// System.out.println(value);
//						gc.gridx = 0;
//						gc.gridy = 0;
//						trade.resizeMessage(30+value);
//						cont.add(new TradePanelA(30 + value), gc);
//						cont.revalidate();
//						cont.repaint();
//					}
//				});
//				
//				gc.gridy++;
//				tempFrame.add(trade, gc);
//				gc.gridy++;
//				tempFrame.add(slider, gc);
//				tempFrame.setVisible(true);

				// tempFrame.revalidate();
				// tempFrame.repaint();
				
				
				String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				for (int i = 0; i < fonts.length; i++) {
					System.out.println(i + "\t" + fonts[i]);
				}
				// GraphicsEnviroment.getGraphicsEnviroment() ge = new
				// GraphicsEnviroment.ge
				// tempFrame.pack();
//				

			}
		});

		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				closeProgram();
			}
		}));

		loadWindow.dispose();

	}

	public static void closeProgram() {
		saveManager.saveToDisk();
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		fileMonitor.stopMonitor();
		System.out.println("SlimTrade Terminated");
	}

}
