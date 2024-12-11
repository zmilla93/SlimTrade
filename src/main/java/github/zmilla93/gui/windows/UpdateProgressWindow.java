package github.zmilla93.gui.windows;

import github.zmilla93.core.utility.AdvancedMouseListener;
import github.zmilla93.core.utility.ZUtil;
import github.zmilla93.gui.components.StyledLabel;
import github.zmilla93.modules.theme.IThemeListener;
import github.zmilla93.modules.theme.ThemeManager;
import github.zmilla93.modules.updater.IUpdateProgressListener;
import github.zmilla93.modules.updater.data.AppInfo;
import github.zmilla93.modules.updater.data.AppVersion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

// FIXME : This should probably inherit from something else once custom window inheritance is cleaned up
public class UpdateProgressWindow extends JFrame implements IThemeListener, IUpdateProgressListener {

    private static final int BORDER_INSET = 20;
    private static final int VERTICAL_GAP = 3;
    private static final int WINDOW_OFFSET = 2;

    private final JPopupMenu popupMenu = new JPopupMenu();
    private final JProgressBar progressBar = new JProgressBar();

    public UpdateProgressWindow(AppInfo appInfo, AppVersion targetVersion) {
        setUndecorated(true);
        setAlwaysOnTop(true);
        setFocusable(false);
        setFocusableWindowState(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel contentPanel = new JPanel();
        setContentPane(contentPanel);

        JPanel innerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gc = ZUtil.getGC();
        innerPanel.add(new StyledLabel("Updating to " + appInfo.appName + targetVersion + "...").bold(), gc);
        gc.gridy++;
        gc.fill = GridBagConstraints.HORIZONTAL;
        gc.weightx = 1;
        gc.insets.top = VERTICAL_GAP;
        innerPanel.add(progressBar, gc);

        // Popup Menu

        JMenuItem quitButton = new JMenuItem("Abort Update");
        popupMenu.add(quitButton);
        quitButton.addActionListener(e -> System.exit(0));
        contentPanel.addMouseListener(new AdvancedMouseListener() {
            @Override
            public void click(MouseEvent e) {
                if (e.getButton() != MouseEvent.BUTTON3) return;
                popupMenu.show(e.getComponent(), e.getX(), e.getY());
            }
        });

        contentPanel.setLayout(new BorderLayout());
        ZUtil.addStrutsToBorderPanel(contentPanel, BORDER_INSET);
        contentPanel.add(innerPanel, BorderLayout.CENTER);
        contentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground")));
        pack();
        setLocation(WINDOW_OFFSET, WINDOW_OFFSET);
        ThemeManager.addFrame(this);
        ThemeManager.addThemeListener(this);
    }

    @Override
    public void onThemeChange() {
        popupMenu.updateUI();
    }

    @Override
    public void dispose() {
        super.dispose();
        ThemeManager.removeFrame(this);
        ThemeManager.removeThemeListener(this);
    }

    @Override
    public void onDownloadProgress(int progressPercent) {
        progressBar.setValue(progressPercent);
    }

    @Override
    public void onDownloadComplete() {

    }

    @Override
    public void onDownloadFailed() {
        setVisible(false);
    }

}
