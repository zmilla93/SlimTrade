package com.slimtrade.core.chatparser;

import com.slimtrade.App;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.core.trading.TradeOffer;
import org.apache.commons.io.input.Tailer;

import java.io.File;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatParser implements IChatParserListener {

    private ChatTailerListener chatTailerListener;
    private Tailer tailer;

    ArrayList<IChatParserLoadedListener> onLoadListeners = new ArrayList<>();
    ArrayList<IPreloadTradeListener> preloadTradeListeners = new ArrayList<>();
    ArrayList<ITradeListener> tradeListeners = new ArrayList<>();

    boolean loaded;
    String path;

    public ChatParser(String path) {
        this.path = path;
    }

    public void init() {
        chatTailerListener = new ChatTailerListener(this);
        File clientFile = new File(path);
        if (clientFile.exists()) {
            tailer = Tailer.create(clientFile, chatTailerListener, 100, false);
//            chatTailerListener.init(tailer);
        }
    }

    public void addOnLoadedCallback(IChatParserLoadedListener listener) {
        onLoadListeners.add(listener);
    }

    public void addPreloadTradeListener(IPreloadTradeListener listener) {
        preloadTradeListeners.add(listener);
    }

    public void addTradeListener(ITradeListener listener) {
        tradeListeners.add(listener);
    }

    @Override
    public void parseLine(String line) {
        if (line.contains("@")) {
            // Check for trade offer
            for (LangRegex l : App.languageManager.langList) {
                if (line.contains(l.wantToBuy)) {
                    for (Pattern pattern : l.tradeOfferPatterns) {
                        Matcher matcher = pattern.matcher(line);
                        if (matcher.matches()) {
                            TradeOffer trade = new TradeOffer();
                            trade.date = matcher.group("date").replaceAll("/", "-");
                            trade.time = matcher.group("time");
                            trade.offerType = getMessageType(matcher.group("messageType"));
                            trade.guildName = matcher.group("guildName");
                            trade.playerName = matcher.group("playerName");
                            trade.itemName = matcher.group("itemName");
                            trade.itemQuantity = cleanDouble(cleanResult(matcher, "itemQuantity"));
                            trade.priceTypeString = cleanResult(matcher, "priceType");
                            trade.priceQuantity = cleanDouble(cleanResult(matcher, "priceQuantity"));
                            trade.stashtabName = cleanResult(matcher, "stashtabName");
                            trade.stashtabX = cleanInt(cleanResult(matcher, "stashX"));
                            trade.stashtabY = cleanInt(cleanResult(matcher, "stashY"));
                            trade.bonusText = cleanResult(matcher, "bonusText");
                            handleTradeOffer(trade);
                            break;
                        }
                    }
                }
            }
        } else {
            // Check for trigger (ie joined area)
            // Check for chat scanner
        }

    }

    @Override
    public void handleEOF() {
        if (!loaded) {
            loaded = true;
            for (IChatParserLoadedListener listener : onLoadListeners) {
                listener.handleChatParserLoaded();
            }
        }
    }

    private void handleTradeOffer(TradeOffer offer) {
        if (loaded) {
            for (ITradeListener listener : tradeListeners) {
                listener.handleTrade(offer);
            }
        } else {
            for (IPreloadTradeListener listener : preloadTradeListeners) {
                listener.handlePreloadTrade(offer);
            }
        }
    }

    private TradeOffer.TradeOfferType getTradeOfferType(String text) {
        switch (text) {

        }
        return TradeOffer.TradeOfferType.UNKNOWN;
    }

    private TradeOffer.TradeOfferType getMessageType(String s) {
        // TODO : Move to LangRegex
        switch (s.toLowerCase()) {
            case "from":
            case "來自":     // Chinese
            case "de":      // French, Portuguese & Spanish
            case "von":     // German
            case "от кого": // Russian
            case "จาก":     // Thai
                return TradeOffer.TradeOfferType.INCOMING;
            case "to":
            case "向":      // Chinese
            case "à":       // French
            case "an":      // German
            case "para":    // Portuguese & Spanish
            case "кому":    // Russian
            case "ถึง":      // Thai
                return TradeOffer.TradeOfferType.OUTGOING;

            default:
                return TradeOffer.TradeOfferType.UNKNOWN;
        }
    }

    private String cleanResult(Matcher matcher, String text) {
        try {
            return matcher.group(text);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    private int cleanInt(String text) {
        if (text == null) {
            return 0;
        }
        return Integer.parseInt(text);
    }

    private double cleanDouble(String text) {
        if (text == null) {
            return 0;
        }
        text = text.replaceAll(",", ".");
        return Double.parseDouble(text);
    }
}
