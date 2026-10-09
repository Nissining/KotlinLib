package dev.yousa.kotlin.utils

import cn.nukkit.Player
import cn.nukkit.command.CommandSender
import cn.nukkit.level.Sound
import dev.yousa.kotlin.utils.CacheUt.inCache
import dev.yousa.kotlin.utils.ColorUtils.color
import java.util.concurrent.TimeUnit

object MsgUtils {
    // 由于网易每tick都检测屏蔽词，需要等待1秒后再更新
    // 只限popup和tip
    private val updateCache = CacheUt.writeCache<String, Player>(1, TimeUnit.SECONDS)

    fun Iterable<Player>.msg(msg: String, type: Int = 0, sound: Sound? = null) {
        forEach { it.msg(msg, type, sound) }
    }

    fun Iterable<Player>.title(
        title: String,
        sub: String,
        sec: Int,
        sound: Sound? = null,
    ) {
        forEach { it.title(title, sub, sec, sound) }
    }

    fun Iterable<Player>.msgAndTitle(
        msg: String,
        sub: String,
        prefix: String = ": ",
        sec: Int = 3,
        sound: Sound? = null,
    ) {
        forEach { it.msgAndTitle(msg, sub, prefix, sec, sound) }
    }

    fun Player.msg(msg: String, type: Int = 0, sound: Sound? = null) {
        when (type) {
            1 -> {
                if (!updateCache.inCache(msg)) {
                    updateCache.put(msg, this)
                    sendTip(msg.color())
                }
            }

            2 -> {
                if (!updateCache.inCache(msg)) {
                    updateCache.put(msg, this)
                    sendPopup(msg.color())
                }
            }

            else -> sendMessage(msg.color())
        }
        if (sound != null) level.addSound(this, sound.sound, this)
    }

    fun CommandSender.msg(msg: String, sound: Sound? = null) = sendMessage(msg.color())

    fun Player.title(msg: String, sub: String, sec: Int, sound: Sound? = null) {
        sendTitle(msg.color(), sub.color(), 5, sec * 20, 5)
        if (sound != null) level.addSound(this, sound.sound, this)
    }

    fun Player.msgAndTitle(
        msg: String,
        sub: String,
        prefix: String = ": ",
        sec: Int = 3,
        sound: Sound? = null,
    ) {
        msg("$msg$prefix$sub", sound)
        title(msg, sub, sec, sound)
    }
}