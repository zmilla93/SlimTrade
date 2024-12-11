package github.zmilla93.gui.components.slimtrade;

import github.zmilla93.core.poe.Game;

import javax.swing.*;

/**
 * This is the JFileChooser that appears when the Browse buttons is clicked on a  {@link POEFolderPicker}.
 */

/// FIXME : Make a ThemeFileChooser and inherit from that
public class POEFileChooser extends JFileChooser {

    public static String TITLE_PREFIX = "SlimTrade - Select ";
    public static String TITLE_SUFFIX = " Folder";

    public POEFileChooser(Game game) {
        setDialogTitle(getWindowTitle(game));
        setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    }

    public static String getWindowTitle(Game game) {
        return TITLE_PREFIX + game + TITLE_SUFFIX;
    }

}
