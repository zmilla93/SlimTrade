package github.zmilla93;

import github.zmilla93.core.chatparser.ChatParser;
import github.zmilla93.core.chatparser.IParserLoadedListener;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.saving.savefiles.ChatScannerSaveFile;
import github.zmilla93.modules.saving.SaveFile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest implements IParserLoadedListener {

    private int count;
    private int onCount;
    private int offCount;
    private static boolean loaded = false;
    private static ChatParser parser;

    @BeforeEach
    public void beforeEach() {
        if (parser != null) {
            parser.close();
            parser.removeAllListeners();
        }
        parser = new ChatParser();
        parser.addOnLoadedCallback(this);
        loaded = false;
        count = 0;
    }

    private static void waitForParser() {
        int maxWait = 50;
        int count = 0;
        while (!loaded) {
            try {
                count++;
                Thread.sleep(50);
                if (count > maxWait) {
                    System.err.println("Stuck waiting for parser!");
                    return;
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    int incomingTradeCount = 0;
    int outgoingTradeCount = 0;

    @Test
    public void tradeTest() {
        parser.addTradeListener((tradeOffer, loaded) -> {
            switch (tradeOffer.offerType) {
                case INCOMING_TRADE:
                    incomingTradeCount++;
                    break;
                case OUTGOING_TRADE:
                    outgoingTradeCount++;
                    break;
            }
        });
        parser.open(Paths.get("/client/client_trade.txt"), true);
        waitForParser();
        assertEquals(14, incomingTradeCount);
        assertEquals(14, outgoingTradeCount);
    }

    @Test
    public void dndTest() {
        onCount = 0;
        offCount = 0;
        parser.addDndListener((state, loaded) -> {
            if (state) onCount++;
            else offCount++;
        });
        parser.open(Paths.get("/client/client_dnd.txt"), true);
        waitForParser();
        assertEquals(8, onCount);
        assertEquals(8, offCount);
    }

    @Test
    public void scannerTest() {
        count = 0;
        SaveManager.chatScannerSaveFile = new SaveFile<>(Paths.get("/saves/scanner.json"), ChatScannerSaveFile.class, true);
        SaveManager.chatScannerSaveFile.loadFromDisk();
        SaveManager.chatScannerSaveFile.data.searching = true;
        SaveManager.chatScannerSaveFile.data.activeSearches = SaveManager.chatScannerSaveFile.data.scannerEntries;
        parser.addChatScannerListener((entry, message, loaded) -> {
            System.out.println("entry:" + message);
            count++;
        });
        parser.open(Paths.get("/client/client_scanner.txt"), true);
        waitForParser();
        assertEquals(10, count);
    }

    @Override
    public void onParserLoaded(boolean dnd) {
        loaded = true;
    }

}
