package com.example.demo.helpers;

import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class BaseTestWithDb {
    private static final PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>(
        "postgres:16-alpine"
    );

    static {
        postgresqlContainer.setWaitStrategy(
            new LogMessageWaitStrategy()
                .withRegEx(".*database system is ready to accept connections.*")
                .withTimes(1)
                .withStartupTimeout(Duration.of(15, ChronoUnit.SECONDS))
        );

        postgresqlContainer.start();
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgresqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgresqlContainer::getUsername);
        registry.add("spring.datasource.password", postgresqlContainer::getPassword);
    }

    @AfterEach
    void truncateTables() {
        var sql = "DO $$\n" +
            "DECLARE\n" +
            "    table_name text;\n" +
            "BEGIN\n" +
            "    FOR table_name IN (SELECT tablename FROM pg_tables WHERE schemaname = 'public')\n" +
            "    LOOP\n" +
            "        EXECUTE 'TRUNCATE TABLE ' || quote_ident('public') || '.' || quote_ident(table_name) || ' CASCADE;';\n" +
            "    END LOOP;\n" +
            "END $$;";

        jdbcTemplate.execute(sql);
    }

}
