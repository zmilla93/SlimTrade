package github.zmilla93.core.event

import javax.swing.SwingUtilities

class EventBus {

    private val listeners = mutableMapOf<Class<*>, MutableList<(Any) -> Unit>>()

    @Synchronized
    fun <T : Any> subscribe(eventType: Class<T>, listener: (T) -> Unit) {
        val list = listeners.getOrPut(eventType) { mutableListOf() }
        list.add { event -> listener(eventType.cast(event)) }
    }

    @Synchronized
    fun <T : Any> unsubscribe(eventType: Class<T>, listener: (T) -> Unit) {
        listeners[eventType]?.removeIf { it == listener }
    }

    @Synchronized
    fun post(event: Any) {
        SwingUtilities.invokeLater {
            listeners[event::class.java]?.forEach { it(event) }
        }
    }

}