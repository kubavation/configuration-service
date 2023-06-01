package com.durys.jakub.configurationservice.sharedkernel.caching

import org.springframework.cache.CacheManager
import org.springframework.stereotype.Service

@Service
internal class ConfigCacheService(val cacheManager: CacheManager) {

    fun evict(context: String, module: String) {
        cacheManager.getCache("config")?.evict("${context},${module}")
    }
}