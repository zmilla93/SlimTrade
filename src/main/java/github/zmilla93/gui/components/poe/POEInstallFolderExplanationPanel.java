package github.zmilla93.gui.components.poe;

import github.zmilla93.gui.components.StyledLabelBuilder;
import github.zmilla93.gui.options.AbstractOptionPanel;

/**
 * A panel with info about finding the Path of Exile install directory(s).
 * Hidden by default.
 */
public class POEInstallFolderExplanationPanel extends AbstractOptionPanel {

    public POEInstallFolderExplanationPanel(boolean addStartingStrut, boolean addEndingStrut) {
        super(false, false);
        if (addStartingStrut) addVerticalStrut();
        addHeader("Finding the Install Folder");
        addComponent(StyledLabelBuilder.builder("Steam: ").bold().label("Library > Right Click POE 1 or 2 > Properties > Installed Files > Browse").build());
        addComponent(StyledLabelBuilder.builder("Standalone: ").bold().label("Chosen when you installed POE, should be similar to the example below").build());
        addVerticalStrut();
        addHeader("Example Install Folders");
        // FIXME : Cross platform examples
        addComponent(StyledLabelBuilder.builder("Steam: ").bold().label("C:/Program Files (x86)/Steam/steamapps/common/Path of Exile").build());
        addComponent(StyledLabelBuilder.builder("Standalone: ").bold().label("C:/Program Files (x86)/Grinding Gear Games/Path of Exile").build());
        if (addEndingStrut) addVerticalStrut();
        setVisible(false);
    }

}
