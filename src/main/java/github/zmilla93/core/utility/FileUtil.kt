package github.zmilla93.core.utility

object FileUtil {

    fun resourceAsString(fileName: String): String {
        val resource = javaClass.classLoader.getResource(fileName)
        if (resource == null) throw RuntimeException("Resource not found: $fileName")
        return resource.readText()
    }

}