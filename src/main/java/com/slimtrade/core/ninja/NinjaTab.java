package com.slimtrade.core.ninja;

import com.slimtrade.gui.ninja.NinjaVirtualTabButton;

import java.util.ArrayList;

public class NinjaTab {

    public final String name;
    public final ArrayList<NinjaGridSection> sections;
    public final NinjaVirtualTabButton button;

    public NinjaTab(String name, ArrayList<NinjaGridSection> sections, NinjaVirtualTabButton button) {
        this.name = name;
        this.sections = sections;
        this.button = button;
    }

}
