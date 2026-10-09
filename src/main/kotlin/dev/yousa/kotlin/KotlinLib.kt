package dev.yousa.kotlin

import cn.nukkit.plugin.PluginBase

/**
 * @author ElainaForever
 * @date 15/12/2023 22:22
 */
class KotlinLib: PluginBase() {
    companion object {
        @JvmStatic
        lateinit var instance: KotlinLib
    }
    override fun onEnable() {
        instance = this
        logger.info("KotlinLib is Enabled!")
        logger.info("内置: Hutool, kt编译器")
    }
}