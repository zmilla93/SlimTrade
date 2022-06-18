package com.slimtrade.gui.chatscanner;

import com.slimtrade.core.enums.ButtonRow;
import com.slimtrade.core.enums.CustomIcon;
import com.slimtrade.core.utility.MacroButton;

import java.util.ArrayList;

/**
 * Stores all the data needed for a chat scanner search.
 */
public class ChatScannerEntry {

    // Input
    public final String title;
    public final String searchTermsRaw;
    public final String ignoreTermsRaw;
    public final ArrayList<MacroButton> macros;

    // Generated
    private transient ArrayList<String> searchTerms;
    private transient ArrayList<String> ignoreTerms;

    public ChatScannerEntry(String title) {
        this.title = title;
        this.searchTermsRaw = "";
        this.ignoreTermsRaw = "";
        macros = new ArrayList<>();
        macros.add(new MacroButton(CustomIcon.INVITE, "/invite {player}", "", ButtonRow.BOTTOM_ROW, false));
        macros.add(new MacroButton(CustomIcon.CART, "/tradewith {player}", "", ButtonRow.BOTTOM_ROW, false));
        macros.add(new MacroButton(CustomIcon.THUMB, "thanks", "", ButtonRow.BOTTOM_ROW, false));
        macros.add(new MacroButton(CustomIcon.LEAVE, "/kick {self}", "", ButtonRow.BOTTOM_ROW, true));
    }

    public ChatScannerEntry(String title, String searchTermsRaw, String ignoreTermsRaw, ArrayList<MacroButton> macros) {
        this.title = title;
        this.searchTermsRaw = searchTermsRaw;
        this.ignoreTermsRaw = ignoreTermsRaw;
        this.macros = macros == null ? new ArrayList<>() : macros;
    }

    public ArrayList<String> getSearchTerms() {
        if (searchTerms == null) {
            searchTerms = cleanArray(searchTermsRaw);
        }
        return searchTerms;
    }

    public ArrayList<String> getIgnoreTerms() {
        if (ignoreTerms == null) {
            ignoreTerms = cleanArray(ignoreTermsRaw);
        }
        return ignoreTerms;
    }

    private static ArrayList<String> cleanArray(String input) {
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
        return clean;
    }

    @Override
    public String toString() {
        return title;
    }
}
