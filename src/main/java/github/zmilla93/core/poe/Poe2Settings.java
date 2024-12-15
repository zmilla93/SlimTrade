package github.zmilla93.core.poe;

public class Poe2Settings extends GameSettings {

    @Override
    public Game game() {
        return Game.PATH_OF_EXILE_2;
    }

    @Override
    public boolean isPoe1() {
        return false;
    }

}
