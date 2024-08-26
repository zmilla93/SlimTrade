package com.slimtrade.gui.ninja;

import com.slimtrade.core.ninja.NinjaConfigParser;
import com.slimtrade.core.ninja.NinjaGridSection;
import com.slimtrade.core.utility.ZUtil;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NinjaFragmentsPanel extends AbstractNinjaGridPanel {

    public NinjaFragmentsPanel() {

        // FIXME: Move buttons to config file
        addTab("General", new Rectangle(12, 10, 138, 33));
        addTab("Scarabs", new Rectangle(169, 10, 138, 33));
        addTab("Breach", new Rectangle(326, 10, 138, 33));
        addTab("Eldrich", new Rectangle(483, 10, 138, 33));

        BufferedReader reader = ZUtil.getBufferedReader("/ninja/fragments.txt", true);
        if (reader == null) return;
        ArrayList<String> lines = new ArrayList<>();
        try {
            while (reader.ready())
                lines.add(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, ArrayList<NinjaGridSection>> map = NinjaConfigParser.parse(lines.toArray(new String[0]));
        for (Map.Entry<String, ArrayList<NinjaGridSection>> entry : map.entrySet()) {
            for (NinjaGridSection section : entry.getValue()) {
                addSection(entry.getKey(), section);
            }
        }
    }

}
