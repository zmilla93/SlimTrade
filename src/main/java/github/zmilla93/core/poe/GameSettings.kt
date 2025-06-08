package github.zmilla93.core.poe

import java.nio.file.Path
import java.nio.file.Paths

/**
 * Fields that are shared 1:1 between Path of Exile 1 & 2.
 */
abstract class GameSettings {
    @JvmField
    var notInstalled: Boolean = false

    @JvmField
    var installFolder: String? = null

    @JvmField
    var usingStashFolder: Boolean = false

    val clientPath: Path?
        get() {
            if (installFolder == null) return null
            val folderPath = Paths.get(installFolder!!)
            return folderPath.resolve(Paths.get(LOG_FOLDER_NAME, CLIENT_TXT_NAME))
        }

    /**
     * Check if client.txt exists.
     */
    fun doesClientLogExist(): Boolean {
        if (installFolder == null) return false
        val folderPath = Paths.get(installFolder)
        val clientPath = folderPath.resolve(Paths.get(LOG_FOLDER_NAME, CLIENT_TXT_NAME))
        return clientPath.toFile().exists()
    }

    abstract val game: Game
    abstract val isPoe1: Boolean

    companion object {
        // FIXME : Move to validator?
        const val CLIENT_TXT_NAME: String = "Client.txt"
        const val LOG_FOLDER_NAME: String = "logs"
    }
}
