package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.saving.SaveFile;
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

    private static final SaveFile saveFile = App.saveManager.saveFile;

    public static void processHotkey(NativeKeyEvent e) {
        if(FrameManager.windowState != WindowState.NORMAL) {
            return;
        }
        if (saveFile.quickPasteSetting == QuickPasteSetting.HOTKEY && checkKey(e, saveFile.quickPasteHotkey)) {
            PoeInterface.attemptQuickPaste();
            return;
        }

        if (!PoeInterface.isPoeFocused(true)) {
            return;
        }

        // Inbuilt Macros
        if (checkKey(e, saveFile.closeTradeHotkey)) {
            FrameManager.messageManager.closeTrade(FrameManager.messageManager.getFirstTrade());
        } else if (checkKey(e, saveFile.betrayalHotkey)) {
            FrameManager.betrayalWindow.toggleShow();
            FrameManager.betrayalWindow.refreshVisibility();
        } else if (checkKey(e, saveFile.chatScannerHotkey)) {
            FrameManager.chatScannerWindow.toggleShow();
            FrameManager.chatScannerWindow.refreshVisibility();
        } else if (checkKey(e, saveFile.historyHotkey)) {
            FrameManager.historyWindow.toggleShow();
            FrameManager.historyWindow.refreshVisibility();
        } else if (checkKey(e, saveFile.optionsHotkey)) {
            FrameManager.optionsWindow.toggleShow();
            FrameManager.optionsWindow.refreshVisibility();
        } else if (checkKey(e, saveFile.stashSearchHotkey)) {
            FrameManager.stashSearchWindow.toggleShow();
            FrameManager.stashSearchWindow.refreshVisibility();
        } else if (checkKey(e, saveFile.dndHotkey)) {
            PoeInterface.runCommand("/dnd");
        } else if (checkKey(e, saveFile.remainingHotkey)) {
            PoeInterface.runCommand("/remaining");
        } else if (checkKey(e, saveFile.hideoutHotkey)) {
            PoeInterface.runCommand("/hideout");
        }else if (checkKey(e, saveFile.delveHotkey)) {
            PoeInterface.runCommand("/delve");
        }else if (checkKey(e, saveFile.harvestHotkey)) {
            PoeInterface.runCommand("/harvest");
        }else if (checkKey(e, saveFile.menagerieHotkey)) {
            PoeInterface.runCommand("/menagerie");
        }else if (checkKey(e, saveFile.metamorphHotkey)) {
            PoeInterface.runCommand("/metamorph");
        }else if (checkKey(e, saveFile.exitHotkey)) {
            PoeInterface.runCommand("/exit");
        } else if (checkKey(e, saveFile.leavePartyHotkey)) {
            PoeInterface.runCommand(new String[]{"/kick {self}"}, "", "", "", "", null);
        } else if (checkKey(e, saveFile.betrayalHotkey)) {
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
                        macros = saveFile.incomingMacros;
                        break;
                    case OUTGOING_TRADE:
                        macros = saveFile.outgoingMacros;
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
