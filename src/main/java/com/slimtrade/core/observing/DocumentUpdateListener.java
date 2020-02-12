package com.slimtrade.core.observing;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class DocumentUpdateListener implements DocumentListener {

    @Override
    public void insertUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        update();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        update();
    }

    public void update(){

    }

}
