package com.slimtrade.gui.components;

import javax.swing.*;
import java.awt.*;

/**
 * Groups swing controls for easy use with GridBagLayout.
 *
 * @param <T> Data class that represents the combined data from all controls.
 */
public abstract class GridBagGroup<T> {

    public abstract T getData();

    public abstract void setData(T data);

    public abstract void addToParent(JComponent parent, GridBagConstraints gc);

}
