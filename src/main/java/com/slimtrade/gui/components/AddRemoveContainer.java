package com.slimtrade.gui.components;

import com.slimtrade.App;
import com.slimtrade.core.jna.NativeMouseAdapter;
import com.slimtrade.core.utility.ZUtil;
import com.slimtrade.modules.updater.ZLogger;
import org.jetbrains.annotations.NotNull;
import org.jnativehook.mouse.NativeMouseEvent;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.ArrayList;

/**
 * A panel that allows {@link AddRemovePanel}s to be added, removed, and easily reordered.
 *
 * @see AddRemovePanel
 */
public class AddRemoveContainer<T extends AddRemovePanel> extends JPanel {

    private int spacing = 0;
    private final GridBagConstraints gc = ZUtil.getGC();

    private final ArrayList<Component> components = new ArrayList<>();
    private final ArrayList<Rectangle> componentBounds = new ArrayList<>();
    private final ArrayList<Component> nonDraggedComponents = new ArrayList<>();
    private AddRemovePanel componentBeingDragged = null;

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

    public void setUseDragBorder(boolean state) {
        useDragBorder = state;
    }

    public void setDragBorder(Border border) {
        dragBorder = border;
    }

    /**
     * This should be called in the mouse down event of whatever controls the component being dragged.
     *
     * @param component Component being dragged
     */
    public void setComponentBeingDragged(AddRemovePanel component) {
        componentBeingDragged = component;
        if (useDragBorder) {
            previousBorder = component.getBorder();
            component.setBorder(dragBorder);
        }
        nonDraggedComponents.clear();
        for (Component comp : getComponents()) {
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
        components.clear();
        componentBounds.clear();
        // Component list is built here as an easy way to get the index of the component being dragged.
        for (Component child : getComponents()) {
            Rectangle rect = child.getBounds();
            rect.x += screenPos.x;
            rect.y += screenPos.y;
            components.add(child);
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
        // Return early in the panel being dragged is already in the correct position.
        if (currentPanelIndex == targetIndex) return;
        // Rebuild the component list based on the target index for the component being dragged
        int nonDragIndex = 0;
        components.clear();
        for (int i = 0; i < componentCount; i++) {
            if (i == targetIndex) {
                components.add(componentBeingDragged);
            } else {
                components.add(nonDraggedComponents.get(nonDragIndex));
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
        Component panelA = components.get(indexA);
        Component panelB = components.get(indexB);
        components.set(indexA, panelB);
        components.set(indexB, panelA);
        rebuildComponentList();
    }

    /**
     * Array bounds checking
     */
    private boolean isInvalidIndex(int index) {
        return index < 0 || index >= components.size();
    }

    /**
     * Removes then adds all child components based on the order the of components array.
     */
    private void rebuildComponentList() {
        super.removeAll();
        gc.insets.top = 0;
        gc.gridy = 0;
        for (Component comp : components) {
            super.add(comp, gc);
            gc.insets.top = spacing;
            gc.gridy++;
        }
        revalidate();
        repaint();
    }

    @SuppressWarnings("unchecked")
    public ArrayList<T> getComponentsTyped() {
        // Warnings are suppressed here because there is no clean way to safely cast to a generic type.
        // Component types are checked when being added, so errors should be caught before ever getting to here.
        ArrayList<T> components = new ArrayList<>();
        for (Component c : getComponents()) {
            components.add((T) c);
        }
        return components;
    }

    @Override
    public Component add(Component comp) {
        gc.gridy = getComponentCount();
        super.add(comp, gc);
        gc.insets.top = spacing;
        revalidate();
        repaint();
        return comp;
    }

    @Override
    public void remove(Component comp) {
        super.remove(comp);
        components.remove(comp);
        rebuildComponentList();
    }

    //
    // Add functions that should not be used - Prints error if they are.
    //

    private void incorrectAddMethod() {
        ZLogger.err("AddRemoveContainer should only have elements added using the add(Component) method!");
        ZUtil.printCallingFunction(AddRemoveContainer.class);
    }

    @Override
    public Component add(String name, Component comp) {
        incorrectAddMethod();
        return super.add(name, comp);
    }

    @Override
    public Component add(Component comp, int index) {
        incorrectAddMethod();
        return super.add(comp, index);
    }

    @Override
    public void add(@NotNull Component comp, Object constraints) {
        incorrectAddMethod();
        super.add(comp, constraints);
    }

    @Override
    public void add(Component comp, Object constraints, int index) {
        incorrectAddMethod();
        super.add(comp, constraints, index);
    }

}
