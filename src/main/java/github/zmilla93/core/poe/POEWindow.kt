package github.zmilla93.core.poe

import com.sun.jna.platform.WindowUtils
import com.sun.jna.platform.win32.WinDef.HWND
import github.zmilla93.App
import github.zmilla93.core.data.MonitorInfo
import github.zmilla93.core.enums.Anchor
import github.zmilla93.core.jna.CustomUser32
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.utility.Platform
import java.awt.Dimension
import java.awt.Point
import java.awt.Rectangle
import java.awt.Window
import java.awt.geom.Rectangle2D
import javax.swing.SwingUtilities

/**
 * A platform independent representation for Path of Exile's game window.
 * Used to calculate the screen location of in game UI elements, or to pin things relative to the game window.
 * Bounds can be updated via different [GameWindowMode]s.
 * Attach a [POEWindowListener] to listen for events.
 */
// TODO : Could also add the option to manually define the game window region to support Mac/Linux users who play in windowed mode.
object POEWindow {

    private val POE_1_PERCENT_STASH_NO_FOLDERS: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(15, 125, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_1_PERCENT_STASH_WITH_FOLDERS_OUTSIDE: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(15, 133, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_1_PERCENT_STASH_WITH_FOLDERS_INSIDE: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(15, 167, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_2_PERCENT_STASH_NO_FOLDERS: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(14, 117, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_2_PERCENT_STASH_WITH_FOLDERS_OUTSIDE: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(14, 125, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_2_PERCENT_STASH_WITH_FOLDERS_INSIDE: Rectangle2D.Float = ScaledRect.getPercentRect(
        Rectangle(14, 160, 634, 634),
        Rectangle(0, 0, 1920, 1080)
    )

    private val POE_1_PERCENT_HELPER_OFFSET_NO_FOLDERS = ScaledInt.getPercentValue(90, 1080)
    private val POE_1_PERCENT_HELPER_OFFSET_WITH_FOLDERS = ScaledInt.getPercentValue(98, 1080)
    private val POE_2_PERCENT_HELPER_OFFSET_NO_FOLDERS = ScaledInt.getPercentValue(86, 1080)
    private val POE_2_PERCENT_HELPER_OFFSET_WITH_FOLDERS = ScaledInt.getPercentValue(92, 1080)

    /** Currently set game bounds */
    @JvmStatic
    var gameBounds: Rectangle = Rectangle()
        private set
    private var centerOfScreen: Point = Point()

    /** Calculated based on the current game bounds */ // Path of Exile 1
    private var poe1StashBoundsWithFoldersInside: Rectangle = Rectangle()
    private var poe1StashBoundsWithFoldersOutside: Rectangle = Rectangle()
    private var poe1StashBoundsNoFolders: Rectangle = Rectangle()

    @JvmStatic
    var poe1StashCellSize: Dimension = Dimension()
        private set

    @JvmStatic
    var poe1StashCellSizeQuad: Dimension = Dimension()
        private set
    private var poe1StashHelperOffsetWithFolders = 0
    private var poe1StashHelperOffsetNoFolders = 0

    // Path of Exile 2
    private var poe2StashBoundsNoFolders: Rectangle = Rectangle()
    private var poe2StashBoundsWithFoldersInside: Rectangle = Rectangle()
    private var poe2StashBoundsWithFoldersOutside: Rectangle = Rectangle()

    // Note: Poe 1 & 2 have the same stash size, so these values are always the same as POE1's.
    // Keeping this calculate independent anyway on the off chance it changes.
    @JvmStatic
    var poe2StashCellSize: Dimension = Dimension()
        private set

    @JvmStatic
    var poe2StashCellSizeQuad: Dimension = Dimension()
        private set
    private var poe2StashHelperOffsetWithFolders = 0
    private var poe2StashHelperOffsetNoFolders = 0

    /** The components most recently used to update the game bounds */ //    private static NativePoeWindow currentGameWindow;
    @JvmStatic
    var gameHandle: HWND? = null
        private set
    private var currentMonitor: MonitorInfo? = null

    /** The things that care about the game bounds */
    private val listeners = ArrayList<POEWindowListener>()

    fun setBoundsByWindowHandle(handle: HWND) {
        setBoundsByWindowHandle(handle, false)
    }

    fun setBoundsByWindowHandle(handle: HWND, forceUpdate: Boolean) {
        assert(SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.DETECT)
        checkNotNull(handle)
        if (handle == gameHandle && !forceUpdate) return
        if (CustomUser32.INSTANCE.IsIconic(handle)) return
        gameHandle = handle
        gameBounds = WindowUtils.getWindowLocationAndSize(handle).bounds
        calculateNewGameBounds()
    }

    fun setBoundsByMonitor(monitor: MonitorInfo) {
        assert(SaveManager.settingsSaveFile.data.gameWindowMode == GameWindowMode.MONITOR)
        checkNotNull(monitor)
        if (monitor == currentMonitor) return
        currentMonitor = monitor
        gameBounds = monitor.bounds
        calculateNewGameBounds()
    }

    private fun setBoundsByRet(rect: Rectangle) {
        gameBounds = rect
        calculateNewGameBounds()
    }

    private fun setBoundsFallback() {
        val monitor = MonitorInfo.getAllMonitors(false)[0]
        gameBounds = monitor.bounds
        calculateNewGameBounds()
    }

    private fun calculateNewGameBounds() {
        centerOfScreen = Point(gameBounds!!.x + gameBounds!!.width / 2, gameBounds!!.y + gameBounds!!.height / 2)
        calculatePoe1UIData()
        calculatePoe2UIData()
        for (listener in listeners) listener.onGameBoundsChange()
    }

    /**
     * Attempts to update the game bounds using user's current settings.
     * This should only be used to manually force updates, like on launch or when detection method changes.
     */
    @JvmStatic
    fun forceGameBoundsRefresh() {
        val method = SaveManager.settingsSaveFile.data.gameWindowMode
        when (method) {
            GameWindowMode.DETECT ->
                /** NOTE : This currently shouldn't be reachable on non Windows platforms, just future proofing. */
                if (Platform.WINDOWS == Platform.current) {
                    val savedBounds = SaveManager.settingsSaveFile.data.detectedGameBounds
                    if (savedBounds != null) setBoundsByRet(savedBounds)
                }

            GameWindowMode.MONITOR -> {
                val monitor = SaveManager.settingsSaveFile.data.selectedMonitor
                if (monitor.exists()) setBoundsByMonitor(monitor)
                else setBoundsFallback()
            }

            GameWindowMode.SCREEN_REGION ->                 // FIXME: Implement this!
                setBoundsFallback()

            GameWindowMode.UNSET -> setBoundsFallback()
            else -> setBoundsFallback()
        }
    }

    /**
     * Center a window relative to the current "game bounds", which could be the
     * actual game window bounds, a monitor bounds, or a screen region. Also ensures
     * that the window is fully within the bounds of a single monitor.
     */
    @JvmStatic
    fun centerWindow(window: Window) {
        assert(SwingUtilities.isEventDispatchThread())
        if (gameBounds == null) {
            window.setLocationRelativeTo(null)
        } else {
            val targetWindowBounds = window.getBounds()
            val halfWidth = window.getWidth() / 2
            val halfHeight = window.getHeight() / 2
            targetWindowBounds.x = centerOfScreen!!.x - halfWidth
            targetWindowBounds.y = centerOfScreen!!.y - halfHeight
            if (!MonitorInfo.isRectWithinAMonitor(targetWindowBounds)) MonitorInfo.lockBoundsToCurrentMonitor(
                targetWindowBounds
            )
            window.setLocation(targetWindowBounds.getLocation())
        }
    }

    /** Moves a window to the respective corner of the game window, while also
     * ensuring the window is fully visible on a monitor. */
    @JvmStatic
    @JvmOverloads
    fun windowToCorner(window: Window, anchor: Anchor = Anchor.TOP_LEFT) {
        val targetBounds = window.bounds
        targetBounds.location = gameBounds!!.location
        if (anchor.isRightSide) targetBounds.x += gameBounds!!.width - window.getWidth()
        if (anchor.isBottomSide) targetBounds.y += gameBounds!!.height - window.getHeight()
        if (!MonitorInfo.isRectWithinAMonitor(targetBounds)) MonitorInfo.lockBoundsToCurrentMonitor(targetBounds)
        window.location = targetBounds.getLocation()
    }

    val stashBounds: Rectangle
        get() {
            if (App.chatParser.currentGame == Game.PATH_OF_EXILE_1) return poe1StashBonds
            else return poe2StashBonds
        }

    val stashHelperOffset: Int
        get() {
            if (App.chatParser.currentGame == Game.PATH_OF_EXILE_1) return poe1StashHelperOffset
            else return poe2StashHelperOffset
        }

    val stashCellSize: Dimension?
        get() {
            if (App.chatParser.currentGame == Game.PATH_OF_EXILE_1) return poe1StashCellSize
            else return poe2StashCellSize
        }

    val stashCellSizeQuad: Dimension?
        get() {
            if (App.chatParser.currentGame == Game.PATH_OF_EXILE_1) return poe1StashCellSizeQuad
            else return poe2StashCellSizeQuad
        }

    fun poeStashHelperOffset(): Int {
        if (App.chatParser.currentGame == Game.PATH_OF_EXILE_1) return poe1StashHelperOffset
        else return poe2StashHelperOffset
    }


    @JvmStatic
    val poe1StashBonds: Rectangle
        // Path of Exile UI Info Getters
        get() {
            if (SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder) {
                if (SaveManager.settingsSaveFile.data.settingsPoe1.tradesAreInsideFolders) return poe1StashBoundsWithFoldersInside
                else return poe1StashBoundsWithFoldersOutside
            } else return poe1StashBoundsNoFolders
        }

    @JvmStatic
    val poe1StashHelperOffset: Int
        get() {
            if (SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder) {
                return poe1StashHelperOffsetWithFolders
            } else return poe1StashHelperOffsetNoFolders
        }

    @JvmStatic
    val poe2StashBonds: Rectangle
        get() {
            if (SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder) {
                return poe2StashBoundsWithFoldersInside
            } else return poe2StashBoundsNoFolders
        }

    @JvmStatic
    val poe2StashHelperOffset: Int
        get() {
            if (SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder) {
                return poe2StashHelperOffsetWithFolders
            } else return poe2StashHelperOffsetNoFolders
        }

    private fun calculatePoe1UIData() {
        poe1StashBoundsNoFolders = ScaledRect.getScaledRect(POE_1_PERCENT_STASH_NO_FOLDERS, gameBounds)
        poe1StashBoundsWithFoldersInside = ScaledRect.getScaledRect(POE_1_PERCENT_STASH_WITH_FOLDERS_INSIDE, gameBounds)
        poe1StashBoundsWithFoldersOutside =
            ScaledRect.getScaledRect(POE_1_PERCENT_STASH_WITH_FOLDERS_OUTSIDE, gameBounds)
        val cellWidth = Math.round(poe1StashBoundsNoFolders!!.width / 12f)
        val cellHeight = Math.round(poe1StashBoundsNoFolders!!.height / 12f)
        poe1StashCellSize = Dimension(cellWidth, cellHeight)
        val quadCellWidth = Math.round(poe1StashBoundsNoFolders!!.width / 24f)
        val quadCellHeight = Math.round(poe1StashBoundsNoFolders!!.height / 24f)
        poe1StashCellSizeQuad = Dimension(quadCellWidth, quadCellHeight)
        poe1StashHelperOffsetWithFolders =
            ScaledInt.getScaledValue(POE_1_PERCENT_HELPER_OFFSET_WITH_FOLDERS, gameBounds!!.height)
        poe1StashHelperOffsetNoFolders =
            ScaledInt.getScaledValue(POE_1_PERCENT_HELPER_OFFSET_NO_FOLDERS, gameBounds!!.height)
    }

    private fun calculatePoe2UIData() {
        poe2StashBoundsNoFolders = ScaledRect.getScaledRect(POE_2_PERCENT_STASH_NO_FOLDERS, gameBounds)
        poe2StashBoundsWithFoldersInside = ScaledRect.getScaledRect(POE_2_PERCENT_STASH_WITH_FOLDERS_INSIDE, gameBounds)
        poe2StashBoundsWithFoldersOutside =
            ScaledRect.getScaledRect(POE_2_PERCENT_STASH_WITH_FOLDERS_OUTSIDE, gameBounds)
        val cellWidth = Math.round(poe2StashBoundsNoFolders!!.width / 12f)
        val cellHeight = Math.round(poe2StashBoundsNoFolders!!.height / 12f)
        poe2StashCellSize = Dimension(cellWidth, cellHeight)
        val quadCellWidth = Math.round(poe2StashBoundsNoFolders!!.width / 24f)
        val quadCellHeight = Math.round(poe2StashBoundsNoFolders!!.height / 24f)
        poe2StashCellSizeQuad = Dimension(quadCellWidth, quadCellHeight)
        poe2StashHelperOffsetWithFolders =
            ScaledInt.getScaledValue(POE_2_PERCENT_HELPER_OFFSET_WITH_FOLDERS, gameBounds!!.height)
        poe2StashHelperOffsetNoFolders =
            ScaledInt.getScaledValue(POE_2_PERCENT_HELPER_OFFSET_NO_FOLDERS, gameBounds!!.height)
    }

    @JvmStatic
    fun addListener(listener: POEWindowListener?) {
        listeners.add(listener!!)
    }

    @JvmStatic
    fun removeListener(listener: POEWindowListener?) {
        listeners.remove(listener)
    }
}
