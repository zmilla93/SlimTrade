package com.slimtrade.core.data;

public class PlayerMessage {

    public final String player;
    public final String message;
    public final boolean isMetaText;
    private final String fullMessage;

    public PlayerMessage(String player, String message) {
        this(player, message, false);
    }

    public PlayerMessage(String player, String message, boolean isMetaText) {
        this.player = player;
        this.message = message;
        this.isMetaText = isMetaText;
        fullMessage = player + ": " + message;
    }

    @Override
    public String toString() {
        return fullMessage;
    }

}
