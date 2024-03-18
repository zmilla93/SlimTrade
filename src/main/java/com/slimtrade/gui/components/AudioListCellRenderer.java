package com.slimtrade.gui.components;

import com.slimtrade.core.audio.Sound;
import com.slimtrade.core.managers.AudioManager;
import com.slimtrade.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;

public class AudioListCellRenderer extends JPanel implements ListCellRenderer<Sound> {

    private final JLabel label = new JLabel();

    public AudioListCellRenderer() {
        setLayout(new GridBagLayout());
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Sound> list, Sound value, int index, boolean isSelected, boolean cellHasFocus) {
        removeAll();
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        label.setText(value.name);
        GridBagConstraints gc = ZUtil.getGC();
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        if (index == AudioManager.getPingCount() || index == AudioManager.getInbuiltCount()) {
            add(new JSeparator(), gc);
        }
        gc.gridy++;
        add(label, gc);
        return this;
    }

}
