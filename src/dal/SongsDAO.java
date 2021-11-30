package dal;

import dal.db.DatabaseConnector;

import java.io.IOException;

public class SongsDAO {

        private DatabaseConnector databaseConnector;

        public SongsDAO() throws IOException {
            databaseConnector = new DatabaseConnector(); //New Database object, used to creating the connection.
        }
}
