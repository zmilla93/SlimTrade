import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.savefiles.ChatScannerSaveFile;
import com.slimtrade.core.trading.LangRegex;
import com.slimtrade.modules.saving.SaveFile;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest implements IParserLoadedListener {

    private int count;
    private int onCount;
    private int offCount;
    private static boolean loaded = false;
    private static ChatParser parser;

    @BeforeAll
    private static void beforeAll() {
        LangRegex.compileAll();
    }

    @BeforeEach
    private void beforeEach() {
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

    @Test
    public void dndTest() {
        onCount = 0;
        offCount = 0;
        parser.addDndListener((state, loaded) -> {
            if (state) onCount++;
            else offCount++;
        });
        parser.open("/client/client_dnd.txt");
        waitForParser();
        assertEquals(8, onCount);
        assertEquals(8, offCount);
    }

    @Test
    public void scannerTest() {
        count = 0;
        SaveManager.chatScannerSaveFile = new SaveFile<>("/saves/scanner.json", ChatScannerSaveFile.class);
        SaveManager.chatScannerSaveFile.loadFromDisk();
        SaveManager.chatScannerSaveFile.data.searching = true;
        SaveManager.chatScannerSaveFile.data.activeSearches = SaveManager.chatScannerSaveFile.data.scannerEntries;
        parser.addChatScannerListener((loaded, entry, message) -> {
            count++;
        });
        parser.open("/client/client_scanner.txt");
        waitForParser();
        assertEquals(10, count);
    }

    @Override
    public void onParserLoaded() {
        loaded = true;
    }

}
