package github.zmilla93.core.poe

import github.zmilla93.core.event.ParserEvent

/**
 * Static game info about Path of Exile 1 & 2.
 * For mutable game settings, use [GameSettings].
 */
enum class Game(
    val gameName: String,
    @JvmField val explicitName: String,
    /** Location of currency icons and translations. */
    @JvmField val assetsFolderName: String,
    val parser: ParserEvent.Parser,
) {
    PATH_OF_EXILE_1("Path of Exile", "Path of Exile 1", "poe1", ParserEvent.Parser.POE1),
    PATH_OF_EXILE_2("Path of Exile 2", "Path of Exile 2", "poe2", ParserEvent.Parser.POE2);

    val isPoe1: Boolean get() = this == PATH_OF_EXILE_1

    override fun toString(): String {
        return gameName
    }

}
