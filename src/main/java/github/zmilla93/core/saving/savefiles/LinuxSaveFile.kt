package github.zmilla93.core.saving.savefiles

import github.zmilla93.modules.saving.AbstractSaveFile

class LinuxSaveFile : AbstractSaveFile() {

    var focusUsingXDoTool = false

    override fun getCurrentTargetVersion(): Int {
        return 1
    }

}