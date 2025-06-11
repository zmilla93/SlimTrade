package github.zmilla93.core.trading

import github.zmilla93.core.poe.Game

abstract class ClientMessage {
    @JvmField
    var time: String = ""

    @JvmField
    var date: String = ""

    @JvmField
    var game: Game = Game.PATH_OF_EXILE_1

    var message: String = ""

    // FIXME : raw message is redundant?
    var messageRaw: String? = null
    var messageType: MessageType? = null


//    constructor()

//    constructor(text: String?) {
//        this.messageRaw = text
//        this.message = text
//        this.messageType = MessageType.META_DATA
//    }
}
