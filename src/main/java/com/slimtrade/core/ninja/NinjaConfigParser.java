package com.slimtrade.core.ninja;

import java.util.ArrayList;
import java.util.HashMap;

public class NinjaConfigParser {

    private static String currentTab;
    private static int cellSize;
    private static int cellSpacingX;
    private static int cellSpacingY;
    private static int sectionX;
    private static int sectionY;


    private static ArrayList<String[]> sectionRows = new ArrayList<>();
    private static ArrayList<NinjaGridSection> sections = new ArrayList<>();
    private static HashMap<String, ArrayList<NinjaGridSection>> tabMap = new HashMap<>();

    public static HashMap<String, ArrayList<NinjaGridSection>> parse(String[] lines) {
        tabMap.clear();
        for (String line : lines) {
            line = line.trim();
            if (line.startsWith("//")) continue;
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
        else if (key.equals("offsetX")) cellSpacingX = parseInt(value);
        else if (key.equals("offsetY")) cellSpacingY = parseInt(value);
        else if (key.equals("offset")) {
            int offset = parseInt(value);
            cellSpacingX = offset;
            cellSpacingY = offset;
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
        if (sections.isEmpty()) return;
        if (currentTab == null) return;
        tabMap.put(currentTab, sections);
        sections = new ArrayList<>();
        currentTab = null;
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
