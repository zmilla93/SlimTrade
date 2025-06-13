package github.zmilla93.gui.options

import github.zmilla93.core.managers.SaveManager
import github.zmilla93.modules.saving.ISavable
import javax.swing.JCheckBox

class LinuxOptionsPanel : AbstractOptionPanel(), ISavable {

    val xdotoolCheckbox = JCheckBox("Focus POE using xdotool")

    init {
        addHeader("Linux X11")
        add(xdotoolCheckbox)
    }

    override fun save() {
        SaveManager.linuxSaveFile.data.focusUsingXDoTool = xdotoolCheckbox.isSelected
    }

    override fun load() {
        xdotoolCheckbox.isSelected = SaveManager.linuxSaveFile.data.focusUsingXDoTool
    }

}