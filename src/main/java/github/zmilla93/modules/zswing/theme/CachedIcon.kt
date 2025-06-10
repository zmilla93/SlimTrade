package github.zmilla93.modules.zswing.theme

import java.awt.Color

data class CachedIcon(
    val path: String,
    val size: Int,
    val color: Color? = null,
    val isResource: Boolean = true
)