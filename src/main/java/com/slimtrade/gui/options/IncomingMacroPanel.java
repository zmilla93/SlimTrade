package com.slimtrade.gui.options;

import com.slimtrade.App;
import com.slimtrade.core.saving.ISavable;
import com.slimtrade.core.utility.MacroButton;

public class IncomingMacroPanel extends AbstractMacroOptionPanel implements ISavable {

    public IncomingMacroPanel() {
        super();
        App.saveManager.registerSavable(this);
    }

    @Override
    public void save() {
        App.saveManager.settingsSaveFile.incomingMacroButtons = getMacros();
    }

    @Override
    public void load() {
        clearMacros();
        for(MacroButton macro : App.saveManager.settingsSaveFile.incomingMacroButtons){
            addMacro(macro);
        }
    }
}
