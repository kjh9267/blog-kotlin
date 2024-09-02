package me.jun.common.config

import org.flywaydb.core.Flyway
import org.springframework.boot.autoconfigure.flyway.FlywayProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(FlywayProperties::class)
class FlywayConfig {

    @Bean
    fun flyway(flywayProperties: FlywayProperties): Flyway {
        val flyway: Flyway = Flyway.configure()
            .dataSource(flywayProperties.url, flywayProperties.user, flywayProperties.password)
            .baselineOnMigrate(flywayProperties.isBaselineOnMigrate())
            .baselineVersion(flywayProperties.baselineVersion)
            .load();

        flyway.repair()
        flyway.migrate()
        return flyway
    }
}