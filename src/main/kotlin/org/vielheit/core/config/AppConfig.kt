package org.vielheit.core.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.transaction.ChainedTransactionManager
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.transaction.PlatformTransactionManager


@Configuration
class AppConfig {
    companion object {
        val API_BASE  = "/api"
    }

    @Bean
    fun transactionManager(
            @Qualifier("coreTxManager") coreTxManager: PlatformTransactionManager,
            @Qualifier("graphTxManager") graphTxManager: Neo4jTransactionManager
    ): ChainedTransactionManager = ChainedTransactionManager(coreTxManager, graphTxManager)
}