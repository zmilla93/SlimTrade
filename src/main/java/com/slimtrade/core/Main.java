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
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.basic.BasicDialog;

public class Main {

	//TODO : move to invoke later?
	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	public static EventManager eventManager = new EventManager();
	public static SaveManager saveManager = new SaveManager();
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		//Move to independent file, make static to load faster
		BasicDialog loadWindow = new BasicDialog();
		loadWindow.setLayout(new GridBagLayout());
		loadWindow.add(new JLabel("Loading SlimTrade..."), new GridBagConstraints());
		loadWindow.setSize(200, 80);
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
//		debug.log("Default Localization : " + Locale.getDefault());
//		Locale.setDefault(Locale.US);

//		ColorManager.setMessageTheme();
		
		// POE Interface


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Locale.setDefault(Locale.US);
				ColorManager.setMessageTheme();
				try {
					PoeInterface poe = new PoeInterface();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				
//				SaveManager saveManager = new SaveManager();
				FrameManager frameManager = new FrameManager();
				
				chatParser.init();
				fileMonitor = new FileMonitor();
				
//				saveManager.exampleArray();
				
//				saveManager
				
//				saveManager.putString("corrupt", "Stash Overlay");
				saveManager.putString("green", "Stash Overlay", "color");
				saveManager.putString("40", "Stash Overlay", "size", "width");
				saveManager.putString("4564560", "Stash Overlay", "size", "height");
				
				
				saveManager.putString("simple value", "simple key");
//				saveManager.putString("what");
				System.out.println("VALUE : " + saveManager.getString("Stash Overlay", "size", "height"));
				
				saveManager.putInt(123, "Example Nest", "Array1", "Array2", "Array3", "Array4", "Array5", "cool key");
				saveManager.putInt(23, "simple key");
				
//				System.out.println("VALUE : " + saveManager.getString("Stash Overlay", "asdf", "what"));
//				System.out.println("VALUE : " + saveManager.getString("Stash Overlay", "size", "what"));
//				System.out.println("VALUE : " + saveManager.getString("Stash Overlay", "size"));
				
				String[] arr = new String[4];
				String[] arr2 = {"34", "test"};
				saveManager.putString("value", new String[]{"key 1", "key 2"});
				
				System.out.println("VALUE : " + saveManager.getInt("simple key"));
				
//				saveManager.putString("whoa", "Example Nest", "Array1", "Array2", "Array3", "Array4", "Array5", "cool key");
				int s = saveManager.getInt("Example Nest", "Array1", "Array2", "Array3", "Array4", "Array5", "cool key");
				System.out.println("NEST : " + s);
				
				System.out.println(saveManager.hasValue("Example Nest", "Array1", "Array2", "Array3", "Array4", "Array5", "cool key"));
				
				saveManager.putStringDefault("default387435", "defkey1", "defkey2", "defkey3");
				
				saveManager.saveToDisk();
//				saveManager.putString("whoa", "Example Nest", "Array1", "Array2", "Array3", "Array4", "Array5", "cool key");
				
//				JSONObject arrMain = new JSONObject();
//				JSONObject arrNested = new JSONObject();
//				
//				saveManager.saveString("t1", "test value");
//				try {
//					arrMain.put("String1", "simple string");
//					arrNested.put("nest1", "COOL!");
//					arrNested.put("nest2", "RAD!");
//					arrMain.put("Array1", arrNested);
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
				//TODO : overlay manager
//				OverlayManager r = new OverlayManager();
//				r.show();
				
			}
		});
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
	        public void run() {
	            closeProgram();
	        }
	    }));
		
		loadWindow.dispose();

	}
	
	public static void closeProgram(){
		try {
			GlobalScreen.unregisterNativeHook();
		} catch (NativeHookException e) {
			e.printStackTrace();
		}
		fileMonitor.stopMonitor();
		System.out.println("SlimTrade Terminated");
	}

}
