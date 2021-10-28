package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.saving.savefiles.SettingsSaveFile;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.enums.WindowState;
import com.slimtrade.gui.options.cheatsheet.CheatSheetWindow;
import com.slimtrade.gui.scanner.ScannerMessage;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class HotkeyManager {

    private static final SettingsSaveFile SETTINGS_SAVE_FILE = App.saveManager.settingsSaveFile;

    public static void processHotkey(NativeKeyEvent e) {
        // Ignore hotkeys when not in normal window state
        if (FrameManager.windowState != WindowState.NORMAL) {
            return;
        }
        // Quick Paste
        if (SETTINGS_SAVE_FILE.quickPasteSetting == QuickPasteSetting.HOTKEY && checkKey(e, SETTINGS_SAVE_FILE.quickPasteHotkey)) {
            PoeInterface.attemptQuickPaste();
            return;
        }
        // Ignore hotkeys while tabbed out
        if (!PoeInterface.isPoeFocused(true)) {
            return;
        }
        // Inbuilt Macros
        if (checkKey(e, SETTINGS_SAVE_FILE.closeTradeHotkey)) {
            FrameManager.messageManager.closeTrade(FrameManager.messageManager.getFirstTrade());
        } else if (checkKey(e, SETTINGS_SAVE_FILE.betrayalHotkey)) {
            FrameManager.betrayalWindow.toggleShow();
            FrameManager.betrayalWindow.refreshVisibility();
        } else if (checkKey(e, SETTINGS_SAVE_FILE.chatScannerHotkey)) {
            FrameManager.chatScannerWindow.toggleShow();
            FrameManager.chatScannerWindow.refreshVisibility();
        } else if (checkKey(e, SETTINGS_SAVE_FILE.historyHotkey)) {
            FrameManager.historyWindow.toggleShow();
            FrameManager.historyWindow.refreshVisibility();
        } else if (checkKey(e, SETTINGS_SAVE_FILE.optionsHotkey)) {
            FrameManager.optionsWindow.toggleShow();
            FrameManager.optionsWindow.refreshVisibility();
        } else if (checkKey(e, SETTINGS_SAVE_FILE.stashSearchHotkey)) {
            FrameManager.stashSearchWindow.toggleShow();
            FrameManager.stashSearchWindow.refreshVisibility();
        } else if (checkKey(e, SETTINGS_SAVE_FILE.dndHotkey)) {
            PoeInterface.runCommand("/dnd");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.remainingHotkey)) {
            PoeInterface.runCommand("/remaining");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.hideoutHotkey)) {
            PoeInterface.runCommand("/hideout");
        }else if (checkKey(e, SETTINGS_SAVE_FILE.guildHotkey)) {
            PoeInterface.runCommand("/guild");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.delveHotkey)) {
            PoeInterface.runCommand("/delve");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.menagerieHotkey)) {
            PoeInterface.runCommand("/menagerie");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.metamorphHotkey)) {
            PoeInterface.runCommand("/metamorph");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.exitHotkey)) {
            PoeInterface.runCommand("/exit");
        } else if (checkKey(e, SETTINGS_SAVE_FILE.leavePartyHotkey)) {
            PoeInterface.runCommand(new String[]{"/kick {self}"}, "", "", "", "", null);
        } else if (checkKey(e, SETTINGS_SAVE_FILE.betrayalHotkey)) {
            FrameManager.betrayalWindow.toggleShow();
            FrameManager.betrayalWindow.refreshVisibility();
        } else {
            // Cheat Sheets
            for (CheatSheetWindow w : FrameManager.cheatSheetWindows) {
                if (checkKey(e, w.data.hotkeyData)) {
                    w.visible = !w.visible;
                    w.refreshVisibility();
                    return;
                }
            }

            // Close Button
            TradeOffer firstTrade = FrameManager.messageManager.getFirstTrade();
            if (firstTrade != null) {
                MacroButton[] macros = null;
                switch (firstTrade.messageType) {
                    case INCOMING_TRADE:
                        macros = SETTINGS_SAVE_FILE.incomingMacros;
                        break;
                    case OUTGOING_TRADE:
                        macros = SETTINGS_SAVE_FILE.outgoingMacros;
                        break;
                    case CHAT_SCANNER:
                        for (ScannerMessage msg : App.saveManager.scannerSaveFile.messages) {
                            if (firstTrade.searchName == msg.name) {
                                macros = msg.macroButtons;
                                break;
                            }
                        }
                        break;
                    case UNKNOWN:
                        break;
                }

                // Custom Macros
                if (macros != null) {
                    // Macros
                    for (MacroButton b : macros) {
                        if (b.hotkeyData != null && e.getKeyCode() == b.hotkeyData.keyCode) {
                            PoeInterface.runCommand(b.getCommandsLeft(), firstTrade.playerName, TradeUtility.getFixedItemName(firstTrade.itemName, firstTrade.itemQuantity, true), firstTrade.priceQuantity + " " + firstTrade.priceTypeString, firstTrade.sentMessage, firstTrade.messageType);
                            if (b.closeOnClick) {
                                FrameManager.messageManager.closeTrade(firstTrade);
                            }
                            return;
                        }
                    }
                }
            }
        }

    }

    public static String[] getCommandList(String text) {
        ArrayList<String> commands = new ArrayList<>();
        StringBuilder builder = new StringBuilder(255);
        if (text.equals("")) {
            return new String[0];
        }
        if (!text.startsWith("/") && !text.startsWith("@")) {
            text = "@{player} " + text;
        }
        int i = 0;
        while (i < text.length()) {
            if ((text.charAt(i) == '@' || text.charAt(i) == '/') && builder.length() > 1) {
                commands.add(builder.toString().trim());
                builder.setLength(0);
            }
            builder.append(text.charAt(i));
            i++;
        }
        if (builder.length() > 1) {
            commands.add(builder.toString().trim());
            builder.setLength(0);
        }
        return commands.toArray(new String[0]);
    }

    private static boolean checkKey(NativeKeyEvent e, HotkeyData data) {
        if (data == null) {
            return false;
        }
        return e.getKeyCode() == data.keyCode && e.getModifiers() == data.modifiers;
    }

}
