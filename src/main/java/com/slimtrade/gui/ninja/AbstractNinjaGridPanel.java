package com.slimtrade.gui.ninja;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.NinjaConfigParser;
import com.slimtrade.core.ninja.NinjaGridSection;
import com.slimtrade.core.ninja.NinjaTab;
import com.slimtrade.core.ninja.NinjaTabType;
import com.slimtrade.core.utility.NinjaInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ThemeLineBorder;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.ThemeManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Renders an overlay on the stash that displays prices from poe.ninja.
 * Handles a single tab (and any sub tabs, like with currency, fragments, etc.)
 */
public abstract class AbstractNinjaGridPanel extends JPanel implements ISaveListener {

    private final ArrayList<NinjaGridSection> gridSections = new ArrayList<>();
    private final HashMap<String, ArrayList<NinjaGridSection>> tabSectionMap = new HashMap<>();
    private static final boolean DRAW_CELL_BORDERS = false;

    public static final Color TEXT_COLOR = new Color(255, 182, 81);
    public static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 150);

    private final ArrayList<Rectangle> buttonRects = new ArrayList<>();
    private final HashMap<Rectangle, String> buttonMap = new HashMap<>();
    private final HashSet<String> tabNames = new HashSet<>();
    private String pressedTab;
    private String selectedTab = "Scarabs";

    public AbstractNinjaGridPanel(String layoutFileName) {
        setBackground(ThemeManager.TRANSPARENT);
        setBorder(new ThemeLineBorder());
        updateSize();
        if (layoutFileName != null) {
            buildLayoutFromFile(layoutFileName);
            addListeners();
        }
    }

    private void buildLayoutFromFile(String fileName) {
        BufferedReader reader = ZUtil.getBufferedReader("/ninja/" + fileName + ".txt", true);
        ArrayList<String> lines = new ArrayList<>();
        try {
            while (reader.ready())
                lines.add(reader.readLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        HashMap<String, NinjaTab> map = NinjaConfigParser.parse(lines.toArray(new String[0]));
        for (Map.Entry<String, NinjaTab> entry : map.entrySet()) {
            NinjaTab tab = entry.getValue();
            if (tab.button != null) addTabButton(tab.button);
            for (NinjaGridSection section : entry.getValue().sections)
                addSection(entry.getKey(), section);
        }
    }

    private void addListeners() {
        // FIXME: Switching to a global mouse listener avoids having to draw buttons,
        //        which currently causes the mouse cursor to change on button hover.
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                pressedTab = getTabNameAtPoint(e.getPoint());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (pressedTab == null) return;
                String tab = getTabNameAtPoint(e.getPoint());
                if (tab == null) return;
                if (tab.equals(pressedTab) && !tab.equals(selectedTab)) {
                    selectedTab = tab;
                    repaint();
                }
            }
        });
    }

    public abstract NinjaTabType getTabType();

    private String getTabNameAtPoint(Point point) {
        for (Rectangle rect : buttonRects) {
            if (rect.contains(point) && buttonMap.containsKey(rect)) {
                return buttonMap.get(rect);
            }
        }
        return null;
    }

    protected void addTabButton(NinjaVirtualTabButton button) {
        tabSectionMap.put(button.name, new ArrayList<>());
        tabNames.add(button.name);
        buttonRects.add(button.rect);
        buttonMap.put(button.rect, button.name);
    }

    protected void addSection(String tab, NinjaGridSection section) {
        if (!tabNames.contains(tab)) {
            System.err.println("Ninja grid panel attempted to add section to non existent tab: " + tab);
            return;
        }
        ArrayList<NinjaGridSection> sectionList = tabSectionMap.get(tab);
        gridSections.add(section);
        sectionList.add(section);
    }

    private void updateSize() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        setMinimumSize(new Dimension(rect.width, rect.height));
        setPreferredSize(new Dimension(rect.width, rect.height));
        setSize(new Dimension(rect.width, rect.height));
    }

    private void drawTabButtons(Graphics g) {
        for (Rectangle rect : buttonRects) {
            g.setColor(ThemeManager.TRANSPARENT_CLICKABLE);
            g.fillRect(rect.x, rect.y, rect.width, rect.height);
            g.setColor(Color.WHITE);
            g.drawRect(rect.x, rect.y, rect.width, rect.height);
        }
    }

    private void drawGridSection(Graphics g, NinjaGridSection section) {
        for (int y = 0; y < section.data.length; y++) {
            for (int x = 0; x < section.data[y].length; x++) {
                String value = section.data[y][x];
                if (value == null) continue;
                drawCell(g, section, x, y, value);
            }
        }
    }

    private void drawCell(Graphics g, NinjaGridSection section, int x, int y, String value) {
        if (value.equals("NULL")) return;
        if (selectedTab.equals("General")) {
            drawText(g, section, x, y, NinjaInterface.getFragment(value).toString());
        } else if (selectedTab.equals("Scarabs")) {
            drawText(g, section, x, y, NinjaInterface.getScarab(value).toString());
        }
    }

    private void drawText(Graphics g, NinjaGridSection section, int x, int y, String text) {
        int cellSize = section.cellSize;
        int cellX = section.x + (x * cellSize + x * section.spacingX);
        int cellY = section.y + (y * cellSize + y * section.spacingY);
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 12));
        FontMetrics fontMetrics = g.getFontMetrics();
        Rectangle2D textBounds = fontMetrics.getStringBounds(text, g);
        g.setColor(BACKGROUND_COLOR);
        g.fillRect(cellX, cellY + cellSize - fontMetrics.getAscent(), (int) textBounds.getWidth(), (int) textBounds.getHeight());
        g.setColor(TEXT_COLOR);
        g.drawString(text, cellX, cellY + cellSize);
        if (DRAW_CELL_BORDERS) {
            g.setColor(TEXT_COLOR);
            g.drawRect(cellX, cellY, cellSize, cellSize);
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ZUtil.clearTransparentComponent(g, this);
        drawTabButtons(g);
        if (selectedTab == null) return;
        ArrayList<NinjaGridSection> sections = tabSectionMap.get(selectedTab);
        if (sections == null) return;
        for (NinjaGridSection section : sections) {
            drawGridSection(g, section);
        }
    }

    @Override
    public void onSave() {
        updateSize();
    }

}
