package com.slimtrade.core.utility;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;
import com.slimtrade.core.enums.MacroButtonType;
import com.slimtrade.core.hotkeys.HotkeyData;

import java.util.ArrayList;

public class MacroButton {

    public MacroButtonType buttonType = MacroButtonType.ICON;
    public CustomIcon icon;
    public String text;
    public String lmbResponse;
    public String rmbResponse;
    public ButtonRow row;
    public HotkeyData hotkeyData;
    public boolean close;

    public MacroButton() {

    }

    // FIXME : Macro button is always null except for legacy importing?
    public MacroButton(CustomIcon icon, String lmbResponse, String rmbResponse, ButtonRow row, HotkeyData hotkeyData, boolean close) {
        this.icon = icon;
        this.lmbResponse = lmbResponse;
        this.rmbResponse = rmbResponse;
        this.row = row;
        this.close = close;
    }

    public static ArrayList<MacroButton> getRowMacros(ArrayList<MacroButton> inputMacros, ButtonRow row) {
        ArrayList<MacroButton> macros = new ArrayList<>();
        for (MacroButton macro : inputMacros) {
            if (macro.row == row) {
                macros.add(macro);
            }
        }
        return macros;
    }

}
