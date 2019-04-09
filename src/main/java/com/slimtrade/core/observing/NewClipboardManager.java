package main.java.com.slimtrade.core.observing;

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

import main.java.com.slimtrade.core.Main;
import main.java.com.slimtrade.core.utility.PoeInterface;

public class NewClipboardManager {

	private Clipboard clipboard;
	private String clipboardText;
	public final static String tradeMessageString = "@(<.+> )?(.+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";

	public NewClipboardManager() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.addFlavorListener(new CustomFlavorListener());
	}


	private class CustomFlavorListener implements FlavorListener {
		public void flavorsChanged(FlavorEvent e) {
			System.out.println("Flavor Changed!!");
			boolean valid = true;
//			POEWindowInfo poe = new POEWindowInfo();
//			// Ignore if POE is not visible
//			if (!poe.getIsOpen() || !poe.getIsVisible()) {
//				Main.logger.log(Level.WARNING, "POE is not visible");
//				valid = false;
//			}
			// Ignore if control is not being pressed
			if (!Main.globalKeyboard.isControlPressed()) {
				Main.logger.log(Level.WARNING, "Control key not pressed");
				valid = false;
			}
			if(valid){
				System.out.println("Valid...");
				try{
					clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
					Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
					if(matcher.matches()){
						PoeInterface.pasteWithFocus(clipboardText);
					}
				}catch(UnsupportedFlavorException | IOException | IllegalStateException err){
					
				}
			}
			//Refresh
			clipboard.setContents(clipboard.getContents(null), null);
		}
	}

}
