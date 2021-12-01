package dal.db;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnector {
    private static final String PROP_FILE = "src/dal/db/database.settings";

    private SQLServerDataSource dataSource;


    /**
     * Connect to the SQL database using the servers IP, database name and user login.
     */
    public DatabaseConnector() throws IOException {
        Properties databaseProperties = new Properties();
        databaseProperties.load(new FileInputStream(PROP_FILE));
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("Server");
        dataSource.setDatabaseName("Database");
        dataSource.setUser("User");
        dataSource.setPassword("Password");
        dataSource.setPortNumber(1433);
    }


    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Prints out if the connection is true or false
     * if true, the connection is open
     */

}
