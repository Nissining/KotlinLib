package dev.yousa.kotlin.utils

import com.google.common.cache.Cache
import com.google.common.cache.CacheBuilder
import java.util.concurrent.TimeUnit

object CacheUt {
    fun <T, U> writeCache(
        time: Long,
        unit: TimeUnit,
        max: Long = 100
    ): Cache<T, U> = CacheBuilder.newBuilder()
        .maximumSize(max)
        .expireAfterWrite(time, unit)
        .build<T, U>()

    fun <T, U> Cache<T, U>.inCache(key: Any) = getIfPresent(key) != null

}