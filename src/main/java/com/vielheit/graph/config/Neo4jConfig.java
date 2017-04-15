package com.vielheit.graph.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@EnableTransactionManagement
@EnableNeo4jRepositories(basePackages = "com.vielheit.graph.repository")
@ComponentScan({"com.vielheit.graph"})
public class Neo4jConfig {
    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.vielheit.graph.domain");
    }

    @Bean(name="graphTransactionManager")
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(getSessionFactory());
    }

    @Bean
    @ConfigurationProperties(prefix="spring.data.neo4j")
    public DataSource secondaryDataSource() {
        return DataSourceBuilder.create().build();
    }
}
