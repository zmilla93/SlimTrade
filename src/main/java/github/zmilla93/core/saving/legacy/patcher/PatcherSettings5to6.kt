package github.zmilla93.core.saving.legacy.patcher

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.core.saving.legacy.ISavePatcher

class PatcherSettings5to6 : ISavePatcher {

    override fun getNewVersion(): Int {
        return 6
    }

    override fun requiresPatch(): Boolean {
        return SaveManager.settingsSaveFile.fileExists() && SaveManager.settingsSaveFile.data.saveFileVersion < newVersion
    }

    override fun patch(): Boolean {
        SaveManager.settingsSaveFile.data.hasInitUsingStashFolders = false
        return true
    }

    override fun applyNewVersion() {
        SaveManager.settingsSaveFile.data.saveFileVersion = newVersion
        SaveManager.settingsSaveFile.saveToDisk(false)
    }

    override fun handleCorruptedFile() {
        SaveManager.settingsSaveFile.initData()
    }

    override fun getErrorMessage(): String? {
        return ""
    }
    
}