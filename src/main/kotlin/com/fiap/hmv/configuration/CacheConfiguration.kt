package com.fiap.hmv.configuration

import com.google.common.cache.CacheBuilder
import org.springframework.cache.Cache
import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCache
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import java.util.concurrent.TimeUnit

@Configuration
@EnableCaching
class CacheConfiguration {

    companion object {
        const val OTP_CACHE = "OTP_CACHE"
        const val ACTIVE_TOKENS = "ACTIVE_TOKEN"
    }

    @Bean
    fun cacheManager(): CacheManager {
        val cacheManager: ConcurrentMapCacheManager = object : ConcurrentMapCacheManager() {
            override fun createConcurrentMapCache(name: String): Cache {
                return ConcurrentMapCache(
                    name, CacheBuilder.newBuilder()
                        .expireAfterWrite(10, TimeUnit.MINUTES)
                        .build<Any, Any>()
                        .asMap(), false
                )
            }
        }
        cacheManager.setCacheNames(listOf(OTP_CACHE))
        return cacheManager
    }

    @Bean
    @Primary
    fun tokenCacheManager(): CacheManager {
        val cacheManager: ConcurrentMapCacheManager = object : ConcurrentMapCacheManager() {
            override fun createConcurrentMapCache(name: String): Cache {
                return ConcurrentMapCache(
                    name, CacheBuilder.newBuilder()
                        .expireAfterWrite(1, TimeUnit.DAYS)
                        .build<Any, Any>()
                        .asMap(), false
                )
            }
        }
        cacheManager.setCacheNames(listOf(ACTIVE_TOKENS))
        return cacheManager
    }

    @Bean("otp-cache")
    fun getOTPCache(): Cache =
        cacheManager().getCache(OTP_CACHE) ?: throw IllegalArgumentException("Cache namespace does not exists")

    @Bean("active-tokens")
    fun getTokenCache(): Cache =
        tokenCacheManager().getCache(ACTIVE_TOKENS) ?: throw IllegalArgumentException("Cache namespace does not exists")

}