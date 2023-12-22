package com.slimtrade.core.enums;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOfferType;

import java.awt.*;

/**
 * Stores dark and light variants of colors.
 */
public enum ThemeColors {

    DARK(
            new Color(44, 145, 35),
            new Color(133, 17, 17),
            new Color(232, 128, 26),
            new Color(31, 62, 164),
            new Color(173, 21, 143),
            new Color(232, 128, 26)
    ),
    LIGHT(
            new Color(105, 201, 97),
            new Color(229, 88, 88),
            new Color(220, 146, 72),
            new Color(95, 105, 213),
            new Color(204, 103, 157),
            new Color(220, 146, 72)
    );

    public final Color INCOMING;
    public final Color OUTGOING;
    public final Color CHAT_SCANNER;
    public final Color INCOMING_COLOR_BLIND;
    public final Color OUTGOING_COLOR_BLIND;
    public final Color CHAT_SCANNER_COLOR_BLIND;

    ThemeColors(Color incoming, Color outgoing, Color chatScanner, Color incomingColorBlind, Color outgoingColorBlind, Color chatScannerColorBlind) {
        INCOMING = incoming;
        OUTGOING = outgoing;
        INCOMING_COLOR_BLIND = incomingColorBlind;
        OUTGOING_COLOR_BLIND = outgoingColorBlind;
        CHAT_SCANNER = chatScanner;
        CHAT_SCANNER_COLOR_BLIND = chatScannerColorBlind;
    }

    public Color getMessageColor(TradeOfferType messageType) {
        switch (messageType) {
            case INCOMING_TRADE:
                if (SaveManager.settingsSaveFile.data.colorBlindMode) {
                    return INCOMING_COLOR_BLIND;
                } else {
                    return INCOMING;
                }
            case OUTGOING_TRADE:
                if (SaveManager.settingsSaveFile.data.colorBlindMode) {
                    return OUTGOING_COLOR_BLIND;
                } else {
                    return OUTGOING;
                }
            case CHAT_SCANNER_MESSAGE:
                if (SaveManager.settingsSaveFile.data.colorBlindMode) {
                    return CHAT_SCANNER_COLOR_BLIND;
                } else {
                    return CHAT_SCANNER;
                }
            default:
                return null;
        }
    }

}
