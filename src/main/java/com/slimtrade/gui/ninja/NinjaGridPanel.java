package com.slimtrade.gui.ninja;

import com.slimtrade.core.managers.SaveManager;
import com.slimtrade.core.ninja.*;
import com.slimtrade.core.ninja.responses.INinjaEntry;
import com.slimtrade.core.utility.NinjaInterface;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.gui.components.ThemeLineBorder;
import com.slimtrade.modules.saving.ISaveListener;
import com.slimtrade.modules.theme.ThemeManager;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

/**
 * Renders an overlay on the stash that displays prices from poe.ninja.
 * Handles a single tab (and any sub tabs, like with currency, fragment, etc.)
 */
public class NinjaGridPanel extends JPanel implements ISaveListener, NinjaMouseAdapter, NinjaSyncListener {

    // FIXME: Make this fully support panels with no tabs
    private final ArrayList<NinjaGridSection> fullSectionList = new ArrayList<>();
    private final HashMap<String, ArrayList<NinjaGridSection>> tabSectionMap = new HashMap<>();
    private static final boolean DRAW_CELL_BORDERS = false;

    public static final Color TEXT_COLOR = new Color(255, 182, 81);
    //    public static final Color TEXT_COLOR = Color.WHITE;
    public static final Color BACKGROUND_COLOR = new Color(0, 0, 0, 150);
    private Image chaosImage;
    private Image divineImage;

    private boolean hasTabs;
    private final ArrayList<NinjaVirtualTabButton> buttonList = new ArrayList<>();
    private final HashMap<Rectangle, String> buttonMap = new HashMap<>();
    private final HashSet<String> tabNames = new HashSet<>();
    private String pressedTab;
    private String currentTab = null;
    private ArrayList<NinjaGridSection> currentSections = new ArrayList<>();
    public final NinjaTabType tabType;
    private int hoverYValue;
    private static final int HOVER_Y_BUFFER = 4;
    private boolean hasBeenSynced = NinjaInterface.useLocalDatasets;

    private int textHeight;
    private int fontMetricAscent;


