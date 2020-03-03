package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;

public class ToggleButton extends BasicButton {

    private boolean state = false;
    private String text;
    private String textSecondary;

    public ToggleButton(String text, String textSecondary) {
        super(text);
        this.text = text;
        this.textSecondary = textSecondary;
//        this.addActionListener(e -> {
//            state = !state;
//            updateColor();
//        });

    }

    public void setState(boolean state) {
        this.state = state;
        this.updateColor();
    }

    @Override
    public void updateColor() {
        super.updateColor();
        if(state) {
            primaryColor = ColorManager.RED_DENY;
            this.setText(textSecondary);
        } else {
            primaryColor = ColorManager.GREEN_APPROVE;
            this.setText(text);
        }

    }

}
