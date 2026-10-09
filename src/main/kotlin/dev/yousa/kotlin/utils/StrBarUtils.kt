package dev.yousa.kotlin.utils

import dev.yousa.kotlin.utils.ColorUtils.color

object StrBarUtils {
    fun Int.toStrPar(
        now: Double,
        progress: String = "&a|",
        mid: String = "&e|",
        base: String = "&7|",
        showPercent: Boolean = false,
    ): String {
        val scale = (now / this * 20).toBigDecimal().setScale(0)
        val sb = StringBuilder()
        for (i in 1..20) {
            if (i < scale.intValueExact()) {
                sb.append(progress)
            } else if (i == scale.intValueExact()) {
                sb.append(mid)
            } else {
                sb.append(base)
            }
        }
        if (showPercent) {
            val toInt = (now / this * 100).toBigDecimal().toInt()
            sb.append("${toInt.coerceAtMost(100)}%")
        }
        return sb.toString().color()
    }
}