    public NinjaGridPanel(NinjaTabType tabType) {
        this.tabType = tabType;
        setBackground(ThemeManager.TRANSPARENT);
        setBorder(new ThemeLineBorder());
        updateSize();
        if (tabType != null) {
            buildLayoutFromFile(tabType.toString().toLowerCase());
            SaveManager.stashSaveFile.addListener(this);
            NinjaInterface.addSyncListener(this, tabType.dependencies);
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
        // FIXME: Turn parser return into an array of objects to preserve order
        //        Until then, the initial tab is wrong
        HashMap<String, NinjaTab> map = NinjaConfigParser.parse(lines.toArray(new String[0]));
        boolean initCurrentTab = false;
        for (Map.Entry<String, NinjaTab> entry : map.entrySet()) {
            NinjaTab tab = entry.getValue();
            addTab(tab);
            for (NinjaGridSection section : tab.sections)
                addSection(entry.getKey(), section);
            if (tab.button != null && !hasTabs) {
                hasTabs = true;
                setCurrentTab(tab.button.name);
                initCurrentTab = true;
            }
        }
        if (!initCurrentTab) setCurrentTab("");
    }

    private void resizeIcons(int size) {
        try {
            chaosImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/currency/Chaos_Orb.png"))).getScaledInstance(size, size, Image.SCALE_SMOOTH);
            divineImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/currency/Divine_Orb.png"))).getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void rebuildButtonMap() {
        buttonMap.clear();
        for (NinjaVirtualTabButton button : buttonList) buttonMap.put(button.rect, button.name);
    }

    // FIXME : Should this just return the tab object itself?
    private String getTabNameAtPoint(Point point) {
        for (NinjaVirtualTabButton button : buttonList) {
            Rectangle rect = button.rect;
            if (rect.contains(point) && buttonMap.containsKey(rect)) {
                return buttonMap.get(rect);
            }
        }
        return null;
    }

    private void setCurrentTab(String tab) {
        currentTab = tab;
        currentSections = new ArrayList<>();
        if (hasTabs) {
            if (currentTab != null) currentSections = tabSectionMap.get(currentTab);
        } else {
            currentSections = fullSectionList;
        }
    }

    protected void addTab(NinjaTab tab) {
        NinjaVirtualTabButton button = tab.button;
        tabSectionMap.put(tab.name, new ArrayList<>());
        tabNames.add(tab.name);
        if (button != null) {
            buttonList.add(button);
            buttonMap.put(button.rect, tab.name);
        }
    }

    protected void addSection(String tab, NinjaGridSection section) {
        ArrayList<NinjaGridSection> tabSectionList = tabSectionMap.get(tab);
        if (tabSectionList != null) tabSectionList.add(section);
        fullSectionList.add(section);
    }

    private void updateSize() {
        Rectangle rect = SaveManager.stashSaveFile.data.gridRect;
        setMinimumSize(new Dimension(rect.width, rect.height));
        setPreferredSize(new Dimension(rect.width, rect.height));
        setSize(new Dimension(rect.width, rect.height));
    }

    private void drawTabButtons(Graphics g) {
        for (NinjaVirtualTabButton button : buttonList) {
            Rectangle rect = button.rect;
            if (hoverYValue != -1 && rect.y + HOVER_Y_BUFFER < hoverYValue) return;
//            g.setColor(ThemeManager.TRANSPARENT_CLICKABLE);
//            g.fillRect(rect.x, rect.y, rect.width, rect.height);
            g.setColor(Color.WHITE);
            int arc = 10;
            boolean drawButton = false;
            if (currentTab.equals(button.name)) {
                g.setColor(TEXT_COLOR);
                drawButton = true;
            }
            if (drawButton || DRAW_CELL_BORDERS) g.drawRoundRect(rect.x, rect.y, rect.width, rect.height, arc, arc);
        }
    }

    private void drawGridSection(Graphics g, NinjaGridSection section) {
        for (int y = 0; y < section.data.length; y++) {
            for (int x = 0; x < section.data[y].length; x++) {
                String value = section.data[y][x];
                if (value == null || value.equals("NULL")) continue;
                INinjaEntry entry = NinjaInterface.getEntry(value);
                if (entry == null) continue;
                String text = entry.getChaosText();
                drawCell(g, section, x, y, text);
            }
        }
    }

    private void drawCell(Graphics g, NinjaGridSection section, int x, int y, String text) {
        if (text.isEmpty()) return;
        int cellSize = section.cellSize;
        int cellX = section.x + (x * cellSize + x * section.spacingX);
        int cellY = section.y + (y * cellSize + y * section.spacingY);
        // Return early if a cell below this is being hovered to avoid obscuring tooltip
        if (hoverYValue != -1 && cellY + HOVER_Y_BUFFER < hoverYValue) return;
        g.setFont(g.getFont().deriveFont(Font.PLAIN, 12));
        Rectangle2D textBounds = g.getFontMetrics().getStringBounds(text, g);
        int topOfText = cellY + cellSize - fontMetricAscent;
        g.setColor(BACKGROUND_COLOR);
        int arc = 4;
        int buffer = 1;
        int bufferDouble = buffer * 2;
        g.fillRoundRect(cellX - buffer, topOfText - buffer,
                (int) textBounds.getWidth() + chaosImage.getWidth(null) + bufferDouble,
                (int) textBounds.getHeight() + bufferDouble,
                arc, arc);
        g.setColor(TEXT_COLOR);
        g.drawString(text, cellX, cellY + cellSize);
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawImage(chaosImage, cellX + textWidth, topOfText, null);
        if (DRAW_CELL_BORDERS) {
            g.setColor(TEXT_COLOR);
            g.drawRect(cellX, cellY, cellSize, cellSize);
        }
    }

    private void setHoverYValue(int value) {
        if (hoverYValue == value) return;
        hoverYValue = value;
        revalidate();
        repaint();
    }

    private void printLoadingMessage(Graphics g) {
        int textHeight = g.getFontMetrics().getHeight();
        g.setColor(TEXT_COLOR);
        g.drawString("Loading...", 5, textHeight);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ZUtil.clearTransparentComponent(g, this);
        if (!hasBeenSynced) {
            printLoadingMessage(g);
            return;
        }
        int previousTextHeight = textHeight;
        fontMetricAscent = g.getFontMetrics().getAscent();
        textHeight = g.getFontMetrics().getHeight();
        if (textHeight != previousTextHeight) resizeIcons(textHeight);
        drawTabButtons(g);
        if (currentSections == null) return;
        for (NinjaGridSection section : currentSections) {
            drawGridSection(g, section);
            if (DRAW_CELL_BORDERS) {
                Rectangle rect = section.boundingBox;
                g.setColor(Color.BLUE);
                g.drawRect(rect.x, rect.y, rect.width, rect.height);
            }
        }
    }

    @Override
    public void onSave() {
        for (NinjaGridSection section : fullSectionList) section.applyScaling();
        for (NinjaVirtualTabButton button : buttonList) button.applyScaling();
        rebuildButtonMap();
        updateSize();
        revalidate();
        repaint();
    }

    private Point screenPosToWindowPos(Point screenPoint) {
        Point windowPos = getLocationOnScreen();
        screenPoint.x -= windowPos.x;
        screenPoint.y -= windowPos.y;
        return screenPoint;
    }

    @Override
    public void nativeMouseMoved(NativeMouseEvent nativeMouseEvent) {
        if (!isVisible()) return;
        Point pos;
        try {
            pos = screenPosToWindowPos(nativeMouseEvent.getPoint());
        } catch (IllegalComponentStateException ignore) {
            return;
        }
        if (!getBounds().contains(pos)) {
            setHoverYValue(-1);
            return;
        }
//        int cellSize = currentSections.size() > 0 ? currentSections.get(0).cellSize : 50;
//        int value = ZUtil.roundTo(pos.y - cellSize, 10);
//        setHoverYValue(value);
//        if (pos.x < 0 || pos.y < 0) return;
        // FIXME : cache this value?
        Rectangle gridRect = SaveManager.stashSaveFile.data.gridRect;
        if (pos.x > gridRect.width || pos.y > gridRect.height) return;
        for (NinjaGridSection section : currentSections) {
            if (!section.boundingBox.contains(pos)) continue;
            int value = ZUtil.roundTo(pos.y - section.cellSize, 10);
            setHoverYValue(value);
            return;
        }
        setHoverYValue(-1);
    }

    @Override
    public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
        // Do nothing
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent nativeMouseEvent) {
        // Do nothing
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        if (!isVisible()) return;
        try {
            pressedTab = getTabNameAtPoint(screenPosToWindowPos(e.getPoint()));
        } catch (IllegalComponentStateException err) {
            pressedTab = null;
        }
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        if (!isVisible()) return;
        if (pressedTab == null) return;
        String tab = getTabNameAtPoint(screenPosToWindowPos(e.getPoint()));
        if (tab == null) return;
        if (tab.equals(pressedTab) && !tab.equals(currentTab)) {
            setCurrentTab(tab);
            repaint();
        }
    }

    @Override
    public void onSync() {
        hasBeenSynced = true;
        if (isVisible()) repaint();
    }

}
