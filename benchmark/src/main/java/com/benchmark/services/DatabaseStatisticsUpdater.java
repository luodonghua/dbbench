package com.benchmark.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

@Component
public class DatabaseStatisticsUpdater {

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(DatabaseStatisticsUpdater.class);


    @Autowired
    public DatabaseStatisticsUpdater(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.dataSource = dataSource;
    }

    public void updateStatistics() {
        String dbType = getDatabaseType();
        
        switch (dbType.toLowerCase()) {
            case "postgresql":
                updatePostgresStatistics();
                break;
            case "oracle":
                updateOracleStatistics();
                break;
            case "mysql":
                updateMySQLStatistics();
                break;
            case "microsoft sql server":
                updateSQLServerStatistics();
                break;
            default:
                logger.error("Unsupported database type for statistics update: " + dbType);
        }
    }

    private String getDatabaseType() {
        try {
            DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
            return metaData.getDatabaseProductName();
        } catch (SQLException e) {
            throw new RuntimeException("Error determining database type", e);
        }
    }

    private void updatePostgresStatistics() {
        logger.info("Updating PostgreSQL statistics...");
        String[] tables = {"dbbench_accounts", "dbbench_branches", "dbbench_tellers", "dbbench_history"};
        for (String table : tables) {
            jdbcTemplate.execute("VACUUM ANALYZE " + table);
        }
        logger.info("PostgreSQL statistics updated successfully.");
    }

    private void updateOracleStatistics() {
        logger.info("Updating Oracle statistics...");
        String[] tables = {"dbbench_accounts", "dbbench_branches", "dbbench_tellers", "dbbench_history"};
        for (String table : tables) {
            jdbcTemplate.execute("BEGIN DBMS_STATS.GATHER_TABLE_STATS(ownname => USER, tabname => '" + table + "'); END;");
        }
        logger.info("Oracle statistics updated successfully.");
    }

    private void updateMySQLStatistics() {
        logger.info("Updating MySQL statistics...");
        String[] tables = {"dbbench_accounts", "dbbench_branches", "dbbench_tellers", "dbbench_history"};
        for (String table : tables) {
            jdbcTemplate.execute("ANALYZE TABLE " + table);
        }
        logger.info("MySQL statistics updated successfully.");
    }

    private void updateSQLServerStatistics() {
        logger.info("Updating SQL Server statistics...");
        jdbcTemplate.execute("EXEC sp_updatestats");
        logger.info("SQL Server statistics updated successfully.");
    }
}
