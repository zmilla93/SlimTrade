package com.slimtrade.core.managers;

import com.slimtrade.App;
import com.slimtrade.core.observing.HotkeyData;
import com.slimtrade.core.saving.MacroButton;
import com.slimtrade.core.utility.TradeOffer;
import com.slimtrade.enums.MessageType;
import com.slimtrade.gui.FrameManager;
import org.jnativehook.keyboard.NativeKeyEvent;

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
                MacroButton[] macros = null;
                switch (firstTrade.messageType) {
                    case INCOMING_TRADE:
                        macros = App.saveManager.saveFile.incomingMacros;
                        break;
                    case OUTGOING_TRADE:
                        macros = App.saveManager.saveFile.outgoingMacros;
                        break;
                    case CHAT_SCANNER:
                        break;
                    case UNKNOWN:
                        break;
                }
                if (macros != null) {
                    for (MacroButton b : macros) {
                        if (b.hotkeyData != null && e.getKeyCode() == b.hotkeyData.keyCode) {
                            //TODO : RUN
                            System.out.println("HOTKEY RUN : " + b.leftMouseResponse);
                            if(b.closeOnClick) {
                                FrameManager.messageManager.closeTrade(firstTrade);
                            }
                        }
                    }
                }
            }
        }

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
