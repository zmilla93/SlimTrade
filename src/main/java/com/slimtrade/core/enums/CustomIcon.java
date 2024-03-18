package com.slimtrade.core.enums;

import com.slimtrade.gui.buttons.IIcon;

import java.awt.image.BufferedImage;

public enum CustomIcon implements IIcon {

    CANCEL("/icons/custom/cancelx48.png"),
    REPLY("/icons/custom/replyx48.png"),
    BOOKMARK("/icons/custom/bookmarkx48.png"),
    MAP("/icons/custom/mapx64.png"),
    BEAKER("/icons/custom/beakerx48.png"),
    FLOW2("/icons/custom/flow-switchx48.png"),
    MAIL1("/icons/custom/mailx64.png"),
    WATCH("/icons/custom/watchx64.png"),
    CLOCK("/icons/custom/clockx64.png"),

    CART("/icons/custom/cartx64.png"),
    HOME("/icons/custom/homex64.png"),
    INVITE("/icons/custom/invitex48.png"),
    LEAVE("/icons/custom/leavex64.png"),
    REFRESH("/icons/custom/refreshx64.png"),
    THUMB("/icons/custom/thumbx64.png"),
    WARP("/icons/custom/warpx64.png"),
    ;

    // FIXME : Move to color manager
    public final String path;
    private BufferedImage image;

    CustomIcon(String path) {
        this.path = path;
    }

    @Override
    public String path() {
        return path;
    }

//    public Image getImage() {
//        if (image == null) {
//            try {
//                image = ImageIO.read(Objects.requireNonNull(getClass().getResource(path)));
//                return image;
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return null;
//    }

//    public Image getColorImage(Color color) {
//        System.out.println("COL");
//        getImage();
//        int width = image.getWidth();
//        int height = image.getHeight();
//        WritableRaster raster = image.getRaster();
//        for (int xx = 0; xx < width; xx++) {
//            for (int yy = 0; yy < height; yy++) {
//                int[] pixels = raster.getPixel(xx, yy, (int[]) null);
//                pixels[0] = color.getRed();
//                pixels[1] = color.getGreen();
//                pixels[2] = color.getBlue();
//                raster.setPixel(xx, yy, pixels);
//            }
//        }
//        return image;
//    }
//
//    public ImageIcon getColorIcon(Color color, int size) {
//        return new ImageIcon(getColorImage(color).getScaledInstance(size, size, Image.SCALE_SMOOTH));
//    }

}
