package com.slimtrade.core.data;

import com.slimtrade.core.utility.PoeInterface;

public class PoeHotkey  implements IHotkeyAction{

    String data;

    public PoeHotkey(String data){
        this.data = data;
    }

    @Override
    public void execute() {
        PoeInterface.paste(data);
    }
}
