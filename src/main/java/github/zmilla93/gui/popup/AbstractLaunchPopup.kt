package github.zmilla93.gui.popup

import java.awt.Window

/**
 * Defines a window conditionally shown at launch.
 * Used for announcements, patch notes, tutorial, etc.
 */
abstract class AbstractLaunchPopup {

    /** Popup window */
    abstract fun window(): Window

    /** Return true if the popup should be shown. */
    abstract fun shouldPopupBeShown(): Boolean

    /** Mark the popup as having been seen. */
    abstract fun markAsSeen()

    /** Show the popup. */
    abstract fun showPopup()

    /** Flag used for debugging. Normal checks use shouldPopupBeShown. */
    var hasShownThisLaunch = false

}