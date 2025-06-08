package github.zmilla93.modules.updater

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.utility.FileUtil
import github.zmilla93.core.utility.MarkdownParser
import github.zmilla93.modules.data.HashMapList
import github.zmilla93.modules.updater.PatchNotesManager.localPatchNotes
import github.zmilla93.modules.updater.data.AppVersion
import java.io.File
import java.util.*

object PatchNotesManager {

    // FIXME: Underscore
    var patchNotesFolderName = "patch_notes"
    var localPatchNotes = ArrayList<PatchNotesEntry>()

    private val PREFIX =
        "**Enjoying the app? Consider supporting on [Patreon](" + References.PATREON_URL + ") or [PayPal](" + References.PAYPAL_URL + ")!**<br>"
    private val SUFFIX =
        "*Want to report a bug or give feedback? Post on [GitHub](" + References.GITHUB_ISSUES_URL + ") or join the [Discord](" + References.DISCORD_INVITE + ")!*"

    fun patchNotesByMajor(): HashMapList<Int, PatchNotesEntry> {
        val map = HashMapList<Int, PatchNotesEntry>()
        getPatchNotes().forEach {
            map.put(it.appVersion.major, it)
        }
        return map
    }

    fun patchNotesMyMinor(): HashMapList<String, PatchNotesEntry> {
        val map = HashMapList<String, PatchNotesEntry>()
        getPatchNotes().forEach {
            map.put(it.appVersion.minorGroupTag, it)
        }
        return map
    }

    fun getPatchNotes(remote: Boolean = false): List<PatchNotesEntry> {
        if (remote) {
            // FIXME : Add support for remote patch notes
        }
        readLocalPatchNotes()
        return localPatchNotes
    }

    /** Load patch notes from resources into [localPatchNotes]*/
    fun readLocalPatchNotes() {
        if (localPatchNotes.isNotEmpty()) return
        val patchNotesResource = PatchNotesManager.javaClass.getResource("/$patchNotesFolderName")
        if (patchNotesResource == null) throw RuntimeException("Resource folder '$patchNotesFolderName' not found.")
        val patchNotesFile = File(patchNotesResource.file)
        patchNotesFile.listFiles().sortedBy { it.name }.reversed().forEachIndexed { i, it ->
            val version = AppVersion(it.nameWithoutExtension)
            if (version.isPreRelease && !App.getAppInfo().appVersion.isPreRelease) return@forEachIndexed
            val contents = FileUtil.resourceAsString("$patchNotesFolderName/${it.name}")
            val cleanPatchNotes = getCleanPatchNotes(version, contents, i == 0)
            localPatchNotes.add(PatchNotesEntry(it.nameWithoutExtension, cleanPatchNotes))
        }
    }

    /** Convert GitHub Markdown to HTML */
    private fun getCleanPatchNotes(version: AppVersion?, body: String, addExtraInfo: Boolean): String {
        val lines = body.split("(\\n|\\\\r\\\\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        builder.append("<h1>SlimTrade ").append(version).append("</h1>")
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(PREFIX))
        for (s in lines) {
            if (s.lowercase(Locale.getDefault()).contains("how to install")) {
                break
            }
            builder.append(MarkdownParser.getHtmlFromMarkdown(s))
        }
        if (addExtraInfo) builder.append(MarkdownParser.getHtmlFromMarkdown(SUFFIX))
        return builder.toString()
    }

}