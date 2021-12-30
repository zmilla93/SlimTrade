package com.slimtrade.core.chatparser;

public interface IChatParserListener {

    void parseLine(String line);
    void handleEOF();

}
