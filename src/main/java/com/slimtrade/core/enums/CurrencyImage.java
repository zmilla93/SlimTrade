package com.slimtrade.core.enums;

import com.slimtrade.core.managers.SaveManager;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

/**
 * Stores metadata for POE currency icons.
 */
public class CurrencyImage {

    public final String[] words;
    private String partialName;
    private String fileName;
    private String path;

    // Internal
    private int cachedImageSize;
    private ImageIcon imageIcon;
    public static HashMap<String, CurrencyImage> iconMap = new HashMap<>();

    public CurrencyImage(String line) {
        words = line.split(",");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
    }

    public String getPartialName() {
        if (partialName == null) {
            // TODO
        }
        return partialName;
    }

    private String getFileName() {
        if (fileName == null) {
            fileName = words[0].replaceAll(" ", "_").replaceAll(":", "") + ".png";
        }
        return fileName;
    }

    public ImageIcon getIcon() {
        if (imageIcon == null || cachedImageSize != SaveManager.settingsSaveFile.data.iconSize) {
            cachedImageSize = SaveManager.settingsSaveFile.data.iconSize;
            imageIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/currency/" + getFileName())));
        }
        return imageIcon;
    }

    public String getPath() {
        if (path == null) {
            String fileName = words[0].replaceAll(" ", "_").replaceAll(":", "") + ".png";
            path = "/currency/" + fileName;
        }
        return path;
    }

    public static void addTag(String tag, CurrencyImage currencyImage) {
        iconMap.put(tag, currencyImage);
    }

    private static void addCSV(String line) {
        CurrencyImage image = new CurrencyImage(line);
        for (String word : image.words) {
            iconMap.put(word, image);
        }
    }

    public static void initIconList() {
        try {
            InputStreamReader stream = new InputStreamReader(Objects.requireNonNull(CurrencyImage.class.getResourceAsStream("/text/currency.txt")));
            BufferedReader reader = new BufferedReader(stream);
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.matches("\\s+")) continue;
                if (line.startsWith("//")) {
                    if (line.startsWith("// Order:")) {
//                        System.out.println(line);
                    }
                    continue;
                }
                addCSV(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addCustomTags();
    }

    private static void addCustomTags() {
        // FIXME : Add all tags
        addTag("Chaos Orb", "chaos");
        addTag("Orb of Alchemy", "alch");
    }

    public static void addTag(String existingTag, String newTag) {
        CurrencyImage image = getCurrencyImage(existingTag);
        if (image == null) return;
        iconMap.put(newTag, image);
    }

    public static CurrencyImage getCurrencyImage(String currency) {
        if (iconMap.containsKey(currency)) {
            return iconMap.get(currency);
        }
        return null;
    }

    public static ImageIcon getImageIcon(String currency) {
        if (iconMap.containsKey(currency)) {
            return iconMap.get(currency).getIcon();
        }
        return null;
    }

    public static String getIconPath(String currency) {
        if (iconMap.containsKey(currency)) {
            return iconMap.get(currency).getPath();
        }
        return null;
    }

}
