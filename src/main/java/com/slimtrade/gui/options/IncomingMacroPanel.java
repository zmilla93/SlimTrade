package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.modules.saving.ISavable;

public class IncomingMacroPanel extends AbstractMacroOptionPanel implements ISavable {

    public IncomingMacroPanel() {
        super(TradeOfferType.INCOMING_TRADE);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.incomingMacroButtons = getMacros();
    }

    @Override
    public void load() {
        clearMacros();
        if (SaveManager.settingsSaveFile.data.incomingMacroButtons == null) return;
        for (MacroButton macro : SaveManager.settingsSaveFile.data.incomingMacroButtons) {
            addMacro(macro);
        }
    }
}
