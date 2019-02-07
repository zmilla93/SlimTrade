package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

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
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.menubar.MenubarDialog;

public class Main {

	// TODO : move to invoke later?
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	public static EventManager eventManager = new EventManager();
	public static SaveManager saveManager = new SaveManager();
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;

	@SuppressWarnings("unused")
	public static void main(String[] args) {

		// Move to independent file, make static to load faster
		BasicDialog loadWindow = new BasicDialog();
		loadWindow.setLayout(new GridBagLayout());
		loadWindow.add(new JLabel("Loading SlimTrade..."), new GridBagConstraints());
		loadWindow.setSize(200, 80);
//		loadWindow.setSize(418, 169);
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
//				Locale swede = new Locale("sv", "SE");
				Locale.setDefault(Locale.US);

				// SAVE MANAGER DEFAULTS
				
				// Wait Button
				saveManager.putBoolDefault(true, "macroButtons", "waitButton", "enabled");
				saveManager.putBoolDefault(true, "macroButtons", "waitButton", "secondaryEnabled");
				saveManager.putStringDefault("one sec", "macroButtons", "waitButton", "textLMB");
				saveManager.putStringDefault("one min", "macroButtons", "waitButton", "textRMB");

				//Thank Button
				saveManager.putBoolDefault(true, "macroButtons", "thankButton", "enabled");
				saveManager.putBoolDefault(false, "macroButtons", "thankButton", "secondaryEnabled");
				saveManager.putStringDefault("thanks", "macroButtons", "thankButton", "textLMB");
				saveManager.putStringDefault("", "macroButtons", "thankButton", "textRMB");
				
				//Overlay
				saveManager.putIntDefault(0, "overlayManager", "menubar", "x");
				saveManager.putIntDefault(TradeUtility.screenSize.height-MenubarDialog.TOTAL_HEIGHT, "overlayManager", "menubar", "y");
				saveManager.putIntDefault(1220, "overlayManager", "messageManager", "x");
				saveManager.putIntDefault(0, "overlayManager", "messageManager", "y");
				
				saveManager.putStringDefault("Bottom Left", "overlayManager", "menubar", "buttonLocation");
				
//				saveManager.saveToDisk();

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
