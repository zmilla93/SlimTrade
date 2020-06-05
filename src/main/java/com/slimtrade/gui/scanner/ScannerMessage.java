package com.slimtrade.gui.scanner;

import com.slimtrade.core.saving.MacroButton;

import java.util.ArrayList;

public class ScannerMessage {

    public String name;
    public String searchTermsRaw;
    public String ignoreTermsRaw;
    public String[] searchTermsArray;
    public String[] ignoreTermsArray;
    //	public ArrayList<MacroButton> macroButtons;
    public MacroButton[] macroButtons;

    public ScannerMessage(String name, String searchTermsRaw, String ignoreTermsRaw, MacroButton[] macroButtons) {
        this.name = name;
        this.searchTermsRaw = searchTermsRaw;
        this.ignoreTermsRaw = ignoreTermsRaw;
        this.macroButtons = macroButtons;
        this.searchTermsArray = cleanArray(searchTermsRaw);
        this.ignoreTermsArray = cleanArray(ignoreTermsRaw);
    }

    public String getName() {
        return name;
    }

    public String getNameLower() {
        return name.toLowerCase();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    private static String[] cleanArray(String input) {
        if (input.replaceAll("\\s+", "").equals("")) {
            return null;
        }
        String[] arr = input.split("\\n|,|;");
        ArrayList<String> clean = new ArrayList();
        for (String s : arr) {
            String curTerm = s.trim().replaceAll("\\s+", " ").trim();
            if (!curTerm.matches("\\s*")) {
                clean.add(curTerm.toLowerCase());
            }
        }
        arr = new String[clean.size()];
        int i = 0;
        for (String s : clean) {
            arr[i] = s;
            i++;
        }
        return arr;
    }

}
