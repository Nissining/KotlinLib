package dev.yousa.kotlin.utils

import cn.nukkit.Player
import cn.nukkit.math.AxisAlignedBB

object AreaUtils {
    fun AxisAlignedBB.inAreaExecute(player: Player, unit: () -> Unit) {
        if (isVectorInside(player)) unit()
    }

    fun AxisAlignedBB.inAreaExecuteCmd(player: Player, cmd: String) {
        inAreaExecute(player) { player.server.dispatchCommand(player, cmd) }
    }

    fun AxisAlignedBB.in2DAreaExecute(player: Player, unit: () -> Unit) {
        if (isVectorInXZ(player)) unit()
    }

    fun AxisAlignedBB.in2DAreaExecuteCmd(player: Player, cmd: String) =
        in2DAreaExecute(player) { player.server.dispatchCommand(player, cmd) }
}