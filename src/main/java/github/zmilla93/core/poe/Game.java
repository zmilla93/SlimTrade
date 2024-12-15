package github.zmilla93.core.poe;

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 * Use explicitName to add the "1" to POE1.
 */
public enum Game {

    PATH_OF_EXILE_1("Path of Exile", "Path of Exile 1", "poe1"),
    PATH_OF_EXILE_2("Path of Exile 2", "Path of Exile 2", "poe2");

    public final String name;
    public final String explicitName;
    /// Location of currency icons and translations.
    public final String assetsFolderName;

    Game(String name, String explicitName, String assetsFolderName) {
        this.name = name;
        this.explicitName = explicitName;
        this.assetsFolderName = assetsFolderName;
    }

    public boolean isPoe1() {
        return this == Game.PATH_OF_EXILE_1;
    }

    @Override
    public String toString() {
        return name;
    }

}
