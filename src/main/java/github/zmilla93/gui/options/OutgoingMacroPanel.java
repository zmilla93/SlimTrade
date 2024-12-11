package github.zmilla93.gui.options;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.modules.saving.ISavable;

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
