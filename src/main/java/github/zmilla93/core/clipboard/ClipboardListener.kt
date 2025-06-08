package github.zmilla93.core.clipboard

/**
 * Listens for the clipboard's current string to change. See [ClipboardMonitor].
 */
interface ClipboardListener {
    fun onStringChange(value: String?)
}
