package config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "org.vielheit.core.com.vielheit.core.repository")
@EnableAutoConfiguration
@EntityScan(basePackages = {"org.vielheit.core.domain"})
public class RepositoryConfig {}
