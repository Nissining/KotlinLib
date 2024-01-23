package dev.yousa.kotlin

import cn.nukkit.plugin.PluginBase

/**
 * @author ElainaForever
 * @date 15/12/2023 22:22
 */
class KotlinLib: PluginBase() {
    override fun onEnable() {
        logger.info("KotlinLib is Enabled!")
        logger.info("内置库: Lombok, Hutool")
    }
}