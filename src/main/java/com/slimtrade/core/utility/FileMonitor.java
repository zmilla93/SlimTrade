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
	private Thread monitor;

	public FileMonitor() {

		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// System.out.println(Main.saveManager.getClientPath());
		Path dir = Paths.get(Main.saveManager.getClientPath());
		Path testDir = Paths.get("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Path of Exile\\logs");
//		System.out.println(dir.toString());
		try {
			clientKey = testDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		startMonitor();

	}

	public void startMonitor() {
		Path dir = Paths.get(Main.saveManager.getClientPath());
		Path testDir = Paths.get("C:\\Program Files (x86)\\Steam\\steamapps\\common\\Path of Exile\\logs");
//		System.out.println(dir.toString());
		try {
			clientKey = testDir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		System.out.println("Staring watch thread...");
		monitor = new Thread(new Runnable() {
			public void run() {
				while (true) {
					WatchKey key;
					try {
						key = watcher.take();
					} catch (InterruptedException e) {
						try {
							watcher.close();
						} catch (IOException e2) {
							e2.printStackTrace();
						}
						break;
					}
					for (WatchEvent<?> event : key.pollEvents()) {
						Main.chatParser.update();
						if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
							System.err.println("Overflow");
							continue;
						}
					}
					key.reset();
				}
			}
		});
		monitor.start();
		
	}
	
	public void stopMonitor(){
		monitor.interrupt();
		try {
			monitor.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	
}
