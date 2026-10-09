package dev.yousa.kotlin.utils

import cn.nukkit.item.Item
import dev.yousa.kotlin.utils.ColorUtils.color
import dev.yousa.kotlin.utils.ColorUtils.colors

object ItemUt {
    // k=>id, k2=>meta
    fun Pair<Int, Int>.fastGet(displayName: String, vararg lore: String): Item? {
        return Triple(this.first, this.second, 1).fastGet(displayName, *lore)
    }

    // k=>id, k2=>meta, k3=>count
    fun Triple<Int, Int, Int>.fastGet(displayName: String, vararg lore: String): Item? {
        return Item.get(this.first, this.second, this.third).also {
            if (displayName.isNotEmpty()) it.customName = displayName.color()
            if (lore.isNotEmpty()) it.setLore(*colors(*lore))
        }
    }
}