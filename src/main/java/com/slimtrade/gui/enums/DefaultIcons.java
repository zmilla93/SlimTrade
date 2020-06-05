package com.slimtrade.gui.enums;

import com.slimtrade.core.References;
import com.slimtrade.core.managers.ColorManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.Objects;

/**
 * Cached images used by the SlimTrade UI. These are intended for internal use only.
 * CustomIcons is for icons used by the user when custom buttons.
 */

public enum DefaultIcons implements ICacheImage {
    ARROW_DOWN("icons/default/arrow-downx48.png"),
    ARROW_UP("icons/default/arrow-upx48.png"),
    CLOSE("icons/default/closex64.png"),
    PLAY("icons/default/playx64.png"),
    TAG("icons/default/tagx64.png"),
    ;

    private Image image;
    private BufferedImage bufferedImage;
    private final String path;
    private int cachedSize = 0;
    private Color cachedColor;

    DefaultIcons(String path) {
        this.path = path;
    }

    public Image getImage() {
        return getImage(References.DEFAULT_IMAGE_SIZE);
    }

    public Image getImage(int size) {
        if (image == null || size != cachedSize) {
            image = new ImageIcon(Objects.requireNonNull(this.getClass().getClassLoader().getResource(path))).getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);


            cachedSize = size;
        }
        return image;
    }

    @Override
    public Image getColorImage(Color color) {

        if (image == null) {
            getImage(References.DEFAULT_IMAGE_SIZE);
        }

        if (bufferedImage == null || color != cachedColor) {

            bufferedImage = new BufferedImage(cachedSize, cachedSize, BufferedImage.TYPE_INT_ARGB);
            Graphics2D bGr = bufferedImage.createGraphics();
            bGr.drawImage(image, 0, 0, null);
            bGr.dispose();
            bufferedImage = colorImage(bufferedImage);

            cachedColor = color;
        }

        return bufferedImage;
    }

    private static BufferedImage colorImage(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        WritableRaster raster = image.getRaster();

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {
                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
                pixels[0] = ColorManager.TEXT.getRed();
                pixels[1] = ColorManager.TEXT.getGreen();
                pixels[2] = ColorManager.TEXT.getBlue();
                raster.setPixel(xx, yy, pixels);
            }
        }
        return image;
    }

}
