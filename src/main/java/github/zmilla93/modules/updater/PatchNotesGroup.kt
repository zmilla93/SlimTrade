package github.zmilla93.modules.updater

/** A list of patch notes that can be displayed together. */
class PatchNotesGroup(val tag: String, val patchNotesList: List<PatchNotesEntry>) {

    val combinedPatchNotes: String

    init {
        val text = StringBuilder()
        patchNotesList.sortedBy { it.appVersion }.reversed().forEach {
            text.append(it.text)
        }
        combinedPatchNotes = text.toString()
    }

    override fun toString(): String {
        return tag
    }

}