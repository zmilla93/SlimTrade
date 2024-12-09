package com.slimtrade.gui.components.slimtrade;

import java.nio.file.Path;

/**
 * Listeners for when a {@link Path} changes.
 */
public interface PathChangeListener {

    void onPathChanged(Path path);

}
