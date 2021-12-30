package com.slimtrade.core.chatparser;

import org.apache.commons.io.input.TailerListenerAdapter;

public class ChatTailerListener extends TailerListenerAdapter {

    private final IChatParserListener chatParserListener;

    public ChatTailerListener(IChatParserListener chatParserListener) {
        this.chatParserListener = chatParserListener;
    }

    @Override
    public void handle(String line) {
        super.handle(line);
        chatParserListener.parseLine(line);
    }

    @Override
    public void fileRotated() {
        super.fileRotated();
        // TODO : THIS
        System.out.println("Client.txt File Rotated!");
    }

    @Override
    public void endOfFileReached() {
        super.endOfFileReached();
        chatParserListener.handleEOF();
    }
}
