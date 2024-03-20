package com.slimtrade.core.enums;

import com.slimtrade.modules.theme.components.IImageRef;

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
    private static final HashMap<String, CurrencyType> currencyNameMap = new HashMap<>();
    private static final ArrayList<CurrencyType> commonCurrencyTypes = new ArrayList<>();

    private CurrencyType(String line) {
        words = line.split(",");
        for (int i = 0; i < words.length; i++) {
            words[i] = words[i].trim();
        }
        ID = words[0];
    }

    /**
     * Adds aliases for partial words
     */
    private static void addCustomTags() {
        // TODO : Add all common tags
        addAlias("Orb of Alchemy", "alch");
        addAlias("Chaos Orb", "chaos");
        addAlias("Exalted Orb", "exalted");
        addAlias("Divine Orb", "divine");
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
        currencyNameMap.clear();
        try {
            InputStreamReader stream = new InputStreamReader(Objects.requireNonNull(CurrencyType.class.getResourceAsStream("/text/currency.txt")));
            BufferedReader reader = new BufferedReader(stream);
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.matches("\\s+")) continue;
                if (line.startsWith("//")) {
                    // TODO: Could parse the order line to ensure languages are always correct, but low priority since they very rarely change
//                    if (line.startsWith("// Order:")) {
//
//                    }
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
            currencyNameMap.put(word, currency);
        }
    }

    private static void buildCommonCurrencyList() {
        commonCurrencyTypes.clear();
        commonCurrencyTypes.add(getCurrencyType("Blessed Orb"));
        commonCurrencyTypes.add(getCurrencyType("Cartographer's Chisel"));
        commonCurrencyTypes.add(getCurrencyType("Chaos Orb"));
        commonCurrencyTypes.add(getCurrencyType("Chromatic Orb"));
        commonCurrencyTypes.add(getCurrencyType("Divine Orb"));
        commonCurrencyTypes.add(getCurrencyType("Engineer's Orb"));
        commonCurrencyTypes.add(getCurrencyType("Exalted Orb"));
        commonCurrencyTypes.add(getCurrencyType("Gemcutter's Prism"));
        commonCurrencyTypes.add(getCurrencyType("Glassblower's Bauble"));
        commonCurrencyTypes.add(getCurrencyType("Jeweller's Orb"));
        commonCurrencyTypes.add(getCurrencyType("Mirror of Kalandra"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Alchemy"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Alteration"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Chance"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Fusing"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Regret"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Scouring"));
        commonCurrencyTypes.add(getCurrencyType("Orb of Transmutation"));
        commonCurrencyTypes.add(getCurrencyType("Regal Orb"));
        commonCurrencyTypes.add(getCurrencyType("Vaal Orb"));
    }

    public static void addAlias(String existingTag, String alias) {
        CurrencyType image = getCurrencyType(existingTag);
        if (image == null) return;
        currencyNameMap.put(alias, image);
    }

    public static CurrencyType getCurrencyType(String currency) {
        if (currencyNameMap.containsKey(currency)) {
            return currencyNameMap.get(currency);
        }
        return null;
    }

    public static String getIconPath(String currency) {
        if (currencyNameMap.containsKey(currency)) {
            return currencyNameMap.get(currency).getPath();
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
