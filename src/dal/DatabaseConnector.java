package dal;


import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import com.microsoft.sqlserver.jdbc.SQLServerException;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnector {

    private SQLServerDataSource dataSource;


    /**
     * Connect to the SQL database using the servers IP, database name and user login.
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


    public Connection getConnection() throws SQLServerException {
        return dataSource.getConnection();
    }

    /**
     * Prints out if the connection is true or false
     * if true, the connection is open
     */
    public static void main(String[] args) throws SQLException {

        DatabaseConnector databaseConnector = new DatabaseConnector();

        try (Connection connection = databaseConnector.getConnection()) {

            System.out.println("Is it open? " + !connection.isClosed());

        } //Connection gets closed here
    }
}
