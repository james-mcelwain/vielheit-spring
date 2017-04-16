package com.vielheit;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.transaction.ChainedTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@SpringBootApplication
@EnableCaching
public class CoreApplication {
	@Bean
	public ChainedTransactionManager transactionManager(
			@Qualifier("coreTransactionManager") PlatformTransactionManager coreTxManager,
			@Qualifier("graphTransactionManager") Neo4jTransactionManager graphTxManager
	) {
		return new ChainedTransactionManager(coreTxManager, graphTxManager);
	}

	public static void main(String[] args) {
		SpringApplication.run(CoreApplication.class, args);
	}
}
