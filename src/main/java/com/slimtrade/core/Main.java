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
import main.java.com.slimtrade.gui.windows.OverlayManager;

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
				
//				OverlayManager r = new OverlayManager();
//				r.show();
				
			}
		});

	}

}
