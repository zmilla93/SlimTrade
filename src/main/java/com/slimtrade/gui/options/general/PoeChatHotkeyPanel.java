package com.slimtrade.gui.options.general;

import com.slimtrade.core.jna.JnaAwtEvent;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.utility.GUIReferences;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ButtonWrapper;
import com.slimtrade.gui.components.ErrorLabel;
import com.slimtrade.gui.components.HotkeyButton;
import com.slimtrade.modules.saving.ISavable;

import javax.swing.*;
import java.awt.*;

public class PoeChatHotkeyPanel extends JPanel implements ISavable {

    private final HotkeyButton poeChatHotkey = new HotkeyButton();
    private final JLabel poeChatHotkeyErrorLabel = new ErrorLabel();

    public PoeChatHotkeyPanel() {
        poeChatHotkeyErrorLabel.setVisible(false);
        setLayout(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        add(new JLabel("POE's Open Chat Hotkey"));
        gc.insets.left = GUIReferences.HORIZONTAL_INSET_SMALL;
        gc.gridx++;
        add(new ButtonWrapper(poeChatHotkey), gc);
        gc.gridx++;
        add(poeChatHotkeyErrorLabel, gc);
        addListeners();
    }

    private void addListeners() {
        poeChatHotkey.addHotkeyChangeListener(data -> {
            int key = JnaAwtEvent.hotkeyToEvent(data);
            if (data != null && key == JnaAwtEvent.INVALID_HOTKEY) {
                setError("Unsupported hotkey. Use a different hotkey or report a bug with KeyCodeError[" + data + ":" + data.keyCode + "].");
            } else {
                setError(null);
            }
        });
    }

    private void setError(String error) {
        poeChatHotkeyErrorLabel.setVisible(error != null);
        poeChatHotkeyErrorLabel.setText(error);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.poeChatHotkey = poeChatHotkey.getData();
    }

    @Override
    public void load() {
        poeChatHotkey.setData(SaveManager.settingsSaveFile.data.poeChatHotkey);
    }

}
