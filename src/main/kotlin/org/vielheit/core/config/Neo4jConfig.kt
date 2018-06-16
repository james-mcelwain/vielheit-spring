package org.vielheit.core.config

import org.neo4j.ogm.session.SessionFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.context.properties.ConfigurationProperties
import org.vielheit.core.config.properties.Neo4jProperties
import org.vielheit.core.util.logger
import javax.sql.DataSource


@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories("org.vielheit.core.repository")
class Neo4jConfig(val props: Neo4jProperties) {
    val log by logger()

    init { log.info("Using neo4j {}", props.uri) }

    @Bean
    fun sessionFactory(): SessionFactory = SessionFactory(configuration(), "org.vielheit.core.domain")

    @Bean("graphTxManager")
    fun transactionManager(): Neo4jTransactionManager = Neo4jTransactionManager(sessionFactory())

    @Bean
    @ConfigurationProperties(prefix = "vielheit.neo4j")
    fun secondaryDataSource(): DataSource = DataSourceBuilder.create().build()

    @Bean
    fun configuration(): org.neo4j.ogm.config.Configuration = org.neo4j.ogm.config.Configuration.Builder()
            .uri(props.uri)
            .credentials(props.username, props.password)
            .autoIndex("assert")
            .build()
}