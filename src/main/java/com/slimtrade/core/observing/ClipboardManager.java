package main.java.com.slimtrade.core.observing;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.PoeInterface;

public class ClipboardManager {

	private Clipboard clipboard;
	private String clipboardText;
	private static boolean ignore = true;
	public final static String tradeMessageString = "@(<.+> )?(.+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
	private String previousText;
	
	public ClipboardManager() {
		
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
//		refreshListener();
//		addListener();
		try{
			clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
			clipboard.setContents(new StringSelection(""), null);
		}catch(IllegalStateException | UnsupportedFlavorException | IOException err){
			
		}
		clipboard.addFlavorListener(new FlavorListener() {
			public void flavorsChanged(FlavorEvent e) {
				if(!Main.globalKeyboard.isControlPressed()){
					Main.logger.log(Level.WARNING, "NO CONTROL KEY");
					refreshFlavor();
					return;
				}
				boolean isText = false;
				try {
					clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
					isText = true;
				} catch (UnsupportedFlavorException | IOException err) {
					
				}catch (IllegalStateException err){
					return;
				}
				if (clipboardText != null && !clipboardText.equals(previousText)) {
					previousText = clipboardText;
					Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
					if(matcher.matches()){
						PoeInterface.pasteWithFocus(clipboardText);
					}
				}
				if(isText){
					refreshFlavor();
				}
			}
		});
	}
	
	private void refreshFlavor(){
		try{
			clipboard.setContents(new StringSelection(clipboardText), null);
		}catch(IllegalStateException err){
			
		}
	}
	
	private void refreshListener(){
		System.out.println("Refreshing clipboard");

		clipboard.addFlavorListener(new FlavorListener(){
			public void flavorsChanged(FlavorEvent e) {

			}
		});
	}
	
	private void addListener(){
		clipboard.addFlavorListener(new CustomFlavorListener());
	}
	
	private void removeListener(){
		for(FlavorListener l : clipboard.getFlavorListeners()){
			clipboard.removeFlavorListener(l);
		}
	}
	
	private class CustomFlavorListener implements FlavorListener{
		public void flavorsChanged(FlavorEvent e) {
			System.out.println("Clipboard Action Detected");
			boolean isText = false;
			try {
				clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
				isText = true;
			} catch (UnsupportedFlavorException | IOException err) {
				err.printStackTrace();
			}catch (IllegalStateException err){
				err.printStackTrace();
				return;
			}
			if (clipboardText != null) {
				Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
				if(matcher.matches()){
					PoeInterface.pasteWithFocus(clipboardText);
				}
			}
			if(isText){
				removeListener();
				try {
					clipboard.setContents(new StringSelection(clipboardText), null);
				} catch (IllegalStateException err) {

				}
//				addListener();
			}
		}
	}

}
