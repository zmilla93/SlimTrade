package main.java.com.slimtrade.core.utility;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import main.java.com.slimtrade.core.Main;

public class FileMonitor {

	private WatchService watcher;
	private WatchKey clientKey;

	public FileMonitor() {

		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println(Main.saveManager.getClientPath());
		Path dir = Paths.get(Main.saveManager.getClientPath());
		Path testDir = Paths.get("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Path of Exile\\logs");
		System.out.println(dir.toString());
		try {
			clientKey = testDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Staring watch thread...");

		startMonitor();

		// new Thread(new Runnable() {
		// public void run() {
		// while (true) {
		// WatchKey key;
		// System.out.println("Checking...");
		// try {
		// System.out.println("taking...");
		// key = watcher.take();
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// System.out.println("Checking 2...");
		//// key.reset();
		// boolean valid = key.reset();
		// System.out.println(valid);
		// try {
		// Thread.sleep(1000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		// }
		//
		// }
		// }).start();
	}

	void startMonitor() {
		Path dir = Paths.get(Main.saveManager.getClientPath());
		Path testDir = Paths.get("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Path of Exile\\logs");
		System.out.println(dir.toString());
		try {
			clientKey = testDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		System.out.println("Staring watch thread...");
		new Thread(new Runnable() {
			public void run() {
				while (true) {
					WatchKey key;
					try {
						key = watcher.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						continue;
					}
					for (WatchEvent<?> event : key.pollEvents()) {
						System.out.println(event.kind());
						Main.chatParser.update();
						if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
							System.out.println("Overflow");
							continue;
						}
					}
					key.reset();
					boolean valid = key.reset();
					System.out.println(valid);
				}
			}
		}).start();

		


	}
}
