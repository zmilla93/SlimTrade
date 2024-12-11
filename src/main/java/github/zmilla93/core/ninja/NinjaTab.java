package github.zmilla93.core.ninja;

import github.zmilla93.gui.ninja.NinjaVirtualTabButton;

import java.util.ArrayList;

public class NinjaTab {

    public static final int UNSCALE_STASH_SIZE = 634;

    public final String name;
    public final ArrayList<NinjaGridSection> sections;
    public final NinjaVirtualTabButton button;

    public NinjaTab(String name, ArrayList<NinjaGridSection> sections, NinjaVirtualTabButton button) {
        this.name = name;
        this.sections = sections;
        this.button = button;
    }

    public static int scaleValue(int value, int clientStashSize) {
        float percent = (float) value / NinjaTab.UNSCALE_STASH_SIZE;
        return Math.round(clientStashSize * percent);
    }

}
