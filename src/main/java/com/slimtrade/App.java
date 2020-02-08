package com.slimtrade;

import java.awt.AWTException;
import java.awt.Frame;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.*;

import com.slimtrade.core.managers.*;
import com.slimtrade.core.observing.GlobalKeyboardListener;
import com.slimtrade.core.observing.GlobalMouseListener;
import com.slimtrade.core.observing.MacroEventManager;
import com.slimtrade.core.observing.improved.EventManager;
import com.slimtrade.core.utility.ChatParser;
import com.slimtrade.core.utility.FileMonitor;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.UpdateChecker;
import com.slimtrade.debug.Debugger;
import com.slimtrade.enums.ColorTheme;
import com.slimtrade.gui.FrameManager;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.slimtrade.gui.dialogs.LoadingDialog;
import com.slimtrade.gui.windows.UpdateDialog;

public class App {
	
	// TODO : move to invoke later?
	public static Debugger debugger;
	public static FrameManager frameManager;
	public static MacroEventManager macroEventManager = new MacroEventManager();
	public static EventManager eventManager = new EventManager();
	public static SaveManager saveManager;
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;
	public static Logger logger = Logger.getLogger("slim");
	public static UpdateChecker updateChecker;
	public static GlobalKeyboardListener globalKeyboard;
	public static LoadingDialog loadingDialog;
//	public static ColorManager colorManager = new ColorManager();
//	public static SaveFile saveFile = new SaveFile();

	public static boolean debugMode = false;

    @SuppressWarnings("unused")
	public static void main(String[] args) {


        Date date = new Date();
        System.out.println(date);
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


		//Loading Dialog
		loadingDialog = new LoadingDialog();
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);

//		Runtime.getRuntime().traceMethodCalls(true);

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				//TODO : Load Color Theme
//                UIManager.put("")
				ColorManager.setTheme(ColorTheme.LIGHT_THEME);

                //Debug Mode
                if(debugMode){
                    debugger = new Debugger();
                    debugger.setState(Frame.ICONIFIED);
                }

                // Check for Updates
                updateChecker = new UpdateChecker();
//                updateChecker.checkForUpdates();

				
				Locale.setDefault(Locale.US);

				saveManager = new SaveManager();
				saveManager.loadFromDisk();
				saveManager.loadStashFromDisk();
				saveManager.loadOverlayFromDisk();



				// POE Interface
				try {
					PoeInterface poe = new PoeInterface();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				
				frameManager = new FrameManager();

				fileMonitor = new FileMonitor();
				fileMonitor.startMonitor();
                chatParser.init();

				
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
                // TODO : DEBUG OPTION
//				ClipboardManager clipboard = new ClipboardManager();

				// Alert about new update
                if(updateChecker.isUpdateAvailable()){
                    UpdateDialog d = new UpdateDialog();
                    d.setVisible(true);
                }


			}
		});


		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				closeProgram();
			}
		}));

		loadingDialog.dispose();
        System.out.println("SlimTrade launched!\n");

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
