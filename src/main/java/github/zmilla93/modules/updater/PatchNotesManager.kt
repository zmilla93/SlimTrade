package github.zmilla93.modules.updater

import github.zmilla93.core.utility.FileUtil
import github.zmilla93.modules.data.HashMapList
import github.zmilla93.modules.updater.data.AppVersion
import java.io.File

object PatchNotesManager {

    // FIXME: Underscore
    var patchNotesFolderName = "patch_notes"
    var localPatchNotes = ArrayList<PatchNotesEntry>()

    fun patchNotesByMajor(): HashMapList<Int, PatchNotesEntry> {
        val map = HashMapList<Int, PatchNotesEntry>()
        getPatchNotes().forEach {
            map.put(it.appVersion.major, it)
        }
        println("MAJOR")
        println(map)
        return map
    }

    fun patchNotesMyMinor(): HashMapList<String, PatchNotesEntry> {
        val map = HashMapList<String, PatchNotesEntry>()
        getPatchNotes().forEach {
            map.put(it.appVersion.minorGroupTag, it)
        }
        println("MINOR")
        println(map)
        return map
    }

    fun getPatchNotes(remote: Boolean = false): List<PatchNotesEntry> {
        if (remote) {
            // FIXME : Add support for remote patch notes
        }
        readLocalPatchNotes()
        return localPatchNotes
    }

    fun readLocalPatchNotes() {
//        val entries = ArrayList<PatchNotesEntry>()
        if (localPatchNotes.isNotEmpty()) return
        val patchNotesResource = PatchNotesManager.javaClass.getResource("/$patchNotesFolderName")
        if (patchNotesResource == null) throw RuntimeException("Resource folder '$patchNotesFolderName' not found.")
        val patchNotesFile = File(patchNotesResource.file)
        patchNotesFile.listFiles().forEach {
//            println(it.name)
            val version = AppVersion(it.nameWithoutExtension)
            val contents = FileUtil.resourceAsString("$patchNotesFolderName/${it.name}")
//            println(contents)
//            localPatchNotes.add(PatchNotesEntry(it.nameWithoutExtension))
//            println("VER: " + version.minor + ":" + version.patch)
            localPatchNotes.add(PatchNotesEntry(it.nameWithoutExtension, contents))
        }
//        return entries
    }

}