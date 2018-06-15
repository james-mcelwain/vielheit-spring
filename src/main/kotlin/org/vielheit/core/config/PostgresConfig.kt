package org.vielheit.core.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.env.Environment
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.orm.jpa.JpaTransactionManager
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.vielheit.core.config.properties.DatabaseProperties
import org.vielheit.core.util.logger
import java.util.*
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("org.vielheit.core.repository")
class PostgresConfig (val props: DatabaseProperties) {
    val log by logger()

    init { log.info("Using datasource {}", props.url) }

    @Bean
    @Primary
    fun dataSource(): DataSource = DriverManagerDataSource()
            .apply {
                setDriverClassName("org.postgresql.Driver")
                url = props.url
                username = props.username
                password = props.password
            }

    @Bean
    @ConfigurationProperties(prefix = "spring.jpa")
    fun entityManagerFactory(builder: EntityManagerFactoryBuilder, dataSource: DataSource): LocalContainerEntityManagerFactoryBean =
            builder.dataSource(dataSource).packages("org.vielheit.core.domain").build()
                    .apply { setJpaProperties(additionalProperties) }


    @Bean("coreTxManager")
    fun transactionManager(entityManagerFactory: EntityManagerFactory) = JpaTransactionManager(entityManagerFactory)

    val additionalProperties
        get() = Properties()
                .apply {
                    setProperty("hibernate.hbm2ddl.auto", "create-drop")
                    setProperty("spring.jpa.database-platform", "org.hibernate.dialect.PostgreSQLDialect")
                    setProperty("hibernate.jdbc.lob.non_contextual_creation", "true") //HHH-12368
                }
}

