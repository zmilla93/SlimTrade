package github.zmilla93.core.hotkeys

import org.jnativehook.keyboard.NativeKeyEvent

/**
 * Stores they key code and any modifiers for a hotkey.
 */
class HotkeyData {
    @JvmField
    val keyCode: Int
    val modifiers: Int

    /** GSON Constructor */
    constructor() {
        keyCode = 0
        modifiers = 0
    }

    constructor(keyCode: Int, modifiers: Int) {
        this.keyCode = keyCode
        this.modifiers = modifiers
    }

    val isAltPressed: Boolean
        get() = (modifiers and NativeKeyEvent.ALT_MASK) > 0

    val isCtrlPressed: Boolean
        get() = (modifiers and NativeKeyEvent.CTRL_MASK) > 0

    val isShiftPressed: Boolean
        get() = (modifiers and NativeKeyEvent.SHIFT_MASK) > 0

    override fun toString(): String {
        if (modifiers > 0) {
            return NativeKeyEvent.getModifiersText(modifiers) + "+" + NativeKeyEvent.getKeyText(keyCode)
        }
        return NativeKeyEvent.getKeyText(keyCode)
    }

    override fun equals(other: Any?): Boolean {
        if (other is HotkeyData) {
            val otherData = other
            if (keyCode != otherData.keyCode) return false
            if (modifiers != otherData.modifiers) return false
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        var result = 17
        result = 31 * result + keyCode
        result = 31 * result + modifiers
        return result
    }
}
