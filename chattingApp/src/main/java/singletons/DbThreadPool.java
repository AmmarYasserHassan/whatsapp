package singletons;

import config.ApplicationProperties;
import org.postgresql.ds.PGPoolingDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DbThreadPool {

    private static volatile DbThreadPool dbThreadPool = null;
    protected PGPoolingDataSource postgresqlDBConnectionsPool;

    private DbThreadPool() {
        String host = ApplicationProperties.getPostgresHost();
        String username = ApplicationProperties.getProperty("postgresUsername");
        String password = ApplicationProperties.getProperty("postgresPassword");
        String database = ApplicationProperties.getProperty("postgresDatabaseName");

        PGPoolingDataSource ds = new PGPoolingDataSource();
        ds.setUser(username);
        ds.setServerName(host);
        ds.setPassword(password);
        ds.setDatabaseName(database);
        ds.setInitialConnections(0);
        Integer maxConnections = Integer.parseInt(ApplicationProperties.getProperty("postgresConnectionsPoolMaxSize"));
        ds.setMaxConnections(maxConnections);
        this.postgresqlDBConnectionsPool = ds;
    }

    public static DbThreadPool getInstance() {
        if (dbThreadPool != null) return dbThreadPool;

        synchronized (DbThreadPool.class) {

            if (dbThreadPool == null) {

                dbThreadPool = new DbThreadPool();
            }
        }

        return dbThreadPool;
    }

    public void setMaxConnections(int max) {
        this.postgresqlDBConnectionsPool.setMaxConnections(max);
    }

    public Connection getConnection() throws SQLException {
        return this.postgresqlDBConnectionsPool.getConnection();
    }

}
