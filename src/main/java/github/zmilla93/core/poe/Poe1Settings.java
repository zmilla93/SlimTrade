package github.zmilla93.core.poe;

public class Poe1Settings extends GameSettings {

    @Override
    public Game game() {
        return Game.PATH_OF_EXILE_1;
    }

    @Override
    public boolean isPoe1() {
        return true;
    }

}
