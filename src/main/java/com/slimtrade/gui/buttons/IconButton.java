package com.slimtrade.gui.buttons;

import com.slimtrade.core.managers.ColorManager;
import com.slimtrade.core.observing.improved.IColorable;
import com.slimtrade.gui.enums.DefaultIcons;
import com.slimtrade.gui.enums.ICacheImage;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class IconButton extends JButton implements IColorable {

	private final int DEFAULT_SIZE = 30;
	private final double IMAGE_SCALE = 0.94;

//	public Color colorDefault = Color.GRAY;
	public Color colorDefault = ColorManager.LOW_CONTRAST_1;
	public Color colorHover = ColorManager.PRIMARY;
	public Color colorPressed = ColorManager.BACKGROUND;

	public Border borderDefault = BorderFactory.createEmptyBorder(1, 1, 1, 1);
	public Border borderHover = BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK);
	public Border borderPressed = borderHover;

	private Image image;
	private BufferedImage bufferedImage;

	private ICacheImage cacheImage;

//	public IconButton(Image image, int size){
//		buildButton(image, size);
//	}

	public IconButton(ICacheImage image, int size) {
		this.cacheImage = image;
		this.setPreferredSize(new Dimension(size, size));
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(borderDefault);
//		this.setIcon(new ImageIcon(image));


		final IconButton localButton = this;
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				localButton.getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				localButton.getModel().setPressed(false);
			}
		});

		updateColor();
	}

	
	private void buildButton(Image image, int size){
		if(cacheImage == null) {
			cacheImage = DefaultIcons.THUMB;
		}
		this.image = image;
		this.setPreferredSize(new Dimension(size, size));
		this.setContentAreaFilled(false);
		this.setFocusable(false);
		this.setBorder(borderDefault);

		bufferedImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bufferedImage.createGraphics();
		bGr.drawImage(image, 1, 1, null);
		bGr.dispose();
		this.setIcon(new ImageIcon(cacheImage.getColorImage(ColorManager.TEXT)));
//		bufferedImage = colorImage(bufferedImage);

//		image = bufferedImage;

//		this.setIcon(new ImageIcon(image));
		this.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e) {
				getModel().setPressed(true);
			}

			public void mouseReleased(MouseEvent e) {
				getModel().setPressed(false);
			}
		});

//		updateColor();
	}

	//TODO : Switch to normal background stuff??
	protected void paintComponent(Graphics g) {
		if (getModel().isPressed()) {
			g.setColor(colorPressed);
			this.setBorder(borderPressed);
		} else if (getModel().isRollover()) {
			g.setColor(colorHover);
			this.setBorder(borderHover);
		} else {
			g.setColor(colorDefault);
			this.setBorder(borderDefault);
		}
		g.fillRect(0, 0, getWidth(), getHeight());
		super.paintComponent(g);
	}


    @Override
    public void updateColor() {
        colorDefault = ColorManager.LOW_CONTRAST_1;
        colorHover = ColorManager.LOW_CONTRAST_2;
        colorPressed = ColorManager.HIGH_CONTRAST_1;
//        this.setIcon(new ImageIcon(colorImage(bufferedImage)));
		this.setIcon(new ImageIcon(cacheImage.getColorImage(ColorManager.TEXT)));
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
