package github.zmilla93.gui.setup

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.poe.Game
import github.zmilla93.gui.managers.FrameManager
import github.zmilla93.modules.zswing.extensions.ActionExtensions.onToggle
import github.zmilla93.modules.zswing.theme.ThemeColorBlind
import io.github.zmilla93.modules.theme.UIProperty.FontColorExtensions.textColor
import javax.swing.JCheckBox
import javax.swing.JLabel

class StashFolderSetupPanel : AbstractSetupPanel() {

    private val poe1UsingFoldersCheckbox = JCheckBox("Using stash subfolders")
    private val poe1tradeTabsInsideFolder = JCheckBox("Trade tabs are in subfolders")

    private val poe2UsingFoldersCheckbox = JCheckBox("Using any subfolders")
    private val poe2tradeTabsInsideFolder = JCheckBox("Trade tabs are in subfolders")

    init {
        poe1tradeTabsInsideFolder.isEnabled = false
        poe2tradeTabsInsideFolder.isEnabled = false

        addHeader("Stash Tab Folders")
        addComponent(JLabel("Do you use stash subfolders? Doing so affects UI alignment."))
        addComponent(JLabel("This settings needs to be updated manually if changed.").textColor(ThemeColorBlind.YELLOW))
        addVerticalStrut()
        addHeader(Game.PATH_OF_EXILE_1.explicitName)
        addComponent(poe1UsingFoldersCheckbox)
        addComponent(poe1tradeTabsInsideFolder)
        addVerticalStrut()
        addHeader(Game.PATH_OF_EXILE_2.toString())
        addComponent(poe2UsingFoldersCheckbox)
        addComponent(poe2tradeTabsInsideFolder)
    }

    protected override fun addComponentListeners() {
        poe1UsingFoldersCheckbox.onToggle {
            poe1tradeTabsInsideFolder.isEnabled = it
            runSetupValidation()
        }
        poe2UsingFoldersCheckbox.onToggle {
            poe2tradeTabsInsideFolder.isEnabled = it
            runSetupValidation()
        }
    }

    override fun initializeComponents() {
        poe1UsingFoldersCheckbox.isSelected = SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder
        poe1tradeTabsInsideFolder.isEnabled = SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder
        poe1tradeTabsInsideFolder.isSelected = SaveManager.settingsSaveFile.data.settingsPoe1.tradesAreInsideFolders
        poe2UsingFoldersCheckbox.isSelected = SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder
        poe2tradeTabsInsideFolder.isEnabled = SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder
        poe2tradeTabsInsideFolder.isSelected = SaveManager.settingsSaveFile.data.settingsPoe2.tradesAreInsideFolders
    }

    override fun isSetupValid(): Boolean {
        return true
    }

    override fun applyCompletedSetup() {
        SaveManager.settingsSaveFile.data.settingsPoe1.usingStashFolder = poe1UsingFoldersCheckbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe1.tradesAreInsideFolders = poe1tradeTabsInsideFolder.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe2.usingStashFolder = poe2UsingFoldersCheckbox.isSelected
        SaveManager.settingsSaveFile.data.settingsPoe2.tradesAreInsideFolders = poe2tradeTabsInsideFolder.isSelected
        SaveManager.settingsSaveFile.data.hasInitUsingStashFolders = true
        FrameManager.stashHelperContainerPoe1.updateBounds()
        FrameManager.stashHelperContainerPoe2.updateBounds()
    }

}
