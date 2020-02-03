package com.slimtrade.core.managers;

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

import com.slimtrade.App;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.core.SaveConstants;
import com.slimtrade.core.utility.POEWindowInfo;
import com.slimtrade.core.utility.PoeInterface;

public class ClipboardManager {

	private Clipboard clipboard;
	private String clipboardText;
	private String previousText;

	private final static String tradeMessageString = "@(<.+> )?(.+) ((Hi, )?(I would|I'd) like to buy your ([\\d.]+)? ?(.+) (listed for|for my) ([\\d.]+)? ?(.+) in (\\w+( \\w+)?) ?([(]stash tab \\\")?((.+)\\\")?(; position: left )?(\\d+)?(, top )?(\\d+)?[)]?(.+)?)";

	public ClipboardManager() {
		clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		refreshFlavor();
		clipboard.addFlavorListener(new CustomFlavorListener());
	}

	private class CustomFlavorListener implements FlavorListener {
		public void flavorsChanged(FlavorEvent e) {
			boolean valid = true;
			POEWindowInfo poe = new POEWindowInfo();
			//Ignore if option is disabled
			if(App.saveManager.getBool(SaveConstants.General.QUICK_PASTE) == false){
				valid = false;
			}
			// Ignore if POE is not visible

            //TODO : ERROR invalid window handle
			else if (!poe.getIsOpen() || !poe.getIsVisible()) {
				App.logger.log(Level.WARNING, "POE is not visible");
				valid = false;
			}
			// Ignore if control is not being pressed
			else if (!App.globalKeyboard.isControlPressed()) {
				App.logger.log(Level.WARNING, "Control key not pressed");
				valid = false;
			} // Ignore non-strings and duplicates
			else {
				try {
					clipboardText = (String) clipboard.getData(DataFlavor.stringFlavor);
					if (clipboardText.equals(previousText)) {
						App.logger.log(Level.WARNING, "Ignoring duplicate");
						previousText = null;
						valid = false;
					} else {
						previousText = clipboardText;
					}
				} catch (UnsupportedFlavorException | IOException | IllegalStateException err) {
					valid = false;
				}
			}
			if (valid) {
				Matcher matcher = Pattern.compile(tradeMessageString).matcher(clipboardText);
				if (matcher.matches()) {
					PoeInterface.pasteWithFocus(clipboardText);
					FrameManager.showVisibleFrames();
//                    FrameManager.forceAllToTop();
				}
			}
			refreshFlavor();
		}
	}

	private void refreshFlavor() {
		// TODO : Throws IOException
		try {
			clipboard.setContents(clipboard.getContents(null), null);
		} catch (IllegalStateException err) {

		}
	}

}
