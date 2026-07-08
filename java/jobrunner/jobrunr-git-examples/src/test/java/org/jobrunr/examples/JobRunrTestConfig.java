package org.jobrunr.examples;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@TestConfiguration
public class JobRunrTestConfig {

    @Bean
    @Primary
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .username("sa")
                .password("lemmein")
                .url("jdbc:sqlserver://zubin-pc\\sqlserver2025:1482;databaseName=jobrunr_db;trustServerCertificate=true")
                .driverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver")
                .build();
    }

    @Bean(name = "jobRunrDataSource")
    public DataSource jobRunrDataSource() {
        return dataSource();
    }
}
