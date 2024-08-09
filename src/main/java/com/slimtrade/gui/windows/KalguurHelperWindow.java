package com.slimtrade.gui.windows;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.gui.kalguur.KalguurCalculatorPanel;
import com.slimtrade.gui.kalguur.KalguurTimerPanel;

import java.awt.*;

public class KalguurHelperWindow extends CustomDialog {

    private final KalguurCalculatorPanel calculatorPanel = new KalguurCalculatorPanel(this);
    private final KalguurTimerPanel timerPanel = new KalguurTimerPanel(this);

    public KalguurHelperWindow() {
        super("Kalguur");
        setMinimumSize(null);
        setResizable(false);

        setLayout(new BorderLayout());
        add(calculatorPanel, BorderLayout.NORTH);
        add(timerPanel, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(null);
        SaveManager.appStateSaveFile.registerSavableContainer(this);
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            calculatorPanel.clearInput();
            timerPanel.clearInput();
            calculatorPanel.getInputField().requestFocus();
        }
    }

}
