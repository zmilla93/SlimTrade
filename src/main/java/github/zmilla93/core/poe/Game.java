package github.zmilla93.core.poe;

/**
 * An enum for knowing which version of Path of Exile is being referenced.
 * The toString() value also matches the name of the Path of Exile's install folder, and the game's window title.
 */
public enum Game {

    PATH_OF_EXILE_1("Path of Exile", "Path of Exile 1"), PATH_OF_EXILE_2("Path of Exile 2");

    private final String name;
    private final String explicitName;

    /**
     * Returns "Path of Exile 1" instead of "Path of Exile" when used on POE1
     */
    public String getExplicitName() {
        return explicitName;
    }

    Game(String name) {
        this.name = name;
        this.explicitName = name;
    }

    Game(String name, String explicitName) {
        this.name = name;
        this.explicitName = explicitName;
    }

    public boolean isPoe1() {
        return this == Game.PATH_OF_EXILE_1;
    }

    @Override
    public String toString() {
        return name;
    }

}
