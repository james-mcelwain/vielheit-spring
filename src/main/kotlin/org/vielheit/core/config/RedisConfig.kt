package org.vielheit.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisStandaloneConfiguration
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.vielheit.core.config.properties.RedisProperties
import org.vielheit.core.util.logger


@Configuration
class RedisConfig(val props: RedisProperties) {
    val log by logger()

    init {
        log.info("Using redis {}:{}", props.host, props.port)
    }

    @Bean
    fun redisConnectionFactory(): LettuceConnectionFactory {
        return LettuceConnectionFactory(RedisStandaloneConfiguration(props.host, props.port.toInt()))
    }
}