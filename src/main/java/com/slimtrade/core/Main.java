package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import main.java.com.slimtrade.debug.Debugger;

public class Main {	
	
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		
		System.out.println(TradeUtility.getCurrencyType("test"));
		System.out.println(TradeUtility.getCurrencyType("alch"));
		System.out.println(TradeUtility.getCurrencyType("chaos"));
		
		
		System.out.println(System.getProperty("file.encoding"));

		
		//JNativeHook Setup
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
		
		//Locale
		debug.log("Default Localization : " + Locale.getDefault());
		Locale.setDefault(Locale.US);
		
		//POE Interface
		try {
			PoeInterface poe = new PoeInterface();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
		
		
		//Initialize Color Manager
		ColorManager.setMessageTheme();
		
//		SaveManager saveManager = new SaveManager();
//		saveManager.saveString("test", "RAD");
//		saveManager.saveString("test", "RAD1");
//		saveManager.saveString("test", "RAD2");
//		saveManager.saveString("test1", "RAD");
//		saveManager.saveInt("int", 2);
//		saveManager.saveDouble("double", 2.5);
//		saveManager.test();
		
//		System.out.println(saveManager.getNestedString("Sub", "test"));
		
//		System.out.println(saveManager.getInt("int"));
//		System.out.println(saveManager.getDouble("double"));
//		System.out.println(Thread.currentThread());
		//Frame Manager
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				SaveManager saveManager = new SaveManager();
				FrameManager frameManager = new FrameManager();
			}
		});
		
		
	}
	
	

}
