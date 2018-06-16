package org.vielheit.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "vielheit.redis")
class RedisProperties {
    lateinit var host: String
    lateinit var port: String
}