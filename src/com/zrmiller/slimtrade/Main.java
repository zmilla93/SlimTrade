package com.zrmiller.slimtrade;

import java.awt.AWTException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.zrmiller.slimtrade.debug.Debugger;

public class Main {	
	
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		//JNativeHook Setup
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		logger.setUseParentHandlers(false);
		try {
			GlobalScreen.registerNativeHook();
		} catch (NativeHookException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GlobalMouseListener globalMouse = new GlobalMouseListener();
		GlobalKeyboardListener globalKeyboard = new GlobalKeyboardListener();
		GlobalScreen.addNativeMouseListener(globalMouse);
		GlobalScreen.addNativeKeyListener(globalKeyboard);
		
		//Locale
		debug.log("Default Localization : " + Locale.getDefault());
		Locale.setDefault(Locale.US);
		
		//POE Interface
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		//Frame Manager
		FrameManager frameManager = new FrameManager();
		
	}
	
	

}
