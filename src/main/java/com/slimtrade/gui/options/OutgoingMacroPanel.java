package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.enums.MessageType;
import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.utility.MacroButton;

public class OutgoingMacroPanel extends AbstractMacroOptionPanel implements ISavable {

    public OutgoingMacroPanel(){
        super(MessageType.OUTGOING_TRADE);
        App.saveManager.registerSavable(this);
    }

    @Override
    public void save() {
        App.saveManager.settingsSaveFile.outgoingMacroButtons = getMacros();
    }

    @Override
    public void load() {
        clearMacros();
        if(App.saveManager.settingsSaveFile.outgoingMacroButtons == null) return;
        for(MacroButton macro : App.saveManager.settingsSaveFile.outgoingMacroButtons){
            addMacro(macro);
        }
    }
}
