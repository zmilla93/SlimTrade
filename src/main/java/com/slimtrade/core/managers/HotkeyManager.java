package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.utility.PoeInterface;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.core.utility.TradeUtility;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.ArrayList;

public class HotkeyManager {

    public static void processHotkey(NativeKeyEvent e) {
//        public HotkeyData remainingHotkey = null;
//        public HotkeyData hideoutHotkey = null;
//        public HotkeyData leavePartyHotkey = null;
//        public HotkeyData betrayalHotkey = null;
        if (checkKey(e, App.saveManager.saveFile.remainingHotkey)) {

        } else if (checkKey(e, App.saveManager.saveFile.hideoutHotkey)) {

        } else if (checkKey(e, App.saveManager.saveFile.leavePartyHotkey)) {

        } else if (checkKey(e, App.saveManager.saveFile.betrayalHotkey)) {

        }
        // TODO : CLOSE BUTTON
        else {
            TradeOffer firstTrade = FrameManager.messageManager.getFirstTrade();
            if (firstTrade != null) {
                HotkeyData closeMacro = null;
                MacroButton[] macros = null;
                switch (firstTrade.messageType) {
                    case INCOMING_TRADE:
                        macros = App.saveManager.saveFile.incomingMacros;
                        closeMacro = App.saveManager.saveFile.closeIncomingHotkey;
                        break;
                    case OUTGOING_TRADE:
                        macros = App.saveManager.saveFile.outgoingMacros;
                        closeMacro = App.saveManager.saveFile.closeOutoingHotkey;
                        break;
                    case CHAT_SCANNER:
                        break;
                    case UNKNOWN:
                        break;
                }
                if (macros != null) {

                    // Macros
                    for (MacroButton b : macros) {
                        if (b.hotkeyData != null && e.getKeyCode() == b.hotkeyData.keyCode) {
                            //TODO : RUN
                            PoeInterface.runCommand(b.getCommandsLeft(), firstTrade.playerName, TradeUtility.getFixedItemName(firstTrade.itemName, firstTrade.itemCount, true), firstTrade.priceCount + " " + firstTrade.priceTypeString);
                            System.out.println("HOTKEY RUN : " + b.leftMouseResponse);
                            if (b.closeOnClick) {
                                FrameManager.messageManager.closeTrade(firstTrade);
                            }
                        }
                    }

                    // Close Button
                    if(e.getKeyCode() == closeMacro.keyCode) {
                        FrameManager.messageManager.closeTrade(firstTrade);
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
