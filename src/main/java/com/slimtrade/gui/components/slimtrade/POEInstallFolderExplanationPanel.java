package com.slimtrade.gui.components.slimtrade;

import com.slimtrade.gui.components.StyledLabelBuilder;
import com.slimtrade.gui.options.AbstractOptionPanel;

/**
 * A panel with info about finding the Path of Exile install directory.
 * Hidden by default.
 */
public class POEInstallFolderExplanationPanel extends AbstractOptionPanel {

    public POEInstallFolderExplanationPanel(boolean addStartingStrut, boolean addEndingStrut) {
        super(false, false);
        if (addStartingStrut) addVerticalStrut();
        addHeader("Finding the Install Folder");
        addComponent(StyledLabelBuilder.builder("Steam").bold().label(" - Select Library > Right Click Game > Properties > Installed Files > Browse").build());
        addComponent(StyledLabelBuilder.builder("Standalone").bold().label(" - Selected when you installed POE, should be similar to the example above").build());
        addHeader("Install Folder Examples");
        addComponent(StyledLabelBuilder.builder("Steam").bold().label(" - C:/Program Files (x86)/Steam/steamapps/common/Path of Exile").build());
        addComponent(StyledLabelBuilder.builder("Standalone").bold().label(" - C:/Program Files (x86)/Grinding Gear Games/Path of Exile").build());
        addVerticalStrut();
        if (addEndingStrut) addVerticalStrut();
        setVisible(false);
    }

}
