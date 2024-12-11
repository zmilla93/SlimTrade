package github.zmilla93.gui.options;

import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.trading.TradeOfferType;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.modules.saving.ISavable;

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
