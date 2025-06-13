package github.zmilla93.core.utility

import java.util.*

/**
 * Enum representation of the 3 major OS platforms.
 * Platform.current returns the current OS.
 */
enum class Platform {
    UNKNOWN, WINDOWS, MAC, LINUX;

    private val osName =
        name.substring(0, 1).uppercase(Locale.getDefault()) + name.substring(1).lowercase(Locale.getDefault())

    override fun toString(): String {
        return osName
    }

    companion object {
        @JvmField
        val current: Platform
        val currentOS: String = System.getProperty("os.name")
        val debugOS: Platform? = null

        init {
            if (debugOS != null) {
                current = debugOS
            } else {
                if (currentOS.startsWith("Windows")) current = WINDOWS
                else if (currentOS.startsWith("Mac")) current = MAC
                else if (currentOS.startsWith("Linux")) current = LINUX
                else current = UNKNOWN
            }
        }
    }
}
