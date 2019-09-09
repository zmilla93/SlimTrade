package com.slimtrade.core.utility;

import java.io.IOException;
import java.nio.file.ClosedWatchServiceException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.logging.Level;

import com.slimtrade.Main;

public class FileMonitor {

	private WatchService watcher;
	private WatchKey clientKey;
	private Thread monitor;

	public FileMonitor() {

	}

	public void startMonitor() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (!Main.saveManager.isValidClientPath()) {
			Main.logger.log(Level.WARNING, "No valid client path found");
			return;
		}

		Path dir = Paths.get(Main.saveManager.getClientDirectory());
		if (clientKey != null) {
			clientKey.cancel();
			clientKey = null;
		}
		System.out.println("CLIENT KEY ::: " + clientKey);
		try {
			// dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
			clientKey = dir.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
		} catch (IOException | ClosedWatchServiceException e) {
			e.printStackTrace();
			return;
		}
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
						// TODO : Check context before parsing
						if (event.context().toString().toLowerCase().equals("client.txt")) {
							Main.chatParser.update();
							if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
								System.err.println("Overflow");
								continue;
							}
						}
					}
					key.reset();
				}
			}
		});
		monitor.start();

	}

	public void stopMonitor() {
		try {
			monitor.interrupt();
			monitor.join();
			clientKey.cancel();
		} catch (InterruptedException | NullPointerException e) {
			// e.printStackTrace();
			return;
		}
	}

}
