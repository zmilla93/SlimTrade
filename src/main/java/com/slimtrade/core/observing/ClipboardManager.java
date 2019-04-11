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
import main.java.com.slimtrade.core.utility.POEWindowInfo;
import main.java.com.slimtrade.core.utility.PoeInterface;

public class ClipboardManager {

	private Clipboard clipboard;
	private String clipboardText;
	private String previousText;
	
	private int count = 0;

	public final static String tradeMessageString = "@(<.+> )?(.+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";

	public ClipboardManager() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		clipboard.addFlavorListener(new CustomFlavorListener());
	}

	private class CustomFlavorListener implements FlavorListener {
		public void flavorsChanged(FlavorEvent e) {
			count++;
			System.out.println("Flavor Changed : " + count);
			boolean valid = true;
			// Ignore if POE is not visible
			POEWindowInfo poe = new POEWindowInfo();
			if (!poe.getIsOpen() || !poe.getIsVisible()) {
				Main.logger.log(Level.WARNING, "POE is not visible");
				valid = false;
			}
			// Ignore if control is not being pressed
			if (!Main.globalKeyboard.isControlPressed()) {
				Main.logger.log(Level.WARNING, "Control key not pressed");
				valid = false;
			}
			// Ignore non-strings and duplicates
			try {
				clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
				if (clipboardText.equals(previousText)) {
					Main.logger.log(Level.WARNING, "Ignoring duplicate");
					previousText = null;
					valid = false;
				}else{
					previousText = clipboardText;
				}
			} catch (UnsupportedFlavorException | IOException | IllegalStateException err) {
				valid = false;
			}
			if (valid) {
				System.out.println("Valid...");
				Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
				if (matcher.matches()) {
					PoeInterface.pasteWithFocus(clipboardText);
				}
			}
			// Refresh
			// TODO : Throws IOException
			try {
				clipboard.setContents(clipboard.getContents(null), null);
			} catch (IllegalStateException err) {

			}
		}
	}

}
