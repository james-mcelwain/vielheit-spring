package cc.kojeve.vielheit

import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@EnableJpaRepositories
@EnableJpaAuditing
@Configuration
class JpaConfig {
}