package github.zmilla93.modules.updater

import github.zmilla93.core.utility.MarkdownParser
import github.zmilla93.modules.updater.data.AppVersion
import java.util.*

// FIXME : Refactor no updater
data class PatchNotesEntry(val versionString: String, val text: String) {

    // FIXME : These getters don't cache their values, but are needed for gson.
    //         A cleaner solution would be nice
    val appVersion get() = AppVersion(versionString)

    var html = ""
        get() {
            if (field == "") field = getCleanPatchNotes(text)
            return field
        }

    /** GSON Constructor */
    constructor() : this("", "")

    override fun toString(): String {
        return appVersion.toString()
    }

    /** Convert GitHub Markdown to HTML */
    private fun getCleanPatchNotes(body: String): String {
        val lines = body.split("(\\n|\\\\r\\\\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        for (s in lines) {
            if (s.lowercase(Locale.getDefault()).contains("how to install")) break
            builder.append(MarkdownParser.getHtmlFromMarkdown(s))
        }
        return builder.toString()
    }

}
