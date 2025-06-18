package github.zmilla93.modules.updater

import github.zmilla93.App
import github.zmilla93.core.References
import github.zmilla93.core.utility.FileUtil
import github.zmilla93.core.utility.ZUtil
import github.zmilla93.modules.data.HashMapList
import github.zmilla93.modules.updater.PatchNotesManager.localPatchNotes
import github.zmilla93.modules.updater.data.AppVersion
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileNotFoundException
import java.net.URL
import java.util.jar.JarFile

object PatchNotesManager {

    // FIXME: Underscore
    /** That patch notes from GitHub. */
    var remotePatchNotes = ArrayList<PatchNotesEntry>()

    /** The patch notes saved locally. */
    var localPatchNotes = ArrayList<PatchNotesEntry>()

    var combinedPatchNotes = ArrayList<PatchNotesEntry>()

    /** The resource folder the patch notes are saved in. */
    private const val patchNotesFolderName = "patch_notes"

    private val logger = LoggerFactory.getLogger(PatchNotesManager.javaClass.simpleName)

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

    // FIXME @important : This will break when moving to 1.0.0
    fun patchNotesMyMinor(): HashMapList<String, PatchNotesEntry> {
        val map = HashMapList<String, PatchNotesEntry>()
        getPatchNotes().forEach {
            map.put(it.appVersion.minorGroupTag, it)
        }
        return map
    }

    fun getPatchNotes(remote: Boolean = false): List<PatchNotesEntry> {
//        if (true) return patchNotes
        if (remotePatchNotes.isEmpty()) {
//            logger.info("Reading remote patch notes from GitHub.")
            remotePatchNotes = App.updateManager.getPatchNotes(App.getAppInfo().appVersion)
        }
//        if (localPatchNotes.isEmpty() && remotePatchNotes.isNotEmpty()) {
//            logger.info("Reading local patch notes.")
//            readLocalPatchNotes()
//        }
        return remotePatchNotes
    }

    fun readLocalPatchNotes() {
//        println("Reading: " + patchNotesEntry.appVersion)
        var success = 0
        remotePatchNotes.forEach {
            val resourceName = "$patchNotesFolderName/v${it.appVersion}.txt"
            try {
                val data = ZUtil.readResourceFileAsString(resourceName)
                localPatchNotes.add(PatchNotesEntry(it.appVersion.toString(), data))
                success++
            } catch (e: Exception) {
                logger.error("Resource not found: $resourceName")
            }
        }

    }


    /** Load patch notes from resources into [localPatchNotes]*/
    fun readLocalPatchNotesDev() {
        try {
            val patchNotesResource = PatchNotesManager.javaClass.getResource("/$patchNotesFolderName")
            if (patchNotesResource == null) throw RuntimeException("Resource folder '$patchNotesFolderName' not found.")
            val patchNotesFile = File(patchNotesResource.file)
            patchNotesFile.listFiles().sortedBy { it.name }.reversed().forEachIndexed { i, it ->
                val version = AppVersion(it.nameWithoutExtension)
                if (!version.valid) return
                if (version.isPreRelease && !App.getAppInfo().appVersion.isPreRelease) return@forEachIndexed
                val contents = FileUtil.resourceAsString("$patchNotesFolderName/${it.name}")
//                val cleanPatchNotes = getCleanPatchNotes(version, contents, i == 0)
                localPatchNotes.add(PatchNotesEntry(it.nameWithoutExtension, contents))
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
        // FIXME @important : This line causes an error for some users, currently caught one level up
        val jarPath: String
        try {
            jarPath = resourceUrl.path.substringAfter("file:").substringBefore("!")
        } catch (e: Exception) {
            ZLogger.err("Failed to load resource: $resourceUrl")
            ZLogger.err(e.stackTrace)
            return
        }
        val jarFile: JarFile
        try {
            jarFile = JarFile(jarPath)
        } catch (_: FileNotFoundException) {
            ZLogger.err("Failed to read patch notes from jar file. This is normal when running in the editor.")
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
//            val cleanPatchNotes = getCleanPatchNotes(it, text, first)
            localPatchNotes.add(PatchNotesEntry(it.toString(), text))
            first = false
        }
        ZLogger.log("Patch notes count: ${localPatchNotes.size}")
    }

//    /** Convert GitHub Markdown to HTML */
//    private fun getCleanPatchNotes(version: AppVersion?, body: String, addExtraInfo: Boolean): String {
//        val lines = body.split("(\\n|\\\\r\\\\n)".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
//        val builder = StringBuilder()
//        builder.append("<h1>SlimTrade ").append(version).append("</h1>")
//        for (s in lines) {
//            if (s.lowercase(Locale.getDefault()).contains("how to install")) break
//            builder.append(MarkdownParser.getHtmlFromMarkdown(s))
//        }
//        return builder.toString()
//    }

}