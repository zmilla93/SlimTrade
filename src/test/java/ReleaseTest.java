import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.modules.updater.data.AppInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These tests should run before a release build is made.
 */
public class ReleaseTest {

    @Test
    public void checkAppSettings() {
        // TODO : Make sure this has all relevant info
        AppInfo appInfo = App.readAppInfo();
        if (appInfo == null) fail();
        assertEquals(References.GITHUB_REPO, "slimtrade");
        assertEquals(appInfo.name, "SlimTrade");
        assertTrue(appInfo.appVersion.valid);
        assertFalse(App.noUpdate);
    }

    @Test
    public void checkDebugFlags() {
        assertFalse(App.debug);
        assertFalse(App.chatInConsole);
        assertFalse(App.debugProfileLaunch);
        assertEquals(App.debugUIBorders, 0);
    }

}
