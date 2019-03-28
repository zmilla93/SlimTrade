package main.java.com.slimtrade.core;

import java.awt.AWTException;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

import com.sun.jna.platform.DesktopWindow;
import com.sun.jna.platform.WindowUtils;

import main.java.com.slimtrade.core.managers.ColorManager;
import main.java.com.slimtrade.core.managers.DefaultManager;
import main.java.com.slimtrade.core.managers.SaveManager;
import main.java.com.slimtrade.core.observing.ClipboardManager;
import main.java.com.slimtrade.core.observing.EventManager;
import main.java.com.slimtrade.core.observing.GlobalKeyboardListener;
import main.java.com.slimtrade.core.observing.GlobalMouseListener;
import main.java.com.slimtrade.core.utility.ChatParser;
import main.java.com.slimtrade.core.utility.FileMonitor;
import main.java.com.slimtrade.core.utility.PoeInterface;
import main.java.com.slimtrade.core.utility.UpdateChecker;
import main.java.com.slimtrade.debug.Debugger;
import main.java.com.slimtrade.gui.FrameManager;
import main.java.com.slimtrade.gui.ImagePreloader;
import main.java.com.slimtrade.gui.basic.BasicDialog;
import main.java.com.slimtrade.gui.windows.UpdateDialog;

public class Main {
	
	// TODO : move to invoke later?
	public static Debugger debug = new Debugger();
//	public static ExternalFileManager fileManager = new ExternalFileManager();
	public static EventManager eventManager = new EventManager();
//	public static OLD_SaveManager saveManagerOld;
	public static SaveManager saveManager;
	public static ChatParser chatParser = new ChatParser();
	public static FileMonitor fileMonitor;
	public static Logger logger = Logger.getLogger("slim");
	public static UpdateChecker updateChecker;
	public static GlobalKeyboardListener globalKeyboard;
	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		
		debug.setState(Frame.ICONIFIED);
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
//		try {
//			GlobalScreen.registerNativeHook();
//		} catch (NativeHookException e) {
//			e.printStackTrace();
//		}
//		GlobalMouseListener globalMouse = new GlobalMouseListener();
//		GlobalKeyboardListener globalKeyboard = new GlobalKeyboardListener();
//		GlobalScreen.addNativeMouseListener(globalMouse);
//		GlobalScreen.addNativeKeyListener(globalKeyboard);

		// Locale
		// debug.log("Default Localization : " + Locale.getDefault());
		// Locale.setDefault(Locale.US);

		ColorManager.setMessageTheme();

		
		
		// POE Interface

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				
				System.setProperty("sun.java2d.noddraw", "true");
				
				// Locale swede = new Locale("sv", "SE");
				Locale.setDefault(Locale.US);
				ImagePreloader imagePreloader = new ImagePreloader();
				
				//NEW SAVE MANAGER
				saveManager = new SaveManager();
				DefaultManager defaultManager = new DefaultManager();
				saveManager.saveToDisk();

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
				
				
				try {
					GlobalScreen.registerNativeHook();
				} catch (NativeHookException e) {
					e.printStackTrace();
				}
				GlobalMouseListener globalMouse = new GlobalMouseListener();
				globalKeyboard = new GlobalKeyboardListener();
				GlobalScreen.addNativeMouseListener(globalMouse);
				GlobalScreen.addNativeKeyListener(globalKeyboard);
				
				ClipboardManager clipboardManager = new ClipboardManager();
				
//				KeyboardListener kbd = new KeyboardListener();
//				SetupWindow tutorial = new SetupWindow();
//				tutorial.setShow(true);
				System.out.println("WINDOWS : ");
				for(DesktopWindow win : WindowUtils.getAllWindows(true)){
					System.out.println(win.getTitle());
					if(win.getTitle().contains("Path of Exile")){
						System.out.println(win.getLocAndSize());
					}
				}
				
				

			}
		});

		
		
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				closeProgram();
			}
		}));

		loadWindow.dispose();

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
