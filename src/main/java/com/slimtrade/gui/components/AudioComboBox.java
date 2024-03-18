package com.slimtrade.gui.components;

import com.slimtrade.core.audio.Sound;

import javax.swing.*;

public class AudioComboBox extends JComboBox<Sound> {

    public AudioComboBox() {
        setRenderer(new AudioListCellRenderer());
    }

}
