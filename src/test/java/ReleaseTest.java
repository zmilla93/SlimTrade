import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.SaveFilePatcherManager;
import com.slimtrade.modules.updater.data.AppInfo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * These tests should always run before a release build is made.
 * Ensures debug flags are turned off and various constants are set correctly.
 */
public class ReleaseTest {

    @Test
    public void checkAppSettings() {
        // TODO : Make sure this has all relevant info
        AppInfo appInfo = App.readAppInfo();
        if (appInfo == null) fail();
        assertEquals(appInfo.name, "SlimTrade");
        assertEquals(SaveManager.folderWin, "SlimTrade");
        assertEquals(SaveManager.folderOther, ".slimtrade");
        assertEquals(References.GITHUB_REPO, "slimtrade");
        assertTrue(appInfo.appVersion.valid);
        assertFalse(App.noUpdate);
    }

    @Test
    public void checkDebugFlags() {
        assertFalse(App.debug);
        assertFalse(App.chatInConsole);
        assertFalse(App.debugProfileLaunch);
        assertEquals(App.debugUIBorders, 0);
        assertFalse(SaveFilePatcherManager.DEBUG_REPORT);
    }

}
