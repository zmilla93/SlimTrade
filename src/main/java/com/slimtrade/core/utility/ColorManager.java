package com.slimtrade.core.utility;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;

public class ColorManager {

    private static List<JFrame> frames = new ArrayList<>();
    private static ColorTheme currentTheme;

//    public static final Color CONFIRM_COLOR = new Color(58, 150, 47, 255);
    public static final Color CONFIRM_COLOR =  new Color(73, 156, 84);
//    public static final Color DENY_COLOR = new Color(154, 75, 75, 255);
    public static final Color DENY_COLOR = new Color(199, 84, 80);
    public static final Color GREEN_SALE = new Color(0, 130, 0);
    public static final Color RED_SALE = new Color(130, 0, 0);


    private static HashMap<String, ImageIcon> imageMap = new HashMap<>();

    public static boolean addFrame(JFrame frame) {
        if (!frames.contains(frame)) {
            return frames.add(frame);
        }
        return true;
    }

    public static boolean removeFrame(JFrame frame) {
        return frames.remove(frame);

    }

    public static void setTheme(ColorTheme theme) {
        setTheme(theme, false);
    }

    public static void setTheme(ColorTheme theme, boolean forceRefresh) {
        if(theme == currentTheme && !forceRefresh) return;
        currentTheme = theme;
        try {
            UIManager.setLookAndFeel(currentTheme.lookAndFeel);
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        Font font = new Font(Font.SANS_SERIF, Font.PLAIN, 12);
        setUIFont(font);
        for(JFrame frame : frames){
            SwingUtilities.updateComponentTreeUI(frame.getRootPane());
            frame.revalidate();
        }

    }

    public static ImageIcon getIcon(String path, int size){
        // Return cached image if possible
        if(imageMap.containsKey(path)){
            return imageMap.get(path);
        }
        // Generate new image
        try {
            System.out.println("Generating image... " + path);
            Image img = ImageIO.read(Objects.requireNonNull(ColorManager.class.getResource(path)));
            return new ImageIcon(img.getScaledInstance(size,size,Image.SCALE_SMOOTH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void setUIFont (Font f){
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get (key);
            if (value instanceof Font)
                UIManager.put (key, f);
        }
    }

}
