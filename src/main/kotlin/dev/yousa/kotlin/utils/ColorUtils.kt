package dev.yousa.kotlin.utils

import cn.nukkit.utils.TextFormat

object ColorUtils {
    fun String.color() = TextFormat.colorize(this)!!
    fun List<String>.colors() = map { "&r$it".color() }
    fun colors(vararg strs: String): Array<String> {
        return strs.map { it.color() }.toTypedArray()
    }
}