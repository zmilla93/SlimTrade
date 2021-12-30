package com.slimtrade.core.utility;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;

public class MacroButton {

    public enum MacroButtonType{ICON, TEXT}
    public MacroButtonType buttonType;
    public CustomIcon icon;
    public String text;
    public String lmbResponse;
    public String rmbResponse;
    public ButtonRow row;
    public boolean close;

}
