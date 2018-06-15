package org.vielheit.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "vielheit.db")
class DatabaseProperties {
    lateinit var url: String
    lateinit var username: String
    lateinit var password: String
}