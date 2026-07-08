package org.jobrunr.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.jobrunr.configuration.JobRunrPro;
import org.jobrunr.storage.sql.common.SqlStorageProviderFactory;

import javax.sql.DataSource;

public class Main {
    public static void main(String[] args) {
        initJobRunrPro();
    }

    private static void initJobRunrPro() {
        // 1. Define the MSSQL DataSource via HikariCP
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:sqlserver://zubin-pc:1482;instanceName=sqlserver2025;databaseName=jobrunr_db;trustServerCertificate=true");
        config.setUsername("sa");
        config.setPassword("lemmein");
        config.setDriverClassName("com.microsoft.sqlserver.jdbc.SQLServerDriver");

        DataSource dataSource = new HikariDataSource(config);

        // 2. Configure JobRunr to use the MS SQL StorageProvider
        JobRunrPro.configure()
                .useStorageProvider(SqlStorageProviderFactory.using(dataSource)) // Auto-detects MSSQL dialect
                .useBackgroundJobServer()
                .useDashboard()
                .initialize();
    }
}