package github.zmilla93.gui.components.poe;

import github.zmilla93.core.enums.ResultStatus;
import github.zmilla93.core.managers.SaveManager;
import github.zmilla93.core.poe.Game;
import github.zmilla93.core.poe.PoeClientPath;
import github.zmilla93.core.utility.ZUtil;

import javax.swing.*;
import java.awt.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class POEFolderPicker extends FilePicker implements PathChangeListener {

    public final Game game;
    public final JCheckBox notInstalledCheckbox;
    private final JLabel notInstalledLabel;
    private final JPanel pathPanel = new JPanel(new GridBagLayout());
    public boolean packParentWindow;

    public POEFolderPicker(Game game) {
        this(game, false);
    }

    public POEFolderPicker(Game game, boolean packParentWindow) {
        super("Select the '" + game + "' install folder.");
        this.game = game;
        this.packParentWindow = packParentWindow;
        notInstalledCheckbox = new JCheckBox("Not Installed") {
            @Override
            public void setSelected(boolean b) {
                super.setSelected(b);
                refreshComponentVisibility();
            }
        };
        notInstalledLabel = new ResultLabel(ResultStatus.INDETERMINATE, "If you ever install " + game + ", update this setting.");
        notInstalledLabel.setVisible(false);
        fileChooser = new POEFileChooser(game);
        JPanel pathWrapperPanel = new JPanel(new BorderLayout());
        pathWrapperPanel.add(notInstalledLabel, BorderLayout.NORTH);
        pathWrapperPanel.add(pathPanel, BorderLayout.SOUTH);
        add(notInstalledCheckbox, BorderLayout.NORTH);
        add(pathWrapperPanel, BorderLayout.SOUTH);
        addPathChangeListener(this);
        notInstalledCheckbox.addActionListener(e -> {
            refreshComponentVisibility();
        });
    }

    public void refreshComponentVisibility() {
        boolean showMainComponents = !notInstalledCheckbox.isSelected();
        chooserPanel.setVisible(showMainComponents);
        pathPanel.setVisible(showMainComponents);
        notInstalledLabel.setVisible(!showMainComponents);
        if (packParentWindow) ZUtil.packComponentWindow(this);
    }

    public Path[] createDuplicatePathPanels(boolean showIfOnlyOneResult) {
        Path[] paths = SaveManager.getValidGameDirectories(game);
        if (paths.length < 2 && !showIfOnlyOneResult) return paths;
        GridBagConstraints gc = ZUtil.getGC();
        gc.weightx = 1;
        gc.fill = GridBagConstraints.HORIZONTAL;
        for (Path path : paths) {
            JButton pathButton = new JButton("Select");
            JLabel pathLabel = new JLabel(path.toString());
            JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
            panel.add(pathButton);
            panel.add(pathLabel);
            pathPanel.add(panel, gc);
            gc.gridy++;
            pathButton.addActionListener(e -> setSelectedPath(path));
        }
        return paths;
    }

    public boolean notInstalledCheckboxValue() {
        return notInstalledCheckbox.isSelected();
    }

    /**
     * Allows setting the path via a string with an added null check.
     */
    // FIXME: Should either add a isValidPOEPath check, or move this to parent
    public void setSelectedPath(String pathString) {
        if (pathString == null) return;
        setSelectedPath(Paths.get(pathString));
    }

    @Override
    public void onPathChanged(Path path) {
        boolean validFolderName = path.endsWith(game.toString());
        PoeClientPath validator = PoeClientPath.validateInstallFolder(game, getSelectedPath());
        setErrorText(validator.message, validator.status);
//        if (validFolderName) {
//            Path logsFolder = path.resolve(SaveManager.POE_LOG_FOLDER_NAME);
//            if (logsFolder.toFile().exists()) {
//                setErrorText("Folder set correctly.", ResultStatus.APPROVE);
//            } else {
//                setErrorText("Correct folder name, wrong folder! The install folder must contain a '" + SaveManager.POE_LOG_FOLDER_NAME + "' folder.", ResultStatus.INDETERMINATE);
//            }
//        } else {
//            setErrorText("Folder should be named '" + game + "'.", ResultStatus.DENY);
//        }
        if (packParentWindow) ZUtil.packComponentWindow(this);
    }

}
