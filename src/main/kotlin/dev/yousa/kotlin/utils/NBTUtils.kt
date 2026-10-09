package dev.yousa.kotlin.utils

import cn.nukkit.entity.Entity
import cn.nukkit.entity.Entity.getDefaultNBT

object NBTUtils {

    fun Entity.shootNBT(shootSpeed: Double = 1.0) = getDefaultNBT(
        add(0.0, eyeHeight.toDouble()),
        directionVector.normalize().multiply(shootSpeed),
        yaw.toFloat(),
        pitch.toFloat()
    )

}