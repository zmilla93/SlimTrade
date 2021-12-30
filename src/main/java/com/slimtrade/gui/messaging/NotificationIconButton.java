package com.slimtrade.gui.messaging;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class NotificationIconButton extends JButton {

    int ICON_INSET = 2;

    public NotificationIconButton(String path) {
        super();
        Image img = null;
        try {
            img = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        assert img != null;
        setIcon(new ImageIcon(img.getScaledInstance(18, 18, Image.SCALE_SMOOTH)));
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
//        setBorder(null);
//        setMargin(new Insets(ICON_INSET, ICON_INSET, ICON_INSET, ICON_INSET));
        setBorder(BorderFactory.createEmptyBorder(ICON_INSET, ICON_INSET, ICON_INSET, ICON_INSET));
    }

}
