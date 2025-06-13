package github.zmilla93.gui.windows

import github.zmilla93.core.utility.AdvancedMouseListener
import github.zmilla93.core.utility.ZUtil.addStrutsToBorderPanel
import github.zmilla93.core.utility.ZUtil.getGC
import github.zmilla93.gui.components.StyledLabel
import github.zmilla93.modules.theme.ThemeManager.addFrame
import github.zmilla93.modules.theme.ThemeManager.addThemeListener
import github.zmilla93.modules.theme.ThemeManager.removeFrame
import github.zmilla93.modules.theme.ThemeManager.removeThemeListener
import github.zmilla93.modules.theme.listeners.IThemeListener
import github.zmilla93.modules.updater.IUpdateProgressListener
import github.zmilla93.modules.updater.data.AppInfo
import github.zmilla93.modules.updater.data.AppVersion
import java.awt.BorderLayout
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.MouseEvent
import javax.swing.*
import kotlin.system.exitProcess

// FIXME : This should probably inherit from something else once custom window inheritance is cleaned up
class UpdateProgressWindow(appInfo: AppInfo, targetVersion: AppVersion) : JFrame(), IThemeListener,
    IUpdateProgressListener {
    private val popupMenu = JPopupMenu()
    private val progressBar = JProgressBar()

    init {
        isUndecorated = true
        setAlwaysOnTop(true)
        setFocusable(false)
        setFocusableWindowState(false)
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)

        val contentPanel = JPanel()
        contentPane = contentPanel

        val innerPanel = JPanel(GridBagLayout())
        val gc = getGC()
        innerPanel.add(StyledLabel("Updating to " + appInfo.appName + " " + targetVersion + "...").bold(), gc)
        gc.gridy++
        gc.fill = GridBagConstraints.HORIZONTAL
        gc.weightx = 1.0
        gc.insets.top = VERTICAL_GAP
        innerPanel.add(progressBar, gc)

        // Popup Menu
        val quitButton = JMenuItem("Abort Update")
        popupMenu.add(quitButton)
        quitButton.addActionListener { exitProcess(0) }
        contentPanel.addMouseListener(object : AdvancedMouseListener() {
            override fun click(e: MouseEvent) {
                if (e.getButton() != MouseEvent.BUTTON3) return
                popupMenu.show(e.component, e.getX(), e.getY())
            }
        })

        contentPanel.setLayout(BorderLayout())
        addStrutsToBorderPanel(contentPanel, BORDER_INSET)
        contentPanel.add(innerPanel, BorderLayout.CENTER)
        contentPanel.setBorder(BorderFactory.createLineBorder(UIManager.getColor("Separator.foreground")))
        pack()
        setLocation(WINDOW_OFFSET, WINDOW_OFFSET)
        addFrame(this)
        addThemeListener(this)
    }

    override fun onThemeChange() {
        popupMenu.updateUI()
    }

    override fun dispose() {
        super.dispose()
        removeFrame(this)
        removeThemeListener(this)
    }

    override fun onDownloadProgress(progressPercent: Int) {
        progressBar.setValue(progressPercent)
    }

    override fun onDownloadComplete() {
    }

    override fun onDownloadFailed() {
        isVisible = false
    }

    companion object {
        private const val BORDER_INSET = 20
        private const val VERTICAL_GAP = 3
        private const val WINDOW_OFFSET = 2

        private var _progressWindow: UpdateProgressWindow? = null

        fun getProgressWindow(appInfo: AppInfo, targetVersion: AppVersion): UpdateProgressWindow {
            if (_progressWindow == null) _progressWindow = UpdateProgressWindow(appInfo, targetVersion)
            return _progressWindow!!
        }

    }
}
