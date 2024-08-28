package com.slimtrade.core.ninja;

import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.ninja.NinjaVirtualTabButton;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class NinjaConfigParser {

    private static String currentTab;
    private static NinjaVirtualTabButton currentTabButton;
    private static int cellSize;
    private static int cellSpacingX;
    private static int cellSpacingY;
    private static int sectionX;
    private static int sectionY;

    private static ArrayList<String[]> sectionRows = new ArrayList<>();
    private static ArrayList<NinjaGridSection> sections = new ArrayList<>();
    private static final HashMap<String, NinjaTab> tabMap = new HashMap<>();

    public static HashMap<String, NinjaTab> parse(String[] lines) {
        tabMap.clear();
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("//") || line.startsWith("=")) continue;
            if (line.startsWith("#")) startNewSection(line);
            else if (line.contains(":")) setKeyValue(line);
            else parseCSV(line);
        }
        finishTab();
        return tabMap;
    }

    // Start a new section using '# sectionX, sectionY'
    private static void startNewSection(String line) {
        appendCurrentSection();
        line = line.replace("#", "");
        String[] values = line.split(",");
        assert (values.length == 2);
        sectionX = Integer.parseInt(values[0].trim());
        sectionY = Integer.parseInt(values[1].trim());
    }

    // Appends the current section to the current tab's list of sections
    private static void appendCurrentSection() {
        if (!sectionRows.isEmpty()) {
            NinjaGridSection section = new NinjaGridSection(cellSize, sectionX, sectionY, cellSpacingX, cellSpacingY, sectionRows.toArray(new String[0][]));
            sections.add(section);
        }
        sectionRows = new ArrayList<>();
    }

    // Set a value using 'key:value'
    private static void setKeyValue(String line) {
        String[] values = line.split(":");
        String key = values[0];
        String value = values[1];
        if (key.equals("tab")) startNewTab(value);
        else if (key.equals("cellSize")) cellSize = parseInt(value);
        else if (key.equals("spacingX")) cellSpacingX = parseInt(value);
        else if (key.equals("spacingY")) cellSpacingY = parseInt(value);
        else if (key.equals("spacing")) {
            String[] offsets = value.split(",");
            cellSpacingX = parseInt(offsets[0]);
            cellSpacingY = parseInt(offsets[1]);
        } else if (key.equals("spacingXY")) {
            int offset = parseInt(value);
            cellSpacingX = offset;
            cellSpacingY = offset;
        } else if (key.equals("button")) {
            int[] bounds = ZUtil.csvToIntArray(value);
            Rectangle rect = new Rectangle(bounds[0], bounds[1], bounds[2], bounds[3]);
            currentTabButton = new NinjaVirtualTabButton(currentTab, rect);
        }
    }

    private static int parseInt(String value) {
        return Integer.parseInt(value.trim());
    }

    private static void startNewTab(String tabName) {
        finishTab();
        tabName = tabName.trim();
        sections = new ArrayList<>();
        currentTab = tabName;
    }

    private static void finishTab() {
        appendCurrentSection();
        NinjaTab tab = new NinjaTab(currentTab, sections, currentTabButton);
        tabMap.put(currentTab, tab);
        sections = new ArrayList<>();
        currentTab = null;
        currentTabButton = null;
    }

    // Try to parse the line as a CSV
    private static void parseCSV(String line) {
        if (line.isEmpty()) return;
        String[] entries = line.trim().split(",");
        for (int i = 0; i < entries.length; i++)
            entries[i] = entries[i].trim();
        if (entries.length == 0) return;
        sectionRows.add(entries);
    }

}
