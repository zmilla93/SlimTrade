package github.zmilla93.core.trading;

/**
 * The types of messages that appear in Path of Exile's Client.txt file.
 */
// FIXME : Only meta data is used right now
public enum MessageType {

    META_DATA,
    LOCAL_CHAT,
    GLOBAL_CHAT,
    TRADE_CHAT,
    INCOMING_WHISPER,
    OUTGOING_WHISPER,
    INCOMING_TRADE,
    OUTGOING_TRADE,

}
