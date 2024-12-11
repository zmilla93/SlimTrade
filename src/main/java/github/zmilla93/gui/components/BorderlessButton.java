package github.zmilla93.gui.components;

import github.zmilla93.modules.theme.components.AdvancedButton;

public class BorderlessButton extends AdvancedButton {

    public BorderlessButton() {
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBorder(null);
    }

}
