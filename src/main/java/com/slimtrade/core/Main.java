package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.SwingUtilities;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.json.JSONException;
import org.json.JSONObject;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.managers.ExternalFileManager;
import main.java.com.slimtrade.core.managers.SaveManager;
import main.java.com.slimtrade.core.observing.EventManager;
import main.java.com.slimtrade.core.observing.GlobalKeyboardListener;
import main.java.com.slimtrade.core.observing.GlobalMouseListener;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.dialogs.ResizeOverlay;

public class Main {

	public static Debugger debug = new Debugger();
	public static ExternalFileManager fileManager = new ExternalFileManager();
	public static EventManager eventManager = new EventManager();
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
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
		debug.log("Default Localization : " + Locale.getDefault());
		Locale.setDefault(Locale.US);

		// POE Interface


		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				try {
					PoeInterface poe = new PoeInterface();
				} catch (AWTException e) {
					e.printStackTrace();
				}
				ColorManager.setMessageTheme();
				SaveManager saveManager = new SaveManager();
				FrameManager frameManager = new FrameManager();
				
				
				JSONObject arrMain = new JSONObject();
				JSONObject arrNested = new JSONObject();
				
				saveManager.saveString("t1", "test value");
				try {
					arrMain.put("String1", "simple string");
					arrNested.put("nest1", "COOL!");
					arrNested.put("nest2", "RAD!");
					arrMain.put("Array1", arrNested);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//				saveManager.saveArray("Parent", arrMain);
				
//				JSONObject arr = saveManager.getArray("Parent");
//				try {
//					JSONObject subArr = (JSONObject) arr.get("Array1");
//					System.out.println(subArr.get("nest2"));
//					System.out.println(arr.get("Array1"));
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				
//				saveManager.saveStringProper("basic str", "baaaasic");
//				saveManager.saveStringProper("arr", "arr2", "value");
//				saveManager.saveStringProper("arr", "arr2", "arr3", "value");
//				saveManager.saveString("t1", "testvalue");
//				
//				saveManager.saveString("Arr", "key2", "vaal2");
//				saveManager.saveStringProper("ARR1", "ARR2", "KEY", "VALUE");
//				saveManager.saveString("arr", "nest1", "nest2", "key", "value");
//				
//				saveManager.saveInteger(1123, "int test");
//				saveManager.saveInteger(345, "int array", "nest1", "nest2");
				
//				saveManager.saveDouble(345.345, "basic double");
//				saveManager.saveDouble(123.123, "arr double", "nest1", "nest2", "nest3");
				
//				System.out.println(saveManager.getInteger("int test"));
//				System.out.println(saveManager.getInteger("int array", "int test"));
//				System.out.println(saveManager.getDouble("basic double"));
//				System.out.println(saveManager.getDouble("arr double", "nest1", "nest2", "nest3"));
				
				
//				saveManager.saveStringProper("ARR1", "ARR2", "KEY2", "VALUE2");
				
//				System.out.println(saveManager.getStringProper("Parent", "Array1", "nest1"));
//				System.out.println(saveManager.getStringProper("Parent", "String1"));
//				System.out.println(saveManager.getStringProper("t1"));
				
				ResizeOverlay r = new ResizeOverlay();
				r.show();
				
			}
		});

	}

}
