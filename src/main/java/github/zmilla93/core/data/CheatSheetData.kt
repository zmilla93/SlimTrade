package github.zmilla93.core.data

import github.zmilla93.core.hotkeys.HotkeyData
import java.io.File

class CheatSheetData @JvmOverloads constructor(
    @JvmField val fileName: String,
    @JvmField val hotkeyData: HotkeyData?
) {
//    @JvmField
//    val hotkeyData: HotkeyData
//    private var title: String
//    private var extension: String

    // FIXME : File info is immutable, but hotkey data (and potential future settings like size/opacity) is not.
    //         If more features are added, file info should be moved to its own class to make this more clear.
    /** GSON Constructor */
    constructor() : this("", HotkeyData())

//    init {
//        val file = File(fileName)
//        title = file.nameWithoutExtension
//        extension = file.extension.lowercase()
//        this.hotkeyData = hotkeyData
//    }

    fun title(): String {
        return File(fileName).nameWithoutExtension
    }

    fun extension(): String {
        return File(fileName).extension
    }

    fun hasImageExtension(): Boolean {
        when (extension().lowercase()) {
            "png", "jpg", "jpeg", "gif" -> return true
        }
        return false
    }

//    private val fileData: Unit
//        get() {
//            if (title != null && extension != null) return
//            val extIndex = fileName.lastIndexOf('.')
//            title = fileName.substring(0, extIndex)
//            extension = fileName.substring(extIndex + 1)
//        }
}
