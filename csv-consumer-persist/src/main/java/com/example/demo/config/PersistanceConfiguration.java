package com.example.demo.config;

import com.example.demo.consumer.Consumer;
import org.flywaydb.core.Flyway;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;

@Configuration
public class PersistanceConfiguration {
    protected final Logger log = LoggerFactory.getLogger(Consumer.class);

    @Bean
    public FlywayMigrationStrategy cleanMigrateStrategy() {
        FlywayMigrationStrategy strategy = new FlywayMigrationStrategy() {
            @Override
            public void migrate(Flyway flyway) {
                flyway.repair();
                flyway.migrate();
            }
        };
        return strategy;
    }
}
