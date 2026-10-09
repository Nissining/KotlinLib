package dev.yousa.kotlin.utils

import cn.nukkit.Player
import cn.nukkit.entity.Entity
import cn.nukkit.event.Listener
import cn.nukkit.level.Level
import cn.nukkit.network.protocol.DataPacket
import cn.nukkit.plugin.PluginBase
import cn.nukkit.scheduler.PluginTask
import java.util.concurrent.atomic.AtomicInteger

object AnyUtils {
    /**
     * 用于代替
     * if (entity is Player) {
     *   // do something
     * }
     */
    fun Any.isPlayerRun(unit: (Player) -> Unit) = isPlayerRun(unit) { true }

    /**
     * 用于代替并支持多条件判断
     * if (entity is Player && entity.name == "Nissining") {
     *   // do something
     * }
     */
    fun Any.isPlayerRun(unit: (Player) -> Unit, filter: (Player) -> Boolean) {
        if (this is Player && filter(this)) unit(this)
    }

    /**
     * 批量玩家发送数据包
     */
    fun Collection<Player>.sendPacket(pk: DataPacket) =
        forEach { it.dataPacket(pk) }

    /**
     * 批量注册事件
     */
    fun Collection<Listener>.regAll(pb: PluginBase) =
        forEach { pb.server.pluginManager.registerEvents(it, pb) }

    /**
     * 判空处理，为空则执行isnull，不为空则执行nonnull
     */
    fun <T> T?.nonNull(nonNull: (T) -> Unit, isNull: () -> Unit) =
        if (this == null) isNull() else nonNull(this)

    /**
     * 清除世界实体（支持过滤lambda）
     */
    fun Level.clearEntities() = clearEntities { it !is Player }

    fun Level.clearEntities(filter: (Entity) -> Boolean) {
        (entities as Array<Entity>).forEach {
            if (filter(it)) it.close()
        }
    }

    /**
     * 匹配大小写
     */
    fun Collection<String>.checkPlugins(pb: PluginBase, enable: Boolean) = forEach {
        val plugin = pb.server.pluginManager.getPlugin(it)
            ?: throw NullPointerException("需要插件：$it")
        if (!plugin.isEnabled && enable) pb.server.pluginManager.enablePlugin(plugin)
    }

    /**
     * 创建一个定时任务
     * @param tick 返回当前秒数
     * @param unit 任务结束回调
     */
    fun Int.createSecTask(
        pluginBase: PluginBase,
        tick: (Int) -> Unit,
        unit: () -> Unit,
        async: Boolean = false,
    ) {
        pluginBase.server.scheduler.scheduleRepeatingTask(
            pluginBase,
            object : PluginTask<PluginBase>(pluginBase) {
                private val over = AtomicInteger(this@createSecTask)
                override fun onRun(p0: Int) {
                    if (over.getAndDecrement() <= 0) {
                        unit()
                        this.cancel()
                        return
                    }
                    tick(over.get())
                }
            },
            20,
            async
        )
    }

}