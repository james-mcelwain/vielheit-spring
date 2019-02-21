package cc.kojeve.vielheit.config.jpa

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.transaction.annotation.EnableTransactionManagement

@EnableTransactionManagement
@EnableJpaRepositories(basePackages = ["cc.kojeve.vielheit.repository"])
@EnableJpaAuditing
@Configuration
class JpaConfig