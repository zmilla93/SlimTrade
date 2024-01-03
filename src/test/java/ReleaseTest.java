import com.slimtrade.App;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * These tests should run before a release build is made.
 */
public class ReleaseTest {

    @Test
    public void checkAppSettings() {
        // TODO : This
    }

    @Test
    public void checkDebugFlags() {
        assertFalse(App.debug);
        assertFalse(App.chatInConsole);
        assertFalse(App.debugProfileLaunch);
        assertEquals(App.debugUIBorders, 0);
    }

}
