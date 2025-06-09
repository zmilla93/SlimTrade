package github.zmilla93.modules.theme

import javax.swing.JComponent

/**
 * A labda for running a function on any JComponent without having to extend a class.
 * Typically applied via [ThemeManager.updateAllComponents].
 */
fun interface ComponentUpdater {

    fun update(comp: JComponent)

}