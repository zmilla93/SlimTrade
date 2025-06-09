package io.github.zmilla93.modules.theme

import javax.swing.JComponent

/**
 * Labda for applying a generic [UIProperty] value of type [T] to a [JComponent].
 */
fun interface UIPropertyApplier<T> {

    fun applyProperty(comp: JComponent, value: T)

}