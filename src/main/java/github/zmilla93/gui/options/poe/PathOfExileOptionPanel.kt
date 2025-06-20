package github.zmilla93.gui.options.poe

import com.sun.jna.platform.WindowUtils
import github.zmilla93.App
import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.core.poe.GameWindowMode
import github.zmilla93.core.poe.POEWindow
import github.zmilla93.gui.components.ComponentPanel
import github.zmilla93.gui.components.HotkeyButton
import github.zmilla93.gui.components.MonitorPicker
import github.zmilla93.gui.components.PoeButton
import github.zmilla93.gui.components.poe.POEFolderPicker
import github.zmilla93.gui.components.poe.POEInstallFolderExplanationPanel
import github.zmilla93.gui.components.poe.detection.GameDetectionButton
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.gui.options.AbstractOptionPanel
import github.zmilla93.modules.saving.ISavable
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onClick
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onToggle
import github.zmilla93.modules.zswing.extensions.StyleExtensions.bold
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import javax.swing.*

class PathOfExileOptionPanel : AbstractOptionPanel(), ISavable {

    // Shared
    private val windowModeCombo = JComboBox<GameWindowMode>()
    private val detectionButton = GameDetectionButton()
    private val monitorPicker = MonitorPicker()
    private val chatHotkeyButton = HotkeyButton()
    private val explanationButton = JButton("Install Folder Info")
    private val explanationPanel: JPanel = POEInstallFolderExplanationPanel(true, false)
    private val previewStashButton = JButton("Test Stash Alignment").onClick {
        FrameManager.stashAlignmentPreviewWindow.showPreview(FrameManager.optionsWindow)
    }
    private var curPreviewGame = Game.PATH_OF_EXILE_1
    private val curGamePreviewButton =
        PoeButton().onGameChange {
            curPreviewGame = it.currentGame
            FrameManager.stashAlignmentPreviewWindow.updateBounds(it.currentGame)
        }


    // Game Specific
    private val usingStashFoldersPoe1Checkbox = JCheckBox("Using any stash folders")
    private val usingStashFoldersPoe2Checkbox = JCheckBox("Using any stash folders")
    private val tradesInsideFoldersPoe1CheckBox = JCheckBox("Trade tabs are inside folders", true)
    private val tradesInsideFoldersPoe2CheckBox = JCheckBox("Trade tabs are inside folders", true)
    private val poe1FolderPicker = POEFolderPicker(Game.PATH_OF_EXILE_1)
    private val poe2FolderPicker = POEFolderPicker(Game.PATH_OF_EXILE_2)
    private val poe1OptionPanel = AbstractOptionPanel(false, false)
    private val poe2OptionPanel = AbstractOptionPanel(false, false)

    init {
        windowModeCombo.addItem(GameWindowMode.DETECT)
        windowModeCombo.addItem(GameWindowMode.MONITOR)

        addHeader("Game Alignment")
        addComponent(
            JLabel("The settings below determine game UI alignment. Save before testing.")
                .textColor(ThemeColorBlind.RED).bold()
        )
        addComponent(ComponentPanel(previewStashButton, curGamePreviewButton))
        addVerticalStrut()

        /** Shared */
        addHeader("Path of Exile 1 & 2")
        addComponent(ComponentPanel(JLabel("Game Window Mode"), windowModeCombo))
        addComponent(detectionButton)
        addComponent(monitorPicker)
        addComponent(ComponentPanel(JLabel("Open Chat Hotkey"), chatHotkeyButton))
        addVerticalStrut()

        /** Path of Exile 1 */
        poe1OptionPanel.addComponent(usingStashFoldersPoe1Checkbox)
        poe1OptionPanel.addComponent(tradesInsideFoldersPoe1CheckBox)
        addHeader(Game.PATH_OF_EXILE_1.explicitName)
        addComponent(poe1FolderPicker)
        addFullWidthComponent(poe1OptionPanel)
        addVerticalStrut()

        /** Path of Exile 2 */
        poe2OptionPanel.addComponent(usingStashFoldersPoe2Checkbox)
        poe2OptionPanel.addComponent(tradesInsideFoldersPoe2CheckBox)
        addHeader(Game.PATH_OF_EXILE_2.toString())
        addComponent(poe2FolderPicker)
        addFullWidthComponent(poe2OptionPanel)
        addVerticalStrut()

        /** Explain Install Files */
        addHeader("More Info")

        addComponent(explanationButton)
        addComponent(explanationPanel)
        addListeners()

        tradesInsideFoldersPoe1CheckBox.isVisible = false
        tradesInsideFoldersPoe2CheckBox.isVisible = false
    }

