package com.slimtrade.core.observing;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.FlavorEvent;
import java.awt.datatransfer.FlavorListener;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.slimtrade.core.Main;
import com.slimtrade.core.audio.AudioManager;
import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.audio.SoundComponent;
import com.slimtrade.core.utility.POEWindowInfo;
import com.slimtrade.core.utility.PoeInterface;

public class OLD_ClipboardManager {

	private Clipboard clipboard;
	private String clipboardText;
	private static boolean ignore = false;
	public final static String tradeMessageString = "@(<.+> )?(.+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";
	private String previousText;
	
	public OLD_ClipboardManager() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		try {
			refreshFlavor();
		} catch (IOException e) {
			e.printStackTrace();
		}
		clipboard.addFlavorListener(new CustomFlavorListener());
	}
	
	//TODO : This throws an error, not sure what triggers it
	private void refreshFlavor() throws IOException{
		try{
			clipboard.setContents(clipboard.getContents(null), null);
		}catch(IllegalStateException err){
			
		}
	}
	
	private class CustomFlavorListener implements FlavorListener{
		public void flavorsChanged(FlavorEvent e) {
			System.out.println("Flavor Changed!!");
			POEWindowInfo poe = new POEWindowInfo();
			if(!poe.getIsOpen() || !poe.getIsVisible()){
				Main.logger.log(Level.WARNING, "POE is not visible");
				try{
					//TODO : ERROR IS THROWN HERE
					refreshFlavor();
				}catch(IOException err){
					AudioManager.playRaw(Sound.PING2, 0);
					err.printStackTrace();
				}
				return;
			}
			if(!Main.globalKeyboard.isControlPressed()){
				Main.logger.log(Level.WARNING, "NO CONTROL KEY");
				try{
					refreshFlavor();
				}catch(IOException err){
					err.printStackTrace();
					AudioManager.playRaw(Sound.PING2, 0);
				}
				return;
			}else if(previousText != null && clipboardText.equals(previousText)){
				previousText = null;
				try{
					refreshFlavor();
				}catch(IOException err){
					err.printStackTrace();
					AudioManager.playRaw(Sound.PING2, 0);
				}
				return;
			}
			boolean isText = false;
//			System.out.println(clipboard.getData(DataFlavor.));
			try {
				clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
				isText = true;
			} catch (UnsupportedFlavorException | IOException err) {
				
			}catch (IllegalStateException err){
				return;
			}
			if (clipboardText != null) {
				previousText = clipboardText;
				Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
				if(matcher.matches()){
					PoeInterface.pasteWithFocus(clipboardText);
				}
			}
			if(isText){
				try{
					refreshFlavor();
				}catch(IOException err){
					err.printStackTrace();
					AudioManager.play(SoundComponent.INCOMING_MESSAGE);
				}
			}
		}
	}

}
