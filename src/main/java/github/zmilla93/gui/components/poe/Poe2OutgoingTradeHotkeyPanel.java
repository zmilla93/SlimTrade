package github.zmilla93.gui.components.poe;

import github.zmilla93.core.enums.ThemeColor;
import github.zmilla93.gui.components.ButtonWrapper;
import github.zmilla93.gui.components.ComponentPanel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.gui.options.AbstractOptionPanel;

import javax.swing.*;

/**
 * Workaround for Path of Exile no longer logging "To/From" part of whispers.
 */
public class Poe2OutgoingTradeHotkeyPanel extends AbstractOptionPanel {

    public final HotkeyButton hotkeyButton = new HotkeyButton();

    public Poe2OutgoingTradeHotkeyPanel() {
        super(false, false);
        addHeader("POE2 Outgoing Trade Detection");
        addComponent(new ResultLabel(ThemeColor.DENY, "POE2 broke incoming/outgoing whisper detection."));
        addComponent(new ResultLabel(ThemeColor.DENY, "Until this is fixed, you need to hold a hotkey when sending OUTGOING trades in POE2."));
        addComponent(new ComponentPanel(new JLabel("Outgoing Trade Hotkey"), new ButtonWrapper(hotkeyButton)));
    }

}
