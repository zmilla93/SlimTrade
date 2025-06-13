package github.zmilla93.modules.updater

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.utility.FileUtil
import github.zmilla93.core.utility.MarkdownParser
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.modules.data.HashMapList
import github.zmilla93.modules.updater.PatchNotesManager.patchNotes
import github.zmilla93.modules.updater.data.AppVersion
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.util.*
import java.util.jar.JarFile

object PatchNotesManager {

    // FIXME: Underscore
    var patchNotesFolderName = "patch_notes"
    var patchNotes = ArrayList<PatchNotesEntry>()

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
//        readLocalPatchNotes()
        if (patchNotes.isEmpty()) {
            println("Reading local patch notes (jar)")
            readLocalPatchNotesJar(patchNotesFolderName)
            if (patchNotes.isEmpty()) {
                println("Reading local patch notes (dev))")
                readLocalPatchNotesDev()
            }
        }
        if (patchNotes.isEmpty()) {
            println("Reading remote patch notes")
            patchNotes = App.updateManager.getPatchNotes(App.getAppInfo().appVersion)
        }
        return patchNotes
    }

    /** Load patch notes from resources into [patchNotes]*/
    fun readLocalPatchNotesDev() {
        try {
            val patchNotesResource = PatchNotesManager.javaClass.getResource("/$patchNotesFolderName")
            if (patchNotesResource == null) throw RuntimeException(" Resource folder '$patchNotesFolderName' not found.")
            val patchNotesFile = File(patchNotesResource.file)
            patchNotesFile.listFiles().sortedBy { it.name }.reversed().forEachIndexed { i, it ->
                val version = AppVersion(it.nameWithoutExtension)
                if (!version.valid) return
                if (version.isPreRelease && !App.getAppInfo().appVersion.isPreRelease) return@forEachIndexed
                val contents = FileUtil.resourceAsString("$patchNotesFolderName/${it.name}")
                val cleanPatchNotes = getCleanPatchNotes(version, contents, i == 0)
                patchNotes.add(PatchNotesEntry(it.nameWithoutExtension, cleanPatchNotes))
            }
            println("Read local patch notes from disk (debug).")
        } catch (e: Exception) {
            // FIXME @important : This
            ZLogger.err("Error reading local patch notes!")
            ZLogger.log(e.stackTrace)
        }
    }

    fun readLocalPatchNotesJar(path: String) {
        val resourceUrl: URL?
        try {
            resourceUrl = Thread.currentThread().contextClassLoader.getResource(path)
        } catch (e: Exception) {
            return
        }
        if (resourceUrl == null) {
            System.err.println("Null patch notes resource: $path")
            return
        }
        val jarPath = resourceUrl.path.substringAfter("file:").substringBefore("!")
        val jarFile: JarFile
        try {
            jarFile = JarFile(jarPath)
        } catch (_: FileNotFoundException) {
            System.err.println("Failed to read patch notes from jar file. This is normal when running in the editor.")
            return
        }
        val entries = jarFile.entries()
        val foundVersions = mutableListOf<AppVersion>()
        val pathWithSlash = if (!path.endsWith("/")) "$path/" else path
        while (entries.hasMoreElements()) {
            val name = entries.nextElement().name
            if (name.startsWith(pathWithSlash) && name != pathWithSlash) {
                val relative = name.removePrefix(pathWithSlash)
                // Ignore directories
                if (!relative.contains("/")) {
                    val version = AppVersion(relative.removeSuffix(".txt"))
                    if (version.valid) foundVersions.add(version)
                }
            }
        }
        ZLogger.log("Found ${foundVersions.size} patch note files.")
        var first = true
        foundVersions.sortedBy { it }.reversed().forEach {
            if (it.isPreRelease) return@forEach
            val text = ZUtil.readResourceFileAsString("$patchNotesFolderName/v$it.txt")
            val cleanPatchNotes = getCleanPatchNotes(it, text, first)
            patchNotes.add(PatchNotesEntry(it.toString(), cleanPatchNotes))
            first = false
        }
        ZLogger.log("Patch notes count: ${patchNotes.size}")
    }

    /** Convert GitHub Markdown to HTML */
    private fun getCleanPatchNotes(version: AppVersion?, body: String, addExtraInfo: Boolean): String {
        val lines = body.split("(\\n|\\\\r\\\\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val builder = StringBuilder()
        builder.append("<h1>SlimTrade ").append(version).append("</h1>")
        for (s in lines) {
            if (s.lowercase(Locale.getDefault()).contains("how to install")) break
            builder.append(MarkdownParser.getHtmlFromMarkdown(s))
        }
        return builder.toString()
    }

}