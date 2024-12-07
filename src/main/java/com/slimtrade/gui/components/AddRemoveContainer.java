package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.jna.NativeMouseAdapter;
import com.slimtrade.core.utility.ZUtil;
import org.jetbrains.annotations.NotNull;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * A panel that allows {@link AddRemovePanel}s to be dynamically added, removed, and easily reordered.
 */
public class AddRemoveContainer<T extends AddRemovePanel<?>> extends JPanel {

    // Layout
    private final GridBagConstraints gc = ZUtil.getGC();
    private int spacing = 0;

    // Component dragging
    private final ArrayList<T> components = new ArrayList<>();
    private final ArrayList<T> orderedComponents = new ArrayList<>();
    private final ArrayList<T> nonDraggedComponents = new ArrayList<>();
    private final ArrayList<Rectangle> componentBounds = new ArrayList<>();
    private T componentBeingDragged = null;

    // Drag Border
    private boolean useDragBorder = true;
    private static final Border DEFAULT_DRAG_BORDER = new InsetBorder();
    private Border dragBorder = DEFAULT_DRAG_BORDER;
    private Border previousBorder;

    public AddRemoveContainer() {
        setLayout(new GridBagLayout());
        gc.weightx = 1;
        gc.anchor = GridBagConstraints.WEST;
        App.globalMouseListener.addMouseAdapter(new NativeMouseAdapter() {
            @Override
            public void nativeMouseReleased(NativeMouseEvent nativeMouseEvent) {
                if (componentBeingDragged != null) {
                    if (useDragBorder) componentBeingDragged.setBorder(previousBorder);
                    componentBeingDragged = null;
                }
            }

            @Override
            public void nativeMouseDragged(NativeMouseEvent nativeMouseEvent) {
                if (componentBeingDragged == null) return;
                handleComponentDrag(nativeMouseEvent);
            }
        });
    }

    /**
     * The same as getComponents(), but returns an ordered list of typed components.
     *
     * @return Typed components
     */
    public ArrayList<T> getComponentsTyped() {
        return components;
    }

    public void setUseDragBorder(boolean state) {
        useDragBorder = state;
    }

    public void setDragBorder(Border border) {
        dragBorder = border;
    }

    /**
     * See {@link AddRemoveContainer#setComponentBeingDragged(AddRemovePanel)}.
     */
    public void setComponentBeingDragged(Object obj) {
        // This unchecked cast is required since T and AddRemovePanel<T>
        // aren't type safe without combining data and gui classes.
        //noinspection unchecked
        setComponentBeingDragged((T) obj);
    }

    /**
     * This should be called in the mouse down event of whatever component controls the AddRemovePanel being dragged.
     *
     * @param component Component being dragged
     */
    public void setComponentBeingDragged(T component) {
        componentBeingDragged = component;
        if (useDragBorder) {
            previousBorder = component.getBorder();
            component.setBorder(dragBorder);
        }
        nonDraggedComponents.clear();
        for (T comp : getComponentsTyped()) {
            if (comp == componentBeingDragged) continue;
            nonDraggedComponents.add(comp);
        }
        calculateBoundsOfChildren();
    }

    /**
     * Create a list of Rectangles that represent the bounds of all child components.
     */
    private void calculateBoundsOfChildren() {
        Point screenPos = getLocationOnScreen();
        componentBounds.clear();
        orderedComponents.clear();
        // Ordered component list is built here as an easy way to get the index of the component being dragged.
        for (T child : getComponentsTyped()) {
            Rectangle rect = child.getBounds();
            rect.x += screenPos.x;
            rect.y += screenPos.y;
            orderedComponents.add(child);
            componentBounds.add(rect);
        }
    }

    /**
     * Calculate the desired order of components based on the mouse screen position and the component being dragged.
     */
    private void handleComponentDrag(NativeMouseEvent nativeMouseEvent) {
        int mouseY = nativeMouseEvent.getY();
        int targetIndex = 0;
        int componentCount = getComponentCount();
        int currentPanelIndex = components.indexOf(componentBeingDragged);
        // Calculate the desired index of the dragged panel.
        for (Rectangle rect : componentBounds) {
            if (mouseY < rect.y + rect.height) break;
            targetIndex++;
        }
        if (targetIndex > componentCount - 1) targetIndex = componentCount - 1;
        // Return early if the panel being dragged is already in the correct position.
        if (currentPanelIndex == targetIndex) return;
        // Rebuild the component list based on the target index for the component being dragged
        int nonDragIndex = 0;
        orderedComponents.clear();
        for (int i = 0; i < componentCount; i++) {
            if (i == targetIndex) {
                orderedComponents.add(componentBeingDragged);
            } else {
                orderedComponents.add(nonDraggedComponents.get(nonDragIndex));
                nonDragIndex++;
            }
        }
        rebuildComponentList();
    }

    /**
     * Adds a gap between components.
     *
     * @param spacing Spacing in pixels
     */
    public void setSpacing(int spacing) {
        this.spacing = spacing;
    }

    @Deprecated // FIXME : Use the new dragButton instead.
    protected void shiftUp(Component panel) {
        int index = components.indexOf(panel);
        int swapIndex = index - 1;
        swapPanels(index, swapIndex);
    }

    @Deprecated // FIXME : Use the new dragButton instead.
    protected void shiftDown(Component panel) {
        int index = components.indexOf(panel);
        int swapIndex = index + 1;
        swapPanels(index, swapIndex);
    }

    @Deprecated // FIXME : Use the new dragButton instead.
    private void swapPanels(int indexA, int indexB) {
        if (isInvalidIndex(indexA) || isInvalidIndex(indexB)) return;
        T panelA = components.get(indexA);
        T panelB = components.get(indexB);
        components.set(indexA, panelB);
        components.set(indexB, panelA);
        rebuildComponentList();
    }

    /**
     * Array bounds checking
     */
    @Deprecated
    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= components.size();
    }

    /**
     * Removes then adds all child components based on the orderedComponents array.
     */
    private void rebuildComponentList() {
        super.removeAll();
        gc.insets.top = 0;
        gc.gridy = 0;
        if (orderedComponents.isEmpty()) orderedComponents.addAll(components);
        components.clear();
        for (T comp : orderedComponents) {
            super.add(comp, gc);
            gc.insets.top = spacing;
            gc.gridy++;
            components.add(comp);
        }
        revalidate();
        repaint();
    }

    /**
     * This should be the only add method used by AddRemoveContainer.
     */
    public Component add(T comp) {
        components.add(comp);
        add((Component) comp);
        gc.gridy = getComponentCount();
        gc.insets.top = spacing;
        super.add(comp, gc);
        revalidate();
        repaint();
        return comp;
    }

    public void remove(T comp) {
        components.remove(comp);
        orderedComponents.remove(comp);
        rebuildComponentList();
        super.remove(comp);
    }

    @Override
    public void removeAll() {
        components.clear();
        orderedComponents.clear();
        super.removeAll();
    }

    // Overrides to prevent incorrect add/remove usage.

    @Override
    public Component add(Component comp) {
        return comp;
    }

    @Override
    public Component add(String name, Component comp) {
        return comp;
    }

    @Override
    public Component add(Component comp, int index) {
        return comp;
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        // Do nothing
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        // Do nothing
    }

    @Override
    public void remove(int index) {
        // Do nothing
    }

    @Override
    public void remove(Component comp) {
        // Do nothing
    }

}
