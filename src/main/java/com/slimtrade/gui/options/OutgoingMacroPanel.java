package com.slimtrade.gui.options;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOfferType;
import com.slimtrade.core.utility.MacroButton;
import com.slimtrade.modules.saving.ISavable;

public class OutgoingMacroPanel extends AbstractMacroOptionPanel implements ISavable {

    public OutgoingMacroPanel() {
        super(TradeOfferType.OUTGOING_TRADE);
//        App.saveManager.registerSavable(this);
    }

    @Override
    public void save() {
        SaveManager.settingsSaveFile.data.outgoingMacroButtons = getMacros();
    }

    @Override
    public void load() {
        clearMacros();
        if (SaveManager.settingsSaveFile.data.outgoingMacroButtons == null) return;
        for (MacroButton macro : SaveManager.settingsSaveFile.data.outgoingMacroButtons) {
            addMacro(macro);
        }
    }
}
