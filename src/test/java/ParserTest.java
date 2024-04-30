import com.slimtrade.core.chatparser.ChatParser;
import com.slimtrade.core.chatparser.IParserLoadedListener;
import com.slimtrade.core.trading.LangRegex;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ParserTest implements IParserLoadedListener {

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
    }

    private static void waitForParser() {
        while (!loaded) {
            try {
                Thread.sleep(50);
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
        parser.open("/text/client_dnd.txt");
        waitForParser();
        assertEquals(8, onCount);
        assertEquals(8, offCount);
    }

    @Override
    public void onParserLoaded() {
        loaded = true;
    }

}
