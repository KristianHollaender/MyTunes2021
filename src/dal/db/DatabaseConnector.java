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

    private SQLServerDataSource dataSource;

    /**
     * Connects to the SQL server using the given IP, database name, port and the given Username/password.
     */
    public DatabaseConnector()
    {
        dataSource = new SQLServerDataSource();
        dataSource.setServerName("10.176.111.31");
        dataSource.setDatabaseName("MyTunesGruppe6");
        dataSource.setUser("CSe21A_5");
        dataSource.setPassword("CSe21A_5");
        dataSource.setPortNumber(1433);
    }


    /**
     * Tries to connect to the SQL server.
     * @return
     * @throws SQLServerException
     */
    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }


    /**
     * Method used for testing the database connector. Will print true if the application can connect to the SQL server.
     */
    public static void main(String[] args) throws SQLException {

        DatabaseConnector databaseConnector = new DatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here
    }

}
