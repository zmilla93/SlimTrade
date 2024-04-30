package com.slimtrade.core.data;

public class PlayerMessage {

    public final String player;
    public final String message;
    private final String fullMessage;

    public PlayerMessage(String player, String message) {
        this.player = player;
        this.message = message;
        fullMessage = player + ": " + message;
    }

    @Override
    public String toString() {
        return fullMessage;
    }

}
