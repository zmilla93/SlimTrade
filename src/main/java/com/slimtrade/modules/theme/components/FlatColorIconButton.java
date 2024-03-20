package com.slimtrade.modules.theme.components;

import com.formdev.flatlaf.ui.FlatButtonBorder;
import com.slimtrade.modules.updater.ZLogger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.Objects;

@Deprecated
public class FlatColorIconButton extends JButton {

    private Image original;
    private BufferedImage bufferedImage;
    private final String path;

    private Icon icon;
    private int size = 20;

    public FlatColorIconButton(String path) {
        this.path = path.startsWith("/") ? path : "/" + path;
        if (!getImageFromFile()) {
            ZLogger.err("[IconButton] File not found: /resources" + path);
            ZLogger.err("[IconButton] If this file exists, try cleaning and rebuilding the project.");
            return;
        }
        createBufferedImage();
        updateUI();
    }

    private boolean getImageFromFile() {
        if (original == null) {
            try {
                original = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
                return true;
            } catch (IOException | NullPointerException e) {
                return false;
            }
        }
        return false;
    }

    public void createBufferedImage() {
        bufferedImage = new BufferedImage(original.getWidth(null), original.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D bGr = bufferedImage.createGraphics();
        bGr.drawImage(original, 0, 0, null);
        bGr.dispose();
    }

    private void recolorBufferedImage() {
        int width = bufferedImage.getWidth();
        int height = bufferedImage.getHeight();
        WritableRaster raster = bufferedImage.getRaster();
        Color textColor = UIManager.getColor("Label.foreground");
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int[] pixels = raster.getPixel(x, y, (int[]) null);
                pixels[0] = textColor.getRed();
                pixels[1] = textColor.getGreen();
                pixels[2] = textColor.getBlue();
                raster.setPixel(x, y, pixels);
            }
        }
    }

    @Override
    public void updateUI() {
        super.updateUI();
        if (original == null) return;
        recolorBufferedImage();
        icon = new ImageIcon(bufferedImage.getScaledInstance(size, size, Image.SCALE_SMOOTH));
        setIcon(icon);
        int margin = 0;
        setMargin(new Insets(margin, margin, margin, margin));
        setBorder(new FlatButtonBorder());
    }

}
