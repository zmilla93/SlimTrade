import com.slimtrade.App;
import com.slimtrade.core.References;
import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.saving.legacy.SaveFilePatcherManager;
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
        assertEquals("SlimTrade", appInfo.name);
        assertEquals("SlimTrade", SaveManager.folderWin);
        assertEquals(".slimtrade", SaveManager.folderOther);
        assertEquals("slimtrade", References.GITHUB_REPO);
        assertTrue(appInfo.appVersion.valid);
        assertFalse(App.noUpdate);
    }

    @Test
    public void checkDebugFlags() {
        assertFalse(App.debug);
        assertFalse(App.chatInConsole);
        assertFalse(App.debugProfileLaunch);
        assertEquals(0, App.debugUIBorders);
        assertFalse(SaveFilePatcherManager.DEBUG_REPORT);
    }

}
