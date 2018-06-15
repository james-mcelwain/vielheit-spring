package org.vielheit.core.config.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "vielheit.neo4j")
class Neo4jProperties {
    lateinit var uri: String
    lateinit var username: String
    lateinit var password: String
}