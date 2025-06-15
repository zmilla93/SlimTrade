package github.zmilla93.core.data

import github.zmilla93.core.enums.MatchType
import github.zmilla93.gui.options.stash.StashTabType

class StashTabData {
    @JvmField
    val stashTabName: String

    @JvmField
    val matchType: MatchType

    @JvmField
    val stashTabType: StashTabType

    @JvmField
    val stashColorIndex: Int

    /** GSON Constructor  */
    constructor() : this("", MatchType.EXACT_MATCH, StashTabType.NORMAL, 0)

    constructor(name: String, matchType: MatchType, stashTabType: StashTabType, stashColorIndex: Int) {
        this.stashTabName = name
        this.matchType = matchType
        this.stashTabType = stashTabType
        this.stashColorIndex = stashColorIndex
    }
}
