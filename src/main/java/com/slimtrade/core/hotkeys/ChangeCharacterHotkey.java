package com.slimtrade.core.hotkeys;

import com.slimtrade.core.utility.TradeUtil;

public class ChangeCharacterHotkey implements IHotkeyAction {

    @Override
    public void execute() {
        TradeUtil.changeCharacterName();
        System.out.println("Change Char!");
    }

}
