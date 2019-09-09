package com.slimtrade.core;

import java.awt.AWTException;
import java.awt.Frame;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import com.slimtrade.core.managers.ClipboardManager;
import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.managers.DefaultManager;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.observing.GlobalKeyboardListener;
import com.slimtrade.core.observing.GlobalMouseListener;
import com.slimtrade.core.observing.REDO_MacroEventManager;
import com.slimtrade.core.observing.improved.EventManager;
import com.slimtrade.core.utility.ChatParser;
import com.slimtrade.core.utility.FileMonitor;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.UpdateChecker;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.ColorThemeType;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.ImagePreloader;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.slimtrade.gui.dialogs.LoadingDialog;
import com.slimtrade.gui.windows.UpdateDialog;

public class Main {
	
	// TODO : move to invoke later?
	public static Debugger debugger;
	public static REDO_MacroEventManager macroEventManager = new REDO_MacroEventManager();
	public static EventManager eventManager = new EventManager();
	public static SaveManager saveManager;
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;
	public static Logger logger = Logger.getLogger("slim");
	public static UpdateChecker updateChecker;
	public static GlobalKeyboardListener globalKeyboard;
	public static LoadingDialog loadingDialog;
	public static ColorManager colorManager = new ColorManager();

	public static boolean debugMode = false;

    @SuppressWarnings("unused")
	public static void main(String[] args) {

		// Command line args
		if(args.length>0){
			for(String s : args){
				switch (s){
					case "debug":
						debugMode = true;
						break;
				}
			}
		}
		//Debug Mode
		if(debugMode){
			debugger = new Debugger();
			debugger.setState(Frame.ICONIFIED);
		}

		//Loading Dialog
		loadingDialog = new LoadingDialog();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);

//		Runtime.getRuntime().traceMethodCalls(true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//TODO : Load Color Theme
//				ColorManager.setTheme(ColorThemeType.DARK_THEME);
				
				
				Locale.setDefault(Locale.US);
				
				//TODO : Remove image preloader
				ImagePreloader imagePreloader = new ImagePreloader();
				
				saveManager = new SaveManager();
				DefaultManager defaultManager = new DefaultManager();
				saveManager.saveToDisk();
				ColorManager.setTheme(ColorThemeType.valueOf(saveManager.getEnumValue(ColorThemeType.class, SaveConstants.General.COLOR_THEME)));
				// POE Interface
				try {
					PoeInterface poe = new PoeInterface();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				
				FrameManager frameManager = new FrameManager();
				chatParser.init();
				fileMonitor = new FileMonitor();
				fileMonitor.startMonitor();
				
				updateChecker = new UpdateChecker();
				if(updateChecker.checkForUpdate()){
					UpdateDialog d = new UpdateDialog();
					d.setVisible(true);
				}

				
				// JNativeHook Setup
				try {
					GlobalScreen.registerNativeHook();
				} catch (NativeHookException e) {
					e.printStackTrace();
				}
				GlobalMouseListener globalMouse = new GlobalMouseListener();
				globalKeyboard = new GlobalKeyboardListener();
				GlobalScreen.addNativeMouseListener(globalMouse);
				GlobalScreen.addNativeKeyListener(globalKeyboard);
				
				//Clipboard listener for fast paste
				ClipboardManager clipboard = new ClipboardManager();

			}
		});

		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				closeProgram();
			}
		}));

		loadingDialog.dispose();

	}
	
	private static void closeProgram() {
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		fileMonitor.stopMonitor();
		System.out.println("SlimTrade Terminated");
	}

}
