package github.zmilla93.core.utility

import java.nio.charset.StandardCharsets

object ResourceUtil {

//    fun bufferedReader(path: String): BufferedReader {
//        val stream = ResourceUtil::class.java.getResourceAsStream(path)
//        if (stream == null) throw RuntimeException("Resource not found: $path")
//        return stream.bufferedReader(StandardCharsets.UTF_8)
//    }

    /**
     * Reads a file resource, returning the lines to a callback labda.
     */
    fun read(path: String, callback: (String) -> Unit) {
        val stream = ResourceUtil::class.java.getResourceAsStream(path)
        if (stream == null) throw RuntimeException("Resource not found: $path")
        stream.bufferedReader(StandardCharsets.UTF_8).useLines { lines ->
            lines.forEach { line -> callback(line) }
        }
    }

}