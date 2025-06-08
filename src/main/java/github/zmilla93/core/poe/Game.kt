package github.zmilla93.core.poe

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 * Use explicitName to add the "1" to POE1.
 */
enum class Game(
    val gameName: String,
    @JvmField val explicitName: String,
    /** Location of currency icons and translations. */
    @JvmField val assetsFolderName: String
) {
    PATH_OF_EXILE_1("Path of Exile", "Path of Exile 1", "poe1"),
    PATH_OF_EXILE_2("Path of Exile 2", "Path of Exile 2", "poe2");

    val isPoe1: Boolean get() = this == PATH_OF_EXILE_1

    override fun toString(): String {
        return gameName
    }

}
