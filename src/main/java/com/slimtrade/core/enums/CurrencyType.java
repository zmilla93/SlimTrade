package com.slimtrade.core.enums;

import com.slimtrade.modules.colortheme.components.IImageRef;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Stores metadata for POE currency icons.
 */
public class CurrencyType implements IImageRef {

    public transient final String[] words;
    private transient String partialName;
    private transient String fileName;
    private transient String path;
    public final String ID; // Used to save to file

    // Internal
    private static final HashMap<String, CurrencyType> iconMap = new HashMap<>();
    private static final ArrayList<CurrencyType> commonCurrencyTypes = new ArrayList<>();

    public CurrencyType(String line) {
        words = line.split(",");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        ID = words[0];
    }

    public String getPartialName() {
        if (partialName == null) {
            // TODO
        }
        return partialName;
    }

    /**
     * Builds a hashmap of all currency types and translations using currency.txt
     */
    public static void initIconList() {
        iconMap.clear();
        try {
            InputStreamReader stream = new InputStreamReader(Objects.requireNonNull(CurrencyType.class.getResourceAsStream("/text/currency.txt")));
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
        buildCommonCurrencyList();
    }

    private static void addCSV(String line) {
        CurrencyType currency = new CurrencyType(line);
        for (String word : currency.words) {
            iconMap.put(word, currency);
        }
    }

    /**
     * Adds aliases for partial words
     */
    private static void addCustomTags() {
        // FIXME : Add all tags
        addAlias("Orb of Alchemy", "alch");
        addAlias("Chaos Orb", "chaos");
        addAlias("Exalted Orb", "exalted");
    }

    private static void buildCommonCurrencyList() {
        commonCurrencyTypes.clear();
        commonCurrencyTypes.add(getCurrencyImage("Chaos Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Exalted Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Blessed Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Cartographer's Chisel"));
        commonCurrencyTypes.add(getCurrencyImage("Chromatic Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Divine Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Engineer's Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Gemcutter's Prism"));
        commonCurrencyTypes.add(getCurrencyImage("Glassblower's Bauble"));
        commonCurrencyTypes.add(getCurrencyImage("Jeweller's Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Mirror of Kalandra"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Alchemy"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Alteration"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Chance"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Fusing"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Regret"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Scouring"));
        commonCurrencyTypes.add(getCurrencyImage("Orb of Transmutation"));
        commonCurrencyTypes.add(getCurrencyImage("Regal Orb"));
        commonCurrencyTypes.add(getCurrencyImage("Vaal Orb"));
    }

    public static void addAlias(String existingTag, String alias) {
        CurrencyType image = getCurrencyImage(existingTag);
        if (image == null) return;
        iconMap.put(alias, image);
    }

    public static CurrencyType getCurrencyImage(String currency) {
        if (iconMap.containsKey(currency)) {
            return iconMap.get(currency);
        }
        return null;
    }

    public static String getIconPath(String currency) {
        if (iconMap.containsKey(currency)) {
            return iconMap.get(currency).getPath();
        }
        return null;
    }

    public static ArrayList<CurrencyType> getCommonCurrencyTypes() {
        return commonCurrencyTypes;
    }

    @Override
    public String getPath() {
        if (path == null) {
            String fileName = words[0].replaceAll(" ", "_").replaceAll(":", "") + ".png";
            path = "/currency/" + fileName;
        }
        return path;
    }

    @Override
    public String toString() {
        return words[0];
    }
}