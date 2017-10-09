package com.vielheit;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.context.request.RequestContextListener;

@SpringBootApplication
@PropertySource("classpath:application-test.properties")
@PropertySource("classpath:application.properties")
@EnableCaching
public class CoreApplication {
    @Bean
    public ChainedTransactionManager transactionManager(
            @Qualifier("coreTransactionManager") PlatformTransactionManager coreTxManager,
            @Qualifier("graphTransactionManager") Neo4jTransactionManager graphTxManager
    ) {
        return new ChainedTransactionManager(coreTxManager, graphTxManager);
    }

    @Bean
    public RequestContextListener requestContextListener(){
        return new RequestContextListener();
    }

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }
}
