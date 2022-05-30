package ua.dao;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;


    public static void inntConfig(String driver, String url, String userName, String password) {
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);
        config.setUsername(userName);
        config.setPassword(password);

        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }


    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
//        inntConfig("com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3306/periodicals", "root", "root");
        return ds.getConnection();
    }
}
