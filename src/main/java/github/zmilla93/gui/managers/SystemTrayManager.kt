package github.zmilla93.gui.managers

import github.zmilla93.App
import github.zmilla93.core.enums.AppState
import github.zmilla93.core.enums.DefaultIcon
import github.zmilla93.modules.updater.ZLogger
import java.awt.*
import java.util.*
import javax.swing.ImageIcon
import kotlin.system.exitProcess

/**
 * Popup menu for the System Tray.
 */
object SystemTrayManager {

    private val popupMenu = PopupMenu()

    // Buttons
    private val optionsButton = MenuItem("Options")
    private val historyButton = MenuItem("History")
    private val chatScannerButton = MenuItem("Chat Scanner")
    private val resetUIButton = MenuItem("Reset UI Position")
    private val exitButton = MenuItem("Exit SlimTrade")

    @JvmStatic
    fun init() {
        if (!SystemTray.isSupported()) return
        // Tray
        val tray = SystemTray.getSystemTray()
        val img =
            ImageIcon(Objects.requireNonNull(SystemTrayManager::class.java.getResource(DefaultIcon.APP_ICON.path()))).getImage()
                .getScaledInstance(16, 16, Image.SCALE_SMOOTH)
        val trayIcon = TrayIcon(img)
        trayIcon.setImageAutoSize(true)
        trayIcon.setToolTip(App.getAppInfo().fullName)
        trayIcon.setPopupMenu(popupMenu)
        trayIcon.addActionListener { if (App.getState() == AppState.RUNNING) FrameManager.optionsWindow.setVisible(true) }
        try {
            tray.add(trayIcon)
        } catch (e: AWTException) {
            ZLogger.err("Failed to set tray icon.")
            ZLogger.log(e.stackTrace)
        }
        addListeners()
        showSimple()
    }

    private fun addListeners() {
        optionsButton.addActionListener { FrameManager.optionsWindow.setVisible(true) }
        historyButton.addActionListener { FrameManager.historyWindow.setVisible(true) }
        chatScannerButton.addActionListener { FrameManager.chatScannerWindow.setVisible(true) }
        exitButton.addActionListener { exitProcess(0) }
        resetUIButton.addActionListener { FrameManager.requestRestoreUIDefaults() }
    }

    @JvmStatic
    fun showSimple() {
        if (!SystemTray.isSupported()) return
        popupMenu.removeAll()
        popupMenu.add(resetUIButton)
        popupMenu.addSeparator()
        popupMenu.add(exitButton)
    }

    @JvmStatic
    fun showDefault() {
        if (!SystemTray.isSupported()) return
        popupMenu.removeAll()
        popupMenu.add(optionsButton)
        popupMenu.add(historyButton)
        popupMenu.add(chatScannerButton)
        popupMenu.addSeparator()
        popupMenu.add(resetUIButton)
        popupMenu.addSeparator()
        popupMenu.add(exitButton)
    }

}
