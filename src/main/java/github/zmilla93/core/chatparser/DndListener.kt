package github.zmilla93.core.chatparser

interface DndListener {
    fun onDndToggle(state: Boolean, loaded: Boolean)
}
