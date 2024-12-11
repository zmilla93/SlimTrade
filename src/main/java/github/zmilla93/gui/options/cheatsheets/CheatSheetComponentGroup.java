package github.zmilla93.gui.options.cheatsheets;

import github.zmilla93.core.data.CheatSheetData;
import github.zmilla93.gui.components.HotkeyButton;

import javax.swing.*;

public class CheatSheetComponentGroup {

    private final CheatSheetData data;
    public final JLabel label;
    public final HotkeyButton hotkeyButton;

    public CheatSheetComponentGroup(CheatSheetData data) {
        this.data = data;
        this.label = new JLabel(data.title());
        this.hotkeyButton = new HotkeyButton();
        hotkeyButton.setData(data.hotkeyData);
    }

    public CheatSheetData getData() {
        return new CheatSheetData(data.fileName, hotkeyButton.getData());
    }

}
