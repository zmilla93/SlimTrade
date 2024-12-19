package github.zmilla93;

import github.zmilla93.core.References;
import github.zmilla93.core.saving.legacy.SaveFilePatcherManager;
import github.zmilla93.core.utility.NinjaInterface;
import github.zmilla93.modules.updater.UpdateManager;
import github.zmilla93.modules.updater.data.AppInfo;
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
        AppInfo appInfo = App.getAppInfo();
        if (appInfo == null) fail();
        assertEquals("SlimTrade", appInfo.appName);
        assertEquals("SlimTrade", References.GITHUB_REPO);
        assertTrue(appInfo.appVersion.valid);
    }

    @Test
    public void checkDebugFlags() {
        assertFalse(App.noUpdate);
        assertTrue(App.useLockFile);
        assertFalse(App.debug);
        assertFalse(App.debugUIAlwaysOnTop);
        assertFalse(App.chatInConsole);
        assertFalse(App.debugProfileLaunch);
        assertFalse(App.showOptionsOnLaunch);
        assertFalse(App.forceSetup);
        assertFalse(App.messageUITest);
        assertFalse(NinjaInterface.useLocalDatasets);
        assertFalse(App.themeDebugWindows);
        assertEquals(0, App.debugUIBorders);
        assertFalse(SaveFilePatcherManager.DEBUG_REPORT);
        assertFalse(UpdateManager.DEBUG_FAST_PERIODIC_CHECK);
        assertNull(App.debugOptionPanelName);
        assertNull(App.debugOptionPanelName);
    }

}
