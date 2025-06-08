package github.zmilla93.core.clipboard

import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.DataFlavor
import java.awt.datatransfer.UnsupportedFlavorException
import java.io.IOException

/**
 * Detects when the clipboard's currently copied string changes, and informs [ClipboardListener]s.
 * A better solution would be using jna callbacks, but that is more complicated and Windows only.
 * This is a good starting solution that can always be used as a cross-platform fallback.
 */
object ClipboardMonitor {
    private val clipboard: Clipboard = Toolkit.getDefaultToolkit().getSystemClipboard()
    private var thread: Thread? = null
    private var currentString: String? = null
    private var running = false
    private var initialized = false
    private const val sleepDurationMs = 200
    private val listeners = ArrayList<ClipboardListener>()

    fun addListener(listener: ClipboardListener?) {
        listeners.add(listener!!)
        startThread()
    }

    fun removeListener(listener: ClipboardListener?) {
        listeners.remove(listener)
        if (listeners.isEmpty()) stopThread()
    }

    private fun tryUpdateClipboardContents() {
        try {
            val contents = clipboard.getContents(DataFlavor.stringFlavor)
            if (!contents.isDataFlavorSupported(DataFlavor.stringFlavor)) return
            val newString = contents.getTransferData(DataFlavor.stringFlavor) as String
            if (newString != currentString) {
                currentString = newString
                if (initialized) {
                    for (listener in listeners) listener.onStringChange(newString)
                }
                initialized = true
            }
        } catch (_: UnsupportedFlavorException) {
        } catch (_: IOException) {
        }
    }

    private fun stopThread() {
        if (thread == null) return
        thread!!.interrupt()
        running = false
    }

    private fun startThread() {
        if (running) return
        running = true
        initialized = false
        thread = Thread(Runnable {
            println("Starting clipboard monitoring thread.")
            while (true) {
                tryUpdateClipboardContents()
                try {
                    Thread.sleep(sleepDurationMs.toLong())
                } catch (_: InterruptedException) {
                    println("Clipboard monitoring thread stopped.")
                    return@Runnable
                }
            }
        })
        thread!!.start()
    }
}
