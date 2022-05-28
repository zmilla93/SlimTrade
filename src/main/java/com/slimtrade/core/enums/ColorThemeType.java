package com.slimtrade.core.enums;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.trading.TradeOffer;

import java.awt.*;

public enum ColorThemeType {

    DARK(
            new Color(38, 145, 32),
            new Color(133, 17, 17),
            new Color(232, 128, 26),
            new Color(31, 62, 164),
            new Color(173, 21, 143),
            new Color(232, 128, 26)
    ),
    LIGHT(
            new Color(105, 201, 97),
            new Color(180, 72, 72),
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

    ColorThemeType(Color incoming, Color outgoing, Color chatScanner, Color incomingColorBlind, Color outgoingColorBlind, Color chatScannerColorBlind) {
        INCOMING = incoming;
        OUTGOING = outgoing;
        INCOMING_COLOR_BLIND = incomingColorBlind;
        OUTGOING_COLOR_BLIND = outgoingColorBlind;
        CHAT_SCANNER = chatScanner;
        CHAT_SCANNER_COLOR_BLIND = chatScannerColorBlind;
    }

    public Color getColor(TradeOffer tradeOffer) {
        switch (tradeOffer.offerType) {
            case INCOMING:
                if (SaveManager.settingsSaveFile.data.colorBlindMode) {
                    return INCOMING_COLOR_BLIND;
                } else {
                    return INCOMING;
                }
            case OUTGOING:
                if (SaveManager.settingsSaveFile.data.colorBlindMode) {
                    return OUTGOING_COLOR_BLIND;
                } else {
                    return OUTGOING;
                }
            case CHAT_SCANNER:
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
