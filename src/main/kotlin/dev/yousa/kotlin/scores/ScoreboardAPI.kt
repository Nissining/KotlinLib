package dev.yousa.kotlin.scores

import cn.nukkit.Player
import cn.nukkit.network.protocol.RemoveObjectivePacket
import cn.nukkit.network.protocol.SetDisplayObjectivePacket
import cn.nukkit.network.protocol.SetScorePacket
import cn.nukkit.network.protocol.types.DisplaySlot
import cn.nukkit.network.protocol.types.SortOrder

import dev.yousa.kotlin.utils.ColorUtils.color

object ScoreboardAPI {
    private val scoreboards = HashMap<String, String>()

    fun Player.sendScoreboard(title: String, lines: List<String>) {
        if (hasPlayer(this)) remove(this)

        val objName: String = name
        scoreboards[objName] = objName

        val displayPk = SetDisplayObjectivePacket().apply {
            displaySlot = DisplaySlot.SIDEBAR
            objectiveId = objName
            displayName = title.color()
            criteria = "dummy"
            sortOrder = SortOrder.ASCENDING
        }

        val scorePk = SetScorePacket().apply {
            action = SetScorePacket.Action.SET
            for ((index, line) in lines.withIndex()) {
                infos.add(
                    SetScorePacket.ScoreInfo(
                        index.toLong(),
                        objName,
                        index,
                        line.ifBlank { blankLine(index) }.color()
                    )
                )
            }
        }

        dataPacket(displayPk)
        dataPacket(scorePk)
    }

    fun remove(player: Player) {
        if (hasPlayer(player)) {
            val pk = RemoveObjectivePacket()
            pk.objectiveId = player.name
            player.dataPacket(pk)
            scoreboards.remove(player.name)
        }
    }

    private fun hasPlayer(player: Player): Boolean {
        return scoreboards.containsKey(player.name)
    }

    private const val BLANK_CODES = "0123456789abcdefklmnor"

    private fun blankLine(index: Int): String = buildString {
        var i = index
        do {
            append('&').append(BLANK_CODES[i % BLANK_CODES.length])
            i /= BLANK_CODES.length
        } while (i > 0)
    }

}
