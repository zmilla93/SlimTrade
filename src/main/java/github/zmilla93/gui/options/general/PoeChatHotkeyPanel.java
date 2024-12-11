package github.zmilla93.gui.options.general;

import github.zmilla93.core.jna.JnaAwtEvent;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.utility.GUIReferences;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.ButtonWrapper;
import github.zmilla93.gui.components.ErrorLabel;
import github.zmilla93.gui.components.HotkeyButton;
import github.zmilla93.modules.saving.ISavable;

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
