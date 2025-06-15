package github.zmilla93.modules.updater

import github.zmilla93.modules.updater.data.AppVersion

// FIXME : Refactor no updater
data class PatchNotesEntry(val versionString: String, val text: String) {
//    var versionString: String = ""
//    var text: String = ""

    @Transient
    val appVersion = AppVersion(versionString)
//        get() {
//            if (field == null) field = AppVersion(versionString)
//            return field
//        }
//        private set

    /** GSON Constructor  */
    constructor() : this("", "")

    override fun toString(): String {
        return this.appVersion.toString()
    }
    
}
