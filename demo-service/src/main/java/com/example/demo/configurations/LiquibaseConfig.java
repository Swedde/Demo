package com.example.demo.configurations;

import liquibase.integration.spring.SpringLiquibase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
public class LiquibaseConfig {

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource, Environment environment) {
		SpringLiquibase liquibase = new SpringLiquibase();
        boolean isDevProfile = Arrays.asList(environment.getActiveProfiles()).contains("dev");
        liquibase.setDropFirst(isDevProfile);
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:db/changelog/db.changelog-master.xml");
		liquibase.setShouldRun(true);
		return liquibase;
	}
}
