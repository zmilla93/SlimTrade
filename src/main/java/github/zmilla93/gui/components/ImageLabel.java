package github.zmilla93.gui.components;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * Used to easily display an image from the resource folder.
 */
public class ImageLabel extends JLabel {

    private final int BORDER_SIZE;
    private final boolean colorBackground;

    public ImageLabel(String path) {
        this(path, 0, false);
    }

    public ImageLabel(String path, int borderSize) {
        this(path, borderSize, false);
    }

    public ImageLabel(String path, int borderSize, boolean colorBackground) {
        setIcon(new ImageIcon(Objects.requireNonNull(getClass().getResource(path))));
        this.BORDER_SIZE = borderSize;
        this.colorBackground = colorBackground;
        if (colorBackground) setOpaque(true);
        updateUI();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (BORDER_SIZE <= 0) return;
        Color color = UIManager.getColor("Separator.foreground");
        setBorder(BorderFactory.createLineBorder(color, BORDER_SIZE));
        if (colorBackground) setBackground(color);
    }

}
