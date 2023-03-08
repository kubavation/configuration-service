package com.durys.jakub.configurationservice.config

import io.lettuce.core.ClientOptions
import io.lettuce.core.protocol.ProtocolVersion
import org.springframework.boot.autoconfigure.data.redis.LettuceClientConfigurationBuilderCustomizer
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext.SerializationPair

@Configuration
@EnableCaching
internal class RedisConfiguration: LettuceClientConfigurationBuilderCustomizer {

    override fun customize(clientConfigurationBuilder: LettuceClientConfiguration.LettuceClientConfigurationBuilder?) {
        clientConfigurationBuilder?.clientOptions(ClientOptions.builder()
                .protocolVersion(ProtocolVersion.RESP2)
                .build());
    }

    @Bean
    fun cacheConfiguration(): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(SerializationPair.fromSerializer(GenericJackson2JsonRedisSerializer()))
    }

}