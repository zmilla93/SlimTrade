package github.zmilla93.gui.chatscanner;

import github.zmilla93.core.enums.ButtonRow;
import github.zmilla93.core.enums.CustomIcon;
import github.zmilla93.core.utility.MacroButton;
import github.zmilla93.core.utility.ZUtil;

import java.util.ArrayList;

/**
 * Stores all the data needed for a chat scanner search.
 */
// FIXME : Rename ChatScannerSearchEntry?
// FIXME : This is currently in GUI but is a data component. Should probably refactor
public class ChatScannerEntry {

    // Input
    public final String title;
    public final String searchTermsRaw;
    public final String ignoreTermsRaw;
    public final ArrayList<MacroButton> macros;

    // Targets
    public boolean allowGlobalAndTradeChat = true;
    public boolean allowWhispers = true;
    public boolean allowMetaText = false;

    // Generated
    private transient ArrayList<String> searchTerms;
    private transient ArrayList<String> ignoreTerms;

    // This constructor is only used by gson. Without it, default values for allowGlobal, etc., are not set
    public ChatScannerEntry() {
        title = null;
        searchTermsRaw = null;
        ignoreTermsRaw = null;
        macros = new ArrayList<>();
    }

    public ChatScannerEntry(String title) {
        this.title = title;
        this.searchTermsRaw = "";
        this.ignoreTermsRaw = "";
        macros = new ArrayList<>();
        macros.add(new MacroButton(CustomIcon.INVITE, "/invite {player}", "", ButtonRow.BOTTOM_ROW, null, false));
        macros.add(new MacroButton(CustomIcon.CART, "/tradewith {player}", "", ButtonRow.BOTTOM_ROW, null, false));
        macros.add(new MacroButton(CustomIcon.THUMB, "thanks", "", ButtonRow.BOTTOM_ROW, null, false));
        macros.add(new MacroButton(CustomIcon.LEAVE, "/kick {player}", "", ButtonRow.BOTTOM_ROW, null, true));
    }

    public ChatScannerEntry(String title, String searchTermsRaw, String ignoreTermsRaw, ArrayList<MacroButton> macros, boolean allowGlobalAndTradeChat, boolean allowWhispers, boolean allowMetaText) {
        this.title = title;
        this.searchTermsRaw = searchTermsRaw;
        this.ignoreTermsRaw = ignoreTermsRaw;
        this.macros = macros == null ? new ArrayList<>() : macros;
        this.allowGlobalAndTradeChat = allowGlobalAndTradeChat;
        this.allowWhispers = allowWhispers;
        this.allowMetaText = allowMetaText;
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
        ArrayList<String> clean = new ArrayList<>();
        for (String s : arr) {
            String curTerm = ZUtil.cleanString(s);
            if (!curTerm.matches("\\s*")) {
                clean.add(curTerm.toLowerCase());
            }
        }
        return clean;
    }

    public ChatScannerEntry getExampleEntry() {
        return new ChatScannerEntry("Example Search");
    }

    @Override
    public String toString() {
        return title;
    }
}
