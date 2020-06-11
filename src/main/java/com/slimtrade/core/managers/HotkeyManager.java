package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.QuickPasteSetting;
import com.slimtrade.gui.FrameManager;
import com.slimtrade.gui.options.cheatsheet.CheatSheetData;
import com.slimtrade.gui.scanner.ScannerMessage;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class HotkeyManager {

    public static void processHotkey(NativeKeyEvent e) {

        // Inbuilt Macros
        if (checkKey(e, App.saveManager.saveFile.closeTradeHotkey)) {
            FrameManager.messageManager.closeTrade(FrameManager.messageManager.getFirstTrade());
        } else if (checkKey(e, App.saveManager.saveFile.stashSearchHotkey)) {
            FrameManager.stashSearchWindow.toggleShow();
            FrameManager.stashSearchWindow.refreshVisibility();
        } else if (checkKey(e, App.saveManager.saveFile.remainingHotkey)) {
            PoeInterface.runCommand("/remaining");
        } else if (checkKey(e, App.saveManager.saveFile.hideoutHotkey)) {
            PoeInterface.runCommand("/hideout");
        } else if (checkKey(e, App.saveManager.saveFile.leavePartyHotkey)) {
            PoeInterface.runCommand(new String[]{"/kick {self}"}, "", "", "", "", null);
        } else if (checkKey(e, App.saveManager.saveFile.betrayalHotkey)) {
            FrameManager.betrayalWindow.toggleShow();
            FrameManager.betrayalWindow.refreshVisibility();
        } else if (App.saveManager.saveFile.quickPasteSetting == QuickPasteSetting.HOTKEY && checkKey(e, App.saveManager.saveFile.quickPasteHotkey)) {
            PoeInterface.attemptQuickPaste();
        }

        else {

            // Cheat Sheets
            for (CheatSheetData data : App.saveManager.saveFile.cheatSheetData) {
                if (checkKey(e, data.hotkeyData)) {
//                    data.getWindow().setVisible(true);
                    data.getWindow().visible = ! data.getWindow().visible;
                    data.getWindow().refreshVisibility();
                }
            }

            // Close Button
            TradeOffer firstTrade = FrameManager.messageManager.getFirstTrade();
            if (firstTrade != null) {
                HotkeyData closeMacro = null;
                MacroButton[] macros = null;
                switch (firstTrade.messageType) {
                    case INCOMING_TRADE:
                        macros = App.saveManager.saveFile.incomingMacros;
                        break;
                    case OUTGOING_TRADE:
                        macros = App.saveManager.saveFile.outgoingMacros;
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
                            //TODO : RUN
                            PoeInterface.runCommand(b.getCommandsLeft(), firstTrade.playerName, TradeUtility.getFixedItemName(firstTrade.itemName, firstTrade.itemQuantity, true), firstTrade.priceQuantity + " " + firstTrade.priceTypeString, firstTrade.sentMessage, firstTrade.messageType);
                            if (b.closeOnClick) {
                                FrameManager.messageManager.closeTrade(firstTrade);
                            }
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
        if (e.getKeyCode() == data.keyCode && e.getModifiers() == data.modifiers) {
            return true;
        }
        return false;
    }

}
