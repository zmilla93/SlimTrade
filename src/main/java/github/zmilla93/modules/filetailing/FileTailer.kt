package github.zmilla93.modules.filetailing

import github.zmilla93.core.utility.ZUtil
import java.io.BufferedReader
import java.io.IOException
import java.nio.file.Path

/**
 * Simple file tailer implementation.
 */
class FileTailer(
    path: Path?,
    isPathRelative: Boolean,
    private val listener: FileTailerListener,
    private val delay: Int,
    private val end: Boolean
) : Runnable {
    private val reader: BufferedReader = ZUtil.getBufferedReader(path, isPathRelative)
    private var running = false
    var isLoaded: Boolean = false
        private set

    fun stop() {
        running = false
    }

    override fun run() {
        listener.init(this)
        try {
            while (running) {
                while (running && reader.ready()) {
                    val line = reader.readLine()
                    if (this.isLoaded || !end) {
                        listener.handle(line)
                    }
                }
                if (!this.isLoaded) {
                    this.isLoaded = true
                    listener.onLoad()
                }
                try {
                    Thread.sleep(delay.toLong())
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    companion object {
        /**
         * Creates a tailer and runs it on a background thread.
         */
        @JvmStatic
        fun createTailer(
            path: Path?,
            isPathRelative: Boolean,
            listener: FileTailerListener,
            delay: Int,
            end: Boolean
        ): FileTailer {
            val tailer = FileTailer(path, isPathRelative, listener, delay, end)
            launchThread(tailer)
            return tailer
        }

        private fun launchThread(tailer: FileTailer) {
            val thread = Thread(tailer)
            thread.setDaemon(true)
            thread.start()
            tailer.running = true
        }
    }
}
