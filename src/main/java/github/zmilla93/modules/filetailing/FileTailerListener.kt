package github.zmilla93.modules.filetailing

interface FileTailerListener {

    fun init(tailer: FileTailer)

    fun fileNotFound()

    fun fileRotated() // Not implemented yet

    fun onLoad()

    fun handle(line: String)

}
