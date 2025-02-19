package github.zmilla93.core.trading;

import github.zmilla93.core.poe.Game;

public abstract class ClientMessage {

    public String time;
    public String date;
    public String messageRaw;
    public String message;
    public MessageType messageType;
    public Game game;

    public ClientMessage() {

    }

    public ClientMessage(String text) {
        this.messageRaw = text;
        this.message = text;
        this.messageType = MessageType.META_DATA;
    }

}
