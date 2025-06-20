package github.zmilla93.gui.components

import github.zmilla93.App
import github.zmilla93.core.events.GameChangedEvent
import github.zmilla93.core.poe.Game
import javax.swing.JButton

class PoeButton : JButton(Game.PATH_OF_EXILE_1.toString()) {

    private val listeners = ArrayList<GameChangeListener>()

    var game = Game.PATH_OF_EXILE_1
        set(value) {
            field = value
            text = value.toString()
            listeners.forEach { it.onGameChange(GameChangedEvent(field)) }
        }

    init {
        App.events.subscribe(GameChangedEvent::class.java) { game = it.currentGame }
        addActionListener { if (game.isPoe1) game = Game.PATH_OF_EXILE_2 else game = Game.PATH_OF_EXILE_1 }
    }

    fun onGameChange(listener: GameChangeListener): PoeButton {
        listeners.add(listener)
        return this
    }

    fun interface GameChangeListener {
        fun onGameChange(e: GameChangedEvent)
    }

}