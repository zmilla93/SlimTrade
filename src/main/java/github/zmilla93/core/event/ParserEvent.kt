package github.zmilla93.core.event

class ParserEvent(val type: Type, val parser: Parser) {

    enum class Parser {
        POE1, POE2, COMBINED
    }

    enum class Type {
        RESTART, LOADED
    }

}