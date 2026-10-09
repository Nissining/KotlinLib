package dev.yousa.kotlin.utils

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.scheduler.PluginTask
import cn.nukkit.scheduler.Task
import dev.yousa.kotlin.KotlinLib

object ServerUt {
    val server: Server = Server.getInstance()

    fun forEachOnlinePlayers(filter: (Player) -> Boolean, unit: (Player) -> Unit) {
        server.onlinePlayers.values
            .filter(filter)
            .forEach(unit)
    }
}