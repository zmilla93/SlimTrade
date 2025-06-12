package github.zmilla93.gui.popup

/**
 * Defines a window conditionally shown at launch.
 * Used for announcements, patch notes, tutorial, etc.
 */
abstract class AbstractLaunchPopup {

    /** Return true if the popup should be shown. */
    abstract fun shouldPopupBeShown(): Boolean

    /** Mark the popup as having been seen. */
    abstract fun markAsSeen()

    /** Show the popup. */
    abstract fun showPopup()

}