    // FIXME: This feels a little hacky?
    private fun refreshPanelVisibility() {
        poe1OptionPanel.isVisible = !poe1FolderPicker.notInstalledCheckbox.isSelected
        poe2OptionPanel.isVisible = !poe2FolderPicker.notInstalledCheckbox.isSelected
    }

    private fun addListeners() {
        explanationButton.addActionListener { explanationPanel.isVisible = !explanationPanel.isVisible }
        poe1FolderPicker.notInstalledCheckbox.addActionListener { refreshPanelVisibility() }
        poe2FolderPicker.notInstalledCheckbox.addActionListener { refreshPanelVisibility() }
        windowModeCombo.addActionListener {
            val mode = windowModeCombo.selectedItem as GameWindowMode
            detectionButton.isVisible = mode == GameWindowMode.DETECT
            monitorPicker.isVisible = mode == GameWindowMode.MONITOR
        }
        usingStashFoldersPoe1Checkbox.onToggle {
            tradesInsideFoldersPoe1CheckBox.isVisible = usingStashFoldersPoe1Checkbox.isSelected
        }
        usingStashFoldersPoe2Checkbox.onToggle {
            tradesInsideFoldersPoe2CheckBox.isVisible = usingStashFoldersPoe2Checkbox.isSelected
        }
    }

    override fun save() {
        /** Shared */
        val windowMode = windowModeCombo.selectedItem as GameWindowMode
        SaveManager.settingsSaveFile.data.gameWindowMode = windowMode
        if (windowMode == GameWindowMode.DETECT) if (detectionButton.latestResultWasSuccess) {
//                SaveManager.settingsSaveFile.data.detectedGameBounds = detectionButton.getLatestResultWindow().clientBounds;
            val handle = detectionButton.latestResultWindow.handle
            POEWindow.setBoundsByWindowHandle(handle, true)
            SaveManager.settingsSaveFile.data.detectedGameBounds =
                WindowUtils.getWindowLocationAndSize(handle).bounds
        }
        if (windowMode == GameWindowMode.MONITOR) {
            val monitor = monitorPicker.selectedMonitor
            SaveManager.settingsSaveFile.data.selectedMonitor = monitorPicker.selectedMonitor
            POEWindow.setBoundsByMonitor(monitor)
        }
        SaveManager.settingsSaveFile.data.poeChatHotkey = chatHotkeyButton.data
        /** Game Specific */
        SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled = poe1FolderPicker.notInstalledCheckbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled = poe2FolderPicker.notInstalledCheckbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe1.installFolder = poe1FolderPicker.getPathString()
        SaveManager.settingsSaveFile.data.settingsPoe2.installFolder = poe2FolderPicker.getPathString()
        SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder = usingStashFoldersPoe1Checkbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder = usingStashFoldersPoe2Checkbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe1.tradesAreInsideFolders =
            tradesInsideFoldersPoe1CheckBox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe2.tradesAreInsideFolders =
            tradesInsideFoldersPoe2CheckBox.isSelected
        FrameManager.stashHelperContainerPoe1.updateBounds()
        FrameManager.stashHelperContainerPoe2.updateBounds()
        FrameManager.stashAlignmentPreviewWindow.updateBounds(curPreviewGame)
        App.chatParser.restartChatParsers(false)
        detectionButton.reset()
    }

    override fun load() {
        /** Shared */
        val windowMode = SaveManager.settingsSaveFile.data.gameWindowMode
        windowModeCombo.setSelectedItem(windowMode)
        if (windowMode == GameWindowMode.MONITOR) monitorPicker.setMonitor(SaveManager.settingsSaveFile.data.selectedMonitor)
        /** Game Specific */
        chatHotkeyButton.setData(SaveManager.settingsSaveFile.data.poeChatHotkey)
        poe1FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe1.notInstalled)
        poe2FolderPicker.notInstalledCheckbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe2.notInstalled)
        poe1FolderPicker.setSelectedPath(SaveManager.settingsSaveFile.data.settingsPoe1.installFolder)
        poe2FolderPicker.setSelectedPath(SaveManager.settingsSaveFile.data.settingsPoe2.installFolder)
        usingStashFoldersPoe1Checkbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder)
        usingStashFoldersPoe2Checkbox.setSelected(SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder)
        tradesInsideFoldersPoe1CheckBox.isSelected =
            SaveManager.settingsSaveFile.data.settingsPoe1.tradesAreInsideFolders
        tradesInsideFoldersPoe2CheckBox.isSelected =
            SaveManager.settingsSaveFile.data.settingsPoe2.tradesAreInsideFolders
        refreshPanelVisibility()
        detectionButton.reset()
    }
}
